package com.clkio.exception;

import com.clkio.ws.domain.common.BadRequestFault;

public class ValidationException extends ClkioException {

	public ValidationException( String message ) {
		super( message );
	}
	
	@Override
	public BadRequestFault getFault() {
		return new BadRequestFault();
	}

}
