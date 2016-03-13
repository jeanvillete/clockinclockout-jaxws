
package com.clkio.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.user.User;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UserPort", targetNamespace = "http://ws.clkio.com")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.clkio.ws.ObjectFactory.class,
    com.clkio.ws.domain.common.ObjectFactory.class,
    com.clkio.ws.domain.user.ObjectFactory.class
})
public interface UserPort {


    /**
     * 
     * @param user
     * @return
     *     returns com.clkio.ws.domain.common.Response
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "http://ws.clkio.com", partName = "response")
    public Response insert(
        @WebParam(name = "user", targetNamespace = "http://ws.clkio.com", partName = "user")
        User user)
        throws ResponseException
    ;

}
