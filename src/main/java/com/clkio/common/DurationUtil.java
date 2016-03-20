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
	 * 
	 * @param hours
	 * @param pattern
	 * @return
	 */
	public static Duration fromString( String hours, String pattern ) {
		Assert.hasText( hours, "The argument 'hours' is mandatory." );
		Assert.state( IntervalUtil.isHoursValid( hours, pattern ), "The provided value for 'hours' is not in a valid format; 'pattern'=[" + pattern + "]" );
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
	 * 
	 * @param duration
	 * @param pattern The pattern set by the user.
	 * @return
	 */
	public static String fromDuration( Duration duration, String pattern ) {
		if ( duration == null ) return null;
		Assert.hasText( pattern, "The argument 'pattern' is mandatory." );
		
		String hours = duration.toString().substring( 2 );
		StringBuilder _return = new StringBuilder( hours.charAt( 0 ) == '-' ? "-" : "" );
		hours = hours.replaceAll( "\\-", "" );
		
		Matcher matcher = Pattern.compile( "^(\\d{1,2}[H])?(\\d{1,2}[M])?(\\d{1,2}[S])?$" ).matcher( hours );
		Assert.state( matcher.find(), "The 'find' method did not succeed." );
		
		Matcher hMatcher = Pattern.compile( "(^HH.?)|(^H.?)" ).matcher( pattern );
		boolean hLong = hMatcher.find() && hMatcher.group( 1 ) != null;
		_return.append( ( hours = matcher.group( 1 ) ) != null ? ( ( hours = hours.replace( "H", "" ) ).length() == 1 && hLong ? "0" + hours : hours ) : hLong ? "00" : "0" );
		
		Matcher mMatcher = Pattern.compile( "(^.+\\:mm.?)|(^.+\\:m.?)" ).matcher( pattern );
		if ( mMatcher.find() ) {
			boolean mLong = mMatcher.group( 1 ) != null;
			_return.append( ":" + ( ( hours = matcher.group( 2 ) ) != null ? ( ( hours = hours.replace( "M", "" ) ).length() == 1 && mLong ? "0" + hours : hours )  : ( mLong ? "00" : "0" ) ) );
		}
		
		Matcher sMatcher = Pattern.compile( "(^.+\\:ss$)|(^.+\\:s$)" ).matcher( pattern );
		
		if ( sMatcher.find() ) {
			boolean sLong = sMatcher.group( 1 ) != null;
			_return.append( ":" + ( ( hours = matcher.group( 3 ) ) != null ? ( ( hours = hours.replace( "S", "" ) ).length() == 1 && sLong ? "0" + hours : hours ) : ( sLong ? "00" : "0" ) ) );
		}
		
		return _return.toString();
	}

}
