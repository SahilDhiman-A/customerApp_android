package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataUsageResponse implements Serializable
{

    @SerializedName("actName")
    @Expose
    private String actName;
    @SerializedName("actNo")
    @Expose
    private String actNo;
    @SerializedName("actid")
    @Expose
    private String actid;
    @SerializedName("bytesin")
    @Expose
    private String bytesin;
    @SerializedName("bytesout")
    @Expose
    private String bytesout;
    @SerializedName("closeReason")
    @Expose
    private String closeReason;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("ipaddr")
    @Expose
    private String ipaddr;
    @SerializedName("macaddr")
    @Expose
    private String macaddr;
    @SerializedName("nasNo")
    @Expose
    private String nasNo;
    @SerializedName("sesNo")
    @Expose
    private String sesNo;
    @SerializedName("startDt")
    @Expose
    private String startDt;
    @SerializedName("total")
    @Expose
    private String total;

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActNo() {
        return actNo;
    }

    public void setActNo(String actNo) {
        this.actNo = actNo;
    }

    public String getActid() {
        return actid;
    }

    public void setActid(String actid) {
        this.actid = actid;
    }

    public String getBytesin() {
        return bytesin;
    }

    public void setBytesin(String bytesin) {
        this.bytesin = bytesin;
    }

    public String getBytesout() {
        return bytesout;
    }

    public void setBytesout(String bytesout) {
        this.bytesout = bytesout;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public void setMacaddr(String macaddr) {
        this.macaddr = macaddr;
    }

    public String getNasNo() {
        return nasNo;
    }

    public void setNasNo(String nasNo) {
        this.nasNo = nasNo;
    }

    public String getSesNo() {
        return sesNo;
    }

    public void setSesNo(String sesNo) {
        this.sesNo = sesNo;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}