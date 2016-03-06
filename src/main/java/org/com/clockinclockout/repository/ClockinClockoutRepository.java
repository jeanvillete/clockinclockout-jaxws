package org.com.clockinclockout.repository;

import java.util.List;

import org.com.clockinclockout.domain.ClockinClockout;
import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.rowmapper.ClockinClockoutRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClockinClockoutRepository extends CommonRepository {

	public void insert( final ClockinClockout clockinClockout ) {
		clockinClockout.setId( this.nextVal( "CLOCKINCLOCKOUT_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO CLOCKINCLOCKOUT ( ID, ID_DAY, CLOCKIN, CLOCKOUT ) "
				+ " VALUES ( ?, ?, ?, ?) ",
				new Object[]{ clockinClockout.getId(),
						clockinClockout.getDay().getId(),
						clockinClockout.getClockin(),
						clockinClockout.getClockout() });
	}

	public void delete( final ClockinClockout clockinClockout ) {
		this.jdbcTemplate.update( " DELETE FROM CLOCKINCLOCKOUT WHERE ID = ? ", new Object[]{ clockinClockout.getId() } );
	}

	public List< ClockinClockout > listBy( Day day ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, CLOCKIN, CLOCKOUT "
				+ " FROM CLOCKINCLOCKOUT WHERE ID_DAY = ? ",
				new Object[]{ day.getId() }, new ClockinClockoutRowMapper() );
	}

}
