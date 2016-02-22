package org.com.clockinclockout.repository;

import org.com.clockinclockout.domain.Profile;
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
						profile.getDefaultExpectedSunday(), 
						profile.getDefaultExpectedMonday(),
						profile.getDefaultExpectedTuesday(),
						profile.getDefaultExpectedWednesday(),
						profile.getDefaultExpectedThursday(),
						profile.getDefaultExpectedFriday(),
						profile.getDefaultExpectedSaturday() });
	}

}
