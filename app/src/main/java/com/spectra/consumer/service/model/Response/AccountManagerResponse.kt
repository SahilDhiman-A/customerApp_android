package com.spectra.consumer.service.model.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class AccountManagerResponse : Serializable {
    @Expose
    @SerializedName("name")
    var name: String? = null
    @Expose
    @SerializedName("address")
    var address: String? = null
    @Expose
    @SerializedName("mobile")
    var mobile: String? = null
    @Expose
    @SerializedName("email")
    var email: String? = null
}