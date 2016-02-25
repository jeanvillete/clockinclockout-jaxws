package org.com.clockinclockout.service;

import java.util.List;

import org.com.clockinclockout.domain.Adjusting;
import org.com.clockinclockout.domain.Profile;

public interface AdjustingService {

	void insert( Adjusting adjusting );
	
	void delete( Adjusting adjusting );
	
	List< Adjusting > listBy( Profile profile );
	
}
