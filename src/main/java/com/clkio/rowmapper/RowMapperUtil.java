package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;

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
		return strValue.equals( "true" ) || strValue.equals( "y" ) || strValue.equals( "1" );
	}
	
	public static Duration getDuration( ResultSet rs, String column ) throws SQLException {
		PGInterval pgInterval = ( PGInterval ) rs.getObject( column );
		return pgInterval != null ? Duration.ofSeconds( ( pgInterval.getHours() * 3600 ) + ( pgInterval.getMinutes() * 60 ) + (int) pgInterval.getSeconds() ) : null;
	}
	
}