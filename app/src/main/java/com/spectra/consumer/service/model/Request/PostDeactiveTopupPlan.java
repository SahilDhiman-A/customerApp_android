package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDeactiveTopupPlan {

//    "Action":"deactivateTopup",
//            "Authkey":"AdgT68HnjkehEqlkd4",
//            "canId":"208184",
//            "topupId":"TOPUP250@99_RC",
//            "topupType":"RC"

    @SerializedName("Action")
    @Expose
    private String Action;

    @SerializedName("Authkey")
    @Expose
    private String Authkey;

    @SerializedName("canId")
    @Expose
    private String canId;

    @SerializedName("topupId")
    @Expose
    private String topupId;

    @SerializedName("topupType")
    @Expose
    private String topupType;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getAuthkey() {
        return Authkey;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public String getCanId() {
        return canId;
    }

    public void setCanId(String canId) {
        this.canId = canId;
    }

    public String getTopupId() {
        return topupId;
    }

    public void setTopupId(String topupId) {
        this.topupId = topupId;
    }

    public String getTopupType() {
        return topupType;
    }

    public void setTopupType(String topupType) {
        this.topupType = topupType;
    }
}
