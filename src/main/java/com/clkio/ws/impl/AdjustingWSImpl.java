package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.service.AdjustingService;
import com.clkio.service.ProfileService;
import com.clkio.ws.AdjustingPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.adjusting.DeleteAdjustingRequest;
import com.clkio.ws.domain.adjusting.InsertAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingResponse;
import com.clkio.ws.domain.adjusting.UpdateAdjustingRequest;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.common.ResponseCreated;

@WebService( endpointInterface = "com.clkio.ws.AdjustingPort" )
public class AdjustingWSImpl extends WebServiceCommon< AdjustingService > implements AdjustingPort {

	private static final Logger LOG = Logger.getLogger( AdjustingWSImpl.class );
	
	public AdjustingWSImpl(){
		super( AdjustingService.class );
	}
	
	@Override
	public ListAdjustingResponse list( String clkioLoginCode, ListAdjustingRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			List< Adjusting > adjustings = this.getService().list( profile );
			ListAdjustingResponse response = new ListAdjustingResponse();
			if ( !CollectionUtils.isEmpty( adjustings ) )
				for ( Adjusting adjusting : adjustings )
					response.getAdjustings().add( new com.clkio.ws.domain.adjusting.Adjusting(
							new BigInteger( adjusting.getId().toString() ),
							adjusting.getDescription(),
							DurationUtil.fromDuration( adjusting.getTimeInterval(), profile.getHoursFormat() ) ) );
			
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
	public ResponseCreated insert( String clkioLoginCode, InsertAdjustingRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getAdjusting() == null )
				throw new ValidationException( "No 'adjusting' instance was found on the request." );
			if ( request.getAdjusting().getProfile() == null || request.getAdjusting().getProfile().getId() == null )
				throw new ValidationException( "Nested 'adjusting's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getAdjusting().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No 'profile' record was found." );
			
			Adjusting adjusting = new Adjusting( request.getAdjusting().getDescription(),
					DurationUtil.fromString( request.getAdjusting().getTimeInterval(), profile.getHoursFormat() ),
					profile );
			
			this.getService().insert( adjusting );
			request.getAdjusting().setId( new BigInteger( adjusting.getId().toString() ) );
			
			return new ResponseCreated( request.getAdjusting() );
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
	public Response update( String clkioLoginCode, UpdateAdjustingRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getAdjusting() == null || request.getAdjusting().getId() == null )
				throw new ValidationException( "No 'adjusting' instance was found on the request or its 'id' property was not provided." );
			if ( request.getAdjusting().getProfile() == null || request.getAdjusting().getProfile().getId() == null )
				throw new ValidationException( "Nested 'adjusting's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getAdjusting().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No 'profile' record was found." );
			
			Adjusting adjusting = new Adjusting( request.getAdjusting().getId().intValue(),
					request.getAdjusting().getDescription(),
					DurationUtil.fromString( request.getAdjusting().getTimeInterval(), profile.getHoursFormat() ),
					profile );
			
			this.getService().update( adjusting );
			
			return new Response( "Adjusting record updated successfully." );
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
	public Response delete( String clkioLoginCode, DeleteAdjustingRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getAdjusting() == null || request.getAdjusting().getId() == null )
				throw new ValidationException( "No 'adjusting' instance was found on the request or its 'id' property was not provided." );
			if ( request.getAdjusting().getProfile() == null || request.getAdjusting().getProfile().getId() == null )
				throw new ValidationException( "Nested 'adjusting's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getAdjusting().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No 'profile' record was found." );
			
			Adjusting adjusting = new Adjusting( request.getAdjusting().getId().intValue() );
			adjusting.setProfile( profile );
			
			this.getService().delete( adjusting );
			
			return new Response( "Adjusting record deleted successfully." );
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
