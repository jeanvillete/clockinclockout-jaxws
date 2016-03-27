package com.clkio.domain;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

public class RequestResetPassword extends CommonDomain {

	private User user;
	private String requestCodeValue;
	private LocalDateTime requestDate;
	private LocalDateTime confirmationDate;
	private String confirmationCodeValue;
	private LocalDateTime changeDate;

	private transient String newPassword;

	public RequestResetPassword( User user ) {
		this( null, user );
	}

	public RequestResetPassword( Integer id, User user ) {
		super( id );
		this.setUser( user );
	}

	public void setUser( User user ) {
		Assert.notNull( user, "Argument user cannot be null." );
		this.user = user;
	}

	public void setRequestCodeValue( String requestCodeValue ) {
		Assert.hasLength( requestCodeValue, "Argument requestCodeValue cannot be null nor empty." );
		this.requestCodeValue = requestCodeValue;
	}

	public void setConfirmationDate( LocalDateTime confirmationDate ) {
		Assert.notNull( confirmationDate, "Argument confirmationDate cannot be null." );
		this.confirmationDate = confirmationDate;
	}

	public void setConfirmationCodeValue( String confirmationCodeValue ) {
		Assert.hasLength( confirmationCodeValue, "Argument confirmationCodeValue cannot be null nor empty." );
		this.confirmationCodeValue = confirmationCodeValue;
	}

	public void setChangeDate( LocalDateTime changeDate ) {
		Assert.notNull( changeDate, "Argument changeDate cannot be null." );
		this.changeDate = changeDate;
	}

	public User getUser() {
		Assert.notNull( user, "Argument user has not been properly provided yet." );
		return user;
	}

	public String getRequestCodeValue() {
		return requestCodeValue;
	}

	public LocalDateTime getConfirmationDate() {
		return confirmationDate;
	}

	public String getConfirmationCodeValue() {
		return confirmationCodeValue;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate( LocalDateTime requestDate ) {
		this.requestDate = requestDate;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword( String newPassword ) {
		Assert.hasText( newPassword );
		this.newPassword = newPassword;
	}
}