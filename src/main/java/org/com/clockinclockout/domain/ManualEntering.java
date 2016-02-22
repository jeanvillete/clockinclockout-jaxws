package org.com.clockinclockout.domain;

import org.springframework.util.Assert;

public class ManualEntering {

	private Day day;
	private ManualEnteringReason reason;
	private Integer timeInterval;
	
	public ManualEntering(Day day, ManualEnteringReason reason, Integer timeInterval) {
		super();
		
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
	public void setTimeInterval(Integer timeInterval) {
		Assert.state( timeInterval != null && timeInterval > 0, "Argument timeInterval has to be not null and greater than 0." );
		this.timeInterval = timeInterval;
	}
	public Day getDay() {
		return day;
	}
	public ManualEnteringReason getReason() {
		return reason;
	}
	public Integer getTimeInterval() {
		return timeInterval;
	}
	
}