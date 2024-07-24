package com.spectra.consumer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
import com.spectra.consumer.service.repository.PlansAndTopupRepository;

import retrofit2.http.Body;

public class PlanAndTopupViewModel extends ViewModel {

    public MutableLiveData<ApiResponse> getKnowMore(PostKnowMoreRequest postKnowMoreRequest) {
        return PlansAndTopupRepository.getRepository().getKnowMore(postKnowMoreRequest);
    }
    public MutableLiveData<ApiResponse> getDeviceSign(UpdateTokenRequest updateTokenRequest) {
        return PlansAndTopupRepository.getRepository().getDeviceSign(updateTokenRequest);
    }
    public MutableLiveData<ApiResponse> getDeviceSignOut(UpdateTokenRequest updateTokenRequest) {
        return PlansAndTopupRepository.getRepository().getDeviceSignOut(updateTokenRequest);
    }
    public MutableLiveData<ApiResponse> getCompareData(PostPlanComparisionRequest postPlanComparisionRequest) {
        return PlansAndTopupRepository.getRepository().getCompareData(postPlanComparisionRequest);
    }

    public MutableLiveData<ApiResponse> getProDataTopUp(PostProDataTopUpRequest postProDataTopUpRequest) {
        return PlansAndTopupRepository.getRepository().getProDataTopUp(postProDataTopUpRequest);
    }

    public MutableLiveData<ApiResponse> getProDataChange(PostProDataChangeRequest proDataChangeRequest) {
        return PlansAndTopupRepository.getRepository().getProDataChange(proDataChangeRequest);
    }

    public MutableLiveData<ApiResponse> changePlan( ChangePlanRequest changePlanRequest) {
        return PlansAndTopupRepository.getRepository().changePlan(changePlanRequest);
    }

    public MutableLiveData<ApiResponse> consumedTopup( ConsumedTopupRequest forgotPasswordRequest) {
        return PlansAndTopupRepository.getRepository().consumedTopup(forgotPasswordRequest);
    }

    public MutableLiveData<ApiResponse> deactivatePlan( PostDeactiveTopupPlan postDeactiveTopupPlan) {
        return PlansAndTopupRepository.getRepository().postDeactivatePlan(postDeactiveTopupPlan);
    }

    public MutableLiveData<ApiResponse> createTransaction( CreateTransactionRequest request) {
        return PlansAndTopupRepository.getRepository().createTransaction(request);
    }
    public MutableLiveData<ApiResponse> updatePaymentStatus( ResponsePaymentStatusRequest request) {
        return PlansAndTopupRepository.getRepository().updatePaymentStatus(request);
    }

    public MutableLiveData<ApiResponse> createOrder( CreateOrderRequest request) {
        return PlansAndTopupRepository.getRepository().createOrder(request);
    }

    public MutableLiveData<ApiResponse> updateStatusForAutopay( ResponsePaymentAutopayRequest request) {
        return PlansAndTopupRepository.getRepository().updateStatusForAutopay(request);
    }

    public MutableLiveData<ApiResponse> disableOrder( DisableOrderRequest request) {
        return PlansAndTopupRepository.getRepository().disableOrder(request);
    }

}
