package com.clkio.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;
import com.clkio.domain.TimeCard;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;
import com.clkio.exception.ValidationException;
import com.clkio.repository.TimeCardRepository;
import com.clkio.service.ClockinClockoutService;
import com.clkio.service.DayService;
import com.clkio.service.ManualEnteringService;
import com.clkio.service.TimeCardService;

@Service
public class TimeCardServiceImpl implements TimeCardService, InitializingBean {
	
	@Autowired
	private TimeCardRepository repository;
	
	@Autowired
	private DayService dayService;
	
	@Autowired
	private ClockinClockoutService clockinClockoutService;
	
	@Autowired
	private ManualEnteringService manualEnteringService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( repository, "Property 'repository' has not been properly initialized." );
		Assert.notNull( dayService, "Property 'dayService' has not been properly initialized." );
		Assert.notNull( clockinClockoutService, "Property 'clockinClockoutService' has not been properly initialized." );
		Assert.notNull( manualEnteringService, "Property 'manualEnteringService' has not been properly initialized." );
	}

	private Day insert( final Profile profile, final LocalDate localDate ) throws PersistenceException, ValidationException, ConflictException {
		Day day = new Day();
		day.setDate( localDate );
		day.setProfile( profile );
		
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		switch ( dayOfWeek.getValue() ) {
		case 1:
			day.setExpectedHours( profile.getDefaultExpectedMonday() );
			break;
		case 2:
			day.setExpectedHours( profile.getDefaultExpectedTuesday() );
			break;
		case 3:
			day.setExpectedHours( profile.getDefaultExpectedWednesday() );
			break;
		case 4:
			day.setExpectedHours( profile.getDefaultExpectedThursday() );
			break;
		case 5:
			day.setExpectedHours( profile.getDefaultExpectedFriday() );
			break;
		case 6:
			day.setExpectedHours( profile.getDefaultExpectedSaturday() );
			break;
		case 7:
			day.setExpectedHours( profile.getDefaultExpectedSunday() );
			break;
		default:
			throw new IllegalStateException( "No valid value was retrieved for 'dayOfWeek'." );
		}
		
		if( day.getExpectedHours() == null )
			throw new ConflictException( "It was not possible infer a proper value for 'expectedHours'." );
		
		this.dayService.insert( day );
		
		return day;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public ClockinClockout punchClock( final Profile profile, final String timestamp ) throws ValidationException, PersistenceException, ConflictException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		
		String pattern = profile.getDateFormat() + " " + profile.getHoursFormat();
		LocalDateTime dateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			dateTime = LocalDateTime.parse( timestamp, formatter );
		} catch ( DateTimeParseException e ) {
			throw new ValidationException( "The provided value for 'timestamp' was not valid for the pattern=[" + pattern + "]. "
					+ "Remember that this pattern is the 'dateFormat' concatenated with 'hoursFormat' set on the profile, in case you want to change it." );
		}
		
		LocalDate localDateDay = dateTime.toLocalDate();
		Day day = this.dayService.get( profile, localDateDay );
		
		ClockinClockout clockinClockout = null;
		if ( day == null ) {
			day = this.insert( profile, localDateDay );
			this.clockinClockoutService.insert( clockinClockout = new ClockinClockout( day, dateTime, null ) );
		} else {
			clockinClockout = this.clockinClockoutService.getNewest( day );
			if ( clockinClockout == null || clockinClockout.getClockout() != null )
				this.clockinClockoutService.insert( clockinClockout = new ClockinClockout( day, dateTime, null ) );
			else {
				clockinClockout.setClockout( dateTime );
				this.clockinClockoutService.update( clockinClockout );
			}
		}
		return clockinClockout;
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void insert( final Profile profile, final ClockinClockout clockinClockout ) throws ValidationException, PersistenceException, ConflictException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( clockinClockout == null )
			throw new ValidationException( "Argument 'clock' is mandatory." );
		if( clockinClockout.getClockin() == null && clockinClockout.getClockout() == null )
			throw new ValidationException( "At least 'clockin' or 'clockout' has to be provided." );
		else if( ( clockinClockout.getClockin() != null && clockinClockout.getClockout() != null ) && clockinClockout.getClockout().isBefore( clockinClockout.getClockin() ) )
			throw new ValidationException( "The provided 'clockout' has to be greater than 'clockin'." );
		
		LocalDate localDateDay = clockinClockout.getClockin() != null ?
				clockinClockout.getClockin().toLocalDate() :
					clockinClockout.getClockout().toLocalDate();
		Day day = this.dayService.get( profile, localDateDay );
		if ( day == null )
			day = this.insert( profile, localDateDay );
			
		clockinClockout.setDay( day );
		this.clockinClockoutService.insert( clockinClockout );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void update( final Profile profile, final ClockinClockout clockinClockout ) throws ValidationException, PersistenceException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( clockinClockout == null || clockinClockout.getId() == null )
			throw new ValidationException( "Argument 'clockinClockout' and its 'id' property are mandatory." );
		if( clockinClockout.getClockin() == null && clockinClockout.getClockout() == null )
			throw new ValidationException( "At least 'clockin' or 'clockout' has to be provided." );
		else if( ( clockinClockout.getClockin() != null && clockinClockout.getClockout() != null ) && clockinClockout.getClockout().isBefore( clockinClockout.getClockin() ) )
			throw new ValidationException( "The provided 'clockout' has to be greater than 'clockin'." );
		
		ClockinClockout syncClockinClockout = this.clockinClockoutService.get( profile, clockinClockout );
		if( syncClockinClockout == null )
			throw new PersistenceException( "No 'clockinClockout' register was found given the provided 'id'." );

		Day day = this.dayService.get( syncClockinClockout.getDay() );
		if( day == null )
			throw new PersistenceException( "No 'day' register was found for the given 'clockinClockout's 'id' property." );
		
		if( clockinClockout.getClockin() != null && !day.getDate().equals( clockinClockout.getClockin().toLocalDate() ) ) 
			throw new ValidationException( "The provided date value for 'clockin' has to be the same as its 'day' relationship." );
		
		syncClockinClockout.setClockin( clockinClockout.getClockin() );
		syncClockinClockout.setClockout( clockinClockout.getClockout() );

		this.clockinClockoutService.update( syncClockinClockout );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void delete( final Profile profile, final ClockinClockout clockinClockout ) throws ValidationException, PersistenceException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( clockinClockout == null || clockinClockout.getId() == null )
			throw new ValidationException( "Argument 'clockinClockout' and its 'id' property are mandatory." );
		this.clockinClockoutService.delete( profile, clockinClockout );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void insert( final Profile profile, final ManualEntering manualEntering ) throws ValidationException, PersistenceException, ConflictException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( manualEntering == null )
			throw new ValidationException( "Argument 'manualEntering' is mandatory." );
		if( manualEntering.getDay() == null || manualEntering.getDay().getDate() == null )
			throw new ValidationException( "Nested 'day' and its 'date' properties are mandatory." );
		if( manualEntering.getReason() == null || manualEntering.getReason().getId() == null )
			throw new ValidationException( "Nested 'reason' and its 'id' properties are mandatory." );
		if( manualEntering.getTimeInterval() == null )
			throw new ValidationException( "Nested 'timeInterval' property is mandatoy." );
		
		Day day = this.dayService.get( profile, manualEntering.getDay().getDate() );
		if ( day == null )
			day = this.insert( profile, manualEntering.getDay().getDate() );
		
		manualEntering.setDay( day );
		this.manualEnteringService.insert( manualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void update( final Profile profile, final ManualEntering manualEntering ) throws ValidationException, PersistenceException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( manualEntering == null )
			throw new ValidationException( "Argument 'manualEntering' is mandatory." );
		if( manualEntering.getDay() == null || manualEntering.getDay().getDate() == null )
			throw new ValidationException( "Nested 'day' and its 'date' properties are mandatory." );
		if( manualEntering.getReason() == null || manualEntering.getReason().getId() == null )
			throw new ValidationException( "Nested 'reason' and its 'id' properties are mandatory." );
		if( manualEntering.getTimeInterval() == null )
			throw new ValidationException( "Nested 'timeInterval' property is mandatoy." );
		
		ManualEntering syncManualEntering = this.manualEnteringService.get( profile, manualEntering );
		if( syncManualEntering == null )
			throw new PersistenceException( "No 'manualEntering' record was found." );
		
		Day day = this.dayService.get( syncManualEntering.getDay() );
		if( day == null )
			throw new PersistenceException( "No 'day' register was found for the given 'clockinClockout's 'id' property." );
		
		if( !day.getDate().equals( manualEntering.getDay().getDate() ) ) 
			throw new ValidationException( "The provided date value for 'day' instance has to be the same as its 'day' relationship." );
		
		syncManualEntering.setReason( manualEntering.getReason() );
		syncManualEntering.setTimeInterval( manualEntering.getTimeInterval() );
		
		this.manualEnteringService.update( syncManualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void delete( final Profile profile, final ManualEntering manualEntering ) throws ValidationException, PersistenceException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( manualEntering == null || manualEntering.getId() == null )
			throw new ValidationException( "Argument 'manualEntering' and its 'id' property are mandatory." );
		this.manualEnteringService.delete( profile, manualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void setNotes( final Profile profile, final LocalDate date, final String text ) throws ValidationException, PersistenceException, ConflictException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( date == null )
			throw new ValidationException( "Argument 'date' is mandatory." );
		
		Day day = this.dayService.get( profile, date );
		if ( day == null )
			day = this.insert( profile, date );
		
		day.setNotes( text );
		this.dayService.update( day );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void setExpectedHours( final Profile profile, final LocalDate date, final Duration expectedHours ) throws ValidationException, PersistenceException, ConflictException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( date == null )
			throw new ValidationException( "Argument 'date' is mandatory." );
		if( expectedHours == null )
			throw new ValidationException( "Argument 'expectedHours' is mandatory." );
		if ( expectedHours.compareTo( Duration.ofHours( 24 ) ) > 0 )
			throw new ValidationException( "Argument 'expectedHours' must be less than 24 hours." );
		
		Day day = this.dayService.get( profile, date );
		if ( day == null )
			day = this.insert( profile, date );
		
		day.setExpectedHours( expectedHours );
		this.dayService.update( day );
	}

	@Override
	public Duration getTotalTime( Profile profile ) throws ValidationException {
		return this.getTotalTime( profile, null );
	}

	@Override
	@Transactional( propagation = Propagation.NOT_SUPPORTED, readOnly = true )
	public Duration getTotalTime( Profile profile, LocalDate until ) throws ValidationException {
		if( profile == null || profile.getId() == null ) 
			throw new ValidationException( "Argument profile and its 'id' property are mandatory." );
		return this.repository.getTotalTime( profile, until );
	}

	@Override
	@Transactional( propagation = Propagation.NOT_SUPPORTED, readOnly = true )
	public TimeCard getTimeCard( Profile profile, YearMonth month ) throws ValidationException {
		if( profile == null || profile.getId() == null ) 
			throw new ValidationException( "Argument profile and its 'id' property are mandatory." );
		if( month == null )
			throw new ValidationException( "Argument 'month' is mandatory." );
		
		TimeCard timeCard = new TimeCard( month );
		
		LocalDate startDate = month.atDay( 1 );
		LocalDate endDate = month.atEndOfMonth();
		
		List< Day > listDay = this.dayService.list( profile, startDate, endDate );
		if ( !CollectionUtils.isEmpty( listDay ) )
			for ( Day day : listDay ) {
				day.setBalance( day.getExpectedHours().negated() );
				timeCard.getDays().put( day.getDate(), day );
			}
		
		List< ClockinClockout > listClockinClockout = this.clockinClockoutService.list( profile, startDate, endDate );
		if ( !CollectionUtils.isEmpty( listClockinClockout ) )
			for ( ClockinClockout clkio : listClockinClockout ) {
				Day day = timeCard.getDays().get( clkio.getDay().getDate() );
				day.getTableEntering().add( clkio );
				if ( clkio.getClockin() != null && clkio.getClockout() != null )
					day.setBalance( Duration.between( clkio.getClockin(), clkio.getClockout() ).plus( day.getBalance() ) );
			}
		
		List< ManualEntering > listManualEntering = this.manualEnteringService.list( profile, startDate, endDate );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering entering : listManualEntering ) {
				Day day  = timeCard.getDays().get( entering.getDay().getDate() );
				day.getTableEntering().add( entering );
				day.setBalance( entering.getTimeInterval().plus( day.getBalance() ) );
			}
		
		return timeCard;
	}
	
}
