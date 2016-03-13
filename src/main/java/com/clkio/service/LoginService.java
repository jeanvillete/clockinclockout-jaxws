package com.clkio.service;

import com.clkio.domain.User;

public interface LoginService {

	/**
	 * The service is responsable to get an user instance which has to be filled with 'emailAddress' and raw 'password' value.
	 * So the service has to check whether exists the related record on database and if so return a unique code that identifies the login request.
	 * The user is going to use this code to make proper requests.
	 * 
	 * @param user
	 * @param ip
	 * @return
	 */
	String login( User user, String ip );

	/**
	 * Service responsable to check if the provided 'code' is valid for login.
	 * @param code
	 * @return
	 */
	boolean check( String code );
}
