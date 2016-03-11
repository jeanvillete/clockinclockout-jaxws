package com.clkio.ws.impl;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

abstract class WebServiceCommon {

	@Resource
	private WebServiceContext wsContext;

	protected < T > T getService( Class< T > target ) {
		ServletContext servletContext = ( ServletContext ) this.wsContext.getMessageContext().get( MessageContext.SERVLET_CONTEXT );
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext( servletContext );
		return applicationContext.getBean( target );
	}
	
}
