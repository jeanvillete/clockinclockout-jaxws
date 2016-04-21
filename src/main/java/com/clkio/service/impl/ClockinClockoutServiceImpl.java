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
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
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
	public void insert( final ClockinClockout clockinClockout ) throws ValidationException, PersistenceException {
		if( clockinClockout == null )
			throw new ValidationException( "Argument 'clockinClockout' is mandatory." );
		if( clockinClockout.getDay() == null || clockinClockout.getDay().getId() == null )
			throw new ValidationException( "Nested property 'day' and its deeper nested 'id' property are mandatory." );
		if( !this.repository.insert( clockinClockout ) )
			throw new PersistenceException( "It was not possible performing insert for 'clockinClockout' record." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile, final ClockinClockout clockinClockout ) throws ValidationException, PersistenceException {
		if( clockinClockout == null || clockinClockout.getId() == null )
			throw new ValidationException( "Argument 'clockinClockout' and its 'id' property are mandatory." );
		if( !this.repository.delete( profile, clockinClockout ) ) 
			throw new PersistenceException( "Some problem happened while deleting the 'clockinClockout' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ClockinClockout > listBy( final Day day ) throws ValidationException {
		if( day == null ) 
			throw new ValidationException( "Argument 'day' is mandatory." );
		return this.repository.listBy( day );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public ClockinClockout getNewest( final Day day ) throws ValidationException, PersistenceException {
		if( day == null || day.getId() == null ) 
			throw new ValidationException( "Argument 'day' and its nested 'id' property are mandatory." );
		try {
			return this.repository.getNewest( day );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final ClockinClockout clockinClockout ) throws ValidationException, PersistenceException {
		if( clockinClockout == null || clockinClockout.getId() == null )
			throw new ValidationException( "Argument 'clockinClockout' and its 'id' property are mandatory." );
		if ( !this.repository.update( clockinClockout ) )
			throw new PersistenceException( "It was not possible performing update for 'clockinClockout' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public ClockinClockout get( final Profile profile, final ClockinClockout clockinClockout ) throws PersistenceException, ValidationException {
		if( clockinClockout == null || clockinClockout.getId() == null )
			throw new ValidationException( "Argument 'clockinClockout' and its property 'id' are mandatory." );
		try {
			return this.repository.get( profile, clockinClockout );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ClockinClockout > list( Profile profile, LocalDate startDate, LocalDate endDate ) throws ValidationException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its nested 'id' property are mandatory." );
		if( startDate == null)
			throw new ValidationException( "Argument 'startDate' is mandatory." );
		if( endDate == null )
			throw new ValidationException( "Argument 'endDate' is mandatory." );

		return this.repository.list( profile, startDate, endDate );
	}

}
