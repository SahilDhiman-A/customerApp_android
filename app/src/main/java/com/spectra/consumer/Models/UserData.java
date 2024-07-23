package com.spectra.consumer.Models;


import com.spectra.consumer.service.model.Response.IvrNotificationResponse;

import java.io.Serializable;
import java.util.List;

import activeandroid.Model;
import activeandroid.annotation.Column;
import activeandroid.annotation.Table;
import activeandroid.query.Delete;
import activeandroid.query.Select;


@Table(name = "UserData")
public class UserData extends Model implements Serializable {

    public String getCANId() {
        return CANId;
    }

    public void setCANId(String CANId) {
        this.CANId = CANId;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getSegment() {
        return Segment;
    }

    public void setSegment(String segment) {
        Segment = segment;
    }

    public String getProductSegment() {
        return ProductSegment;
    }

    public void setProductSegment(String productSegment) {
        ProductSegment = productSegment;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getBillFrequency() {
        return BillFrequency;
    }

    public void setBillFrequency(String billFrequency) {
        BillFrequency = billFrequency;
    }

    public String getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        AccountStatus = accountStatus;
    }

    public String getAccountActivationdate() {
        return AccountActivationdate;
    }

    public void setAccountActivationdate(String accountActivationdate) {
        AccountActivationdate = accountActivationdate;
    }

    public String getSRNumber() {
        return SRNumber;
    }

    public void setSRNumber(String SRNumber) {
        this.SRNumber = SRNumber;
    }

    public String getSRcasecategory() {
        return SRcasecategory;
    }

    public void setSRcasecategory(String SRcasecategory) {
        this.SRcasecategory = SRcasecategory;
    }

    public String getSRCaseStatus() {
        return SRCaseStatus;
    }

    public void setSRCaseStatus(String SRCaseStatus) {
        this.SRCaseStatus = SRCaseStatus;
    }

    public String getSRcreationTypeID() {
        return SRcreationTypeID;
    }

    public void setSRcreationTypeID(String SRcreationTypeID) {
        this.SRcreationTypeID = SRcreationTypeID;
    }

    public String getSRcreationType() {
        return SRcreationType;
    }

    public void setSRcreationType(String SRcreationType) {
        this.SRcreationType = SRcreationType;
    }

    public String getSRcreationSubTypeID() {
        return SRcreationSubTypeID;
    }

    public void setSRcreationSubTypeID(String SRcreationSubTypeID) {
        this.SRcreationSubTypeID = SRcreationSubTypeID;
    }

    public String getSRcreationSubType() {
        return SRcreationSubType;
    }

    public void setSRcreationSubType(String SRcreationSubType) {
        this.SRcreationSubType = SRcreationSubType;
    }

    public String getSRcreationSubSubTypeID() {
        return SRcreationSubSubTypeID;
    }

    public void setSRcreationSubSubTypeID(String SRcreationSubSubTypeID) {
        this.SRcreationSubSubTypeID = SRcreationSubSubTypeID;
    }

    public String getSRcreationSubSubType() {
        return SRcreationSubSubType;
    }

    public void setSRcreationSubSubType(String SRcreationSubSubType) {
        this.SRcreationSubSubType = SRcreationSubSubType;
    }

    public String getSRCreatedOn() {
        return SRCreatedOn;
    }

    public void setSRCreatedOn(String SRCreatedOn) {
        this.SRCreatedOn = SRCreatedOn;
    }

    public String getSRETR() {
        return SRETR;
    }

    public void setSRETR(String SRETR) {
        this.SRETR = SRETR;
    }

    public String getSRExETR() {
        return SRExETR;
    }

    public void setSRExETR(String SRExETR) {
        this.SRExETR = SRExETR;
    }

    public String getSRExETRFlag() {
        return SRExETRFlag;
    }

    public void setSRExETRFlag(String SRExETRFlag) {
        this.SRExETRFlag = SRExETRFlag;
    }

    public String getOpenSRCount() {
        return OpenSRCount;
    }

    public void setOpenSRCount(String openSRCount) {
        OpenSRCount = openSRCount;
    }

    public String getOpenSRFlag() {
        return OpenSRFlag;
    }

    public void setOpenSRFlag(String openSRFlag) {
        OpenSRFlag = openSRFlag;
    }

    public String getMassOutage() {
        return MassOutage;
    }

    public void setMassOutage(String massOutage) {
        MassOutage = massOutage;
    }

    public String getMOpenSRCount() {
        return MOpenSRCount;
    }

    public void setMOpenSRCount(String MOpenSRCount) {
        this.MOpenSRCount = MOpenSRCount;
    }

    public String getMultipleOpenSRFlag() {
        return MultipleOpenSRFlag;
    }

    public void setMultipleOpenSRFlag(String multipleOpenSRFlag) {
        MultipleOpenSRFlag = multipleOpenSRFlag;
    }

    public String getMSRNumber() {
        return MSRNumber;
    }

    public void setMSRNumber(String MSRNumber) {
        this.MSRNumber = MSRNumber;
    }

    public String getMcasecategory() {
        return Mcasecategory;
    }

    public void setMcasecategory(String mcasecategory) {
        Mcasecategory = mcasecategory;
    }

    public String getMCaseStatus() {
        return MCaseStatus;
    }

    public void setMCaseStatus(String MCaseStatus) {
        this.MCaseStatus = MCaseStatus;
    }

    public String getMcreationTypeID() {
        return McreationTypeID;
    }

    public void setMcreationTypeID(String mcreationTypeID) {
        McreationTypeID = mcreationTypeID;
    }

    public String getMcreationType() {
        return McreationType;
    }

    public void setMcreationType(String mcreationType) {
        McreationType = mcreationType;
    }

    public String getMcreationSubTypeID() {
        return McreationSubTypeID;
    }

    public void setMcreationSubTypeID(String mcreationSubTypeID) {
        McreationSubTypeID = mcreationSubTypeID;
    }

    public String getMcreationSubType() {
        return McreationSubType;
    }

    public void setMcreationSubType(String mcreationSubType) {
        McreationSubType = mcreationSubType;
    }

    public String getMcreationSubSubTypeID() {
        return McreationSubSubTypeID;
    }

    public void setMcreationSubSubTypeID(String mcreationSubSubTypeID) {
        McreationSubSubTypeID = mcreationSubSubTypeID;
    }

    public String getMcreationSubSubType() {
        return McreationSubSubType;
    }

    public void setMcreationSubSubType(String mcreationSubSubType) {
        McreationSubSubType = mcreationSubSubType;
    }

    public String getMSRCreatedOn() {
        return MSRCreatedOn;
    }

    public void setMSRCreatedOn(String MSRCreatedOn) {
        this.MSRCreatedOn = MSRCreatedOn;
    }

    public String getETR() {
        return ETR;
    }

    public void setETR(String ETR) {
        this.ETR = ETR;
    }

    public String getExtendedETR() {
        return ExtendedETR;
    }

    public void setExtendedETR(String extendedETR) {
        ExtendedETR = extendedETR;
    }

    public String getExETRFlag() {
        return ExETRFlag;
    }

    public void setExETRFlag(String exETRFlag) {
        ExETRFlag = exETRFlag;
    }

    public String getExETRCount() {
        return ExETRCount;
    }

    public void setExETRCount(String exETRCount) {
        ExETRCount = exETRCount;
    }

    public String getCancellationFlag() {
        return CancellationFlag;
    }

    public void setCancellationFlag(String cancellationFlag) {
        CancellationFlag = cancellationFlag;
    }

    public String getPreCanceledFlag() {
        return PreCanceledFlag;
    }

    public void setPreCanceledFlag(String preCanceledFlag) {
        PreCanceledFlag = preCanceledFlag;
    }

    public String getCancelledDate() {
        return CancelledDate;
    }

    public void setCancelledDate(String cancelledDate) {
        CancelledDate = cancelledDate;
    }

    public String getPreBarredFlag() {
        return PreBarredFlag;
    }

    public void setPreBarredFlag(String preBarredFlag) {
        PreBarredFlag = preBarredFlag;
    }

    public String getBarringDate() {
        return BarringDate;
    }

    public void setBarringDate(String barringDate) {
        BarringDate = barringDate;
    }

    public String getBarringFlag() {
        return BarringFlag;
    }

    public void setBarringFlag(String barringFlag) {
        BarringFlag = barringFlag;
    }

    public String getInvoiceCreationDate() {
        return InvoiceCreationDate;
    }

    public void setInvoiceCreationDate(String invoiceCreationDate) {
        InvoiceCreationDate = invoiceCreationDate;
    }

    public String getInvoiceAmount() {
        return InvoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        InvoiceAmount = invoiceAmount;
    }

    public String getOutstandingBalanceFlag() {
        return OutstandingBalanceFlag;
    }

    public void setOutstandingBalanceFlag(String outstandingBalanceFlag) {
        OutstandingBalanceFlag = outstandingBalanceFlag;
    }

    public String getOutStandingAmount() {
        return OutStandingAmount;
    }

    public void setOutStandingAmount(String outStandingAmount) {
        OutStandingAmount = outStandingAmount;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getBillStartDate() {
        return BillStartDate;
    }

    public void setBillStartDate(String billStartDate) {
        BillStartDate = billStartDate;
    }

    public String getBillEndDate() {
        return BillEndDate;
    }

    public void setBillEndDate(String billEndDate) {
        BillEndDate = billEndDate;
    }

    public String getLastPaymentDate() {
        return LastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        LastPaymentDate = lastPaymentDate;
    }

    public String getLastPayment() {
        return LastPayment;
    }

    public void setLastPayment(String lastPayment) {
        LastPayment = lastPayment;
    }

    public String getFUPFlag() {
        return FUPFlag;
    }

    public void setFUPFlag(String FUPFlag) {
        this.FUPFlag = FUPFlag;
    }

    public String getDataConsumption() {
        return DataConsumption;
    }

    public void setDataConsumption(String dataConsumption) {
        DataConsumption = dataConsumption;
    }

    public String getBabyCareFlag() {
        return BabyCareFlag;
    }

    public void setBabyCareFlag(String babyCareFlag) {
        BabyCareFlag = babyCareFlag;
    }

    public String getCallRestrictionFlag() {
        return CallRestrictionFlag;
    }

    public void setCallRestrictionFlag(String callRestrictionFlag) {
        CallRestrictionFlag = callRestrictionFlag;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPlanDataVolume() {
        return planDataVolume;
    }

    public void setPlanDataVolume(String planDataVolume) {
        this.planDataVolume = planDataVolume;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String Speed) {
        this.Speed = Speed;
    }

    public String getActInProgressFlag() {
        return actInProgressFlag;
    }

    public void setActInProgressFlag(String actInProgressFlag) {
        this.actInProgressFlag = actInProgressFlag;
    }

    @Column(name = "CANId" ,unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
public String CANId;
    @Column(name = "AccountName")
public String AccountName;
    @Column(name = "Segment")
public  String Segment;
    @Column(name = "ProductSegment")
public String ProductSegment;
    @Column(name = "Product")
public String Product;
    @Column(name = "BillFrequency")
public String BillFrequency;
    @Column(name = "AccountStatus")
public String AccountStatus;
    @Column(name = "AccountActivationdate")
    public String AccountActivationdate;
    @Column(name = "SRNumber")
public String SRNumber;
    @Column(name = "SRcasecategory")
public String SRcasecategory;
    @Column(name = "SRCaseStatus")
public String SRCaseStatus;
    @Column(name = "SRcreationTypeID")
public String SRcreationTypeID;
    @Column(name = "SRcreationType")
public String SRcreationType;
    @Column(name = "SRcreationSubTypeID")
public String SRcreationSubTypeID;
    @Column(name = "SRcreationSubType")
public String SRcreationSubType;
    @Column(name = "SRcreationSubSubTypeID")
public String SRcreationSubSubTypeID;
    @Column(name = "SRcreationSubSubType")
public String SRcreationSubSubType;
    @Column(name = "SRCreatedOn")
public String SRCreatedOn;
    @Column(name = "SRETR")
public String SRETR;
    @Column(name = "SRExETR")
public String SRExETR;
    @Column(name = "SRExETRFlag")
public String SRExETRFlag;
    @Column(name = "OpenSRCount")
public String OpenSRCount;
    @Column(name = "OpenSRFlag")
public String OpenSRFlag;
    @Column(name = "MassOutage")
public String MassOutage;
    @Column(name = "MOpenSRCount")
public String MOpenSRCount;
    @Column(name = "MultipleOpenSRFlag")
public  String MultipleOpenSRFlag;
    @Column(name = "MSRNumber")
public String MSRNumber;
    @Column(name = "Mcasecategory")
public String Mcasecategory;
    @Column(name = "MCaseStatus")
    public String MCaseStatus;
    @Column(name = "McreationTypeID")
    public String McreationTypeID;
    @Column(name = "McreationType")
    public String McreationType;
    @Column(name = "McreationSubTypeID")
    public String McreationSubTypeID;
    @Column(name = "McreationSubType")
    public String McreationSubType;
    @Column(name = "McreationSubSubTypeID")
    public String McreationSubSubTypeID;
    @Column(name = "McreationSubSubType")
    public String McreationSubSubType;
    @Column(name = "MSRCreatedOn")
    public String MSRCreatedOn;
    @Column(name = "ETR")
    public String ETR;
    @Column(name = "ExtendedETR")
    public String ExtendedETR;
    @Column(name = "ExETRFlag")
    public String ExETRFlag;
    @Column(name = "ExETRCount")
    public String ExETRCount;
    @Column(name = "CancellationFlag")
    public String CancellationFlag;
    @Column(name = "PreCanceledFlag")
    public String PreCanceledFlag;
    @Column(name = "CancelledDate")
    public String CancelledDate;
    @Column(name = "PreBarredFlag")
    public String PreBarredFlag;
    @Column(name = "BarringDate")
    public String BarringDate;
    @Column(name = "BarringFlag")
    public String BarringFlag;
    @Column(name = "InvoiceCreationDate")
    public String InvoiceCreationDate;
    @Column(name = "InvoiceAmount")
    public String InvoiceAmount;
    @Column(name = "OutstandingBalanceFlag")
    public String OutstandingBalanceFlag;
    @Column(name = "OutStandingAmount")
    public String OutStandingAmount;
    @Column(name = "DueDate")
    public String DueDate;
    @Column(name = "BillStartDate")
    public String BillStartDate;
    @Column(name = "BillEndDate")
    public String BillEndDate;
    @Column(name = "LastPaymentDate")
    public String LastPaymentDate;
    @Column(name = "LastPayment")
    public String LastPayment;
    @Column(name = "FUPFlag")
    public String FUPFlag;
    @Column(name = "DataConsumption")
    public String DataConsumption;
    @Column(name = "BabyCareFlag")
    public String BabyCareFlag;
    @Column(name = "CallRestrictionFlag")
    public String CallRestrictionFlag;
    @Column(name = "guid")
    public String guid;
    @Column(name = "planDataVolume")
    public String planDataVolume;
    @Column(name = "Speed")
    public String Speed;
    @Column(name = "actInProgressFlag")
    public String actInProgressFlag;
    @Column(name ="ivrNotification")
    public List<IvrNotificationResponse> ivrNotification = null;

    public List<IvrNotificationResponse> getIvrNotification() {
        return ivrNotification;
    }

    public void setIvrNotification(List<IvrNotificationResponse> ivrNotification) {
        this.ivrNotification = ivrNotification;
    }

    public UserData(){
        super();
    }
    public static List<UserData> getAll(){
        return new Select().from(UserData.class).execute();
    }
    public static UserData get(String CANId){
        return new Select().from(UserData.class).where("CANId = ?",CANId).executeSingle();
    }
    public static void DeleteUsersInfo(){
        new Delete().from(UserData.class).execute();
    }
}
