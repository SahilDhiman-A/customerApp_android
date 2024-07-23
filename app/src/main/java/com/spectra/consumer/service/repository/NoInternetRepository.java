package com.spectra.consumer.service.repository;

import androidx.lifecycle.MutableLiveData;
import com.spectra.consumer.service.model.ApiResponse;
import java.util.HashMap;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NoInternetRepository {
    private static NoInternetRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static NoInternetRepository getRepository() {
        if (repository == null) {
            synchronized (NoInternetRepository.class) {
                if (repository == null) {
                    repository = new NoInternetRepository();
                    apiService = ApiClient.getInternetNotWorkingClient().create(ApiService.class);
                }
            }
        }
        return repository;
    }


    public MutableLiveData<ApiResponse> getNoInternet(String noInternetRequesDTO,String code) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getNoInternet(noInternetRequesDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, code)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> updateStatus(String noInternetRequesDTO,String code,String type ,String status) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        HashMap<String, Object> mHash = new HashMap<>();
        mHash.put(type, status);
        disposables.add(apiService.reActivePlan(noInternetRequesDTO,mHash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, code)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
}