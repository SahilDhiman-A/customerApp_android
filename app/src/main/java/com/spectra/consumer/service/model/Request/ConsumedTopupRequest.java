package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ConsumedTopupRequest implements Serializable
{

    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("canId")
    @Expose
    private String canID;

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
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


}