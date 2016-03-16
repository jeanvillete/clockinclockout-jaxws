package com.clkio.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.rowmapper.EmailRowMapper;

@Repository
public class EmailRepository extends CommonRepository {

	public void insert( final Email email ) {
		email.setId( this.nextVal( "EMAIL_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO EMAIL ( ID, ID_CLK_USER, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ",
				new Object[]{ email.getId(),
						email.getUser().getId(),
						email.getAddress(),
						email.getRecordedTime(),
						email.getConfirmationCode(),
						email.getConfirmationDate(),
						email.isPrimary(), });
	}

	public boolean exists( final Email email ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM EMAIL WHERE ADDRESS = ? ", new Object[]{ email.getAddress() }, Integer.class ) > 0;
	}
	
	public boolean confirm( final Email email, final Date validRange ) {
		return this.jdbcTemplate.update( " UPDATE EMAIL SET CONFIRMATION_DATE = ? "
				+ " WHERE RECORDED_TIME > ? AND ADDRESS = ? AND CONFIRMATION_CODE = ? AND CONFIRMATION_DATE IS NULL ",
				new Object[]{
					new Date(),
					validRange,
					email.getAddress(),
					email.getConfirmationCode()
				}) == 1;
	}

	/**
	 * List all the email confirmation request related to primaries ones which were confirmed by the user.
	 * @param date 
	 * @return
	 */
	public List< Email > listPrimaryNotConfirmed( final Date date ) {
		return this.jdbcTemplate.query(  " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE CONFIRMATION_DATE IS NULL AND RECORDED_TIME < ? AND IS_PRIMARY = ? ",
				new Object[]{ date, true },
				new EmailRowMapper() );
	}

	public void delete( final Email email ) {
		this.jdbcTemplate.update( " DELETE FROM EMAIL WHERE ID = ? ", new Object[]{ email.getId() } );
	}

	public void deleteNotPrimaryNotConfirmed( final Date date ) {
		this.jdbcTemplate.update( " DELETE FROM EMAIL WHERE CONFIRMATION_DATE IS NULL AND RECORDED_TIME < ? AND IS_PRIMARY = ? ",
				new Object[]{ date, false } );
	}

	public Email getBy( final String emailAddress, final boolean isPrimary ) {
		return this.jdbcTemplate.queryForObject( " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE CONFIRMATION_DATE IS NOT NULL AND ADDRESS = ? AND IS_PRIMARY = ? ",
				new Object[]{ emailAddress, isPrimary },
				new EmailRowMapper() );
	}

	public List< Email > list( User user ) {
		return this.jdbcTemplate.query(  " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE ID_CLK_USER = ? ",
				new Object[]{ user.getId() },
				new EmailRowMapper() );
	}

}
