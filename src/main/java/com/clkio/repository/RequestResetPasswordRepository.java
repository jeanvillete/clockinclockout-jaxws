package com.clkio.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.RequestResetPassword;
import com.clkio.domain.User;

@Repository
public class RequestResetPasswordRepository extends CommonRepository {

	public boolean insert( final RequestResetPassword requestResetPassword ) {
		requestResetPassword.setId( this.nextVal( "REQUEST_RESET_PASSWORD_SEQ" ) );
		return this.jdbcTemplate.update( " INSERT INTO REQUEST_RESET_PASSWORD "
				+ " ( ID, ID_CLK_USER, REQUEST_CODE_VALUE, REQUEST_DATE, CONFIRMATION_CODE_VALUE, CONFIRMATION_DATE, CHANGE_DATE ) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ? ) ",
				new Object[]{ 
					requestResetPassword.getId(),
					requestResetPassword.getUser().getId(),
					requestResetPassword.getRequestCodeValue(),
					LocalDateTimeUtil.getTimestamp( requestResetPassword.getRequestDate() ),
					requestResetPassword.getConfirmationCodeValue(),
					LocalDateTimeUtil.getTimestamp( requestResetPassword.getConfirmationDate() ),
					LocalDateTimeUtil.getTimestamp( requestResetPassword.getChangeDate() )
				}) == 1;
	}

	public void deleteNotConfirmed( final User user ) {
		this.jdbcTemplate.update( " DELETE FROM REQUEST_RESET_PASSWORD "
				+ " WHERE ID_CLK_USER = ? AND "
				+ " ( CONFIRMATION_CODE_VALUE IS NULL OR CONFIRMATION_DATE IS NULL OR CHANGE_DATE IS NULL ) ",
				new Object[]{ user.getId() });
	}

	public boolean confirm( final RequestResetPassword requestResetPassword, final LocalDateTime validRange ) {
		return this.jdbcTemplate.update( " UPDATE REQUEST_RESET_PASSWORD SET CONFIRMATION_CODE_VALUE = ?, CONFIRMATION_DATE = ? "
				+ " WHERE CONFIRMATION_CODE_VALUE IS NULL AND CONFIRMATION_DATE IS NULL AND CHANGE_DATE IS NULL "
				+ " AND REQUEST_DATE > ? "
				+ " AND REQUEST_CODE_VALUE = ? AND ID_CLK_USER = ? ",
				new Object[]{
						requestResetPassword.getConfirmationCodeValue(),
						LocalDateTimeUtil.getTimestamp( requestResetPassword.getConfirmationDate() ),
						LocalDateTimeUtil.getTimestamp( validRange ),
						requestResetPassword.getRequestCodeValue(),
						requestResetPassword.getUser().getId() }) == 1;
	}

	public void cleanNotConfirmed( final LocalDateTime date ) {
		this.jdbcTemplate.update( " DELETE FROM REQUEST_RESET_PASSWORD "
				+ " WHERE REQUEST_DATE < ? "
				+ " AND ( CONFIRMATION_CODE_VALUE IS NULL OR CONFIRMATION_DATE IS NULL OR CHANGE_DATE IS NULL ) ",
				new Object[]{ LocalDateTimeUtil.getTimestamp( date ) });
	}

	public boolean changePassword( final RequestResetPassword requestResetPassword, final LocalDateTime validRange ) {
		return this.jdbcTemplate.update( " UPDATE REQUEST_RESET_PASSWORD SET CHANGE_DATE = ? "
				+ " WHERE CONFIRMATION_CODE_VALUE = ?"
				+ " AND CONFIRMATION_DATE > ? "
				+ " AND CHANGE_DATE IS NULL "
				+ " AND ID_CLK_USER = ? ",
				new Object[]{
						LocalDateTimeUtil.getTimestamp( requestResetPassword.getChangeDate() ),
						requestResetPassword.getConfirmationCodeValue(),
						LocalDateTimeUtil.getTimestamp( validRange ),
						requestResetPassword.getUser().getId() }) == 1;
	}
	
}
