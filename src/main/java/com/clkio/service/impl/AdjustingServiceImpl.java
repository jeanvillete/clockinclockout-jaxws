package com.clkio.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;
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
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void insert( final Adjusting adjusting ) throws ValidationException, ConflictException, PersistenceException {
		if ( adjusting == null )
			throw new ValidationException( "Argument 'adjusting' is mandatory." );
		if ( !StringUtils.hasText( adjusting.getDescription() ) )
			throw new ValidationException( "The nested property 'description' is mandatory." );
		if( this.exists( adjusting.getDescription(), adjusting.getProfile() ) )
			throw new ConflictException( "An 'adjusting' record already exists with the provided 'description'." );
		if( !this.repository.insert( adjusting ) )
			throw new PersistenceException( "It was not possbile performing insert for 'adjusting' record." );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void delete( final Adjusting adjusting ) throws ValidationException, PersistenceException {
		if ( adjusting == null )
			throw new ValidationException( "Argument 'adjusting' is mandatory." );
		if( adjusting.getProfile() == null || adjusting.getProfile().getId() == null )
				throw new ValidationException( "The nested 'adjusting's property profile.id is mandatory." );
		if( !this.repository.delete( adjusting ) )
				throw new PersistenceException( "It was not possible deleting 'adjusting' from database." );
	}

	@Override
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public List< Adjusting > list( final Profile profile ) throws ValidationException {
		if( profile == null || profile.getUser() == null )
			throw new ValidationException( "Argument 'profile' and its property 'user' are must have instance." );
		return this.repository.list( profile );
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRED, rollbackFor = { Exception.class, RuntimeException.class } )
	public void update( final Adjusting adjusting ) throws ValidationException, ConflictException, PersistenceException {
		if( adjusting == null || adjusting.getId() == null )
			throw new ValidationException( "Argument 'adjusting' and its 'id' property are mandatory." );
		if( !StringUtils.hasText( adjusting.getDescription() ) ) 
			throw new ValidationException( "The nested property 'description' is mandatory." );
		if( this.exists( adjusting.getDescription(), adjusting.getProfile(), adjusting.getId() ) )
			throw new ConflictException( "An 'adjusting' record already exists with the provided 'description'." );
		if( !this.repository.update( adjusting ) )
			throw new PersistenceException( "Some problem happened while performing update for the 'adjusting' record." );
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
