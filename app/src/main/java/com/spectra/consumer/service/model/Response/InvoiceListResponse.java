package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InvoiceListResponse implements Serializable
{

    @SerializedName("invoiceNo")
    @Expose
    private String invoiceNo;
    @SerializedName("actNo")
    @Expose
    private String actNo;
    @SerializedName("startdt")
    @Expose
    private String startdt;
    @SerializedName("enddt")
    @Expose
    private String enddt;
    @SerializedName("duedt")
    @Expose
    private String duedt;
    @SerializedName("invoicedt")
    @Expose
    private String invoicedt;
    @SerializedName("openingBalance")
    @Expose
    private String openingBalance;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("content")
    @Expose
    private List<Object> content = null;
    @SerializedName("itNo")
    @Expose
    private String itNo;
    @SerializedName("usrNo")
    @Expose
    private String usrNo;
    @SerializedName("unPaidBalance")
    @Expose
    private String unPaidBalance;
    @SerializedName("voucherNo")
    @Expose
    private String voucherNo;
    @SerializedName("orgNo")
    @Expose
    private String orgNo;
    @SerializedName("cslno")
    @Expose
    private String cslno;
    @SerializedName("invoiceCharge")
    @Expose
    private String invoiceCharge;
    @SerializedName("displayInvNo")
    @Expose
    private String displayInvNo;
    @SerializedName("tdsAmount")
    @Expose
    private double tdsAmount;
    @SerializedName("tdsSlab")
    @Expose
    private String tdsSlab;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getStartdt() {
        return startdt;
    }

    public void setStartdt(String startdt) {
        this.startdt = startdt;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }

    public String getDuedt() {
        return duedt;
    }

    public void setDuedt(String duedt) {
        this.duedt = duedt;
    }

    public String getInvoicedt() {
        return invoicedt;
    }

    public void setInvoicedt(String invoicedt) {
        this.invoicedt = invoicedt;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Object> getContent() {
        return content;
    }

    public void setContent(List<Object> content) {
        this.content = content;
    }

    public String getItNo() {
        return itNo;
    }

    public void setItNo(String itNo) {
        this.itNo = itNo;
    }

    public String getUsrNo() {
        return usrNo;
    }

    public void setUsrNo(String usrNo) {
        this.usrNo = usrNo;
    }

    public String getUnPaidBalance() {
        return unPaidBalance;
    }

    public void setUnPaidBalance(String unPaidBalance) {
        this.unPaidBalance = unPaidBalance;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getCslno() {
        return cslno;
    }

    public void setCslno(String cslno) {
        this.cslno = cslno;
    }

    public String getInvoiceCharge() {
        return invoiceCharge;
    }

    public void setInvoiceCharge(String invoiceCharge) {
        this.invoiceCharge = invoiceCharge;
    }

    public String getDisplayInvNo() {
        return displayInvNo;
    }

    public void setDisplayInvNo(String displayInvNo) {
        this.displayInvNo = displayInvNo;
    }

    public double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public String getTdsSlab() {
        return tdsSlab;
    }

    public void setTdsSlab(String tdsSlab) {
        this.tdsSlab = tdsSlab;
    }

}