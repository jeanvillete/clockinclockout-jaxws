package com.clkio.service;

import java.util.List;

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;

public interface ManualEnteringService {

	List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason );

	List< ManualEntering > listBy( Day day );
	
	void insert( ManualEntering manualEntering );

	void delete( ManualEntering manualEntering );
	
}
