package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateTransactionData implements Serializable
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("txnId")
    @Expose
    private String txnId;
    @SerializedName("keyId")
    @Expose
    private String keyId;
    @SerializedName("pgId")
    @Expose
    private String pgId;
    @SerializedName("keySecret")
    @Expose
    private String keySecret;
    @SerializedName("orderId")
    @Expose
    private String orderId;



    @SerializedName("customerId")
    @Expose
    private String customerId;



    @SerializedName("paidAmount")
    @Expose
    private String paidAmount;

    public String getPaidAmount() {

        if(TextUtils.isEmpty(paidAmount)){
            paidAmount="10";
        }
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}