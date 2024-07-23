package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateMobileRequest
{
    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("canID")
    @Expose
    private String canID;
    @SerializedName("newMobile")
    @Expose
    private String newMobile;
    @SerializedName("OTP")
    @Expose
    private String OTP;

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

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }
    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

}