package org.com.clockinclockout.domain;

import java.util.Date;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class Day extends CommonDomain {

	private Date date;
	private Integer expectedHours;
	private String notes;
	private Profile profile;
	
	public Day( Integer id ) {
		super( id );
	}

	public Day( Date date, Integer expectedHours, String notes, Profile profile ) {
		this(null, date, expectedHours, notes, profile);
	}
	
	public Day( Integer id, Date date, Integer expectedHours, String notes, Profile profile ) {
		super(id);
		this.setDate(date);
		this.setExpectedHours(expectedHours);
		this.setNotes(notes);
		this.setProfile(profile);
	}
	
	public void setDate(Date date) {
		Assert.notNull( date, "Argument date cannot be null." );
		this.date = date;
	}
	public void setExpectedHours(Integer expectedHours) {
		Assert.state( expectedHours != null && expectedHours > 0, "Argument expectedHours has to be not null and greater than 0." );
		this.expectedHours = expectedHours;
	}
	public void setNotes(String notes) {
		Assert.hasLength( notes, "Argument notes cannot be null nor empty." );
		this.notes = notes;
	}
	public void setProfile(Profile profile) {
		Assert.notNull( profile, "Argument profile cannot be null." );
		this.profile = profile;
	}
	public Date getDate() {
		Assert.state( date != null, "The property 'date' has not been properly initialized." );
		return date;
	}
	public Integer getExpectedHours() {
		Assert.state( expectedHours != null && expectedHours > 0, "The property 'expectedHours' has not been properly initialized." );
		return expectedHours;
	}
	public String getNotes() {
		Assert.state( StringUtils.hasText( notes ), "The property 'notes' has not been properly initialized." );
		return notes;
	}
	public Profile getProfile() {
		Assert.state( profile != null, "The property 'profile' has not been properly initialized." );
		return profile;
	}
}