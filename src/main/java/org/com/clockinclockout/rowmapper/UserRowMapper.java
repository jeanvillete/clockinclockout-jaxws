package org.com.clockinclockout.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.com.clockinclockout.domain.User;
import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper< User > {

	@Override
	public User mapRow( ResultSet rs, int rowNum ) throws SQLException {
		User user = new User( RowMapperUtil.getInteger( rs, "ID" ) );
		user.setLocale( new Locale( rs.getString( "locale" ) ) );
		
		return user;
	}

}
