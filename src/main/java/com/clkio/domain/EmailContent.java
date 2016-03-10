package com.clkio.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public abstract class EmailContent {

	private static final SimpleDateFormat SDF = new SimpleDateFormat( "dd_MM_yyyy_HH:mm:ss_z" );

	private Email email;
	private String encodedEmailAddress;
	private String subject;
	private String hash;
	
	public EmailContent( Email email ) {
		super();
		
		Assert.notNull( email, "Argument email cannot be null.");
		
		this.email = email;
		
		this.hash = this.email.getAddress() + SDF.format( this.email.getRecordedTime() );
		this.hash = new BCryptPasswordEncoder().encode( this.hash );
		try {
			this.encodedEmailAddress = URLEncoder.encode(email.getAddress(), "UTF-8");
			this.hash = URLEncoder.encode( this.hash, "UTF-8");
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
		Assert.hasText( hash, "The property 'hash' has not been initialized yet." );
		return hash;
	}
}
