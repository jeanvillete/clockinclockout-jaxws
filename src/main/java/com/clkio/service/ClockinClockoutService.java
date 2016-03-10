package com.clkio.service;

import java.util.List;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;

public interface ClockinClockoutService {

	void insert( ClockinClockout clockinClockout );
	
	void delete( ClockinClockout clockinClockout );
	
	List< ClockinClockout > listBy( Day day );
	
}
