package com.clkio.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	public void insert( final Day day ) {
		Assert.notNull( day, "Argument 'day' is mandatory." );
		Assert.notNull( day.getDate(), "Nested argument's property 'date' cannot be null." );
		Assert.notNull( day.getExpectedHours(), "Nested argument's property 'expectedHours' cannot be null." );
		Assert.state( day.getProfile() != null && day.getProfile().getId() != null, 
				"Nested argument's property 'profile' alongside its 'id' attribute cannot be null." );
		Assert.state( this.repository.insert( day ), 
				"Some problem happened while performing insert for 'day' record." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final Day day ) {
		Assert.state( day != null && day.getId() != null,
				"Argument 'day' and its 'id' property are mandatory." );
		Assert.notNull( day.getExpectedHours(), "Nested argument's property 'expectedHours' cannot be null." );
		Assert.state( day.getProfile() != null && day.getProfile().getId() != null, 
				"Nested argument's property 'profile' alongside its 'id' attribute cannot be null." );
		Assert.state( this.repository.update( day ),
				"Some problem happened while performnig update for 'day' record." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Day day ) {
		Assert.notNull( day );
		
		List< ClockinClockout > listClockinClockout = this.clockinClockoutService.listBy( day );
		if ( !CollectionUtils.isEmpty( listClockinClockout ) )
			for ( ClockinClockout clockinClockout : listClockinClockout )
				this.clockinClockoutService.delete( day.getProfile(), clockinClockout );
		
		List< ManualEntering > listManualEntering = this.manualEnteringService.listBy( day );
		if ( !CollectionUtils.isEmpty( listManualEntering ) )
			for ( ManualEntering manualEntering : listManualEntering )
				this.manualEnteringService.delete( day.getProfile(),manualEntering );
		
		Assert.state( this.repository.delete( day ), 
				"Some problem happend while performing delete for 'day' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public List< Day > listBy( final Profile profile ) {
		Assert.notNull( profile );
		return this.repository.listBy( profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public Day get( final Profile profile, final LocalDate localDateDay ) {
		Assert.notNull( profile, "Argument 'profile' is mandatory." );
		Assert.notNull( localDateDay, "Argument 'localDateDay' is mandatory." );
		
		try {
			return this.repository.get( profile, localDateDay );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = false )
	public Day get( Day day ) {
		Assert.state( day != null && day.getId() != null, 
				"Argument 'day' and its 'id' property are mandatory." );
		try {
			return this.repository.get( day );
		} catch ( EmptyResultDataAccessException e ) {
			return null;
		}
	}

}
