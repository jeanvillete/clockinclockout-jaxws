package org.com.clockinclockout.domain;

import org.springframework.util.Assert;

public class Profile extends CommonDomain {
	
	private User user;
	private String description;
	private String hoursFormat;
	private String dateFormat;
	private Integer defaultExpectedSunday;
	private Integer defaultExpectedMonday;
	private Integer defaultExpectedTuesday;
	private Integer defaultExpectedWednesday;
	private Integer defaultExpectedThursday;
	private Integer defaultExpectedFriday;
	private Integer defaultExpectedSaturday;

	public Profile( User user, String description, String hoursFormat, String dateFormat ) {
		this( null, user, description, hoursFormat, dateFormat );
	}
	
	public Profile( Integer id, User user, String description, String hoursFormat, String dateFormat ) {
		super( id );
		
		this.setUser( user );
		this.setDescription( description );
		this.setHoursFormat( hoursFormat );
		this.setDateFormat( dateFormat );
	}
	
	public void setUser(User user) {
		Assert.notNull( user, "Argument user cannot be null." );
		this.user = user;
	}
	public void setDescription(String description) {
		Assert.hasLength( description, "Argument description cannot be null nor empty." );
		this.description = description;
	}
	public void setHoursFormat(String hoursFormat) {
		Assert.hasLength( hoursFormat, "Argument hoursFormat cannot be null nor empty." );
		this.hoursFormat = hoursFormat;
	}
	public void setDateFormat(String dateFormat) {
		Assert.hasLength( dateFormat, "Argument dateFormat cannot be null nor empty." );
		this.dateFormat = dateFormat;
	}
	public void setDefaultExpectedSunday(Integer defaultExpectedSunday) {
		this.defaultExpectedSunday = defaultExpectedSunday;
	}
	public void setDefaultExpectedMonday(Integer defaultExpectedMonday) {
		this.defaultExpectedMonday = defaultExpectedMonday;
	}
	public void setDefaultExpectedTuesday(Integer defaultExpectedTuesday) {
		this.defaultExpectedTuesday = defaultExpectedTuesday;
	}
	public void setDefaultExpectedWednesday(Integer defaultExpectedWednesday) {
		this.defaultExpectedWednesday = defaultExpectedWednesday;
	}
	public void setDefaultExpectedThursday(Integer defaultExpectedThursday) {
		this.defaultExpectedThursday = defaultExpectedThursday;
	}
	public void setDefaultExpectedFriday(Integer defaultExpectedFriday) {
		this.defaultExpectedFriday = defaultExpectedFriday;
	}
	public void setDefaultExpectedSaturday(Integer defaultExpectedSaturday) {
		this.defaultExpectedSaturday = defaultExpectedSaturday;
	}
	public User getUser() {
		return user;
	}
	public String getDescription() {
		return description;
	}
	public String getHoursFormat() {
		return hoursFormat;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public Integer getDefaultExpectedSunday() {
		return defaultExpectedSunday;
	}
	public Integer getDefaultExpectedMonday() {
		return defaultExpectedMonday;
	}
	public Integer getDefaultExpectedTuesday() {
		return defaultExpectedTuesday;
	}
	public Integer getDefaultExpectedWednesday() {
		return defaultExpectedWednesday;
	}
	public Integer getDefaultExpectedThursday() {
		return defaultExpectedThursday;
	}
	public Integer getDefaultExpectedFriday() {
		return defaultExpectedFriday;
	}
	public Integer getDefaultExpectedSaturday() {
		return defaultExpectedSaturday;
	}
}