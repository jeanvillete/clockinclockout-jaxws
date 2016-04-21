package com.clkio.domain;

import java.time.LocalDateTime;

import com.clkio.exception.DomainValidationException;

public class ClockinClockout extends DayEntering {

	private Day day;
	private LocalDateTime clockin;
	private LocalDateTime clockout;

	public ClockinClockout( Integer id ) {
		super( id );
	}

	public ClockinClockout( LocalDateTime clockin, LocalDateTime clockout ) {
		this( null, null, clockin, clockout );
	}
	
	public ClockinClockout( Day day, LocalDateTime clockin, LocalDateTime clockout ) {
		this( null, day, clockin, clockout );
	}

	public ClockinClockout( Integer id, Day day, LocalDateTime clockin, LocalDateTime clockout ) {
		super( id );
		this.setDay( day );
		this.setClockin( clockin );
		this.setClockout( clockout );
	}

	public void setDay( Day day ) {
		this.day = day;
	}

	public void setClockin( LocalDateTime clockin ) {
		this.clockin = clockin;
	}

	public void setClockout( LocalDateTime clockout ) {
		this.clockout = clockout;
	}

	public Day getDay() {
		if( day == null ) throw new DomainValidationException( "The property 'day' has not been initialized yet." );
		return day;
	}

	public LocalDateTime getClockin() {
		return clockin;
	}

	public LocalDateTime getClockout() {
		return clockout;
	}

}
