package com.spectra.consumer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.AddLinkAccountRequest;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Request.ContactRequest;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
import com.spectra.consumer.service.model.Request.DisableAutoPayRequest;
import com.spectra.consumer.service.model.Request.ForgotPasswordRequest;
import com.spectra.consumer.service.model.Request.GetAccountDataRequest;
import com.spectra.consumer.service.model.Request.GetCasetypeRequest;
import com.spectra.consumer.service.model.Request.GetInvoiceContentRequest;
import com.spectra.consumer.service.model.Request.GetInvoiceListRequest;
import com.spectra.consumer.service.model.Request.GetLinkAccountRequest;
import com.spectra.consumer.service.model.Request.GetMrtgRequest;
import com.spectra.consumer.service.model.Request.GetOffersRequest;
import com.spectra.consumer.service.model.Request.GetProfileRequest;
import com.spectra.consumer.service.model.Request.GetRatePlanRequest;
import com.spectra.consumer.service.model.Request.GetSessionHistoryRequest;
import com.spectra.consumer.service.model.Request.GetSrRequest;
import com.spectra.consumer.service.model.Request.GetTransactionListRequest;
import com.spectra.consumer.service.model.Request.InvoiceDetailRequest;
import com.spectra.consumer.service.model.Request.LoginViaMobileRequest;
import com.spectra.consumer.service.model.Request.LoginViapasswordRequest;
import com.spectra.consumer.service.model.Request.PostFUPFlagRequest;
import com.spectra.consumer.service.model.Request.RemoveLinkAccountRequest;
import com.spectra.consumer.service.model.Request.ResendOtpRequest;
import com.spectra.consumer.service.model.Request.RestPasswordRequest;
import com.spectra.consumer.service.model.Request.SendOtpLinkAccountRequest;
import com.spectra.consumer.service.model.Request.SendOtpRequest;
import com.spectra.consumer.service.model.Request.SiStatusRequest;
import com.spectra.consumer.service.model.Request.TopupRequest;
import com.spectra.consumer.service.model.Request.TrackOrderRequest;
import com.spectra.consumer.service.model.Request.UpdateEmailRequest;
import com.spectra.consumer.service.model.Request.UpdateGSTNRequest;
import com.spectra.consumer.service.model.Request.UpdateMobileRequest;
import com.spectra.consumer.service.repository.FDSSRepository;
import com.spectra.consumer.service.repository.NoInternetRepository;
import com.spectra.consumer.service.repository.SpectraDisableAutoPayRepository;
import com.spectra.consumer.service.repository.SpectraRepository;
import com.spectra.consumer.service.repository.SpectraSrRepository;

public class SpectraViewModel extends ViewModel {

    public MutableLiveData<ApiResponse> getAccountByPassword(LoginViapasswordRequest loginViapasswordRequest) {
        return SpectraRepository.getRepository().getAccountByPassword(loginViapasswordRequest);
    }
    public MutableLiveData<ApiResponse> trackOrder(TrackOrderRequest trackOrderRequest) {
        return SpectraRepository.getRepository().trackOrder(trackOrderRequest);
    }

    public MutableLiveData<ApiResponse> getAccountByMobile(LoginViaMobileRequest loginViaMobileRequest) {
        return SpectraRepository.getRepository().getAccountByMobile(loginViaMobileRequest);
    }

    public MutableLiveData<ApiResponse> getAccountByCanId(GetAccountDataRequest getAccountDataRequest) {
    return SpectraRepository.getRepository().getAccountByCanId(getAccountDataRequest);
    }

    public MutableLiveData<ApiResponse> sendOtp(SendOtpRequest sendOtpRequest) {
    return SpectraRepository.getRepository().sendOtp(sendOtpRequest);
    }

    public MutableLiveData<ApiResponse> getLinkAccount(GetLinkAccountRequest accountRequest) {
        return SpectraRepository.getRepository().getLinkAcoount(accountRequest);
    }
    public MutableLiveData<ApiResponse> removeLinkAccountRequest(RemoveLinkAccountRequest removeLinkAccountRequest) {
        return SpectraRepository.getRepository().removeLinkAccountRequest(removeLinkAccountRequest);
    }

    public MutableLiveData<ApiResponse> sendOtpLinkAccountRequest(SendOtpLinkAccountRequest sendOtpLinkAccountRequest) {
        return SpectraRepository.getRepository().sendOtpLinkAccountRequest(sendOtpLinkAccountRequest);
    }


    public MutableLiveData<ApiResponse> addLinkAccount(AddLinkAccountRequest addLinkAccountRequest) {
        return SpectraRepository.getRepository().addLinkAccount(addLinkAccountRequest);
    }




    public MutableLiveData<ApiResponse> updateEmail(UpdateEmailRequest request) {
        return SpectraRepository.getRepository().updateEmail(request);
    }

    public MutableLiveData<ApiResponse> updateEmailViaOTP(UpdateEmailRequest request) {
        return SpectraRepository.getRepository().updateEmailViaOTP(request);
    }

    public MutableLiveData<ApiResponse> updateGSTAN(UpdateGSTNRequest request) {
        return SpectraRepository.getRepository().updateGSTAN(request);
    }
    //////


    public MutableLiveData<ApiResponse> updateMobile( UpdateMobileRequest otpRequest) {
        return SpectraRepository.getRepository().updateMobile(otpRequest);
    }

    public MutableLiveData<ApiResponse> updateMobileViaOTP( UpdateMobileRequest otpRequest) {
        return SpectraRepository.getRepository().updateMobileViaOTP(otpRequest);
    }

    public MutableLiveData<ApiResponse> getInvoiceDetail( InvoiceDetailRequest invoiceDetailRequest) {
        return SpectraRepository.getRepository().getInvoiceDetail(invoiceDetailRequest);
    }
    public MutableLiveData<ApiResponse> resendOtp( ResendOtpRequest resendOtpRequest) {
        return SpectraRepository.getRepository().resendOtp(resendOtpRequest);
    }
    public MutableLiveData<ApiResponse> forgotPassword( ForgotPasswordRequest forgotPasswordRequest) {
        return SpectraRepository.getRepository().forgotPassword(forgotPasswordRequest);
    }
    public MutableLiveData<ApiResponse> getTopUpList( TopupRequest forgotPasswordRequest) {
        return SpectraRepository.getRepository().getTopUpList(forgotPasswordRequest);
    }
    public MutableLiveData<ApiResponse> consumedTopup( ConsumedTopupRequest forgotPasswordRequest) {
        return SpectraRepository.getRepository().consumedTopup(forgotPasswordRequest);
    }


    public MutableLiveData<ApiResponse> getContactList( ContactRequest contactRequest) {
        return SpectraRepository.getRepository().getContactList(contactRequest);
    }

    public MutableLiveData<ApiResponse> addContact( AddContactRequest contactRequest) {
        return SpectraRepository.getRepository().addContact(contactRequest);
    }




    public MutableLiveData<ApiResponse> changePlan( ChangePlanRequest changePlanRequest) {
        return SpectraRepository.getRepository().changePlan(changePlanRequest);
    }
    public MutableLiveData<ApiResponse> getOffers( GetOffersRequest getOffersRequest) {
        return SpectraRepository.getRepository().getOffers(getOffersRequest);
    }
    public MutableLiveData<ApiResponse> getSiStatus( SiStatusRequest siStatusRequest) {
        return SpectraRepository.getRepository().getSiStatus(siStatusRequest);
    }
    public MutableLiveData<ApiResponse> addTopUp( AddTopUpRequest addTopUpRequest) {
        return SpectraRepository.getRepository().addTopUp(addTopUpRequest);
    }

    public MutableLiveData<ApiResponse> getCaseType( GetCasetypeRequest getCasetypeRequest) {
        return SpectraRepository.getRepository().getCaseType(getCasetypeRequest);
    }

    public MutableLiveData<ApiResponse> createSR( CreateSrRequest createSrRequest) {
        return SpectraRepository.getRepository().createSR(createSrRequest);
    }


    public MutableLiveData<ApiResponse> getratePlanByCan( GetRatePlanRequest getRatePlanRequest) {
        return SpectraRepository.getRepository().getratePlanByCan(getRatePlanRequest);
    }
    public MutableLiveData<ApiResponse> getNoInternet(String request, String code) {
        return NoInternetRepository.getRepository().getNoInternet(request,code);
    }


    public MutableLiveData<ApiResponse> updateStatus(String request, String code,String type ,String status) {
        return NoInternetRepository.getRepository().updateStatus(request,code,type,status);
    }

    public MutableLiveData<ApiResponse> getProfile( GetProfileRequest getProfileRequest) {
        return SpectraRepository.getRepository().getProfile(getProfileRequest);
    }


    public MutableLiveData<ApiResponse> getResetPassword( RestPasswordRequest restPasswordRequest) {
        return SpectraRepository.getRepository().getResetPassword(restPasswordRequest);
    }

    public MutableLiveData<ApiResponse> getMrtg( GetMrtgRequest getMrtgRequest) {
        return SpectraRepository.getRepository().getMrtg(getMrtgRequest);
    }

    public MutableLiveData<ApiResponse> getSessionHistory( GetSessionHistoryRequest getSessionHistoryRequest) {
        return SpectraRepository.getRepository().getSessionHistory(getSessionHistoryRequest);
    }

    public MutableLiveData<ApiResponse> getInvoiceList( GetInvoiceListRequest getInvoiceListRequest) {
        return SpectraRepository.getRepository().getInvoiceList(getInvoiceListRequest);
    }

    public MutableLiveData<ApiResponse> getTransactionList( GetTransactionListRequest getTransactionListRequest) {
        return SpectraRepository.getRepository().getTransactionList(getTransactionListRequest);
    }

    public MutableLiveData<ApiResponse> getInvoiceContent( GetInvoiceContentRequest getInvoiceContentRequest) {
        return SpectraRepository.getRepository().getInvoiceContent(getInvoiceContentRequest);
    }

    public MutableLiveData<ApiResponse> disableAutoPay( DisableAutoPayRequest disableAutoPayRequest) {
        return SpectraDisableAutoPayRepository.getRepository().disableAutoPay(disableAutoPayRequest);
    }

    public MutableLiveData<ApiResponse> getSrStatus( GetSrRequest getSrRequest) {
        return SpectraRepository.getRepository().getSrStatus(getSrRequest);
    }
    public MutableLiveData<ApiResponse> getCheckSrStatus( GetSrRequest getSrRequest) {
        return SpectraSrRepository.getRepository().getSrStatus(getSrRequest);
    }

}
