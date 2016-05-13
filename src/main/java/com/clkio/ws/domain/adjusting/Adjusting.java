
package com.clkio.ws.domain.adjusting;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.clkio.ws.domain.common.CommonDomain;
import com.clkio.ws.domain.profile.Profile;


/**
 * <p>Java class for adjusting complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="adjusting">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}commonDomain">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timeInterval" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="profile" type="{http://schemas.clkio.com/profile}profile"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adjusting", propOrder = {
    "description",
    "timeInterval",
    "profile"
})
public class Adjusting
    extends CommonDomain
{

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String timeInterval;
    @XmlElement(required = true, nillable = true)
    protected Profile profile;

    public Adjusting() {
    	super();
    }
    
    public Adjusting( BigInteger id, String description, String timeInterval ) {
		super();
		this.setId( id );
		this.description = description;
		this.timeInterval = timeInterval;
	}

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the timeInterval property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeInterval() {
        return timeInterval;
    }

    /**
     * Sets the value of the timeInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeInterval(String value) {
        this.timeInterval = value;
    }

    /**
     * Gets the value of the profile property.
     * 
     * @return
     *     possible object is
     *     {@link Profile }
     *     
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Sets the value of the profile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Profile }
     *     
     */
    public void setProfile(Profile value) {
        this.profile = value;
    }

}
