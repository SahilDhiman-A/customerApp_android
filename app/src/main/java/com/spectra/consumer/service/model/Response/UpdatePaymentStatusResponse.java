package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdatePaymentStatusResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    public UpdatePaymentStatusData getResponse() {
        return response;
    }

    public void setResponse(UpdatePaymentStatusData response) {
        this.response = response;
    }

    @SerializedName("response")
    @Expose
    private UpdatePaymentStatusData response;


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



}