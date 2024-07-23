package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetplanResponse implements Serializable
{

    @SerializedName("planNo")
    @Expose
    private String planNo;
    @SerializedName("planName")
    @Expose
    private String planName;
    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("rcCharge")
    @Expose
    private List<RcChargeResponse> rcCharge = null;
    @SerializedName("nrcCharge")
    @Expose
    private List<NrcChargeResponse> nrcCharge = null;
    private final static long serialVersionUID = 1518316592695070346L;

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public List<RcChargeResponse> getRcCharge() {
        return rcCharge;
    }

    public void setRcCharge(List<RcChargeResponse> rcCharge) {
        this.rcCharge = rcCharge;
    }

    public List<NrcChargeResponse> getNrcCharge() {
        return nrcCharge;
    }

    public void setNrcCharge(List<NrcChargeResponse> nrcCharge) {
        this.nrcCharge = nrcCharge;
    }

}