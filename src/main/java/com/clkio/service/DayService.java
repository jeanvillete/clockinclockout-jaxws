package com.clkio.service;

import java.util.List;

import com.clkio.domain.Day;
import com.clkio.domain.Profile;

public interface DayService {

	void insert( Day day );
	
	void delete( Day day );

	List< Day > listBy( Profile profile );
	
}
