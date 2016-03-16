package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.service.UserService;
import com.clkio.ws.ResponseException;
import com.clkio.ws.UserPort;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.user.InsertUserRequest;

@WebService( endpointInterface = "com.clkio.ws.UserPort" )
public class UserWSImpl extends WebServiceCommon implements UserPort {

	private static final Logger LOG = Logger.getLogger( UserWSImpl.class );

	@Override
	public Response insert( InsertUserRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getUser() != null && request.getUser().getEmail() != null && request.getUser().getPassword() != null,
					"[clkiows] Instance 'user' alongside its 'email' and 'password' properties are mandatory." );
			
			User newUser = new User( new Email( request.getUser().getEmail() ), new Locale( request.getUser().getLocale() ) );
			newUser.setPassword( request.getUser().getPassword() );
			this.getService( UserService.class ).insert( newUser );
			
			return new Response( "User persisted successfully. An email is going to be sent to you in order to confirm it, please, check your inbox." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}
	
}
