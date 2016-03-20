package com.clkio.test;

import java.time.Duration;

import org.junit.Test;
import org.springframework.util.Assert;

import com.clkio.common.DurationUtil;
import com.clkio.common.IntervalUtil;

public class TestDurationUtil {

	private void testFromString( String hours, String pattern ) {
		try {
			System.out.println( "Valid: hours[" + hours + "], pattern[" + pattern + "] = " + DurationUtil.fromString( hours, pattern ) );
		} catch ( IllegalStateException e ) {
			System.out.println( "Invalid: hours[" + hours + "], pattern[" + pattern + "]" );
		}
	}
	
	@Test
	public void testFromString() {
		for ( String pattern : new String[]{ "HH:mm:ss", "H:mm:ss", "H:m:ss", "H:m:s", "HH:mm", "H:mm", "H:m", "HH", "H" } ) {
			
			Assert.state( IntervalUtil.isHoursFormatValid( pattern ), "The 'pattern' is not a valid 'hours format'." );
			
			testFromString( "-99:5:59", pattern );
			testFromString( "-99", pattern );
			testFromString( "-08", pattern );
			testFromString( "08", pattern );
			testFromString( "8:00", pattern );
			testFromString( "8:00:00", pattern );
			testFromString( "08:00", pattern );
			testFromString( "08:00:00", pattern );
			testFromString( "1:1:1", pattern );
			testFromString( "-1:1:1", pattern );
			testFromString( "8:8", pattern );
			testFromString( "-8:0", pattern );
			testFromString( "21:21", pattern );
			testFromString( "-21:21", pattern );
			testFromString( "21:21:59", pattern );
			testFromString( "-21:21:59", pattern );
			
			System.out.println();
		}
		
	}
	
	private void testFromDuration( String duration, String pattern ) {
		System.out.println( "Duration string=[" + duration + "], pattern=[" + pattern + "]; " + DurationUtil.fromDuration( Duration.parse( duration ), pattern ) );
	}
	
	@Test
	public void testFromDuration() {
		for ( String pattern : new String[]{ "HH:mm:ss", "H:mm:ss", "H:m:ss", "H:m:s", "HH:mm", "H:mm", "H:m", "HH", "H" } ) {
			
			Assert.state( IntervalUtil.isHoursFormatValid( pattern ), "The 'pattern' is not a valid 'hours format'." );
			
			testFromDuration( "PT-99H-59M-59S" , pattern );
			testFromDuration( "PT-99H" , pattern );
			testFromDuration( "PT-8H" , pattern );
			testFromDuration( "PT8H" , pattern );
			testFromDuration( "PT8H" , pattern );
			testFromDuration( "PT8H" , pattern );
			testFromDuration( "PT21H21M" , pattern );
			testFromDuration( "PT-21H-21M" , pattern );
			testFromDuration( "PT21H21M59S" , pattern );
			testFromDuration( "PT-21H-21M-59S" , pattern );
			testFromDuration( "PT8H" , pattern );
			testFromDuration( "PT21H21M59S" , pattern );
			testFromDuration( "PT-21H-21M-59S" , pattern );
			testFromDuration( "PT-99H-5M-59S" , pattern );
			testFromDuration( "PT1H1M1S" , pattern );
			testFromDuration( "PT-1H-1M-1S" , pattern );
			testFromDuration( "PT21H21M" , pattern );
			testFromDuration( "PT-21H-21M" , pattern );
			testFromDuration( "PT8H8M" , pattern );
			testFromDuration( "PT-8H" , pattern );
			testFromDuration( "PT-99H" , pattern );
			
			System.out.println();
		}
	}
	
}
