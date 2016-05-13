
package com.clkio.ws.domain.day;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.clkio.ws.domain.clockinclockout.Clockinclockout;
import com.clkio.ws.domain.common.CommonDomain;
import com.clkio.ws.domain.manualentering.ManualEntering;


/**
 * <p>Java class for dayEntering complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dayEntering">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}commonDomain">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dayEntering")
@XmlSeeAlso({
    Clockinclockout.class,
    ManualEntering.class
})
public class DayEntering
    extends CommonDomain
{


}
