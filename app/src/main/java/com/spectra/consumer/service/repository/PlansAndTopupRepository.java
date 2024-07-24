package com.spectra.consumer.service.repository;

import androidx.lifecycle.MutableLiveData;

import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Request.CreateOrderRequest;
import com.spectra.consumer.service.model.Request.CreateTransactionRequest;
import com.spectra.consumer.service.model.Request.DisableOrderRequest;
import com.spectra.consumer.service.model.Request.PostDeactiveTopupPlan;
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;
import com.spectra.consumer.service.model.Request.PostPlanComparisionRequest;
import com.spectra.consumer.service.model.Request.PostProDataChangeRequest;
import com.spectra.consumer.service.model.Request.PostProDataTopUpRequest;
import com.spectra.consumer.service.model.Request.ResponsePaymentStatusRequest;
import com.spectra.consumer.service.model.Request.ResponsePaymentAutopayRequest;
import com.spectra.consumer.service.model.Request.UpdateTokenRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;

public class PlansAndTopupRepository {

    private static PlansAndTopupRepository repository;
    private static ApiService apiService;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static PlansAndTopupRepository getRepository() {
        if (repository == null) {
            synchronized (PlansAndTopupRepository.class) {
                if (repository == null) {
                    repository = new PlansAndTopupRepository();
                    apiService = ApiClient.getPlanAndTopup().create(ApiService.class);
                }
            }
        }
        return repository;
    }

    public MutableLiveData<ApiResponse> getDeviceSign(UpdateTokenRequest getLinkAccountRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getDeviceSingIn(getLinkAccountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getLinkAccountRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
    public MutableLiveData<ApiResponse> getDeviceSignOut(UpdateTokenRequest getLinkAccountRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getDeviceSingOut(getLinkAccountRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, getLinkAccountRequest.getAction())),
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

    public MutableLiveData<ApiResponse> getCompareData(PostPlanComparisionRequest postPlanComparisionRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getCompareData(postPlanComparisionRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, postPlanComparisionRequest.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> getProDataChange(PostProDataChangeRequest postProDataChangeRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getProDataChange(postProDataChangeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, postProDataChangeRequest.getAction())),
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



    public MutableLiveData<ApiResponse> getProDataTopUp(PostProDataTopUpRequest postProDataTopUpRequest) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getProDataTopUp(postProDataTopUpRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, postProDataTopUpRequest.getAction())),
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

    public MutableLiveData<ApiResponse> postDeactivatePlan(PostDeactiveTopupPlan postDeactiveTopupPlan) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.getDeactivaPlan(postDeactiveTopupPlan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, postDeactiveTopupPlan.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> updateStatusForAutopay(ResponsePaymentAutopayRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updateStatusForAutopay(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> createOrder(CreateOrderRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.createOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> updatePaymentStatus(ResponsePaymentStatusRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.updatePaymentStatus(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }

    public MutableLiveData<ApiResponse> createTransaction(CreateTransactionRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.createTransaction(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }


    public MutableLiveData<ApiResponse> disableOrder(DisableOrderRequest request) {
        final MutableLiveData<ApiResponse> data = new MutableLiveData<>();
        disposables.add(apiService.disableOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> data.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> data.setValue(ApiResponse.success(result, request.getAction())),
                        throwable -> data.setValue(ApiResponse.error(throwable))
                ));
        return data;
    }
}
