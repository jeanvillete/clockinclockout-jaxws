package com.clkio.ws.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.clkio.service.EmailService;
import com.clkio.ws.EmailPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.email.Email;

@WebService( endpointInterface = "com.clkio.ws.EmailPort" )
public class EmailWSImpl extends WebServiceCommon implements EmailPort {

	private static final Logger LOG = Logger.getLogger( EmailWSImpl.class );
	
	@Override
	public Response confirm( Email emailParam ) throws ResponseException {
		try {
			com.clkio.domain.Email email = new com.clkio.domain.Email( emailParam.getEmailAddress() );
			email.setConfirmationCode( emailParam.getConfirmationCode() );
			
			this.getService( EmailService.class ).confirm( email );
			
			return new Response( "Email address confirmed successfully. You can use it alongside your password to login." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}
}