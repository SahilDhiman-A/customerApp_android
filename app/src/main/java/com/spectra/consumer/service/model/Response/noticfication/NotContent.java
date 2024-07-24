package com.spectra.consumer.service.model.Response.noticfication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotContent{
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("body")
    @Expose
    private String body;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}