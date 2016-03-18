package com.clkio.ws.impl;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.service.ManualEnteringReasonService;
import com.clkio.ws.ManualEnteringReasonPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.reason.ListManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonResponse;
import com.clkio.ws.domain.reason.Reason;

@WebService( endpointInterface = "com.clkio.ws.ManualEnteringReasonPort" )
public class ManualEnteringReasonWSImpl extends WebServiceCommon implements ManualEnteringReasonPort {

	private static final Logger LOG = Logger.getLogger( ManualEnteringReasonWSImpl.class );
	
	@Override
	public ListManualEnteringReasonResponse list( ListManualEnteringReasonRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			
			ListManualEnteringReasonResponse response = new ListManualEnteringReasonResponse();
			List< ManualEnteringReason > reasons = this.getService( ManualEnteringReasonService.class ).list( profile );
			if ( !CollectionUtils.isEmpty( reasons ) )
				for ( ManualEnteringReason reason : reasons )
					response.getReasons().add( new Reason( new BigInteger( reason.getId().toString() ), reason.getReason() ) );
			
			return response;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
