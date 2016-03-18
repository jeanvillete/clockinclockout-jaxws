package com.clkio.service;

import java.util.List;

import com.clkio.domain.Adjusting;
import com.clkio.domain.Profile;

public interface AdjustingService {

	void insert( Adjusting adjusting );
	
	void delete( Adjusting adjusting );
	
	List< Adjusting > list( Profile profile );

}
