package com.clkio.domain;

import java.time.Duration;

import org.springframework.util.StringUtils;

import com.clkio.exception.DomainValidationException;

public class Adjusting extends CommonDomain {

	private String description;
	private Duration timeInterval;
	private Profile profile;
	
	public Adjusting( Integer id ) {
		super( id );
	}
	
	public Adjusting( String description, Duration timeInterval, Profile profile ) {
		this(null, description, timeInterval, profile);
	}
	
	public Adjusting( Integer id, String description, Duration timeInterval, Profile profile ) {
		super( id );
		
		this.setDescription(description);
		this.setTimeInterval(timeInterval);
		this.setProfile(profile);
	}
	
	public void setDescription(String description) {
		if ( !StringUtils.hasLength( description ) ) throw new DomainValidationException( "Argument description cannot be null nor empty." );
		this.description = description;
	}
	public void setProfile(Profile profile) {
		if( profile == null ) throw new DomainValidationException( "Argument profile cannot be null." );
		this.profile = profile;
	}
	public void setTimeInterval( Duration timeInterval ) {
		if( timeInterval == null ) throw new DomainValidationException( "Argument timeInterval has to be not null." );
		this.timeInterval = timeInterval;
	}
	public String getDescription() {
		return description;
	}
	public Profile getProfile() {
		return profile;
	}

	public Duration getTimeInterval() {
		return timeInterval;
	}
}
