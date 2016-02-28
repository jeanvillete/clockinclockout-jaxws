package org.com.clockinclockout.service;

import java.util.List;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.ManualEntering;
import org.com.clockinclockout.domain.ManualEnteringReason;

public interface ManualEnteringService {

	List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason );

	List< ManualEntering > listBy( Day day );
	
	void delete( ManualEntering manualEntering );
	
}
