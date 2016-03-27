package com.clkio.ws.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.service.EmailService;
import com.clkio.ws.EmailPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.email.ConfirmEmailRequest;
import com.clkio.ws.domain.email.DeleteEmailRequest;
import com.clkio.ws.domain.email.InsertEmailRequest;
import com.clkio.ws.domain.email.ListEmailRequest;
import com.clkio.ws.domain.email.ListEmailResponse;
import com.clkio.ws.domain.email.SetEmailAsPrimaryRequest;

@WebService( endpointInterface = "com.clkio.ws.EmailPort" )
public class EmailWSImpl extends WebServiceCommon implements EmailPort {

	private static final Logger LOG = Logger.getLogger( EmailWSImpl.class );

	@Override
	public Response confirm( ConfirmEmailRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getEmail(), "[clkiows] No 'email' instance was found on the request." );
			
			Email email = new Email( request.getEmail().getEmailAddress() );
			email.setConfirmationCode( request.getEmail().getConfirmationCode() );
			
			this.getService( EmailService.class ).confirm( email );
			
			return new Response( "Email address confirmed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response insert( InsertEmailRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getEmail(), "[clkiows] No 'email' instance was found on the request." );
			
			Email email = new Email( request.getEmail().getEmailAddress() );
			email.setUser( this.getCurrentUser() );
			email.setRecordedTime( LocalDateTime.now() );
			email.setPrimary( false );
			NewEmailConfirmation emailConfirmation = new NewEmailConfirmation( email );
			email.setConfirmationCode( emailConfirmation.getHash() );
			
			EmailService service = this.getService( EmailService.class );
			service.insert( email );
			service.send( emailConfirmation );
			
			return new Response( "Email address stored successfully. An email is going to be sent you in order to confirm it." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public ListEmailResponse list( ListEmailRequest request ) throws ResponseException {
		try {
			List< Email > emails = this.getService( EmailService.class ).list( this.getCurrentUser() );
			ListEmailResponse response = new ListEmailResponse();
			
			if ( !CollectionUtils.isEmpty( emails ) )
				for ( Email email : emails )
					response.getEmails().add( new com.clkio.ws.domain.email.Email( new BigInteger( email.getId().toString() ), email.getAddress(), ( email.getConfirmationDate() != null ), email.isPrimary() ) );
			
			return response;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response delete( DeleteEmailRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getEmail(), "[clkiows] No 'email' instance was found on the request." );
			Assert.notNull( request.getEmail().getId(), "[clkiows] No value found for 'email's 'id' property found on the request." );
			
			Email email = new Email( request.getEmail().getId().intValue() );
			email.setUser( getCurrentUser() );
			
			this.getService( EmailService.class ).delete( email );
			
			return new Response( "Email address deleted successfully as requested." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response setEmailAsPrimary( SetEmailAsPrimaryRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getEmail(), "[clkiows] No 'email' instance was found on the request." );
			Assert.notNull( request.getEmail().getId(), "[clkiows] No value for 'email's 'id' property was found on the request." );
			
			Email email = new Email( request.getEmail().getId().intValue() );
			email.setUser( getCurrentUser() );
			
			this.getService( EmailService.class ).setAsPrimary( email );
			
			return new Response( "Operation set as primary processed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}
	
}