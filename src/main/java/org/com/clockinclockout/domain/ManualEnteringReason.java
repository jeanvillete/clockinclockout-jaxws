package org.com.clockinclockout.domain;

import org.springframework.util.Assert;

public class ManualEnteringReason extends CommonDomain {

	private Profile profile;
	private String reason;
	
	public ManualEnteringReason(Profile profile, String reason) {
		this( null, profile, reason );
	}
	
	public ManualEnteringReason(Integer id, Profile profile, String reason) {
		super(id);
		
		this.setProfile(profile);
		this.setReason(reason);
	}
	
	public void setProfile(Profile profile) {
		Assert.notNull( profile, "Argument profile cannot be null." );
		this.profile = profile;
	}
	public void setReason(String reason) {
		Assert.hasLength( reason, "Argument reason cannot be null nor empty." );
		this.reason = reason;
	}	
	public Profile getProfile() {
		return profile;
	}
	public String getReason() {
		return reason;
	}
}
