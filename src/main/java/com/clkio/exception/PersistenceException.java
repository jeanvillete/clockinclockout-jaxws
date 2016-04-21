package com.clkio.exception;

import com.clkio.ws.domain.common.ForbiddenFault;

public class PersistenceException extends ClkioException {

	public PersistenceException( String message ) {
		super( message );
	}

	@Override
	public ForbiddenFault getFault() {
		return new ForbiddenFault();
	}
	
}
