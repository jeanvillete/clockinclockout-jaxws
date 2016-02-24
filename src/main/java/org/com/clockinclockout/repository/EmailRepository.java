package org.com.clockinclockout.repository;

import java.util.Date;
import java.util.List;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.rowmapper.EmailRowMapper;
import org.springframework.stereotype.Repository;

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

	public boolean exists( Email email ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM EMAIL WHERE ADDRESS = ? ", new Object[]{ email.getAddress() }, Integer.class ) > 0;
	}
	
	public boolean confirm( Email email ) {
		return this.jdbcTemplate.update( " UPDATE EMAIL SET CONFIRMATION_DATE = ? WHERE ADDRESS = ? AND CONFIRMATION_CODE = ? AND CONFIRMATION_DATE IS NULL ",
				new Object[]{
					new Date(),
					email.getAddress(),
					email.getConfirmationCode()
				}) == 1;
	}

	/**
	 * List all the email confirmation request related to primaries ones which were confirmed by the user.
	 * @param date 
	 * @return
	 */
	public List< Email > listPrimaryNotConfirmed( Date date ) {
		return this.jdbcTemplate.query(  " SELECT ID, ADDRESS, RECORDED_TIME, CONFIRMATION_CODE, CONFIRMATION_DATE, IS_PRIMARY, ID_CLK_USER FROM EMAIL "
				+ " WHERE CONFIRMATION_DATE IS NULL AND RECORDED_TIME < ? AND IS_PRIMARY = ? ",
				new Object[]{ date, true },
				new EmailRowMapper() );
	}

	public void delete( Email email ) {
		this.jdbcTemplate.update( " DELETE FROM EMAIL WHERE ID = ? ", new Object[]{ email.getId() } );
	}

	public void deleteNotPrimaryNotConfirmed( Date date ) {
		this.jdbcTemplate.update( " DELETE FROM EMAIL WHERE CONFIRMATION_DATE IS NULL AND RECORDED_TIME < ? AND IS_PRIMARY = ? ",
				new Object[]{ date, false } );
	}

}
