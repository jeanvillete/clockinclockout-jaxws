package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.ManualEntering;
import org.com.clockinclockout.domain.ManualEnteringReason;
import org.com.clockinclockout.repository.ManualEnteringRepository;
import org.com.clockinclockout.service.ManualEnteringService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	public void delete( ManualEntering manualEntering ) {
		Assert.notNull( manualEntering );
		this.repository.delete( manualEntering );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ManualEntering > listBy( Day day ) {
		Assert.notNull( day );
		return this.repository.listBy( day );
	}

}
