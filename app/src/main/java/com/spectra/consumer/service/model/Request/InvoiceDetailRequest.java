package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InvoiceDetailRequest implements Serializable
{

    @SerializedName("Action")
    @Expose
    private String action;

    @SerializedName("Authkey")
    @Expose
    private String authkey;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }



    public String getAuthkey() {
        return authkey;
    }

    public void setAuthkey(String authkey) {
        this.authkey = authkey;
    }

}