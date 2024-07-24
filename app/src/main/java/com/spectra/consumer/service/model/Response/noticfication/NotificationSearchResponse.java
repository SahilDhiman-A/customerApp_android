package com.spectra.consumer.service.model.Response.noticfication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationSearchResponse implements Serializable
{
    @SerializedName("statusCode")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private ArrayList<NotificationInfoData>  response;
    @SerializedName("message")
    @Expose
    private String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<NotificationInfoData>  getResponse() {
        return response;
    }

    public void setResponse(ArrayList<NotificationInfoData> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}