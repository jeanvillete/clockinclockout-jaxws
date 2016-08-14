package com.clkio.ws.impl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.jws.WebService;

import org.apache.log4j.Logger;
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
import com.clkio.exception.ClkioException;
import com.clkio.exception.ClkioRuntimeException;
import com.clkio.exception.ValidationException;
import com.clkio.service.ProfileService;
import com.clkio.service.TimeCardService;
import com.clkio.ws.ResponseException;
import com.clkio.ws.TimeCardPort;
import com.clkio.ws.domain.clockinclockout.Clockinclockout;
import com.clkio.ws.domain.common.InternalServerError;
import com.clkio.ws.domain.reason.Reason;
import com.clkio.ws.domain.timecard.DeleteClockinClockoutRequest;
import com.clkio.ws.domain.timecard.DeleteManualEnteringRequest;
import com.clkio.ws.domain.timecard.GetTimeCardRequest;
import com.clkio.ws.domain.timecard.InsertClockinClockoutRequest;
import com.clkio.ws.domain.timecard.InsertManualEnteringRequest;
import com.clkio.ws.domain.timecard.PunchClockRequest;
import com.clkio.ws.domain.timecard.SetExpectedHoursRequest;
import com.clkio.ws.domain.timecard.SetNotesRequest;
import com.clkio.ws.domain.timecard.TimeCardResponse;
import com.clkio.ws.domain.timecard.UpdateClockinClockoutRequest;
import com.clkio.ws.domain.timecard.UpdateManualEnteringRequest;

@WebService( endpointInterface = "com.clkio.ws.TimeCardPort" )
public class TimeCardWSImpl extends WebServiceCommon< TimeCardService > implements TimeCardPort {

	private static final Logger LOG = Logger.getLogger( TimeCardWSImpl.class );
	
	public TimeCardWSImpl() {
		super( TimeCardService.class );
	}
	
	private TimeCardResponse decorateTimeCardResponse( TimeCard timeCard, Profile profile, YearMonth month ) throws ValidationException {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( profile.getDateFormat() );
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern( profile.getDateFormat() + " " + profile.getHoursFormat() );
		
		TimeCardResponse response = new TimeCardResponse( new com.clkio.ws.domain.timecard.TimeCard() );
		for ( Day day : timeCard.getDaysSorted() ) {
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
		
		response.getTimeCard().setTotalTime( DurationUtil.fromDuration( this.getService().getTotalTime( profile ), profile.getHoursFormat() ) );
		
		month = month != null ? month : YearMonth.now();
		response.getTimeCard().setTotalTimeMonthly( DurationUtil.fromDuration( this.getService().getTotalTime( profile, month.atEndOfMonth() ), profile.getHoursFormat() ) );
		
		return response;
	}

	@Override
	public TimeCardResponse getTimeCard( String clkioLoginCode, GetTimeCardRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			YearMonth month = null;
			if ( StringUtils.hasText( request.getMonth() ) ) {
				String pattern = "yyyy-MM";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
				try {
					month = YearMonth.parse( request.getMonth(), formatter );
				} catch ( DateTimeParseException e ) {
					throw new IllegalStateException( "The provided value for 'month' was not valid for the pattern=[" + pattern + "]. " );
				}
			} else 
				month = YearMonth.now();
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, month ), profile, month );
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
	public TimeCardResponse punchClock( String clkioLoginCode, PunchClockRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( !StringUtils.hasText( request.getTimestamp() ) )
				throw new ValidationException( "Argument 'timestamp' is mandatory." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat() + " " + profile.getHoursFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			
			this.getService().punchClock( profile, request.getTimestamp() );

			LocalDateTime dateTime;
			try {
				dateTime = LocalDateTime.parse( request.getTimestamp(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new ValidationException( "The provided value for 'timestamp' was not valid for the pattern=[" + pattern + "]. "
						+ "Remember that this pattern is the 'dateFormat' concatenated with 'hoursFormat' set on the profile, in case you want to change it." );
			}
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, dateTime.toLocalDate() ), profile, YearMonth.from( dateTime ) );
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
	public TimeCardResponse insertClockinClockout( String clkioLoginCode, InsertClockinClockoutRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( request.getClockinclockout() == null )
				throw new ValidationException( "No 'clockinclockout' instance was found on the request." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat() + " " + profile.getHoursFormat();
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
			
			ClockinClockout clockinClockout = new ClockinClockout( clockin, clockout );
			this.getService().insert( profile, clockinClockout );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, clockinClockout.getDay().getDate() ), profile, YearMonth.from( clockinClockout.getDay().getDate() ) );
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
	public TimeCardResponse updateClockinClockout( String clkioLoginCode, UpdateClockinClockoutRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( request.getClockinclockout() == null || request.getClockinclockout().getId() == null )
				throw new ValidationException( "No 'clockinclockout' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat() + " "+ profile.getHoursFormat();
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
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, clkio.getDay().getDate() ), profile, YearMonth.from( clkio.getDay().getDate() ) );
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
	public TimeCardResponse deleteClockinClockout( String clkioLoginCode, DeleteClockinClockoutRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( request.getClockinclockout() == null || request.getClockinclockout().getId() == null )
				throw new ValidationException( "No 'clockinclockout' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			ClockinClockout clkio = new ClockinClockout( request.getClockinclockout().getId().intValue() );
			
			this.getService().delete( profile, clkio );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, clkio.getDay().getDate() ), profile, YearMonth.from( clkio.getDay().getDate() ) );
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
	public TimeCardResponse insertManualEntering( String clkioLoginCode, InsertManualEnteringRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( request.getManualEntering() == null || !StringUtils.hasText( request.getManualEntering().getTimeInterval() ) )
				throw new ValidationException( "No 'manualEntering' instance was found on the request or its 'timeInterval' property was not provided." );
			if ( request.getManualEntering().getDay() == null || !StringUtils.hasText( request.getManualEntering().getDay().getDate() ) )
				throw new ValidationException( "No 'day' instance was found on the request or its 'date' property was not provided." );
			if ( request.getManualEntering().getReason() == null || request.getManualEntering().getReason().getId() == null )
				throw new ValidationException( "No 'reason' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getManualEntering().getDay().getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + pattern + "]. " );
			}
			
			ManualEntering manualEntering = new ManualEntering( 
					new Day( day ), 
					new ManualEnteringReason( request.getManualEntering().getReason().getId().intValue() ),
					DurationUtil.fromString( request.getManualEntering().getTimeInterval(), profile.getHoursFormat() ) );
			
			this.getService().insert( profile, manualEntering );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, manualEntering.getDay().getDate() ), profile, YearMonth.from( manualEntering.getDay().getDate() ) );
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
	public TimeCardResponse updateManualEntering( String clkioLoginCode, UpdateManualEnteringRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( request.getManualEntering() == null || request.getManualEntering().getId() == null || !StringUtils.hasText( request.getManualEntering().getTimeInterval() ) )
				throw new ValidationException( "No 'manualEntering' instance was found on the request or either its 'id' or 'timeInterval' properties were not provided." );
			if ( request.getManualEntering().getDay() == null || !StringUtils.hasText( request.getManualEntering().getDay().getDate() ) )
				throw new ValidationException( "No 'day' instance was found on the request or its 'date' property was not provided." );
			if ( request.getManualEntering().getReason() == null || request.getManualEntering().getReason().getId() == null )
				throw new ValidationException( "No 'reason' instance was found on the request or its 'id' property was not provided." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String pattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getManualEntering().getDay().getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + pattern + "]. " );
			}
			
			ManualEntering manualEntering = new ManualEntering( request.getManualEntering().getId().intValue(), 
					new Day( day ), 
					new ManualEnteringReason( request.getManualEntering().getReason().getId().intValue() ),
					DurationUtil.fromString( request.getManualEntering().getTimeInterval(), profile.getHoursFormat() ) );
			
			this.getService().update( profile, manualEntering );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, manualEntering.getDay().getDate() ), profile, YearMonth.from( manualEntering.getDay().getDate() ) );
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
	public TimeCardResponse deleteManualEntering( String clkioLoginCode, DeleteManualEnteringRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( request.getManualEntering() == null || request.getManualEntering().getId() == null )
				throw new ValidationException( "No 'manualEntering' instance was found on the request or its 'id' property was not provided." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			ManualEntering manualEntering = new ManualEntering( request.getManualEntering().getId().intValue() );
			
			this.getService().delete( profile, manualEntering );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, manualEntering.getDay().getDate() ), profile, YearMonth.from( manualEntering.getDay().getDate() ) );
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
	public TimeCardResponse setNotes( String clkioLoginCode, SetNotesRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( !StringUtils.hasText( request.getDate() ) )
				throw new ValidationException( "Argument 'date' is mandatory." );
			
			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String dayPattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( dayPattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + dayPattern + "]. " );
			}
			
			this.getService().setNotes( profile, day, request.getText() );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, day ), profile, YearMonth.from( day ) );
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
	public TimeCardResponse setExpectedHours( String clkioLoginCode, SetExpectedHoursRequest request ) throws ResponseException {
		try {
			if ( request == null )
				throw new ValidationException( "No valid request was provided." );
			if ( request.getProfile() == null || request.getProfile().getId() == null )
				throw new ValidationException( "No 'profile' instance was found on the request or its 'id' property was not provided." );
			if ( !StringUtils.hasText( request.getDate() ) )
				throw new ValidationException( "Argument 'date' is mandatory." );
			if ( !StringUtils.hasText( request.getExpectedHours() ) )
				throw new ValidationException( "Argument 'expectedHours' is mandatory." );

			Profile profile = new Profile( request.getProfile().getId().intValue() );
			profile.setUser( this.getCurrentUser( clkioLoginCode ) );
			if ( ( profile = this.getService( ProfileService.class ).get( profile ) ) == null )
				throw new ValidationException( "No record found for the provided 'profile'." );
			
			String dayPattern = profile.getDateFormat();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( dayPattern );
			LocalDate day = null;
			try {
				day = LocalDate.parse( request.getDate(), formatter );
			} catch ( DateTimeParseException e ) {
				throw new IllegalStateException( "The provided value for 'date' was not valid for the pattern=[" + dayPattern + "]. " );
			}
			
			this.getService().setExpectedHours( profile, day, DurationUtil.fromString( request.getExpectedHours(), profile.getHoursFormat() ) );
			
			return decorateTimeCardResponse( this.getService().getTimeCard( profile, day ), profile, YearMonth.from( day ) );
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
