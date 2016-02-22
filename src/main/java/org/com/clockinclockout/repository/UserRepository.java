package org.com.clockinclockout.repository;

import org.com.clockinclockout.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends CommonRepository {

	public void insert( final User user ) {
		user.setId( this.nextVal( "CLK_USER_SEQ" ) );
		this.jdbcTemplate.update( " INSERT INTO CLK_USER( ID, LOCALE, PASSWORD ) VALUES ( ?, ?, ? ) ",
				new Object[]{ user.getId(),
						user.getLocale().getLanguage(),
						user.getPassword() });
	}
	
}
