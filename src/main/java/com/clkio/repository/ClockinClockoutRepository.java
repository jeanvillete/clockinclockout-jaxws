package com.clkio.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.ClockinClockoutRowMapper;

@Repository
public class ClockinClockoutRepository extends CommonRepository {

	public void insert( final ClockinClockout clockinClockout ) {
		clockinClockout.setId( this.nextVal( "CLOCKINCLOCKOUT_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO CLOCKINCLOCKOUT ( ID, ID_DAY, CLOCKIN, CLOCKOUT ) "
				+ " VALUES ( ?, ?, ?, ?) ",
				new Object[]{ clockinClockout.getId(),
						clockinClockout.getDay().getId(),
						LocalDateTimeUtil.getTimestamp( clockinClockout.getClockin() ),
						LocalDateTimeUtil.getTimestamp( clockinClockout.getClockout() ) });
	}

	public boolean delete( final Profile profile, final ClockinClockout clockinClockout ) {
		return this.jdbcTemplate.update( " DELETE FROM CLOCKINCLOCKOUT WHERE ID = "
				+ " ( SELECT CLKIO.ID FROM CLOCKINCLOCKOUT CLKIO "
				+ " JOIN DAY D ON CLKIO.ID_DAY = D.ID "
				+ " WHERE D.ID_PROFILE = ? AND CLKIO.ID = ? ) ",
				new Object[]{
						profile.getId(),
						clockinClockout.getId() } ) == 1;
	}

	public List< ClockinClockout > listBy( final Day day ) {
		return this.jdbcTemplate.query( " SELECT ID, ID_DAY, CLOCKIN, CLOCKOUT "
				+ " FROM CLOCKINCLOCKOUT WHERE ID_DAY = ? ",
				new Object[]{ day.getId() }, new ClockinClockoutRowMapper() );
	}

	public ClockinClockout getNewest( final Day day ) {
		return this.jdbcTemplate.queryForObject( " SELECT CLKIO.ID, CLKIO.ID_DAY, CLKIO.CLOCKIN, CLKIO.CLOCKOUT "
				+ " FROM CLOCKINCLOCKOUT CLKIO "
				+ " JOIN ( SELECT MAX( ID ) ID FROM CLOCKINCLOCKOUT WHERE ID_DAY = ? ) CLKIOMAX ON CLKIO.ID = CLKIOMAX.ID ",
				new Object[]{ day.getId() }, new ClockinClockoutRowMapper() );
	}

	public boolean update( final ClockinClockout clockinClockout ) {
		return this.jdbcTemplate.update( " UPDATE CLOCKINCLOCKOUT SET CLOCKIN = ?, CLOCKOUT = ?"
				+ " WHERE ID = ? AND ID_DAY = ? ",
				new Object[]{
						LocalDateTimeUtil.getTimestamp( clockinClockout.getClockin() ),
						LocalDateTimeUtil.getTimestamp( clockinClockout.getClockout() ),
						clockinClockout.getId(),
						clockinClockout.getDay().getId() }) == 1;
	}

	public ClockinClockout get( final Profile profile, final ClockinClockout clockinClockout ) {
		return this.jdbcTemplate.queryForObject( " SELECT CLKIO.ID, CLKIO.ID_DAY, CLKIO.CLOCKIN, CLKIO.CLOCKOUT "
				+ " FROM CLOCKINCLOCKOUT CLKIO "
				+ " JOIN DAY D ON CLKIO.ID_DAY = D.ID "
				+ " WHERE D.ID_PROFILE = ? AND CLKIO.ID = ? ",
				new Object[]{
						profile.getId(),
						clockinClockout.getId() }
				, new ClockinClockoutRowMapper() );
	}

}
