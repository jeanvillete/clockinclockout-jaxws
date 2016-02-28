package org.com.clockinclockout.service.impl;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.EmailResetPassword;
import org.com.clockinclockout.domain.RequestResetPassword;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.repository.RequestResetPasswordRepository;
import org.com.clockinclockout.service.EmailService;
import org.com.clockinclockout.service.RequestResetPasswordService;
import org.com.clockinclockout.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class RequestResetPasswordServiceImpl implements RequestResetPasswordService, InitializingBean {

	@Autowired
	private RequestResetPasswordRepository repository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserService userService;
	
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

}
