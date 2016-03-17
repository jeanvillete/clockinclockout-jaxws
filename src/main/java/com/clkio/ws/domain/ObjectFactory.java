
package com.clkio.ws.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.common.ResponseException;
import com.clkio.ws.domain.email.ConfirmEmailRequest;
import com.clkio.ws.domain.email.DeleteEmailRequest;
import com.clkio.ws.domain.email.InsertEmailRequest;
import com.clkio.ws.domain.email.ListEmailRequest;
import com.clkio.ws.domain.email.ListEmailResponse;
import com.clkio.ws.domain.email.SetEmailAsPrimaryRequest;
import com.clkio.ws.domain.login.DoLoginRequest;
import com.clkio.ws.domain.login.LoginResponse;
import com.clkio.ws.domain.profile.ListProfileRequest;
import com.clkio.ws.domain.profile.ListProfileResponse;
import com.clkio.ws.domain.user.InsertUserRequest;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.clkio.ws.domain package. 
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

    private final static QName _LoginResponse_QNAME = new QName("http://schemas.clkio.com", "loginResponse");
    private final static QName _ListEmailResponse_QNAME = new QName("http://schemas.clkio.com", "listEmailResponse");
    private final static QName _ListEmailRequest_QNAME = new QName("http://schemas.clkio.com", "listEmailRequest");
    private final static QName _DeleteEmailRequest_QNAME = new QName("http://schemas.clkio.com", "deleteEmailRequest");
    private final static QName _ResponseException_QNAME = new QName("http://schemas.clkio.com", "responseException");
    private final static QName _ConfirmEmailRequest_QNAME = new QName("http://schemas.clkio.com", "confirmEmailRequest");
    private final static QName _SetEmailAsPrimaryRequest_QNAME = new QName("http://schemas.clkio.com", "setEmailAsPrimaryRequest");
    private final static QName _ListProfileResponse_QNAME = new QName("http://schemas.clkio.com", "listProfileResponse");
    private final static QName _ListProfileRequest_QNAME = new QName("http://schemas.clkio.com", "listProfileRequest");
    private final static QName _DoLoginRequest_QNAME = new QName("http://schemas.clkio.com", "doLoginRequest");
    private final static QName _Response_QNAME = new QName("http://schemas.clkio.com", "response");
    private final static QName _InsertUserRequest_QNAME = new QName("http://schemas.clkio.com", "insertUserRequest");
    private final static QName _InsertEmailRequest_QNAME = new QName("http://schemas.clkio.com", "insertEmailRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "loginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listEmailResponse")
    public JAXBElement<ListEmailResponse> createListEmailResponse(ListEmailResponse value) {
        return new JAXBElement<ListEmailResponse>(_ListEmailResponse_QNAME, ListEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListEmailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listEmailRequest")
    public JAXBElement<ListEmailRequest> createListEmailRequest(ListEmailRequest value) {
        return new JAXBElement<ListEmailRequest>(_ListEmailRequest_QNAME, ListEmailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEmailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "deleteEmailRequest")
    public JAXBElement<DeleteEmailRequest> createDeleteEmailRequest(DeleteEmailRequest value) {
        return new JAXBElement<DeleteEmailRequest>(_DeleteEmailRequest_QNAME, DeleteEmailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "responseException")
    public JAXBElement<ResponseException> createResponseException(ResponseException value) {
        return new JAXBElement<ResponseException>(_ResponseException_QNAME, ResponseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmEmailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "confirmEmailRequest")
    public JAXBElement<ConfirmEmailRequest> createConfirmEmailRequest(ConfirmEmailRequest value) {
        return new JAXBElement<ConfirmEmailRequest>(_ConfirmEmailRequest_QNAME, ConfirmEmailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetEmailAsPrimaryRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "setEmailAsPrimaryRequest")
    public JAXBElement<SetEmailAsPrimaryRequest> createSetEmailAsPrimaryRequest(SetEmailAsPrimaryRequest value) {
        return new JAXBElement<SetEmailAsPrimaryRequest>(_SetEmailAsPrimaryRequest_QNAME, SetEmailAsPrimaryRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listProfileResponse")
    public JAXBElement<ListProfileResponse> createListProfileResponse(ListProfileResponse value) {
        return new JAXBElement<ListProfileResponse>(_ListProfileResponse_QNAME, ListProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListProfileRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listProfileRequest")
    public JAXBElement<ListProfileRequest> createListProfileRequest(ListProfileRequest value) {
        return new JAXBElement<ListProfileRequest>(_ListProfileRequest_QNAME, ListProfileRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoLoginRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "doLoginRequest")
    public JAXBElement<DoLoginRequest> createDoLoginRequest(DoLoginRequest value) {
        return new JAXBElement<DoLoginRequest>(_DoLoginRequest_QNAME, DoLoginRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "response")
    public JAXBElement<Response> createResponse(Response value) {
        return new JAXBElement<Response>(_Response_QNAME, Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertUserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertUserRequest")
    public JAXBElement<InsertUserRequest> createInsertUserRequest(InsertUserRequest value) {
        return new JAXBElement<InsertUserRequest>(_InsertUserRequest_QNAME, InsertUserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertEmailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertEmailRequest")
    public JAXBElement<InsertEmailRequest> createInsertEmailRequest(InsertEmailRequest value) {
        return new JAXBElement<InsertEmailRequest>(_InsertEmailRequest_QNAME, InsertEmailRequest.class, null, value);
    }

}
