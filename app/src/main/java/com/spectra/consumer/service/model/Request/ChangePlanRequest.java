package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangePlanRequest implements Serializable
{

    @SerializedName("Authkey")
    @Expose
    private String authkey;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("canId")
    @Expose
    private String canId;
    @SerializedName("pkgName")
    @Expose
    private String pkgName;

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

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

}