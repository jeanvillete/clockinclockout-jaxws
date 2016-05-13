
package com.clkio.ws.domain.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseCreated complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responseCreated">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}response">
 *       &lt;sequence>
 *         &lt;element name="domain" type="{http://schemas.clkio.com/common}commonDomain"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseCreated", propOrder = {
    "domain"
})
public class ResponseCreated
    extends Response
{

    @XmlElement(required = true)
    protected CommonDomain domain;

    public ResponseCreated() {
    	super();
    }
    
    public ResponseCreated( CommonDomain domain ) {
		super();
		this.domain = domain;
	}

	/**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link CommonDomain }
     *     
     */
    public CommonDomain getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonDomain }
     *     
     */
    public void setDomain(CommonDomain value) {
        this.domain = value;
    }

}
