package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.exception.ValidationException;
import com.clkio.service.LoginService;
import com.clkio.ws.LoginPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.login.DoLoginRequest;
import com.clkio.ws.domain.login.DoLogoutRequest;
import com.clkio.ws.domain.login.LoginResponse;

@WebService( endpointInterface = "com.clkio.ws.LoginPort" )
public class LoginWSImpl extends WebServiceCommon< LoginService > implements LoginPort {

	private static final Logger LOG = Logger.getLogger( LoginWSImpl.class );

	public LoginWSImpl() {
		super( LoginService.class );
	}
	
	@Override
	public LoginResponse doLogin( DoLoginRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getUser() == null || request.getUser().getEmail() == null || request.getUser().getPassword() == null )
				throw new ValidationException( "Instance 'user' alongside its 'email' and 'password' properties are mandatory." );
			
			Locale locale = !StringUtils.isEmpty( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			User user = new User( new Email( request.getUser().getEmail() ), locale );
			user.setPassword( request.getUser().getPassword(), false );
			
			return new LoginResponse( this.getService().login( user, this.getRequesterIP() ) );
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

	@Override
	public Response doLogout( String clkioLoginCode, DoLogoutRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			
			this.getService().logout( clkioLoginCode );
			
			return new Response( "Logout succeeded." );
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