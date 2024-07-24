package com.spectra.consumer.service.model.Response.noticfication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationResponseBase implements Serializable
{
    @SerializedName("statusCode")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private Object  response;
    @SerializedName("message")
    @Expose
    private String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object  getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}