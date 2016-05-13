
package com.clkio.ws.domain.manualentering;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.clkio.ws.domain.day.Day;
import com.clkio.ws.domain.day.DayEntering;
import com.clkio.ws.domain.reason.Reason;


/**
 * <p>Java class for manualEntering complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="manualEntering">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/day}dayEntering">
 *       &lt;sequence>
 *         &lt;element name="timeInterval" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reason" type="{http://schemas.clkio.com/reason}reason"/>
 *         &lt;element name="day" type="{http://schemas.clkio.com/day}day"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "manualEntering", propOrder = {
    "timeInterval",
    "reason",
    "day"
})
public class ManualEntering
    extends DayEntering
{

    @XmlElement(required = true)
    protected String timeInterval;
    @XmlElement(required = true)
    protected Reason reason;
    @XmlElement(required = true)
    protected Day day;
    
    public ManualEntering() {
    	super();
    }

    public ManualEntering( BigInteger id, String timeInterval, Reason reason ) {
		super();
		this.setId( id );
		this.timeInterval = timeInterval;
		this.reason = reason;
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
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link Reason }
     *     
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reason }
     *     
     */
    public void setReason(Reason value) {
        this.reason = value;
    }

    /**
     * Gets the value of the day property.
     * 
     * @return
     *     possible object is
     *     {@link Day }
     *     
     */
    public Day getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     * @param value
     *     allowed object is
     *     {@link Day }
     *     
     */
    public void setDay(Day value) {
        this.day = value;
    }

}
