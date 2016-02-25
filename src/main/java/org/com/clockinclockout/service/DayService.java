package org.com.clockinclockout.service;

import java.util.List;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.Profile;

public interface DayService {

	void insert( Day day );
	
	void delete( Day day );

	List< Day > listBy( Profile profile );
	
}
