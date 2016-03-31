package com.clkio.domain;

import java.time.Duration;

import org.springframework.util.Assert;

public class ManualEntering extends DayEntering {

	private Day day;
	private ManualEnteringReason reason;
	private Duration timeInterval;
	
	public ManualEntering( Integer id ) {
		super( id );
	}
	
	public ManualEntering( Day day, ManualEnteringReason reason, Duration timeInterval ) {
		this( null, day, reason, timeInterval );
	}
	
	public ManualEntering( Integer id, Day day, ManualEnteringReason reason, Duration timeInterval ) {
		super( id );
		
		this.setDay(day);
		this.setReason(reason);
		this.setTimeInterval(timeInterval);
	}
	
	public void setDay(Day day) {
		Assert.notNull( day, "Argument day cannot be null." );
		this.day = day;
	}
	public void setReason(ManualEnteringReason reason) {
		Assert.notNull( reason, "Argument reason cannot be null." );
		this.reason = reason;
	}
	public void setTimeInterval( Duration timeInterval ) {
		Assert.state( timeInterval != null, "Argument timeInterval has to be not null and greater than 0." );
		this.timeInterval = timeInterval;
	}
	public Day getDay() {
		return day;
	}
	public ManualEnteringReason getReason() {
		return reason;
	}
	public Duration getTimeInterval() {
		return timeInterval;
	}
	
}