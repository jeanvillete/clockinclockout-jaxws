package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.ClockinClockout;
import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.repository.ClockinClockoutRepository;
import org.com.clockinclockout.service.ClockinClockoutService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ClockinClockoutServiceImpl implements ClockinClockoutService, InitializingBean {

	@Autowired
	private ClockinClockoutRepository repository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property 'repository' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( ClockinClockout clockinClockout ) {
		Assert.notNull( clockinClockout );
		
		//TODO apply/implement proper validations
		
		this.repository.insert( clockinClockout );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( ClockinClockout clockinClockout ) {
		Assert.notNull( clockinClockout );
		this.repository.delete( clockinClockout );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< ClockinClockout > listBy( Day day ) {
		Assert.notNull( day );
		return this.repository.listBy( day );
	}

}
