package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetLinkAccountRequest implements Serializable
{
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("baseCanID")
    @Expose
    private String baseCanID;

    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCanID() {
        return baseCanID;
    }

    public void setCanID(String canID) {
        this.baseCanID = canID;
    }
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }
}
