package org.com.clockinclockout.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.util.Assert;

public abstract class EmailContent {

	private Email email;
	private String encodedEmailAddress;
	private String subject;
	
	public EmailContent( Email email ) {
		super();
		
		Assert.notNull( email, "Argument email cannot be null.");
		
		this.email = email;
		try {
			this.encodedEmailAddress = URLEncoder.encode(email.getAddress(), "UTF-8");
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
	
}
