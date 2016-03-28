package com.clkio.service;

import java.util.List;

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;

public interface ManualEnteringService {

	List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason );

	List< ManualEntering > listBy( Day day );
	
	ManualEntering get( Profile profile, ManualEntering manualEntering );
	
	void insert( ManualEntering manualEntering );

	void update( ManualEntering manualEntering );
	
	void delete( Profile profile, ManualEntering manualEntering );
	
}
