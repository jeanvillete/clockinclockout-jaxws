package com.clkio.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.Profile;
import com.clkio.rowmapper.RowMapperUtil;

@Repository
public class TimeCardRepository extends CommonRepository {

	public Duration getTotalTime( Profile profile, LocalDate until ) {
		return this.jdbcTemplate.queryForObject(
				" SELECT SUM ( TOTAL_INTERVALS.TIME_INTERVAL ) TOTAL_TIME FROM " +
				" (  " +
				"     SELECT ADJ.TIME_INTERVAL FROM ADJUSTING ADJ WHERE ADJ.ID_PROFILE = ? " +
				"   UNION " +
				"     SELECT SUM( D_INNER.TIME_INTERVAL ) - D_OUT.EXPECTED_HOURS AS TIME_INTERVAL FROM DAY D_OUT " +
				"     JOIN ( " +
				"         SELECT D.ID, ( CLKIO.CLOCKOUT - CLKIO.CLOCKIN ) AS TIME_INTERVAL FROM CLOCKINCLOCKOUT CLKIO " +
				"         JOIN DAY D ON CLKIO.ID_DAY = D.ID " +
				"         WHERE CLKIO.CLOCKIN IS NOT NULL AND CLKIO.CLOCKOUT IS NOT NULL AND D.ID_PROFILE = ? AND ( COALESCE( ? ) IS NULL OR D.DATE <= ? ) " +
				"       UNION " +
				"         SELECT D.ID, MAN_E.TIME_INTERVAL AS TIME_INTERVAL FROM MANUAL_ENTERING MAN_E " +
				"         JOIN DAY D ON MAN_E.ID_DAY = D.ID " +
				"         WHERE D.ID_PROFILE = ? AND ( COALESCE( ? ) IS NULL OR D.DATE <= ? ) " +
				"     ) AS D_INNER ON D_OUT.ID = D_INNER.ID " +
				"     GROUP BY D_OUT.ID " +
				" ) AS TOTAL_INTERVALS ",
		new Object[]{
			profile.getId(),
			profile.getId(), LocalDateTimeUtil.getTimestamp( until ), LocalDateTimeUtil.getTimestamp( until ),
			profile.getId(), LocalDateTimeUtil.getTimestamp( until ), LocalDateTimeUtil.getTimestamp( until )
		},
		new RowMapper< Duration >() {
			@Override
			public Duration mapRow( ResultSet rs, int rowNum ) throws SQLException {
				return RowMapperUtil.getDuration( rs, "TOTAL_TIME" );
			}
		});
	}

}
