package com.spectra.consumer.service.repository;


import androidx.lifecycle.MutableLiveData;

import com.spectra.consumer.service.model.ApiResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.spectra.consumer.Utils.Constant.SOURCE;

public class SpectraNotificationRepository {
    private static SpectraNotificationRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static SpectraNotificationRepository getRepository() {

        if (repository == null) {
            synchronized (SpectraNotificationRepository.class) {
                if (repository == null) {
                    repository = new SpectraNotificationRepository();
                    apiService = ApiClient.getAffleClient().create(ApiService.class);
                }
            }
        }
        return repository;
    }

    public MutableLiveData<ApiResponse> getAllNotification(String action,String canID,int skip,int limit) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getNotification(SOURCE,action,canID,""+skip,""+limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> searchNotification(String action,String canID, String key,int page) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.searchNotification(SOURCE,action,canID,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> deleteNotification(String action,String notID) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.deleteNotifications(SOURCE,action,notID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> readNotification(String action,String notID) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.readNotifications(SOURCE,action,notID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> archivedNotification(String action,String notID) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.archiveNotifications(SOURCE,action,notID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
}
