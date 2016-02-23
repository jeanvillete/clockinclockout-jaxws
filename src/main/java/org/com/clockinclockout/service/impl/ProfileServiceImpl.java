package org.com.clockinclockout.service.impl;

import java.util.List;

import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.repository.ProfileRepository;
import org.com.clockinclockout.service.ProfileService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ProfileServiceImpl implements ProfileService, InitializingBean {

	@Autowired
	private ProfileRepository repository;
	
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
		
		// TODO invoke adjustingService.delete( profile.getAdjustings() )
		// TODO invoke dayService.delete( profile.getDays() )
		// TODO ivoke manualEnteringReason.delete( profile.getManualEnteringReasons() )
		
		this.repository.delete( profile );
	}


}
