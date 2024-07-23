package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddTopUpRequest implements Serializable
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
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("topupName")
    @Expose
    private String topupName;
    @SerializedName("topupType")
    @Expose
    private String topupType;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTopupName() {
        return topupName;
    }

    public void setTopupName(String topupName) {
        this.topupName = topupName;
    }

    public String getTopupType() {
        return topupType;
    }

    public void setTopupType(String topupType) {
        this.topupType = topupType;
    }

}