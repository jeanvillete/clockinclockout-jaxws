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

abstract class WebServiceCommon {

	@Resource
	private WebServiceContext wsContext;

	protected < T > T getService( Class< T > target ) {
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
	
	protected User getCurrentUser() {
		HttpServletRequest request = ( HttpServletRequest ) this.wsContext.getMessageContext().get( MessageContext.SERVLET_REQUEST );
		String loginCode = request.getHeader( "CLKIO-LOGIN-CODE" );
		
		Assert.hasText( loginCode, "No 'CLKIO-LOGIN-CODE' header was provided." );
		
		User user = this.getService( UserService.class ).getBy( loginCode );
		Assert.notNull( user, "The provided value for 'CLKIO-LOGIN-CODE' header is not valid." );
		
		return user;
	}
	
}
