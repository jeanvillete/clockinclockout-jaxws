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
import com.clkio.ws.domain.adjusting.DeleteAdjustingRequest;
import com.clkio.ws.domain.adjusting.InsertAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingResponse;
import com.clkio.ws.domain.adjusting.UpdateAdjustingRequest;
import com.clkio.ws.domain.common.Response;

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
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );

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

	@Override
	public Response insert( InsertAdjustingRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.notNull( request.getAdjusting(), "[clkiows] No 'adjusting' instance was found on the request." );
			Assert.state( request.getAdjusting().getProfile() != null && request.getAdjusting().getProfile().getId() != null,
					"[clkiows] Nested 'adjusting's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getAdjusting().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.notNull( profile = this.getService( ProfileService.class ).get( profile ), "No 'profile' record was found." );
			
			Adjusting adjusting = new Adjusting( request.getAdjusting().getDescription(),
					DurationUtil.fromString( request.getAdjusting().getTimeInterval(), profile.getHoursFormat() ),
					profile );
			
			this.getService( AdjustingService.class ).insert( adjusting );
			
			return new Response( "Profile record stored successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response update( UpdateAdjustingRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getAdjusting() != null && request.getAdjusting().getId() != null,
					"[clkiows] No 'adjusting' instance was found on the request or its 'id' property was not provided." );
			Assert.state( request.getAdjusting().getProfile() != null && request.getAdjusting().getProfile().getId() != null,
					"[clkiows] Nested 'adjusting's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getAdjusting().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.notNull( profile = this.getService( ProfileService.class ).get( profile ), "No 'profile' record was found." );
			
			Adjusting adjusting = new Adjusting( request.getAdjusting().getId().intValue(),
					request.getAdjusting().getDescription(),
					DurationUtil.fromString( request.getAdjusting().getTimeInterval(), profile.getHoursFormat() ),
					profile );
			
			this.getService( AdjustingService.class ).update( adjusting );
			
			return new Response( "Adjusting record updated successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response delete( DeleteAdjustingRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getAdjusting() != null && request.getAdjusting().getId() != null,
					"[clkiows] No 'adjusting' instance was found on the request or its 'id' property was not provided." );
			Assert.state( request.getAdjusting().getProfile() != null && request.getAdjusting().getProfile().getId() != null,
					"[clkiows] Nested 'adjusting's property profile.id is mandatory." );
			
			Profile profile = new Profile( request.getAdjusting().getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.notNull( profile = this.getService( ProfileService.class ).get( profile ), "No 'profile' record was found." );
			
			Adjusting adjusting = new Adjusting( request.getAdjusting().getId().intValue() );
			adjusting.setProfile( profile );
			
			this.getService( AdjustingService.class ).delete( adjusting );
			
			return new Response( "Adjusting record deleted successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
