package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.postgresql.util.PGInterval;

abstract class RowMapperUtil {

	public static Integer getInteger( ResultSet rs, String column ) throws SQLException {
		int intValue = rs.getInt( column );
		return rs.wasNull() ? null : intValue;
	}
	
	public static Long getLong( ResultSet rs, String column ) throws SQLException {
		long longValue = rs.getLong( column );
		return rs.wasNull() ? null : longValue;
	}
	
	public static Boolean getBoolean( ResultSet rs, String column ) throws SQLException {
		String strValue = rs.getString( column );
		if ( rs.wasNull() ) return null;
		strValue = strValue.trim().toLowerCase();
		return strValue.equals( "t" ) || strValue.equals( "true" ) || strValue.equals( "y" ) || strValue.equals( "1" );
	}
	
	public static Duration getDuration( ResultSet rs, String column ) throws SQLException {
		PGInterval pgInterval = ( PGInterval ) rs.getObject( column );
		return pgInterval != null ? Duration.ofSeconds( ( pgInterval.getHours() * 3600 ) + ( pgInterval.getMinutes() * 60 ) + (int) pgInterval.getSeconds() ) : null;
	}
	
	public static LocalDateTime getLocalDateTime( ResultSet rs, String column ) throws SQLException {
		Timestamp timestamp = ( Timestamp ) rs.getTimestamp( column );
		return timestamp != null ? LocalDateTime.ofInstant( Instant.ofEpochMilli( timestamp.getTime() ), ZoneId.systemDefault() ): null;
	}
	
	public static LocalDate getLocalDate( ResultSet rs, String column ) throws SQLException {
		LocalDateTime localDateTime = getLocalDateTime( rs, column );
		return localDateTime != null ? LocalDate.from( localDateTime ): null;
	}
	
}