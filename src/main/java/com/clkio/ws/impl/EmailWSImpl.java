package com.clkio.ws.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.clkio.domain.Email;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.service.EmailService;
import com.clkio.ws.EmailPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.common.ResponseCreated;
import com.clkio.ws.domain.email.ConfirmEmailRequest;
import com.clkio.ws.domain.email.DeleteEmailRequest;
import com.clkio.ws.domain.email.InsertEmailRequest;
import com.clkio.ws.domain.email.ListEmailRequest;
import com.clkio.ws.domain.email.ListEmailResponse;
import com.clkio.ws.domain.email.SetEmailAsPrimaryRequest;

@WebService( endpointInterface = "com.clkio.ws.EmailPort" )
public class EmailWSImpl extends WebServiceCommon< EmailService > implements EmailPort {

	private static final Logger LOG = Logger.getLogger( EmailWSImpl.class );

	public EmailWSImpl() {
		super( EmailService.class );
	}
	
	@Override
	public Response confirm( ConfirmEmailRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getEmail() == null )
				throw new ValidationException( "No 'email' instance was found on the request." );
			
			Email email = new Email( request.getEmail().getEmailAddress() );
			email.setConfirmationCode( request.getEmail().getConfirmationCode() );
			
			this.getService().confirm( email );
			
			return new Response( "Email address confirmed successfully." );
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
	public ResponseCreated insert( String clkioLoginCode, InsertEmailRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			
			if ( request.getEmail() == null )
				throw new ValidationException( "No 'email' instance was found on the request." );
			
			Email email = new Email( request.getEmail().getEmailAddress() );
			email.setUser( this.getCurrentUser( clkioLoginCode ) );
			email.setRecordedTime( LocalDateTime.now() );
			email.setPrimary( false );
			NewEmailConfirmation emailConfirmation = new NewEmailConfirmation( email );
			email.setConfirmationCode( emailConfirmation.getHash() );
			
			EmailService service = this.getService();
			service.insert( email );
			service.send( emailConfirmation );
			request.getEmail().setId( new BigInteger( email.getId().toString() ) );
			
			return new ResponseCreated( request.getEmail() );
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
	public ListEmailResponse list( String clkioLoginCode, ListEmailRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			
			List< Email > emails = this.getService().list( this.getCurrentUser( clkioLoginCode ) );
			ListEmailResponse response = new ListEmailResponse();
			
			if ( !CollectionUtils.isEmpty( emails ) )
				for ( Email email : emails )
					response.getEmails().add( new com.clkio.ws.domain.email.Email( new BigInteger( email.getId().toString() ), email.getAddress(), ( email.getConfirmationDate() != null ), email.isPrimary() ) );
			
			return response;
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
	public Response delete( String clkioLoginCode, DeleteEmailRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getEmail() == null )
				throw new ValidationException( "No 'email' instance was found on the request." );
			if ( request.getEmail().getId() == null )
				throw new ValidationException( "No value found for 'email's 'id' property found on the request." );
			
			Email email = new Email( request.getEmail().getId().intValue() );
			email.setUser( getCurrentUser( clkioLoginCode ) );
			
			this.getService().delete( email );
			
			return new Response( "Email address deleted successfully as requested." );
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
	public Response setEmailAsPrimary( String clkioLoginCode, SetEmailAsPrimaryRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			
			if ( request.getEmail() == null )
				throw new ValidationException( "No 'email' instance was found on the request." );
			if ( request.getEmail().getId() == null )
				throw new ValidationException( "No value found for 'email's 'id' property found on the request." );
			
			Email email = new Email( request.getEmail().getId().intValue() );
			email.setUser( getCurrentUser( clkioLoginCode ) );
			
			this.getService().setAsPrimary( email );
			
			return new Response( "Operation set as primary processed successfully." );
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