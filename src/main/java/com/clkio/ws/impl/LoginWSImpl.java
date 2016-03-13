package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.clkio.domain.Email;
import com.clkio.service.LoginService;
import com.clkio.ws.LoginPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.login.LoginResponse;
import com.clkio.ws.domain.user.User;

@WebService( endpointInterface = "com.clkio.ws.LoginPort" )
public class LoginWSImpl extends WebServiceCommon implements LoginPort {

	private static final Logger LOG = Logger.getLogger( LoginWSImpl.class );
	
	@Override
	public LoginResponse doLogin( User userParam ) throws ResponseException {
		try {
			com.clkio.domain.User user = new com.clkio.domain.User( new Email( userParam.getEmail() ), new Locale( userParam.getLocale() ) );
			user.setPassword( userParam.getPassword(), false );
			
			return new LoginResponse( this.getService( LoginService.class ).login( user, this.getRequesterIP() ) );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
