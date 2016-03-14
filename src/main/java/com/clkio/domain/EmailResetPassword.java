package com.clkio.domain;

public class EmailResetPassword extends EmailContent {

	public EmailResetPassword( Email email ) {
		super( "velocity-reset-password_", email );
	}

}
