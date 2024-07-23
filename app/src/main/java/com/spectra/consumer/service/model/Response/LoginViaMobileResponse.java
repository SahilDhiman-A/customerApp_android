package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spectra.consumer.Models.UserData;

import java.io.Serializable;
import java.util.List;

public class LoginViaMobileResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    public List<LoginMobileResponse> response = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LoginMobileResponse>  getResponse() {
        return response;
    }

    public void setResponse(List<LoginMobileResponse>  response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}