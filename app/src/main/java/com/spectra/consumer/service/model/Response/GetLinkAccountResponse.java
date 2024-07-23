package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GetLinkAccountResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    public ArrayList<CanResponse> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<CanResponse> response) {
        this.response = response;
    }

    @SerializedName("response")
    @Expose
    private ArrayList<CanResponse> response;

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