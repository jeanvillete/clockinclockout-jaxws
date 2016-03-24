
package com.clkio.ws.domain.resetpassword;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.common.Response;


/**
 * <p>Java class for confirmResetPasswordResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="confirmResetPasswordResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}response">
 *       &lt;sequence>
 *         &lt;element name="confirmationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confirmResetPasswordResponse", propOrder = {
    "confirmationCode"
})
public class ConfirmResetPasswordResponse
    extends Response
{

    @XmlElement(required = true)
    protected String confirmationCode;

    public ConfirmResetPasswordResponse() {
    	super();
    }
    
    public ConfirmResetPasswordResponse( String confirmationCode, String message ) {
		super();
		this.confirmationCode = confirmationCode;
		this.message = message;
	}

	/**
     * Gets the value of the confirmationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmationCode() {
        return confirmationCode;
    }

    /**
     * Sets the value of the confirmationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmationCode(String value) {
        this.confirmationCode = value;
    }

}
