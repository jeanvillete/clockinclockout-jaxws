package com.clkio.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;
import com.clkio.repository.DayRepository;
import com.clkio.service.ClockinClockoutService;
import com.clkio.service.DayService;
import com.clkio.service.ManualEnteringService;

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
