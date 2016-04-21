package com.clkio.service;

import java.util.List;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;

public interface ManualEnteringReasonService {

	void insert( ManualEnteringReason manualEnteringReason ) throws PersistenceException, ValidationException, ConflictException;
	
	/**
	 * Method responsable to check if already exists some 'manualEnteringReason' record for a given
	 * 'reason' for a given 'profile'.
	 * Method used preferably by the 'insert' one.
	 * 
	 * @param reason
	 * @param profile
	 * @return
	 */
	boolean exists( String reason, Profile profile );

	void update( ManualEnteringReason manualEnteringReason ) throws ValidationException, ConflictException, PersistenceException;
	
	/**
	 * Method responsable to check if already exists some 'manualEnteringReason' record for a given
	 * reason for a given 'profile' for an 'id' different of the current one.
	 * Method used preferably by the 'update' one.
	 * 
	 * @param reason
	 * @param profile
	 * @param id
	 * @return
	 */
	boolean exists( String reason, Profile profile, Integer id );
	
	void delete( ManualEnteringReason manualEnteringReason ) throws PersistenceException, ValidationException;
	
	List< ManualEnteringReason > list( Profile profile ) throws ValidationException;
}
