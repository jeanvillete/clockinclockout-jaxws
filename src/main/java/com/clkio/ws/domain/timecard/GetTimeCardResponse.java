
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.common.Response;


/**
 * <p>Java class for getTimeCardResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTimeCardResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}response">
 *       &lt;sequence>
 *         &lt;element name="timeCard" type="{http://schemas.clkio.com/timecard}timeCard"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTimeCardResponse", propOrder = {
    "timeCard"
})
public class GetTimeCardResponse
    extends Response
{

	@XmlElement(required = true)
    protected TimeCard timeCard;
	
	public GetTimeCardResponse() {
		super();
	}

	public GetTimeCardResponse( TimeCard timeCard ) {
		super();
		this.timeCard = timeCard;
	}
	
    /**
     * Gets the value of the timeCard property.
     * 
     * @return
     *     possible object is
     *     {@link TimeCard }
     *     
     */
    public TimeCard getTimeCard() {
        return timeCard;
    }

    /**
     * Sets the value of the timeCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeCard }
     *     
     */
    public void setTimeCard(TimeCard value) {
        this.timeCard = value;
    }

}
