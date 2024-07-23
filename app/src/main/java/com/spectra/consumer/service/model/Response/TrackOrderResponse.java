package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrackOrderResponse implements Serializable
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private TrackOrder response;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("canID")
    @Expose
    private String canID;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TrackOrder  getResponse() {
        return response;
    }

    public void setResponse(TrackOrder response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class TrackOrder {

        @SerializedName("CANID")
        @Expose
        private String cANID;
        @SerializedName("BusinessSegment")
        @Expose
        private String businessSegment;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("StatusDates")
        @Expose
        private String statusDates;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("StateCode")
        @Expose
        private String stateCode;
        @SerializedName("StatusCode")
        @Expose
        private String statusCode;

        public String getCANID() {
            return cANID;
        }

        public void setCANID(String cANID) {
            this.cANID = cANID;
        }

        public String getBusinessSegment() {
            return businessSegment;
        }

        public void setBusinessSegment(String businessSegment) {
            this.businessSegment = businessSegment;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatusDates() {
            return statusDates;
        }

        public void setStatusDates(String statusDates) {
            this.statusDates = statusDates;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

    }
}