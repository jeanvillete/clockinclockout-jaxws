package com.clkio.common;

import java.sql.SQLException;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.postgresql.util.PGInterval;
import org.springframework.util.Assert;

abstract public class DurationUtil {

	/**
	 * Util method responsable to translate properly a Duration string representation to Postgres Interval datatype.
	 * @param duration
	 * @return
	 */
	public static PGInterval durationToPG( Duration duration ) {
		Assert.notNull( duration, "The argument 'duration' is mandatory." );
		try {
			return duration == null ? null : new PGInterval( duration.toString().replace( "PT", "" ).replace( "H", " hour " ).replace( "M", " min " ).replace( "S", " sec " ).trim() );
		} catch ( SQLException e ) {
			throw new RuntimeException( e );
		}
	}
	
	/**
	 * Method responsable to get a Duration instance based on a String representating hours.
	 * @return
	 */
	public static Duration fromString( String hours ) {
		Assert.hasText( hours, "The argument 'hours' is mandatory." );
		Assert.state( IntervalUtil.isHoursValid( hours ), "The provided value for 'hours' is not a valid format." );
		Matcher matcher = Pattern.compile( "^(\\-?)(\\d{1,2})(\\:\\d{1,2})?(\\:\\d{1,2})?$" ).matcher( hours );
		if ( !matcher.find() ) throw new IllegalStateException( "The provided 'hours' doesn't match the minimum requirement to get a Durable instance." );
		StringBuilder _return = new StringBuilder( "PT" );
		String sign = ( sign = matcher.group( 1 ) ) != null ? sign : "";
		_return.append( ( hours = matcher.group( 2 ) ) != null ? sign + hours + "H" : "" );
		_return.append( ( hours = matcher.group( 3 ) ) != null ? sign + hours + "M" : "" );
		_return.append( ( hours = matcher.group( 4 ) ) != null ? sign + hours + "S" : "" );
		
		return Duration.parse( _return.toString().replaceAll( "\\:", "" ) );
	}
	
	/**
	 * Method responsable to get a String 'hours' representation based on a Duration instance.
	 * @param duration
	 * @return
	 */
	public static String fromDuration( Duration duration ) {
		if ( duration == null ) return null;
		
		String hours = duration.toString().substring( 2 );
		StringBuilder _return = new StringBuilder( hours.charAt( 0 ) == '-' ? "-" : "" );
		hours = hours.replaceAll( "\\-", "" );
		
		String sign = hours.charAt( 0 ) == '-' ? "-" : "";
		
		Matcher matcher = Pattern.compile( "^(\\-?\\d{1,2}[H])?(\\-?\\d{1,2}[M])?(\\-?\\d{1,2}[S])?$" ).matcher( hours );
		Assert.state( matcher.find(), "The 'find' method did not succeed." );
		_return.append( ( ( hours = matcher.group( 1 ) ) != null ? hours.replace( "H", "" ) : "00" ) + ":" );
		_return.append( ( ( hours = matcher.group( 2 ) ) != null ? ( hours = hours.replace( "M", "" ) ).length() == 1 ? "0" + hours : hours : "00" ) + ":" );
		_return.append( ( hours = matcher.group( 3 ) ) != null ? ( hours = hours.replace( "S", "" ) ).length() == 1 ? "0" + hours : hours : "00" );
		
		return sign + _return.toString();
	}

}
