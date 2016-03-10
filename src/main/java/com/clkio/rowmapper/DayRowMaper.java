package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.Day;
import com.clkio.domain.Profile;

public class DayRowMaper implements RowMapper< Day > {

	@Override
	public Day mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new Day( RowMapperUtil.getInteger( rs, "ID" ), 
				rs.getTimestamp( "DATE" ), 
				RowMapperUtil.getDuration( rs, "EXPECTED_HOURS" ), 
				rs.getString( "NOTES" ),
				new Profile( RowMapperUtil.getInteger( rs, "ID_PROFILE" ) ) );
	}

}
