
package com.clkio.ws.domain.profile;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.adjusting.Adjusting;
import com.clkio.ws.domain.common.CommonDomain;
import com.clkio.ws.domain.reason.Reason;


/**
 * <p>Java class for profile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="profile">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}commonDomain">
 *       &lt;sequence>
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
 *         &lt;element name="adjustings" type="{http://schemas.clkio.com/adjusting}adjusting" maxOccurs="unbounded"/>
 *         &lt;element name="reasons" type="{http://schemas.clkio.com/reason}reason" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "profile", propOrder = {
    "description",
    "hoursFormat",
    "dateFormat",
    "expectedSunday",
    "expectedMonday",
    "expectedTuesday",
    "expectedWednesday",
    "expectedThursday",
    "expectedFriday",
    "expectedSaturday",
    "adjustings",
    "reasons"
})
public class Profile
    extends CommonDomain
{

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
    @XmlElement(required = true, nillable = true)
    protected List<Adjusting> adjustings;
    @XmlElement(required = true, nillable = true)
    protected List<Reason> reasons;

    public Profile() {
    }
    
    public Profile( BigInteger id, String description ) {
		super();
		this.setId( id );
		this.description = description;
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

    /**
     * Gets the value of the adjustings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adjustings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdjustings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Adjusting }
     * 
     * 
     */
    public List<Adjusting> getAdjustings() {
        if (adjustings == null) {
            adjustings = new ArrayList<Adjusting>();
        }
        return this.adjustings;
    }

    /**
     * Gets the value of the reasons property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reasons property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReasons().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Reason }
     * 
     * 
     */
    public List<Reason> getReasons() {
        if (reasons == null) {
            reasons = new ArrayList<Reason>();
        }
        return this.reasons;
    }

}
