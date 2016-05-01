package com.clkio.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.repository.LoginRepository;
import com.clkio.service.EmailService;
import com.clkio.service.LoginService;
import com.clkio.service.UserService;

@Service
public class LoginServiceImpl implements LoginService, InitializingBean {

	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern( "yyyyMMddHHmmss" );
	
	@Autowired
	private LoginRepository repository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The property 'repository' has not been properly initialized." );
		Assert.notNull( this.emailService, "The property 'emailService' has not been properly initialized." );
		Assert.notNull( this.userService, "The property 'userService' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class, RuntimeException.class } )
	public String login( final User user, final String ip ) throws ValidationException, PersistenceException {
		if ( user == null )
			throw new ValidationException( "Argument 'user' is mandatory." );
		if ( !StringUtils.hasText( user.getPassword() ) )
			throw new ValidationException( "No proper password was provided." );
		if( !StringUtils.hasText( ip ) )
			throw new ValidationException( "Argument 'ip' is mandatory." );
		
		String loginFailed = "Email address and/or password not found or they do not match.";
		
		Email syncEmail = this.emailService.getBy( user.getEmail().getAddress(), true );
		if ( syncEmail == null )
			throw new PersistenceException( loginFailed );
		
		User syncUser = this.userService.getBy( syncEmail );
		if ( syncUser == null )
			throw new PersistenceException( loginFailed );
		
		BCryptPasswordEncoder pwdCrypter = new BCryptPasswordEncoder();
		if ( !pwdCrypter.matches( user.getPassword(), syncUser.getPassword() ) )
			throw new PersistenceException( loginFailed );
		
		LocalDateTime now = LocalDateTime.now();
		String code = pwdCrypter.encode( DTF.format( now ) + user.getEmail().getAddress() );
		
		if ( !this.repository.insert( syncUser, code, now, ip ) )
			throw new PersistenceException( "It was not possible performing insert for 'login' record." );
		
		return code;
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean check( final String code ) throws ValidationException {
		if ( !StringUtils.hasText( code ) )
			throw new ValidationException( "The argument 'code' is mandatory." );
		return this.repository.check( code );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void setAsInvalid( final User user ) throws ValidationException {
		if ( user == null || user.getId() == null )
			throw new ValidationException( "The argument 'user' and its nested 'id' property are mandatory." );
		this.repository.setAsInvalid( user );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void logout( final String code ) throws ValidationException, PersistenceException {
		if ( !StringUtils.hasText( code ) )
			throw new ValidationException( "The argument 'code' is mandatory." );
		
		if( !this.repository.logout( code ) )
			throw new PersistenceException( "Logout failed." );
	}
	
}
