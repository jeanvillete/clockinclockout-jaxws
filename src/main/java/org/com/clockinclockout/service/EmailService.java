package org.com.clockinclockout.service;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.EmailConfirmation;
import org.com.clockinclockout.domain.EmailResetPassword;

public interface EmailService {

	void insert( Email email );
	
	void send( EmailConfirmation email );

	void send( EmailResetPassword email );
	
	/**
	 * Checks if the emailAddress exists on the database.
	 * @param email
	 * @return
	 */
	boolean exists( Email email );
	
	void confirm( Email email );

	/**
	 * Service invoked by a job that runs once a day looking for
	 * confirm email requests which were not met by the user.
	 */
	void cleanNotConfirmed();
	
	void delete( Email email );
	
	Email getBy( String emailAddress, boolean isPrimary );
}