package org.com.clockinclockout.domain;

import java.util.Date;

import org.springframework.util.Assert;

public class Day extends CommonDomain {

	private Date date;
	private Integer expectedHours;
	private String notes;
	private Profile profile;
	
	public Day(Date date, Integer expectedHours, String notes, Profile profile) {
		this(null, date, expectedHours, notes, profile);
	}
	
	public Day(Integer id, Date date, Integer expectedHours, String notes, Profile profile) {
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
		return date;
	}
	public Integer getExpectedHours() {
		return expectedHours;
	}
	public String getNotes() {
		return notes;
	}
	public Profile getProfile() {
		return profile;
	}
}