package com.clkio.domain;

import java.time.Duration;

import org.springframework.util.Assert;

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
		Assert.hasLength( description, "Argument description cannot be null nor empty." );
		this.description = description;
	}
	public void setProfile(Profile profile) {
		Assert.notNull( profile, "Argument profile cannot be null." );
		this.profile = profile;
	}
	public void setTimeInterval( Duration timeInterval ) {
		Assert.state( timeInterval != null, "Argument timeInterval has to be not null." );
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
