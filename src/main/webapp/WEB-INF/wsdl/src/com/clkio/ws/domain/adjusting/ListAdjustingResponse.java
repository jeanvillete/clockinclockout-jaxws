
package com.clkio.ws.domain.adjusting;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.common.Response;


/**
 * <p>Java class for listAdjustingResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listAdjustingResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}response">
 *       &lt;sequence>
 *         &lt;element name="adjustings" type="{http://schemas.clkio.com/adjusting}adjusting" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listAdjustingResponse", propOrder = {
    "adjustings"
})
public class ListAdjustingResponse
    extends Response
{

    @XmlElement(required = true, nillable = true)
    protected List<Adjusting> adjustings;

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

}
