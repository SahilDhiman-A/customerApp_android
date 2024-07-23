package com.spectra.consumer.service.repository;


import androidx.lifecycle.MutableLiveData;

import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.AddLinkAccountRequest;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Request.ContactRequest;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SpectraRepository {
    private static SpectraRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static SpectraRepository getRepository() {
            synchronized (SpectraRepository.class) {
                    repository = new SpectraRepository();
                    apiService = ApiClient.getClient().create(ApiService.class);
            }
        return repository;
    }

    public MutableLiveData<ApiResponse> getAccountByPassword(LoginViapasswordRequest loginViapasswordRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getAccountByPassword(loginViapasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, loginViapasswordRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> trackOrder(TrackOrderRequest trackOrderRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.trackOrder(trackOrderRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, trackOrderRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> getAccountByMobile(LoginViaMobileRequest loginViaMobileRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getAccountByMobile(loginViaMobileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, loginViaMobileRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> getAccountByCanId(GetAccountDataRequest getAccountDataRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getAccountByCanId(getAccountDataRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getAccountDataRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> sendOtp(SendOtpRequest sendOtpRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.sendOtp(sendOtpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, sendOtpRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getLinkAcoount(GetLinkAccountRequest getLinkAccountRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getLinkAcoount(getLinkAccountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getLinkAccountRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> removeLinkAccountRequest(RemoveLinkAccountRequest removeLinkAccountRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.removeLinkAccountRequest(removeLinkAccountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, removeLinkAccountRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> sendOtpLinkAccountRequest(SendOtpLinkAccountRequest sendOtpLinkAccountRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.sendOtpLinkAccountRequest(sendOtpLinkAccountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, sendOtpLinkAccountRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> addLinkAccount(AddLinkAccountRequest addLinkAccountRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.addLinkAccount(addLinkAccountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, addLinkAccountRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> updateEmail(UpdateEmailRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updateEmail(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> updateEmailViaOTP(UpdateEmailRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updateEmailViaOTP(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }



    public MutableLiveData<ApiResponse> updateGSTAN(UpdateGSTNRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updateGSTAN(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> updateMobile(UpdateMobileRequest otpRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updateMobile(otpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, otpRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> updateMobileViaOTP(UpdateMobileRequest otpRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updateMobileViaOTP(otpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, otpRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> resendOtp(ResendOtpRequest otpRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.resendOtp(otpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, otpRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getInvoiceDetail( InvoiceDetailRequest invoiceDetailRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getInvoiceDetail(invoiceDetailRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, invoiceDetailRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.forgotPassword(forgotPasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, forgotPasswordRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> getTopUpList(TopupRequest topupRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getTopUpList(topupRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, topupRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> consumedTopup(ConsumedTopupRequest topupRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.consumedTopup(topupRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, topupRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getContactList(ContactRequest contactRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getContactListList(contactRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, contactRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> addContact(AddContactRequest contactRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.addContact(contactRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, contactRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }



    public MutableLiveData<ApiResponse> changePlan(ChangePlanRequest changePlanRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.changePlan(changePlanRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, changePlanRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> getOffers(GetOffersRequest getOffersRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getOffers(getOffersRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getOffersRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getSiStatus(SiStatusRequest siStatusRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getSiStatus(siStatusRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, siStatusRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> addTopUp(AddTopUpRequest addTopUpRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.addTopUp(addTopUpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, addTopUpRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> getCaseType(GetCasetypeRequest getCasetypeRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getCaseType(getCasetypeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getCasetypeRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> createSR(CreateSrRequest createSrRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.createSR(createSrRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, createSrRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }



    public MutableLiveData<ApiResponse> getratePlanByCan(GetRatePlanRequest getRatePlanRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getratePlanByCan(getRatePlanRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getRatePlanRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }




    public MutableLiveData<ApiResponse> getProfile(GetProfileRequest getProfileRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getProfile(getProfileRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getProfileRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> getResetPassword(RestPasswordRequest restPasswordRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getResetPassword(restPasswordRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, restPasswordRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getMrtg(GetMrtgRequest getMrtgRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getMrtg(getMrtgRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getMrtgRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getSessionHistory(GetSessionHistoryRequest getSessionHistoryRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getSessionHistory(getSessionHistoryRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getSessionHistoryRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

public MutableLiveData<ApiResponse> getInvoiceList(GetInvoiceListRequest getInvoiceListRequest) {
    final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
    disposables.add(apiService.getInvoiceList(getInvoiceListRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
            .subscribe(
                    result -> data.setValue(ApiResponse.success(result, getInvoiceListRequest.getAction())),
                    throwable -> data.setValue(ApiResponse.error(throwable))
            ));
    return data;
}
    public MutableLiveData<ApiResponse> getTransactionList(GetTransactionListRequest getTransactionListRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getTransactionList(getTransactionListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getTransactionListRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getInvoiceContent(GetInvoiceContentRequest getInvoiceContentRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getInvoiceContent(getInvoiceContentRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getInvoiceContentRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> getSrStatus(GetSrRequest getSrRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getSrStatus(getSrRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getSrRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
}
