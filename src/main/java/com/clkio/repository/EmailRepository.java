package com.clkio.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.rowmapper.EmailRowMapper;

@Repository
public class EmailRepository extends CommonRepository {

	public boolean insert( final Email email ) {
		email.setId( this.nextVal( "EMAIL_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO EMAIL ( ID, ID_CLK_USER, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ",
				new Object[]{ email.getId(),
						email.getUser().getId(),
						email.getAddress(),
						LocalDateTimeUtil.getTimestamp( email.getRecordedTime() ),
						email.getConfirmationCode(),
						LocalDateTimeUtil.getTimestamp( email.getConfirmationDate() ),
						email.isPrimary() }) == 1;
	}

	public boolean exists( final Email email ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM EMAIL WHERE ADDRESS = ? ", new Object[]{ email.getAddress() }, Integer.class ) > 0;
	}
	
	public boolean confirm( final Email email, final LocalDateTime validRange ) {
		return this.jdbcTemplate.update( " UPDATE EMAIL SET CONFIRMATION_DATE = ? "
				+ " WHERE RECORDED_TIME > ? AND ADDRESS = ? AND CONFIRMATION_CODE = ? AND CONFIRMATION_DATE IS NULL ",
				new Object[]{
					LocalDateTimeUtil.getTimestamp( LocalDateTime.now() ),
					LocalDateTimeUtil.getTimestamp( validRange ),
					email.getAddress(),
					email.getConfirmationCode()
				}) == 1;
	}

	/**
	 * List all the email confirmation request related to primaries ones which were confirmed by the user.
	 * @param date 
	 * @return
	 */
	public List< Email > listPrimaryNotConfirmed( final LocalDateTime date ) {
		return this.jdbcTemplate.query(  " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE CONFIRMATION_DATE IS NULL AND RECORDED_TIME < ? AND IS_PRIMARY = ? ",
				new Object[]{ LocalDateTimeUtil.getTimestamp( date ), true },
				new EmailRowMapper() );
	}

	public boolean delete( final Email email ) {
		return this.jdbcTemplate.update( " DELETE FROM EMAIL WHERE ID = ? AND ID_CLK_USER = ? ", new Object[]{ email.getId(), email.getUser().getId() } ) == 1;
	}

	public void deleteNotPrimaryNotConfirmed( final LocalDateTime date ) {
		this.jdbcTemplate.update( " DELETE FROM EMAIL WHERE CONFIRMATION_DATE IS NULL AND RECORDED_TIME < ? AND IS_PRIMARY = ? ",
				new Object[]{ LocalDateTimeUtil.getTimestamp( date ), false } );
	}

	public Email getBy( final String emailAddress, final boolean isPrimary ) {
		return this.jdbcTemplate.queryForObject( " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE CONFIRMATION_DATE IS NOT NULL AND ADDRESS = ? AND IS_PRIMARY = ? ",
				new Object[]{ emailAddress, isPrimary },
				new EmailRowMapper() );
	}

	public List< Email > list( final User user ) {
		return this.jdbcTemplate.query(  " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE ID_CLK_USER = ? ",
				new Object[]{ user.getId() },
				new EmailRowMapper() );
	}

	public Email get( final Email email ) {
		return this.jdbcTemplate.queryForObject( " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE ID = ? AND ID_CLK_USER = ? ",
				new Object[]{ email.getId(), email.getUser().getId() },
				new EmailRowMapper() );
	}
	
	public boolean unsetPrimary( final User user ) {
		return this.jdbcTemplate.update( " UPDATE EMAIL SET IS_PRIMARY = ? WHERE ID_CLK_USER = ? ", new Object[]{ false, user.getId() } ) > 0;
	}

	public boolean setAsPrimary( final Email email ) {
		return this.jdbcTemplate.update( " UPDATE EMAIL SET IS_PRIMARY = ? WHERE ID = ? AND ID_CLK_USER = ? ", new Object[]{ true, email.getId(), email.getUser().getId() } ) == 1;
	}

}
