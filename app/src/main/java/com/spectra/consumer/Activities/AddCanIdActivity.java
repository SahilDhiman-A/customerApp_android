package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserDataDB;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.GetLinkAccountRequest;
import com.spectra.consumer.service.model.Request.SendOtpLinkAccountRequest;
import com.spectra.consumer.service.model.Response.CanResponse;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.service.model.Response.UpdateMobileResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_ACCOUNT;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_ALL_MENU;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD;
import static com.spectra.consumer.Utils.Constant.LINKED_ACCOUNT;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.USER_DB;
import static com.spectra.consumer.service.repository.ApiConstant.GET_LINK_ACCOUNT;
import static com.spectra.consumer.service.repository.ApiConstant.SEND_OTP_LINK_ACCOUNT;

public class AddCanIdActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    AppCompatImageView imgBack;
    @BindView(R.id.txt_head)
    TextView txtHead;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_internet)
    LinearLayout noInternet;
    @BindView(R.id.txtUpdateHeading)
    AppCompatTextView txtUpdateHeading;
    @BindView(R.id.input_update_field)
    TextInputEditText inputUpdateField;
    private SpectraViewModel spectraViewModel;
    private String canID;
    private ArrayList<CanResponse> canResponseArrayList = new ArrayList<>();
    private ArrayList<String> canResponseArrayListMain = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_canid_activity);
        ButterKnife.bind(this);
        txtHead.setText(R.string.addCanId);
        txtUpdateHeading.setText(R.string.pleaseAddyourCanID);
        imgBack.setVisibility(View.VISIBLE);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        imgBack.setOnClickListener(view -> {
            onBackPressed();
        });
        UserDataDB userDataDB = DroidPrefs.get(this, USER_DB, UserDataDB.class);
        setLIst(userDataDB.getResponseHashMap());
        getContactList();
    }

    private void sendOtp() {
        if (Constant.isInternetConnected(this)) {
            noInternet.setVisibility(View.GONE);
            SendOtpLinkAccountRequest sendOtpLinkAccountRequest = new SendOtpLinkAccountRequest();
            sendOtpLinkAccountRequest.setAuthkey(BuildConfig.AUTH_KEY);
            sendOtpLinkAccountRequest.setAction(SEND_OTP_LINK_ACCOUNT);
            sendOtpLinkAccountRequest.setCanID(canID);
            spectraViewModel.sendOtpLinkAccountRequest(sendOtpLinkAccountRequest).observe(AddCanIdActivity.this, AddCanIdActivity.this::consumeResponse);
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                showLoadingView(false);
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                break;
            case ERROR:
                showLoadingView(false);
                assert apiResponse.error != null;
                Constant.MakeToastMessage(AddCanIdActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case GET_LINK_ACCOUNT:
                    GetLinkAccountResponse getLinkAccountResponse = (GetLinkAccountResponse) response;
                    if (getLinkAccountResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        canResponseArrayList = getLinkAccountResponse.getResponse();
                        if (canResponseArrayList != null && canResponseArrayList.size() > 0) {
                            for (CanResponse canResponse : canResponseArrayList) {
                                canResponseArrayListMain.add(canResponse.getLink_canid());
                            }
                        }
                    }
                    break;
                case SEND_OTP_LINK_ACCOUNT:
                    Intent intent = new Intent(AddCanIdActivity.this, OtpActivity.class);
                    UpdateMobileResponse updateMobileResponse = (UpdateMobileResponse) response;
                    if (updateMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        try {
                            JSONObject jsonObject = new JSONObject(updateMobileResponse.getResponse().toString());
                            String phone = jsonObject.getString("mobileNo");
                            int otp = jsonObject.getInt("OTP");
                            intent.putExtra("phone", phone);
                            intent.putExtra("otp", "" + otp);
                            intent.putExtra("canID", canID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("type", LINKED_ACCOUNT);
                        startActivity(intent);
                    } else {
                        Constant.MakeToastMessage(AddCanIdActivity.this, updateMobileResponse.getMessage());
                    }
                    break;
            }

        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.btn_update)
    public void onClick() {
        canID = Objects.requireNonNull(inputUpdateField.getText()).toString();
        if (TextUtils.isEmpty(canID)) {
            Constant.MakeToastMessage(AddCanIdActivity.this, "CanID can't be blank");
            return;
        }
        if (canID.length() < 5) {
            Constant.MakeToastMessage(AddCanIdActivity.this, "CanID should be min 5 char long");
            return;
        }
        if (canResponseArrayListMain.contains(canID)) {
            Constant.MakeToastMessage(AddCanIdActivity.this, "CanID already linked");
            return;
        }
        sendOtp();
    }

    public void setLIst(LinkedHashMap<String, LoginMobileResponse> mp) {
        for (Map.Entry<String, LoginMobileResponse> stringLoginMobileResponseEntry : mp.entrySet()) {
            LoginMobileResponse loginMobileResponse = (LoginMobileResponse) ((Map.Entry) stringLoginMobileResponseEntry).getValue();
            canResponseArrayListMain.add(loginMobileResponse.getCANId());
        }
    }

    private void getContactList() {
        CAN_ID can_id = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        if (Constant.isInternetConnected(this)) {
            GetLinkAccountRequest linkAccountRequest = new GetLinkAccountRequest();
            linkAccountRequest.setAuthkey(BuildConfig.AUTH_KEY);
            linkAccountRequest.setAction(GET_LINK_ACCOUNT);
            if (can_id.isMobile) {
                linkAccountRequest.setMobileNo(can_id.mobile);
            } else {
                linkAccountRequest.setCanID(can_id.baseCanID);
            }
            spectraViewModel.getLinkAccount(linkAccountRequest).observe(AddCanIdActivity.this, AddCanIdActivity.this::consumeResponse);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}







