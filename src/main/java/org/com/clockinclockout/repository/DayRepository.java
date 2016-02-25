package org.com.clockinclockout.repository;

import java.util.List;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.rowmapper.DayRowMaper;
import org.springframework.stereotype.Repository;

@Repository
public class DayRepository extends CommonRepository {

	public void insert( final Day day ) {
		day.setId( this.nextVal( "DAY_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO DAY ( ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE )"
				+ " VALUES ( ?, ?, ?, ? ) ",
				new Object[]{ day.getId(),
						day.getDate(),
						day.getNotes(),
						day.getProfile().getId() });
	}

	public void delete( final Day day ) {
		this.jdbcTemplate.update( " DELETE FROM DAY WHERE ID = ? ", new Object[]{ day.getId() } );
	}

	public List< Day > listBy( Profile profile ) {
		return this.jdbcTemplate.query( " SELECT ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE "
				+ " FROM DAY WHERE ID_PROFILE = ? ",
				new Object[]{ profile.getId() }, new DayRowMaper() );
	}

}
