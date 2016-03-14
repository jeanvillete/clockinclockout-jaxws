package com.clkio.ws.impl;

import java.util.Date;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.service.EmailService;
import com.clkio.ws.EmailPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.email.ConfirmEmail;
import com.clkio.ws.domain.email.InsertEmail;

@WebService( endpointInterface = "com.clkio.ws.EmailPort" )
public class EmailWSImpl extends WebServiceCommon implements EmailPort {

	private static final Logger LOG = Logger.getLogger( EmailWSImpl.class );

	@Override
	public Response confirm( ConfirmEmail param ) throws ResponseException {
		try {
			Assert.notNull( param );
			Assert.notNull( param.getEmail() );
			
			Email email = new Email( param.getEmail().getEmailAddress() );
			email.setConfirmationCode( param.getEmail().getConfirmationCode() );
			
			this.getService( EmailService.class ).confirm( email );
			
			return new Response( "Email address confirmed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response insert( InsertEmail param ) throws ResponseException {
		try {
			Assert.notNull( param );
			Assert.notNull( param.getEmail() );
			
			Email email = new Email( param.getEmail().getEmailAddress() );
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
	
	
}