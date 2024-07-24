package com.spectra.consumer.service.model.Response.noticfication;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationInfoData implements Serializable {
    @SerializedName("notification")
    @Expose
    private NotContent NotContent;

    public NotData getNotData() {
        return notData;
    }

    public void setNotData(NotData notData) {
        this.notData = notData;
    }

    @SerializedName("data")
    @Expose
    private NotData notData;


    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("can_id")
    @Expose
    private String can_id;
    @SerializedName("is_archieved")
    @Expose
    private Boolean isArchived;

    @SerializedName("is_read")
    @Expose
    private Boolean is_read;



    public String getPdf_url() {
        return pdf_url;
    }

    public void setPdf_url(String pdf_url) {
        this.pdf_url = pdf_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    @SerializedName("pdf_url")
    @Expose
    private String pdf_url;

    @SerializedName("image_url")
    @Expose
    private String image_url;

    @SerializedName("dateString")
    @Expose
    private String dateString;

    @SerializedName("date")
    @Expose
    private String date;
    private boolean isSelect;

    public Boolean getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Boolean isSelect) {
        this.isSelect = isSelect;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void getDateString(String dateString) {
        this.dateString = dateString;
    }


    public String getCan_id() {
        return can_id;
    }

    public void setCan_id(String can_id) {
        this.can_id = can_id;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    public NotContent getContent() {
        return NotContent;
    }

    public void setContent(NotContent content) {
        this.NotContent = content;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getType() {
        return getNotData().getOrderInfo().getType();
    }

    public String getDetail() {
        return getNotData().getOrderInfo().getDetailedDescription();
    }


    public class NotData {

        @SerializedName("order_info")
        @Expose
        private OrderInfo orderInfo;
        @SerializedName("video_url")
        @Expose
        private String video_url;

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public String getVideoUrl() {
            return video_url;
        }

        public void setVideoUrl(String video_url) {
            this.video_url = video_url;
        }

        public class OrderInfo {
            @SerializedName("detailed_description")
            @Expose
            private String detailedDescription;

            @SerializedName("type")
            @Expose
            private String type;

            public String getDetailedDescription() {
                return detailedDescription;
            }

            public void setDetailedDescription(String orderInfo) {
                detailedDescription = detailedDescription;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

    }


}