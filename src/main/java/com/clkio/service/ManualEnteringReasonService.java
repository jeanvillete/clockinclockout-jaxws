package com.clkio.service;

import java.util.List;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;

public interface ManualEnteringReasonService {

	void insert( ManualEnteringReason manualEnteringReason );
	
	void delete( ManualEnteringReason manualEnteringReason );
	
	List< ManualEnteringReason > list( Profile profile );
}
