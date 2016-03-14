
package com.clkio.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.common.ResponseException;
import com.clkio.ws.domain.email.ConfirmEmail;
import com.clkio.ws.domain.email.InsertEmail;
import com.clkio.ws.domain.login.DoLogin;
import com.clkio.ws.domain.login.LoginResponse;
import com.clkio.ws.domain.user.InsertUser;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.clkio.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InsertUser_QNAME = new QName("http://ws.clkio.com", "insertUser");
    private final static QName _LoginResponse_QNAME = new QName("http://ws.clkio.com", "loginResponse");
    private final static QName _DoLogin_QNAME = new QName("http://ws.clkio.com", "doLogin");
    private final static QName _Response_QNAME = new QName("http://ws.clkio.com", "response");
    private final static QName _ResponseException_QNAME = new QName("http://ws.clkio.com", "responseException");
    private final static QName _InsertEmail_QNAME = new QName("http://ws.clkio.com", "insertEmail");
    private final static QName _ConfirmEmail_QNAME = new QName("http://ws.clkio.com", "confirmEmail");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "insertUser")
    public JAXBElement<InsertUser> createInsertUser(InsertUser value) {
        return new JAXBElement<InsertUser>(_InsertUser_QNAME, InsertUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "doLogin")
    public JAXBElement<DoLogin> createDoLogin(DoLogin value) {
        return new JAXBElement<DoLogin>(_DoLogin_QNAME, DoLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "response")
    public JAXBElement<Response> createResponse(Response value) {
        return new JAXBElement<Response>(_Response_QNAME, Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "responseException")
    public JAXBElement<ResponseException> createResponseException(ResponseException value) {
        return new JAXBElement<ResponseException>(_ResponseException_QNAME, ResponseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "insertEmail")
    public JAXBElement<InsertEmail> createInsertEmail(InsertEmail value) {
        return new JAXBElement<InsertEmail>(_InsertEmail_QNAME, InsertEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.clkio.com", name = "confirmEmail")
    public JAXBElement<ConfirmEmail> createConfirmEmail(ConfirmEmail value) {
        return new JAXBElement<ConfirmEmail>(_ConfirmEmail_QNAME, ConfirmEmail.class, null, value);
    }

}
