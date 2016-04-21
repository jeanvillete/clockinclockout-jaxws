package com.clkio.domain;

import org.springframework.util.StringUtils;

import com.clkio.exception.DomainValidationException;

public class ManualEnteringReason extends CommonDomain {

	private Profile profile;
	private String reason;
	
	public ManualEnteringReason( Integer id ) {
		super( id );
	}
	
	public ManualEnteringReason(Profile profile, String reason) {
		this( null, profile, reason );
	}
	
	public ManualEnteringReason(Integer id, Profile profile, String reason) {
		super(id);
		
		this.setProfile(profile);
		this.setReason(reason);
	}

	public void setProfile(Profile profile) {
		if( profile == null ) throw new DomainValidationException( "Argument profile cannot be null." );
		this.profile = profile;
	}
	public void setReason(String reason) {
		if( !StringUtils.hasText( reason ) ) throw new DomainValidationException( "Argument reason cannot be null nor empty." );
		this.reason = reason;
	}	
	public Profile getProfile() {
		if( profile == null ) throw new DomainValidationException( "The property 'profile' has not been initialized yet." );
		return profile;
	}
	public String getReason() {
		if( !StringUtils.hasText( reason ) ) throw new DomainValidationException( "The property 'reason' has not been initialized yet." );
		return reason;
	}
}
