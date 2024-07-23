package com.spectra.consumer.service.model.Request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class PostFUPFlagRequest implements Serializable {
    @SerializedName("getTopup")
    @Expose
    private String getTopup;

    public String getGetTopup() {
        return getTopup;
    }

    public void setGetTopup(String getTopup) {
        this.getTopup = getTopup;
    }
}
