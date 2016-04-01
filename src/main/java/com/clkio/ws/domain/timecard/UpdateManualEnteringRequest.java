
package com.clkio.ws.domain.timecard;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.manualentering.ManualEntering;
import com.clkio.ws.domain.profile.Profile;


/**
 * <p>Java class for updateManualEnteringRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updateManualEnteringRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="profile" type="{http://schemas.clkio.com/profile}profile"/>
 *         &lt;element name="manualEntering" type="{http://schemas.clkio.com/manualentering}manualEntering"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateManualEnteringRequest", propOrder = {
    "profile",
    "manualEntering"
})
public class UpdateManualEnteringRequest {

    @XmlElement(required = true)
    protected Profile profile;
    @XmlElement(required = true)
    protected ManualEntering manualEntering;

    /**
     * Gets the value of the profile property.
     * 
     * @return
     *     possible object is
     *     {@link Profile }
     *     
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Sets the value of the profile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Profile }
     *     
     */
    public void setProfile(Profile value) {
        this.profile = value;
    }

    /**
     * Gets the value of the manualEntering property.
     * 
     * @return
     *     possible object is
     *     {@link ManualEntering }
     *     
     */
    public ManualEntering getManualEntering() {
        return manualEntering;
    }

    /**
     * Sets the value of the manualEntering property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManualEntering }
     *     
     */
    public void setManualEntering(ManualEntering value) {
        this.manualEntering = value;
    }

}
