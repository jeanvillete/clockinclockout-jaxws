package com.clkio.domain;

import java.time.Duration;
import java.time.LocalDate;

import org.springframework.util.Assert;

public class Day extends CommonDomain {

	private LocalDate date;
	private Duration expectedHours;
	private String notes;
	private Profile profile;
	
	public Day() {
		super( null );
	}

	public Day( Integer id ) {
		super( id );
	}

	public Day( LocalDate date ) {
		super( null );
		this.setDate( date );
	}
	
	public Day( LocalDate date, Duration expectedHours, String notes, Profile profile ) {
		this( null, date, expectedHours, notes, profile );
	}

	public Day( Integer id, LocalDate date, Duration expectedHours, String notes, Profile profile ) {
		super( id );
		this.setDate( date );
		this.setExpectedHours( expectedHours );
		this.setNotes( notes );
		this.setProfile( profile );
	}

	public void setDate( LocalDate date ) {
		Assert.notNull( date, "Argument date cannot be null." );
		this.date = date;
	}

	public void setExpectedHours( Duration expectedHours ) {
		Assert.state( expectedHours != null, "Argument expectedHours has to be not null and greater than 0." );
		this.expectedHours = expectedHours;
	}

	public void setNotes( String notes ) {
		this.notes = notes;
	}

	public void setProfile( Profile profile ) {
		Assert.notNull( profile, "Argument profile cannot be null." );
		this.profile = profile;
	}

	public LocalDate getDate() {
		Assert.state( date != null, "The property 'date' has not been properly initialized." );
		return date;
	}

	public Duration getExpectedHours() {
		Assert.state( expectedHours != null, "The property 'expectedHours' has not been properly initialized." );
		return expectedHours;
	}

	public String getNotes() {
		return notes;
	}

	public Profile getProfile() {
		Assert.state( profile != null, "The property 'profile' has not been properly initialized." );
		return profile;
	}
}