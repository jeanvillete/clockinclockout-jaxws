package com.clkio.exception;

import com.clkio.ws.domain.common.ResponseFault;

public abstract class ClkioException extends Exception {

	public ClkioException( String message ) {
		super( message );
	}
	
	public abstract ResponseFault getFault();
	
}
