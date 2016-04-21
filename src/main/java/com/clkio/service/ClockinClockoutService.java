package com.clkio.service;

import java.time.LocalDate;
import java.util.List;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;

public interface ClockinClockoutService {

	void insert( ClockinClockout clockinClockout ) throws ValidationException, PersistenceException;
	
	void update( ClockinClockout clockinClockout ) throws ValidationException, PersistenceException;
	
	void delete( Profile profile, ClockinClockout clockinClockout ) throws ValidationException, PersistenceException;
	
	List< ClockinClockout > listBy( Day day ) throws ValidationException;

	/**
	 * Service responsible to bring the most new 'clockinClockout' record from
	 * 	data base based on the provided 'day' parameter.
	 * Note: It is possible retrieve a 'null' reference if no record is found.
	 * @param day
	 * @return
	 * @throws ValidationException 
	 * @throws PersistenceException 
	 */
	ClockinClockout getNewest( Day day ) throws ValidationException, PersistenceException;

	ClockinClockout get( Profile profile, ClockinClockout clockinClockout ) throws PersistenceException, ValidationException;

	List< ClockinClockout > list( Profile profile, LocalDate startDate, LocalDate endDate ) throws ValidationException;
}
