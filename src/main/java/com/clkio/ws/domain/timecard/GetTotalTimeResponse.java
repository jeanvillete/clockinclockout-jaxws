
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.common.Response;


/**
 * <p>Java class for getTotalTimeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTotalTimeResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}response">
 *       &lt;sequence>
 *         &lt;element name="totalTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTotalTimeResponse", propOrder = {
    "totalTime"
})
public class GetTotalTimeResponse
    extends Response
{

    @XmlElement(required = true)
    protected String totalTime;

    public GetTotalTimeResponse() {
    	super();
    }
    
    public GetTotalTimeResponse( String totalTime ) {
    	super();
    	this.totalTime = totalTime;
    }
    
    /**
     * Gets the value of the totalTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * Sets the value of the totalTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalTime(String value) {
        this.totalTime = value;
    }

}
