package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SiResponse implements Serializable
{

    @SerializedName("siStatus")
    @Expose
    private String siStatus;

    public String getSiStatus() {
        return siStatus;
    }

    public void setSiStatus(String siStatus) {
        this.siStatus = siStatus;
    }

}