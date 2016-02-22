package org.com.clockinclockout.service;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.EmailConfirmation;
import org.com.clockinclockout.domain.EmailResetPassword;

public interface EmailService {

	void insert( Email email );
	
	void send( EmailConfirmation email );

	void send( EmailResetPassword email );
	
	boolean exists( Email email );
	
	void confirm( Email email );
	
	Email get( String address );
	
	Email get( Email email );
}
