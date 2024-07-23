package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostMRTGRequest implements Serializable {
    @SerializedName("isUpgradeBandwidth")
    @Expose
    private String isUpgradeBandwidth;

    public String getIsUpgradeBandwidth() {
        return isUpgradeBandwidth;
    }

    public void setIsUpgradeBandwidth(String isUpgradeBandwidth) {
        this.isUpgradeBandwidth = isUpgradeBandwidth;
    }
}
