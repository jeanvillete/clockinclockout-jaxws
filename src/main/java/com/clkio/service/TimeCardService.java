package com.clkio.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;
import com.clkio.domain.TimeCard;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;

public interface TimeCardService {

	/**
	 * Service responsible to decide whether the 'clock' provided as argument refers to a 'clockin' or 'clockout'
	 * 	record.
	 * The provided 'clock' has to meet the pattern 'dateFormat' with the 'hoursFormat' appended,
	 * 	e.g; if for the supplied profile has dateFormat = 'yyyy-MM-dd' and hoursFormat = 'H:mm', so the resulting 
	 * 	pattern is 'yyyy-MM-ddH:mm'.
	 * 
	 * @param profile
	 * @param clock
	 * @return
	 * @throws ValidationException
	 * @throws PersistenceException
	 * @throws ConflictException
	 */
	ClockinClockout punchClock( Profile profile, String clock ) throws ValidationException, PersistenceException, ConflictException;
	
	void insert( Profile profile, ClockinClockout clockinClockout ) throws ValidationException, PersistenceException, ConflictException;

	void update( Profile profile, ClockinClockout clockinClockout ) throws ValidationException, PersistenceException;

	void delete( Profile profile, ClockinClockout clockinClockout ) throws ValidationException, PersistenceException;
	
	void insert( Profile profile, ManualEntering manualEntering ) throws ValidationException, PersistenceException, ConflictException;

	void update( Profile profile, ManualEntering manualEntering ) throws ValidationException, PersistenceException;

	void delete( Profile profile, ManualEntering manualEntering ) throws ValidationException, PersistenceException;
	
	void setNotes( Profile profile, LocalDate date, String text ) throws ValidationException, PersistenceException, ConflictException;

	void setExpectedHours( Profile profile, LocalDate date, Duration expectedHours ) throws ValidationException, PersistenceException, ConflictException;
	
	Duration getTotalTime( Profile profile ) throws ValidationException;
	
	Duration getTotalTime( Profile profile, LocalDate until ) throws ValidationException;
	
	/**
	 * This service aims retrieve a whole timecard for the entire month supplied as parameter.
	 * @param profile
	 * @param month
	 * @return
	 * @throws ValidationException
	 */
	TimeCard getTimeCard( Profile profile, YearMonth month ) throws ValidationException;

	/**
	 * This service aims retrieve a timecard containing only the "day/date" with the updated data from the database.
	 * @param profile
	 * @param date
	 * @return
	 * @throws ValidationException
	 */
	TimeCard getTimeCard( Profile profile, LocalDate date ) throws ValidationException;
}
