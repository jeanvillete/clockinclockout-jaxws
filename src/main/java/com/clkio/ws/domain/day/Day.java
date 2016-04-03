
package com.clkio.ws.domain.day;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for day complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="day">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expectedHours" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tableEntering" type="{http://schemas.clkio.com/day}dayEntering" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "day", propOrder = {
    "date",
    "expectedHours",
    "balance",
    "notes",
    "tableEntering"
})
public class Day {

    @XmlElement(required = true)
    protected String date;
    @XmlElement(required = true)
    protected String expectedHours;
    @XmlElement(required = true)
    protected String balance;
    @XmlElement(required = true)
    protected String notes;
    @XmlElement(required = true, nillable = true)
    protected List<DayEntering> tableEntering;

    public Day() {
    	super();
    }
    
    public Day( String date, String expectedHours, String balance, String notes ) {
		super();
		this.date = date;
		this.expectedHours = expectedHours;
		this.balance = balance;
		this.notes = notes;
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

    /**
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalance(String value) {
        this.balance = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the tableEntering property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tableEntering property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTableEntering().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DayEntering }
     * 
     * 
     */
    public List<DayEntering> getTableEntering() {
        if (tableEntering == null) {
            tableEntering = new ArrayList<DayEntering>();
        }
        return this.tableEntering;
    }

}
