package org.com.clockinclockout.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.EmailConfirmation;
import org.com.clockinclockout.domain.EmailContent;
import org.com.clockinclockout.domain.EmailResetPassword;
import org.com.clockinclockout.repository.EmailRepository;
import org.com.clockinclockout.service.EmailService;
import org.com.clockinclockout.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
	
	private UserService userService;

	@Autowired
	void setUserService( UserService userService ) {
		this.userService = userService;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The field repository shouldn't be null." );
		Assert.notNull( this.userService, "The field userService shouldn't be null." );
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
	@Async
	@Transactional( propagation = Propagation.NOT_SUPPORTED )
	public void send( EmailConfirmation emailConfirmation ) {
		Assert.notNull( emailConfirmation );
		String velocityResource = "velocity-email-confirmation_" + emailConfirmation.getEmail().getUser().getLocale().getLanguage().toLowerCase() + ".vm";
		emailConfirmation.setSubject( this.messageSource.getMessage( "email.confirmation.title", null, emailConfirmation.getEmail().getUser().getLocale() ) );
		this.send( emailConfirmation, velocityResource );
	}

	@Override
	@Async
	@Transactional( propagation = Propagation.NOT_SUPPORTED )
	public void send( EmailResetPassword emailResetPassword ) {
		Assert.notNull( emailResetPassword );
		String velocityResource = "velocity-reset-password_" + emailResetPassword.getEmail().getUser().getLocale().getLanguage().toLowerCase() + ".vm";
		emailResetPassword.setSubject( this.messageSource.getMessage( "email.resetpassword.title", null, emailResetPassword.getEmail().getUser().getLocale() ) );
		this.send( emailResetPassword, velocityResource );
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
		Assert.state(  this.repository.confirm( email ), "Problems while performing email confirmation." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void cleanNotConfirmed() {
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DATE, 1 );
		
		List< Email > emails = this.repository.listPrimaryNotConfirmed( calendar.getTime() );
		if ( !CollectionUtils.isEmpty( emails ) ) {
			for ( Email email : emails ) {
				this.delete( email );
				this.userService.delete( email.getUser() );
			}
		}
		
		// TODO this.repository.deleteNotPrimaryNotConfirmed()
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( Email email ) {
		Assert.notNull( email );
		this.repository.delete( email );
	}

}
