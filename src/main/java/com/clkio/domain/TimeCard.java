package com.clkio.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.clkio.exception.DomainValidationException;

public class TimeCard {

	private HashMap< LocalDate, Day > days;
	private String totalTime;
	private String totalTimeMonthly;
	
	public TimeCard( LocalDate date ) {
		if( date == null) throw new DomainValidationException( "Argument 'date' is mandatory." );
		days = new HashMap< LocalDate, Day >();
		days.put( date, new Day( date ) );
	}
	
	public TimeCard( YearMonth month ) {
		if( month == null) throw new DomainValidationException( "Argument 'month' is mandatory." );
		days = new HashMap< LocalDate, Day >();
		
		LocalDate endOfMonth = month.atEndOfMonth();
		for ( LocalDate date = month.atDay( 1 ); !date.isAfter( endOfMonth ) ; date = date.plusDays( 1 ) )
			days.put( date, new Day( date ) );
	}
	
	public HashMap< LocalDate, Day > getDays() {
		return days;
	}
	
	public List< Day > getDaysSorted() {
		List< Day > days = new ArrayList< Day >( getDays().values() );
		days.sort( new Comparator< Day >() {
			/**
			 * Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second
			 */
			@Override
			public int compare( Day o1, Day o2 ) {
				LocalDate d1 = o1.getDate(), d2 = o2.getDate();
				return d1.equals( d2 ) ? 0 : d1.isBefore( d2 ) ? -1 : 1;
			}
		} );
		return days;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public String getTotalTimeMonthly() {
		return totalTimeMonthly;
	}

	public void setTotalTime( String totalTime ) {
		this.totalTime = totalTime;
	}

	public void setTotalTimeMonthly( String totalTimeMonthly ) {
		this.totalTimeMonthly = totalTimeMonthly;
	}
	
}
