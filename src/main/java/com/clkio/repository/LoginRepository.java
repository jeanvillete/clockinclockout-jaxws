package com.clkio.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.clkio.common.LocalDateTimeUtil;
import com.clkio.domain.User;

@Repository
public class LoginRepository extends CommonRepository {

	public void insert( final User user, String code, LocalDateTime since, String ip ) {
		this.jdbcTemplate.update( " INSERT INTO LOGIN ( ID, ID_CLK_USER, CODE, SINCE, IP, VALID ) "
				+ " VALUES ( NEXTVAL( ? ), ?, ?, ?, ?, ? ) ",
				new Object[]{
						"LOGIN_SEQ", 
						user.getId(), 
						code, 
						LocalDateTimeUtil.getTimestamp( since ), 
						ip, 
						true });
	}

	public boolean check( String code ) {
		return this.jdbcTemplate.queryForObject( " SELECT COUNT( ID ) FROM LOGIN WHERE CODE = ? AND VALID = ? ", 
				new Object[]{ code, true },
				Integer.class ) == 1;
	}

	public void setAsInvalid( User user ) {
		this.jdbcTemplate.update( " UPDATE LOGIN SET VALID = ? WHERE ID_CLK_USER = ? ",
				new Object[]{
					false,
					user.getId()
				});
	}

	public boolean logout( String code ) {
		return this.jdbcTemplate.update( " UPDATE LOGIN SET VALID = ? WHERE CODE = ? ",
				new Object[]{
					false,
					code
				}) == 1;
	}

}
