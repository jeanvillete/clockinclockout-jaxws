package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.service.LoginService;
import com.clkio.ws.LoginPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.login.DoLoginRequest;
import com.clkio.ws.domain.login.LoginResponse;

@WebService( endpointInterface = "com.clkio.ws.LoginPort" )
public class LoginWSImpl extends WebServiceCommon implements LoginPort {

	private static final Logger LOG = Logger.getLogger( LoginWSImpl.class );

	@Override
	public LoginResponse doLogin( DoLoginRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getUser() != null && request.getUser().getEmail() != null && request.getUser().getPassword() != null,
					"[clkiows] Instance 'user' alongside its 'email' and 'password' properties are mandatory." );
			
			com.clkio.domain.User user = new com.clkio.domain.User( new Email( request.getUser().getEmail() ), new Locale( request.getUser().getLocale() ) );
			user.setPassword( request.getUser().getPassword(), false );
			
			return new LoginResponse( this.getService( LoginService.class ).login( user, this.getRequesterIP() ) );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	
}