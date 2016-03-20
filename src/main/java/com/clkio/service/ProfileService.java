package com.clkio.service;

import java.util.List;

import com.clkio.domain.Profile;
import com.clkio.domain.User;

public interface ProfileService {

	void insert( Profile profile );
	
	void update( Profile profile );
	
	List< Profile > listBy( User user );
	
	void delete( Profile profile );
	
	Profile get( Profile profile );
	
	/**
	 * Check if already exists some profile record with a given 'description' for the given 'user'.
	 * @param description
	 * @param user
	 * @return
	 */
	boolean exists( String description, User user );
	
	/**
	 * Check if already exists some profile record with a given 'description' for the given 'user' for the given 'id'.
	 * @param description
	 * @param user
	 * @param id
	 * @return
	 */
	boolean exists( String description, User user, Integer id );
	
}
