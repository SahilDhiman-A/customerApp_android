package com.spectra.consumer.Fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.spectra.consumer.Activities.DataUsageActivity;
import com.spectra.consumer.Activities.GeneralSeachActivity;
import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.MyAccountActivity;
import com.spectra.consumer.Activities.NotificationLActivity;
import com.spectra.consumer.Activities.PayNowActivity;
import com.spectra.consumer.Activities.SpectraApplication;
import com.spectra.consumer.Activities.TopUpActivity;
import com.spectra.consumer.Adapters.HomeBannerAdapter;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Response.IvrNotificationResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationInfoData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponse;
import com.spectra.consumer.viewModel.SpectraNotificationViewModel;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD_MENU;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_HOME;
import static com.spectra.consumer.Utils.Constant.SEARCHSOURCE;
import static com.spectra.consumer.Utils.Constant.STATUS_CODE;
import static com.spectra.consumer.Utils.Constant.allCan;
import static com.spectra.consumer.service.repository.ApiConstant.ALL_NOTIFICATION;

public class HomeFragment extends Fragment {
    private HomeActivity homeActivity;
    private Context context;
    private View view;
    @BindView(R.id.txt_greeting)
    TextView txt_greeting;
    @BindView(R.id.txt_user_and_can)
    TextView txt_user_and_can;
    @BindView(R.id.txt_outstanding_amount)
    TextView txt_outstanding_amount;
    @BindView(R.id.txt_due_date)
    TextView txt_due_date;
    @BindView(R.id.btn_pay_now)
    AppCompatTextView btn_pay_now;
    @BindView(R.id.txt_status)
    ImageView txt_status;
    @BindView(R.id.txt_ticket_number)
    TextView txt_ticket_number;
    @BindView(R.id.txt_estimated_time)
    TextView txt_estimated_time;
    @BindView(R.id.txt_sr_number)
    TextView txt_sr_number;
    @BindView(R.id.txt_due)
    TextView txt_due;
    @BindView(R.id.view_data)
    RelativeLayout view_data;
    @BindView(R.id.view_full)
    View view_full;
    @BindView(R.id.view_banner)
    ViewPager viewPager;
    @BindView(R.id.card_sr)
    CardView card_sr;
    @BindView(R.id.layout_view)
    RelativeLayout layout_view;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_data_used)
    TextView txt_data_used;
    @BindView(R.id.txt_total_data)
    TextView txt_total_data;
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.relative_viewpager)
    RelativeLayout relative_viewpager;
    @BindView(R.id.img_data)
    ImageView img_data;
    @BindView(R.id.layout_data_limited)
    RelativeLayout layout_data_limited;
    @BindView(R.id.txt_unlimited)
    TextView txt_unlimited;
    @BindView(R.id.layout_dataWarning)
    LinearLayout layout_dataWarning;
    @BindView(R.id.ivNotification)
    ImageView ivNotification;

    @BindView(R.id.rlNotification)
    RelativeLayout rlNotification;

    @BindView(R.id.ivNotificationDot)
    ImageView ivNotificationDot;

    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    private CurrentUserData Data;
    String canIdAnalytics;
    boolean didClickedPayNow = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) context;
        this.context = context;
        CAN_ID canIdNik = DroidPrefs.get(context, BASE_CAN, CAN_ID.class);
        Log.d("Nik Can", canIdNik.baseCanID);
        canIdAnalytics = canIdNik.baseCanID;
        didClickedPayNow = false;
    }


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_home_fragment, null);
            ButterKnife.bind(this, view);
            Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
            Constant.greeting_msg(txt_greeting);
            String user_can = Data.AccountName + " (" + Data.CANId + ")";
            txt_user_and_can.setText(user_can);
            setUserData(Data);
        }
        ivNotificationDot.setVisibility(View.GONE);
        //T3ODO SP
        getNotificationList();
        context.registerReceiver(myBroadcastReceiver, new IntentFilter("FBRIMAGE"));
        context.registerReceiver(myBroadcastReceiver2, new IntentFilter("NOT_CALL"));
        return view;
    }


    @SuppressLint("SetTextI18n")
    private void setUserData(CurrentUserData currentUserData) {
        if (isAdded()) {
            if (currentUserData.planDataVolume != null && !currentUserData.planDataVolume.equalsIgnoreCase("")) {
                if (currentUserData.planDataVolume.equalsIgnoreCase("Unlimited")) {
                    img_data.setImageResource(R.drawable.ic_gas_station_pump);
                    view_data.setVisibility(View.GONE);
                    view_full.setVisibility(View.GONE);
                    layout_data_limited.setVisibility(View.GONE);
//                    txt_unlimited.setVisibility(View.VISIBLE);
                } else {
                    boolean dataWarning = false;
                    img_data.setImageResource(R.drawable.ic_data_total_used);
                    view_data.setVisibility(View.VISIBLE);
                    view_full.setVisibility(View.VISIBLE);
                    String total = currentUserData.planDataVolume.substring(0, currentUserData.planDataVolume.lastIndexOf(" "));
                    float total_data = Float.parseFloat(total);
                    float used_data;
                    if (TextUtils.isEmpty(currentUserData.DataConsumption) || currentUserData.DataConsumption.equalsIgnoreCase("Unlimited")) {
                        used_data = 0;
                    } else {
                        used_data = Float.parseFloat(currentUserData.DataConsumption);
                    }
                    float data_left = Constant.round(total_data - used_data);
                    float used_percentage;
                    used_percentage = used_data / total_data * 100;
                    // TODO topup warning functionality
                    if (used_percentage >= 80) {
                        dataWarning = true;
                    }
                    RelativeLayout.LayoutParams view_full_params = (RelativeLayout.LayoutParams) view_full.getLayoutParams();
                    float view_full_height = (float) view_full_params.height;
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_data.getLayoutParams();
                    float view_data_used = used_percentage / 100 * view_full_height;
                    if (data_left > 0) {
                        params.height = Math.round(view_data_used);
                        txt_data_used.setText(data_left + " GB");
                    } else {
                        txt_data_used.setText("0 GB");
                        params.height = Math.round(view_full_height);
                    }
                    // TODO topup warning functionality
                    if (dataWarning) {
                        layout_dataWarning.setVisibility(View.VISIBLE);
                        txt_unlimited.setVisibility(View.GONE);
                        layout_data_limited.setVisibility(View.GONE);
                    } else {
                        layout_dataWarning.setVisibility(View.GONE);
                        txt_unlimited.setVisibility(View.GONE);
                        layout_data_limited.setVisibility(View.VISIBLE);
                    }
                    view_data.setLayoutParams(params);
                }
                txt_total_data.setText(currentUserData.planDataVolume);
            }


            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parse = null;
            Date sr_parse = null;
            Date sr_parse_etr = null;
            try {
                String date = "";
                if(!TextUtils.isEmpty(currentUserData.DueDate)) {
                    date = currentUserData.DueDate;
                }
                parse = sdf.parse(date);
                if (currentUserData.SRCreatedOn != null && !currentUserData.SRCreatedOn.equalsIgnoreCase("")) {
                    sr_parse = sdf.parse(currentUserData.SRCreatedOn);
                    sr_parse_etr = sdf.parse(currentUserData.SRETR);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String myFor = "MMM dd, yyyy";
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat my = new SimpleDateFormat(myFormat, Locale.US);
            SimpleDateFormat my_forms = new SimpleDateFormat(myFor, Locale.US);
            if (currentUserData.OutStandingAmount.equalsIgnoreCase("0")) {
                //Pay in Advance
                didClickedPayNow = false;
                txt_outstanding_amount.setText(getString(R.string.no_dues));
                btn_pay_now.setText("Pay in Advance");
                btn_pay_now.setVisibility(View.VISIBLE);
                txt_due_date.setVisibility(View.GONE);
                txt_due.setVisibility(View.GONE);
            } else {
                //Pay now
                didClickedPayNow = true;
                txt_outstanding_amount.setText(MessageFormat.format("₹ {0}", Constant.Round(Float.parseFloat(currentUserData.OutStandingAmount), 2)));
                btn_pay_now.setVisibility(View.VISIBLE);
                txt_due.setVisibility(View.VISIBLE);
                if (parse != null) {
                    txt_due_date.setText(my_forms.format(parse.getTime()));
                    if (currentUserData.PreBarredFlag.equalsIgnoreCase("true")) {
                        txt_due_date.setTextColor(getResources().getColor(R.color.back_color));
                    } else {
                        txt_due_date.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }

            }
            if (sr_parse != null) {
                txt_ticket_number.setText(my.format(sr_parse.getTime()));
            }
            if (sr_parse_etr != null) {
                txt_estimated_time.setText(my.format(sr_parse_etr.getTime()));
            }
            if (currentUserData.SRNumber != null && !currentUserData.SRNumber.equalsIgnoreCase("")) {
                card_sr.setVisibility(View.VISIBLE);
                txt_sr_number.setText(currentUserData.SRNumber);
                if (currentUserData.SRCaseStatus != null && !currentUserData.SRCaseStatus.equalsIgnoreCase("")) {
                    if (currentUserData.SRCaseStatus.equalsIgnoreCase("In Progress")) {
                        txt_status.setImageResource(R.drawable.ic_in_progress);
                    } else {
                        txt_status.setImageResource(R.drawable.ic_resolved);
                    }
                }

            } else {
                card_sr.setVisibility(View.GONE);
            }
            List<IvrNotificationResponse> ivrDatalist = currentUserData.ivrNotification;
            if (ivrDatalist != null && ivrDatalist.size() > 0) {
                viewPager.setVisibility(View.VISIBLE);
                relative_viewpager.setVisibility(View.VISIBLE);
                HomeBannerAdapter adapter = new HomeBannerAdapter(homeActivity, ivrDatalist);
                viewPager.setAdapter(adapter);
            } else {
                relative_viewpager.setVisibility(View.GONE);
            }
        }
        layout_view.setVisibility(View.VISIBLE);
        ivNotification.setOnClickListener(v -> {
            SpectraApplication.getInstance().postEvent(CATEGORY_HOME, "Notification_open", "Notification clicked",canIdAnalytics);
            Intent intent = new Intent(context, NotificationLActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        didClickedPayNow = false;
        ivNotification.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.layout_view_details, R.id.btn_pay_now, R.id.try_again, R.id.view_all, R.id.upgradePlan, R.id.txt_user_and_can, R.id.ivSearch})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivSearch:
                Intent searchintent = new Intent(homeActivity, GeneralSeachActivity.class);
                searchintent.putExtra(SEARCHSOURCE, "HOME");
                startActivity(searchintent);
                break;
            case R.id.txt_user_and_can:
                Intent userintent = new Intent(homeActivity, MyAccountActivity.class);
                startActivity(userintent);
                break;
            case R.id.layout_view_details:
                SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD, "view_details", "view_details",canIdAnalytics);
                Intent intent = new Intent(homeActivity, DataUsageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pay_now:
                if (didClickedPayNow){
                    SpectraApplication.getInstance().postEvent(CATEGORY_HOME, "Pay_Now", "pay now clicked "+Data.OutStandingAmount,canIdAnalytics);
                }else {
                    SpectraApplication.getInstance().postEvent(CATEGORY_HOME, "Pay_advance", "Pay in Advance", canIdAnalytics);
                }
                Intent intent_pay = new Intent(homeActivity, PayNowActivity.class);
                String amount = Data.OutStandingAmount;
                if (TextUtils.isEmpty(amount)) {
                    amount = "0";
                }
                if (!TextUtils.isEmpty(amount)) {
                    intent_pay.putExtra("email", Data.Email);
                    intent_pay.putExtra("mobile", Data.Number);
                    intent_pay.putExtra("payableAamount", amount);
                    intent_pay.putExtra("canID", Data.CANId);
                    intent_pay.putExtra("type", "unpaid");
                    intent_pay.putExtra("subType", "normal");
                    startActivity(intent_pay);
                } else {
                    Constant.MakeToastMessage(context, "Payable amount can't be 0");
                }
                break;
            case R.id.view_all:
                SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD, "view_all_service_requests", "view_all_service_request_click",canIdAnalytics);
                homeActivity.setSelectedID(R.id.navigation_Sr, false);
                break;
            case R.id.upgradePlan:
                Intent selectplanintent = new Intent(homeActivity, TopUpActivity.class);
                startActivity(selectplanintent);
                break;
        }
    }

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (ivNotificationDot != null) {
                    CurrentUserData userData = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                    userData.isNot = true;
                    DroidPrefs.apply(context, CurrentuserKey, userData);
                    ivNotificationDot.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private final BroadcastReceiver myBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                getNotificationList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void getNotificationList() {
        if (Constant.isInternetConnected(getActivity())) {
            SpectraNotificationViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraNotificationViewModel.class);
            if (!TextUtils.isEmpty(allCan)) {
                spectraViewModel.getAllNotification(ALL_NOTIFICATION, allCan, 0, 20).observe(this, this::consumeResponse);
            }
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                break;
            case ERROR:
                assert apiResponse.error != null;
                Constant.MakeToastMessage(context, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case ALL_NOTIFICATION:
                    boolean isNot = false;
                    NotificationResponse notificationResponse = (NotificationResponse) response;
                    if (notificationResponse.getStatus() == STATUS_CODE) {
                        ArrayList<NotificationData> notificationDataList1 = notificationResponse.getResponse();
                        for (NotificationData notificationData : notificationDataList1) {
                            ArrayList<NotificationInfoData> notificationInfo = notificationData.getResponse();
                            for (NotificationInfoData notification : notificationInfo) {
                                if (!notification.getIs_read()) {
                                    isNot = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (isNot) {
                        ivNotificationDot.setVisibility(View.VISIBLE);
                    } else {
                        ivNotificationDot.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {

        try {
            if (myBroadcastReceiver2 != null)
                context.unregisterReceiver(myBroadcastReceiver2);

            if (myBroadcastReceiver != null)
                context.unregisterReceiver(myBroadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
