package com.clkio.repository;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.clkio.domain.User;

@Repository
public class LoginRepository extends CommonRepository {

	public void insert( final User user, String code, Date since, String ip ) {
		this.jdbcTemplate.update( " INSERT INTO LOGIN ( ID, ID_CLK_USER, CODE, SINCE, IP, VALID ) "
				+ " VALUES ( NEXTVAL( ? ), ?, ?, ?, ?, ? ) ",
				new Object[]{ "LOGIN_SEQ", user.getId(), code, since, ip, true });
	}

	public boolean check( String code ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM LOGIN WHERE CODE = ? AND VALID = ? ", 
				new Object[]{ code, true },
				Integer.class ) == 1;
	}

}