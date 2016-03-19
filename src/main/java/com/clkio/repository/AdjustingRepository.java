package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.AdjustingRowMapper;

@Repository
public class AdjustingRepository extends CommonRepository {

	public void insert( final Adjusting adjusting ) {
		adjusting.setId( this.nextVal( "ADJUSTING_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO ADJUSTING ( ID, DESCRIPTION, TIME_INTERVAL, ID_PROFILE )"
				+ " VALUES ( ?, ?, ?, ? ) ",
				new Object[]{ adjusting.getId(),
						adjusting.getDescription(),
						DurationUtil.durationToPG( adjusting.getTimeInterval() ),
						adjusting.getProfile().getId() });
	}

	public void delete( final Adjusting adjusting ) {
		this.jdbcTemplate.update( " DELETE FROM ADJUSTING WHERE ID = ? ", new Object[]{ adjusting.getId() } );
	}

	public List< Adjusting > list( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT ADJ.ID, ADJ.DESCRIPTION, ADJ.TIME_INTERVAL, ADJ.ID_PROFILE "
				+ " FROM ADJUSTING ADJ "
				+ " JOIN PROFILE PROF ON ADJ.ID_PROFILE = PROF.ID "
				+ " WHERE PROF.ID_CLK_USER = ? AND ADJ.ID_PROFILE = ? ",
				new Object[]{ profile.getUser().getId(), profile.getId() }, new AdjustingRowMapper() );
	}

}
