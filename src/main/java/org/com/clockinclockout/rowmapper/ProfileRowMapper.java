package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;
import org.springframework.jdbc.core.RowMapper;

public class ProfileRowMapper implements RowMapper< Profile > {

	@Override
	public Profile mapRow( ResultSet rs, int rowNum ) throws SQLException {
		Profile profile = new Profile( RowMapperUtil.getInteger( rs, "ID" ),
				new User( RowMapperUtil.getInteger( rs, "ID_CLK_USER" ) ),
				rs.getString( "DESCRIPTION" ),
				rs.getString( "HOURS_FORMAT" ),
				rs.getString( "DATE_FORMAT" ) );
		profile.setDefaultExpectedSunday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_SUNDAY" ) );
		profile.setDefaultExpectedMonday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_MONDAY" ) );
		profile.setDefaultExpectedTuesday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_TUESDAY" ) );
		profile.setDefaultExpectedWednesday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_WEDNESDAY" ) );
		profile.setDefaultExpectedThursday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_THURSDAY" ) );
		profile.setDefaultExpectedFriday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_FRIDAY" ) );
		profile.setDefaultExpectedSaturday( RowMapperUtil.getInteger( rs, "DEFAULT_EXPECTED_SATURDAY" ) );
		
		return profile;
	}

}
