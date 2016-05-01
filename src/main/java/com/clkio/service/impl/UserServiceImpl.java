package com.clkio.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.NewUserEmailConfirmation;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;
import com.clkio.repository.UserRepository;
import com.clkio.service.EmailService;
import com.clkio.service.ProfileService;
import com.clkio.service.UserService;

@Service
public class UserServiceImpl implements UserService, InitializingBean {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ProfileService profileService;

	@Autowired
	private EmailService emailService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The field 'repository' shouldn't be null." );
		Assert.notNull( this.profileService, "The field 'profileService' shouldn't be null." );
		Assert.notNull( this.emailService, "The field 'emailService' shouldn't be null." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void insert( final User user ) throws ConflictException, ValidationException, PersistenceException {
		if ( user.getLocale() == null )
			throw new ValidationException( "No locale was provided to argument user." );

		if ( !this.repository.insert( user ) )
			throw new PersistenceException( "It was not possible perfoming insert for the 'user' record." );
		
		Profile profile = new Profile( user, "default profile", "HH:mm", "yyyy-MM-dd" );
		profile.setDefaultExpectedSunday( Duration.ofHours( 0 ) );
		profile.setDefaultExpectedMonday( Duration.ofHours( 8 ) );
		profile.setDefaultExpectedTuesday( Duration.ofHours( 8 ) );
		profile.setDefaultExpectedWednesday( Duration.ofHours( 8 ) );
		profile.setDefaultExpectedThursday( Duration.ofHours( 8 ) );
		profile.setDefaultExpectedFriday( Duration.ofHours( 8 ) );
		profile.setDefaultExpectedSaturday( Duration.ofHours( 0 ) );
		this.profileService.insert( profile );
		
		user.getEmail().setPrimary( true );
		user.getEmail().setRecordedTime( LocalDateTime.now() );
		NewUserEmailConfirmation emailConfirmation = new NewUserEmailConfirmation( user.getEmail() );
		user.getEmail().setConfirmationCode( emailConfirmation.getHash() );
		this.emailService.insert( user.getEmail() );
		this.emailService.send( emailConfirmation );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public User getBy( final Email email ) throws PersistenceException, ValidationException {
		if ( email == null )
			throw new ValidationException( "Argument 'email' is mandatory." );
		try {
			User syncUser = this.repository.getBy( email );
			syncUser.setEmail( email );
			return syncUser;
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void delete( final User user ) throws ValidationException, PersistenceException {
		if ( user == null )
			throw new ValidationException( "Argument 'user' is mandatory." );
		
		List< Profile > profiles = this.profileService.listBy( user );
		if ( !CollectionUtils.isEmpty( profiles ) ) {
			for ( Profile profile : profiles ) {
				this.profileService.delete( profile );
			}
		}
		
		if ( !this.repository.delete( user ) )
			throw new PersistenceException( "It was not possible performing delete for 'user' record." );
	}

	@Override
	@Transactional( propagation = Propagation.MANDATORY, rollbackFor = { Exception.class, RuntimeException.class } )
	public boolean changePassword( final User user ) throws ValidationException {
		if ( user == null )
			throw new ValidationException( "Argument 'user' is mandatory." );
		return this.repository.changePassword( user );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void cleanNotConfirmed() throws ValidationException, PersistenceException, ConflictException {
		LocalDateTime range = LocalDateTime.now().minusDays( 1 );
		
		List< Email > emails = this.emailService.listPrimaryNotConfirmed( range );
		if ( !CollectionUtils.isEmpty( emails ) ) {
			for ( Email email : emails ) {
				this.emailService.delete( email );
				this.delete( email.getUser() );
			}
		}
		
		this.emailService.deleteNotPrimaryNotConfirmed( range );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public User getBy( final String loginCode ) throws PersistenceException, ValidationException {
		if ( !StringUtils.hasText( loginCode ) )
			throw new ValidationException( "No value was provide for 'loginCode'." );
		try {
			return this.repository.getBy( loginCode );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}
}
