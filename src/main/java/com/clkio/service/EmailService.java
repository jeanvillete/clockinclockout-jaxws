package com.clkio.service;

import java.time.LocalDateTime;
import java.util.List;

import com.clkio.domain.Email;
import com.clkio.domain.EmailResetPassword;
import com.clkio.domain.NewEmailConfirmation;
import com.clkio.domain.NewUserEmailConfirmation;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;

public interface EmailService {

	void insert( Email email ) throws ValidationException, PersistenceException, ConflictException;
	
	void send( NewUserEmailConfirmation emailConfirmation );
	
	void send( NewEmailConfirmation newEmailConfirmation );
	
	void send( EmailResetPassword emailResetPassword );
	
	/**
	 * Checks if the emailAddress exists on the database.
	 * @param email
	 * @return
	 * @throws ValidationException 
	 */
	boolean exists( Email email ) throws ValidationException;
	
	void confirm( Email email ) throws ValidationException, PersistenceException;

	void delete( Email email ) throws ValidationException, PersistenceException, ConflictException;
	
	Email get( Email email ) throws ValidationException, PersistenceException;
	
	List< Email > list( User user ) throws ValidationException;
	
	/**
	 * This is meant for bring a synchronized instance from database based and the parameters 'emailAddress' and 'isPrimary',
	 * but behind scenes it also considers only email record that is already confirmed (CONFIRMATION_DATE NOT NULL).
	 * @param emailAddress
	 * @param isPrimary
	 * @return
	 * @throws ValidationException 
	 * @throws PersistenceException 
	 */
	Email getBy( String emailAddress, boolean isPrimary ) throws ValidationException, PersistenceException;
	
	List< Email > listPrimaryNotConfirmed( LocalDateTime date ) throws ValidationException;
	
	void deleteNotPrimaryNotConfirmed( LocalDateTime date ) throws ValidationException;
	
	void setAsPrimary( Email email ) throws ValidationException, PersistenceException, ConflictException;
}