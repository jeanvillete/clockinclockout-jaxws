package com.clkio.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.NewUserEmailConfirmation;
import com.clkio.domain.EmailContent;
import com.clkio.domain.EmailResetPassword;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.repository.EmailRepository;
import com.clkio.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService, InitializingBean {

	private static final Logger SENDING_EMAIL_LOGGER = Logger.getLogger( "sendingEmailLogger" );
	
	@Autowired
	private EmailRepository repository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value( "${clockinclockout.mail.username}" )
	private String from;
	
	@Value( "${clockinclockout.link}" )
	private String linkSite;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	@Qualifier( "messageSource" )
	private MessageSource messageSource;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The field repository shouldn't be null." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final Email email ) {
		Assert.notNull( email, "Argument email cannot be null." );
		Assert.state( email.getAddress().matches( "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$" ), "The provided email address is not valid." );
		Assert.state( email.getRecordedTime() != null, "No recordedTime was provided to argument email." );
		Assert.state( StringUtils.hasText( email.getConfirmationCode() ), "No confirmationCode was provided to argument email." );
		Assert.state( !this.exists( email ), "The provided email address is already in use." );
		
		this.repository.insert( email );
	}

	@Override
	@Async( "asyncExecutor" )
	@Transactional( propagation = Propagation.NOT_SUPPORTED )
	public void send( NewUserEmailConfirmation emailConfirmation ) {
		Assert.notNull( emailConfirmation );
		emailConfirmation.setSubject( this.messageSource.getMessage( "email.confirmation.title", null, emailConfirmation.getEmail().getUser().getLocale() ) );
		this.send( emailConfirmation, emailConfirmation.getVelocityResource() );
	}

	@Override
	@Async( "asyncExecutor" )
	@Transactional( propagation = Propagation.NOT_SUPPORTED )
	public void send( NewEmailConfirmation newEmailConfirmation ) {
		Assert.notNull( newEmailConfirmation );
		newEmailConfirmation.setSubject( this.messageSource.getMessage( "email.confirmation.title", null, newEmailConfirmation.getEmail().getUser().getLocale() ) );
		this.send( newEmailConfirmation, newEmailConfirmation.getVelocityResource() );
	}
	
	@Override
	@Async( "asyncExecutor" )
	@Transactional( propagation = Propagation.NOT_SUPPORTED )
	public void send( EmailResetPassword emailResetPassword ) {
		Assert.notNull( emailResetPassword );
		emailResetPassword.setSubject( this.messageSource.getMessage( "email.resetpassword.title", null, emailResetPassword.getEmail().getUser().getLocale() ) );
		this.send( emailResetPassword, emailResetPassword.getVelocityResource() );
	}
	
	private void send( final EmailContent emailContent, final String velocityResource ) {
		try {
			this.javaMailSender.send( new MimeMessagePreparator() {
				@Override
				public void prepare( MimeMessage mimeMessage ) throws Exception {
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( mimeMessage );
					mimeMessageHelper.setTo( emailContent.getEmail().getAddress() );
					mimeMessageHelper.setFrom( from );
					mimeMessageHelper.setSubject( emailContent.getSubject() );
					mimeMessageHelper.setSentDate( new Date() );

					Map< String, Object > model = new HashMap< String, Object >();
					model.put( "emailContent", emailContent );
					model.put( "linkSite", linkSite );
					
					String text = VelocityEngineUtils.mergeTemplateIntoString(
			                velocityEngine,
			                "velocity/" + velocityResource,
			                "UTF-8",
			                model );
					mimeMessageHelper.setText( text, true );
				}
			} );
			
			SENDING_EMAIL_LOGGER.debug( "[EmailServiceImpl.send] Sending email: " + emailContent.getEmail() );
		} catch ( Exception e ) {
			SENDING_EMAIL_LOGGER.error( e );
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( Email email ) {
		Assert.notNull( email );
		return this.repository.exists( email );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void confirm( Email email ) {
		Assert.notNull( email );
		Assert.hasText( email.getConfirmationCode() );
		
		Calendar validRange = Calendar.getInstance();
		validRange.add( Calendar.DATE, -1 );
		
		Assert.state(  this.repository.confirm( email, validRange.getTime() ), "Problems while performing email confirmation." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( Email email ) {
		Assert.notNull( email );
		this.repository.delete( email );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Email getBy( String emailAddress, boolean isPrimary ) {
		Assert.hasText( emailAddress );
		Assert.state( emailAddress.matches( "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$" ), "The provided email address is not valid." );
		Email email = null;
		try {
			email = this.repository.getBy( emailAddress, isPrimary );
		} catch ( EmptyResultDataAccessException e ) { }
		return email;
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< Email > listPrimaryNotConfirmed( Date date ) {
		Assert.notNull( date, "Parameter 'date' cannot be null." );
		return this.repository.listPrimaryNotConfirmed( date );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void deleteNotPrimaryNotConfirmed( Date date ) {
		Assert.notNull( date, "Parameter 'date' cannot be null." );
		this.repository.deleteNotPrimaryNotConfirmed( date );
	}

}
