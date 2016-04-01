
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.profile.Profile;


/**
 * <p>Java class for setExpectedHoursRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="setExpectedHoursRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="profile" type="{http://schemas.clkio.com/profile}profile"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedHours" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setExpectedHoursRequest", propOrder = {
    "profile",
    "date",
    "expectedHours"
})
public class SetExpectedHoursRequest {

    @XmlElement(required = true)
    protected Profile profile;
    @XmlElement(required = true)
    protected String date;
    @XmlElement(required = true)
    protected String expectedHours;

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
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the expectedHours property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedHours() {
        return expectedHours;
    }

    /**
     * Sets the value of the expectedHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedHours(String value) {
        this.expectedHours = value;
    }

}
