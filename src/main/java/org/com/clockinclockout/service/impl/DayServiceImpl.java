package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.repository.DayRepository;
import org.com.clockinclockout.service.DayService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class DayServiceImpl implements DayService, InitializingBean {

	@Autowired
	private DayRepository repository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property repository has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( Day day ) {
		Assert.notNull( day );
		
		// TODO apply validations as defined on requirements
		
		this.repository.insert( day );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( Day day ) {
		Assert.notNull( day );
		
		// TODO clockinclockoutService.delete( day.getListClockinClockout() ) 
		// TODO manualEnteringService.delete( day.getListManualEntering() ) 
		
		this.repository.delete( day );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public List< Day > listBy( Profile profile ) {
		Assert.notNull( profile );
		return this.repository.listBy( profile );
	}

}
