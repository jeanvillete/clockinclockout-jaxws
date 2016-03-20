
package com.clkio.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.profile.DeleteProfileRequest;
import com.clkio.ws.domain.profile.InsertProfileRequest;
import com.clkio.ws.domain.profile.ListProfileRequest;
import com.clkio.ws.domain.profile.ListProfileResponse;
import com.clkio.ws.domain.profile.UpdateProfileRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ProfilePort", targetNamespace = "http://ws.clkio.com")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.clkio.ws.domain.ObjectFactory.class,
    com.clkio.ws.domain.adjusting.ObjectFactory.class,
    com.clkio.ws.domain.common.ObjectFactory.class,
    com.clkio.ws.domain.email.ObjectFactory.class,
    com.clkio.ws.domain.login.ObjectFactory.class,
    com.clkio.ws.domain.profile.ObjectFactory.class,
    com.clkio.ws.domain.reason.ObjectFactory.class,
    com.clkio.ws.domain.resetpassword.ObjectFactory.class,
    com.clkio.ws.domain.timecard.ObjectFactory.class,
    com.clkio.ws.domain.user.ObjectFactory.class
})
public interface ProfilePort {


    /**
     * 
     * @param request
     * @return
     *     returns com.clkio.ws.domain.profile.ListProfileResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "listProfileResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public ListProfileResponse list(
        @WebParam(name = "listProfileRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        ListProfileRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.clkio.ws.domain.common.Response
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public Response insert(
        @WebParam(name = "insertProfileRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        InsertProfileRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.clkio.ws.domain.common.Response
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public Response update(
        @WebParam(name = "updateProfileRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        UpdateProfileRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @return
     *     returns com.clkio.ws.domain.common.Response
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public Response delete(
        @WebParam(name = "deleteProfileRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        DeleteProfileRequest request)
        throws ResponseException
    ;

}
