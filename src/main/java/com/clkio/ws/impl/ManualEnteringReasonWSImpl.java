package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.service.ManualEnteringReasonService;
import com.clkio.service.ProfileService;
import com.clkio.ws.ManualEnteringReasonPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.reason.DeleteManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.InsertManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonResponse;
import com.clkio.ws.domain.reason.Reason;
import com.clkio.ws.domain.reason.UpdateManualEnteringReasonRequest;

@WebService( endpointInterface = "com.clkio.ws.ManualEnteringReasonPort" )
public class ManualEnteringReasonWSImpl extends WebServiceCommon< ManualEnteringReasonService > implements ManualEnteringReasonPort {

	private static final Logger LOG = Logger.getLogger( ManualEnteringReasonWSImpl.class );
	
	public ManualEnteringReasonWSImpl() {
		super( ManualEnteringReasonService.class );
	}
	
	@Override
	public ListManualEnteringReasonResponse list( String clkioLoginCode, ListManualEnteringReasonRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			
			ListManualEnteringReasonResponse response = new ListManualEnteringReasonResponse();
			List< ManualEnteringReason > reasons = this.getService().list( profile );
			if ( !CollectionUtils.isEmpty( reasons ) )
				for ( ManualEnteringReason reason : reasons )
					response.getReasons().add( new Reason( new BigInteger( reason.getId().toString() ), reason.getReason() ) );
			
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
	public Response insert( String clkioLoginCode, InsertManualEnteringReasonRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getReason() == null )
				throw new ValidationException( "No 'reason' instance was found on the request." );
			if ( request.getReason().getProfile() == null || request.getReason().getProfile().getId() == null )
				throw new ValidationException( "Nested 'reason's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getReason().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No 'profile' record was found." );
			
			ManualEnteringReason reason = new ManualEnteringReason( profile, request.getReason().getReason() );
			this.getService().insert( reason );
			
			return new Response( "Reason record stored successfully." );
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
	public Response update( String clkioLoginCode, UpdateManualEnteringReasonRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getReason() == null || request.getReason().getId() == null )
				throw new ValidationException( "No 'reason' instance was found on the request or its 'id' property was not provided." );
			if ( request.getReason().getProfile() == null || request.getReason().getProfile().getId() == null )
				throw new ValidationException( "Nested 'reason's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getReason().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No 'profile' record was found." );
			
			ManualEnteringReason reason = new ManualEnteringReason( 
					request.getReason().getId().intValue(), profile, request.getReason().getReason() );
			this.getService().update( reason );
			
			return new Response( "Reason record updated successfully." );
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
	public Response delete( String clkioLoginCode, DeleteManualEnteringReasonRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getReason() == null || request.getReason().getId() == null )
				throw new ValidationException( "No 'reason' instance was found on the request or its 'id' property was not provided." );
			if ( request.getReason().getProfile() == null || request.getReason().getProfile().getId() == null )
				throw new ValidationException( "Nested 'reason's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getReason().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No 'profile' record was found." );
			
			ManualEnteringReason reason = new ManualEnteringReason( request.getReason().getId().intValue() );
			reason.setProfile( profile );
			this.getService().delete( reason );
			
			return new Response( "Reason record deleted successfully." );
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
