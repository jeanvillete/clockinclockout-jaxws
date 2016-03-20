
package com.clkio.ws.domain.profile;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.clkio.ws.domain.profile package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws.domain.profile
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateProfileRequest }
     * 
     */
    public UpdateProfileRequest createUpdateProfileRequest() {
        return new UpdateProfileRequest();
    }

    /**
     * Create an instance of {@link DeleteProfileRequest }
     * 
     */
    public DeleteProfileRequest createDeleteProfileRequest() {
        return new DeleteProfileRequest();
    }

    /**
     * Create an instance of {@link ListProfileResponse }
     * 
     */
    public ListProfileResponse createListProfileResponse() {
        return new ListProfileResponse();
    }

    /**
     * Create an instance of {@link ListProfileRequest }
     * 
     */
    public ListProfileRequest createListProfileRequest() {
        return new ListProfileRequest();
    }

    /**
     * Create an instance of {@link InsertProfileRequest }
     * 
     */
    public InsertProfileRequest createInsertProfileRequest() {
        return new InsertProfileRequest();
    }

    /**
     * Create an instance of {@link Profile }
     * 
     */
    public Profile createProfile() {
        return new Profile();
    }

}
