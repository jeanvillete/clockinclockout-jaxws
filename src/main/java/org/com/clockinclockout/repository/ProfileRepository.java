package org.com.clockinclockout.repository;

import java.util.List;

import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.rowmapper.ProfileRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepository extends CommonRepository {

	public void insert( final Profile profile ) {
		profile.setId( this.nextVal( "PROFILE_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO PROFILE ( ID, "
				+ " ID_CLK_USER, "
				+ " DESCRIPTION, "
				+ " HOURS_FORMAT, "
				+ " DATE_FORMAT, "
				+ " DEFAULT_EXPECTED_SUNDAY, "
				+ " DEFAULT_EXPECTED_MONDAY, "
				+ " DEFAULT_EXPECTED_TUESDAY, "
				+ " DEFAULT_EXPECTED_WEDNESDAY, "
				+ " DEFAULT_EXPECTED_THURSDAY, "
				+ " DEFAULT_EXPECTED_FRIDAY, "
				+ " DEFAULT_EXPECTED_SATURDAY )"
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ",
				new Object[]{ profile.getId(),
						profile.getUser().getId(),
						profile.getDescription(),
						profile.getHoursFormat(),
						profile.getDateFormat(),
						durationToPG( profile.getDefaultExpectedSunday() ), 
						durationToPG( profile.getDefaultExpectedMonday() ),
						durationToPG( profile.getDefaultExpectedTuesday() ),
						durationToPG( profile.getDefaultExpectedWednesday() ),
						durationToPG( profile.getDefaultExpectedThursday() ),
						durationToPG( profile.getDefaultExpectedFriday() ),
						durationToPG( profile.getDefaultExpectedSaturday() ) });
	}

	public List< Profile > listBy( final User user ) {
		return this.jdbcTemplate.query( 
				" SELECT ID, "
				+ " ID_CLK_USER, "
				+ " DESCRIPTION, "
				+ " HOURS_FORMAT, "
				+ " DATE_FORMAT, "
				+ " DEFAULT_EXPECTED_SUNDAY, "
				+ " DEFAULT_EXPECTED_MONDAY, "
				+ " DEFAULT_EXPECTED_TUESDAY, "
				+ " DEFAULT_EXPECTED_WEDNESDAY, "
				+ " DEFAULT_EXPECTED_THURSDAY, "
				+ " DEFAULT_EXPECTED_FRIDAY, "
				+ " DEFAULT_EXPECTED_SATURDAY "
				+ " FROM PROFILE "
				+ " WHERE ID_CLK_USER = ? ",
				new Object[]{ user.getId() },
				new ProfileRowMapper() );
	}

	public void delete( final Profile profile ) {
		this.jdbcTemplate.update( " DELETE FROM PROFILE WHERE ID = ? ", new Object[]{ profile.getId() } );
	}

}
