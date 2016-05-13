
package com.clkio.ws.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.clkio.ws.domain.adjusting.DeleteAdjustingRequest;
import com.clkio.ws.domain.adjusting.InsertAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingRequest;
import com.clkio.ws.domain.adjusting.ListAdjustingResponse;
import com.clkio.ws.domain.adjusting.UpdateAdjustingRequest;
import com.clkio.ws.domain.common.Response;
import com.clkio.ws.domain.common.ResponseCreated;
import com.clkio.ws.domain.common.ResponseFault;
import com.clkio.ws.domain.email.ConfirmEmailRequest;
import com.clkio.ws.domain.email.DeleteEmailRequest;
import com.clkio.ws.domain.email.InsertEmailRequest;
import com.clkio.ws.domain.email.ListEmailRequest;
import com.clkio.ws.domain.email.ListEmailResponse;
import com.clkio.ws.domain.email.SetEmailAsPrimaryRequest;
import com.clkio.ws.domain.login.DoLoginRequest;
import com.clkio.ws.domain.login.DoLogoutRequest;
import com.clkio.ws.domain.login.LoginResponse;
import com.clkio.ws.domain.profile.DeleteProfileRequest;
import com.clkio.ws.domain.profile.InsertProfileRequest;
import com.clkio.ws.domain.profile.ListProfileRequest;
import com.clkio.ws.domain.profile.ListProfileResponse;
import com.clkio.ws.domain.profile.UpdateProfileRequest;
import com.clkio.ws.domain.reason.DeleteManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.InsertManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonRequest;
import com.clkio.ws.domain.reason.ListManualEnteringReasonResponse;
import com.clkio.ws.domain.reason.UpdateManualEnteringReasonRequest;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordRequest;
import com.clkio.ws.domain.resetpassword.ConfirmResetPasswordResponse;
import com.clkio.ws.domain.resetpassword.RequestResetPasswordRequest;
import com.clkio.ws.domain.resetpassword.ResetPasswordRequest;
import com.clkio.ws.domain.timecard.DeleteClockinClockoutRequest;
import com.clkio.ws.domain.timecard.DeleteManualEnteringRequest;
import com.clkio.ws.domain.timecard.GetTimeCardRequest;
import com.clkio.ws.domain.timecard.GetTimeCardResponse;
import com.clkio.ws.domain.timecard.GetTotalTimeMonthlyRequest;
import com.clkio.ws.domain.timecard.GetTotalTimeMonthlyResponse;
import com.clkio.ws.domain.timecard.GetTotalTimeRequest;
import com.clkio.ws.domain.timecard.GetTotalTimeResponse;
import com.clkio.ws.domain.timecard.InsertClockinClockoutRequest;
import com.clkio.ws.domain.timecard.InsertManualEnteringRequest;
import com.clkio.ws.domain.timecard.PunchClockRequest;
import com.clkio.ws.domain.timecard.SetExpectedHoursRequest;
import com.clkio.ws.domain.timecard.SetNotesRequest;
import com.clkio.ws.domain.timecard.UpdateClockinClockoutRequest;
import com.clkio.ws.domain.timecard.UpdateManualEnteringRequest;
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

    private final static QName _GetTotalTimeRequest_QNAME = new QName("http://schemas.clkio.com", "getTotalTimeRequest");
    private final static QName _UpdateClockinClockoutRequest_QNAME = new QName("http://schemas.clkio.com", "updateClockinClockoutRequest");
    private final static QName _ListEmailResponse_QNAME = new QName("http://schemas.clkio.com", "listEmailResponse");
    private final static QName _GetTotalTimeMonthlyRequest_QNAME = new QName("http://schemas.clkio.com", "getTotalTimeMonthlyRequest");
    private final static QName _ConfirmEmailRequest_QNAME = new QName("http://schemas.clkio.com", "confirmEmailRequest");
    private final static QName _GetTotalTimeResponse_QNAME = new QName("http://schemas.clkio.com", "getTotalTimeResponse");
    private final static QName _GetTotalTimeMonthlyResponse_QNAME = new QName("http://schemas.clkio.com", "getTotalTimeMonthlyResponse");
    private final static QName _InsertClockinClockoutRequest_QNAME = new QName("http://schemas.clkio.com", "insertClockinClockoutRequest");
    private final static QName _InsertAdjustingRequest_QNAME = new QName("http://schemas.clkio.com", "insertAdjustingRequest");
    private final static QName _SetExpectedHoursRequest_QNAME = new QName("http://schemas.clkio.com", "setExpectedHoursRequest");
    private final static QName _InsertProfileRequest_QNAME = new QName("http://schemas.clkio.com", "insertProfileRequest");
    private final static QName _DoLoginRequest_QNAME = new QName("http://schemas.clkio.com", "doLoginRequest");
    private final static QName _DeleteClockinClockoutRequest_QNAME = new QName("http://schemas.clkio.com", "deleteClockinClockoutRequest");
    private final static QName _DeleteManualEnteringReasonRequest_QNAME = new QName("http://schemas.clkio.com", "deleteManualEnteringReasonRequest");
    private final static QName _ConfirmResetPasswordRequest_QNAME = new QName("http://schemas.clkio.com", "confirmResetPasswordRequest");
    private final static QName _GetTimeCardRequest_QNAME = new QName("http://schemas.clkio.com", "getTimeCardRequest");
    private final static QName _InsertEmailRequest_QNAME = new QName("http://schemas.clkio.com", "insertEmailRequest");
    private final static QName _UpdateManualEnteringReasonRequest_QNAME = new QName("http://schemas.clkio.com", "updateManualEnteringReasonRequest");
    private final static QName _PunchClockRequest_QNAME = new QName("http://schemas.clkio.com", "punchClockRequest");
    private final static QName _ListManualEnteringReasonResponse_QNAME = new QName("http://schemas.clkio.com", "listManualEnteringReasonResponse");
    private final static QName _UpdateProfileRequest_QNAME = new QName("http://schemas.clkio.com", "updateProfileRequest");
    private final static QName _UpdateManualEnteringRequest_QNAME = new QName("http://schemas.clkio.com", "updateManualEnteringRequest");
    private final static QName _LoginResponse_QNAME = new QName("http://schemas.clkio.com", "loginResponse");
    private final static QName _GetTimeCardResponse_QNAME = new QName("http://schemas.clkio.com", "getTimeCardResponse");
    private final static QName _InsertManualEnteringRequest_QNAME = new QName("http://schemas.clkio.com", "insertManualEnteringRequest");
    private final static QName _DoLogoutRequest_QNAME = new QName("http://schemas.clkio.com", "doLogoutRequest");
    private final static QName _DeleteProfileRequest_QNAME = new QName("http://schemas.clkio.com", "deleteProfileRequest");
    private final static QName _ListAdjustingResponse_QNAME = new QName("http://schemas.clkio.com", "listAdjustingResponse");
    private final static QName _ListEmailRequest_QNAME = new QName("http://schemas.clkio.com", "listEmailRequest");
    private final static QName _DeleteEmailRequest_QNAME = new QName("http://schemas.clkio.com", "deleteEmailRequest");
    private final static QName _DeleteAdjustingRequest_QNAME = new QName("http://schemas.clkio.com", "deleteAdjustingRequest");
    private final static QName _ListManualEnteringReasonRequest_QNAME = new QName("http://schemas.clkio.com", "listManualEnteringReasonRequest");
    private final static QName _ResponseFault_QNAME = new QName("http://schemas.clkio.com", "responseFault");
    private final static QName _SetEmailAsPrimaryRequest_QNAME = new QName("http://schemas.clkio.com", "setEmailAsPrimaryRequest");
    private final static QName _ListProfileResponse_QNAME = new QName("http://schemas.clkio.com", "listProfileResponse");
    private final static QName _InsertManualEnteringReasonRequest_QNAME = new QName("http://schemas.clkio.com", "insertManualEnteringReasonRequest");
    private final static QName _ClkioLoginCode_QNAME = new QName("http://schemas.clkio.com", "clkioLoginCode");
    private final static QName _ConfirmResetPasswordResponse_QNAME = new QName("http://schemas.clkio.com", "confirmResetPasswordResponse");
    private final static QName _ListAdjustingRequest_QNAME = new QName("http://schemas.clkio.com", "listAdjustingRequest");
    private final static QName _DeleteManualEnteringRequest_QNAME = new QName("http://schemas.clkio.com", "deleteManualEnteringRequest");
    private final static QName _ListProfileRequest_QNAME = new QName("http://schemas.clkio.com", "listProfileRequest");
    private final static QName _ResetPasswordRequest_QNAME = new QName("http://schemas.clkio.com", "resetPasswordRequest");
    private final static QName _Response_QNAME = new QName("http://schemas.clkio.com", "response");
    private final static QName _InsertUserRequest_QNAME = new QName("http://schemas.clkio.com", "insertUserRequest");
    private final static QName _ResponseCreated_QNAME = new QName("http://schemas.clkio.com", "responseCreated");
    private final static QName _RequestResetPasswordRequest_QNAME = new QName("http://schemas.clkio.com", "requestResetPasswordRequest");
    private final static QName _UpdateAdjustingRequest_QNAME = new QName("http://schemas.clkio.com", "updateAdjustingRequest");
    private final static QName _SetNotesRequest_QNAME = new QName("http://schemas.clkio.com", "setNotesRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.clkio.ws.domain
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTimeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "getTotalTimeRequest")
    public JAXBElement<GetTotalTimeRequest> createGetTotalTimeRequest(GetTotalTimeRequest value) {
        return new JAXBElement<GetTotalTimeRequest>(_GetTotalTimeRequest_QNAME, GetTotalTimeRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateClockinClockoutRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "updateClockinClockoutRequest")
    public JAXBElement<UpdateClockinClockoutRequest> createUpdateClockinClockoutRequest(UpdateClockinClockoutRequest value) {
        return new JAXBElement<UpdateClockinClockoutRequest>(_UpdateClockinClockoutRequest_QNAME, UpdateClockinClockoutRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTimeMonthlyRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "getTotalTimeMonthlyRequest")
    public JAXBElement<GetTotalTimeMonthlyRequest> createGetTotalTimeMonthlyRequest(GetTotalTimeMonthlyRequest value) {
        return new JAXBElement<GetTotalTimeMonthlyRequest>(_GetTotalTimeMonthlyRequest_QNAME, GetTotalTimeMonthlyRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTimeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "getTotalTimeResponse")
    public JAXBElement<GetTotalTimeResponse> createGetTotalTimeResponse(GetTotalTimeResponse value) {
        return new JAXBElement<GetTotalTimeResponse>(_GetTotalTimeResponse_QNAME, GetTotalTimeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTotalTimeMonthlyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "getTotalTimeMonthlyResponse")
    public JAXBElement<GetTotalTimeMonthlyResponse> createGetTotalTimeMonthlyResponse(GetTotalTimeMonthlyResponse value) {
        return new JAXBElement<GetTotalTimeMonthlyResponse>(_GetTotalTimeMonthlyResponse_QNAME, GetTotalTimeMonthlyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertClockinClockoutRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertClockinClockoutRequest")
    public JAXBElement<InsertClockinClockoutRequest> createInsertClockinClockoutRequest(InsertClockinClockoutRequest value) {
        return new JAXBElement<InsertClockinClockoutRequest>(_InsertClockinClockoutRequest_QNAME, InsertClockinClockoutRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertAdjustingRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertAdjustingRequest")
    public JAXBElement<InsertAdjustingRequest> createInsertAdjustingRequest(InsertAdjustingRequest value) {
        return new JAXBElement<InsertAdjustingRequest>(_InsertAdjustingRequest_QNAME, InsertAdjustingRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetExpectedHoursRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "setExpectedHoursRequest")
    public JAXBElement<SetExpectedHoursRequest> createSetExpectedHoursRequest(SetExpectedHoursRequest value) {
        return new JAXBElement<SetExpectedHoursRequest>(_SetExpectedHoursRequest_QNAME, SetExpectedHoursRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertProfileRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertProfileRequest")
    public JAXBElement<InsertProfileRequest> createInsertProfileRequest(InsertProfileRequest value) {
        return new JAXBElement<InsertProfileRequest>(_InsertProfileRequest_QNAME, InsertProfileRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteClockinClockoutRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "deleteClockinClockoutRequest")
    public JAXBElement<DeleteClockinClockoutRequest> createDeleteClockinClockoutRequest(DeleteClockinClockoutRequest value) {
        return new JAXBElement<DeleteClockinClockoutRequest>(_DeleteClockinClockoutRequest_QNAME, DeleteClockinClockoutRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteManualEnteringReasonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "deleteManualEnteringReasonRequest")
    public JAXBElement<DeleteManualEnteringReasonRequest> createDeleteManualEnteringReasonRequest(DeleteManualEnteringReasonRequest value) {
        return new JAXBElement<DeleteManualEnteringReasonRequest>(_DeleteManualEnteringReasonRequest_QNAME, DeleteManualEnteringReasonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmResetPasswordRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "confirmResetPasswordRequest")
    public JAXBElement<ConfirmResetPasswordRequest> createConfirmResetPasswordRequest(ConfirmResetPasswordRequest value) {
        return new JAXBElement<ConfirmResetPasswordRequest>(_ConfirmResetPasswordRequest_QNAME, ConfirmResetPasswordRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeCardRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "getTimeCardRequest")
    public JAXBElement<GetTimeCardRequest> createGetTimeCardRequest(GetTimeCardRequest value) {
        return new JAXBElement<GetTimeCardRequest>(_GetTimeCardRequest_QNAME, GetTimeCardRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertEmailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertEmailRequest")
    public JAXBElement<InsertEmailRequest> createInsertEmailRequest(InsertEmailRequest value) {
        return new JAXBElement<InsertEmailRequest>(_InsertEmailRequest_QNAME, InsertEmailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateManualEnteringReasonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "updateManualEnteringReasonRequest")
    public JAXBElement<UpdateManualEnteringReasonRequest> createUpdateManualEnteringReasonRequest(UpdateManualEnteringReasonRequest value) {
        return new JAXBElement<UpdateManualEnteringReasonRequest>(_UpdateManualEnteringReasonRequest_QNAME, UpdateManualEnteringReasonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PunchClockRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "punchClockRequest")
    public JAXBElement<PunchClockRequest> createPunchClockRequest(PunchClockRequest value) {
        return new JAXBElement<PunchClockRequest>(_PunchClockRequest_QNAME, PunchClockRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListManualEnteringReasonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listManualEnteringReasonResponse")
    public JAXBElement<ListManualEnteringReasonResponse> createListManualEnteringReasonResponse(ListManualEnteringReasonResponse value) {
        return new JAXBElement<ListManualEnteringReasonResponse>(_ListManualEnteringReasonResponse_QNAME, ListManualEnteringReasonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProfileRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "updateProfileRequest")
    public JAXBElement<UpdateProfileRequest> createUpdateProfileRequest(UpdateProfileRequest value) {
        return new JAXBElement<UpdateProfileRequest>(_UpdateProfileRequest_QNAME, UpdateProfileRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateManualEnteringRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "updateManualEnteringRequest")
    public JAXBElement<UpdateManualEnteringRequest> createUpdateManualEnteringRequest(UpdateManualEnteringRequest value) {
        return new JAXBElement<UpdateManualEnteringRequest>(_UpdateManualEnteringRequest_QNAME, UpdateManualEnteringRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTimeCardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "getTimeCardResponse")
    public JAXBElement<GetTimeCardResponse> createGetTimeCardResponse(GetTimeCardResponse value) {
        return new JAXBElement<GetTimeCardResponse>(_GetTimeCardResponse_QNAME, GetTimeCardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertManualEnteringRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertManualEnteringRequest")
    public JAXBElement<InsertManualEnteringRequest> createInsertManualEnteringRequest(InsertManualEnteringRequest value) {
        return new JAXBElement<InsertManualEnteringRequest>(_InsertManualEnteringRequest_QNAME, InsertManualEnteringRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoLogoutRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "doLogoutRequest")
    public JAXBElement<DoLogoutRequest> createDoLogoutRequest(DoLogoutRequest value) {
        return new JAXBElement<DoLogoutRequest>(_DoLogoutRequest_QNAME, DoLogoutRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProfileRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "deleteProfileRequest")
    public JAXBElement<DeleteProfileRequest> createDeleteProfileRequest(DeleteProfileRequest value) {
        return new JAXBElement<DeleteProfileRequest>(_DeleteProfileRequest_QNAME, DeleteProfileRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAdjustingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listAdjustingResponse")
    public JAXBElement<ListAdjustingResponse> createListAdjustingResponse(ListAdjustingResponse value) {
        return new JAXBElement<ListAdjustingResponse>(_ListAdjustingResponse_QNAME, ListAdjustingResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAdjustingRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "deleteAdjustingRequest")
    public JAXBElement<DeleteAdjustingRequest> createDeleteAdjustingRequest(DeleteAdjustingRequest value) {
        return new JAXBElement<DeleteAdjustingRequest>(_DeleteAdjustingRequest_QNAME, DeleteAdjustingRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListManualEnteringReasonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listManualEnteringReasonRequest")
    public JAXBElement<ListManualEnteringReasonRequest> createListManualEnteringReasonRequest(ListManualEnteringReasonRequest value) {
        return new JAXBElement<ListManualEnteringReasonRequest>(_ListManualEnteringReasonRequest_QNAME, ListManualEnteringReasonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "responseFault")
    public JAXBElement<ResponseFault> createResponseFault(ResponseFault value) {
        return new JAXBElement<ResponseFault>(_ResponseFault_QNAME, ResponseFault.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertManualEnteringReasonRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "insertManualEnteringReasonRequest")
    public JAXBElement<InsertManualEnteringReasonRequest> createInsertManualEnteringReasonRequest(InsertManualEnteringReasonRequest value) {
        return new JAXBElement<InsertManualEnteringReasonRequest>(_InsertManualEnteringReasonRequest_QNAME, InsertManualEnteringReasonRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "clkioLoginCode")
    public JAXBElement<String> createClkioLoginCode(String value) {
        return new JAXBElement<String>(_ClkioLoginCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmResetPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "confirmResetPasswordResponse")
    public JAXBElement<ConfirmResetPasswordResponse> createConfirmResetPasswordResponse(ConfirmResetPasswordResponse value) {
        return new JAXBElement<ConfirmResetPasswordResponse>(_ConfirmResetPasswordResponse_QNAME, ConfirmResetPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAdjustingRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "listAdjustingRequest")
    public JAXBElement<ListAdjustingRequest> createListAdjustingRequest(ListAdjustingRequest value) {
        return new JAXBElement<ListAdjustingRequest>(_ListAdjustingRequest_QNAME, ListAdjustingRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteManualEnteringRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "deleteManualEnteringRequest")
    public JAXBElement<DeleteManualEnteringRequest> createDeleteManualEnteringRequest(DeleteManualEnteringRequest value) {
        return new JAXBElement<DeleteManualEnteringRequest>(_DeleteManualEnteringRequest_QNAME, DeleteManualEnteringRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetPasswordRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "resetPasswordRequest")
    public JAXBElement<ResetPasswordRequest> createResetPasswordRequest(ResetPasswordRequest value) {
        return new JAXBElement<ResetPasswordRequest>(_ResetPasswordRequest_QNAME, ResetPasswordRequest.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseCreated }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "responseCreated")
    public JAXBElement<ResponseCreated> createResponseCreated(ResponseCreated value) {
        return new JAXBElement<ResponseCreated>(_ResponseCreated_QNAME, ResponseCreated.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestResetPasswordRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "requestResetPasswordRequest")
    public JAXBElement<RequestResetPasswordRequest> createRequestResetPasswordRequest(RequestResetPasswordRequest value) {
        return new JAXBElement<RequestResetPasswordRequest>(_RequestResetPasswordRequest_QNAME, RequestResetPasswordRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAdjustingRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "updateAdjustingRequest")
    public JAXBElement<UpdateAdjustingRequest> createUpdateAdjustingRequest(UpdateAdjustingRequest value) {
        return new JAXBElement<UpdateAdjustingRequest>(_UpdateAdjustingRequest_QNAME, UpdateAdjustingRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetNotesRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.clkio.com", name = "setNotesRequest")
    public JAXBElement<SetNotesRequest> createSetNotesRequest(SetNotesRequest value) {
        return new JAXBElement<SetNotesRequest>(_SetNotesRequest_QNAME, SetNotesRequest.class, null, value);
    }

}
