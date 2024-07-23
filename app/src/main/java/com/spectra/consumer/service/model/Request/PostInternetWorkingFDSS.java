package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//isInternetWorking
public class PostInternetWorkingFDSS {
    @SerializedName("isInternetWorking")
    @Expose
    private String isInternetWorking;

    public String getIsInternetWorking() {
        return isInternetWorking;
    }

    public void setIsInternetWorking(String isInternetWorking) {
        this.isInternetWorking = isInternetWorking;
    }
}
