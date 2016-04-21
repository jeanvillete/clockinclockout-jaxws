package com.clkio.service;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;

public interface UserService {

	void insert( User user ) throws ConflictException, ValidationException, PersistenceException;
	
	User getBy( Email email ) throws PersistenceException, ValidationException;
	
	/**
	 * The goal of this service is retrieve a proper User instance syncronyzed with database.
	 * 
	 * @param loginCode
	 * @return
	 * @throws PersistenceException 
	 * @throws ValidationException 
	 */
	User getBy( String loginCode ) throws PersistenceException, ValidationException;
	
	void delete( User user ) throws ValidationException, PersistenceException;

	boolean changePassword( User syncUser ) throws ValidationException;
	
	/**
	 * Service invoked by a job that runs once a day looking for
	 * confirm email requests which were not met by the user.
	 * @throws ValidationException 
	 * @throws ConflictException 
	 * @throws PersistenceException 
	 */
	void cleanNotConfirmed() throws ValidationException, PersistenceException, ConflictException;
}
