package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;

public class ClockinClockoutRowMapper implements RowMapper< ClockinClockout > {

	@Override
	public ClockinClockout mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new ClockinClockout( RowMapperUtil.getInteger( rs, "ID" ), 
				new Day( RowMapperUtil.getInteger( rs, "ID_DAY" ) ), 
				RowMapperUtil.getLocalDateTime( rs, "CLOCKIN" ), 
				RowMapperUtil.getLocalDateTime( rs, "CLOCKOUT" ) );
	}

}
