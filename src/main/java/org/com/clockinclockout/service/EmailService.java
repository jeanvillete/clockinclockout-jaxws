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
	
	/**
	 * This is meant for bring a synchronized instance from database based and the parameters 'emailAddress' and 'isPrimary',
	 * but behind scenes it also considers only email record that is already confirmed (CONFIRMATION_DATE NOT NULL).
	 * @param emailAddress
	 * @param isPrimary
	 * @return
	 */
	Email getBy( String emailAddress, boolean isPrimary );
}