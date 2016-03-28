package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.ManualEnteringReasonRowMapper;

@Repository
public class ManualEnteringReasonRepository extends CommonRepository {

	public boolean insert( final ManualEnteringReason manualEnteringReason ) {
		manualEnteringReason.setId( this.nextVal( "MANUAL_ENTERING_REASON_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO MANUAL_ENTERING_REASON ( ID, REASON, ID_PROFILE )"
				+ " VALUES ( ?, ?, ? ) ",
				new Object[]{ manualEnteringReason.getId(),
						manualEnteringReason.getReason(),
						manualEnteringReason.getProfile().getId() }) == 1;
	}

	public boolean delete( final ManualEnteringReason manualEnteringReason ) {
		return this.jdbcTemplate.update( " DELETE FROM MANUAL_ENTERING_REASON WHERE ID = ? AND ID_PROFILE = ? ",
				new Object[]{ 
						manualEnteringReason.getId(),
						manualEnteringReason.getProfile().getId() } ) == 1;
	}

	public List< ManualEnteringReason > list( final Profile profile ) {
		return this.jdbcTemplate.query( " SELECT REASON.ID, REASON.REASON, REASON.ID_PROFILE "
				+ " FROM MANUAL_ENTERING_REASON REASON "
				+ " JOIN PROFILE PROF ON REASON.ID_PROFILE = PROF.ID "
				+ " WHERE PROF.ID_CLK_USER = ? AND REASON.ID_PROFILE = ? ",
				new Object[]{ profile.getUser().getId(), profile.getId() }, new ManualEnteringReasonRowMapper() );
	}

	public boolean exists( final String reason, final Profile profile ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM MANUAL_ENTERING_REASON "
				+ " WHERE REASON = ? AND ID_PROFILE = ? ",
				new Object[]{
						reason,
						profile.getId() },
				Integer.class ) > 0;
	}

	public boolean exists( final String reason, final Profile profile, final Integer id ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM MANUAL_ENTERING_REASON "
				+ " WHERE REASON = ? AND ID_PROFILE = ? AND ID <> ? ",
				new Object[]{
						reason,
						profile.getId(),
						id},
				Integer.class ) > 0;
	}

	public boolean update( final ManualEnteringReason manualEnteringReason ) {
		return this.jdbcTemplate.update( " UPDATE MANUAL_ENTERING_REASON SET REASON = ? "
				+ " WHERE ID = ? AND ID_PROFILE = ? ",
				new Object[]{ 
						manualEnteringReason.getReason(),
						manualEnteringReason.getId(),
						manualEnteringReason.getProfile().getId() }) == 1;
	}

}
