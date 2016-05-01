package com.clkio.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;
import com.clkio.repository.ManualEnteringReasonRepository;
import com.clkio.service.ManualEnteringReasonService;
import com.clkio.service.ManualEnteringService;

@Service
public class ManualEnteringReasonServiceImpl implements ManualEnteringReasonService, InitializingBean {

	@Autowired
	private ManualEnteringReasonRepository repository;
	
	@Autowired
	private ManualEnteringService manualEnteringService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property 'repository' has not been properly initialized." );
		Assert.state( manualEnteringService != null, "The property 'manualEnteringService' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void insert( final ManualEnteringReason manualEnteringReason ) throws PersistenceException, ValidationException, ConflictException {
		if( manualEnteringReason == null || manualEnteringReason.getProfile() == null ) 
			throw new ValidationException( "Argument 'manualEnteringReason' and its nested 'id' property are mandatory." );
		if( this.exists( manualEnteringReason.getReason(), manualEnteringReason.getProfile() ) )
			throw new ConflictException( "It already exists a record with the provided 'reason'." );
		if( !this.repository.insert( manualEnteringReason ) )
			throw new PersistenceException( "It was not possible performing insert for 'reason' record." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void delete( final ManualEnteringReason manualEnteringReason ) throws PersistenceException, ValidationException {
		if( manualEnteringReason == null )
			throw new ValidationException( "Argument 'manualEnteringReason' is mandatory." );
	
		List< ManualEntering > listManualEntering = this.manualEnteringService.listBy( manualEnteringReason );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering manualEntering : listManualEntering )
				this.manualEnteringService.delete( manualEnteringReason.getProfile(), manualEntering );
		
		if( !this.repository.delete( manualEnteringReason ) )
			throw new PersistenceException( "It was not possible performing delete for 'reason' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEnteringReason > list( final Profile profile ) throws ValidationException {
		if( profile == null || profile.getUser() == null )
			throw new ValidationException( "The argument 'profile' and its nested 'user' property are mandatory." );
		return this.repository.list( profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( final String reason, final Profile profile ) {
		return this.repository.exists( reason, profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( final String reason, final Profile profile, final Integer id ) {
		return this.repository.exists( reason, profile, id );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void update( final ManualEnteringReason manualEnteringReason ) throws ValidationException, ConflictException, PersistenceException {
		if( manualEnteringReason == null || manualEnteringReason.getId() == null )
			throw new ValidationException( "Argument 'manualEnteringReason' and its 'id' property are mandatory." );
		if( exists( manualEnteringReason.getReason(), manualEnteringReason.getProfile(), manualEnteringReason.getId() ) )
			throw new ConflictException( "It already exists another 'manualEnteringReason' with the same 'reason' description." );
		if( !this.repository.update( manualEnteringReason ) )
			throw new PersistenceException( "It was not possible performing update for 'reason' record." );
	}

}
