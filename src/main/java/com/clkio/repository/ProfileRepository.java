package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.rowmapper.ProfileRowMapper;

@Repository
public class ProfileRepository extends CommonRepository {

	public boolean insert( final Profile profile ) {
		profile.setId( this.nextVal( "PROFILE_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO PROFILE ( ID, "
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
						DurationUtil.durationToPG( profile.getDefaultExpectedSunday() ), 
						DurationUtil.durationToPG( profile.getDefaultExpectedMonday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedTuesday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedWednesday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedThursday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedFriday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedSaturday() ) }) == 1;
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
				+ " WHERE ID_CLK_USER = ? "
				+ " ORDER BY ID ",
				new Object[]{ user.getId() },
				new ProfileRowMapper() );
	}

	public boolean delete( final Profile profile ) {
		return this.jdbcTemplate.update( " DELETE FROM PROFILE WHERE ID = ? AND ID_CLK_USER = ? ", new Object[]{ profile.getId(), profile.getUser().getId() } ) == 1;
	}

	public Profile get( final Profile profile ) {
		return this.jdbcTemplate.queryForObject( 
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
				+ " WHERE ID = ? AND ID_CLK_USER = ? ",
				new Object[]{ profile.getId(), profile.getUser().getId() },
				new ProfileRowMapper() );
	}

	public boolean exists( final String description, final User user ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM PROFILE "
				+ " WHERE DESCRIPTION = ? AND ID_CLK_USER = ? ",
				new Object[]{ description, user.getId() },
				Integer.class ) > 0;
	}

	public boolean exists( final String description, final User user, final Integer id ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM PROFILE "
				+ " WHERE ID <> ? AND DESCRIPTION = ? AND ID_CLK_USER = ? ",
				new Object[]{ id, description, user.getId() },
				Integer.class ) > 0;
	}

	public boolean update( final Profile profile ) {
		return this.jdbcTemplate.update( " UPDATE PROFILE SET "
				+ " DESCRIPTION = ?, "
				+ " HOURS_FORMAT = ?, "
				+ " DATE_FORMAT = ?, "
				+ " DEFAULT_EXPECTED_SUNDAY = ?, "
				+ " DEFAULT_EXPECTED_MONDAY = ?, "
				+ " DEFAULT_EXPECTED_TUESDAY = ?, "
				+ " DEFAULT_EXPECTED_WEDNESDAY = ?, "
				+ " DEFAULT_EXPECTED_THURSDAY = ?, "
				+ " DEFAULT_EXPECTED_FRIDAY = ?, "
				+ " DEFAULT_EXPECTED_SATURDAY = ? "
				+ " WHERE ID = ? AND ID_CLK_USER = ? ",
				new Object[]{ 
						profile.getDescription(),
						profile.getHoursFormat(),
						profile.getDateFormat(),
						DurationUtil.durationToPG( profile.getDefaultExpectedSunday() ), 
						DurationUtil.durationToPG( profile.getDefaultExpectedMonday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedTuesday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedWednesday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedThursday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedFriday() ),
						DurationUtil.durationToPG( profile.getDefaultExpectedSaturday() ),
						profile.getId(),
						profile.getUser().getId() }) == 1;
	}

}
