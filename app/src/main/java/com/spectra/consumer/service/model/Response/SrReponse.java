package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SrReponse implements Serializable
{
    @SerializedName("ActionCode")
    @Expose
    private String actionCode;


    @SerializedName("SRstatusETR")
    @Expose
    private String sRstatusETR;


    @SerializedName("MessageTemplate")
    @Expose
    private String messageTemplate;


    @SerializedName("srNumber")
    @Expose
    private String srNumber;
    @SerializedName("problemType")
    @Expose
    private String problemType;
    @SerializedName("subType")
    @Expose
    private String subType;
    @SerializedName("subSubType")
    @Expose
    private String subSubType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("lastUpdatedOn")
    @Expose
    private String lastUpdatedOn;

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    @SerializedName("createdon")
    @Expose
    private String createdon;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ETR")
    @Expose
    private String eTR;

    public String getSrNumber() {
        if(srNumber==null){
            srNumber="";
        }
        return srNumber;
    }

    public void setSrNumber(String srNumber) {
        this.srNumber = srNumber;
    }

    public String getProblemType() {
        if(TextUtils.isEmpty(problemType)){
            return "";
        }
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getSubType() {
        if(TextUtils.isEmpty(subType)){
            return "";
        }
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubSubType() {
        return subSubType;
    }

    public void setSubSubType(String subSubType) {
        this.subSubType = subSubType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getStatus() {
        if(TextUtils.isEmpty(status)){
            return "";
        }
        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getETR() {
        if(TextUtils.isEmpty(eTR)){
            return "";
        }
        return eTR;
    }
    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getsRstatusETR() {
        return sRstatusETR;
    }

    public void setsRstatusETR(String sRstatusETR) {
        this.sRstatusETR = sRstatusETR;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String geteTR() {
        return eTR;
    }

    public void seteTR(String eTR) {
        this.eTR = eTR;
    }


    public void setETR(String eTR) {
        this.eTR = eTR;
    }

}