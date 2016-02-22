package org.com.clockinclockout.domain;

import org.springframework.util.Assert;

abstract class CommonDomain {

	private Integer id;
	
	CommonDomain() {
		super();
	}
	
	CommonDomain(Integer id) {
		super();
		this.setId( id );
	}
	
	public void setId(Integer id) {
		Assert.state( id == null || id > 0, "Argument id has to be greater than 0." );
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
}
