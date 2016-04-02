package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.service.LoginService;
import com.clkio.ws.LoginPort;
import com.clkio.ws.ResponseException;
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
			Assert.notNull( request );
			Assert.state( request.getUser() != null && request.getUser().getEmail() != null && request.getUser().getPassword() != null,
					"[clkiows] Instance 'user' alongside its 'email' and 'password' properties are mandatory." );
			
			com.clkio.domain.User user = new com.clkio.domain.User( new Email( request.getUser().getEmail() ), new Locale( request.getUser().getLocale() ) );
			user.setPassword( request.getUser().getPassword(), false );
			
			return new LoginResponse( this.getService().login( user, this.getRequesterIP() ) );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response doLogout( DoLogoutRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			
			this.getService().logout( this.getLoginCode() );
			
			return new Response( "Logout succeeded." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	
}