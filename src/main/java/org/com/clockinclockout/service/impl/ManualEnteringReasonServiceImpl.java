package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.ManualEnteringReason;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.repository.ManualEnteringReasonRepository;
import org.com.clockinclockout.service.ManualEnteringReasonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ManualEnteringReasonServiceImpl implements ManualEnteringReasonService, InitializingBean {

	@Autowired
	private ManualEnteringReasonRepository repository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property repository has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final ManualEnteringReason manualEnteringReason ) {
		Assert.notNull( manualEnteringReason );
		
		// TODO apply validations
		
		this.repository.insert( manualEnteringReason );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final ManualEnteringReason manualEnteringReason ) {
		Assert.notNull( manualEnteringReason );
		
		// TODO manualEnteringService.delete( day.getListManualEntering() )
		
		this.repository.delete( manualEnteringReason );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEnteringReason > listBy( final Profile profile ) {
		Assert.notNull( profile );
		return this.repository.listBy( profile );
	}

}
