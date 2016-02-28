package org.com.clockinclockout.service;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.User;

public interface UserService {

	void insert( User user );
	
	User getBy( Email email );
	
	void delete( User user );

	boolean changePassword( User syncUser );
}
