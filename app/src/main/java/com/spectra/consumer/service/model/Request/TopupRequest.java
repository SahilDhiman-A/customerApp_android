package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopupRequest implements Serializable
{

    //"Authkey":"AdgT68HnjkehEqlkd4",
    //"Action":"getTopup",
    //"canID":"139525",
    //"basePlan":"HBB_250_1000_HY"

    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("canID")
    @Expose
    private String canID;
    @SerializedName("basePlan")
    @Expose
    private String basePlan;

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

    public String getBasePlan() {
        return basePlan;
    }

    public void setBasePlan(String basePlan) {
        this.basePlan = basePlan;
    }

}