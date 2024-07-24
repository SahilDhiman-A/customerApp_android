package com.spectra.consumer.service.repository;

import org.jetbrains.annotations.NotNull;

public class ApiConstant {
    public static final String GET_ACCOUNT_BY_MOBILE="getAccountByMobile";
    public static final String GET_ACCOUNT_BY_PASSWORD="getAccountByPassword";
    public static final String GET_ACCOUNT_DATA="getAccountData";
    public static final String SEND_OTP="sendotp";
    public static final String RESEND_OTP="resendotp";
    public static final String CREATE_SR="createSR";
    public static final String GET_INVOICE_DETAIL="getInvoiceContent";

    public static final String GET_MRTG_BY_CANID="getMRTGbycanid";
    public static final String GET_SESSION_HISTORY="getSessionhistory";
    public static final String FORGOT_PASSWORD="forgotpassword";
    public static final String GET_INVOICE_CONTENT="getInvoiceContent";
    public static final String GET_PROFILE="getprofile";
    public static final String GET_RATEPLAN_BY_CANID="getrateplanByCanID";
    public static final String GET_CASE_TYPE="getcasetype";
    public static final String CHANGE_PLAN="changeplan";
    public static final String GET_OFFERS="getOffers";
    public static final String UPDATE_EMAIL="updateEmailSendOTP";
    public static final String UPDATE_MOBILE="updateMobileSendOTP";
    public static final String GET_TOPUP="getTopup";
    public static final String CONSUMED_TOPUP="consumedTopup";
    public static final String GET_STATUS_AUTOPAY="getStatusAutopay";
    public static final String ADD_TOPUP="addTopup";
    public static final String GET_INVOICE_LIST="getInvoiceList";
    public static final String PAYMENT_TRANSACTIONDETAIL="paymentTransactionDetail";
    public static final String GET_SR_STATUS="getSRstatus";
    public static final String CHECK_SR="checkSR";
    public static final String GET_CONTACT="getContactDetails";
    public static final String ADD_CONTACT="addContactDetails";
    public static final String UPDATE_CONTACT="updateContactDetails";
    public static final String UPDATE_GSTN="updateGSTN";
    public static final String NO_INTERNET="getInternetNotWorking";
    public static final String NO_FDSS_INTERNET="getFDSSInternetNotWorking";
    public static final String REACTIVE_PLAN="reactivateAccount";
    public static final String ENABLE_CASE_STUDY="enableSafeCustodytoActive";
    public static final String KNOW_MORE="knowMoreForPlan";
    public static final String COMPARISION_PLAN = "comparisonPlan";
    public static final String PRO_DATA_CHARGES_PLAN = "proDataChargesForPlan";
    public static final String PRO_DATA_CHARGES_TOPUP_PLAN = "proDataChargesForTopup";
    public static final String DEACTIVE_TOPUP = "deactivateTopup";


    public static final String UPDATE_TAN="updateTAN";
    public static final String UPDATE_EMAIL_VIA_OTP="updateemail";
    public static final String UPDATE_MOBILE_VIA_OTP="updatemobile";

    public static final String RESEND_OTP_="resendotp";

    public static final String RESEND_OTP_UPDATE_EMAIL="updateEmailSendOTP";
    public static final String RESEND_OTP_UPDATE_MOBILE="updateMobileSendOTP";
    public static final String TRACK_ORDER="trackOrder";

    public static final String RESET_PASSWORD="resetpassword";
    public static final String GET_LINK_ACCOUNT="getLinkAccount";
    public static final String REMOVE_LINK_ACCOUNT="removeLinkAccount"
            ;
    public static final String SEND_OTP_LINK_ACCOUNT="sendOTPLinkAccount";
    public static final String RESEND_OTP_LINK_ACCOUNT="reSendOTPLinkAccount";
    public static final String ADD_LINK_ACCOUNT="addLinkAccount";
    public static final String TEST_NOTIFICATION="testnotification";
    public static final String DYNAMIC_NOTIFICATION="dynamicnotificationsent";
    public static final String ALL_NOTIFICATION="getallnotifications";
    public static final String GET_ARCHIVED_NOTIFICATION="getallarchievenotifications";

    public static final String SEARCH_NOTIFICATION="searchnotification";
    public static final String DELETE_NOTIFICATION="deletenotifications";
    public static final String ARCHIVED_NOTIFICATION="archievenotifications";
    public static final String READ_NOTIFICATION="readnotifications";
    public static final String DEVICE_SIGN_IN="deviceSignIn";
    public static final String DEVICE_SIGN_OUT="deviceSignOut";

    public static final String CREATE_TRANSACTION="createTransaction";
    public static final String UPDATE_PAYMENT_STATUS="responsePayment";
    public static final String CREATE_ORDER="createOrder";
    public static final String UPDATE_STATUS_AUTO_PAY="responsePaymentForAutopay";
    public static final String DISABLE_ORDER="disableOrder";
    public static final String PAGE_ID="RAZORPAY";
    public static final String SENGMENT="segment";
    public static final String ACTION_SEGMENT="action_segement";


    public static final String SENGMENTBYID = "segment_by_id";
    public static final String GET_SEGMENT_BY_ID = "get_segment_by_id";
    public static final String FAQCATEGORY = "faqcategory";
    public static final String LIKE_FAQ = "likefaq";
    public static final String UNLIKE_FAQ = "unlikefaq";
    public static final String VIEWCOUNT = "viewcount";
    public static final String GETTHOUMBCOUNT = "getthumbscount";

    @NotNull
    public static final String ACTION_RECENTSEARCH="RecentSearch";
}
