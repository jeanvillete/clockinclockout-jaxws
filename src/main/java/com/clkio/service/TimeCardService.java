package com.clkio.service;

import java.time.Duration;
import java.time.LocalDate;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;

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
	 */
	void punchClock( Profile profile, String clock );
	
	void insert( Profile profile, ClockinClockout clockinClockout );

	void update( Profile profile, ClockinClockout clockinClockout );

	void delete( Profile profile, ClockinClockout clockinClockout );
	
	void insert( Profile profile, ManualEntering manualEntering );

	void update( Profile profile, ManualEntering manualEntering );

	void delete( Profile profile, ManualEntering manualEntering );
	
	void setNotes( Profile profile, LocalDate date, String text );

	void setExpectedHours( Profile profile, LocalDate date, Duration expectedHours );
}
