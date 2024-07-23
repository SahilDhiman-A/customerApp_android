package com.spectra.consumer.Models;

import com.spectra.consumer.service.model.Response.IvrNotificationResponse;

import java.io.Serializable;
import java.util.List;

public class CurrentUserData implements Serializable {
    public String CANId;
    public String AccountName;
    public String Segment;
    public String Product;
    public String BillFrequency;
    public String SRNumber;
    public String SRCaseStatus;
    public String SRCreatedOn;
    public String ETR;
    public String CancellationFlag;
    public String PreBarredFlag;
    public String OutStandingAmount;
    public String DueDate;
    public String planDataVolume;
    public boolean is_Login;
    public String speed;
    public String actInProgressFlag;
    public String SRETR;
    public String DataConsumption;
    public String Email;
    public String Number;
    public  List<IvrNotificationResponse> ivrNotification ;
}
