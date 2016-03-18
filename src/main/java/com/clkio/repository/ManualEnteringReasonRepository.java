package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.ManualEnteringReasonRowMapper;

@Repository
public class ManualEnteringReasonRepository extends CommonRepository {

	public void insert( final ManualEnteringReason manualEnteringReason ) {
		manualEnteringReason.setId( this.nextVal( "MANUAL_ENTERING_REASON_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO MANUAL_ENTERING_REASON ( ID, REASON, ID_PROFILE )"
				+ " VALUES ( ?, ?, ? ) ",
				new Object[]{ manualEnteringReason.getId(),
						manualEnteringReason.getReason(),
						manualEnteringReason.getProfile().getId() });
	}

	public void delete( final ManualEnteringReason manualEnteringReason ) {
		this.jdbcTemplate.update( " DELETE FROM MANUAL_ENTERING_REASON WHERE ID = ? ", new Object[]{ manualEnteringReason.getId() } );
	}

	public List< ManualEnteringReason > list( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT REASON.ID, REASON.REASON, REASON.ID_PROFILE "
				+ " FROM MANUAL_ENTERING_REASON REASON "
				+ " JOIN PROFILE PROF ON REASON.ID_PROFILE = PROF.ID "
				+ " WHERE PROF.ID_CLK_USER = ? AND REASON.ID_PROFILE = ? ",
				new Object[]{ profile.getUser().getId(), profile.getId() }, new ManualEnteringReasonRowMapper() );
	}

}
