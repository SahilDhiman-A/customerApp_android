package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import okhttp3.Response;

public class GetDataChrgesResponse implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("response")
    @Expose
    private ResponseData response = null;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseData getResponse() {
        return response;
    }

    public void setResponse(ResponseData response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ResponseData {
        @SerializedName("Balance amount to be reversed")
        @Expose
        private int reversedBalance;
        @SerializedName("Charges for Duration consumed")
        @Expose
        private String Duration ;
        @SerializedName("Current plan Activation date")
        @Expose
        private String date;

        @SerializedName("Difference Amount Payable")
        @Expose
        private int DifferenceAmountPayable;

        @SerializedName("New Plan Charges")
        @Expose
        private int NewPlanCharges;

        @SerializedName("taxes")
        @Expose
        private int taxes;

        @SerializedName("pgDataCharges")
        @Expose
        private int pgDataCharges;
        @SerializedName("remarks")
        @Expose
        private String remarks;


        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getReversedBalance() {
            return reversedBalance;
        }

        public void setReversedBalance(int reversedBalance) {
            this.reversedBalance = reversedBalance;
        }

        public String getDuration() {
            return Duration;
        }

        public void setDuration(String duration) {
            Duration = duration;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getDifferenceAmountPayable() {
            return DifferenceAmountPayable;
        }

        public void setDifferenceAmountPayable(int differenceAmountPayable) {
            DifferenceAmountPayable = differenceAmountPayable;
        }

        public int getNewPlanCharges() {
            return NewPlanCharges;
        }

        public void setNewPlanCharges(int newPlanCharges) {
            NewPlanCharges = newPlanCharges;
        }

        public int getTaxes() {
            return taxes;
        }

        public void setTaxes(int taxes) {
            this.taxes = taxes;
        }

        public int getPgDataCharges() {
            return pgDataCharges;
        }

        public void setPgDataCharges(int pgDataCharges) {
            this.pgDataCharges = pgDataCharges;
        }
    }

}