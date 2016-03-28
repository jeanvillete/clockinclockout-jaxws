package com.clkio.service;

import java.util.List;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.Profile;

public interface ClockinClockoutService {

	void insert( ClockinClockout clockinClockout );
	
	void update( ClockinClockout clockinClockout );
	
	void delete( Profile profile, ClockinClockout clockinClockout );
	
	List< ClockinClockout > listBy( Day day );

	/**
	 * Service responsible to bring the most new 'clockinClockout' record from
	 * 	data base based on the provided 'day' parameter.
	 * Note: It is possible retrieve a 'null' reference if no record is found.
	 * @param day
	 * @return
	 */
	ClockinClockout getNewest( Day day );

	ClockinClockout get( Profile profile, ClockinClockout clockinClockout );

}
