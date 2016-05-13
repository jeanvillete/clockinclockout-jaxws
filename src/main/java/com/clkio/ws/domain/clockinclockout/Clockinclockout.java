
package com.clkio.ws.domain.clockinclockout;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.clkio.ws.domain.day.DayEntering;


/**
 * <p>Java class for clockinclockout complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clockinclockout">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/day}dayEntering">
 *       &lt;sequence>
 *         &lt;element name="clockin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="clockout" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clockinclockout", propOrder = {
    "clockin",
    "clockout"
})
public class Clockinclockout
    extends DayEntering
{

    @XmlElement(required = true)
    protected String clockin;
    @XmlElement(required = true)
    protected String clockout;
    
    public Clockinclockout() {
    	super();
    }
    
    public Clockinclockout( BigInteger id, String clockin, String clockout ) {
		super();
		this.setId( id );
		this.clockin = clockin;
		this.clockout = clockout;
	}


    /**
     * Gets the value of the clockin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClockin() {
        return clockin;
    }

    /**
     * Sets the value of the clockin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClockin(String value) {
        this.clockin = value;
    }

    /**
     * Gets the value of the clockout property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClockout() {
        return clockout;
    }

    /**
     * Sets the value of the clockout property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClockout(String value) {
        this.clockout = value;
    }

}
