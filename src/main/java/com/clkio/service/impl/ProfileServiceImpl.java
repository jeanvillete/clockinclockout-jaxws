package com.clkio.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.domain.Adjusting;
import com.clkio.domain.Day;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.repository.ProfileRepository;
import com.clkio.service.AdjustingService;
import com.clkio.service.DayService;
import com.clkio.service.ManualEnteringReasonService;
import com.clkio.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService, InitializingBean {

	@Autowired
	private ProfileRepository repository;
	
	@Autowired
	private AdjustingService adjustingService;
	
	@Autowired
	private DayService dayService;
	
	@Autowired
	private ManualEnteringReasonService manualEnteringReasonService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The field repository shouldn't be null." );
		Assert.notNull( this.adjustingService, "The field adjustingService shouldn't be null." );
		Assert.notNull( this.dayService, "The field dayService shouldn't be null." );
		Assert.notNull( this.manualEnteringReasonService, "The field manualEnteringReasonService shouldn't be null." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final Profile profile ) {
		Assert.notNull( profile );
		this.repository.insert( profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true)
	public List< Profile > listBy( final User user ) {
		Assert.notNull( user );
		return this.repository.listBy( user );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Profile profile ) {
		Assert.notNull( profile );
		
		List< Adjusting > listAdjusting = this.adjustingService.list( profile );
		if ( !CollectionUtils.isEmpty( listAdjusting ) )
			for ( Adjusting adjusting : listAdjusting )
				this.adjustingService.delete( adjusting );

		List< Day > listDay = this.dayService.listBy( profile );
		if ( !CollectionUtils.isEmpty( listDay ) )
			for ( Day day : listDay )
				this.dayService.delete( day );
		
		List< ManualEnteringReason > listManualEnteringReason = this.manualEnteringReasonService.listBy( profile );
		if ( !CollectionUtils.isEmpty( listManualEnteringReason ) )
			for ( ManualEnteringReason manualEnteringReason : listManualEnteringReason )
				this.manualEnteringReasonService.delete( manualEnteringReason );
		
		this.repository.delete( profile );
	}

}
