package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.Utils.GSTINValidator;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.UpdateEmailRequest;
import com.spectra.consumer.service.model.Request.UpdateGSTNRequest;
import com.spectra.consumer.service.model.Request.UpdateMobileRequest;
import com.spectra.consumer.service.model.Response.GSTNObject;
import com.spectra.consumer.service.model.Response.UpdateEmailResponse;
import com.spectra.consumer.service.model.Response.UpdateGSTNResponse;
import com.spectra.consumer.service.model.Response.UpdateMobileResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static activeandroid.Cache.getContext;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EMAIL;
import static com.spectra.consumer.Utils.Constant.GSTN;
import static com.spectra.consumer.Utils.Constant.MOBILE;
import static com.spectra.consumer.Utils.Constant.PROFILE_VERIFY_TYPE_EMAIL;
import static com.spectra.consumer.Utils.Constant.PROFILE_VERIFY_TYPE_MOBILE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.TAN;
import static com.spectra.consumer.Utils.Constant.USER_NAME;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_EMAIL;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_GSTN;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_MOBILE;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_TAN;

public class EditProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;
    CurrentUserData userData;
    @BindView(R.id.etUserName)
    TextInputEditText etUserName;
    @BindView(R.id.etCompanyName)
    TextInputEditText etCompanyName;
    @BindView(R.id.inlUserName)
    TextInputLayout inlUserName;
    @BindView(R.id.inlCompanyName)
    TextInputLayout inlCompanyName;
    @BindView(R.id.etEmailId)
    TextInputEditText etEmailId;
    @BindView(R.id.etGSTN)
    TextInputEditText etGSTN;
    @BindView(R.id.etTAN)
    TextInputEditText etTAN;
    @BindView(R.id.etMobileNumber)
    TextInputEditText etMobileNumber;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    SpectraViewModel spectraViewModel;
    Intent intent;
    AlertDialog dial;
    String tanPre, emailPre,gstnPre,mobilePre;
    boolean apicall = false;
    boolean apicall2 = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.edit_profile));
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        getDetails();
    }

    @OnClick({R.id.img_back, R.id.tvEditMobileNumber, R.id.tvEditEmailId, R.id.tvEditGSTN, R.id.tvEditTAN})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tvEditMobileNumber:
                updateDialog(MOBILE, Objects.requireNonNull(etMobileNumber.getText()).toString());
                break;
            case R.id.tvEditEmailId:
                updateDialog(EMAIL, Objects.requireNonNull(etEmailId.getText()).toString());
                break;
            case R.id.tvEditGSTN:
                updateDialog(GSTN, Objects.requireNonNull(etGSTN.getText()).toString());
                break;
            case R.id.tvEditTAN:
                updateDialog(TAN, Objects.requireNonNull(etTAN.getText()).toString());
                break;

        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        apicall = true;

        switch (apiResponse.status) {
            case LOADING:
                apicall2=true;
                showLoadingView(true);
                break;
            case SUCCESS:
                apicall2 = false;
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                showLoadingView(false);
                break;
            case ERROR:
                apicall2 = false;
                dial.dismiss();
                showLoadingView(false);
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            Intent intent = new Intent(EditProfileActivity.this, OtpActivity.class);
            switch (code) {
                case UPDATE_MOBILE:
                    dial.dismiss();
                    UpdateMobileResponse updateMobileResponse = (UpdateMobileResponse) response;
                    if (updateMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        try {
                            JSONObject jsonObject = new JSONObject(updateMobileResponse.getResponse().toString());
                            String phone = jsonObject.getString("mobileNo");
                            int otp = jsonObject.getInt("OTP");
                            intent.putExtra("phone", phone);
                            intent.putExtra("otp", "" + otp);
                            etMobileNumber.setText(phone);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("type", PROFILE_VERIFY_TYPE_MOBILE);
                        startActivity(intent);
                    }
                    Constant.MakeToastMessage(EditProfileActivity.this, updateMobileResponse.getMessage());
                    break;
                case UPDATE_EMAIL:
                    dial.dismiss();
                    UpdateEmailResponse updateEmailResponse = (UpdateEmailResponse) response;
                    if (updateEmailResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        updateEmailResponse.setResponse(((UpdateEmailResponse) response).getResponse());
                        intent.putExtra("phone", updateEmailResponse.getResponse().getEmailID());
                        intent.putExtra("otp", String.valueOf(updateEmailResponse.getResponse().getOTP()));
                        intent.putExtra("type", PROFILE_VERIFY_TYPE_EMAIL);
                        startActivity(intent);
                    }
                    Constant.MakeToastMessage(EditProfileActivity.this, updateEmailResponse.getMessage());
                    break;
                case UPDATE_GSTN:
                    dial.dismiss();
                    UpdateGSTNResponse gstnResponse = (UpdateGSTNResponse) response;
                    Constant.MakeToastMessage(EditProfileActivity.this, gstnResponse.getMessage());
                    if (gstnResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && gstnResponse.getResponse() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(gstnResponse.getResponse().toString());
                            String num = jsonObject.getString("value");
                            etGSTN.setText(num);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onBackPressed();
                    }
                    break;
                case UPDATE_TAN:
                    dial.dismiss();
                    UpdateGSTNResponse gstnResponse1 = (UpdateGSTNResponse) response;
                    Constant.MakeToastMessage(EditProfileActivity.this, gstnResponse1.getMessage());
                    if (gstnResponse1.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && gstnResponse1.getResponse() != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(gstnResponse1.getResponse().toString());
                            String num = jsonObject.getString("value");
                            etTAN.setText(num);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        onBackPressed();
                    }
                    break;
            }
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (apicall) {
            Intent intent_account = new Intent(EditProfileActivity.this, MyAccountActivity.class);
            startActivity(intent_account);
        } else {
            super.onBackPressed();
        }
        finish();
    }

    private void getDetails() {
        intent = getIntent();
        String name = intent.getStringExtra(USER_NAME);
         mobilePre = intent.getStringExtra(MOBILE);
         emailPre = intent.getStringExtra(EMAIL);
        etEmailId.setText(emailPre);
        etUserName.setText(name);
        etCompanyName.setText(name);
        etMobileNumber.setText(mobilePre);
        if (intent.hasExtra(TAN)) {
            gstnPre=intent.getStringExtra(GSTN);
            tanPre=intent.getStringExtra(TAN);
            etGSTN.setText(intent.getStringExtra(GSTN));
            etTAN.setText(intent.getStringExtra(TAN));
            inlCompanyName.setVisibility(View.VISIBLE);
            inlUserName.setVisibility(View.GONE);

        } else {
            inlCompanyName.setVisibility(View.GONE);
            inlUserName.setVisibility(View.VISIBLE);
            findViewById(R.id.rlGSTN).setVisibility(View.GONE);
            findViewById(R.id.rlTAN).setVisibility(View.GONE);
        }
        etUserName.setHint("h?");
    }

    private void updateDialog(String type, String update) {
        AtomicBoolean isCall = new AtomicBoolean(false);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.dialog_update, null);
        AppCompatTextView cancel = v.findViewById(R.id.cancel);
        TextInputEditText input_update_field = v.findViewById(R.id.input_update_field);
        AppCompatTextView txtUpdateHeading = v.findViewById(R.id.txtUpdateHeading);
        RelativeLayout btn_updateLayout = v.findViewById(R.id.btn_updateLayout);
        ProgressBar progress_bar = v.findViewById(R.id.progress_bar);
        TextInputLayout tiL = v.findViewById(R.id.tiL);
        input_update_field.setText(update);
        input_update_field.setSelection(update.length());
        switch (type) {
            case MOBILE:
                input_update_field.setInputType(InputType.TYPE_CLASS_NUMBER);
                input_update_field.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(10)});
                txtUpdateHeading.setText(getString(R.string.enter_mobile));
                input_update_field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString().length() == 10) {
                            btn_updateLayout.setVisibility(View.VISIBLE);
                        } else {
                            btn_updateLayout.setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case EMAIL:
                input_update_field.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                txtUpdateHeading.setText(getString(R.string.enter_email));
                input_update_field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (Constant.isValidEmailId(editable.toString())) {
                            btn_updateLayout.setVisibility(View.VISIBLE);
                        } else {
                            btn_updateLayout.setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case TAN:
                input_update_field.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(10)});

                txtUpdateHeading.setText("Please enter TAN number");
                input_update_field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        try {
                            if (TextUtils.isEmpty(editable.toString())) {
                                tiL.setError("TAN cannot be blank");
                                progress_bar.setVisibility(View.GONE);
                            } else {
                                if (editable.toString().length() != 10 || !validateTan(editable.toString())) {
                                    tiL.setError("Please enter a valid TAN");
                                    progress_bar.setVisibility(View.GONE);
                                    btn_updateLayout.setVisibility(View.GONE);
                                    return;
                                } else {
                                    btn_updateLayout.setVisibility(View.VISIBLE);
                                    tiL.setError(null);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (editable.toString().toString().length() == 10) {
                            btn_updateLayout.setVisibility(View.VISIBLE);
                        } else {
                            btn_updateLayout.setVisibility(View.GONE);
                        }
                    }
                });
                break;
            case GSTN:
                input_update_field.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(15)});
                txtUpdateHeading.setText("Please enter GSTN number");
                input_update_field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        try {
                            if (isCall.get() && !GSTINValidator.validGSTIN(editable.toString())) {
                                tiL.setError("Please enter a valid GSTIN");
                            } else {
                                tiL.setError(null);
                            }
                            if (editable.toString().length() == 15) {
                                btn_updateLayout.setVisibility(View.VISIBLE);
                            } else {
                                btn_updateLayout.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
        dialog.setView(v);
        dialog.setCancelable(true);
        dial = dialog.create();
        dial.show();
        cancel.setOnClickListener(view -> dial.dismiss());
        btn_updateLayout.setOnClickListener(view -> {
           if(!apicall2) {
               UpdateGSTNRequest updateGSTNRequest;
               progress_bar.setVisibility(View.VISIBLE);
               switch (type) {
                   case MOBILE:
                       if (mobilePre.equals(Objects.requireNonNull(input_update_field.getText()).toString())) {
                           progress_bar.setVisibility(View.GONE);
                           Constant.MakeToastMessage(EditProfileActivity.this, "New and old Mobile Number cannot be same");
                           return;
                       }
                       UpdateMobileRequest otpRequest = new UpdateMobileRequest();
                       otpRequest.setAuthkey(BuildConfig.AUTH_KEY);
                       otpRequest.setAction(UPDATE_MOBILE);
                       otpRequest.setCanID(userData.CANId);
                       otpRequest.setNewMobile(Objects.requireNonNull(input_update_field.getText()).toString());
                       otpRequest.setOTP("");
                       spectraViewModel.updateMobile(otpRequest).observe(EditProfileActivity.this, EditProfileActivity.this::consumeResponse);
                       break;
                   case EMAIL:
                       if (emailPre.equals(Objects.requireNonNull(input_update_field.getText()).toString())) {
                           progress_bar.setVisibility(View.GONE);
                           Constant.MakeToastMessage(EditProfileActivity.this, "New and old Email ID cannot be same");
                           return;
                       }

                       UpdateEmailRequest request = new UpdateEmailRequest();
                       request.setAuthkey(BuildConfig.AUTH_KEY);
                       request.setAction(UPDATE_EMAIL);
                       request.setCanID(userData.CANId);
                       request.setNewEmailID(Objects.requireNonNull(input_update_field.getText()).toString());
                       request.setOTP("");
                       spectraViewModel.updateEmail(request).observe(EditProfileActivity.this, EditProfileActivity.this::consumeResponse);
                       break;
                   case TAN:
                       String TAN = input_update_field.getText().toString();
                       if (tanPre.equals(Objects.requireNonNull(input_update_field.getText()).toString())) {
                           progress_bar.setVisibility(View.GONE);
                           Constant.MakeToastMessage(EditProfileActivity.this, "New and old TAN Number cannot be same");
                           return;
                       }
                       isCall.set(true);
                       try {
                           if (TextUtils.isEmpty(TAN)) {
                               tiL.setError("TAN cannot be blank");
                               progress_bar.setVisibility(View.GONE);
                           } else {
                               if (TAN.length() != 10||!validateTan(TAN)) {
                                   tiL.setError("Please enter a valid TAN");
                                   progress_bar.setVisibility(View.GONE);
                                   return;
                               } else {
                                   tiL.setError(null);
                               }
                           }
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                       updateGSTNRequest = new UpdateGSTNRequest();
                       updateGSTNRequest.setAuthkey(BuildConfig.AUTH_KEY);
                       updateGSTNRequest.setAction(UPDATE_TAN);
                       updateGSTNRequest.setCanID(userData.CANId);
                       updateGSTNRequest.setTanNumber(Objects.requireNonNull(input_update_field.getText()).toString());
                       spectraViewModel.updateGSTAN(updateGSTNRequest).observe(EditProfileActivity.this, EditProfileActivity.this::consumeResponse);
                       break;
                   case GSTN:
                       if (gstnPre.equals(Objects.requireNonNull(input_update_field.getText()).toString())) {
                           progress_bar.setVisibility(View.GONE);
                           Constant.MakeToastMessage(EditProfileActivity.this, "New and old GST Number cannot be same");
                           return;
                       }
                       isCall.set(true);
                       String gstin = input_update_field.getText().toString();
                       try {
                           if (!GSTINValidator.validGSTIN(gstin)) {
                               tiL.setError("Please enter a valid GSTIN");
                               progress_bar.setVisibility(View.GONE);
                               return;
                           }
                       } catch (Exception e) {
                           e.printStackTrace();
                       }

                       updateGSTNRequest = new UpdateGSTNRequest();
                       updateGSTNRequest.setAuthkey(BuildConfig.AUTH_KEY);
                       updateGSTNRequest.setAction(UPDATE_GSTN);
                       updateGSTNRequest.setCanID(userData.CANId);
                       updateGSTNRequest.setGstNumber(gstin);
                       spectraViewModel.updateGSTAN(updateGSTNRequest).observe(EditProfileActivity.this, EditProfileActivity.this::consumeResponse);
                       break;
               }
           }


        });

        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    InputFilter filter = (source, start, end, dest, dstart, dend) -> {
        for (int i = start; i < end; ++i) {
            if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                return "";
            }
        }

        return null;
    };
    public static boolean validateTan(String s1) {
        Pattern regexp = Pattern.compile("[A-Z]{4}[0-9]{5}[A-Z]{1}");
        Matcher matcher = regexp.matcher(s1);
        return matcher.matches();
    }


}
