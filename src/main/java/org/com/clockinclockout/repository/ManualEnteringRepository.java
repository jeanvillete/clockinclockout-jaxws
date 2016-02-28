package org.com.clockinclockout.repository;

import java.util.List;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.ManualEntering;
import org.com.clockinclockout.domain.ManualEnteringReason;
import org.com.clockinclockout.rowmapper.ManualEnteringRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ManualEnteringRepository extends CommonRepository {

	public void delete( final ManualEntering manualEntering ) {
		this.jdbcTemplate.update( " DELETE FROM MANUAL_ENTERING WHERE ID = ? ", new Object[]{ manualEntering.getId() } );
	}

	public List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL "
				+ " FROM MANUAL_ENTERING WHERE ID_MANUAL_ENTERING_REASON = ? ",
				new Object[]{ manualEnteringReason.getId() }, new ManualEnteringRowMapper() );
	}

	public List< ManualEntering > listBy( Day day ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL "
				+ " FROM MANUAL_ENTERING WHERE ID_DAY = ? ",
				new Object[]{ day.getId() }, new ManualEnteringRowMapper() );
	}

}
