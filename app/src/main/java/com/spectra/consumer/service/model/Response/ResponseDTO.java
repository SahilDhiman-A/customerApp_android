package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
    private String problemType;
    private String problemStatus;
    private String outstandingAmount;
    private String dueDate;
    private String messageCode;
    private String messageDescription;
    private String etr;
    private String alarmType;
    private String powerLevel;
    private String utilizationPercentage;
    private String fupSpeed;
    private String subType;
    private String productSegment;
    private String endDate;
    private String extETR;
    private InvoiceDTO invoiceList;
    private String type;
    private String consumedVolume;

    public String getConsumedVolume() {
        return consumedVolume;
    }

    public void setConsumedVolume(String consumedVolume) {
        this.consumedVolume = consumedVolume;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getEndDate() {
        return endDate;
    }

    public InvoiceDTO getInvoiceList() {
        return invoiceList;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setInvoiceList(InvoiceDTO invoiceList) {
        this.invoiceList = invoiceList;
    }

    public String getEtr() {
        return etr;
    }

    public String getProductSegment() {
        if(TextUtils.isEmpty(productSegment)){
            productSegment="";
        }
        return productSegment;
    }

    public void setProductSegment(String productSegment) {
        this.productSegment = productSegment;
    }

    public void setEtr(String etr) {
        this.etr = etr;
    }

    public String getExtETR() {
        return extETR;
    }

    public void setExtETR(String extETR) {
        this.extETR = extETR;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    private String srNo;

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    private String Product;

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemStatus(String problemStatus) {
        this.problemStatus = problemStatus;
    }

    public String getProblemStatus() {
        return problemStatus;
    }

    public void setOutstandingAmount(String outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getOutstandingAmount() {
        if(TextUtils.isEmpty(outstandingAmount)){outstandingAmount="0";}
        return outstandingAmount;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public String getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public void setUtilizationPercentage(String utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public String getFupSpeed() {
        return fupSpeed;
    }

    public void setFupSpeed(String fupSpeed) {
        this.fupSpeed = fupSpeed;
    }

    @Override
    public String toString() {
        return
                "ResponseDTO{" +
                        "problemType = '" + problemType + '\'' +
                        ",problemStatus = '" + problemStatus + '\'' +
                        ",outstandingAmount = '" + outstandingAmount + '\'' +
                        ",dueDate = '" + dueDate + '\'' +
                        ",messageCode = '" + messageCode + '\'' +
                        ",messageDescription = '" + messageDescription + '\'' +
                        "}";
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(String powerLevel) {
        this.powerLevel = powerLevel;
    }
}