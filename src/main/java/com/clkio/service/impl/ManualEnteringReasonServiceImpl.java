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
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final ManualEnteringReason manualEnteringReason ) {
		Assert.state( manualEnteringReason != null && manualEnteringReason.getProfile() != null, 
				"Argument 'manualEnteringReason' and its nested 'id' property are mandatory." );
		Assert.state( !this.exists( manualEnteringReason.getReason(), manualEnteringReason.getProfile() ),
				"It already exists a record with the provided 'reason'." );
		Assert.state( this.repository.insert( manualEnteringReason ),
				"Some problem happened while performing insert for 'reason' record." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final ManualEnteringReason manualEnteringReason ) {
		Assert.notNull( manualEnteringReason, "Argument 'manualEnteringReason' is mandatory." );
	
		List< ManualEntering > listManualEntering = this.manualEnteringService.listBy( manualEnteringReason );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering manualEntering : listManualEntering )
				this.manualEnteringService.delete( manualEnteringReason.getProfile(), manualEntering );
		
		Assert.state( this.repository.delete( manualEnteringReason ),
				"Some problem happened while performing delete operation for 'reason' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEnteringReason > list( final Profile profile ) {
		Assert.state( profile != null && profile.getUser() != null,
				"The argument 'profile' and its nested 'user' property are mandatory." );
		return this.repository.list( profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( String reason, Profile profile ) {
		return this.repository.exists( reason, profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( String reason, Profile profile, Integer id ) {
		return this.repository.exists( reason, profile, id );
	}

	@Override
	public void update( ManualEnteringReason manualEnteringReason ) {
		Assert.state( manualEnteringReason != null && manualEnteringReason.getId() != null,
				"Argument 'manualEnteringReason' and its 'id' property are mandatory." );
		Assert.state( !exists( manualEnteringReason.getReason(), manualEnteringReason.getProfile(), manualEnteringReason.getId() ) );
		Assert.state( this.repository.update( manualEnteringReason ), 
				"Some problem happened while performing update for 'reason' record." );
	}

}
