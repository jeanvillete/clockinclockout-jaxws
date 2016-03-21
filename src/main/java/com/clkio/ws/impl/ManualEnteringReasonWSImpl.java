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
import com.clkio.service.ProfileService;
import com.clkio.ws.ManualEnteringReasonPort;
import com.clkio.ws.ResponseException;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.reason.DeleteManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.InsertManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonResponse;
import com.clkio.ws.domain.reason.Reason;
import com.clkio.ws.domain.reason.UpdateManualEnteringReasonRequest;

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

	@Override
	public Response insert( InsertManualEnteringReasonRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getReason(), "[clkiows] No 'reason' instance was found on the request." );
			Assert.state( request.getReason().getProfile() != null && request.getReason().getProfile().getId() != null,
					"[clkiows] Nested 'reason's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getReason().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.notNull( profile = this.getService( ProfileService.class ).get( profile ), "No 'profile' record was found." );
			
			ManualEnteringReason reason = new ManualEnteringReason( profile, request.getReason().getReason() );
			this.getService( ManualEnteringReasonService.class ).insert( reason );
			
			return new Response( "Reason record stored successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response update( UpdateManualEnteringReasonRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getReason() != null && request.getReason().getId() != null,
					"[clkiows] No 'reason' instance was found on the request or its 'id' property was not provided." );
			Assert.state( request.getReason().getProfile() != null && request.getReason().getProfile().getId() != null,
					"[clkiows] Nested 'reason's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getReason().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.notNull( profile = this.getService( ProfileService.class ).get( profile ), "No 'profile' record was found." );
			
			ManualEnteringReason reason = new ManualEnteringReason( 
					request.getReason().getId().intValue(), profile, request.getReason().getReason() );
			this.getService( ManualEnteringReasonService.class ).update( reason );
			
			return new Response( "Reason record updated successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response delete( DeleteManualEnteringReasonRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getReason() != null && request.getReason().getId() != null,
					"[clkiows] No 'reason' instance was found on the request or its 'id' property was not provided." );
			Assert.state( request.getReason().getProfile() != null && request.getReason().getProfile().getId() != null,
					"[clkiows] Nested 'reason's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getReason().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.notNull( profile = this.getService( ProfileService.class ).get( profile ), "No 'profile' record was found." );
			
			ManualEnteringReason reason = new ManualEnteringReason( request.getReason().getId().intValue() );
			reason.setProfile( profile );
			this.getService( ManualEnteringReasonService.class ).delete( reason );
			
			return new Response( "Reason record deleted successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
