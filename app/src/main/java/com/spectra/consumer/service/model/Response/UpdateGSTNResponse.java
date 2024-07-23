package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateGSTNResponse implements Serializable
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


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public Object getResponse() {
        return response;
    }

    public void setResponse(GSTNObject response) {
        this.response = response;
    }
}