
package com.clkio.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import com.clkio.ws.domain.timecard.DeleteClockinClockoutRequest;
import com.clkio.ws.domain.timecard.DeleteManualEnteringRequest;
import com.clkio.ws.domain.timecard.GetTimeCardRequest;
import com.clkio.ws.domain.timecard.InsertClockinClockoutRequest;
import com.clkio.ws.domain.timecard.InsertManualEnteringRequest;
import com.clkio.ws.domain.timecard.PunchClockRequest;
import com.clkio.ws.domain.timecard.SetExpectedHoursRequest;
import com.clkio.ws.domain.timecard.SetNotesRequest;
import com.clkio.ws.domain.timecard.TimeCardResponse;
import com.clkio.ws.domain.timecard.UpdateClockinClockoutRequest;
import com.clkio.ws.domain.timecard.UpdateManualEnteringRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "TimeCardPort", targetNamespace = "http://ws.clkio.com")
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
public interface TimeCardPort {


    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse getTimeCard(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "getTimeCardRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        GetTimeCardRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse punchClock(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "punchClockRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        PunchClockRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse insertClockinClockout(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "insertClockinClockoutRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        InsertClockinClockoutRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse updateClockinClockout(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "updateClockinClockoutRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        UpdateClockinClockoutRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse deleteClockinClockout(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "deleteClockinClockoutRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        DeleteClockinClockoutRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse insertManualEntering(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "insertManualEnteringRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        InsertManualEnteringRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse updateManualEntering(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "updateManualEnteringRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        UpdateManualEnteringRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse deleteManualEntering(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "deleteManualEnteringRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        DeleteManualEnteringRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse setNotes(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "setNotesRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        SetNotesRequest request)
        throws ResponseException
    ;

    /**
     * 
     * @param request
     * @param clkioLoginCode
     * @return
     *     returns com.clkio.ws.domain.timecard.TimeCardResponse
     * @throws ResponseException
     */
    @WebMethod
    @WebResult(name = "timeCardResponse", targetNamespace = "http://schemas.clkio.com", partName = "result")
    public TimeCardResponse setExpectedHours(
        @WebParam(name = "clkioLoginCode", targetNamespace = "http://schemas.clkio.com", header = true, partName = "clkioLoginCode")
        String clkioLoginCode,
        @WebParam(name = "setExpectedHoursRequest", targetNamespace = "http://schemas.clkio.com", partName = "request")
        SetExpectedHoursRequest request)
        throws ResponseException
    ;

}
