package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostSS5GHzRequest implements Serializable {

    @SerializedName("speedOn5Ghz")
    @Expose
    private String speedOn5Ghz;

    public String getSpeedOn5Ghz() {
        return speedOn5Ghz;
    }
    public void setSpeedOn5Ghz(String facingIssue) {
        this.speedOn5Ghz = facingIssue;
    }
}
