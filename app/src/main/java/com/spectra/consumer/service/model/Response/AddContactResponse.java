package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AddContactResponse implements Serializable
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private Object response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("canID")
    @Expose
    private String canID;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object  getResponse() {
        return response;
    }

    public void setResponse(Contact response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}