package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactListResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    public ResponseObject getResponse() {
        return response;
    }

    public void setResponse(ResponseObject response) {
        this.response = response;
    }

    @SerializedName("response")
    @Expose
    private ResponseObject response;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ResponseObject{

        @SerializedName("canID")
        @Expose
        private String canID;

        @SerializedName("results")
        @Expose
        private ArrayList<Contact> results;

        public String getCanID() {
            return canID;
        }

        public void setCanID(String canID) {
            this.canID = canID;
        }
        public ArrayList<Contact> getResults() {
            return results;
        }

        public void setResults(ArrayList<Contact> results) {
            this.results = results;
        }
    }

}