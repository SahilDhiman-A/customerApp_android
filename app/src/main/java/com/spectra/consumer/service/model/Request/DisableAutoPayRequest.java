package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DisableAutoPayRequest implements Serializable
{

    @SerializedName("secretKey")
    @Expose
    private String secretKey;
    @SerializedName("canID")
    @Expose
    private String canID;
    @SerializedName("billAmount")
    @Expose
    private String billAmount;
    @SerializedName("returnUrl")
    @Expose
    private String returnUrl;
    @SerializedName("requetType")
    @Expose
    private String requetType;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getCanID() {
        return canID;
    }

    public void setCanID(String canID) {
        this.canID = canID;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getRequetType() {
        return requetType;
    }

    public void setRequetType(String requetType) {
        this.requetType = requetType;
    }

}