package org.com.clockinclockout.service;

import java.util.List;

import org.com.clockinclockout.domain.ManualEnteringReason;
import org.com.clockinclockout.domain.Profile;

public interface ManualEnteringReasonService {

	void insert( ManualEnteringReason manualEnteringReason );
	
	void delete( ManualEnteringReason manualEnteringReason );
	
	List< ManualEnteringReason > listBy( Profile profile );
}
