package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
}