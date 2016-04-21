package com.clkio.domain;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.clkio.exception.DomainValidationException;

public class Email extends CommonDomain {

	private String address;
	private LocalDateTime recordedTime;
	private LocalDateTime confirmationDate;
	private String confirmationCode;
	private boolean primary;
	private User user;

	public Email( String address ) {
		this( null, address );
	}

	public Email( Integer id ) {
		super( id );
	}

	public Email( Integer id, String address ) {
		super( id );

		this.setAddress( address );
	}

	@Override
	public String toString() {
		return "Email [address=" + address + ", recordedTime=" + recordedTime + ", confirmationDate=" + confirmationDate
				+ ", confirmationCode=" + confirmationCode + ", primary=" + primary + "]";
	}

	public void setAddress( String address ) {
		if ( !StringUtils.hasLength( address ) ) throw new DomainValidationException( "Argument address cannot be null nor empty." );
		this.address = address;
	}

	public void setRecordedTime( LocalDateTime recordedTime ) {
		if( recordedTime == null ) throw new DomainValidationException( "Argument recordedTime cannot be null." );
		this.recordedTime = recordedTime;
	}

	public void setConfirmationDate( LocalDateTime confirmationDate ) {
		this.confirmationDate = confirmationDate;
	}

	public void setConfirmationCode( String confirmationCode ) {
		if ( !StringUtils.hasLength( confirmationCode ) ) throw new DomainValidationException( "Argument confirmationCode cannot be null nor empty." );
		this.confirmationCode = confirmationCode;
	}

	public void setPrimary( boolean primary ) {
		this.primary = primary;
	}

	public void setUser( User user ) {
		if( user == null ) throw new DomainValidationException( "Argument user cannot be null." );
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public LocalDateTime getRecordedTime() {
		if( recordedTime == null ) throw new DomainValidationException( "Argument 'recordedTime' cannot be null." );
		return recordedTime;
	}

	public LocalDateTime getConfirmationDate() {
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
