
package com.clkio.ws.domain.adjusting;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.clkio.ws.domain.adjusting package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws.domain.adjusting
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InsertAdjustingRequest }
     * 
     */
    public InsertAdjustingRequest createInsertAdjustingRequest() {
        return new InsertAdjustingRequest();
    }

    /**
     * Create an instance of {@link ListAdjustingResponse }
     * 
     */
    public ListAdjustingResponse createListAdjustingResponse() {
        return new ListAdjustingResponse();
    }

    /**
     * Create an instance of {@link DeleteAdjustingRequest }
     * 
     */
    public DeleteAdjustingRequest createDeleteAdjustingRequest() {
        return new DeleteAdjustingRequest();
    }

    /**
     * Create an instance of {@link ListAdjustingRequest }
     * 
     */
    public ListAdjustingRequest createListAdjustingRequest() {
        return new ListAdjustingRequest();
    }

    /**
     * Create an instance of {@link UpdateAdjustingRequest }
     * 
     */
    public UpdateAdjustingRequest createUpdateAdjustingRequest() {
        return new UpdateAdjustingRequest();
    }

    /**
     * Create an instance of {@link Adjusting }
     * 
     */
    public Adjusting createAdjusting() {
        return new Adjusting();
    }

}
