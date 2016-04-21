package com.clkio.exception;

import com.clkio.ws.domain.common.UnauthorizedFault;

public class UnauthorizedException extends ClkioException {

	public UnauthorizedException( String message ) {
		super( message );
	}
	
	@Override
	public UnauthorizedFault getFault() {
		return new UnauthorizedFault();
	}
	
}
