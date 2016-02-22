package org.com.clockinclockout.domain;

public class EmailResetPassword extends EmailContent {

	private String hash;
	
	public EmailResetPassword( Email email ) {
		super( email );
	}
	
	public String getHash() {
		return hash;
	}

}
