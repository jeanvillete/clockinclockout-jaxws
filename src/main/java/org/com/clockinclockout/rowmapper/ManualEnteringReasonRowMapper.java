package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.ManualEnteringReason;
import org.com.clockinclockout.domain.Profile;
import org.springframework.jdbc.core.RowMapper;

public class ManualEnteringReasonRowMapper implements RowMapper< ManualEnteringReason > {

	@Override
	public ManualEnteringReason mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new ManualEnteringReason( RowMapperUtil.getInteger( rs, "ID" ), 
				new Profile( RowMapperUtil.getInteger( rs, "ID_PROFILE" ) ), 
				rs.getString( "REASON" ) );
	}

}
