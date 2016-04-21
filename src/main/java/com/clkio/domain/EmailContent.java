package com.clkio.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.clkio.exception.DomainValidationException;

public abstract class EmailContent {

	private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern( "dd_MM_yyyy_HH:mm:ss" );

	private Email email;
	private String encodedEmailAddress;
	private String subject;
	private String hash;
	private String encodedHash;
	private String velocityResource;
	
	public EmailContent( String velocityResource, Email email ) {
		super();
		
		if( !StringUtils.hasText( velocityResource ) ) throw new DomainValidationException( "Argument 'velocityResource' is mandatory." );
		if( email == null ) throw new DomainValidationException( "Argument email cannot be null.");
		
		this.email = email;
		this.velocityResource = velocityResource + email.getUser().getLocale().getLanguage().toLowerCase() + ".vm";
		
		this.hash = this.email.getAddress() + DTF.format( this.email.getRecordedTime() );
		this.hash = new BCryptPasswordEncoder().encode( this.hash );
		try {
			this.encodedEmailAddress = URLEncoder.encode(email.getAddress(), "UTF-8");
			this.encodedHash = URLEncoder.encode( this.hash, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException( e );
		}
	}

	public Email getEmail() {
		return email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEncodedEmailAddress() {
		return encodedEmailAddress;
	}

	public String getHash() {
		if ( !StringUtils.hasText( hash ) ) throw new DomainValidationException( "The property 'hash' has not been initialized yet." );
		return hash;
	}

	public String getEncodedHash() {
		if ( !StringUtils.hasText( encodedHash ) ) throw new DomainValidationException( "The property 'encodedHash' has not been initialized yet." );
		return encodedHash;
	}

	public String getVelocityResource() {
		return velocityResource;
	}
}
