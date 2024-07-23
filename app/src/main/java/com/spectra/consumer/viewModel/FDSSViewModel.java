package com.spectra.consumer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.repository.FDSSRepository;

public class FDSSViewModel extends ViewModel {

    public MutableLiveData<ApiResponse> getFDSSNoInternet(String request, String code) {
        return FDSSRepository.getRepository().getFDSSNoInternet(request,code);
    }


    public MutableLiveData<ApiResponse> fupYesORNO(String request, Object reqObject,String code) {
        return FDSSRepository.getRepository().fupYesOrNO(request,reqObject,code);
    }
}
