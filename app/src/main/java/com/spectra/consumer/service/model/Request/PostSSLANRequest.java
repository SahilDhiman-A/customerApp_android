package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostSSLANRequest implements Serializable {
    @SerializedName("speedOnLan")
    @Expose
    private String speedOnLan;
    public String getSpeedOnLan() {
        return speedOnLan;
    }

    @SerializedName("comment")
    @Expose
    private String comment;
    public String getComment() {
        return comment;
    }

    public void setSpeedOnLan(String speedOnLan, String comment) {
        this.speedOnLan = speedOnLan;
        this.comment = comment;
    }
}
