package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.service.ProfileService;
import com.clkio.ws.ProfilePort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.profile.DeleteProfileRequest;
import com.clkio.ws.domain.profile.InsertProfileRequest;
import com.clkio.ws.domain.profile.ListProfileRequest;
import com.clkio.ws.domain.profile.ListProfileResponse;
import com.clkio.ws.domain.profile.UpdateProfileRequest;

@WebService( endpointInterface = "com.clkio.ws.ProfilePort" )
public class ProfileWSImpl extends WebServiceCommon< ProfileService > implements ProfilePort {

	private static final Logger LOG = Logger.getLogger( ProfileWSImpl.class );
	
	public ProfileWSImpl() {
		super( ProfileService.class );
	}
	
	@Override
	public ListProfileResponse list( ListProfileRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			
			ListProfileResponse response = new ListProfileResponse();
			List< Profile > profiles = this.getService().listBy( this.getCurrentUser() );
			if ( !CollectionUtils.isEmpty( profiles ) )
				for ( Profile profile : profiles ) {
					com.clkio.ws.domain.profile.Profile profileWs = 
							new com.clkio.ws.domain.profile.Profile( new BigInteger( profile.getId().toString() ), profile.getDescription() );
					profileWs.setHoursFormat( profile.getHoursFormat() );
					profileWs.setDateFormat( profile.getDateFormat() );
					profileWs.setExpectedSunday( DurationUtil.fromDuration( profile.getDefaultExpectedSunday(), profile.getHoursFormat() ) );
					profileWs.setExpectedMonday( DurationUtil.fromDuration( profile.getDefaultExpectedMonday(), profile.getHoursFormat() ) );
					profileWs.setExpectedTuesday( DurationUtil.fromDuration( profile.getDefaultExpectedTuesday(), profile.getHoursFormat() ) );
					profileWs.setExpectedWednesday( DurationUtil.fromDuration( profile.getDefaultExpectedWednesday(), profile.getHoursFormat() ) );
					profileWs.setExpectedThursday( DurationUtil.fromDuration( profile.getDefaultExpectedThursday(), profile.getHoursFormat() ) );
					profileWs.setExpectedFriday( DurationUtil.fromDuration( profile.getDefaultExpectedFriday(), profile.getHoursFormat() ) );
					profileWs.setExpectedSaturday( DurationUtil.fromDuration( profile.getDefaultExpectedSaturday(), profile.getHoursFormat() ) );
					
					response.getProfiles().add( profileWs );
				}
			
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
	public Response insert( InsertProfileRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null )
				throw new ValidationException( "No 'profile' instance was found on the request." );
			
			Profile profile = new Profile( this.getCurrentUser(),
					request.getProfile().getDescription(),
					request.getProfile().getHoursFormat(),
					request.getProfile().getDateFormat() );
			profile.setDefaultExpectedSunday( DurationUtil.fromString( request.getProfile().getExpectedSunday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedMonday( DurationUtil.fromString( request.getProfile().getExpectedMonday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedTuesday( DurationUtil.fromString( request.getProfile().getExpectedTuesday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedWednesday( DurationUtil.fromString( request.getProfile().getExpectedWednesday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedThursday( DurationUtil.fromString( request.getProfile().getExpectedThursday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedFriday( DurationUtil.fromString( request.getProfile().getExpectedFriday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedSaturday( DurationUtil.fromString( request.getProfile().getExpectedSaturday(), request.getProfile().getHoursFormat() ) );
			
			this.getService().insert( profile );
			
			return new Response( "Profile record stored successfully." );
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
	public Response update( UpdateProfileRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property is null." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue(),
					this.getCurrentUser(),
					request.getProfile().getDescription(),
					request.getProfile().getHoursFormat(),
					request.getProfile().getDateFormat() );
			profile.setDefaultExpectedSunday( DurationUtil.fromString( request.getProfile().getExpectedSunday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedMonday( DurationUtil.fromString( request.getProfile().getExpectedMonday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedTuesday( DurationUtil.fromString( request.getProfile().getExpectedTuesday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedWednesday( DurationUtil.fromString( request.getProfile().getExpectedWednesday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedThursday( DurationUtil.fromString( request.getProfile().getExpectedThursday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedFriday( DurationUtil.fromString( request.getProfile().getExpectedFriday(), request.getProfile().getHoursFormat() ) );
			profile.setDefaultExpectedSaturday( DurationUtil.fromString( request.getProfile().getExpectedSaturday(), request.getProfile().getHoursFormat() ) );
			
			this.getService().update( profile );
			
			return new Response( "Profile record updated successfully." );
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
	public Response delete( DeleteProfileRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property is null." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( getCurrentUser() );
			
			this.getService().delete( profile );
			
			return new Response( "Profile record deleted successfully." );
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
