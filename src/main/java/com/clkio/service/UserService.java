package com.clkio.service;

import com.clkio.domain.Email;
import com.clkio.domain.User;

public interface UserService {

	void insert( User user );
	
	User getBy( Email email );
	
	void delete( User user );

	boolean changePassword( User syncUser );
	
	/**
	 * Service invoked by a job that runs once a day looking for
	 * confirm email requests which were not met by the user.
	 */
	void cleanNotConfirmed();
}
