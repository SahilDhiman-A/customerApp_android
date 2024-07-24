package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OfferListResponse implements Serializable
{

    @SerializedName("planid")
    @Expose
    private String planid;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("charges")
    @Expose
    private String charges;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("frequency")
    @Expose
    private String frequency;

    private boolean isSelected = false;

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

}