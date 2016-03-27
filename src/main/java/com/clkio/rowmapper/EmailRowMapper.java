package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.Email;
import com.clkio.domain.User;

public class EmailRowMapper implements RowMapper< Email > {

	@Override
	public Email mapRow( ResultSet rs, int rowNum ) throws SQLException {
		Email email = new Email( RowMapperUtil.getInteger( rs, "ID" ), rs.getString( "ADDRESS" ) );
		email.setRecordedTime( RowMapperUtil.getLocalDateTime( rs, "RECORDED_TIME" ) );
		email.setConfirmationCode( rs.getString( "CONFIRMATION_CODE" ) );
		email.setConfirmationDate( RowMapperUtil.getLocalDateTime( rs, "CONFIRMATION_DATE" ) );
		email.setPrimary( RowMapperUtil.getBoolean( rs, "IS_PRIMARY" ) );
		
		User user = new User( RowMapperUtil.getInteger( rs, "ID_CLK_USER" ) );
		email.setUser( user );
		
		return email;
	}

}
