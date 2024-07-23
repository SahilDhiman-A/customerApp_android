package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateGSTNRequest implements Serializable
{

    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("canID")
    @Expose
    private String canID;


    @SerializedName("tanNumber")
    @Expose
    private String tanNumber;

    @SerializedName("gstNumber")
    @Expose
    private String gstNumber;
    @SerializedName("Authkey")
    @Expose
    private String authkey;

    public String getGstNumber() {
        return gstNumber;
    }
    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCanID() {
        return canID;
    }

    public void setCanID(String canID) {
        this.canID = canID;
    }

    public String getTanNumber() {
        return tanNumber;
    }

    public void setTanNumber(String tanNumber) {
        this.tanNumber = tanNumber;
    }


    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

}