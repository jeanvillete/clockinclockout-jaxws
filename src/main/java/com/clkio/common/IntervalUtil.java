package com.clkio.common;

import org.springframework.util.Assert;

abstract public class IntervalUtil {

	private static final String HOURS_FORMAT_REGEX = "^[H]{1,2}(\\:[m]{1,2})?(\\:[m]{1,2}\\:[s]{1,2})?$";
	private static final String DATE_FORMAT_REGEX = "^([d]{1,2}[M]{0,2}[y]{0,4})$|"
											+ "^([y]{0,4}[d]{1,2}[M]{0,2})$|"
											+ "^([y]{0,4}[M]{0,2}[d]{1,2})$|"
											+ "^([y]{0,4}[d]{1,2}[M]{0,2})$|"
											+ "^([d]{1,2}[y]{0,4}[M]{0,2})$|"
											+ "^([M]{0,2}[y]{0,4}[d]{1,2})$|"
											+ "^([M]{0,2}[d]{1,2}[y]{0,4})$";
	private static final String HOURS_REGEX = "^\\-?\\d{1,2}(\\:[0-5]\\d)?(\\:[0-5]\\d)?$";
	
	
	/**
	 * Method responsable to check whether the 'hoursFormat' provided by the user is valid to the application. 
	 * @param hoursFormat
	 * @return
	 */
	public static boolean isHoursFormatValid( String hoursFormat ) {
		Assert.hasText( hoursFormat, "Argument 'hoursFormat' is mandatory." );
		return hoursFormat.matches( HOURS_FORMAT_REGEX );
	}
	
	/**
	 * Method responsable to check whether a provided 'hour' value is valid.
	 * @param hour
	 * @return
	 */
	public static boolean isHoursValid( String hour ) {
		Assert.hasText( hour, "Argument 'hour' is mandatory." );
		return hour.matches( HOURS_REGEX );
	}
	
	/**
	 * Method responsable to check whether the 'dateFormat' provided by the user is valid to the application.
	 * @param dateFormat
	 * @return
	 */
	public static boolean isDateFormatValid( String dateFormat ) {
		Assert.hasText( dateFormat, "Argument 'dateFormat' is mandatory." );
		dateFormat = dateFormat.replaceAll( "\\s|\\/|\\-", "" );
		return dateFormat.matches( DATE_FORMAT_REGEX );
	}

}
