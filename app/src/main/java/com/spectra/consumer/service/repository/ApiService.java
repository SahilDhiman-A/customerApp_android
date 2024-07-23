package com.spectra.consumer.service.repository;

import com.google.gson.JsonElement;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.AddLinkAccountRequest;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Request.ContactRequest;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
import com.spectra.consumer.service.model.Request.DisableAutoPayRequest;
import com.spectra.consumer.service.model.Request.ForgotPasswordRequest;
import com.spectra.consumer.service.model.Request.GetAccountDataRequest;
import com.spectra.consumer.service.model.Request.GetCasetypeRequest;
import com.spectra.consumer.service.model.Request.GetInvoiceContentRequest;
import com.spectra.consumer.service.model.Request.GetInvoiceListRequest;
import com.spectra.consumer.service.model.Request.GetLinkAccountRequest;
import com.spectra.consumer.service.model.Request.GetMrtgRequest;
import com.spectra.consumer.service.model.Request.GetOffersRequest;
import com.spectra.consumer.service.model.Request.GetProfileRequest;
import com.spectra.consumer.service.model.Request.GetRatePlanRequest;
import com.spectra.consumer.service.model.Request.GetSessionHistoryRequest;
import com.spectra.consumer.service.model.Request.GetSrRequest;
import com.spectra.consumer.service.model.Request.GetTransactionListRequest;
import com.spectra.consumer.service.model.Request.InvoiceDetailRequest;
import com.spectra.consumer.service.model.Request.LoginViaMobileRequest;
import com.spectra.consumer.service.model.Request.LoginViapasswordRequest;
import com.spectra.consumer.service.model.Request.PostFUPFlagRequest;
import com.spectra.consumer.service.model.Request.RemoveLinkAccountRequest;
import com.spectra.consumer.service.model.Request.ResendOtpRequest;
import com.spectra.consumer.service.model.Request.RestPasswordRequest;
import com.spectra.consumer.service.model.Request.SendOtpLinkAccountRequest;
import com.spectra.consumer.service.model.Request.SendOtpRequest;
import com.spectra.consumer.service.model.Request.SiStatusRequest;
import com.spectra.consumer.service.model.Request.TopupRequest;
import com.spectra.consumer.service.model.Request.TrackOrderRequest;
import com.spectra.consumer.service.model.Request.UpdateEmailRequest;
import com.spectra.consumer.service.model.Request.UpdateGSTNRequest;
import com.spectra.consumer.service.model.Request.UpdateMobileRequest;
import com.spectra.consumer.service.model.Response.AddContactResponse;
import com.spectra.consumer.service.model.Response.AddTopUpResponse;
import com.spectra.consumer.service.model.Response.BaseResponse;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.ContactListResponse;
import com.spectra.consumer.service.model.Response.CreateSrResponse;
import com.spectra.consumer.service.model.Response.DisableAutoPayResponse;
import com.spectra.consumer.service.model.Response.ForgotPasswordResponse;
import com.spectra.consumer.service.model.Response.GetInvoicelistResponse;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import com.spectra.consumer.service.model.Response.GetMrtgResponse;
import com.spectra.consumer.service.model.Response.GetOfferListResponse;
import com.spectra.consumer.service.model.Response.GetRatePlanResponse;
import com.spectra.consumer.service.model.Response.GetSessionHistoryResponse;
import com.spectra.consumer.service.model.Response.GetSrStatusResponse;
import com.spectra.consumer.service.model.Response.GetTransactionListResponse;
import com.spectra.consumer.service.model.Response.GetcaseTypeResponse;
import com.spectra.consumer.service.model.Response.GetprofileResponse;
import com.spectra.consumer.service.model.Response.InternetNotWorkModelDTO;
import com.spectra.consumer.service.model.Response.InvoiceDetailResponse;
import com.spectra.consumer.service.model.Response.LoginViaMobileResponse;
import com.spectra.consumer.service.model.Response.SiStatusResponse;
import com.spectra.consumer.service.model.Response.TopUpListResponse;
import com.spectra.consumer.service.model.Response.TrackOrderResponse;
import com.spectra.consumer.service.model.Response.UpdateEmailResponse;
import com.spectra.consumer.service.model.Response.UpdateGSTNResponse;
import com.spectra.consumer.service.model.Response.UpdateMobileResponse;

import java.util.HashMap;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {



    @POST(BuildConfig.END_URL)
    Single<LoginViaMobileResponse> getAccountByPassword(@Body LoginViapasswordRequest loginViapasswordRequest);

    @POST(BuildConfig.END_URL)
    Single<LoginViaMobileResponse> getAccountByMobile(@Body LoginViaMobileRequest loginViaMobileRequest);

    @POST(BuildConfig.END_URL)
    Single<TrackOrderResponse> trackOrder(@Body TrackOrderRequest loginViaMobileRequest);

    @POST(BuildConfig.END_URL)
    Single<LoginViaMobileResponse> getAccountByCanId(@Body GetAccountDataRequest getAccountDataRequest);

    @POST(BuildConfig.END_URL)
    Single<UpdateMobileResponse> sendOtp(@Body SendOtpRequest otpRequest);

    @POST(BuildConfig.END_URL)
    Single<GetLinkAccountResponse> getLinkAcoount(@Body GetLinkAccountRequest getLinkAccountRequest);

    @POST(BuildConfig.END_URL)
    Single<BaseResponse> removeLinkAccountRequest(@Body RemoveLinkAccountRequest removeLinkAccountRequest);

    @POST(BuildConfig.END_URL)
    Single<BaseResponse> addLinkAccount(@Body AddLinkAccountRequest addLinkAccountRequest);

    @POST(BuildConfig.END_URL)
    Single<UpdateMobileResponse> sendOtpLinkAccountRequest(@Body SendOtpLinkAccountRequest sendOtpLinkAccountRequest);

    @POST(BuildConfig.END_URL)
    Single<UpdateEmailResponse> updateEmail(@Body UpdateEmailRequest request);

    @POST(BuildConfig.END_URL)
    Single<BaseResponse> updateEmailViaOTP(@Body UpdateEmailRequest request);

    @POST(BuildConfig.END_URL)
    Single<UpdateGSTNResponse> updateGSTAN(@Body UpdateGSTNRequest request);


    @POST(BuildConfig.END_URL)
    Single<UpdateMobileResponse> updateMobile(@Body UpdateMobileRequest otpRequest);

    @POST(BuildConfig.END_URL)
    Single<BaseResponse> updateMobileViaOTP(@Body UpdateMobileRequest otpRequest);

    @POST(BuildConfig.END_URL)
    Single<UpdateMobileResponse> resendOtp(@Body ResendOtpRequest otpRequest);
    @POST(BuildConfig.END_URL)
    Single<InvoiceDetailResponse> getInvoiceDetail(@Body InvoiceDetailRequest invoiceDetailRequest);

    @POST(BuildConfig.END_URL)
    Single<ForgotPasswordResponse> forgotPassword(@Body ForgotPasswordRequest otpRequest);

    @POST(BuildConfig.END_URL)
    Single<TopUpListResponse> getTopUpList(@Body TopupRequest topupRequest);
    @POST(BuildConfig.END_URL)
    Single<TopUpListResponse> consumedTopup(@Body ConsumedTopupRequest topupRequest);



    @POST(BuildConfig.END_URL)
    Single<ContactListResponse> getContactListList(@Body ContactRequest request);

    @POST(BuildConfig.END_URL)
    Single<AddContactResponse> addContact(@Body AddContactRequest request);

    @POST(BuildConfig.END_URL)
    Single<ChangePlanResponse> changePlan(@Body ChangePlanRequest changePlanRequest);

    @POST(BuildConfig.END_URL)
    Single<GetOfferListResponse> getOffers(@Body GetOffersRequest getOffersRequest);

    @POST(BuildConfig.END_URL)
    Single<SiStatusResponse> getSiStatus(@Body SiStatusRequest siStatusRequest);

    @POST(BuildConfig.END_URL)
    Single<AddTopUpResponse> addTopUp(@Body AddTopUpRequest addTopUpRequest);

    @POST(BuildConfig.END_URL)
    Single<GetcaseTypeResponse> getCaseType(@Body GetCasetypeRequest getCasetypeRequest);

    @POST(BuildConfig.END_URL)
    Single<CreateSrResponse> createSR(@Body CreateSrRequest createSrRequest);

    @POST(BuildConfig.END_URL)
    Single<GetRatePlanResponse> getratePlanByCan(@Body GetRatePlanRequest getRatePlanRequest);

    @GET("{api}")
    Single<InternetNotWorkModelDTO> getNoInternet(@Path(value = "api", encoded = true) String api);
    @POST("{api}")
    Single<InternetNotWorkModelDTO> reActivePlan(@Path(value = "api", encoded = true) String api, @Body HashMap<String, Object> request);
    @POST("{api}")
    Single<InternetNotWorkModelDTO> enableSafeCustod(@Path(value = "api", encoded = true) String api, @Body HashMap<String, Object> request);



    @POST(BuildConfig.END_URL)
    Single<GetprofileResponse> getProfile(@Body GetProfileRequest getProfileRequest);
    @POST(BuildConfig.END_URL)
    Single<BaseResponse> getResetPassword(@Body RestPasswordRequest getProfileRequest);

    @POST(BuildConfig.END_URL)
    Single<GetMrtgResponse> getMrtg(@Body GetMrtgRequest getMrtgRequest);

    @POST(BuildConfig.END_URL)
    Single<GetSessionHistoryResponse> getSessionHistory(@Body GetSessionHistoryRequest getSessionHistoryRequest);

    @POST(BuildConfig.END_URL)
    Single<GetInvoicelistResponse> getInvoiceList(@Body GetInvoiceListRequest getInvoiceListRequest);

    @POST(BuildConfig.END_URL)
    Single<GetTransactionListResponse> getTransactionList(@Body GetTransactionListRequest getTransactionListRequest);

    @POST(BuildConfig.END_URL)
    Single<JsonElement> getInvoiceContent(@Body GetInvoiceContentRequest invoiceContentRequest);

    @POST(BuildConfig.END_URL)
    Single<GetSrStatusResponse> getSrStatus(@Body GetSrRequest getSrRequest);

    @POST(BuildConfig.DISABLE_URL)
    Single<DisableAutoPayResponse> disableAutoPay(@Body DisableAutoPayRequest disableAutoPayRequest);



    @Headers({"Content-Type: application/json", "Accept: application/json", "Authorization: Bearer 01a62ae9623beb096ec88dca3836858c"})
    @GET("{api}")
    Single<InternetNotWorkModelDTO> getFDSSRequest(@Path(value = "api", encoded = true) String api);


    @Headers({"Content-Type: application/json", "Accept: application/json", "Authorization: Bearer 01a62ae9623beb096ec88dca3836858c"})
    @POST("{api}")
    Single<InternetNotWorkModelDTO> getFDSSFUPRequest(@Path(value = "api", encoded = true) String api,@Body Object requestBody);

}
