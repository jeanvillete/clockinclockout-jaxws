package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.rowmapper.ManualEnteringRowMapper;

@Repository
public class ManualEnteringRepository extends CommonRepository {

	public void insert( final ManualEntering manualEntering ) {
		manualEntering.setId( this.nextVal( "MANUAL_ENTERING_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO MANUAL_ENTERING ( ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL )"
				+ " VALUES ( ?, ?, ?, ? ) ",
				new Object[]{ manualEntering.getId(),
						manualEntering.getDay().getId(),
						manualEntering.getReason().getId(),
						DurationUtil.durationToPG( manualEntering.getTimeInterval() )});
	}
	
	public void delete( final ManualEntering manualEntering ) {
		this.jdbcTemplate.update( " DELETE FROM MANUAL_ENTERING WHERE ID = ? ", new Object[]{ manualEntering.getId() } );
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

}
