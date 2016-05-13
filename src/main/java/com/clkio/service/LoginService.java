package com.clkio.service;

import com.clkio.domain.User;
import com.clkio.exception.PersistenceException;
import com.clkio.exception.UnauthorizedException;
import com.clkio.exception.ValidationException;

public interface LoginService {

	/**
	 * The service is responsable to get an user instance which has to be filled with 'emailAddress' and raw 'password' value.
	 * So the service has to check whether exists the related record on database and if so return a unique code that identifies the login request.
	 * The user is going to use this code to make proper requests.
	 * 
	 * @param user
	 * @param ip
	 * @return
	 * @throws ValidationException 
	 * @throws UnauthorizedException 
	 * @throws PersistenceException 
	 */
	String login( User user, String ip ) throws ValidationException, UnauthorizedException, PersistenceException;

	/**
	 * Service responsable to check if the provided 'code' is valid for login.
	 * @param code
	 * @return
	 * @throws ValidationException 
	 */
	boolean check( String code ) throws ValidationException;
	
	void logout( String code ) throws ValidationException, PersistenceException;
	
	/**
	 * Service responsible to turn invalid all login records related to the provided 'user' parameter.
	 * @param user
	 * @throws ValidationException 
	 */
	void setAsInvalid( User user ) throws ValidationException;
}
