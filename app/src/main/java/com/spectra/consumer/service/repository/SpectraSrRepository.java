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
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;
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

public class SpectraSrRepository {
    private static SpectraSrRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static SpectraSrRepository getRepository() {

        if (repository == null) {
            synchronized (SpectraSrRepository.class) {
                if (repository == null) {
                    repository = new SpectraSrRepository();
                    apiService = ApiClient.getSRClient().create(ApiService.class);
                }
            }
        }
        return repository;
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

    public MutableLiveData<ApiResponse> updateToken(GetSrRequest getSrRequest) {
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
