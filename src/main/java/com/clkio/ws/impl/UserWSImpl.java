package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.clkio.domain.Email;
import com.clkio.service.UserService;
import com.clkio.ws.ResponseException;
import com.clkio.ws.UserPort;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.user.User;

@WebService( endpointInterface = "com.clkio.ws.UserPort" )
public class UserWSImpl extends WebServiceCommon implements UserPort {

	private static final Logger LOG = Logger.getLogger( UserWSImpl.class );
	
	@Override
	public Response insert( User userParam ) throws ResponseException {
		try {
			com.clkio.domain.User newUser = new com.clkio.domain.User( new Email( userParam.getEmail() ), new Locale( userParam.getLocale() ) );
			newUser.setPassword( userParam.getPassword() );
			this.getService( UserService.class ).insert( newUser );
			
			return new Response( "User persisted successfully. An email is going to be sent to you in order to confirm it, please, check your inbox." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}
}
