
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.common.Response;


/**
 * <p>Java class for getTotalTimeMonthlyResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTotalTimeMonthlyResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}response">
 *       &lt;sequence>
 *         &lt;element name="totalTimeMonthly" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTotalTimeMonthlyResponse", propOrder = {
    "totalTimeMonthly"
})
public class GetTotalTimeMonthlyResponse
    extends Response
{

    @XmlElement(required = true)
    protected String totalTimeMonthly;

    /**
     * Gets the value of the totalTimeMonthly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalTimeMonthly() {
        return totalTimeMonthly;
    }

    /**
     * Sets the value of the totalTimeMonthly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalTimeMonthly(String value) {
        this.totalTimeMonthly = value;
    }

}
