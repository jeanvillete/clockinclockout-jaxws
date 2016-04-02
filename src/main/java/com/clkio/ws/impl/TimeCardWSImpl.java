package com.clkio.ws.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Profile;
import com.clkio.service.ProfileService;
import com.clkio.service.TimeCardService;
import com.clkio.ws.ResponseException;
import com.clkio.ws.TimeCardPort;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.timecard.DeleteClockinClockoutRequest;
import com.clkio.ws.domain.timecard.DeleteManualEnteringRequest;
import com.clkio.ws.domain.timecard.GetTimeCardRequest;
import com.clkio.ws.domain.timecard.GetTimeCardResponse;
import com.clkio.ws.domain.timecard.GetTotalTimeMonthlyRequest;
import com.clkio.ws.domain.timecard.GetTotalTimeMonthlyResponse;
import com.clkio.ws.domain.timecard.GetTotalTimeRequest;
import com.clkio.ws.domain.timecard.GetTotalTimeResponse;
import com.clkio.ws.domain.timecard.InsertClockinClockoutRequest;
import com.clkio.ws.domain.timecard.InsertManualEnteringRequest;
import com.clkio.ws.domain.timecard.PunchClockRequest;
import com.clkio.ws.domain.timecard.SetExpectedHoursRequest;
import com.clkio.ws.domain.timecard.SetNotesRequest;
import com.clkio.ws.domain.timecard.UpdateClockinClockoutRequest;
import com.clkio.ws.domain.timecard.UpdateManualEnteringRequest;

@WebService( endpointInterface = "com.clkio.ws.TimeCardPort" )
public class TimeCardWSImpl extends WebServiceCommon implements TimeCardPort {

	private static final Logger LOG = Logger.getLogger( TimeCardWSImpl.class );
	
	@Override
	public GetTotalTimeResponse getTotalTime( GetTotalTimeRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public GetTimeCardResponse getTimeCard( GetTimeCardRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response punchClock( PunchClockRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );
			Assert.hasText( request.getTimestamp(), "Argument 'timestamp' is mandatory." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			this.getService( TimeCardService.class ).punchClock( profile, request.getTimestamp() );
			
			return new Response( "Service 'punchClock' executed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response insertClockinClockout( InsertClockinClockoutRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );
			Assert.notNull( request.getClockinclockout(),
					"[clkiows] No 'clockinclockout' instance was found on the request." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat() + profile.getHoursFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			
			LocalDateTime clockin = null;
			if ( StringUtils.hasText( request.getClockinclockout().getClockin() ) )
				try {
					clockin = LocalDateTime.parse( request.getClockinclockout().getClockin(), formatter );
				} catch ( DateTimeParseException e ) {
					throw new IllegalStateException( "The provided value for 'clockin' was not valid for the pattern=[" + pattern + "]. "
							+ "Remember that this pattern is the 'dateFormat' concatenated with 'hoursFormat' set on the profile, in case you want to change it." );
				}
			
			LocalDateTime clockout = null;
			if ( StringUtils.hasText( request.getClockinclockout().getClockout() ) )
				try {
					clockout = LocalDateTime.parse( request.getClockinclockout().getClockout(), formatter );
				} catch ( DateTimeParseException e ) {
					throw new IllegalStateException( "The provided value for 'clockout' was not valid for the pattern=[" + pattern + "]. "
							+ "Remember that this pattern is the 'dateFormat' concatenated with 'hoursFormat' set on the profile, in case you want to change it." );
				}
			
			this.getService( TimeCardService.class ).insert( profile, new ClockinClockout( clockin, clockout ) );
			
			return new Response( "Service 'insertClockinClockout' executed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response updateClockinClockout( UpdateClockinClockoutRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );
			Assert.state( request.getClockinclockout() != null && request.getClockinclockout().getId() != null,
					"[clkiows] No 'clockinclockout' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat() + profile.getHoursFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			
			LocalDateTime clockin = null;
			if ( StringUtils.hasText( request.getClockinclockout().getClockin() ) )
				try {
					clockin = LocalDateTime.parse( request.getClockinclockout().getClockin(), formatter );
				} catch ( DateTimeParseException e ) {
					throw new IllegalStateException( "The provided value for 'clockin' was not valid for the pattern=[" + pattern + "]. "
							+ "Remember that this pattern is the 'dateFormat' concatenated with 'hoursFormat' set on the profile, in case you want to change it." );
				}
			
			LocalDateTime clockout = null;
			if ( StringUtils.hasText( request.getClockinclockout().getClockout() ) )
				try {
					clockout = LocalDateTime.parse( request.getClockinclockout().getClockout(), formatter );
				} catch ( DateTimeParseException e ) {
					throw new IllegalStateException( "The provided value for 'clockout' was not valid for the pattern=[" + pattern + "]. "
							+ "Remember that this pattern is the 'dateFormat' concatenated with 'hoursFormat' set on the profile, in case you want to change it." );
				}
			
			ClockinClockout clkio = new ClockinClockout( request.getClockinclockout().getId().intValue(), null, clockin, clockout );
			this.getService( TimeCardService.class ).update( profile, clkio );
			
			return new Response( "Service 'updateClockinClockout' executed successfully." );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response deleteClockinClockout( DeleteClockinClockoutRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response insertManualEntering( InsertManualEnteringRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response updateManualEntering( UpdateManualEnteringRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response deleteManualEntering( DeleteManualEnteringRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response setNotes( SetNotesRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public Response setExpectedHours( SetExpectedHoursRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

	@Override
	public GetTotalTimeMonthlyResponse getTotalTimeMonthly( GetTotalTimeMonthlyRequest request ) throws ResponseException {
		try {
			Assert.notNull( request );
			Assert.state( request.getProfile() != null && request.getProfile().getId() != null,
					"[clkiows] No 'profile' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			return null;
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
