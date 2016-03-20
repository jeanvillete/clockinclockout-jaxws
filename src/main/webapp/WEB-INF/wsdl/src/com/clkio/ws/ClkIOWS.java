
package com.clkio.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ClkIOWS", targetNamespace = "http://ws.clkio.com", wsdlLocation = "file:/home/jean/Documents/git/clockinclockout-jaxws/src/main/webapp/WEB-INF/wsdl/clkio.wsdl")
public class ClkIOWS
    extends Service
{

    private final static URL CLKIOWS_WSDL_LOCATION;
    private final static WebServiceException CLKIOWS_EXCEPTION;
    private final static QName CLKIOWS_QNAME = new QName("http://ws.clkio.com", "ClkIOWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/jean/Documents/git/clockinclockout-jaxws/src/main/webapp/WEB-INF/wsdl/clkio.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CLKIOWS_WSDL_LOCATION = url;
        CLKIOWS_EXCEPTION = e;
    }

    public ClkIOWS() {
        super(__getWsdlLocation(), CLKIOWS_QNAME);
    }

    public ClkIOWS(WebServiceFeature... features) {
        super(__getWsdlLocation(), CLKIOWS_QNAME, features);
    }

    public ClkIOWS(URL wsdlLocation) {
        super(wsdlLocation, CLKIOWS_QNAME);
    }

    public ClkIOWS(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CLKIOWS_QNAME, features);
    }

    public ClkIOWS(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ClkIOWS(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns UserPort
     */
    @WebEndpoint(name = "UserService")
    public UserPort getUserService() {
        return super.getPort(new QName("http://ws.clkio.com", "UserService"), UserPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserPort
     */
    @WebEndpoint(name = "UserService")
    public UserPort getUserService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "UserService"), UserPort.class, features);
    }

    /**
     * 
     * @return
     *     returns LoginPort
     */
    @WebEndpoint(name = "LoginService")
    public LoginPort getLoginService() {
        return super.getPort(new QName("http://ws.clkio.com", "LoginService"), LoginPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns LoginPort
     */
    @WebEndpoint(name = "LoginService")
    public LoginPort getLoginService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "LoginService"), LoginPort.class, features);
    }

    /**
     * 
     * @return
     *     returns EmailPort
     */
    @WebEndpoint(name = "EmailService")
    public EmailPort getEmailService() {
        return super.getPort(new QName("http://ws.clkio.com", "EmailService"), EmailPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EmailPort
     */
    @WebEndpoint(name = "EmailService")
    public EmailPort getEmailService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "EmailService"), EmailPort.class, features);
    }

    /**
     * 
     * @return
     *     returns ProfilePort
     */
    @WebEndpoint(name = "ProfileService")
    public ProfilePort getProfileService() {
        return super.getPort(new QName("http://ws.clkio.com", "ProfileService"), ProfilePort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ProfilePort
     */
    @WebEndpoint(name = "ProfileService")
    public ProfilePort getProfileService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "ProfileService"), ProfilePort.class, features);
    }

    /**
     * 
     * @return
     *     returns AdjustingPort
     */
    @WebEndpoint(name = "AdjustingService")
    public AdjustingPort getAdjustingService() {
        return super.getPort(new QName("http://ws.clkio.com", "AdjustingService"), AdjustingPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AdjustingPort
     */
    @WebEndpoint(name = "AdjustingService")
    public AdjustingPort getAdjustingService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "AdjustingService"), AdjustingPort.class, features);
    }

    /**
     * 
     * @return
     *     returns ManualEnteringReasonPort
     */
    @WebEndpoint(name = "ManualEnteringReasonService")
    public ManualEnteringReasonPort getManualEnteringReasonService() {
        return super.getPort(new QName("http://ws.clkio.com", "ManualEnteringReasonService"), ManualEnteringReasonPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ManualEnteringReasonPort
     */
    @WebEndpoint(name = "ManualEnteringReasonService")
    public ManualEnteringReasonPort getManualEnteringReasonService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "ManualEnteringReasonService"), ManualEnteringReasonPort.class, features);
    }

    /**
     * 
     * @return
     *     returns TimeCardPort
     */
    @WebEndpoint(name = "TimeCardService")
    public TimeCardPort getTimeCardService() {
        return super.getPort(new QName("http://ws.clkio.com", "TimeCardService"), TimeCardPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TimeCardPort
     */
    @WebEndpoint(name = "TimeCardService")
    public TimeCardPort getTimeCardService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "TimeCardService"), TimeCardPort.class, features);
    }

    /**
     * 
     * @return
     *     returns ResetPasswordPort
     */
    @WebEndpoint(name = "ResetPasswordService")
    public ResetPasswordPort getResetPasswordService() {
        return super.getPort(new QName("http://ws.clkio.com", "ResetPasswordService"), ResetPasswordPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ResetPasswordPort
     */
    @WebEndpoint(name = "ResetPasswordService")
    public ResetPasswordPort getResetPasswordService(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.clkio.com", "ResetPasswordService"), ResetPasswordPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CLKIOWS_EXCEPTION!= null) {
            throw CLKIOWS_EXCEPTION;
        }
        return CLKIOWS_WSDL_LOCATION;
    }

}
