package org.com.clockinclockout.repository;

import org.com.clockinclockout.domain.Adjusting;
import org.springframework.stereotype.Repository;

@Repository
public class AdjustingRepository extends CommonRepository {

	public void insert( final Adjusting adjusting ) {
		adjusting.setId( this.nextVal( "_SEQ" ) );
		this.jdbcTemplate.update( "  ",
				new Object[]{ });
	}

	public void delete( final Adjusting adjusting ) {
		this.jdbcTemplate.update( " DELETE FROM PROFILE WHERE ID = ? ", new Object[]{ adjusting.getId() } );
	}

}
