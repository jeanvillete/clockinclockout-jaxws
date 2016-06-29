package com.clkio.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.DurationUtil;
import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.ManualEnteringRowMapper;
import com.clkio.rowmapper.RowMapperUtil;

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
				+ " FROM MANUAL_ENTERING WHERE ID_MANUAL_ENTERING_REASON = ? "
				+ " ORDER BY ID " ,
				new Object[]{ manualEnteringReason.getId() }, new ManualEnteringRowMapper() );
	}

	public List< ManualEntering > listBy( final Day day ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, ID_MANUAL_ENTERING_REASON, TIME_INTERVAL "
				+ " FROM MANUAL_ENTERING WHERE ID_DAY = ? "
				+ " ORDER BY ID " ,
				new Object[]{ day.getId() }, new ManualEnteringRowMapper() );
	}

	public ManualEntering get( final Profile profile, final ManualEntering manualEntering ) {
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

	public boolean update( final ManualEntering manualEntering ) {
		return this.jdbcTemplate.update( " 	UPDATE MANUAL_ENTERING SET ID_MANUAL_ENTERING_REASON = ?, TIME_INTERVAL = ? "
				+ " WHERE ID = ? AND ID_DAY = ? ",
				new Object[]{ 
						manualEntering.getReason().getId(),
						DurationUtil.durationToPG( manualEntering.getTimeInterval() ),
						manualEntering.getId(),
						manualEntering.getDay().getId() }) == 1;
	}

	public List< ManualEntering > list( Profile profile, LocalDate startDate, LocalDate endDate ) {
		return this.jdbcTemplate.query( " SELECT ME.ID, ME.ID_DAY, ME.ID_MANUAL_ENTERING_REASON, ME.TIME_INTERVAL, D.DATE, MER.REASON "
				+ " FROM MANUAL_ENTERING ME "
				+ " JOIN MANUAL_ENTERING_REASON MER ON ME.ID_MANUAL_ENTERING_REASON = MER.ID "
				+ " JOIN DAY D ON ME.ID_DAY = D.ID "
				+ " WHERE D.ID_PROFILE = ? AND D.DATE >= ? AND D.DATE <= ? "
				+ " ORDER BY ME.ID " ,
				new Object[]{
					profile.getId(),
					LocalDateTimeUtil.getTimestamp( startDate ),
					LocalDateTimeUtil.getTimestamp( endDate )
				}, 
				new ManualEnteringRowMapper(){
					@Override
					public ManualEntering mapRow( ResultSet rs, int rowNum ) throws SQLException {
						ManualEntering manualEntering = super.mapRow( rs, rowNum );
						manualEntering.getDay().setDate( RowMapperUtil.getLocalDate( rs, "DATE" ) );
						manualEntering.getReason().setReason( rs.getString( "REASON" ) );
						
						return manualEntering;
					}
				} );
	}
	
}
