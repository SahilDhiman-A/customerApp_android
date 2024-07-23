package com.spectra.consumer.service.model.Response;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginMobileResponse implements Serializable
{


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("UserImage")
    @Expose
    private String UserImage;

    @SerializedName("CANId")
    @Expose
    private String cANId;



    @SerializedName("AccountName")
    @Expose
    private String accountName;
    @SerializedName("Segment")
    @Expose
    private String segment;
    @SerializedName("ProductSegment")
    @Expose
    private String productSegment;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("Speed")
    @Expose
    private String speed;
    @SerializedName("BillFrequency")
    @Expose
    private String billFrequency;
    @SerializedName("AccountStatus")
    @Expose
    private String accountStatus;
    @SerializedName("AccountActivationdate")
    @Expose
    private String accountActivationdate;
    @SerializedName("SRNumber")
    @Expose
    private String sRNumber;
    @SerializedName("SRcasecategory")
    @Expose
    private String sRcasecategory;
    @SerializedName("SRCaseStatus")
    @Expose
    private String sRCaseStatus;
    @SerializedName("SRcreationTypeID")
    @Expose
    private String sRcreationTypeID;
    @SerializedName("SRcreationType")
    @Expose
    private String sRcreationType;
    @SerializedName("SRcreationSubTypeID")
    @Expose
    private String sRcreationSubTypeID;
    @SerializedName("SRcreationSubType")
    @Expose
    private String sRcreationSubType;
    @SerializedName("SRcreationSubSubTypeID")
    @Expose
    private String sRcreationSubSubTypeID;
    @SerializedName("SRcreationSubSubType")
    @Expose
    private String sRcreationSubSubType;
    @SerializedName("SRCreatedOn")
    @Expose
    private String sRCreatedOn;
    @SerializedName("SRETR")
    @Expose
    private String sRETR;
    @SerializedName("SRExETR")
    @Expose
    private String sRExETR;
    @SerializedName("SRExETRFlag")
    @Expose
    private String sRExETRFlag;
    @SerializedName("OpenSRCount")
    @Expose
    private String openSRCount;
    @SerializedName("OpenSRFlag")
    @Expose
    private String openSRFlag;
    @SerializedName("MassOutage")
    @Expose
    private String massOutage;
    @SerializedName("MOpenSRCount")
    @Expose
    private String mOpenSRCount;
    @SerializedName("MultipleOpenSRFlag")
    @Expose
    private String multipleOpenSRFlag;
    @SerializedName("MSRNumber")
    @Expose
    private String mSRNumber;
    @SerializedName("Mcasecategory")
    @Expose
    private String mcasecategory;
    @SerializedName("MCaseStatus")
    @Expose
    private String mCaseStatus;
    @SerializedName("McreationTypeID")
    @Expose
    private String mcreationTypeID;
    @SerializedName("McreationType")
    @Expose
    private String mcreationType;
    @SerializedName("McreationSubTypeID")
    @Expose
    private String mcreationSubTypeID;
    @SerializedName("McreationSubType")
    @Expose
    private String mcreationSubType;
    @SerializedName("McreationSubSubTypeID")
    @Expose
    private String mcreationSubSubTypeID;
    @SerializedName("McreationSubSubType")
    @Expose
    private String mcreationSubSubType;
    @SerializedName("MSRCreatedOn")
    @Expose
    private String mSRCreatedOn;
    @SerializedName("ETR")
    @Expose
    private String eTR;
    @SerializedName("ExtendedETR")
    @Expose
    private String extendedETR;
    @SerializedName("ExETRFlag")
    @Expose
    private String exETRFlag;
    @SerializedName("ExETRCount")
    @Expose
    private String exETRCount;
    @SerializedName("CancellationFlag")
    @Expose
    private String cancellationFlag;
    @SerializedName("PreCanceledFlag")
    @Expose
    private String preCanceledFlag;
    @SerializedName("CancelledDate")
    @Expose
    private String cancelledDate;
    @SerializedName("PreBarredFlag")
    @Expose
    private String preBarredFlag;
    @SerializedName("BarringDate")
    @Expose
    private String barringDate;
    @SerializedName("BarringFlag")
    @Expose
    private String barringFlag;
    @SerializedName("InvoiceCreationDate")
    @Expose
    private String invoiceCreationDate;
    @SerializedName("InvoiceAmount")
    @Expose
    private String invoiceAmount;
    @SerializedName("OutstandingBalanceFlag")
    @Expose
    private String outstandingBalanceFlag;
    @SerializedName("OutStandingAmount")
    @Expose
    private String outStandingAmount;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("BillStartDate")
    @Expose
    private String billStartDate;
    @SerializedName("BillEndDate")
    @Expose
    private String billEndDate;
    @SerializedName("LastPaymentDate")
    @Expose
    private String lastPaymentDate;
    @SerializedName("LastPayment")
    @Expose
    private String lastPayment;
    @SerializedName("FUPFlag")
    @Expose
    private String fUPFlag;
    @SerializedName("FUPEnabled")
    @Expose
    private String fUPEnabled;
    @SerializedName("planDataVolume")
    @Expose
    private String planDataVolume;
    @SerializedName("DataConsumption")
    @Expose
    private String dataConsumption;
    @SerializedName("BabyCareFlag")
    @Expose
    private String babyCareFlag;
    @SerializedName("CallRestrictionFlag")
    @Expose
    private String callRestrictionFlag;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("actInProgressFlag")
    @Expose
    private String actInProgressFlag;
    @SerializedName("ivrNotification")
    @Expose
    private List<IvrNotificationResponse> ivrNotification = null;
    private final static long serialVersionUID = -7178158992312808228L;

    public String getCANId() {
        return cANId;
    }

    public void setCANId(String cANId) {
        this.cANId = cANId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getProductSegment() {
        return productSegment;
    }

    public void setProductSegment(String productSegment) {
        this.productSegment = productSegment;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getBillFrequency() {
        return billFrequency;
    }

    public void setBillFrequency(String billFrequency) {
        this.billFrequency = billFrequency;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountActivationdate() {
        return accountActivationdate;
    }

    public void setAccountActivationdate(String accountActivationdate) {
        this.accountActivationdate = accountActivationdate;
    }

    public String getSRNumber() {
        return sRNumber;
    }

    public void setSRNumber(String sRNumber) {
        this.sRNumber = sRNumber;
    }

    public String getSRcasecategory() {
        return sRcasecategory;
    }

    public void setSRcasecategory(String sRcasecategory) {
        this.sRcasecategory = sRcasecategory;
    }

    public String getSRCaseStatus() {
        return sRCaseStatus;
    }

    public void setSRCaseStatus(String sRCaseStatus) {
        this.sRCaseStatus = sRCaseStatus;
    }

    public String getSRcreationTypeID() {
        return sRcreationTypeID;
    }

    public void setSRcreationTypeID(String sRcreationTypeID) {
        this.sRcreationTypeID = sRcreationTypeID;
    }

    public String getSRcreationType() {
        return sRcreationType;
    }

    public void setSRcreationType(String sRcreationType) {
        this.sRcreationType = sRcreationType;
    }

    public String getSRcreationSubTypeID() {
        return sRcreationSubTypeID;
    }

    public void setSRcreationSubTypeID(String sRcreationSubTypeID) {
        this.sRcreationSubTypeID = sRcreationSubTypeID;
    }

    public String getSRcreationSubType() {
        return sRcreationSubType;
    }

    public void setSRcreationSubType(String sRcreationSubType) {
        this.sRcreationSubType = sRcreationSubType;
    }

    public String getSRcreationSubSubTypeID() {
        return sRcreationSubSubTypeID;
    }

    public void setSRcreationSubSubTypeID(String sRcreationSubSubTypeID) {
        this.sRcreationSubSubTypeID = sRcreationSubSubTypeID;
    }

    public String getSRcreationSubSubType() {
        return sRcreationSubSubType;
    }

    public void setSRcreationSubSubType(String sRcreationSubSubType) {
        this.sRcreationSubSubType = sRcreationSubSubType;
    }

    public String getSRCreatedOn() {
        return sRCreatedOn;
    }

    public void setSRCreatedOn(String sRCreatedOn) {
        this.sRCreatedOn = sRCreatedOn;
    }

    public String getSRETR() {
        return sRETR;
    }

    public void setSRETR(String sRETR) {
        this.sRETR = sRETR;
    }

    public String getSRExETR() {
        return sRExETR;
    }

    public void setSRExETR(String sRExETR) {
        this.sRExETR = sRExETR;
    }

    public String getSRExETRFlag() {
        return sRExETRFlag;
    }

    public void setSRExETRFlag(String sRExETRFlag) {
        this.sRExETRFlag = sRExETRFlag;
    }

    public String getOpenSRCount() {
        return openSRCount;
    }

    public void setOpenSRCount(String openSRCount) {
        this.openSRCount = openSRCount;
    }

    public String getOpenSRFlag() {
        return openSRFlag;
    }

    public void setOpenSRFlag(String openSRFlag) {
        this.openSRFlag = openSRFlag;
    }

    public String getMassOutage() {
        return massOutage;
    }

    public void setMassOutage(String massOutage) {
        this.massOutage = massOutage;
    }

    public String getMOpenSRCount() {
        return mOpenSRCount;
    }

    public void setMOpenSRCount(String mOpenSRCount) {
        this.mOpenSRCount = mOpenSRCount;
    }

    public String getMultipleOpenSRFlag() {
        return multipleOpenSRFlag;
    }

    public void setMultipleOpenSRFlag(String multipleOpenSRFlag) {
        this.multipleOpenSRFlag = multipleOpenSRFlag;
    }

    public String getMSRNumber() {
        return mSRNumber;
    }

    public void setMSRNumber(String mSRNumber) {
        this.mSRNumber = mSRNumber;
    }

    public String getMcasecategory() {
        return mcasecategory;
    }

    public void setMcasecategory(String mcasecategory) {
        this.mcasecategory = mcasecategory;
    }

    public String getMCaseStatus() {
        return mCaseStatus;
    }

    public void setMCaseStatus(String mCaseStatus) {
        this.mCaseStatus = mCaseStatus;
    }

    public String getMcreationTypeID() {
        return mcreationTypeID;
    }

    public void setMcreationTypeID(String mcreationTypeID) {
        this.mcreationTypeID = mcreationTypeID;
    }

    public String getMcreationType() {
        return mcreationType;
    }

    public void setMcreationType(String mcreationType) {
        this.mcreationType = mcreationType;
    }

    public String getMcreationSubTypeID() {
        return mcreationSubTypeID;
    }

    public void setMcreationSubTypeID(String mcreationSubTypeID) {
        this.mcreationSubTypeID = mcreationSubTypeID;
    }

    public String getMcreationSubType() {
        return mcreationSubType;
    }

    public void setMcreationSubType(String mcreationSubType) {
        this.mcreationSubType = mcreationSubType;
    }

    public String getMcreationSubSubTypeID() {
        return mcreationSubSubTypeID;
    }

    public void setMcreationSubSubTypeID(String mcreationSubSubTypeID) {
        this.mcreationSubSubTypeID = mcreationSubSubTypeID;
    }

    public String getMcreationSubSubType() {
        return mcreationSubSubType;
    }

    public void setMcreationSubSubType(String mcreationSubSubType) {
        this.mcreationSubSubType = mcreationSubSubType;
    }

    public String getMSRCreatedOn() {
        return mSRCreatedOn;
    }

    public void setMSRCreatedOn(String mSRCreatedOn) {
        this.mSRCreatedOn = mSRCreatedOn;
    }

    public String getETR() {
        return eTR;
    }

    public void setETR(String eTR) {
        this.eTR = eTR;
    }

    public String getExtendedETR() {
        return extendedETR;
    }

    public void setExtendedETR(String extendedETR) {
        this.extendedETR = extendedETR;
    }

    public String getExETRFlag() {
        return exETRFlag;
    }

    public void setExETRFlag(String exETRFlag) {
        this.exETRFlag = exETRFlag;
    }

    public String getExETRCount() {
        return exETRCount;
    }

    public void setExETRCount(String exETRCount) {
        this.exETRCount = exETRCount;
    }

    public String getCancellationFlag() {
        if (TextUtils.isEmpty(cancellationFlag)){
            return "false";
        }
        return cancellationFlag;
    }

    public void setCancellationFlag(String cancellationFlag) {
        this.cancellationFlag = cancellationFlag;
    }

    public String getPreCanceledFlag() {
        return preCanceledFlag;
    }

    public void setPreCanceledFlag(String preCanceledFlag) {
        this.preCanceledFlag = preCanceledFlag;
    }

    public String getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(String cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getPreBarredFlag() {
        return preBarredFlag;
    }

    public void setPreBarredFlag(String preBarredFlag) {
        this.preBarredFlag = preBarredFlag;
    }

    public String getBarringDate() {
        return barringDate;
    }

    public void setBarringDate(String barringDate) {
        this.barringDate = barringDate;
    }

    public String getBarringFlag() {
        return barringFlag;
    }

    public void setBarringFlag(String barringFlag) {
        this.barringFlag = barringFlag;
    }

    public String getInvoiceCreationDate() {
        return invoiceCreationDate;
    }

    public void setInvoiceCreationDate(String invoiceCreationDate) {
        this.invoiceCreationDate = invoiceCreationDate;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getOutstandingBalanceFlag() {
        return outstandingBalanceFlag;
    }

    public void setOutstandingBalanceFlag(String outstandingBalanceFlag) {
        this.outstandingBalanceFlag = outstandingBalanceFlag;
    }

    public String getOutStandingAmount() {
        return outStandingAmount;
    }

    public void setOutStandingAmount(String outStandingAmount) {
        this.outStandingAmount = outStandingAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillStartDate() {
        return billStartDate;
    }

    public void setBillStartDate(String billStartDate) {
        this.billStartDate = billStartDate;
    }

    public String getBillEndDate() {
        return billEndDate;
    }

    public void setBillEndDate(String billEndDate) {
        this.billEndDate = billEndDate;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(String lastPayment) {
        this.lastPayment = lastPayment;
    }

    public String getFUPFlag() {
        return fUPFlag;
    }

    public void setFUPFlag(String fUPFlag) {
        this.fUPFlag = fUPFlag;
    }

    public String getFUPEnabled() {
        return fUPEnabled;
    }

    public void setFUPEnabled(String fUPEnabled) {
        this.fUPEnabled = fUPEnabled;
    }

    public String getPlanDataVolume() {
        return planDataVolume;
    }

    public void setPlanDataVolume(String planDataVolume) {
        this.planDataVolume = planDataVolume;
    }

    public String getDataConsumption() {
        return dataConsumption;
    }

    public void setDataConsumption(String dataConsumption) {
        this.dataConsumption = dataConsumption;
    }

    public String getBabyCareFlag() {
        return babyCareFlag;
    }

    public void setBabyCareFlag(String babyCareFlag) {
        this.babyCareFlag = babyCareFlag;
    }

    public String getCallRestrictionFlag() {
        return callRestrictionFlag;
    }

    public void setCallRestrictionFlag(String callRestrictionFlag) {
        this.callRestrictionFlag = callRestrictionFlag;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getActInProgressFlag() {
        if (TextUtils.isEmpty(actInProgressFlag)){
            return "";
        }
        return actInProgressFlag;
    }

    public void setActInProgressFlag(String actInProgressFlag) {
        this.actInProgressFlag = actInProgressFlag;
    }

    public List<IvrNotificationResponse> getIvrNotification() {
        return ivrNotification;
    }

    public void setIvrNotification(List<IvrNotificationResponse> ivrNotification) {
        this.ivrNotification = ivrNotification;
    }
    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }
}