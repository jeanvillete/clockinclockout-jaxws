package com.clkio.service;

import java.util.List;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;

public interface ManualEnteringReasonService {

	void insert( ManualEnteringReason manualEnteringReason );
	
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

	void update( ManualEnteringReason manualEnteringReason );
	
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
	
	void delete( ManualEnteringReason manualEnteringReason );
	
	List< ManualEnteringReason > list( Profile profile );
}
