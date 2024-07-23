package com.spectra.consumer.service.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

 public  class MobileUpdateOtpResponse implements Serializable
    {
        @SerializedName("mobileNo")
        @Expose
        private String mobileNo;
        @SerializedName("OTP")
        @Expose
        private Integer OTP;
        private final static long serialVersionUID = -8294234958897338246L;

        public String getMobileNo() {
        return mobileNo;
    }

        public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

        public Integer getOTP() {
        return OTP;
    }

        public void setOTP(Integer oTP) {
        this.OTP = oTP;
    }

    }
