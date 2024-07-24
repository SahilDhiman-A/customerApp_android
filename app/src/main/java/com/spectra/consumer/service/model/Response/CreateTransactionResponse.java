package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateTransactionResponse implements Serializable
{
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    public CreateTransactionData getResponse() {
        return response;
    }

    public void setResponse(CreateTransactionData response) {
        this.response = response;
    }

    @SerializedName("response")
    @Expose
    private CreateTransactionData response;


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