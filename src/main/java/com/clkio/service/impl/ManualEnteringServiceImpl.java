package com.clkio.service.impl;

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
	public List< ManualEntering > listBy( ManualEnteringReason manualEnteringReason ) {
		Assert.notNull( manualEnteringReason );
		return this.repository.listBy( manualEnteringReason );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final ManualEntering manualEntering ) {
		Assert.notNull( manualEntering, "Argument 'manualEntering' is mandatory." );
		Assert.state( manualEntering.getDay() != null && manualEntering.getDay().getId() != null,
				"Nested 'day' and its 'date' properties are mandatory." );
		Assert.state( manualEntering.getReason() != null && manualEntering.getReason().getId() != null,
				"Nested 'reason' and its 'id' properties are mandatory." );
		Assert.notNull( manualEntering.getTimeInterval(), "Nested 'timeInterval' property is mandatoy." );
		Assert.state( this.repository.insert( manualEntering ),
				"Some problem happend while performing insert for 'manualEntering' record." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile, final ManualEntering manualEntering ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.state( manualEntering != null && manualEntering.getId() != null,
				"Argument 'manualEntering' and its 'id' property are mandatory." );
		Assert.state( this.repository.delete( profile, manualEntering ),
				"Some problem happened while performing delete for 'manualEntering' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEntering > listBy( final Day day ) {
		Assert.notNull( day );
		return this.repository.listBy( day );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public ManualEntering get( final Profile profile, final ManualEntering manualEntering ) {
		Assert.state( profile != null && profile.getId() != null,
				"Argument 'profile' and its 'id' property are mandatory." );
		Assert.state( manualEntering != null && manualEntering.getId() != null,
				"Argument 'manualEntering' and its 'id' property are mandatory." );
		try {
			return this.repository.get( profile, manualEntering );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final ManualEntering manualEntering ) {
		Assert.notNull( manualEntering, "Argument 'manualEntering' is mandatory." );
		Assert.state( manualEntering.getDay() != null && manualEntering.getDay().getId() != null,
				"Nested 'day' and its 'date' properties are mandatory." );
		Assert.state( manualEntering.getReason() != null && manualEntering.getReason().getId() != null,
				"Nested 'reason' and its 'id' properties are mandatory." );
		Assert.notNull( manualEntering.getTimeInterval(), "Nested 'timeInterval' property is mandatoy." );
		Assert.state( this.repository.update( manualEntering ),
				"Some problem happened while performing update for 'manualEntering' record." );
	}

}
