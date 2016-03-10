package com.clkio.service;

import java.util.List;

import com.clkio.domain.Profile;
import com.clkio.domain.User;

public interface ProfileService {

	void insert( Profile profile );
	
	List< Profile > listBy( User user );
	
	void delete( Profile profile );
	
}
