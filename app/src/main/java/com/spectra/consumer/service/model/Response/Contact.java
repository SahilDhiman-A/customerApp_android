package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contact implements Serializable {

    @SerializedName("contactId")
    @Expose
    private String contactId;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;


    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        if (!TextUtils.isEmpty(lastName)) {
            if (this.firstName.length() > 1) {
                this.firstName = this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1);
            }
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (!TextUtils.isEmpty(lastName)) {
            if (this.lastName.length() > 1) {
                this.lastName = this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1);
            }
        }
        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        if (!TextUtils.isEmpty(jobTitle)) {
            if (this.jobTitle.length() > 1) {
                this.jobTitle = this.jobTitle.substring(0, 1).toUpperCase() + this.jobTitle.substring(1);
            }
        }
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}