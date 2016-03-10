package com.clkio.domain;

import java.time.Duration;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class Profile extends CommonDomain {
	
	private User user;
	private String description;
	private String hoursFormat;
	private String dateFormat;
	private Duration defaultExpectedSunday;
	private Duration defaultExpectedMonday;
	private Duration defaultExpectedTuesday;
	private Duration defaultExpectedWednesday;
	private Duration defaultExpectedThursday;
	private Duration defaultExpectedFriday;
	private Duration defaultExpectedSaturday;

	public Profile( Integer id ) {
		super( id );
	}
	
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
	public void setDefaultExpectedSunday(Duration defaultExpectedSunday) {
		this.defaultExpectedSunday = defaultExpectedSunday;
	}
	public void setDefaultExpectedMonday(Duration defaultExpectedMonday) {
		this.defaultExpectedMonday = defaultExpectedMonday;
	}
	public void setDefaultExpectedTuesday(Duration defaultExpectedTuesday) {
		this.defaultExpectedTuesday = defaultExpectedTuesday;
	}
	public void setDefaultExpectedWednesday(Duration defaultExpectedWednesday) {
		this.defaultExpectedWednesday = defaultExpectedWednesday;
	}
	public void setDefaultExpectedThursday(Duration defaultExpectedThursday) {
		this.defaultExpectedThursday = defaultExpectedThursday;
	}
	public void setDefaultExpectedFriday(Duration defaultExpectedFriday) {
		this.defaultExpectedFriday = defaultExpectedFriday;
	}
	public void setDefaultExpectedSaturday(Duration defaultExpectedSaturday) {
		this.defaultExpectedSaturday = defaultExpectedSaturday;
	}
	public User getUser() {
		Assert.state( user != null, "The field 'user' was not properly initialized." );
		return user;
	}
	public String getDescription() {
		Assert.state( StringUtils.hasText( description ), "The field 'description' was not properly initialized." );
		return description;
	}
	public String getHoursFormat() {
		Assert.state( StringUtils.hasText( hoursFormat ), "The field 'hoursFormat' was not properly initialized." );
		return hoursFormat;
	}
	public String getDateFormat() {
		Assert.state( StringUtils.hasText( dateFormat ), "The field 'dateFormat' was not properly initialized." );
		return dateFormat;
	}
	public Duration getDefaultExpectedSunday() {
		return defaultExpectedSunday;
	}
	public Duration getDefaultExpectedMonday() {
		return defaultExpectedMonday;
	}
	public Duration getDefaultExpectedTuesday() {
		return defaultExpectedTuesday;
	}
	public Duration getDefaultExpectedWednesday() {
		return defaultExpectedWednesday;
	}
	public Duration getDefaultExpectedThursday() {
		return defaultExpectedThursday;
	}
	public Duration getDefaultExpectedFriday() {
		return defaultExpectedFriday;
	}
	public Duration getDefaultExpectedSaturday() {
		return defaultExpectedSaturday;
	}
}