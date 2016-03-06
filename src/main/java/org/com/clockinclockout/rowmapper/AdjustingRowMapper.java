package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.Adjusting;
import org.com.clockinclockout.domain.Profile;
import org.springframework.jdbc.core.RowMapper;

public class AdjustingRowMapper implements RowMapper< Adjusting > {

	@Override
	public Adjusting mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new Adjusting( RowMapperUtil.getInteger( rs, "ID" ),
				rs.getString( "DESCRIPTION" ),
				RowMapperUtil.getDuration( rs, "TIME_INTERVAL" ),
				new Profile( RowMapperUtil.getInteger( rs, "ID_PROFILE" ) ) );
	}

}
