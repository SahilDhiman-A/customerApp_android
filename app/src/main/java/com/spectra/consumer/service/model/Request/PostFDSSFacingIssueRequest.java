package com.spectra.consumer.service.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostFDSSFacingIssueRequest implements Serializable {
    @SerializedName("facingIssue")
    @Expose
    private String facingIssue;
    public String getFacingIssue() {
        return facingIssue;
    }
    public void setFacingIssue(String facingIssue) {
        this.facingIssue = facingIssue;
    }
}
