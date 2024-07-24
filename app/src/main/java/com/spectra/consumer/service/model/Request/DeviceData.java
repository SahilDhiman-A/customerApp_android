package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceData implements Serializable
{
    @SerializedName("canId")
    @Expose
    private ArrayList<String>  canIds;
    @SerializedName("deviceToken")
    @Expose
    private ArrayList<String>  deviceToken;

    @SerializedName("deviceType")
    @Expose
    private ArrayList<String> deviceType;


    public ArrayList<String> getCanIds() {
        return canIds;
    }

    public void setCanIds(ArrayList<String> canIds) {
        this.canIds = canIds;
    }

    public ArrayList<String> getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(ArrayList<String> deviceToken) {
        this.deviceToken = deviceToken;
    }

    public ArrayList<String> getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(ArrayList<String> deviceType) {
        this.deviceType = deviceType;
    }
}
