
package com.clkio.ws.domain.timecard;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.day.Day;


/**
 * <p>Java class for timeCard complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="timeCard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="days" type="{http://schemas.clkio.com/day}day" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "timeCard", propOrder = {
    "days"
})
public class TimeCard {

    @XmlElement(required = true, nillable = true)
    protected List<Day> days;

    /**
     * Gets the value of the days property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the days property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDays().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Day }
     * 
     * 
     */
    public List<Day> getDays() {
        if (days == null) {
            days = new ArrayList<Day>();
        }
        return this.days;
    }

}
