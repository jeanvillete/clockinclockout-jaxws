package com.clkio.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;
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
		Assert.notNull( dayService, "Property 'dayService' has not been properly initialized." );
		Assert.notNull( clockinClockoutService, "Property 'clockinClockoutService' has not been properly initialized." );
		Assert.notNull( manualEnteringService, "Property 'manualEnteringService' has not been properly initialized." );
	}

	private Day insert( final Profile profile, final LocalDate localDate ) {
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
		
		Assert.notNull( day.getExpectedHours(), "A proper value for 'expectedHours' is mandatory for 'day' instance." );
		this.dayService.insert( day );
		
		return day;
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final Profile profile, final ClockinClockout clockinClockout ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.notNull( clockinClockout, "Argument 'clock' is mandatory." );
		Assert.state( clockinClockout.getClockin() != null || clockinClockout.getClockout() != null,
				"At least 'clockin' or 'clockout' has to be provided." );
		
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
	@Transactional( propagation = Propagation.REQUIRED )
	public void punchClock( final Profile profile, final String clock ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		
		String pattern = profile.getDateFormat() + profile.getHoursFormat();
		LocalDateTime dateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( pattern );
			dateTime = LocalDateTime.parse( clock, formatter );
		} catch ( DateTimeParseException e ) {
			throw new IllegalStateException( "Argument 'clock' doesn't meet the pattern for the provided 'profile': " + pattern );
		}
		
		LocalDate localDateDay = dateTime.toLocalDate();
		Day day = this.dayService.get( profile, localDateDay );
		
		if ( day == null ) {
			day = this.insert( profile, localDateDay );
			this.clockinClockoutService.insert( new ClockinClockout( day, dateTime, null ) );
		} else {
			ClockinClockout clockinClockout = this.clockinClockoutService.getNewest( day );
			if ( clockinClockout == null || clockinClockout.getClockout() != null )
				this.clockinClockoutService.insert( new ClockinClockout( day, dateTime, null ) );
			else {
				clockinClockout.setClockout( dateTime );
				this.clockinClockoutService.update( clockinClockout );
			}
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final Profile profile, final ClockinClockout clockinClockout ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.state( clockinClockout != null && clockinClockout.getId() != null,
				"Argument 'clockinClockout' and its 'id' property are mandatory." );
		Assert.state( clockinClockout.getClockin() != null || clockinClockout.getClockout() != null,
				"At least 'clockin' or 'clockout' has to be provided." );
		
		ClockinClockout syncClockinClockout = this.clockinClockoutService.get( profile, clockinClockout );
		Assert.notNull( syncClockinClockout, "No 'clockinClockout' register was found given the provided 'id'." );

		Day day = this.dayService.get( syncClockinClockout.getDay() );
		Assert.notNull( day, "No 'day' register was found for the given 'clockinClockout's 'id' property." );
		
		Assert.state( clockinClockout.getClockin() == null || 
				day.getDate().equals( clockinClockout.getClockin().toLocalDate() ), 
				"The provided date value for 'clockin' has to be the same as its 'day' relationship." );
		
		syncClockinClockout.setClockin( clockinClockout.getClockin() );
		syncClockinClockout.setClockout( clockinClockout.getClockout() );

		this.clockinClockoutService.update( syncClockinClockout );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile, final ClockinClockout clockinClockout ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.state( clockinClockout != null && clockinClockout.getId() != null,
				"Argument 'clockinClockout' and its 'id' property are mandatory." );
		this.clockinClockoutService.delete( profile, clockinClockout );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final Profile profile, final ManualEntering manualEntering ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.notNull( manualEntering, "Argument 'manualEntering' is mandatory." );
		Assert.state( manualEntering.getDay() != null && manualEntering.getDay().getDate() != null,
				"Nested 'day' and its 'date' properties are mandatory." );
		Assert.state( manualEntering.getReason() != null && manualEntering.getReason().getId() != null,
				"Nested 'reason' and its 'id' properties are mandatory." );
		Assert.notNull( manualEntering.getTimeInterval(), "Nested 'timeInterval' property is mandatoy." );
		
		Day day = this.dayService.get( profile, manualEntering.getDay().getDate() );
		if ( day == null )
			day = this.insert( profile, manualEntering.getDay().getDate() );
		
		manualEntering.setDay( day );
		this.manualEnteringService.insert( manualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final Profile profile, final ManualEntering manualEntering ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.notNull( manualEntering, "Argument 'manualEntering' is mandatory." );
		Assert.state( manualEntering.getDay() != null && manualEntering.getDay().getDate() != null,
				"Nested 'day' and its 'date' properties are mandatory." );
		Assert.state( manualEntering.getReason() != null && manualEntering.getReason().getId() != null,
				"Nested 'reason' and its 'id' properties are mandatory." );
		Assert.notNull( manualEntering.getTimeInterval(), "Nested 'timeInterval' property is mandatoy." );
		
		ManualEntering syncManualEntering = this.manualEnteringService.get( profile, manualEntering );
		Assert.notNull( syncManualEntering, "No 'manualEntering' record was found." );
		
		Day day = this.dayService.get( syncManualEntering.getDay() );
		Assert.notNull( day, "No 'day' register was found for the given 'clockinClockout's 'id' property." );
		
		Assert.state( day.getDate().equals( manualEntering.getDay().getDate() ), 
				"The provided date value for 'day' instance has to be the same as its 'day' relationship." );
		
		syncManualEntering.setReason( manualEntering.getReason() );
		syncManualEntering.setTimeInterval( manualEntering.getTimeInterval() );
		
		this.manualEnteringService.update( syncManualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile, final ManualEntering manualEntering ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.state( manualEntering != null && manualEntering.getId() != null,
				"Argument 'manualEntering' and its 'id' property are mandatory." );
		this.manualEnteringService.delete( profile, manualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void setNotes( final Profile profile, final LocalDate date, final String text ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.notNull( date, "Argument 'date' is mandatory." );
		Assert.hasText( text, "Argument 'text' is mandatory." );
		
		Day day = this.dayService.get( profile, date );
		if ( day == null )
			day = this.insert( profile, date );
		
		day.setNotes( text );
		this.dayService.update( day );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void setExpectedHours( final Profile profile, final LocalDate date, final Duration expectedHours ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.notNull( date, "Argument 'date' is mandatory." );
		Assert.notNull( expectedHours, "Argument 'expectedHours' is mandatory." );
		
		Day day = this.dayService.get( profile, date );
		if ( day == null )
			day = this.insert( profile, date );
		
		day.setExpectedHours( expectedHours );
		this.dayService.update( day );
	}

	@Override
	public Duration getTotalTime( Profile profile ) {
		return this.getTotalTime( profile, null );
	}

	@Override
	@Transactional( propagation = Propagation.NOT_SUPPORTED, readOnly = true )
	public Duration getTotalTime( Profile profile, LocalDate until ) {
		Assert.state( profile != null && profile.getId() != null, 
				"Argument profile and its 'id' property are mandatory." );
		return this.repository.getTotalTime( profile, until );
	}
	
}
