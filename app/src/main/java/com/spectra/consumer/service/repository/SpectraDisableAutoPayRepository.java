package com.spectra.consumer.service.repository;


import androidx.lifecycle.MutableLiveData;

import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.DisableAutoPayRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SpectraDisableAutoPayRepository {
    private static SpectraDisableAutoPayRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static SpectraDisableAutoPayRepository getRepository() {

        if (repository == null) {
            synchronized (SpectraDisableAutoPayRepository.class) {
                if (repository == null) {
                    repository = new SpectraDisableAutoPayRepository();
                    apiService = ApiClient.getAutoPayClient().create(ApiService.class);
                }
            }
        }
        return repository;
    }


    public MutableLiveData<ApiResponse> disableAutoPay(DisableAutoPayRequest disableAutoPayRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.disableAutoPay(disableAutoPayRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, disableAutoPayRequest.getRequetType())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


}
