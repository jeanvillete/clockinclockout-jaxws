package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.AdjustingRowMapper;

@Repository
public class AdjustingRepository extends CommonRepository {

	public boolean insert( final Adjusting adjusting ) {
		adjusting.setId( this.nextVal( "ADJUSTING_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO ADJUSTING ( ID, DESCRIPTION, TIME_INTERVAL, ID_PROFILE )"
				+ " VALUES ( ?, ?, ?, ? ) ",
				new Object[]{ 
						adjusting.getId(),
						adjusting.getDescription(),
						DurationUtil.durationToPG( adjusting.getTimeInterval() ),
						adjusting.getProfile().getId() }) == 1;
	}

	public boolean delete( final Adjusting adjusting ) {
		return this.jdbcTemplate.update( " DELETE FROM ADJUSTING WHERE ID = ? AND ID_PROFILE = ? ",
				new Object[]{ 
						adjusting.getId(), 
						adjusting.getProfile().getId() } ) == 1;
	}

	public List< Adjusting > list( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT ADJ.ID, ADJ.DESCRIPTION, ADJ.TIME_INTERVAL, ADJ.ID_PROFILE "
				+ " FROM ADJUSTING ADJ "
				+ " JOIN PROFILE PROF ON ADJ.ID_PROFILE = PROF.ID "
				+ " WHERE PROF.ID_CLK_USER = ? AND ADJ.ID_PROFILE = ? "
				+ " ORDER BY ADJ.ID ",
				new Object[]{ 
						profile.getUser().getId(), 
						profile.getId() }, 
				new AdjustingRowMapper() );
	}

	public boolean update( final Adjusting adjusting ) {
		return this.jdbcTemplate.update( " UPDATE ADJUSTING SET DESCRIPTION = ?, TIME_INTERVAL = ? "
				+ " WHERE ID = ? AND ID_PROFILE = ? ",
				new Object[]{ 
						adjusting.getDescription(),
						DurationUtil.durationToPG( adjusting.getTimeInterval() ),
						adjusting.getId(),
						adjusting.getProfile().getId() }) == 1;
	}

	public boolean exists( final String description, final Profile profile ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM ADJUSTING "
				+ " WHERE DESCRIPTION = ? AND ID_PROFILE = ? ",
				new Object[]{ 
						description,
						profile.getId()
				}, Integer.class) > 0 ;
	}

	public boolean exists( final String description, final Profile profile, final Integer id ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM ADJUSTING "
				+ " WHERE DESCRIPTION = ? AND ID_PROFILE = ? AND ID <> ? ",
				new Object[]{ 
						description,
						profile.getId(),
						id
				}, Integer.class) > 0 ;
	}

}
