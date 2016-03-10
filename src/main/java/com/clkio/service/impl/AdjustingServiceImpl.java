package com.clkio.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.repository.AdjustingRepository;
import com.clkio.service.AdjustingService;

@Service
public class AdjustingServiceImpl implements AdjustingService, InitializingBean {

	@Autowired
	private AdjustingRepository repository;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.state( repository != null, "The property repository has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void insert(Adjusting adjusting) {
		Assert.notNull( adjusting );
		// TODO apply validations
		this.repository.insert( adjusting );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete(Adjusting adjusting) {
		Assert.notNull( adjusting );
		this.repository.delete( adjusting );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< Adjusting > listBy( Profile profile ) {
		Assert.notNull( profile );
		return this.repository.listBy( profile );
	}

}
