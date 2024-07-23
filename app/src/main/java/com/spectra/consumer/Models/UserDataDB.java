package com.spectra.consumer.Models;

import com.spectra.consumer.service.model.Response.LoginMobileResponse;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UserDataDB implements Serializable {
    public LinkedHashMap<String, LoginMobileResponse> getResponseHashMap() {
        return responseHashMap;
    }

    public void setResponseHashMap(LinkedHashMap<String, LoginMobileResponse> responseHashMap) {
        this.responseHashMap = responseHashMap;
    }

   private LinkedHashMap<String, LoginMobileResponse> responseHashMap=new LinkedHashMap<>();
}
