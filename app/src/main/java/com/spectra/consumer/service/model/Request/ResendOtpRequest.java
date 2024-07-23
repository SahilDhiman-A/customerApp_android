package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResendOtpRequest implements Serializable
{

    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;


    @SerializedName("newEmailID")
    @Expose
    private String newEmailID;


    @SerializedName("newMobile")
    @Expose
    private String newMobile;


    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("Authkey")
    @Expose
    private String authkey;


    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }
    public String getNewEmailID() {
        return newEmailID;
    }

    public void setNewEmailID(String newEmailID) {
        this.newEmailID = newEmailID;
    }

}