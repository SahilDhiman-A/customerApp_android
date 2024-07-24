package com.spectra.consumer.service.model.Response.noticfication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spectra.consumer.service.model.Response.Contact;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationResponse implements Serializable
{
    @SerializedName("statusCode")
    @Expose
    private int status;

    @SerializedName("additionalInfo")
    @Expose
    private int additionalInfo;

    @SerializedName("data")
    @Expose
    private ArrayList<NotificationData>  response;
    @SerializedName("message")
    @Expose
    private String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getMaxCount() {
        return additionalInfo;
    }

    public void setAdditionalInfo(int additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    public ArrayList<NotificationData>  getResponse() {
        return response;
    }

    public void setResponse(ArrayList<NotificationData> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}