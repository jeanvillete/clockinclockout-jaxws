package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Day;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.DayRowMaper;

@Repository
public class DayRepository extends CommonRepository {

	public void insert( final Day day ) {
		day.setId( this.nextVal( "DAY_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO DAY ( ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE )"
				+ " VALUES ( ?, ?, ?, ?, ? ) ",
				new Object[]{ day.getId(),
						day.getDate(),
						DurationUtil.durationToPG( day.getExpectedHours() ),
						day.getNotes(),
						day.getProfile().getId() });
	}

	public void delete( final Day day ) {
		this.jdbcTemplate.update( " DELETE FROM DAY WHERE ID = ? ", new Object[]{ day.getId() } );
	}

	public List< Day > listBy( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE "
				+ " FROM DAY WHERE ID_PROFILE = ? ",
				new Object[]{ profile.getId() }, new DayRowMaper() );
	}

}
