package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.Date;
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
import com.clkio.ws.domain.email.InsertEmailRequest;
import com.clkio.ws.domain.email.ListEmailRequest;
import com.clkio.ws.domain.email.ListEmailResponse;

@WebService( endpointInterface = "com.clkio.ws.EmailPort" )
public class EmailWSImpl extends WebServiceCommon implements EmailPort {

	private static final Logger LOG = Logger.getLogger( EmailWSImpl.class );

	@Override
	public Response confirm( ConfirmEmailRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getEmail() );
			
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
			Assert.notNull( request.getEmail() );
			
			Email email = new Email( request.getEmail().getEmailAddress() );
			email.setUser( this.getCurrentUser() );
			email.setRecordedTime( new Date() );
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
	
}