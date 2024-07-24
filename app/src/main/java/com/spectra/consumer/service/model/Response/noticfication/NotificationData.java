package com.spectra.consumer.service.model.Response.noticfication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spectra.consumer.service.model.Response.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationData implements Serializable
{
    @SerializedName("_id")
    @Expose
    private DateData _id;

    @SerializedName("notification_info")
    @Expose
    private ArrayList<NotificationInfoData> notificationInfo;

    @SerializedName("created_at")
    @Expose
    private String created_at;


    public DateData getData() {
        return _id;
    }

    public void getData(DateData _id) {
        this._id = _id;
    }

    public ArrayList<NotificationInfoData>  getResponse() {
        return notificationInfo;
    }

    public void setResponse(ArrayList<NotificationInfoData> response) {
        this.notificationInfo = response;
    }

    public String getMessage() {
        return created_at;
    }

    public void setMessage(String message) {
        this.created_at = message;
    }

    public class DateData{
        @SerializedName("month")
        @Expose
        private int month;

        @SerializedName("day")
        @Expose
        private int day;

        @SerializedName("year")
        @Expose
        private int year;


        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getDate(){

            return ""+day+""+month+""+year;
        }
    }


}