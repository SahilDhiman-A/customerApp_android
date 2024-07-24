package com.spectra.consumer.service.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.RequestThumbsDown;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FAQRepository {

    private static FAQRepository repository;
    private static ApiService mApiService;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static FAQRepository getRepository() {
        synchronized (FAQRepository.class) {
            repository = new FAQRepository();
            mApiService = ApiClient.getFaqClient().create(ApiService.class);
        }
        return repository;
    }


    public MutableLiveData<ApiResponse> getFAQListbySegment(String segment_id, String quary, String CanId, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.getFAQListbySegment(segment_id, CanId, quary)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getSengmentlist(String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.getSengmentlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> getFAQCategory(String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.getFAQCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> getTop5FAQCategorySegment(String segemntId, String Quary, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.getTop5FAQCategorySegment(segemntId, Quary)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> addViewCountinFaq(String faq_id, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.addViewCountinFaq(faq_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> removeViewCountinFaq(String faq_id, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.removeViewCountinFaq(faq_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public LiveData<ApiResponse> submitFAQFeedback(String faq_id, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.submitFAQFeedback(faq_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public LiveData<ApiResponse> likeFaq(String faqId, String canId, String action) {

        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.likeFaq(faqId, canId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public LiveData<ApiResponse> unlikeFaq(RequestThumbsDown requestThumbsDown, String action) {

        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.unlikeFaq(requestThumbsDown)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public LiveData<ApiResponse> getThumbsCount(String faqId, String canId, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.getThumbsCount(faqId, canId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, action)),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public LiveData<ApiResponse> getRecentSearch(String canId, String action) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(mApiService.getRecentSearch(canId)
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
