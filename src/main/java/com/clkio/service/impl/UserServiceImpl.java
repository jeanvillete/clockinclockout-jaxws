package com.clkio.service.impl;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.Email;
import com.clkio.domain.NewUserEmailConfirmation;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
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
		Assert.notNull( this.repository, "The field repository shouldn't be null." );
		Assert.notNull( this.emailService, "The field emailService shouldn't be null." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final User user ) {
		Assert.state( user.getLocale() != null, "No locale was provided to argument user.");
		
		this.repository.insert( user );
		
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
		user.getEmail().setRecordedTime( new Date() );
		NewUserEmailConfirmation emailConfirmation = new NewUserEmailConfirmation( user.getEmail() );
		user.getEmail().setConfirmationCode( emailConfirmation.getHash() );
		this.emailService.insert( user.getEmail() );
		this.emailService.send( emailConfirmation );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public User getBy( final Email email ) {
		Assert.notNull( email );
		try {
			User syncUser = this.repository.getBy( email );
			syncUser.setEmail( email );
			return syncUser;
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final User user ) {
		Assert.notNull( user );
		
		List< Profile > profiles = this.profileService.listBy( user );
		if ( !CollectionUtils.isEmpty( profiles ) ) {
			for ( Profile profile : profiles ) {
				this.profileService.delete( profile );
			}
		}
		
		this.repository.delete( user );
	}

	@Override
	@Transactional( propagation = Propagation.MANDATORY )
	public boolean changePassword( User syncUser ) {
		Assert.notNull( syncUser );
		return this.repository.changePassword( syncUser );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void cleanNotConfirmed() {
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DATE, -1 );
		
		List< Email > emails = this.emailService.listPrimaryNotConfirmed( calendar.getTime() );
		if ( !CollectionUtils.isEmpty( emails ) ) {
			for ( Email email : emails ) {
				this.emailService.delete( email );
				this.delete( email.getUser() );
			}
		}
		
		this.emailService.deleteNotPrimaryNotConfirmed( calendar.getTime() );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public User getBy( String loginCode ) {
		Assert.hasText( loginCode, "No value was provide for 'loginCode' argument." );
		try {
			return this.repository.getBy( loginCode );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}
}
