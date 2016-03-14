package com.clkio.service;

import java.util.Date;
import java.util.List;

import com.clkio.domain.Email;
import com.clkio.domain.EmailResetPassword;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.domain.NewUserEmailConfirmation;

public interface EmailService {

	void insert( Email email );
	
	void send( NewUserEmailConfirmation emailConfirmation );
	
	void send( NewEmailConfirmation newEmailConfirmation );
	
	void send( EmailResetPassword emailResetPassword );
	
	/**
	 * Checks if the emailAddress exists on the database.
	 * @param email
	 * @return
	 */
	boolean exists( Email email );
	
	void confirm( Email email );

	void delete( Email email );
	
	/**
	 * This is meant for bring a synchronized instance from database based and the parameters 'emailAddress' and 'isPrimary',
	 * but behind scenes it also considers only email record that is already confirmed (CONFIRMATION_DATE NOT NULL).
	 * @param emailAddress
	 * @param isPrimary
	 * @return
	 */
	Email getBy( String emailAddress, boolean isPrimary );
	
	List< Email > listPrimaryNotConfirmed( Date date );
	
	void deleteNotPrimaryNotConfirmed( Date date );
}