
package com.clkio.ws.domain.common;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.clkio.ws.domain.common package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws.domain.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseFault }
     * 
     */
    public ResponseFault createResponseFault() {
        return new ResponseFault();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link ResponseCreated }
     * 
     */
    public ResponseCreated createResponseCreated() {
        return new ResponseCreated();
    }

    /**
     * Create an instance of {@link ForbiddenFault }
     * 
     */
    public ForbiddenFault createForbiddenFault() {
        return new ForbiddenFault();
    }

    /**
     * Create an instance of {@link CommonDomain }
     * 
     */
    public CommonDomain createCommonDomain() {
        return new CommonDomain();
    }

    /**
     * Create an instance of {@link UnauthorizedFault }
     * 
     */
    public UnauthorizedFault createUnauthorizedFault() {
        return new UnauthorizedFault();
    }

    /**
     * Create an instance of {@link ConflictFault }
     * 
     */
    public ConflictFault createConflictFault() {
        return new ConflictFault();
    }

    /**
     * Create an instance of {@link InternalServerError }
     * 
     */
    public InternalServerError createInternalServerError() {
        return new InternalServerError();
    }

    /**
     * Create an instance of {@link BadRequestFault }
     * 
     */
    public BadRequestFault createBadRequestFault() {
        return new BadRequestFault();
    }

}
