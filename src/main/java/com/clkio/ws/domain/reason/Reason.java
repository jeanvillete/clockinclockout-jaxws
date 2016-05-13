
package com.clkio.ws.domain.reason;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.clkio.ws.domain.common.CommonDomain;
import com.clkio.ws.domain.profile.Profile;


/**
 * <p>Java class for reason complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="reason">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}commonDomain">
 *       &lt;sequence>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "reason", propOrder = {
    "reason",
    "profile"
})
public class Reason
    extends CommonDomain
{

    @XmlElement(required = true)
    protected String reason;
    @XmlElement(required = true)
    protected Profile profile;

    public Reason() {
    	super();
    }
    
    public Reason( BigInteger id, String reason ) {
		super();
		this.setId( id );
		this.reason = reason;
	}

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
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
