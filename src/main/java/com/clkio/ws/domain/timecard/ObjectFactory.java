
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.clkio.ws.domain.timecard package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws.domain.timecard
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TimeCardResponse }
     * 
     */
    public TimeCardResponse createTimeCardResponse() {
        return new TimeCardResponse();
    }

    /**
     * Create an instance of {@link UpdateClockinClockoutRequest }
     * 
     */
    public UpdateClockinClockoutRequest createUpdateClockinClockoutRequest() {
        return new UpdateClockinClockoutRequest();
    }

    /**
     * Create an instance of {@link InsertClockinClockoutRequest }
     * 
     */
    public InsertClockinClockoutRequest createInsertClockinClockoutRequest() {
        return new InsertClockinClockoutRequest();
    }

    /**
     * Create an instance of {@link SetExpectedHoursRequest }
     * 
     */
    public SetExpectedHoursRequest createSetExpectedHoursRequest() {
        return new SetExpectedHoursRequest();
    }

    /**
     * Create an instance of {@link DeleteClockinClockoutRequest }
     * 
     */
    public DeleteClockinClockoutRequest createDeleteClockinClockoutRequest() {
        return new DeleteClockinClockoutRequest();
    }

    /**
     * Create an instance of {@link GetTimeCardRequest }
     * 
     */
    public GetTimeCardRequest createGetTimeCardRequest() {
        return new GetTimeCardRequest();
    }

    /**
     * Create an instance of {@link PunchClockRequest }
     * 
     */
    public PunchClockRequest createPunchClockRequest() {
        return new PunchClockRequest();
    }

    /**
     * Create an instance of {@link UpdateManualEnteringRequest }
     * 
     */
    public UpdateManualEnteringRequest createUpdateManualEnteringRequest() {
        return new UpdateManualEnteringRequest();
    }

    /**
     * Create an instance of {@link InsertManualEnteringRequest }
     * 
     */
    public InsertManualEnteringRequest createInsertManualEnteringRequest() {
        return new InsertManualEnteringRequest();
    }

    /**
     * Create an instance of {@link DeleteManualEnteringRequest }
     * 
     */
    public DeleteManualEnteringRequest createDeleteManualEnteringRequest() {
        return new DeleteManualEnteringRequest();
    }

    /**
     * Create an instance of {@link SetNotesRequest }
     * 
     */
    public SetNotesRequest createSetNotesRequest() {
        return new SetNotesRequest();
    }

    /**
     * Create an instance of {@link TimeCard }
     * 
     */
    public TimeCard createTimeCard() {
        return new TimeCard();
    }

}
