package org.com.clockinclockout.repository;

import org.com.clockinclockout.domain.RequestResetPassword;
import org.com.clockinclockout.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class RequestResetPasswordRepository extends CommonRepository {

	public void insert( RequestResetPassword requestResetPassword ) {
		requestResetPassword.setId( this.nextVal( "REQUEST_RESET_PASSWORD_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO REQUEST_RESET_PASSWORD "
				+ " ( ID, ID_CLK_USER, REQUEST_CODE_VALUE, REQUEST_DATE, CONFIRMATION_CODE_VALUE, CONFIRMATION_DATE, CHANGE_DATE ) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ? ) ",
				new Object[]{ 
					requestResetPassword.getId(),
					requestResetPassword.getUser().getId(),
					requestResetPassword.getRequestCodeValue(),
					requestResetPassword.getRequestDate(),
					requestResetPassword.getConfirmationCodeValue(),
					requestResetPassword.getConfirmationDate(),
					requestResetPassword.getChangeDate()
				});
	}

	public void deleteNotConfirmed( User user ) {
		this.jdbcTemplate.update( " DELETE FROM REQUEST_RESET_PASSWORD "
				+ " WHERE ID_CLK_USER = ? AND "
				+ " ( CONFIRMATION_CODE_VALUE IS NULL OR CONFIRMATION_DATE IS NULL OR CHANGE_DATE IS NULL ) ",
				new Object[]{ user.getId() });
	}
	
}
