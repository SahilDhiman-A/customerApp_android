package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EmailUpdateOtpResponse implements Serializable
{

    @SerializedName("EmailID")
    @Expose
    private String emailID;
    @SerializedName("OTP")
    @Expose
    private Integer oTP;
    private final static long serialVersionUID = -4534970602212714442L;

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public Integer getOTP() {
        return oTP;
    }

    public void setOTP(Integer oTP) {
        this.oTP = oTP;
    }

}