package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SendOtpLinkAccountResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    public OTPResponse getResponse() {
        return response;
    }

    public void setResponse(OTPResponse response) {
        this.response = response;
    }

    @SerializedName("response")
    @Expose
    private OTPResponse response;

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

    public class OTPResponse {
        
        @SerializedName("OTP")
        @Expose
        private String OTP;

        @SerializedName("mobileNo")
        @Expose
        private String mobileNo;

        public String getOTP() {
            return OTP;
        }

        public void setOTP(String OTP) {
            this.OTP = OTP;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }
    }


}