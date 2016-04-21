package com.clkio.exception;

import com.clkio.ws.domain.common.ConflictFault;

public class ConflictException extends ClkioException {

	public ConflictException( String message ) {
		super( message );
	}

	@Override
	public ConflictFault getFault() {
		return new ConflictFault();
	}
	
}
