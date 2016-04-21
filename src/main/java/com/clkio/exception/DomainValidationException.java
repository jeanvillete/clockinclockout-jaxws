package com.clkio.exception;

import com.clkio.ws.domain.common.BadRequestFault;

public class DomainValidationException extends ClkioRuntimeException {

	public DomainValidationException( String message ) {
		super( message );
	}
	
	@Override
	public BadRequestFault getFault() {
		return new BadRequestFault();
	}

}
