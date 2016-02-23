package org.com.clockinclockout.service;

import java.util.List;

import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;

public interface ProfileService {

	void insert( Profile profile );
	
	List< Profile > listBy( User user );
	
	void delete( Profile profile );
	
}
