package org.com.clockinclockout.repository;

import org.com.clockinclockout.domain.RequestResetPassword;
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
	
}
