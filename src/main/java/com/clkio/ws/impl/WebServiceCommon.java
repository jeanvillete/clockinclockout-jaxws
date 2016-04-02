package com.clkio.ws.impl;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.clkio.domain.User;
import com.clkio.service.UserService;

abstract class WebServiceCommon< T > {

	Class< T > target;
	
	WebServiceCommon() {
	}
	
	WebServiceCommon( Class< T > defaultService ) {
		this.target = defaultService;
	}
	
	@Resource
	private WebServiceContext wsContext;

	protected T getService() {
		return this.getService( this.target );
	}
	
	protected < E > E getService( Class< E > target ) {
		ServletContext servletContext = ( ServletContext ) this.wsContext.getMessageContext().get( MessageContext.SERVLET_CONTEXT );
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext( servletContext );
		return applicationContext.getBean( target );
	}
	
	/**
	 * Method to get the ip address from the requester.
	 * @return
	 */
	protected String getRequesterIP() {
		HttpServletRequest request = ( HttpServletRequest ) this.wsContext.getMessageContext().get( MessageContext.SERVLET_REQUEST );
		
		String ip = request.getHeader( "X-Forwarded-For" );
		
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "Proxy-Client-IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "WL-Proxy-Client-IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "HTTP_CLIENT_IP" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) ) {
			ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
		}
		if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip )) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}
	
	protected String getLoginCode() {
		HttpServletRequest request = ( HttpServletRequest ) this.wsContext.getMessageContext().get( MessageContext.SERVLET_REQUEST );
		String loginCode = request.getHeader( "CLKIO-LOGIN-CODE" );
		Assert.hasText( loginCode, "[clkiows] No 'CLKIO-LOGIN-CODE' header was provided." );

		return loginCode;
	}
	
	protected User getCurrentUser() {
		User user = this.getService( UserService.class ).getBy( this.getLoginCode() );
		Assert.notNull( user, "[clkiows] The provided value for 'CLKIO-LOGIN-CODE' header is not valid." );
		
		return user;
	}
	
}
