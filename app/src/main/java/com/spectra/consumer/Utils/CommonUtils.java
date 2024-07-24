package com.spectra.consumer.Utils;

import android.content.Context;
import android.text.TextUtils;

import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;

public class CommonUtils {

    public static CurrentUserData saveUser(Context context, LoginMobileResponse data, boolean isNot){
        CurrentUserData currentUserData = new CurrentUserData();
        currentUserData.CANId = data.getCANId();
        currentUserData.AccountName = data.getAccountName();
        currentUserData.OutStandingAmount = data.getOutStandingAmount();
        currentUserData.SRCreatedOn = data.getSRCreatedOn();
        currentUserData.SRNumber = data.getSRNumber();
        currentUserData.SRCaseStatus = data.getSRCaseStatus();
        currentUserData.ETR = data.getETR();
        currentUserData.speed = data.getSpeed();
        currentUserData.DueDate = data.getDueDate();
        currentUserData.CancellationFlag = data.getCancellationFlag();
        currentUserData.actInProgressFlag = data.getActInProgressFlag();
        currentUserData.PreBarredFlag = data.getPreBarredFlag();
        currentUserData.OutStandingAmount = data.getOutStandingAmount();
        currentUserData.BillFrequency = data.getBillFrequency();
        currentUserData.planDataVolume = data.getPlanDataVolume();
        currentUserData.Segment = data.getSegment();
        currentUserData.SRETR=data.getSRETR();
        currentUserData.is_Login=true;
        currentUserData.DataConsumption=data.getDataConsumption();
        currentUserData.Email=data.getEmail();
        currentUserData.Product=data.getProduct();
        currentUserData.Number=data.getMobile();
        currentUserData.ivrNotification=data.getIvrNotification();
        currentUserData.FUPResetDate =data.getFUPResetDate();
        currentUserData.TOKEN =data.getToken();
        currentUserData.isNot =isNot;
        DroidPrefs.apply(context, CurrentuserKey, currentUserData);

        return currentUserData;
    }

   /* public static CurrentUserData saveUser(Context context, LoginMobileResponse data){
        CurrentUserData currentUserData = new CurrentUserData();
        currentUserData.CANId = data.getCANId();
        currentUserData.AccountName = data.getAccountName();
        currentUserData.OutStandingAmount = data.getOutStandingAmount();
        currentUserData.SRCreatedOn = data.getSRCreatedOn();
        currentUserData.SRNumber = data.getSRNumber();
        currentUserData.SRCaseStatus = data.getSRCaseStatus();
        currentUserData.ETR = data.getETR();
        currentUserData.speed = data.getSpeed();
        currentUserData.DueDate = data.getDueDate();
        currentUserData.CancellationFlag = data.getCancellationFlag();
        currentUserData.actInProgressFlag = data.getActInProgressFlag();
        currentUserData.PreBarredFlag = data.getPreBarredFlag();
        currentUserData.OutStandingAmount = data.getOutStandingAmount();
        currentUserData.BillFrequency = data.getBillFrequency();
        currentUserData.planDataVolume = data.getPlanDataVolume();
        currentUserData.Segment = data.getSegment();
        currentUserData.SRETR=data.getSRETR();
        currentUserData.is_Login=true;
        currentUserData.DataConsumption=data.getDataConsumption();
        currentUserData.Email=data.getEmail();
        currentUserData.Number=data.getMobile();
        currentUserData.ivrNotification=data.getIvrNotification();
        DroidPrefs.apply(context, CurrentuserKey, currentUserData);
        return currentUserData;
    }*/
}
