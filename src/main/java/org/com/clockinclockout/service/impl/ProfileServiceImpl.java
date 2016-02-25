package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.Adjusting;
import org.com.clockinclockout.domain.Day;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.repository.ProfileRepository;
import org.com.clockinclockout.service.AdjustingService;
import org.com.clockinclockout.service.DayService;
import org.com.clockinclockout.service.ProfileService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class ProfileServiceImpl implements ProfileService, InitializingBean {

	@Autowired
	private ProfileRepository repository;
	
	@Autowired
	private AdjustingService adjustingService;
	
	@Autowired
	private DayService dayService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.repository, "The field repository shouldn't be null." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert( final Profile profile ) {
		Assert.notNull( profile );
		this.repository.insert( profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true)
	public List< Profile > listBy( User user ) {
		Assert.notNull( user );
		return this.repository.listBy( user );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( Profile profile ) {
		Assert.notNull( profile );
		
		List< Adjusting > listAdjusting = this.adjustingService.listBy( profile );
		if ( !CollectionUtils.isEmpty( listAdjusting )) {
			for ( Adjusting adjusting : listAdjusting ) {
				this.adjustingService.delete( adjusting );
			}
		}

		List< Day > listDay = this.dayService.listBy( profile );
		if ( !CollectionUtils.isEmpty( listDay )) {
			for ( Day day : listDay ) {
				this.dayService.delete( day );
			}
		}
		// TODO ivoke manualEnteringReasonService.delete( profile.getManualEnteringReasons() )
		
		this.repository.delete( profile );
	}

}
