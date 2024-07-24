package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPlanComparisionRequest {

    @SerializedName("Action")
    @Expose
    private String Action;

    @SerializedName("Authkey")
    @Expose
    private String Authkey;

    @SerializedName("planIdList")
    @Expose
    private List<String> planId;


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

    public List<String> getPlanId() {
        return planId;
    }

    public void setPlanId(List<String> planId) {
        this.planId = planId;
    }
}
