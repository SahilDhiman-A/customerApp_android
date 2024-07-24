package com.spectra.consumer.service.repository;

import androidx.lifecycle.MutableLiveData;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FDSSRepository {

    private static FDSSRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static FDSSRepository getRepository() {
        if (repository == null) {
            synchronized (FDSSRepository.class) {
                if (repository == null) {
                    repository = new FDSSRepository();
                    apiService = ApiClient.getFDSSClient().create(ApiService.class);
                }
            }
        }
        return repository;
    }

    public MutableLiveData<ApiResponse> getFDSSNoInternet(String noInternetRequesDTO, String code) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getFDSSRequest(noInternetRequesDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, code)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> fupYesOrNO(String request, Object reqObject, String code) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getFDSSFUPRequest(request,reqObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, code)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getKnowMore(PostKnowMoreRequest postKnowMoreRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getKnowMore(postKnowMoreRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, postKnowMoreRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
}
