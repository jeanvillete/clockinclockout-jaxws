package org.com.clockinclockout.domain;

import java.util.Date;

import org.springframework.util.Assert;

public class ClockinClockout extends CommonDomain {

	private Day day;
	private Date clockin;
	private Date clockout;
	
	public ClockinClockout( Integer id ) {
		super( id );
	}
	
	public ClockinClockout( Day day, Date clockin, Date clockout) {
		this( null, day, clockin, clockout );
	}
	
	public ClockinClockout( Integer id, Day day, Date clockin, Date clockout) {
		super( id );
		this.setDay(day);
		this.setClockin(clockin);
		this.setClockout(clockout);
	}
	
	public void setDay(Day day) {
		Assert.notNull( day, "Argument day cannot be null." );
		this.day = day;
	}
	public void setClockin(Date clockin) {
		Assert.notNull( clockin, "Argument clockin cannot be null." );
		this.clockin = clockin;
	}
	public void setClockout(Date clockout) {
		Assert.notNull( clockout, "Argument clockout cannot be null." );
		this.clockout = clockout;
	}
	public Day getDay() {
		Assert.state( day != null, "The property 'day' has not been initialized yet." );
		return day;
	}
	public Date getClockin() {
		Assert.state( clockin != null, "The property 'clockin' has not been initialized yet." );
		return clockin;
	}
	public Date getClockout() {
		Assert.state( clockout != null, "The property 'clockout' has not been initialized yet." );
		return clockout;
	}
	
}
