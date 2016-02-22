package org.com.clockinclockout.domain;

import java.util.Date;

import org.springframework.util.Assert;

public class Email extends CommonDomain {

	private String address;
	private Date recordedTime;
	private Date confirmationDate;
	private String confirmationCode;
	private boolean primary;
	private User user;
	
	public Email(String address) {
		this(null, address);
	}
	
	public Email(Integer id, String address) {
		super(id);
		
		this.setAddress(address);
	}
	
	@Override
	public String toString() {
		return "Email [address=" + address 
				+ ", recordedTime=" + recordedTime
				+ ", confirmationDate=" + confirmationDate 
				+ ", confirmationCode=" + confirmationCode 
				+ ", primary=" + primary + "]";
	}

	public void setAddress(String address) {
		Assert.hasLength( address, "Argument address cannot be null nor empty." );
		this.address = address;
	}
	public void setRecordedTime(Date recordedTime) {
		Assert.notNull( recordedTime, "Argument recordedTime cannot be null." );
		this.recordedTime = recordedTime;
	}
	public void setConfirmationDate(Date confirmationDate) {
		Assert.notNull( confirmationDate, "Argument confirmationDate cannot be null." );
		this.confirmationDate = confirmationDate;
	}
	public void setConfirmationCode(String confirmationCode) {
		Assert.hasLength( confirmationCode, "Argument confirmationCode cannot be null nor empty." );
		this.confirmationCode = confirmationCode;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	public void setUser(User user) {
		Assert.notNull( user, "Argument user cannot be null." );
		this.user = user;
	}
	
	public String getAddress() {
		return address;
	}
	public Date getRecordedTime() {
		return recordedTime;
	}
	public Date getConfirmationDate() {
		return confirmationDate;
	}
	public String getConfirmationCode() {
		return confirmationCode;
	}
	public boolean isPrimary() {
		return primary;
	}
	public User getUser() {
		return user;
	}
}
