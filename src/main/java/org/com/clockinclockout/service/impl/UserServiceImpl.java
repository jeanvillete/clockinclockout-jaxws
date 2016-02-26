package org.com.clockinclockout.service.impl;

import java.util.Date;
import java.util.List;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.EmailConfirmation;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.repository.UserRepository;
import org.com.clockinclockout.service.EmailService;
import org.com.clockinclockout.service.ProfileService;
import org.com.clockinclockout.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class UserServiceImpl implements UserService, InitializingBean {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ProfileService profileService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The field repository shouldn't be null." );
		Assert.notNull( this.emailService, "The field emailService shouldn't be null." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final User user ) {
		Assert.state( user.getLocale() != null, "No locale was provided to argument user.");
		
		user.setPassword( new BCryptPasswordEncoder().encode( user.getPassword() ) );
		this.repository.insert( user );
		
		Profile profile = new Profile( user, "default profile", "HH:mm", "yyyy-MM-dd" );
		this.profileService.insert( profile );
		
		user.getEmail().setPrimary( true );
		user.getEmail().setRecordedTime( new Date() );
		EmailConfirmation emailConfirmation = new EmailConfirmation( user.getEmail() );
		user.getEmail().setConfirmationCode( emailConfirmation.getHash() );
		this.emailService.insert( user.getEmail() );
		
		this.emailService.send( emailConfirmation );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public User getBy( final Email email ) {
		Assert.notNull( email );
		
		User user = this.repository.getBy( email );
		user.setProfiles( this.profileService.listBy( user ) );
		
		return user;
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
	
}
