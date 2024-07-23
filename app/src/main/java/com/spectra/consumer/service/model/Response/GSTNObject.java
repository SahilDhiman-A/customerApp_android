package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GSTNObject implements Serializable
{

    @SerializedName("ledgerAccountId")
    @Expose
    private String ledgerAccountId;

    @SerializedName("statutoryNo")
    @Expose
    private String statutoryNo;

    @SerializedName("statutoryTypeNo")
    @Expose
    private String statutoryTypeNo;

    @SerializedName("value")
    @Expose
    private String value;


    public String getLedgerAccountId() {
        return ledgerAccountId;
    }

    public void setLedgerAccountId(String ledgerAccountId) {
        this.ledgerAccountId = ledgerAccountId;
    }

    public String getStatutoryNo() {
        return statutoryNo;
    }

    public void setStatutoryNo(String statutoryNo) {
        this.statutoryNo = statutoryNo;
    }

    public String getStatutoryTypeNo() {
        return statutoryTypeNo;
    }

    public void setStatutoryTypeNo(String statutoryTypeNo) {
        this.statutoryTypeNo = statutoryTypeNo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}