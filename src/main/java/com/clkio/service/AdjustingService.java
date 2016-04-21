package com.clkio.service;

import java.util.List;

import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;

public interface AdjustingService {

	void insert( Adjusting adjusting ) throws ValidationException, ConflictException, PersistenceException;
	
	/**
	 * Method responsable to check if already exists some 'adjusting' record for a given
	 * description for a given 'profile'.
	 * Method used preferably by the 'insert' one.
	 * @param description
	 * @param profile
	 * @return
	 */
	boolean exists( String description, Profile profile );
	
	void update( Adjusting adjusting ) throws ValidationException, ConflictException, PersistenceException;

	/**
	 * Method responsable to check if already exists some 'adjusting' record for a given
	 * description for a given 'profile' for an 'id' different of the current one.
	 * Method used preferably by the 'update' one.
	 * @param description
	 * @param profile
	 * @param id
	 * @return
	 */
	boolean exists( String description, Profile profile, Integer id );
	
	void delete( Adjusting adjusting ) throws ValidationException, PersistenceException;
	
	List< Adjusting > list( Profile profile ) throws ValidationException;

}
