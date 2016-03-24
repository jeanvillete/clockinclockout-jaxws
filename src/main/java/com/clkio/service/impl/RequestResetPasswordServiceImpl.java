package com.clkio.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.EmailResetPassword;
import com.clkio.domain.RequestResetPassword;
import com.clkio.domain.User;
import com.clkio.repository.RequestResetPasswordRepository;
import com.clkio.service.EmailService;
import com.clkio.service.LoginService;
import com.clkio.service.RequestResetPasswordService;
import com.clkio.service.UserService;

@Service
public class RequestResetPasswordServiceImpl implements RequestResetPasswordService, InitializingBean {

	private static final SimpleDateFormat SDF = new SimpleDateFormat( "dd_MM_yyyy_HH:mm:ss_z" );
	
	@Autowired
	private RequestResetPasswordRepository repository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The property 'repository' has not been properly initialized." );
		Assert.notNull( this.emailService, "The property 'emailService' has not been properly initialized." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void processRequest( RequestResetPassword requestResetPassword ) {
		Assert.notNull( requestResetPassword );
		
		Email syncEmail = this.emailService.getBy( requestResetPassword.getUser().getEmail().getAddress(), true ); // email's database synchronized reference
		Assert.notNull( syncEmail, "No record found for the provided emailAddress." );
		
		User syncUser = this.userService.getBy( syncEmail ); // user's database synchronized reference
		requestResetPassword.setUser( syncUser );
		requestResetPassword.setRequestDate( new Date() );
		
		this.deleteNotConfirmed( syncUser );
		
		EmailResetPassword emailResetPassword = new EmailResetPassword( syncEmail );
		requestResetPassword.setRequestCodeValue( emailResetPassword.getHash() );
		this.repository.insert( requestResetPassword );
		
		this.emailService.send( emailResetPassword );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void deleteNotConfirmed( User user ) {
		Assert.notNull( user );
		this.repository.deleteNotConfirmed( user );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void cleanNotConfirmed() {
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DATE, -1 );
		
		this.repository.cleanNotConfirmed( calendar.getTime() );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public String confirm( RequestResetPassword requestResetPassword ) {
		Assert.notNull( requestResetPassword );
		Assert.state( StringUtils.hasText( requestResetPassword.getRequestCodeValue() ), "Invalid parameters. No 'requestCodeValue' was provided." );
		
		Email syncEmail = this.emailService.getBy( requestResetPassword.getUser().getEmail().getAddress(), true ); // email's database synchronized reference
		Assert.notNull( syncEmail, "No record found for the provided emailAddress." );
		
		User syncUser = this.userService.getBy( syncEmail ); // user's database synchronized reference
		requestResetPassword.setUser( syncUser );

		requestResetPassword.setConfirmationDate( new Date() );
		requestResetPassword.setConfirmationCodeValue( new BCryptPasswordEncoder().encode( syncEmail.getAddress() + SDF.format( requestResetPassword.getConfirmationDate() ) ) );
		
		Calendar validRange = Calendar.getInstance();
		validRange.add( Calendar.DATE, -1 );
		
		Assert.state( this.repository.confirm( requestResetPassword, validRange.getTime() ), "Invalid parameters. No 'requestResetPassword' record was found on database." );
		
		return requestResetPassword.getConfirmationCodeValue();
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public boolean changePassword( RequestResetPassword requestResetPassword ) {
		Assert.notNull( requestResetPassword );
		Assert.state( StringUtils.hasText( requestResetPassword.getConfirmationCodeValue() ) );
		Assert.state( StringUtils.hasText( requestResetPassword.getNewPassword() ) );
		
		Email syncEmail = this.emailService.getBy( requestResetPassword.getUser().getEmail().getAddress(), true ); // email's database synchronized reference
		Assert.notNull( syncEmail, "No record found for the provided emailAddress." );
		
		User syncUser = this.userService.getBy( syncEmail ); // user's database synchronized reference
		requestResetPassword.setUser( syncUser );
		requestResetPassword.setChangeDate( new Date() );

		Calendar validRange = Calendar.getInstance();
		validRange.add( Calendar.MINUTE, -10 );
		
		Assert.state( this.repository.changePassword( requestResetPassword, validRange.getTime() ), "Invalid request. It was not possible to perform the request." );
		
		this.loginService.setAsInvalid( syncUser );
		
		syncUser.setPassword( requestResetPassword.getNewPassword() );
		Assert.state( this.userService.changePassword( syncUser ), "Invalid request. It was not possible to perform the request." );
		
		return true;
	}

}