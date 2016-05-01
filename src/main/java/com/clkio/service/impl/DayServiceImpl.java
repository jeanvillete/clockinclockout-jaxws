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
import org.springframework.util.CollectionUtils;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.repository.DayRepository;
import com.clkio.service.ClockinClockoutService;
import com.clkio.service.DayService;
import com.clkio.service.ManualEnteringService;

@Service
public class DayServiceImpl implements DayService, InitializingBean {

	@Autowired
	private DayRepository repository;
	
	@Autowired
	private ManualEnteringService manualEnteringService;
	
	@Autowired
	private ClockinClockoutService clockinClockoutService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property 'repository' has not been properly initialized." );
		Assert.state( manualEnteringService != null, "The property 'manualEnteringService' has not been properly initialized." );
		Assert.state( clockinClockoutService != null, "The property 'clockinClockoutService' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void insert( final Day day ) throws PersistenceException, ValidationException {
		if( day == null )
			throw new ValidationException( "Argument 'day' is mandatory." );
		if( day.getDate() == null )
			throw new ValidationException( "Nested argument's property 'date' cannot be null." );
		if( day.getExpectedHours() == null )
			throw new ValidationException( "Nested argument's property 'expectedHours' cannot be null." );
		if( day.getProfile() == null || day.getProfile().getId() == null ) 
			throw new ValidationException( "Nested argument's property 'profile' alongside its 'id' attribute cannot be null." );
		if( !this.repository.insert( day ) ) 
			throw new PersistenceException( "It was not possible performing insert for 'day' record." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void update( final Day day ) throws ValidationException, PersistenceException {
		if( day == null || day.getId() == null )
			throw new ValidationException( "Argument 'day' and its 'id' property are mandatory." );
		if( day.getExpectedHours() == null )
			throw new ValidationException( "Nested argument's property 'expectedHours' cannot be null." );
		if( day.getProfile() == null || day.getProfile().getId() == null ) 
			throw new ValidationException( "Nested argument's property 'profile' alongside its 'id' attribute cannot be null." );
		if( !this.repository.update( day ) )
			throw new PersistenceException( "Some problem happened while performnig update for 'day' record." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void delete( final Day day ) throws ValidationException, PersistenceException {
		if( day == null )
			throw new ValidationException( "Argument 'day' is mandatory." );
		
		List< ClockinClockout > listClockinClockout = this.clockinClockoutService.listBy( day );
		if ( !CollectionUtils.isEmpty( listClockinClockout ) )
			for ( ClockinClockout clockinClockout : listClockinClockout )
				this.clockinClockoutService.delete( day.getProfile(), clockinClockout );
		
		List< ManualEntering > listManualEntering = this.manualEnteringService.listBy( day );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering manualEntering : listManualEntering )
				this.manualEnteringService.delete( day.getProfile(),manualEntering );
		
		if( !this.repository.delete( day ) )
			throw new PersistenceException( "It was not possible performing delete for 'day' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public List< Day > listBy( final Profile profile ) throws ValidationException {
		if( profile == null )
			throw new ValidationException( "Argument 'profile' is mandatory." );
		return this.repository.listBy( profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public Day get( final Profile profile, final LocalDate localDateDay ) throws ValidationException, PersistenceException {
		if( profile == null )
			throw new ValidationException( "Argument 'profile' is mandatory." );
		if( localDateDay == null )
			throw new ValidationException( "Argument 'localDateDay' is mandatory." );
		try {
			return this.repository.get( profile, localDateDay );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public Day get( final Day day ) throws ValidationException, PersistenceException {
		if( day == null || day.getId() == null ) 
			throw new ValidationException( "Argument 'day' and its 'id' property are mandatory." );
		try {
			return this.repository.get( day );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public List< Day > list( Profile profile, LocalDate startDate, LocalDate endDate ) throws ValidationException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( startDate == null )
			throw new ValidationException( "Argument 'startDate' is mandatory." );
		if( endDate == null )
			throw new ValidationException( "Argument 'endDate' is mandatory." );
		
		return this.repository.list( profile, startDate, endDate );
	}

}
