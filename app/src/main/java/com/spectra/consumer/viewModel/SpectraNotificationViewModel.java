package com.spectra.consumer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.repository.SpectraNotificationRepository;

public class SpectraNotificationViewModel extends ViewModel {


    public MutableLiveData<ApiResponse> getAllNotification(String action, String canID, int skip,int limit) {
        return SpectraNotificationRepository.getRepository().getAllNotification(action, canID, skip,limit);
    }

    public MutableLiveData<ApiResponse> searchNotification(String action, String canID, String key, int page) {
        return SpectraNotificationRepository.getRepository().searchNotification(action, canID, key, page);
    }

    public MutableLiveData<ApiResponse> deleteNotification(String action, String notID) {
        return SpectraNotificationRepository.getRepository().deleteNotification(action, notID);
    }

    public MutableLiveData<ApiResponse> readNotification(String action, String notID) {
        return SpectraNotificationRepository.getRepository().readNotification(action, notID);
    }

    public MutableLiveData<ApiResponse> archivedNotification(String action, String notID) {
        return SpectraNotificationRepository.getRepository().archivedNotification(action, notID);
    }
}