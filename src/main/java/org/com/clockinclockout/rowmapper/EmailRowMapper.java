package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.User;
import org.springframework.jdbc.core.RowMapper;

public class EmailRowMapper implements RowMapper< Email > {

	@Override
	public Email mapRow( ResultSet rs, int rowNum ) throws SQLException {
		Email email = new Email( RowMapperUtil.getInteger( rs, "ID" ), rs.getString( "ADDRESS" ) );
		email.setRecordedTime( rs.getTimestamp( "RECORDED_TIME" ) );
		email.setConfirmationCode( rs.getString( "CONFIRMATION_CODE" ) );
		email.setConfirmationDate( rs.getTimestamp( "CONFIRMATION_DATE" ) );
		email.setPrimary( RowMapperUtil.getBoolean( rs, "IS_PRIMARY" ) );
		
		User user = new User( RowMapperUtil.getInteger( rs, "ID_CLK_USER" ) );
		email.setUser( user );
		
		return email;
	}

}
