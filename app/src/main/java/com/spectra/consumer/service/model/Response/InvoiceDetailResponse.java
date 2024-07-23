package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InvoiceDetailResponse implements Serializable
{
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = -6194529421806488110L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

}