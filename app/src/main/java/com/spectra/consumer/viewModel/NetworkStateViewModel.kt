package com.spectra.consumer.viewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.spectra.consumer.service.model.NetworkStateModel
import com.spectra.consumer.service.repository.NetworkRepository

open class NetworkStateViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        private val networkRepository: NetworkRepository = NetworkRepository()
    }

    fun getNetworkData(delay:Long): MutableLiveData<NetworkStateModel> {
        return networkRepository.getNetworkLiveData(this.getApplication(),delay);
    }


    fun getSingleNetworkState():MutableLiveData<NetworkStateModel>{
        return networkRepository.getSingleNetworkLiveData(this.getApplication())
    }


    fun getSingleNetwork(){
        networkRepository.getSingleNetworkState(this.getApplication())
    }

    fun isOnlineStatus(application: Application):MutableLiveData<String>{
        return networkRepository.getNetworkStatus(application)
    }

    override fun onCleared() {
        super.onCleared()
        networkRepository.clearTimer()
    }
}