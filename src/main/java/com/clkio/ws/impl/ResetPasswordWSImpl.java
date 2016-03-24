package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.RequestResetPassword;
import com.clkio.domain.User;
import com.clkio.service.RequestResetPasswordService;
import com.clkio.ws.ResetPasswordPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordRequest;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordResponse;
import com.clkio.ws.domain.resetpassword.RequestResetPasswordRequest;
import com.clkio.ws.domain.resetpassword.ResetPasswordRequest;

@WebService( endpointInterface = "com.clkio.ws.ResetPasswordPort" )
public class ResetPasswordWSImpl extends WebServiceCommon implements ResetPasswordPort {

	private static final Logger LOG = Logger.getLogger( ResetPasswordWSImpl.class );
	
	@Override
	public Response requestResetPassword( RequestResetPasswordRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getUser() != null && StringUtils.hasText( request.getUser().getEmail() ),
					"[clkiows] No 'user' instance was found on the request or its 'email' property was not provided." );

			Locale locale = StringUtils.hasText( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			User user = new User( new Email( request.getUser().getEmail() ), locale );
			
			this.getService( RequestResetPasswordService.class ).processRequest( new RequestResetPassword( user ) );
			
			return new Response( "Request for reset password received successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public ConfirmResetPasswordResponse confirmResetPassword( ConfirmResetPasswordRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getUser() != null && StringUtils.hasText( request.getUser().getEmail() ),
					"[clkiows] No 'user' instance was found on the request or its 'email' property was not provided." );
			Assert.hasText( request.getRequestCode(), "[clkiows] A proper value for 'requestCode' is mandatory." );
			
			Locale locale = StringUtils.hasText( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			User user = new User( new Email( request.getUser().getEmail() ), locale );
			
			RequestResetPassword requestResetPassword = new RequestResetPassword( user );
			requestResetPassword.setRequestCodeValue( request.getRequestCode() );
			
			return new ConfirmResetPasswordResponse( 
					this.getService( RequestResetPasswordService.class ).confirm( requestResetPassword ),
					"Confirmation processed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response resetPassword( ResetPasswordRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getUser() != null && StringUtils.hasText( request.getUser().getEmail() ),
					"[clkiows] No 'user' instance was found on the request or its 'email' property was not provided." );
			Assert.hasText( request.getConfirmationCode(), "[clkiows] A proper value for 'confirmationCode' is mandatory." );
			Assert.hasText( request.getNewPassword(), "[clkiows] A proper value for 'newPassword' is mandatory." );
			
			Locale locale = StringUtils.hasText( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			RequestResetPassword requestResetPassword = new RequestResetPassword( new User( new Email( request.getUser().getEmail() ), locale ) );
			requestResetPassword.setConfirmationCodeValue( request.getConfirmationCode() );
			requestResetPassword.setNewPassword( request.getNewPassword() );
			
			this.getService( RequestResetPasswordService.class ).changePassword( requestResetPassword );
			
			return new Response( "New password has been set successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
