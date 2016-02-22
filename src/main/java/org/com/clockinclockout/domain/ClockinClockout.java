package org.com.clockinclockout.domain;

import java.util.Date;

import org.springframework.util.Assert;

public class ClockinClockout {

	private Day day;
	private Date clockin;
	private Date clockout;
	
	public ClockinClockout(Day day, Date clockin, Date clockout) {
		super();
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
		return day;
	}
	public Date getClockin() {
		return clockin;
	}
	public Date getClockout() {
		return clockout;
	}
	
}
