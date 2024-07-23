package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostFDSSFluctuatingLightRequest implements Serializable {
    @SerializedName("fluctuatingLight")
    @Expose
    private String fluctuatingLight;

    public String getFluctuatingLight() {
        return fluctuatingLight;
    }

    public void setFluctuatingLight(String fluctuatingLight) {
        this.fluctuatingLight = fluctuatingLight;
    }
}
