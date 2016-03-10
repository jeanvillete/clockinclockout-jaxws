package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;

public class ManualEnteringRowMapper implements RowMapper< ManualEntering > {

	@Override
	public ManualEntering mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new ManualEntering( RowMapperUtil.getInteger( rs, "ID" ),
				new Day( RowMapperUtil.getInteger( rs, "ID_DAY" ) ),
				new ManualEnteringReason( RowMapperUtil.getInteger( rs, "ID_MANUAL_ENTERING_REASON" ) ),
				RowMapperUtil.getDuration( rs, "TIME_INTERVAL" ) );
	}

}
