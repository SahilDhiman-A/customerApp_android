package com.spectra.consumer.Models;

import com.spectra.consumer.service.model.Response.LoginMobileResponse;

import java.io.Serializable;
import java.util.HashMap;

public class UserImage implements Serializable {
    public HashMap<String, String> getResponseHashMap() {
        return responseHashMap;
    }

    public void setResponseHashMap(HashMap<String, String> responseHashMap) {
        this.responseHashMap = responseHashMap;
    }

    HashMap<String, String> responseHashMap=new HashMap<>();
}
