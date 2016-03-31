package com.clkio.service;

import java.time.LocalDate;
import java.util.List;

import com.clkio.domain.Day;
import com.clkio.domain.Profile;

public interface DayService {

	void insert( Day day );

	void update( Day day );
	
	void delete( Day day );

	List< Day > listBy( Profile profile );

	Day get( Profile profile, LocalDate localDateDay );

	Day get( Day day );

	List< Day > list( Profile profile, LocalDate startDate, LocalDate endDate );
	
}
