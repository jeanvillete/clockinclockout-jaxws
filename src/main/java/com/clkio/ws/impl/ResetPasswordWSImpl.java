package com.clkio.ws.impl;

import java.util.Locale;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.clkio.domain.Email;
import com.clkio.domain.RequestResetPassword;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.service.RequestResetPasswordService;
import com.clkio.ws.ResetPasswordPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordRequest;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordResponse;
import com.clkio.ws.domain.resetpassword.RequestResetPasswordRequest;
import com.clkio.ws.domain.resetpassword.ResetPasswordRequest;

@WebService( endpointInterface = "com.clkio.ws.ResetPasswordPort" )
public class ResetPasswordWSImpl extends WebServiceCommon< RequestResetPasswordService > implements ResetPasswordPort {

	private static final Logger LOG = Logger.getLogger( ResetPasswordWSImpl.class );
	
	public ResetPasswordWSImpl() {
		super( RequestResetPasswordService.class );
	}
	
	@Override
	public Response requestResetPassword( RequestResetPasswordRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getUser() == null || !StringUtils.hasText( request.getUser().getEmail() ) )
				throw new ValidationException( "No 'user' instance was found on the request or its 'email' property was not provided." );
			
			Locale locale = StringUtils.hasText( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			User user = new User( new Email( request.getUser().getEmail() ), locale );
			
			this.getService().processRequest( new RequestResetPassword( user ) );
			
			return new Response( "Request for reset password received successfully." );
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
	public ConfirmResetPasswordResponse confirmResetPassword( ConfirmResetPasswordRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getUser() == null || !StringUtils.hasText( request.getUser().getEmail() ) )
				throw new ValidationException( "No 'user' instance was found on the request or its 'email' property was not provided." );
			if ( !StringUtils.hasText( request.getRequestCode() ) )
				throw new ValidationException( "A proper value for 'requestCode' is mandatory." );
			
			Locale locale = StringUtils.hasText( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			User user = new User( new Email( request.getUser().getEmail() ), locale );
			
			RequestResetPassword requestResetPassword = new RequestResetPassword( user );
			requestResetPassword.setRequestCodeValue( request.getRequestCode() );
			
			return new ConfirmResetPasswordResponse( 
					this.getService().confirm( requestResetPassword ),
					"Confirmation processed successfully." );
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
	public Response resetPassword( ResetPasswordRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getUser() == null || !StringUtils.hasText( request.getUser().getEmail() ) )
				throw new ValidationException( "No 'user' instance was found on the request or its 'email' property was not provided." );
			if ( !StringUtils.hasText( request.getConfirmationCode() ) )
				throw new ValidationException( "A proper value for 'confirmationCode' is mandatory." );
			if ( !StringUtils.hasText( request.getNewPassword() ) )
				throw new ValidationException( "A proper value for 'newPassword' is mandatory." );
			
			Locale locale = StringUtils.hasText( request.getUser().getLocale() ) ? new Locale( request.getUser().getLocale() ) : Locale.getDefault();
			RequestResetPassword requestResetPassword = new RequestResetPassword( new User( new Email( request.getUser().getEmail() ), locale ) );
			requestResetPassword.setConfirmationCodeValue( request.getConfirmationCode() );
			requestResetPassword.setNewPassword( request.getNewPassword() );
			
			this.getService().changePassword( requestResetPassword );
			
			return new Response( "New password has been set successfully." );
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
