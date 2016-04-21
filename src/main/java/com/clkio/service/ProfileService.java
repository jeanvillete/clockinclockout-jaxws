package com.clkio.service;

import java.util.List;

import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;

public interface ProfileService {

	void insert( Profile profile ) throws ValidationException, PersistenceException, ConflictException;
	
	void update( Profile profile ) throws ValidationException, ConflictException, PersistenceException;
	
	List< Profile > listBy( User user ) throws ValidationException;
	
	void delete( Profile profile ) throws ValidationException, PersistenceException;
	
	Profile get( Profile profile ) throws ValidationException, PersistenceException;
	
	/**
	 * Check if already exists some profile record with a given 'description' for the given 'user'.
	 * @param description
	 * @param user
	 * @return
	 * @throws ValidationException 
	 */
	boolean exists( String description, User user ) throws ValidationException;
	
	/**
	 * Check if already exists some profile record with a given 'description' for the given 'user' for the 'id' different of the current one.
	 * @param description
	 * @param user
	 * @param id
	 * @return
	 * @throws ValidationException 
	 */
	boolean exists( String description, User user, Integer id ) throws ValidationException;
	
}
