
package com.clkio.ws.domain.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.adjusting.ListAdjustingResponse;
import com.clkio.ws.domain.email.ListEmailResponse;
import com.clkio.ws.domain.login.LoginResponse;
import com.clkio.ws.domain.profile.ListProfileResponse;
import com.clkio.ws.domain.reason.ListManualEnteringReasonResponse;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordResponse;
import com.clkio.ws.domain.timecard.GetTotalTimeResponse;


/**
 * <p>Java class for response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "response", propOrder = {
    "message"
})
@XmlSeeAlso({
    ListEmailResponse.class,
    GetTotalTimeResponse.class,
    ListManualEnteringReasonResponse.class,
    LoginResponse.class,
    ListAdjustingResponse.class,
    ListProfileResponse.class,
    ConfirmResetPasswordResponse.class
})
public class Response {

    @XmlElement(required = true)
    protected String message;
	
	public Response() {
		super();
	}

	public Response( String message ) {
		super();
		this.message = message;
	}

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
