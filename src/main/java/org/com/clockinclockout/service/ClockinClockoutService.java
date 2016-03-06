package org.com.clockinclockout.service;

import java.util.List;

import org.com.clockinclockout.domain.ClockinClockout;
import org.com.clockinclockout.domain.Day;

public interface ClockinClockoutService {

	void insert( ClockinClockout clockinClockout );
	
	void delete( ClockinClockout clockinClockout );
	
	List< ClockinClockout > listBy( Day day );
	
}
