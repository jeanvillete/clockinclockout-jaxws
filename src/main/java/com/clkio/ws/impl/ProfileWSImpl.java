package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.clkio.domain.Profile;
import com.clkio.service.ProfileService;
import com.clkio.ws.ProfilePort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.profile.ListProfileRequest;
import com.clkio.ws.domain.profile.ListProfileResponse;

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
					profileWs.setExpectedSunday( profile.getDefaultExpectedSunday().toString() );
					profileWs.setExpectedMonday( profile.getDefaultExpectedMonday().toString() );
					profileWs.setExpectedTuesday( profile.getDefaultExpectedTuesday().toString() );
					profileWs.setExpectedWednesday( profile.getDefaultExpectedWednesday().toString() );
					profileWs.setExpectedThursday( profile.getDefaultExpectedThursday().toString() );
					profileWs.setExpectedFriday( profile.getDefaultExpectedFriday().toString() );
					profileWs.setExpectedSaturday( profile.getDefaultExpectedSaturday().toString() );
					
					response.getProfiles().add( profileWs );
				}
			
			return response;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}