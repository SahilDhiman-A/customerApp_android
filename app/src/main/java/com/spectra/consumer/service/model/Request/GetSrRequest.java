package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetSrRequest implements Serializable
{
    public String getCanID2() {
        return canID2;
    }

    public void setCanID2(String canID2) {
        this.canID2 = canID2;
    }

    @SerializedName("canId")
    @Expose
    private String canID2;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("canID")
    @Expose
    private String canID;

    @SerializedName("srNumber")
    @Expose
    private String srNumber;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

    public String getCanID() {
        return canID;
    }

    public void setCanID(String canID) {
        this.canID = canID;
    }

    public String getSrNumber() {
        return srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

}