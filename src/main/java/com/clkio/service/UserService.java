package com.clkio.service;

import com.clkio.domain.Email;
import com.clkio.domain.User;

public interface UserService {

	void insert( User user );
	
	User getBy( Email email );
	
	/**
	 * The goal of this service is retrieve a proper User instance syncronyzed with database.
	 * 
	 * @param loginCode
	 * @return
	 */
	User getBy( String loginCode );
	
	void delete( User user );

	boolean changePassword( User syncUser );
	
	/**
	 * Service invoked by a job that runs once a day looking for
	 * confirm email requests which were not met by the user.
	 */
	void cleanNotConfirmed();
}
