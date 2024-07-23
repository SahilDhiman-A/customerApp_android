package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostSS2_4GHzRequest implements Serializable {

    @SerializedName("speedOn2.4Ghz")
    @Expose
    private String speedOn2_4Ghz;

    public String getSpeedOn2_4Ghz() {
        return speedOn2_4Ghz;
    }

    public void setSpeedOn2_4Ghz(String speedOn2_4Ghz) {
        this.speedOn2_4Ghz = speedOn2_4Ghz;
    }
}
