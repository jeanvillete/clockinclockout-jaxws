
package com.clkio.ws.domain.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for forbiddenFault complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="forbiddenFault">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.clkio.com/common}responseFault">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "forbiddenFault")
public class ForbiddenFault
    extends ResponseFault
{

	public ForbiddenFault() {
		super( "403" );
	}

}
