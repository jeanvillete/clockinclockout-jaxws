package com.clkio.service;

import java.time.LocalDate;
import java.util.List;

import com.clkio.domain.Day;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;

public interface DayService {

	void insert( Day day ) throws PersistenceException, ValidationException;

	void update( Day day ) throws ValidationException, PersistenceException;
	
	void delete( Day day ) throws ValidationException, PersistenceException;

	List< Day > listBy( Profile profile ) throws ValidationException;

	Day get( Profile profile, LocalDate localDateDay ) throws ValidationException, PersistenceException;

	Day get( Day day ) throws ValidationException, PersistenceException;

	List< Day > list( Profile profile, LocalDate startDate, LocalDate endDate ) throws ValidationException;
	
}
