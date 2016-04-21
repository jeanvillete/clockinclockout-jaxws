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

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.repository.ManualEnteringRepository;
import com.clkio.service.ManualEnteringService;

@Service
public class ManualEnteringServiceImpl implements ManualEnteringService, InitializingBean {

	@Autowired
	private ManualEnteringRepository repository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property repository has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason ) throws ValidationException {
		if( manualEnteringReason == null )
			throw new ValidationException( "Argument 'manualEnteringReason' is mandatory." );
		return this.repository.listBy( manualEnteringReason );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final ManualEntering manualEntering ) throws ValidationException, PersistenceException {
		if( manualEntering == null )
			throw new ValidationException( "Argument 'manualEntering' is mandatory." );
		if( manualEntering.getDay() == null || manualEntering.getDay().getId() == null )
			throw new ValidationException( "Nested 'day' and its 'date' properties are mandatory." );
		if( manualEntering.getReason() == null || manualEntering.getReason().getId() == null )
			throw new ValidationException( "Nested 'reason' and its 'id' properties are mandatory." );
		if( manualEntering.getTimeInterval() == null )
			throw new ValidationException( "Nested 'timeInterval' property is mandatoy." );
		if( !this.repository.insert( manualEntering ) )
			throw new PersistenceException( "It was not possible performing insert for 'manualEntering' record." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile, final ManualEntering manualEntering ) throws ValidationException, PersistenceException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( manualEntering == null || manualEntering.getId() == null )
			throw new ValidationException( "Argument 'manualEntering' and its 'id' property are mandatory." );
		if( !this.repository.delete( profile, manualEntering ) )
			throw new PersistenceException( "It was not possible performing delete for 'manualEntering' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEntering > listBy( final Day day ) throws ValidationException {
		if( day == null)
			throw new ValidationException( "Argument 'day' is mandatory." );
		return this.repository.listBy( day );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public ManualEntering get( final Profile profile, final ManualEntering manualEntering ) throws ValidationException, PersistenceException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its 'id' property are mandatory." );
		if( manualEntering == null || manualEntering.getId() == null )
			throw new ValidationException( "Argument 'manualEntering' and its 'id' property are mandatory." );
		try {
			return this.repository.get( profile, manualEntering );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final ManualEntering manualEntering ) throws ValidationException, PersistenceException {
		if( manualEntering == null )
			throw new ValidationException( "Argument 'manualEntering' is mandatory." );
		if( manualEntering.getDay() == null || manualEntering.getDay().getId() == null )
			throw new ValidationException( "Nested 'day' and its 'date' properties are mandatory." );
		if( manualEntering.getReason() == null || manualEntering.getReason().getId() == null )
			throw new ValidationException( "Nested 'reason' and its 'id' properties are mandatory." );
		if( manualEntering.getTimeInterval() == null )
			throw new ValidationException( "Nested 'timeInterval' property is mandatoy." );
		if( !this.repository.update( manualEntering ) )
			throw new PersistenceException( "It was not possible performing update for 'manualEntering' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEntering > list( Profile profile, LocalDate startDate, LocalDate endDate ) throws ValidationException {
		if( profile == null || profile.getId() == null )
			throw new ValidationException( "Argument 'profile' and its nested 'id' property are mandatory." );
		if( startDate == null )
			throw new ValidationException( "Argument 'startDate' is mandatory." );
		if( endDate == null )
			throw new ValidationException( "Argument 'endDate' is mandatory." );
		
		return this.repository.list( profile, startDate, endDate );
	}

}
