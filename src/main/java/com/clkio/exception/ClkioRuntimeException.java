package com.clkio.exception;

import com.clkio.ws.domain.common.ResponseFault;

public abstract class ClkioRuntimeException extends RuntimeException {

	public ClkioRuntimeException( String message ) {
		super( message );
	}
	
	public abstract ResponseFault getFault();
	
}
