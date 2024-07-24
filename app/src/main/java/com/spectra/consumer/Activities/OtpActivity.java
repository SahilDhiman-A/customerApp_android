package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.AddLinkAccountRequest;
import com.spectra.consumer.service.model.Request.ResendOtpRequest;
import com.spectra.consumer.service.model.Request.UpdateEmailRequest;
import com.spectra.consumer.service.model.Request.UpdateMobileRequest;
import com.spectra.consumer.service.model.Response.BaseResponse;
import com.spectra.consumer.service.model.Response.UpdateMobileResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.util.Objects;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_ALL_MENU;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_LOGIN;
import static com.spectra.consumer.Utils.Constant.LINKED_ACCOUNT;
import static com.spectra.consumer.Utils.Constant.LOGIN_VERIFY_TYPE;
import static com.spectra.consumer.Utils.Constant.PROFILE_VERIFY_TYPE_EMAIL;
import static com.spectra.consumer.Utils.Constant.PROFILE_VERIFY_TYPE_MOBILE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.ADD_LINK_ACCOUNT;
import static com.spectra.consumer.service.repository.ApiConstant.RESEND_OTP;
import static com.spectra.consumer.service.repository.ApiConstant.RESEND_OTP_LINK_ACCOUNT;
import static com.spectra.consumer.service.repository.ApiConstant.RESEND_OTP_UPDATE_EMAIL;
import static com.spectra.consumer.service.repository.ApiConstant.RESEND_OTP_UPDATE_MOBILE;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_EMAIL_VIA_OTP;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_MOBILE_VIA_OTP;

public class OtpActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.txt_edit)
    AppCompatTextView txt_edit;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.txt_pin_entry)
    PinEntryEditText2 otpEntry;
    private String pinCode;
    Intent intent;
    private String phone_number;
    private String otp;
    private String type;
    SpectraViewModel spectraViewModel;
    private boolean isCall = true;
    String canID;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_otp);
        ButterKnife.bind(this);
        intent = getIntent();
        phone_number = intent.getStringExtra("phone");
        Log.d("Phone OA", phone_number);
        otp = intent.getStringExtra("otp");
        if (otp == null) {
            otp = "";
        }
        type = intent.getStringExtra("type");
        if (intent.hasExtra("canID")) {
            canID = intent.getStringExtra("canID");
        }


        context = OtpActivity.this;
        txt_edit.setText(phone_number);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        pinCode = "";
        img_back.setOnClickListener(view -> finish());
        otpEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 4) {
                    hideKeyboard(OtpActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (!TextUtils.isEmpty(type) && type.equals(LOGIN_VERIFY_TYPE)) {
            CurrentUserData userData = DroidPrefs.get(OtpActivity.this, CurrentuserKey, CurrentUserData.class);
            userData.is_Login = false;
            DroidPrefs.apply(this, CurrentuserKey, userData);
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                progress.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                isCall = true;
                progress.setVisibility(View.GONE);
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                break;
            case ERROR:
                progress.setVisibility(View.GONE);
                isCall = true;
                assert apiResponse.error != null;
                Constant.MakeToastMessage(OtpActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            progress.setVisibility(View.GONE);
            UpdateMobileResponse updateMobileResponse;
            if (code.equals(UPDATE_EMAIL_VIA_OTP) || code.equals(UPDATE_MOBILE_VIA_OTP) || code.equals(ADD_LINK_ACCOUNT)) {
                BaseResponse baseResponse = (BaseResponse) response;
                if (baseResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    //Nikhil -Add new Can Id
                    if (code.equals(ADD_LINK_ACCOUNT)) {
                        SpectraApplication.getInstance().postEvent(CATEGORY_ALL_MENU + "My Account", "Add_new_CanID", "New Can ID added", canID);
                    }
                    setVerifiedDialog(baseResponse.getMessage());
                } else {
                    Constant.MakeToastMessage(context, baseResponse.getMessage());
                }
            } else {
                if (code.equals(RESEND_OTP) || code.equals(RESEND_OTP_UPDATE_EMAIL) || code.equals(RESEND_OTP_UPDATE_MOBILE) ||
                        code.equals(RESEND_OTP_LINK_ACCOUNT)) {
                    SpectraApplication.getInstance().postEvent(CATEGORY_LOGIN, "otp_resend", "otp_resend", "");
                    updateMobileResponse = (UpdateMobileResponse) response;
                    //Nikhil - commented if block for resend otp
//                    if (updateMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
//                        Constant.MakeToastMessage(context, updateMobileResponse.getMessage());
//                    }
                }
            }
        }
    }


    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnClick({R.id.txt_resend, R.id.layout_submit})
    public void onClick(View view) {
        hideKeyboard(OtpActivity.this);
        pinCode = otpEntry.getText().toString();

        switch (view.getId()) {

            case R.id.txt_resend:
                progress.setVisibility(View.VISIBLE);
                ResendOtpRequest request = new ResendOtpRequest();
                request.setAuthkey(BuildConfig.AUTH_KEY);
                request.setOTP(otp);
                switch (type) {
                    case LOGIN_VERIFY_TYPE:
                        request.setAction(RESEND_OTP);
                        request.setMobileNo(phone_number);
                        break;
                    case PROFILE_VERIFY_TYPE_EMAIL:
                        request.setAction(RESEND_OTP_UPDATE_EMAIL);
                        request.setNewEmailID(phone_number);
                        break;
                    case PROFILE_VERIFY_TYPE_MOBILE:
                        request.setAction(RESEND_OTP_UPDATE_MOBILE);
                        request.setNewMobile(phone_number);
                        break;
                    case LINKED_ACCOUNT:
                        request.setAction(RESEND_OTP_LINK_ACCOUNT);
                        request.setMobileNo(phone_number);
                        break;
                }
                spectraViewModel.resendOtp(request).observe(OtpActivity.this, OtpActivity.this::consumeResponse);
                break;
            case R.id.layout_submit:
                if (isCall) {
                    progress.setVisibility(View.GONE);
                    if (pinCode == null || pinCode.equalsIgnoreCase("")) {
                        Constant.MakeToastMessage(context, getString(R.string.otp_blank_validation));
                        return;
                    } else if (!otp.equalsIgnoreCase(pinCode)) {
                        Constant.MakeToastMessage(OtpActivity.this, getString(R.string.invalid_otp));
                        return;
                    }
                    isCall = false;
                    progress.setVisibility(View.VISIBLE);
                    SpectraApplication.getInstance().postEvent(CATEGORY_LOGIN, "otp_validation", "otp_validation", "");
                    CurrentUserData userData = DroidPrefs.get(OtpActivity.this, CurrentuserKey, CurrentUserData.class);
                    switch (type) {
                        case LOGIN_VERIFY_TYPE:
                            userData.is_Login = true;
                            DroidPrefs.apply(this, CurrentuserKey, userData);
                            Intent intent;
                            if (userData.actInProgressFlag.equals("false")) {
                                intent = new Intent(OtpActivity.this, HomeActivity.class);
                            } else {
                                intent = new Intent(OtpActivity.this, TrackActivity.class);
                            }
                            startActivity(intent);
                            finish();
                            break;
                        case PROFILE_VERIFY_TYPE_EMAIL:
                            UpdateEmailRequest request1 = new UpdateEmailRequest();
                            request1.setAuthkey(BuildConfig.AUTH_KEY);
                            request1.setAction(UPDATE_EMAIL_VIA_OTP);
                            request1.setCanID(userData.CANId);
                            request1.setNewEmailID(phone_number);
                            spectraViewModel.updateEmailViaOTP(request1).observe(OtpActivity.this, OtpActivity.this::consumeResponse);
                            break;
                        case PROFILE_VERIFY_TYPE_MOBILE:
                            UpdateMobileRequest otpRequest = new UpdateMobileRequest();
                            otpRequest.setAuthkey(BuildConfig.AUTH_KEY);
                            otpRequest.setAction(UPDATE_MOBILE_VIA_OTP);
                            otpRequest.setCanID(userData.CANId);
                            otpRequest.setNewMobile(phone_number);
                            spectraViewModel.updateMobileViaOTP(otpRequest).observe(OtpActivity.this, OtpActivity.this::consumeResponse);
                            break;
                        case LINKED_ACCOUNT:
                            CAN_ID can_id = DroidPrefs.get(OtpActivity.this, BASE_CAN, CAN_ID.class);
                            AddLinkAccountRequest addLinkAccountRequest = new AddLinkAccountRequest();
                            addLinkAccountRequest.setAuthkey(BuildConfig.AUTH_KEY);
                            if (can_id.isMobile) {
                                addLinkAccountRequest.setmobileNo(can_id.mobile);
                            } else {
                                addLinkAccountRequest.setCanID(can_id.baseCanID);
                            }
                            addLinkAccountRequest.setAction(ADD_LINK_ACCOUNT);
                            addLinkAccountRequest.setlinkCanID(canID);
                            addLinkAccountRequest.setuserName("");
                            spectraViewModel.addLinkAccount(addLinkAccountRequest).observe(OtpActivity.this, OtpActivity.this::consumeResponse);
                            break;
                    }
                }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setVerifiedDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_sr_dialog, null);
        AppCompatTextView back_to_sr = v.findViewById(R.id.back_to_sr);
        AppCompatTextView txt_head = v.findViewById(R.id.txt_head);
        TextView txt_heading = v.findViewById(R.id.txt_heading);
        txt_head.setVisibility(View.VISIBLE);
        txt_head.setText(getString(R.string.otpVerified));
        back_to_sr.setText("Ok");
        back_to_sr.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        txt_heading.setText(message);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();

        back_to_sr.setOnClickListener(vie -> {
            dial.dismiss();
            Intent intent;
            if (type.equals(LINKED_ACCOUNT)) {
                CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                CAN_ID can_id = DroidPrefs.get(context, BASE_CAN, CAN_ID.class);
                Data.CANId = canID;
                can_id.Linked = Data.CANId;
                DroidPrefs.apply(context, BASE_CAN, can_id);
                DroidPrefs.apply(context, CurrentuserKey, Data);
                intent = new Intent(context, HomeActivity.class);

            } else {
                intent = new Intent(context, MyAccountActivity.class);
            }
            startActivity(intent);
            finish();
        });
        if (!isFinishing()) {
            dial.show();
        }
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
