package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.service.UserService;
import com.clkio.ws.ResponseException;
import com.clkio.ws.UserPort;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.user.InsertUserRequest;

@WebService( endpointInterface = "com.clkio.ws.UserPort" )
public class UserWSImpl extends WebServiceCommon< UserService > implements UserPort {

	private static final Logger LOG = Logger.getLogger( UserWSImpl.class );

	public UserWSImpl() {
		super( UserService.class );
	}
	
	@Override
	public Response insert( InsertUserRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getUser() == null || request.getUser().getEmail() == null || request.getUser().getPassword() == null )
				throw new ValidationException( "Instance 'user' alongside its 'email' and 'password' properties are mandatory." );
			
			Locale locale = !StringUtils.isEmpty( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			User newUser = new User( new Email( request.getUser().getEmail() ), locale );
			newUser.setPassword( request.getUser().getPassword() );
			this.getService().insert( newUser );
			
			return new Response( "User persisted successfully. An email is going to be sent to you in order to confirm it, please, check your inbox." );
		} catch ( ClkioException e ) {
			LOG.debug( e );
			throw new ResponseException( e.getMessage(), e.getFault() );
		} catch ( ClkioRuntimeException e ) {
			LOG.debug( e );
			throw new ResponseException( e.getMessage(), e.getFault() );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new InternalServerError() );
		}
	}
	
}
