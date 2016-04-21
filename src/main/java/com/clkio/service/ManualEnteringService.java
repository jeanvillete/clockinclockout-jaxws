package com.clkio.service;

import java.time.LocalDate;
import java.util.List;

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;

public interface ManualEnteringService {

	List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason ) throws ValidationException;

	List< ManualEntering > listBy( Day day ) throws ValidationException;
	
	ManualEntering get( Profile profile, ManualEntering manualEntering ) throws ValidationException, PersistenceException;
	
	void insert( ManualEntering manualEntering ) throws ValidationException, PersistenceException;

	void update( ManualEntering manualEntering ) throws ValidationException, PersistenceException;
	
	void delete( Profile profile, ManualEntering manualEntering ) throws ValidationException, PersistenceException;

	List< ManualEntering > list( Profile profile, LocalDate startDate, LocalDate endDate ) throws ValidationException;
}
