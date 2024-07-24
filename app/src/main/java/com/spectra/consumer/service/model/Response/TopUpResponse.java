package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TopUpResponse implements Serializable {
    @SerializedName("created_date")
    @Expose
    private String created_date;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("data_volume")
    @Expose
    private String data_volume;

    @SerializedName("pg_price")
    @Expose
    private String pg_price;

    @SerializedName("topup_name")
    @Expose
    private String topup_name;

    @SerializedName("topup_id")
    @Expose
    private String topup_id;

    @SerializedName("deactivateFlag")
    @Expose
    private boolean deactivateFlag;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("data")
    @Expose
    private String data;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("proDataCharges")
    @Expose
    private String proDataCharges;

    @SerializedName("pgDataCharges")
    @Expose
    private String pgDataCharges;


    public String getProDataCharges() {
        return proDataCharges;
    }

    public void setProDataCharges(String proDataCharges) {
        this.proDataCharges = proDataCharges;
    }

    public String getPgDataCharges() {
        return pgDataCharges;
    }

    public void setPgDataCharges(String pgDataCharges) {
        this.pgDataCharges = pgDataCharges;
    }

    public boolean isDeactivateFlag() {
        return deactivateFlag;
    }

    public void setDeactivateFlag(boolean deactivateFlag) {
        this.deactivateFlag = deactivateFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData_volume() {
        return data_volume;
    }

    public void setData_volume(String data_volume) {
        this.data_volume = data_volume;
    }

    public String getPg_price() {
        if(TextUtils.isEmpty(pg_price))
        {
            pg_price="0";
        }
        return pg_price;
    }

    public void setPg_price(String pg_price) {
        this.pg_price = pg_price;
    }

    public String getTopup_name() {
        return topup_name;
    }

    public void setTopup_name(String topup_name) {
        this.topup_name = topup_name;
    }

    public String getTopup_id() {
        return topup_id;
    }

    public void setTopup_id(String topup_id) {
        this.topup_id = topup_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}