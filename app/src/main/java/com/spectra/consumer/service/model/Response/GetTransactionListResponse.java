package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetTransactionListResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private List<TransactionListResponse> response = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TransactionListResponse> getResponse() {
        return response;
    }

    public void setResponse(List<TransactionListResponse> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}