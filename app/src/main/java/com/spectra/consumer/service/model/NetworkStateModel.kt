package com.spectra.consumer.service.model

import java.io.Serializable

data class NetworkStateModel(
    var frequency: String,
    var ipAddress: String,
    var rssi: Int,
    var bssid: String?,
    var linkspeed: String,
    var connectedStatus:String): Serializable{
    companion object {
        private const val serialVersionUID = 20180617104400L
    }
}