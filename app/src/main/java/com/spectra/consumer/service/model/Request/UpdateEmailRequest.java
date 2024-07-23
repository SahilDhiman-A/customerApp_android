package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateEmailRequest {

    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("canID")
    @Expose
    private String canID;
    @SerializedName("newEmailID")
    @Expose
    private String newEmailID;
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
    public String getNewEmailID() {
        return newEmailID;
    }

    public void setNewEmailID(String newEmailID) {
        this.newEmailID = newEmailID;
    }
    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }


}
