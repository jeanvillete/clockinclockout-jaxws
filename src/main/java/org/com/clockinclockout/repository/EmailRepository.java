package org.com.clockinclockout.repository;

import java.util.Date;

import org.com.clockinclockout.domain.Email;
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

}
