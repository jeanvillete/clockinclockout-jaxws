
package com.clkio.ws.domain.profile;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for profile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="profile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hoursFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateFormat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedSunday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedMonday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedTuesday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedWednesday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedThursday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedFriday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedSaturday" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "profile", propOrder = {
    "id",
    "description",
    "hoursFormat",
    "dateFormat",
    "expectedSunday",
    "expectedMonday",
    "expectedTuesday",
    "expectedWednesday",
    "expectedThursday",
    "expectedFriday",
    "expectedSaturday"
})
public class Profile {

    @XmlElement(required = true)
    protected BigInteger id;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true, defaultValue = "H:mm")
    protected String hoursFormat;
    @XmlElement(required = true, defaultValue = "yyyy-MM-dd")
    protected String dateFormat;
    @XmlElement(required = true)
    protected String expectedSunday;
    @XmlElement(required = true)
    protected String expectedMonday;
    @XmlElement(required = true)
    protected String expectedTuesday;
    @XmlElement(required = true)
    protected String expectedWednesday;
    @XmlElement(required = true)
    protected String expectedThursday;
    @XmlElement(required = true)
    protected String expectedFriday;
    @XmlElement(required = true)
    protected String expectedSaturday;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
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
     * Gets the value of the hoursFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoursFormat() {
        return hoursFormat;
    }

    /**
     * Sets the value of the hoursFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoursFormat(String value) {
        this.hoursFormat = value;
    }

    /**
     * Gets the value of the dateFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * Sets the value of the dateFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateFormat(String value) {
        this.dateFormat = value;
    }

    /**
     * Gets the value of the expectedSunday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedSunday() {
        return expectedSunday;
    }

    /**
     * Sets the value of the expectedSunday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedSunday(String value) {
        this.expectedSunday = value;
    }

    /**
     * Gets the value of the expectedMonday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedMonday() {
        return expectedMonday;
    }

    /**
     * Sets the value of the expectedMonday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedMonday(String value) {
        this.expectedMonday = value;
    }

    /**
     * Gets the value of the expectedTuesday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedTuesday() {
        return expectedTuesday;
    }

    /**
     * Sets the value of the expectedTuesday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedTuesday(String value) {
        this.expectedTuesday = value;
    }

    /**
     * Gets the value of the expectedWednesday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedWednesday() {
        return expectedWednesday;
    }

    /**
     * Sets the value of the expectedWednesday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedWednesday(String value) {
        this.expectedWednesday = value;
    }

    /**
     * Gets the value of the expectedThursday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedThursday() {
        return expectedThursday;
    }

    /**
     * Sets the value of the expectedThursday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedThursday(String value) {
        this.expectedThursday = value;
    }

    /**
     * Gets the value of the expectedFriday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedFriday() {
        return expectedFriday;
    }

    /**
     * Sets the value of the expectedFriday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedFriday(String value) {
        this.expectedFriday = value;
    }

    /**
     * Gets the value of the expectedSaturday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedSaturday() {
        return expectedSaturday;
    }

    /**
     * Sets the value of the expectedSaturday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedSaturday(String value) {
        this.expectedSaturday = value;
    }

}
