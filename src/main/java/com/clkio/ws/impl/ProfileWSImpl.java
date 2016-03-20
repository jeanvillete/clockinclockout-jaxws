package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Profile;
import com.clkio.service.ProfileService;
import com.clkio.ws.ProfilePort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.profile.InsertProfileRequest;
import com.clkio.ws.domain.profile.ListProfileRequest;
import com.clkio.ws.domain.profile.ListProfileResponse;
import com.clkio.ws.domain.profile.UpdateProfileRequest;

@WebService( endpointInterface = "com.clkio.ws.ProfilePort" )
public class ProfileWSImpl extends WebServiceCommon implements ProfilePort {

	private static final Logger LOG = Logger.getLogger( ProfileWSImpl.class );
	
	@Override
	public ListProfileResponse list( ListProfileRequest request ) throws ResponseException {
		try {
			ListProfileResponse response = new ListProfileResponse();
			List< Profile > profiles = this.getService( ProfileService.class ).listBy( this.getCurrentUser() );
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
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response insert( InsertProfileRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getProfile(), "[clkiows] No 'profile' instance was found on the request." );
			
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
			
			this.getService( ProfileService.class ).insert( profile );
			
			return new Response( "Profile record stored successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response update( UpdateProfileRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property is null." );
			
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
			
			this.getService( ProfileService.class ).update( profile );
			
			return new Response( "Profile record updated successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
