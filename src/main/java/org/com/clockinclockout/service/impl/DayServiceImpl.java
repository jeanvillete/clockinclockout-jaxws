package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.ClockinClockout;
import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.ManualEntering;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.repository.DayRepository;
import org.com.clockinclockout.service.ClockinClockoutService;
import org.com.clockinclockout.service.DayService;
import org.com.clockinclockout.service.ManualEnteringService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

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
		
		List< ClockinClockout > listClockinClockout = this.clockinClockoutService.listBy( day );
		if ( !CollectionUtils.isEmpty( listClockinClockout ) )
			for ( ClockinClockout clockinClockout : listClockinClockout )
				this.clockinClockoutService.delete( clockinClockout );
		
		List< ManualEntering > listManualEntering = this.manualEnteringService.listBy( day );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering manualEntering : listManualEntering )
				this.manualEnteringService.delete( manualEntering );
		
		this.repository.delete( day );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public List< Day > listBy( Profile profile ) {
		Assert.notNull( profile );
		return this.repository.listBy( profile );
	}

}
