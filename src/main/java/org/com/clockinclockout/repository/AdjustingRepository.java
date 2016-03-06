package org.com.clockinclockout.repository;

import java.util.List;

import org.com.clockinclockout.domain.Adjusting;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.rowmapper.AdjustingRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AdjustingRepository extends CommonRepository {

	public void insert( final Adjusting adjusting ) {
		adjusting.setId( this.nextVal( "ADJUSTING_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO ADJUSTING ( ID, DESCRIPTION, TIME_INTERVAL, ID_PROFILE )"
				+ " VALUES ( ?, ?, ?, ? ) ",
				new Object[]{ adjusting.getId(),
						adjusting.getDescription(),
						durationToPG( adjusting.getTimeInterval() ),
						adjusting.getProfile().getId() });
	}

	public void delete( final Adjusting adjusting ) {
		this.jdbcTemplate.update( " DELETE FROM ADJUSTING WHERE ID = ? ", new Object[]{ adjusting.getId() } );
	}

	public List< Adjusting > listBy( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT ID, DESCRIPTION, TIME_INTERVAL, ID_PROFILE "
				+ " FROM ADJUSTING WHERE ID_PROFILE = ? ",
				new Object[]{ profile.getId() }, new AdjustingRowMapper() );
	}

}
