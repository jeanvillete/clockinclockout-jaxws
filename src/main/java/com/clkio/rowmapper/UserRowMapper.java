package com.clkio.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import com.clkio.domain.User;

public class UserRowMapper implements RowMapper< User > {

	@Override
	public User mapRow( ResultSet rs, int rowNum ) throws SQLException {
		User user = new User( RowMapperUtil.getInteger( rs, "ID" ) );
		user.setLocale( new Locale( rs.getString( "LOCALE" ) ) );
		user.setPassword( rs.getString( "PASSWORD" ), false ); // the password is always encoded
		
		return user;
	}

}
