package com.clkio.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.clkio.exception.DomainValidationException;

public class Day extends CommonDomain {

	private LocalDate date;
	private Duration expectedHours;
	private String notes;
	private Profile profile;
	
	private transient List< DayEntering > tableEntering;
	private Duration balance;
	
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

	@Override
	public boolean equals( Object obj ) {
		if ( obj == null ) return false;
		if ( obj == this ) return true;
		try {
			Day other = ( Day ) obj;
			return other.getDate() != null && other.getDate().equals( this.date );
		} catch ( ClassCastException e ) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		if( date == null ) throw new DomainValidationException( "Property 'date' has not been initialized." );
		return this.date.hashCode();
	}
	
	public void setDate( LocalDate date ) {
		if( date == null ) throw new DomainValidationException( "Argument date cannot be null." );
		this.date = date;
	}

	public void setExpectedHours( Duration expectedHours ) {
		if( expectedHours == null ) throw new DomainValidationException( "Argument expectedHours has to be not null and greater than 0." );
		this.expectedHours = expectedHours;
	}

	public void setNotes( String notes ) {
		this.notes = notes;
	}

	public void setProfile( Profile profile ) {
		if( profile == null ) throw new DomainValidationException( "Argument profile cannot be null." );
		this.profile = profile;
	}

	public LocalDate getDate() {
		if( date == null ) throw new DomainValidationException( "The property 'date' has not been properly initialized." );
		return date;
	}

	public Duration getExpectedHours() {
		return expectedHours;
	}

	public String getNotes() {
		return notes;
	}

	public Profile getProfile() {
		if( profile == null ) throw new DomainValidationException( "The property 'profile' has not been properly initialized." );
		return profile;
	}

	public List< DayEntering > getTableEntering() {
		if ( tableEntering == null )
			tableEntering = new ArrayList<>();
		
		return tableEntering;
	}

	public Duration getBalance() {
		return balance;
	}

	public void setBalance( Duration balance ) {
		this.balance = balance;
	}
	
}