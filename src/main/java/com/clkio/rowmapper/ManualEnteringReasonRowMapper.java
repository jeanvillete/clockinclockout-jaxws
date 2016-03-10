package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;

public class ManualEnteringReasonRowMapper implements RowMapper< ManualEnteringReason > {

	@Override
	public ManualEnteringReason mapRow( ResultSet rs, int rowNum ) throws SQLException {
		return new ManualEnteringReason( RowMapperUtil.getInteger( rs, "ID" ), 
				new Profile( RowMapperUtil.getInteger( rs, "ID_PROFILE" ) ), 
				rs.getString( "REASON" ) );
	}

}
