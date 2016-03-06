package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.ManualEntering;
import org.com.clockinclockout.domain.ManualEnteringReason;
import org.springframework.jdbc.core.RowMapper;

public class ManualEnteringRowMapper implements RowMapper< ManualEntering > {

	@Override
	public ManualEntering mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new ManualEntering( RowMapperUtil.getInteger( rs, "ID" ),
				new Day( RowMapperUtil.getInteger( rs, "ID_DAY" ) ),
				new ManualEnteringReason( RowMapperUtil.getInteger( rs, "ID_MANUAL_ENTERING_REASON" ) ),
				RowMapperUtil.getDuration( rs, "TIME_INTERVAL" ) );
	}

}
