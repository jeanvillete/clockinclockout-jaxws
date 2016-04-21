package com.clkio.domain;

import com.clkio.exception.DomainValidationException;

abstract class CommonDomain {

	private Integer id;
	
	CommonDomain() {
		super();
	}
	
	CommonDomain( Integer id ) {
		super();
		this.setId( id );
	}
	
	public void setId( Integer id ) {
		if( id != null && id <= 0 ) throw new DomainValidationException( "Argument id has to be greater than 0." );
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
}
