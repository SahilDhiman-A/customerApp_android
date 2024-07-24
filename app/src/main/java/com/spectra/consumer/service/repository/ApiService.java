package com.spectra.consumer.service.repository;

import com.google.gson.JsonElement;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.AddLinkAccountRequest;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Request.ContactRequest;
import com.spectra.consumer.service.model.Request.CreateOrderRequest;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
import com.spectra.consumer.service.model.Request.CreateTransactionRequest;
import com.spectra.consumer.service.model.Request.DisableAutoPayRequest;
import com.spectra.consumer.service.model.Request.DisableOrderRequest;
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
import com.spectra.consumer.service.model.Request.PostDeactiveTopupPlan;
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;
import com.spectra.consumer.service.model.Request.PostPlanComparisionRequest;
import com.spectra.consumer.service.model.Request.PostProDataChangeRequest;
import com.spectra.consumer.service.model.Request.PostProDataTopUpRequest;
import com.spectra.consumer.service.model.Request.RemoveLinkAccountRequest;
import com.spectra.consumer.service.model.Request.RequestThumbsDown;
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
import com.spectra.consumer.service.model.Request.ResponsePaymentStatusRequest;
import com.spectra.consumer.service.model.Request.ResponsePaymentAutopayRequest;
import com.spectra.consumer.service.model.Request.UpdateTokenRequest;
import com.spectra.consumer.service.model.Response.AddContactResponse;
import com.spectra.consumer.service.model.Response.AddTopUpResponse;
import com.spectra.consumer.service.model.Response.BaseResponse;
import com.spectra.consumer.service.model.Response.CategoryResponseBase;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.ComparePlanResponse;
import com.spectra.consumer.service.model.Response.ContactListResponse;
import com.spectra.consumer.service.model.Response.CreateSrResponse;
import com.spectra.consumer.service.model.Response.CreateTransactionResponse;
import com.spectra.consumer.service.model.Response.DeactivaPlanResponse;
import com.spectra.consumer.service.model.Response.DisableAutoPayResponse;
import com.spectra.consumer.service.model.Response.FAQResponseBase;
import com.spectra.consumer.service.model.Response.FaqLikeUnlikeUpdate;
import com.spectra.consumer.service.model.Response.ForgotPasswordResponse;
import com.spectra.consumer.service.model.Response.GetDataChrgesResponse;
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
import com.spectra.consumer.service.model.Response.KnowMoreResponse;
import com.spectra.consumer.service.model.Response.LoginViaMobileResponse;
import com.spectra.consumer.service.model.Response.ProTopupResponse;
import com.spectra.consumer.service.model.Response.RecentSearchResponse;
import com.spectra.consumer.service.model.Response.SiStatusResponse;
import com.spectra.consumer.service.model.Response.ThoumbsCountResponse;
import com.spectra.consumer.service.model.Response.Top5FAQResponse;
import com.spectra.consumer.service.model.Response.TopUpListResponse;
import com.spectra.consumer.service.model.Response.TrackOrderResponse;
import com.spectra.consumer.service.model.Response.UpdateEmailResponse;
import com.spectra.consumer.service.model.Response.UpdateGSTNResponse;
import com.spectra.consumer.service.model.Response.UpdateMobileResponse;
import com.spectra.consumer.service.model.Response.UpdatePaymentStatusResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponseBase;
import com.spectra.consumer.service.model.Response.noticfication.NotificationSearchResponse;
import com.spectra.consumer.service.model.SegmentResponseBase;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @Headers({"Content-Type: application/json", "Accept: application/json", "Authorization: Bearer 01a62ae9623beb096ec88dca3836858c", "X-Source: Android"})
    @POST(BuildConfig.END_URL)
    Single<GetLinkAccountResponse> getDeviceSingIn(@Body UpdateTokenRequest getLinkAccountRequest);


    @Headers({"Content-Type: application/json", "Accept: application/json", "Authorization: Bearer 01a62ae9623beb096ec88dca3836858c", "X-Source: Android"})
    @POST(BuildConfig.END_URL)
    Single<GetLinkAccountResponse> getDeviceSingOut(@Body UpdateTokenRequest getLinkAccountRequest);

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
    Single<InternetNotWorkModelDTO> getFDSSFUPRequest(@Path(value = "api", encoded = true) String api, @Body Object requestBody);


    /**
     * Sprint 2
     */

    @POST(BuildConfig.END_URL)
    Single<TopUpListResponse> getTopUpList(@Body TopupRequest topupRequest);

    @POST(BuildConfig.END_URL)
    Single<TopUpListResponse> consumedTopup(@Body ConsumedTopupRequest topupRequest);

    @POST(BuildConfig.END_URL)
    Single<KnowMoreResponse> getKnowMore(@Body PostKnowMoreRequest postKnowMoreRequest);

    @POST(BuildConfig.END_URL)
    Single<ComparePlanResponse> getCompareData(@Body PostPlanComparisionRequest postPlanComparisionRequest);


    @POST(BuildConfig.END_URL)
    Single<ProTopupResponse> getProDataTopUp(@Body PostProDataTopUpRequest postProDataTopUpRequest);

    @POST(BuildConfig.END_URL)
    Single<GetDataChrgesResponse> getProDataChange(@Body PostProDataChangeRequest postProDataChangeRequest);

    @POST(BuildConfig.END_URL)
    Single<DeactivaPlanResponse> getDeactivaPlan(@Body PostDeactiveTopupPlan postDeactiveTopupPlan);

    @POST(BuildConfig.END_URL)
    Single<CreateTransactionResponse> createTransaction(@Body CreateTransactionRequest postDeactiveTopupPlan);

    @POST(BuildConfig.END_URL)
    Single<UpdatePaymentStatusResponse> updatePaymentStatus(@Body ResponsePaymentStatusRequest postDeactiveTopupPlan);

    @POST(BuildConfig.END_URL)
    Single<CreateTransactionResponse> createOrder(@Body CreateOrderRequest postDeactiveTopupPlan);

    @POST(BuildConfig.END_URL)
    Single<UpdatePaymentStatusResponse> updateStatusForAutopay(@Body ResponsePaymentAutopayRequest postDeactiveTopupPlan);

    @POST(BuildConfig.END_URL)
    Single<CreateTransactionResponse> disableOrder(@Body DisableOrderRequest disableOrderRequest);

    @GET("{path}")
    Single<NotificationResponse> getNotification(@Header("X-Source") String Source, @Path("path") String path, @Query("can_id") String can_id, @Query("skip") String skip, @Query("limit") String limit);

    @GET("{path}")
    Single<NotificationSearchResponse> searchNotification(@Header("X-Source") String Source, @Path("path") String path, @Query("can_id") String can_id, @Query("search_keyword") String search_keyword);

    @DELETE("{path}")
    Single<NotificationResponseBase> deleteNotifications(@Header("X-Source") String Source, @Path("path") String path, @Query("_id") String _id);

    @PUT("{path}")
    Single<NotificationResponseBase> archiveNotifications(@Header("X-Source") String Source, @Path("path") String path, @Query("_id") String _id);

    @PUT("{path}")
    Single<NotificationResponseBase> readNotifications(@Header("X-Source") String Source, @Path("path") String path, @Query("_id") String _id);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("segment/getsegmentlist")
    Single<SegmentResponseBase> getSengmentlist();

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("faq/getfaqbysegmentid/{id}/{can_id}")
    Single<FAQResponseBase> getFAQListbySegment(@Path("id") String segment_id,@Path("can_id")  String CanId,@Query("searchKey") String searchKey );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("category/getcategorylist")
    Single<CategoryResponseBase> getFAQCategory();


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("faq/gettop5faqlist/{id}")
    Single<Top5FAQResponse> getTop5FAQCategorySegment(@Path("id") String segment_id, @Query("searchKey") String quary);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("faq/viewincrement")
    Single<FaqLikeUnlikeUpdate>  addViewCountinFaq( @Query("id") String faq_id);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("faq/viewincrement")
    Single<FaqLikeUnlikeUpdate> removeViewCountinFaq( @Query("id") String faq_id);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("faq/submitfeedback/{id}")
    Single<FaqLikeUnlikeUpdate> submitFAQFeedback(@Path("id") String faq_id);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("faq/thumbsdown")
    Single<FaqLikeUnlikeUpdate> unlikeFaq(@Body RequestThumbsDown requestThumbsDown);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT("faq/thumbsup")
    Single<FaqLikeUnlikeUpdate> likeFaq( @Query("faq_id") String faqId, @Query("can_id") String canId);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("faq/getthumbscount")
    Single<ThoumbsCountResponse> getThumbsCount( @Query("faq_id") String faqId, @Query("can_id") String canId);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("faq/getrecentsearchlist/{can_id}")
    Single<RecentSearchResponse> getRecentSearch(@Path("can_id") String canId);

}

