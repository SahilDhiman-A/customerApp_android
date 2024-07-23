package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DisableSiResponse implements Serializable
{

    @SerializedName("StandardInstrunction")
    @Expose
    private String standardInstrunction;

    public String getStandardInstrunction() {
        return standardInstrunction;
    }

    public void setStandardInstrunction(String standardInstrunction) {
        this.standardInstrunction = standardInstrunction;
    }

}