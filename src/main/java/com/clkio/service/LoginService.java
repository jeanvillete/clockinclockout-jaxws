package com.clkio.service;

import com.clkio.domain.User;

public interface LoginService {

	/**
	 * The service is responsable to get an user instance which has to be filled with 'emailAddress' and raw 'password' value.
	 * So the service hast to check whether exists the related record on database and if so return the synchronizied (from database) instance.
	 * 
	 * @param user
	 * @return
	 */
	User login( User user );
	
}
