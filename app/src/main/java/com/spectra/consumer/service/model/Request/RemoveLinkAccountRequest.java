package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RemoveLinkAccountRequest implements Serializable
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
    

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("linkCanID")
    @Expose
    private String linkCanID;

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getmobileNo() {
        return mobileNo;
    }

    public void setmobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getlinkCanID() {
        return linkCanID;
    }

    public void setlinkCanID(String linkCanID) {
        this.linkCanID = linkCanID;
    }
    
    
    
    
    

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

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }
}
