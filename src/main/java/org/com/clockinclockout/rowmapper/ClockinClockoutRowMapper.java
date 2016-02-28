package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.ClockinClockout;
import org.com.clockinclockout.domain.Day;
import org.springframework.jdbc.core.RowMapper;

public class ClockinClockoutRowMapper implements RowMapper< ClockinClockout > {

	@Override
	public ClockinClockout mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new ClockinClockout( RowMapperUtil.getInteger( rs, "ID" ), 
				new Day( RowMapperUtil.getInteger( rs, "ID_DAY" ) ), 
				rs.getTimestamp( "CLOCKIN" ), 
				rs.getTimestamp( "CLOCKOUT" ) );
	}

}
