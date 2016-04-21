package com.clkio.domain;

import java.time.Duration;

import org.springframework.util.StringUtils;

import com.clkio.common.IntervalUtil;
import com.clkio.exception.DomainValidationException;

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
		if( user == null ) throw new DomainValidationException( "Argument user cannot be null." );
		this.user = user;
	}
	public void setDescription(String description) {
		if( !StringUtils.hasText( description ) ) throw new DomainValidationException( "Argument description cannot be null nor empty." );
		this.description = description;
	}
	public void setHoursFormat(String hoursFormat) {
		if( !IntervalUtil.isHoursFormatValid( hoursFormat ) ) throw new DomainValidationException( "The provided value for 'hoursFormat' is not valid." );
		this.hoursFormat = hoursFormat;
	}
	public void setDateFormat(String dateFormat) {
		if( !IntervalUtil.isDateFormatValid( dateFormat ) ) throw new DomainValidationException( "The provided value for 'dateFormat' is not valid." );
		this.dateFormat = dateFormat;
	}
	public void setDefaultExpectedSunday(Duration defaultExpectedSunday) {
		if( defaultExpectedSunday == null ) throw new DomainValidationException( "The argument 'defaultExpectedSunday' is mandatory." );
		this.defaultExpectedSunday = defaultExpectedSunday;
	}
	public void setDefaultExpectedMonday(Duration defaultExpectedMonday) {
		if( defaultExpectedMonday == null ) throw new DomainValidationException( "The argument 'defaultExpectedMonday' is mandatory." );
		this.defaultExpectedMonday = defaultExpectedMonday;
	}
	public void setDefaultExpectedTuesday(Duration defaultExpectedTuesday) {
		if( defaultExpectedTuesday == null ) throw new DomainValidationException( "The argument 'defaultExpectedTuesday' is mandatory." );
		this.defaultExpectedTuesday = defaultExpectedTuesday;
	}
	public void setDefaultExpectedWednesday(Duration defaultExpectedWednesday) {
		if( defaultExpectedWednesday == null ) throw new DomainValidationException( "The argument 'defaultExpectedWednesday' is mandatory." );
		this.defaultExpectedWednesday = defaultExpectedWednesday;
	}
	public void setDefaultExpectedThursday(Duration defaultExpectedThursday) {
		if( defaultExpectedThursday == null ) throw new DomainValidationException( "The argument 'defaultExpectedThursday' is mandatory." );
		this.defaultExpectedThursday = defaultExpectedThursday;
	}
	public void setDefaultExpectedFriday(Duration defaultExpectedFriday) {
		if( defaultExpectedFriday == null ) throw new DomainValidationException( "The argument 'defaultExpectedFriday' is mandatory." );
		this.defaultExpectedFriday = defaultExpectedFriday;
	}
	public void setDefaultExpectedSaturday(Duration defaultExpectedSaturday) {
		if( defaultExpectedSaturday == null ) throw new DomainValidationException( "The argument 'defaultExpectedSaturday' is mandatory." );
		this.defaultExpectedSaturday = defaultExpectedSaturday;
	}
	public User getUser() {
		if( user == null ) throw new DomainValidationException( "The field 'user' was not properly initialized." );
		return user;
	}
	public String getDescription() {
		if( !StringUtils.hasText( description ) ) throw new DomainValidationException( "The field 'description' was not properly initialized." );
		return description;
	}
	public String getHoursFormat() {
		if( !StringUtils.hasText( hoursFormat ) ) throw new DomainValidationException( "The field 'hoursFormat' was not properly initialized." );
		return hoursFormat;
	}
	public String getDateFormat() {
		if( !StringUtils.hasText( dateFormat ) ) throw new DomainValidationException( "The field 'dateFormat' was not properly initialized." );
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