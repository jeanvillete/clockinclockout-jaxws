package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.ManualEnteringRowMapper;

@Repository
public class ManualEnteringRepository extends CommonRepository {

	public boolean insert( final ManualEntering manualEntering ) {
		manualEntering.setId( this.nextVal( "MANUAL_ENTERING_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO MANUAL_ENTERING ( ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL )"
				+ " VALUES ( ?, ?, ?, ? ) ",
				new Object[]{ manualEntering.getId(),
						manualEntering.getDay().getId(),
						manualEntering.getReason().getId(),
						DurationUtil.durationToPG( manualEntering.getTimeInterval() )}) == 1;
	}
	
	public boolean delete( final Profile profile, final ManualEntering manualEntering ) {
		return this.jdbcTemplate.update( " DELETE FROM MANUAL_ENTERING WHERE ID = "
				+ " ( SELECT ME.ID FROM MANUAL_ENTERING ME "
				+ "   JOIN DAY D ON ME.ID_DAY = D.ID "
				+ "   WHERE D.ID_PROFILE = ? AND ME.ID = ? ) ", 
				new Object[]{ 
						profile.getId(), 
						manualEntering.getId() 
				}) == 1;
	}

	public List< ManualEntering > listBy( final ManualEnteringReason manualEnteringReason ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL "
				+ " FROM MANUAL_ENTERING WHERE ID_MANUAL_ENTERING_REASON = ? ",
				new Object[]{ manualEnteringReason.getId() }, new ManualEnteringRowMapper() );
	}

	public List< ManualEntering > listBy( final Day day ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL "
				+ " FROM MANUAL_ENTERING WHERE ID_DAY = ? ",
				new Object[]{ day.getId() }, new ManualEnteringRowMapper() );
	}

	public ManualEntering get( Profile profile, ManualEntering manualEntering ) {
		return this.jdbcTemplate.queryForObject( " SELECT ME.ID, ME.ID_DAY, ME.ID_MANUAL_ENTERING_REASON, ME.TIME_INTERVAL "
				+ " FROM MANUAL_ENTERING ME "
				+ " JOIN DAY D ON ME.ID_DAY = D.ID "
				+ " WHERE D.ID_PROFILE = ? AND ME.ID = ? ",
				new Object[]{ 
					profile.getId(),
					manualEntering.getId()
				},
				new ManualEnteringRowMapper() );
	}

	public boolean update( ManualEntering manualEntering ) {
		return this.jdbcTemplate.update( " 	UPDATE MANUAL_ENTERING SET ID_MANUAL_ENTERING_REASON = ?, TIME_INTERVAL = ? "
				+ " WHERE ID = ? AND ID_DAY = ? ",
				new Object[]{ 
						manualEntering.getReason().getId(),
						DurationUtil.durationToPG( manualEntering.getTimeInterval() ),
						manualEntering.getId(),
						manualEntering.getDay().getId() }) == 1;
	}

}
