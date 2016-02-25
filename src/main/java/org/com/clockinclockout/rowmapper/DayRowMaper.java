package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.Profile;
import org.springframework.jdbc.core.RowMapper;

public class DayRowMaper implements RowMapper< Day > {

	@Override
	public Day mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new Day( RowMapperUtil.getInteger( rs, "ID" ), 
				rs.getTimestamp( "DATE" ), 
				RowMapperUtil.getInteger( rs, "EXPECTED_HOURS" ), 
				rs.getString( "NOTES" ),
				new Profile( RowMapperUtil.getInteger( rs, "ID_PROFILE" ) ) );
	}

}
