package com.clkio.ws;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService( targetNamespace = "http://ws.clkio.com", name="UserPort" )
@SOAPBinding( style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL )
public interface UserWS {

	
	
}
