package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.Profile;
import com.clkio.domain.User;

public class ProfileRowMapper implements RowMapper< Profile > {

	@Override
	public Profile mapRow( ResultSet rs, int rowNum ) throws SQLException {
		Profile profile = new Profile( RowMapperUtil.getInteger( rs, "ID" ),
				new User( RowMapperUtil.getInteger( rs, "ID_CLK_USER" ) ),
				rs.getString( "DESCRIPTION" ),
				rs.getString( "HOURS_FORMAT" ),
				rs.getString( "DATE_FORMAT" ) );
		profile.setDefaultExpectedSunday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_SUNDAY" ) );
		profile.setDefaultExpectedMonday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_MONDAY" ) );
		profile.setDefaultExpectedTuesday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_TUESDAY" ) );
		profile.setDefaultExpectedWednesday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_WEDNESDAY" ) );
		profile.setDefaultExpectedThursday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_THURSDAY" ) );
		profile.setDefaultExpectedFriday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_FRIDAY" ) );
		profile.setDefaultExpectedSaturday( RowMapperUtil.getDuration( rs, "DEFAULT_EXPECTED_SATURDAY" ) );
		
		return profile;
	}

}
