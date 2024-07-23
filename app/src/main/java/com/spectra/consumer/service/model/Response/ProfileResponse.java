package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileResponse implements Serializable
{

    @SerializedName("GSTN")
    @Expose
    private Object GSTN;

    public Object getGSTN() {
        return GSTN.toString();
    }

    public void setGSTN(String GSTN) {
        this.GSTN = GSTN;
    }

    public String getTAN() {
        return TAN;
    }

    public void setTAN(String TAN) {
        this.TAN = TAN;
    }

    @SerializedName("TAN")
    @Expose
    private String TAN;

    @SerializedName("billTo")
    @Expose
    private BillToResponse billTo;
    @SerializedName("shipTo")
    @Expose
    private ShipToResponse shipTo;
    @SerializedName("BillingTo")
    @Expose
    private Object billingTo;
    @SerializedName("installationA")
    @Expose
    private String installationA;
    @SerializedName("installationB")
    @Expose
    private String installationB;

    public BillToResponse getBillTo() {
        return billTo;
    }

    public void setBillTo(BillToResponse billTo) {
        this.billTo = billTo;
    }

    public ShipToResponse getShipTo() {
        return shipTo;
    }

    public void setShipTo(ShipToResponse shipTo) {
        this.shipTo = shipTo;
    }

    public BillingToResponse getBillingTo() {
        if(TextUtils.isEmpty(billingTo.toString()))
        {
            BillingToResponse billingToResponse= (BillingToResponse) billingTo;
            return billingToResponse;
        }
        return null;
    }

    public void setBillingTo(Object billingTo) {
        this.billingTo = billingTo;
    }

    public String getInstallationA() {
        return installationA;
    }

    public void setInstallationA(String installationA) {
        this.installationA = installationA;
    }

    public String getInstallationB() {
        return installationB;
    }

    public void setInstallationB(String installationB) {
        this.installationB = installationB;
    }


}