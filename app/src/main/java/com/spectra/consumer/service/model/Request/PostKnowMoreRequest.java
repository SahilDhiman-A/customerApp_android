package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostKnowMoreRequest {

        //    "":"knowMore",
//            "":"AdgT68HnjkehEqlkd4",
//            "":"208184"
    @SerializedName("Action")
    @Expose
    private String Action;

    @SerializedName("Authkey")
    @Expose
    private String Authkey;

    @SerializedName("planId")
    @Expose
    private String planId;


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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
