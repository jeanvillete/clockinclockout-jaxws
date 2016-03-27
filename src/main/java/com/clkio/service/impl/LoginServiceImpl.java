package com.clkio.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.repository.LoginRepository;
import com.clkio.service.EmailService;
import com.clkio.service.LoginService;
import com.clkio.service.UserService;

@Service
public class LoginServiceImpl implements LoginService, InitializingBean {

	private static final Logger LOG = Logger.getLogger( LoginServiceImpl.class );
	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" );
	
	@Autowired
	private LoginRepository repository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.emailService, "The property 'emailService' has not been properly initialized." );
		Assert.notNull( this.userService, "The property 'userService' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW )
	public String login( User user, String ip ) {
		Assert.notNull( user );
		Assert.state( StringUtils.hasText( user.getPassword() ), "No proper password was provided." );
		Assert.state( StringUtils.hasText( ip ), "Argument 'ip' is mandatory." );
		
		try {
			Email syncEmail = this.emailService.getBy( user.getEmail().getAddress(), true );
			Assert.state( syncEmail != null );
			
			User syncUser = this.userService.getBy( syncEmail );
			Assert.state( syncUser != null );
			
			BCryptPasswordEncoder pwdCrypter = new BCryptPasswordEncoder();
			
			Assert.state( pwdCrypter.matches( user.getPassword(), syncUser.getPassword() ) );
			
			LocalDateTime now = LocalDateTime.now();
			String code = pwdCrypter.encode( DTF.format( now ) + user.getEmail().getAddress() );
			
			this.repository.insert( syncUser, code, now, ip );
			
			return code;
		} catch ( IllegalStateException e ) {
			LOG.error( e );
			throw new IllegalArgumentException( "Email address and/or password not found or they do not match." );
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean check( String code ) {
		Assert.hasText( code, "The argument 'code' is mandatory." );
		return this.repository.check( code );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void setAsInvalid( User user ) {
		Assert.state( user != null && user.getId() != null,
				"The argument 'user' and its nested 'id' property are mandatory." );
		this.repository.setAsInvalid( user );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void logout( String code ) {
		Assert.hasText( code, "The argument 'code' is mandatory." );
		Assert.state( this.repository.logout( code ), "Logout failed." );
	}
	
}
