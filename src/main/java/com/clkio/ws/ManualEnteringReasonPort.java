
package com.clkio.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.common.ResponseCreated;
import com.clkio.ws.domain.reason.DeleteManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.InsertManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonResponse;
import com.clkio.ws.domain.reason.UpdateManualEnteringReasonRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ManualEnteringReasonPort", targetNamespace = "http://ws.clkio.com")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.clkio.ws.domain.ObjectFactory.class,
    com.clkio.ws.domain.adjusting.ObjectFactory.class,
    com.clkio.ws.domain.clockinclockout.ObjectFactory.class,
    com.clkio.ws.domain.common.ObjectFactory.class,
    com.clkio.ws.domain.day.ObjectFactory.class,
    com.clkio.ws.domain.email.ObjectFactory.class,
    com.clkio.ws.domain.login.ObjectFactory.class,
    com.clkio.ws.domain.manualentering.ObjectFactory.class,
    com.clkio.ws.domain.profile.ObjectFactory.class,
    com.clkio.ws.domain.reason.ObjectFactory.class,
    com.clkio.ws.domain.resetpassword.ObjectFactory.class,
    com.clkio.ws.domain.timecard.ObjectFactory.class,
    com.clkio.ws.domain.user.ObjectFactory.class
})
public interface ManualEnteringReasonPort {


    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.reason.ListManualEnteringReasonResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "listManualEnteringReasonResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public ListManualEnteringReasonResponse list(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "listManualEnteringReasonRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        ListManualEnteringReasonRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.common.ResponseCreated
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "responseCreated", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public ResponseCreated insert(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "insertManualEnteringReasonRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        InsertManualEnteringReasonRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.common.Response
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public Response update(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "updateManualEnteringReasonRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        UpdateManualEnteringReasonRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.common.Response
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "response", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public Response delete(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "deleteManualEnteringReasonRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        DeleteManualEnteringReasonRequest request)
        throws ResponseException
    ;

}
