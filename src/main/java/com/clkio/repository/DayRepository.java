package com.clkio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.Day;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.DayRowMaper;

@Repository
public class DayRepository extends CommonRepository {

	public boolean insert( final Day day ) {
		day.setId( this.nextVal( "DAY_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO DAY ( ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE )"
				+ " VALUES ( ?, ?, ?, ?, ? ) ",
				new Object[]{ day.getId(),
						LocalDateTimeUtil.getTimestamp( day.getDate() ),
						DurationUtil.durationToPG( day.getExpectedHours() ),
						day.getNotes(),
						day.getProfile().getId() }) == 1;
	}

	public boolean delete( final Day day ) {
		return this.jdbcTemplate.update( " DELETE FROM DAY WHERE ID = ? ", new Object[]{ day.getId() } ) == 1;
	}

	public List< Day > listBy( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE "
				+ " FROM DAY WHERE ID_PROFILE = ? ",
				new Object[]{ profile.getId() }, new DayRowMaper() );
	}

	public Day get( final Profile profile, final LocalDate localDateDay ) {
		return this.jdbcTemplate.queryForObject( " SELECT ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE "
				+ " FROM DAY WHERE DATE = ? AND ID_PROFILE = ? ",
				new Object[]{
					LocalDateTimeUtil.getTimestamp( localDateDay ),
					profile.getId()
				},
				new DayRowMaper() );
	}

	public Day get( final Day day ) {
		return this.jdbcTemplate.queryForObject( " SELECT ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE "
				+ " FROM DAY WHERE ID = ? ",
				new Object[]{ day.getId() },
				new DayRowMaper() );
	}

	public boolean update( final Day day ) {
		return this.jdbcTemplate.update( " UPDATE DAY SET EXPECTED_HOURS = ?, NOTES = ? "
				+ " WHERE ID = ? AND ID_PROFILE = ? ",
				new Object[]{
						DurationUtil.durationToPG( day.getExpectedHours() ),
						day.getNotes(),
						day.getId(),
						day.getProfile().getId() }) == 1;
	}

	public List< Day > list( Profile profile, LocalDate startDate, LocalDate endDate ) {
		return this.jdbcTemplate.query( " SELECT ID, DATE, EXPECTED_HOURS, NOTES, ID_PROFILE "
				+ " FROM DAY WHERE ID_PROFILE = ? AND DATE >= ? AND DATE <= ? ",
				new Object[]{ 
					profile.getId(),
					LocalDateTimeUtil.getTimestamp( startDate ),
					LocalDateTimeUtil.getTimestamp( endDate )
				},
				new DayRowMaper() );
	}

}
