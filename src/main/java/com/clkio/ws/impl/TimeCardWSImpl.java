package com.clkio.ws.impl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.clkio.common.DurationUtil;
import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.DayEntering;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.domain.TimeCard;
import com.clkio.service.ProfileService;
import com.clkio.service.TimeCardService;
import com.clkio.ws.ResponseException;
import com.clkio.ws.TimeCardPort;
import com.clkio.ws.domain.clockinclockout.Clockinclockout;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.reason.Reason;
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
public class TimeCardWSImpl extends WebServiceCommon< TimeCardService > implements TimeCardPort {

	private static final Logger LOG = Logger.getLogger( TimeCardWSImpl.class );
	
	public TimeCardWSImpl() {
		super( TimeCardService.class );
	}
	
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
			
			return new GetTotalTimeResponse( DurationUtil.fromDuration( this.getService().getTotalTime( profile ), profile.getHoursFormat() ) );
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
			Assert.hasText( request.getMonth(), "Argument 'month' is mandatory." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String pattern = "yyyy-MM";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			YearMonth month = null;
			try {
				month = YearMonth.parse( request.getMonth(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'month' was not valid for the pattern=[" + pattern + "]. " );
			}
			
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( profile.getDateFormat() );
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern( profile.getHoursFormat() );
			
			TimeCard tc = this.getService().getTimeCard( profile, month );
			GetTimeCardResponse response = new GetTimeCardResponse( new com.clkio.ws.domain.timecard.TimeCard() );
			for ( Day day : tc.getDaysSorted() ) {
				com.clkio.ws.domain.day.Day _day = new com.clkio.ws.domain.day.Day( 
						day.getDate().format( dateTimeFormatter ), 
						( day.getExpectedHours() != null ? DurationUtil.fromDuration( day.getExpectedHours(), profile.getHoursFormat() ) : null ), 
						DurationUtil.fromDuration( day.getBalance(), profile.getHoursFormat() ), 
						day.getNotes() );
				if ( !CollectionUtils.isEmpty( day.getTableEntering() ) )
					for ( DayEntering entering : day.getTableEntering() )
						if ( entering instanceof ClockinClockout ) {
							ClockinClockout clkio = ( ClockinClockout ) entering;
							_day.getTableEntering().add( new Clockinclockout( new BigInteger( clkio.getId().toString() ), 
									( clkio.getClockin() != null ? clkio.getClockin().format( timeFormatter ) : null ), 
									( clkio.getClockout() != null ? clkio.getClockout().format( timeFormatter ) : null ) ) );
						} else if ( entering instanceof ManualEntering ) {
							ManualEntering me = ( ManualEntering ) entering;
							_day.getTableEntering().add( new com.clkio.ws.domain.manualentering.ManualEntering( 
									new BigInteger( me.getId().toString() ), 
									DurationUtil.fromDuration( me.getTimeInterval(), profile.getHoursFormat() ), 
									new Reason( 
											new BigInteger( me.getReason().getId().toString() ), 
											me.getReason().getReason() ) ) );
						} else throw new IllegalStateException( "Invalid value for 'entering'" );
				
				response.getTimeCard().getDays().add( _day );
			}
			
			return response;
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
			
			this.getService().punchClock( profile, request.getTimestamp() );
			
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
			
			this.getService().insert( profile, new ClockinClockout( clockin, clockout ) );
			
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
			this.getService().update( profile, clkio );
			
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
			Assert.state( request.getClockinclockout() != null && request.getClockinclockout().getId() != null,
					"[clkiows] No 'clockinclockout' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			this.getService().delete( profile, new ClockinClockout( request.getClockinclockout().getId().intValue() ) );
			
			return new Response( "Service 'deleteClockinClockout' executed successfully." );
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
			Assert.state( request.getManualEntering() != null && StringUtils.hasText( request.getManualEntering().getTimeInterval() ),
					"[clkiows] No 'manualEntering' instance was found on the request or its 'timeInterval' property was not provided." );
			Assert.state( request.getManualEntering().getDay() != null && StringUtils.hasText( request.getManualEntering().getDay().getDate() ),
					"[clkiows] No 'day' instance was found on the request or its 'date' property was not provided." );
			Assert.state( request.getManualEntering().getReason() != null && request.getManualEntering().getReason().getId() != null,
					"[clkiows] No 'reason' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getManualEntering().getDay().getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + pattern + "]. " );
			}
			
			this.getService().insert( profile, 
					new ManualEntering( 
							new Day( day ), 
							new ManualEnteringReason( request.getManualEntering().getReason().getId().intValue() ),
							DurationUtil.fromString( request.getManualEntering().getTimeInterval(), profile.getHoursFormat() ) ) );
			
			return new Response( "Service 'insertManualEntering' executed successfully." );
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
			Assert.state( request.getManualEntering() != null && request.getManualEntering().getId() != null && StringUtils.hasText( request.getManualEntering().getTimeInterval() ),
					"[clkiows] No 'manualEntering' instance was found on the request or either its 'id' or 'timeInterval' properties were not provided." );
			Assert.state( request.getManualEntering().getDay() != null && StringUtils.hasText( request.getManualEntering().getDay().getDate() ),
					"[clkiows] No 'day' instance was found on the request or its 'date' property was not provided." );
			Assert.state( request.getManualEntering().getReason() != null && request.getManualEntering().getReason().getId() != null,
					"[clkiows] No 'reason' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getManualEntering().getDay().getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + pattern + "]. " );
			}
			
			this.getService().update( profile, 
					new ManualEntering( request.getManualEntering().getId().intValue(), 
							new Day( day ), 
							new ManualEnteringReason( request.getManualEntering().getReason().getId().intValue() ),
							DurationUtil.fromString( request.getManualEntering().getTimeInterval(), profile.getHoursFormat() ) ) );
			
			return new Response( "Service 'updateManualEntering' executed successfully." );
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
			Assert.state( request.getManualEntering() != null && request.getManualEntering().getId() != null,
					"[clkiows] No 'manualEntering' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			this.getService().delete( profile, new ManualEntering( request.getManualEntering().getId().intValue() ) );
			
			return new Response( "Service 'deleteManualEntering' executed successfully." );
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
			Assert.hasText( request.getDate(), "[clkiows] Argument 'date' is mandatory." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String dayPattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( dayPattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + dayPattern + "]. " );
			}
			
			this.getService().setNotes( profile, day, request.getText() );
			
			return new Response( "Service 'setNotes' executed successfully." );
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
			Assert.hasText( request.getDate(), "[clkiows] Argument 'date' is mandatory." );
			Assert.hasText( request.getExpectedHours(), "[clkiows] Argument 'expectedHours' is mandatory." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String dayPattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( dayPattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + dayPattern + "]. " );
			}
			
			this.getService().setExpectedHours( profile, day, DurationUtil.fromString( request.getExpectedHours(), profile.getHoursFormat() ) );
			
			return new Response( "Service 'setExpectedHours' executed successfully." );
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
			Assert.hasText( request.getMonth(), "Argument 'month' is mandatory." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser() );
			Assert.state( ( profile = this.getService( ProfileService.class ).get( profile ) ) != null,
					"No record found for the provided 'profile'." );
			
			String pattern = "yyyy-MM";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			YearMonth month = null;
			try {
				month = YearMonth.parse( request.getMonth(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'month' was not valid for the pattern=[" + pattern + "]. " );
			}
			
			return new GetTotalTimeMonthlyResponse( DurationUtil.fromDuration( this.getService().getTotalTime( profile, month.atEndOfMonth() ), profile.getHoursFormat() ) );
		} catch ( Exception e ) {
			LOG.error( e );
			throw new ResponseException( e.getMessage(), new com.clkio.ws.domain.common.ResponseException() );
		}
	}

}
