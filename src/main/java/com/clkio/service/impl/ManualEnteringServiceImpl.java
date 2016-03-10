package com.clkio.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
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
	public void insert( ManualEntering manualEntering ) {
		Assert.notNull( manualEntering );
		
		//TODO apply/implement proper validations
		
		this.repository.insert( manualEntering );
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
