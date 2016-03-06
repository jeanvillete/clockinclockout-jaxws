package org.com.clockinclockout.repository;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.rowmapper.UserRowMapper;
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

	public User getBy( final Email email ) {
		return this.jdbcTemplate.queryForObject( " SELECT USR.ID, USR.LOCALE, USR.PASSWORD FROM CLK_USER USR "
				+ " LEFT JOIN EMAIL EML ON USR.ID = EML.ID_CLK_USER "
				+ " WHERE EML.ID = ? ",
				new Object[]{ email.getId() },
				new UserRowMapper() );
	}

	public void delete( final User user ) {
		this.jdbcTemplate.update( " DELETE FROM CLK_USER WHERE ID = ? ", new Object[]{ user.getId() } );
	}

	public boolean changePassword( final User user ) {
		return this.jdbcTemplate.update( " UPDATE CLK_USER SET PASSWORD = ? WHERE ID = ? ",
				new Object[]{ user.getPassword(), user.getId() }) == 1;
	}
	
}
