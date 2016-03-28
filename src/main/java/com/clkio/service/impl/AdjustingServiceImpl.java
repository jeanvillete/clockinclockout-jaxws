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
	public void insert( final Adjusting adjusting ) {
		Assert.notNull( adjusting, "Argument 'adjusting' is mandatory." );
		Assert.hasText( adjusting.getDescription(), "The nested property 'description' is mandatory." );
		Assert.state( !this.exists( adjusting.getDescription(), adjusting.getProfile() ),
				"An 'adjusting' record already exists with the provided 'description'." );
		Assert.state( this.repository.insert( adjusting ),
				"Some problem happened while performing insert for 'adjusting'." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void delete( final Adjusting adjusting ) {
		Assert.notNull( adjusting, "Argument 'adjusting' is mandatory." );
		Assert.state( adjusting.getProfile() != null && adjusting.getProfile().getId() != null,
				"The nested 'adjusting's property profile.id is mandatory." );
		Assert.state( this.repository.delete( adjusting ),
				"Some problem happened while deleting 'adjusting' from database." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< Adjusting > list( final Profile profile ) {
		Assert.state( profile != null && profile.getUser() != null,
				"Argument 'profile' and its property 'user' are must have instance." );
		return this.repository.list( profile );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED )
	public void update( final Adjusting adjusting ) {
		Assert.state( adjusting != null && adjusting.getId() != null,
				"Argument 'adjusting' and its 'id' property are mandatory." );
		Assert.hasText( adjusting.getDescription(), "The nested property 'description' is mandatory." );
		Assert.state( !this.exists( adjusting.getDescription(), adjusting.getProfile(), adjusting.getId() ),
				"An 'adjusting' record already exists with the provided 'description'." );
		Assert.state( this.repository.update( adjusting ),
				"Some problem happened while performing update for the 'adjusting' record." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( final String description, final Profile profile ) {
		Assert.hasText( description, "The argument 'description' is mandatory." );
		Assert.notNull( profile, "Argument 'profile' is mandatory." );
		return this.repository.exists( description, profile );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean exists( final String description, final Profile profile, final Integer id ) {
		Assert.hasText( description, "The argument 'description' is mandatory." );
		Assert.notNull( profile, "Argument 'profile' is mandatory." );
		return this.repository.exists( description, profile, id );
	}

}
