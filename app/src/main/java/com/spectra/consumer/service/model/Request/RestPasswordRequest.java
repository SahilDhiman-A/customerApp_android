package com.spectra.consumer.service.model.Request;

import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class RestPasswordRequest implements Serializable
{

    @SerializedName("Action")
    @Expose
    private String action;

    @SerializedName("newPassword")
    @Expose
    private String newPassword;


    @SerializedName("canID")
    @Expose
    private String canID;


    @SerializedName("oldPassword")
    @Expose
    private String oldPassword;

    @SerializedName("Authkey")
    @Expose
    private String authkey;


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

    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {

        byte[] data = newPassword.getBytes(StandardCharsets.UTF_8);
        newPassword = Base64.encodeToString(data, Base64.DEFAULT);
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        byte[] data = oldPassword.getBytes(StandardCharsets.UTF_8);
        oldPassword = Base64.encodeToString(data, Base64.DEFAULT);
        this.oldPassword = oldPassword;
    }
}