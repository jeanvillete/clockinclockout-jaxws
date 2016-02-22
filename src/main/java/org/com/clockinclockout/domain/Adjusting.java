package org.com.clockinclockout.domain;

import org.springframework.util.Assert;

public class Adjusting extends CommonDomain {

	private String description;
	private Integer timeInterval;
	private Profile profile;
	
	public Adjusting(String description, Integer timeInterval, Profile profile) {
		this(null, description, timeInterval, profile);
	}
	
	public Adjusting(Integer id, String description, Integer timeInterval, Profile profile) {
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
	public void setTimeInterval(Integer timeInterval) {
		Assert.state( timeInterval != null && timeInterval > 0, "Argument timeInterval has to be not null and greater than 0." );
		this.timeInterval = timeInterval;
	}
	public String getDescription() {
		return description;
	}
	public Profile getProfile() {
		return profile;
	}

	public Integer getTimeInterval() {
		return timeInterval;
	}
}
