package com.clkio.repository;

import java.sql.SQLException;
import java.time.Duration;

import javax.sql.DataSource;

import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

abstract class CommonRepository implements InitializingBean {

	JdbcTemplate jdbcTemplate;
	
	@Override
	public final void afterPropertiesSet() throws Exception {
		Assert.notNull( this.jdbcTemplate, "The field jdbcTemplate shouldn't be null." );
	}
	
	@Autowired
	@Qualifier( "dataSource" )
	final void setDataSource( DataSource dataSource ) {
		Assert.notNull( dataSource, "Argument dataSource cannot be null." );
		this.jdbcTemplate = new JdbcTemplate( dataSource );
	}
	
	Integer nextVal( String sequence ) {
		Assert.hasText( sequence, "Argument sequence cannot be null nor empty.");
		return this.jdbcTemplate.queryForObject( " select nextval( ? ) ", new Object[]{ sequence }, Integer.class);
	}
	
	static PGInterval durationToPG( Duration duration ) {
		try {
			return duration == null ? null : new PGInterval( duration.toString().replace( "PT", "" ).replace( "H", " hour " ).replace( "M", " min " ).replace( "S", " sec " ).trim() );
		} catch ( SQLException e ) {
			throw new RuntimeException( e );
		}
	}
}
