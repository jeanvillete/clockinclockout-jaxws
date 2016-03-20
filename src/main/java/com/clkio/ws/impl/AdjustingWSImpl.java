package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.service.AdjustingService;
import com.clkio.service.ProfileService;
import com.clkio.ws.AdjustingPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.adjusting.ListAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingResponse;

@WebService( endpointInterface = "com.clkio.ws.AdjustingPort" )
public class AdjustingWSImpl extends WebServiceCommon implements AdjustingPort {

	private static final Logger LOG = Logger.getLogger( AdjustingWSImpl.class );
	
	@Override
	public ListAdjustingResponse list( ListAdjustingRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			profile = this.getService( ProfileService.class ).get( profile );

			List< Adjusting > adjustings = this.getService( AdjustingService.class ).list( profile );
			ListAdjustingResponse response = new ListAdjustingResponse();
			if ( !CollectionUtils.isEmpty( adjustings ) )
				for ( Adjusting adjusting : adjustings )
					response.getAdjustings().add( new com.clkio.ws.domain.adjusting.Adjusting(
							new BigInteger( adjusting.getId().toString() ),
							adjusting.getDescription(),
							DurationUtil.fromDuration( adjusting.getTimeInterval(), profile.getHoursFormat() ) ) );
			
			return response;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
