package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KnowMoreContext implements Serializable {

    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("planDescription")
    @Expose
    private String planDescription;
    @SerializedName("contentText")
    @Expose
    private List<KnowMoreItems> contentText;


    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public List<KnowMoreItems> getContentText() {
        return contentText;
    }

    public void setContentText(List<KnowMoreItems> contentText) {
        this.contentText = contentText;
    }
}


