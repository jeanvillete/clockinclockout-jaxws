
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.clockinclockout.Clockinclockout;
import com.clkio.ws.domain.profile.Profile;


/**
 * <p>Java class for deleteClockinClockoutRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteClockinClockoutRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="profile" type="{http://schemas.clkio.com/profile}profile"/>
 *         &lt;element name="clockinclockout" type="{http://schemas.clkio.com/clockinclockout}clockinclockout"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteClockinClockoutRequest", propOrder = {
    "profile",
    "clockinclockout"
})
public class DeleteClockinClockoutRequest {

    @XmlElement(required = true)
    protected Profile profile;
    @XmlElement(required = true)
    protected Clockinclockout clockinclockout;

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

    /**
     * Gets the value of the clockinclockout property.
     * 
     * @return
     *     possible object is
     *     {@link Clockinclockout }
     *     
     */
    public Clockinclockout getClockinclockout() {
        return clockinclockout;
    }

    /**
     * Sets the value of the clockinclockout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Clockinclockout }
     *     
     */
    public void setClockinclockout(Clockinclockout value) {
        this.clockinclockout = value;
    }

}
