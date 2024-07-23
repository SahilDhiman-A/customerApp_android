package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CanResponse {



  
    private boolean isLinked =true;

    @SerializedName("base_canid")
    @Expose
    private String base_canid;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("link_canid")
    @Expose
    private String link_canid;

    public String getBase_canid() {
        return base_canid;
    }

    public void setBase_canid(String base_canid) {
        this.base_canid = base_canid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLink_canid() {
        return link_canid;
    }

    public void setLink_canid(String link_canid) {
        this.link_canid = link_canid;
    }

    public boolean isLinked() {
        return isLinked;
    }

    public void setLinked(boolean linked) {
        isLinked = linked;
    }
}