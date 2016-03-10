package com.clkio.service;

import com.clkio.domain.Email;
import com.clkio.domain.User;

public interface UserService {

	void insert( User user );
	
	User getBy( Email email );
	
	void delete( User user );

	boolean changePassword( User syncUser );
}
