package com.spectra.consumer.service.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.spectra.consumer.service.model.NetworkStateModel
import java.util.*
import kotlin.concurrent.timerTask

open class NetworkRepository {

    companion object {
        lateinit var timer : Timer
        var networkMutableData: MutableLiveData<NetworkStateModel> = MutableLiveData<NetworkStateModel>();
        var networkSingleMutableData: MutableLiveData<NetworkStateModel> = MutableLiveData<NetworkStateModel>();
        var networkConnectivityMode: MutableLiveData<String> = MutableLiveData()

        @JvmStatic
        fun isOnline(application: Application): Boolean {
            val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    if (capabilities != null) {
                        when {
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                                networkConnectivityMode.postValue("TRANSPORT_CELLULAR")
                                return true
                            }
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                                networkConnectivityMode.postValue("TRANSPORT_WIFI")
                                return true
                            }
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                                networkConnectivityMode.postValue("TRANSPORT_ETHERNET")
                                return true
                            }
                        }
                    }
                    networkConnectivityMode.postValue("TRANSPORT_ETHERNET")
                    return false
                } else {
                    val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    if (cm != null) {
                        var isWifiConn = false
                        var isMobileConn = false
                        var isEthernet = false
                        for (network in cm.getAllNetworks()) {
                            val networkInfo: NetworkInfo = cm.getNetworkInfo(network)!!
                            when (networkInfo.type) {
                                ConnectivityManager.TYPE_WIFI -> {
                                    isWifiConn = isWifiConn or networkInfo.isConnected
                                }
                            }
                            when (networkInfo.type) {
                                ConnectivityManager.TYPE_MOBILE -> {
                                    isMobileConn = isMobileConn or networkInfo.isConnected
                                }
                            }
                            when (networkInfo.type) {
                                ConnectivityManager.TYPE_ETHERNET -> {
                                    isEthernet = isEthernet or networkInfo.isConnected
                                }
                            }
                        }
                        when {
                            isWifiConn -> {
                                networkConnectivityMode.postValue("TRANSPORT_WIFI")
                                return true
                            }
                            isMobileConn -> {
                                networkConnectivityMode.postValue("TRANSPORT_CELLULAR")
                                return true
                            }
                            isEthernet -> {
                                networkConnectivityMode.postValue("TRANSPORT_ETHERNET")
                                return true
                            }
                        }

                    }
                }
            }
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            networkConnectivityMode.postValue("TRANSPORT_ETHERNET")
            return false
        }
    }

    open fun getNetworkStatus(application: Application):MutableLiveData<String>{
        isOnline(application)
        return networkConnectivityMode;
    }

    open fun getNetworkLiveData(application: Application, delay: Long):MutableLiveData<NetworkStateModel>{
        getNetworkState(application, delay)
        return networkMutableData;
    }

    open fun getSingleNetworkLiveData(application: Application):MutableLiveData<NetworkStateModel>{
        return networkSingleMutableData;
    }

    private fun getNetworkState(application: Application, delay: Long) {
        timer = Timer()
        timer.scheduleAtFixedRate(timerTask { getNetworkState(application) }, 0, delay);
    }

    private fun getNetworkState(application: Application) {
        val wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.connectionInfo
        wifiManager.is5GHzBandSupported
        wifiManager.isP2pSupported

        var connected : Boolean = isOnline(application)
        var connectedStaus : String = if(connected)
            "Connected"
        else
            "Not Connected"

        isOnline(application)

        var networkStateModel: NetworkStateModel =
                NetworkStateModel(
                        frequency = wifiInfo.frequency.toString(),
                        ipAddress = wifiInfo.ipAddress.toString(),
                        rssi = wifiInfo.rssi,
                        bssid = wifiInfo.ssid,
                        linkspeed = wifiInfo.linkSpeed.toString(),
                        connectedStatus = connectedStaus
                )
//        val level = WifiManager.calculateSignalLevel(wifiInfo.rssi, 5)
//        val net = wifiInfo.rssi.toString()
        networkMutableData.postValue(networkStateModel);
    }

    open fun getSingleNetworkState(application: Application) {
        val wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.connectionInfo

        wifiManager.is5GHzBandSupported
        wifiManager.isP2pSupported

        var connected : Boolean = isOnline(application)
        var connectedStaus : String = if(connected)
            "Connected"
        else
            "Not Connected"

        isOnline(application)

        var networkStateModel: NetworkStateModel =
                NetworkStateModel(
                        frequency = wifiInfo.frequency.toString(),
                        ipAddress = wifiInfo.ipAddress.toString(),
                        rssi = wifiInfo.rssi,
                        bssid = wifiInfo.ssid,
                        linkspeed = wifiInfo.linkSpeed.toString(),
                        connectedStatus = connectedStaus
                )
//        val level = WifiManager.calculateSignalLevel(wifiInfo.rssi, 5)
//        val net = wifiInfo.rssi.toString()
        networkSingleMutableData.postValue(networkStateModel);
    }

   open fun clearTimer(){
       //timer.cancel()
        //timer?.purge()
    }
}