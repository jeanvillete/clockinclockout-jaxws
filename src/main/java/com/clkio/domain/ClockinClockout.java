package com.clkio.domain;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

public class ClockinClockout extends CommonDomain {

	private Day day;
	private LocalDateTime clockin;
	private LocalDateTime clockout;

	public ClockinClockout( Integer id ) {
		super( id );
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
		Assert.notNull( day, "Argument day cannot be null." );
		this.day = day;
	}

	public void setClockin( LocalDateTime clockin ) {
		Assert.notNull( clockin, "Argument clockin cannot be null." );
		this.clockin = clockin;
	}

	public void setClockout( LocalDateTime clockout ) {
		Assert.notNull( clockout, "Argument clockout cannot be null." );
		this.clockout = clockout;
	}

	public Day getDay() {
		Assert.state( day != null, "The property 'day' has not been initialized yet." );
		return day;
	}

	public LocalDateTime getClockin() {
		Assert.state( clockin != null, "The property 'clockin' has not been initialized yet." );
		return clockin;
	}

	public LocalDateTime getClockout() {
		Assert.state( clockout != null, "The property 'clockout' has not been initialized yet." );
		return clockout;
	}

}
