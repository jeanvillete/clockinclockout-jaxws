package com.clkio.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.Profile;
import com.clkio.repository.ClockinClockoutRepository;
import com.clkio.service.ClockinClockoutService;

@Service
public class ClockinClockoutServiceImpl implements ClockinClockoutService, InitializingBean {

	@Autowired
	private ClockinClockoutRepository repository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property 'repository' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final ClockinClockout clockinClockout ) {
		Assert.notNull( clockinClockout, "Argument 'clockinClockout' is mandatory." );
		Assert.state( clockinClockout.getDay() != null && clockinClockout.getDay().getId() != null,
				"Nested property 'day' and its deeper nested 'id' property are mandatory." );
		
		this.repository.insert( clockinClockout );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile, final ClockinClockout clockinClockout ) {
		Assert.state( clockinClockout != null && clockinClockout.getId() != null,
				"Argument 'clockinClockout' and its 'id' property are mandatory." );
		Assert.state( this.repository.delete( profile, clockinClockout ), 
				"Some problem happened while deleting the 'clockinClockout' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ClockinClockout > listBy( final Day day ) {
		Assert.notNull( day );
		return this.repository.listBy( day );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public ClockinClockout getNewest( final Day day ) {
		Assert.state( day != null && day.getId() != null, 
				"Argument 'day' and its nested 'id' property are mandatory." );
		try {
			return this.repository.getNewest( day );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final ClockinClockout clockinClockout ) {
		Assert.state( clockinClockout != null && clockinClockout.getId() != null,
				"Argument 'clockinClockout' and its 'id' property are mandatory." );
		Assert.state( this.repository.update( clockinClockout ),
				"Some problem happened while performin update for the 'clockinClockout' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public ClockinClockout get( final Profile profile, final ClockinClockout clockinClockout ) {
		Assert.state( clockinClockout != null && clockinClockout.getId() != null,
				"Argument 'clockinClockout' and its property 'id' are mandatory." );
		try {
			return this.repository.get( profile, clockinClockout );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ClockinClockout > list( Profile profile, LocalDate startDate, LocalDate endDate ) {
		Assert.notNull( profile, "Argument 'profile' is mandatory." );
		Assert.notNull( profile.getId(), "Argument 'profile's id property is mandatory." );
		Assert.notNull( startDate, "Argument 'startDate' is mandatory." );
		Assert.notNull( endDate, "Argument 'endDate' is mandatory." );
		
		return this.repository.list( profile, startDate, endDate );
	}

}
