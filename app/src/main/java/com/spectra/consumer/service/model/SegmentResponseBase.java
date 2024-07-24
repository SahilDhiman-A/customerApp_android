package com.spectra.consumer.service.model;

import com.spectra.consumer.service.model.Response.FAQResponseBase;
import com.spectra.consumer.service.model.Response.Segment;

import java.util.HashMap;
import java.util.List;

public class SegmentResponseBase {
     List<Segment> data;
     String message;
     String statusCode;
     String additionalInfo;

    public List<Segment> getData() {
        return data;
    }

    public void setData(List<Segment> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}



//{
//        "data": [
//        {
//        "name": "Topup",
//        "is_deleted": false,
//        "is_active": true,
//        "_id": "60f6602a04dd893488aece5b",
//        "created_at": "2021-07-20T05:33:30.588Z",
//        "updated_at": "2021-07-20T05:33:30.589Z"
//        },
//        {
//        "name": "Plan",
//        "is_deleted": false,
//        "is_active": true,
//        "_id": "60f6616c04dd893488aece5c",
//        "created_at": "2021-07-20T05:38:52.837Z",
//        "updated_at": "2021-07-20T05:38:52.838Z"
//        },
//        {
//        "name": "Top up",
//        "is_deleted": false,
//        "is_active": true,
//        "_id": "60f6959a84dd72406984d367",
//        "created_at": "2021-07-20T09:21:30.962Z",
//        "updated_at": "2021-07-20T09:21:30.962Z"
//        }
//        ],
//        "message": "Segment List!!",
//        "statusCode": 200,
//        "additionalInfo": null
//        }