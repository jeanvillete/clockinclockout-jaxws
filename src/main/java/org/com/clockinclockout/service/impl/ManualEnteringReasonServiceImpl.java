package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.ManualEntering;
import org.com.clockinclockout.domain.ManualEnteringReason;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.repository.ManualEnteringReasonRepository;
import org.com.clockinclockout.service.ManualEnteringReasonService;
import org.com.clockinclockout.service.ManualEnteringService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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
		Assert.notNull( manualEnteringReason );
		
		// TODO apply validations
		
		this.repository.insert( manualEnteringReason );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final ManualEnteringReason manualEnteringReason ) {
		Assert.notNull( manualEnteringReason );
	
		List< ManualEntering > listManualEntering = this.manualEnteringService.listBy( manualEnteringReason );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering manualEntering : listManualEntering )
				this.manualEnteringService.delete( manualEntering );
		
		this.repository.delete( manualEnteringReason );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEnteringReason > listBy( final Profile profile ) {
		Assert.notNull( profile );
		return this.repository.listBy( profile );
	}

}
