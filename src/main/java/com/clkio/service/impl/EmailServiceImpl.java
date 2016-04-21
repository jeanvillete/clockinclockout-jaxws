package com.clkio.service.impl;

import java.time.LocalDateTime;
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
import com.clkio.domain.EmailContent;
import com.clkio.domain.EmailResetPassword;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.domain.NewUserEmailConfirmation;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;
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
		Assert.notNull( this.repository, "The field 'repository' shouldn't be null." );
		Assert.notNull( this.javaMailSender, "The field 'javaMailSender' shouldn't be null." );
		Assert.notNull( this.velocityEngine, "The field 'velocityEngine' shouldn't be null." );
		Assert.notNull( this.messageSource, "The field 'messageSource' shouldn't be null." );
		Assert.hasText( this.from, "No value was provided for the property 'from'." );
		Assert.hasText( this.linkSite, "No value was provided for the property 'linkSite'." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final Email email ) throws ValidationException, PersistenceException, ConflictException {
		if ( email == null )
			throw new ValidationException( "Argument email cannot be null." );
		if ( !email.getAddress().matches( "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$" ) )
			throw new ValidationException( "The provided email address is not valid." );
		if ( email.getRecordedTime() == null )
			throw new ValidationException( "No recordedTime was provided to argument email." );
		if ( !StringUtils.hasText( email.getConfirmationCode() ) )
			throw new ValidationException( "No confirmationCode was provided to argument email." );
		if ( this.exists( email ) )
			throw new ConflictException( "The provided email address is already in use." );
		
		if ( !this.repository.insert( email ) )
			throw new PersistenceException( "It was not possible performing insert for the 'email' record." );
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
	public boolean exists( final Email email ) throws ValidationException {
		if ( email == null )
			throw new ValidationException( "Argument 'email' is mandatory." );
		return this.repository.exists( email );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void confirm( final Email email ) throws ValidationException, PersistenceException {
		if ( email == null )
			throw new ValidationException( "Argument 'email' is mandatory." );
		if ( !StringUtils.hasText( email.getConfirmationCode() ) )
			throw new ValidationException( "Nested property 'confirmationCode' is mandatory." );
		
		if (  !this.repository.confirm( email, LocalDateTime.now().minusDays( 1 ) ) )
			throw new PersistenceException( "Problems while performing email confirmation." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Email email ) throws ValidationException, PersistenceException, ConflictException {
		if ( email == null )
			throw new ValidationException( "Argument 'email' is mandatory." );
		if ( email.getUser() == null || email.getUser().getId() == null )
			throw new ValidationException( "Nested property 'user' and its more nested 'id' properties are mandatory." );
		
		Email syncEmail = this.get( email );
		if ( syncEmail == null)
			throw new PersistenceException( "Email record not found for deletion." );
		
		if ( syncEmail.isPrimary() && syncEmail.getConfirmationDate() != null )
			throw new ConflictException( "A 'primary email' record cannot be deleted." );
		
		if( !this.repository.delete( email ) )
			throw new PersistenceException( "It was not possible delete 'email' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Email getBy( final String emailAddress, final boolean isPrimary ) throws ValidationException, PersistenceException {
		if ( !StringUtils.hasText( emailAddress ) )
			throw new ValidationException( "Argument 'emailAddress' is mandatory." );
		if ( !emailAddress.matches( "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$" ) )
			throw new ValidationException( "The provided email address is not valid." );
		try {
			return this.repository.getBy( emailAddress, isPrimary );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< Email > listPrimaryNotConfirmed( final LocalDateTime date ) throws ValidationException {
		if ( date == null )
			throw new ValidationException( "Parameter 'date' cannot be null." );
		return this.repository.listPrimaryNotConfirmed( date );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void deleteNotPrimaryNotConfirmed( final LocalDateTime date ) throws ValidationException {
		if ( date == null )
			throw new ValidationException( "Parameter 'date' cannot be null." );
		this.repository.deleteNotPrimaryNotConfirmed( date );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< Email > list( final User user ) throws ValidationException {
		if ( user == null )
			throw new ValidationException( "Parameter 'user' cannot be null." );
		return this.repository.list( user );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Email get( final Email email ) throws ValidationException, PersistenceException {
		if ( email == null )
			throw new ValidationException( "Parameter 'email' cannot be null." );
		if ( email.getUser() == null || email.getUser().getId() == null )
			throw new ValidationException( "Nested property 'user' along its more nested 'id' property are mandatory." );
		try {
			return this.repository.get( email );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void setAsPrimary( final Email email ) throws ValidationException, PersistenceException, ConflictException {
		if ( email == null )
			throw new ValidationException( "Parameter 'email' cannot be null." );
		
		Email syncEmail = this.get( email );
		if ( syncEmail == null )
			throw new PersistenceException( "No 'email' record was retrieved." );
		if ( syncEmail.getConfirmationDate() == null )
			throw new ConflictException( "The provided 'email' has not been confirmed yet!" );
		if ( syncEmail.isPrimary() )
			throw new ConflictException( "The provided email is already the 'PRIMARY' one." );
			
		if ( !( this.repository.unsetPrimary( email.getUser() ) && this.repository.setAsPrimary( email ) ) )
			throw new PersistenceException( "Operation 'setAsPrimary' not performed." );
	}

}
