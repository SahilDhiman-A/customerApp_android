package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserDataDB;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.DeviceData;
import com.spectra.consumer.service.model.Request.LoginViaMobileRequest;
import com.spectra.consumer.service.model.Request.LoginViapasswordRequest;
import com.spectra.consumer.service.model.Request.SendOtpRequest;
import com.spectra.consumer.service.model.Request.UpdateTokenRequest;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.service.model.Response.LoginViaMobileResponse;
import com.spectra.consumer.service.model.Response.UpdateMobileResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Activities.HomeActivity.TOKEN;
import static com.spectra.consumer.Activities.HomeActivity.canID;
import static com.spectra.consumer.Activities.HomeActivity.canIDS1;
import static com.spectra.consumer.Utils.CommonUtils.saveUser;
import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_LOGIN;
import static com.spectra.consumer.Utils.Constant.LOGIN_VERIFY_TYPE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.USER_DB;
import static com.spectra.consumer.Utils.Constant.hideKeyboard;
import static com.spectra.consumer.service.repository.ApiConstant.DEVICE_SIGN_OUT;
import static com.spectra.consumer.service.repository.ApiConstant.GET_ACCOUNT_BY_MOBILE;
import static com.spectra.consumer.service.repository.ApiConstant.GET_ACCOUNT_BY_PASSWORD;
import static com.spectra.consumer.service.repository.ApiConstant.SEND_OTP;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.img_login)
    ImageView img_login;
    @BindView(R.id.txt_make_life_better)
    TextView txt_make_life_better;
    @BindView(R.id.layout_mobile)
    CardView layout_mobile;
    @BindView(R.id.layout_mobile_username)
    CardView layout_mobile_username;
    @BindView(R.id.view_mobile)
    LinearLayout view_mobile;
    @BindView(R.id.input_mobile)
    TextInputEditText input_mobile;
    @BindView(R.id.btn_login)
    FloatingActionButton btn_login;
    @BindView(R.id.view_username)
    LinearLayout view_username;
    @BindView(R.id.input_username)
    TextInputEditText input_username;
    @BindView(R.id.input_password)
    TextInputEditText input_password;
    @BindView(R.id.txt_show_password)
    TextView txt_show_password;
    @BindView(R.id.txt_login)
    TextView txt_login;
    @BindView(R.id.txt_make_life_better_small)
    TextView txt_make_life_better_small;
    @BindView(R.id.txt_enter_mobile)
    TextView txt_enter_mobile;
    @BindView(R.id.btn_login_layout)
    RelativeLayout btn_login_layout;
    @BindView(R.id.btn_login_username_layout)
    RelativeLayout btn_login_username_layout;
    @BindView(R.id.txt_forgot_password)
    TextView txt_forgot_password;
    @BindView(R.id.txt_login_via_username)
    TextView txt_login_via_username;
    @BindView(R.id.img_login_layout)
    RelativeLayout img_login_layout;
    @BindView(R.id.img_login_layout_short)
    RelativeLayout img_login_layout_short;
    @BindView(R.id.txt_enter_username)
    TextView txt_enter_username;
    @BindView(R.id.layout_error)
    LinearLayout layout_error;
    @BindView(R.id.txt_error)
    TextView txt_error;
    @BindView(R.id.img_cross)
    ImageView img_cross;
    @BindView(R.id.view_name)
    View view_name;
    @BindView(R.id.view_pass)
    View view_pass;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.transition_container)
    RelativeLayout transition_container;
    boolean is_valid;
    List<LoginMobileResponse> user_data_list = new ArrayList<>();
    SpectraViewModel spectraViewModel;
    AlertDialog dialog;
    boolean is_error = false;
    UserDataDB userDataDB;
    private long pressedTime;
    private FirebaseAnalytics firebaseAnalytics;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        is_error = false;
        Constant.ShowPassword(input_password, txt_show_password);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);

        input_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_login_layout.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 10) {
                    hideKey();
                    btn_login_layout.setVisibility(View.VISIBLE);
                } else {

                    btn_login_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 10) {
                    hideKey();
                    btn_login_layout.setVisibility(View.VISIBLE);
                } else {
                    btn_login_layout.setVisibility(View.GONE);
                }
            }
        });
        input_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    if (!Objects.requireNonNull(input_password.getText()).toString().trim().isEmpty()) {
                        is_valid = true;
                        btn_login_username_layout.setBackground(getResources().getDrawable(R.drawable.login_background));
                    }
                    if (charSequence.length() == 20) {
                        hideKey();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() >= 1) {
                    if (!Objects.requireNonNull(input_password.getText()).toString().trim().isEmpty()) {
                        is_valid = true;
                        btn_login_username_layout.setBackground(getResources().getDrawable(R.drawable.login_background));
                    }

                }

            }
        });
        input_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    if (!Objects.requireNonNull(input_username.getText()).toString().trim().isEmpty()) {
                        is_valid = true;
                        btn_login_username_layout.setBackground(getResources().getDrawable(R.drawable.login_background));
                    }
                    if (charSequence.length() == 20) {
                        hideKey();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 10) {
                    if (!Objects.requireNonNull(input_username.getText()).toString().trim().isEmpty()) {
                        is_valid = true;
                        btn_login_username_layout.setBackground(getResources().getDrawable(R.drawable.login_background));
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            new GetVersionCode().execute();
        }
        userDataDB = DroidPrefs.get(this, USER_DB, UserDataDB.class);
        //TODO SP3
        getUpdateToken();
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                showLoadingView(false);
                break;
            case ERROR:
                showLoadingView(false);
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case GET_ACCOUNT_BY_PASSWORD:
                    SpectraApplication.getInstance().postEvent(CATEGORY_LOGIN, "login_user_pwd", "username_password", "");
                    LoginViaMobileResponse loginViaMobileResponse = (LoginViaMobileResponse) response;
                    if (loginViaMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && loginViaMobileResponse.response != null) {
                        is_error = false;
                        List<LoginMobileResponse> loginMobileResponses = loginViaMobileResponse.response;
                        if (loginMobileResponses.size() > 0) {
                            LinkedHashMap<String, LoginMobileResponse> responseHashMap = new LinkedHashMap<>();
                            responseHashMap.put(loginMobileResponses.get(0).getCANId(), loginMobileResponses.get(0));
                            userDataDB.setResponseHashMap(responseHashMap);
                            LoginMobileResponse data = loginMobileResponses.get(0);
                            saveUser(LoginActivity.this, data, false);
                            DroidPrefs.apply(LoginActivity.this, USER_DB, userDataDB);
                            CAN_ID can_id = new CAN_ID();
                            can_id.baseCanID = data.getCANId();
                            can_id.Linked = "";
                            can_id.mobile = "";
                            can_id.isMobile = false;
                            DroidPrefs.apply(LoginActivity.this, BASE_CAN, can_id);
                            if (data.getActInProgressFlag().equals("false")) {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, TrackActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } else {
                        is_error = true;
                        layout_error.setVisibility(View.VISIBLE);
                        txt_error.setText(loginViaMobileResponse.getMessage());
                        txt_enter_username.setVisibility(View.GONE);
                        input_username.setTextColor(getResources().getColor(R.color.error_color));
                        input_password.setTextColor(getResources().getColor(R.color.error_color));
                        view_name.setBackgroundColor(getResources().getColor(R.color.error_color));
                        view_pass.setBackgroundColor(getResources().getColor(R.color.error_color));
                        btn_login_username_layout.setBackground(getResources().getDrawable(R.drawable.login_background_error));
                    }
                    break;
                case GET_ACCOUNT_BY_MOBILE:
                    SpectraApplication.getInstance().postEvent(CATEGORY_LOGIN, "login_rmn", "registered_mobile_number", "");
                    LoginViaMobileResponse res = (LoginViaMobileResponse) response;
                    if (res.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && res.response != null) {
                        user_data_list = res.getResponse();
                        userDataDB.getResponseHashMap().clear();
                        if (user_data_list.size() > 0) {
                            LinkedHashMap<String, LoginMobileResponse> responseHashMap = new LinkedHashMap<>();
                            for (LoginMobileResponse loginMobileResponse : user_data_list) {
                                responseHashMap.put(loginMobileResponse.getCANId(), loginMobileResponse);
                            }
                            userDataDB.setResponseHashMap(responseHashMap);
                            DroidPrefs.apply(LoginActivity.this, USER_DB, userDataDB);
                            LoginMobileResponse data = user_data_list.get(0);
                            saveUser(LoginActivity.this, data, false);
                            CAN_ID can_id = new CAN_ID();
                            can_id.baseCanID = data.getCANId();
                            String mobile = input_mobile.getText().toString().trim();
                            can_id.mobile = mobile;
                            can_id.isMobile = true;
                            can_id.Linked = "";
                            DroidPrefs.apply(LoginActivity.this, BASE_CAN, can_id);
                            generate_otp(Objects.requireNonNull(input_mobile.getText()).toString().trim());
                        }
                    } else {
                        btn_login_layout.setClickable(true);
                        btn_login_layout.setEnabled(true);
                        Constant.MakeToastMessage(LoginActivity.this, res.getMessage());
                    }

                    break;
                case SEND_OTP:
                    UpdateMobileResponse updateMobileResponse = (UpdateMobileResponse) response;
                    if (updateMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {

                        Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                        try {
                            JSONObject jsonObject = new JSONObject(updateMobileResponse.getResponse().toString());
                            String phone = jsonObject.getString("mobileNo");
                            String otp = "" + jsonObject.getInt("OTP");
                            // Toast.makeText(this, otp, Toast.LENGTH_LONG).show();
                            intent.putExtra("phone", phone);
                            intent.putExtra("otp", otp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        CurrentUserData userData = DroidPrefs.get(LoginActivity.this, CurrentuserKey, CurrentUserData.class);
                        userData.is_Login = false;
                        DroidPrefs.apply(this, CurrentuserKey, userData);
                        intent.putExtra("type", LOGIN_VERIFY_TYPE);

                        startActivity(intent);
                    } else {
                        Constant.MakeToastMessage(LoginActivity.this, updateMobileResponse.getMessage());
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


    public void login_using_username() {
        if (Constant.isInternetConnected(this)) {
            hideKey();
            showLoadingView(true);
            String password = Objects.requireNonNull(input_password.getText()).toString().trim();
            byte[] data = password.getBytes(StandardCharsets.UTF_8);
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);
            LoginViapasswordRequest loginViapasswordRequest = new LoginViapasswordRequest();
            loginViapasswordRequest.setAction(GET_ACCOUNT_BY_PASSWORD);
            loginViapasswordRequest.setAuthkey(BuildConfig.AUTH_KEY);
            loginViapasswordRequest.setPassword(base64);
            loginViapasswordRequest.setUsername(Objects.requireNonNull(input_username.getText()).toString().trim());
            spectraViewModel.getAccountByPassword(loginViapasswordRequest).observe(this, this::consumeResponse);
        }
    }

    public void login_using_mobile(String mobile) {
        if (Constant.isInternetConnected(this)) {
            hideKey();
            showLoadingView(true);
            LoginViaMobileRequest loginViaMobileRequest = new LoginViaMobileRequest();
            loginViaMobileRequest.setAction(GET_ACCOUNT_BY_MOBILE);
            loginViaMobileRequest.setAuthkey(BuildConfig.AUTH_KEY);
            loginViaMobileRequest.setMobile(mobile);
            spectraViewModel.getAccountByMobile(loginViaMobileRequest).observe(this, this::consumeResponse);
        }
    }

    public void generate_otp(String mobile) {

        if (Constant.isInternetConnected(this)) {
            SendOtpRequest sendOtpRequest = new SendOtpRequest();
            sendOtpRequest.setAction(SEND_OTP);
            sendOtpRequest.setAuthkey(BuildConfig.AUTH_KEY);
            sendOtpRequest.setMobileNo(mobile);
            spectraViewModel.sendOtp(sendOtpRequest).observe(this, this::consumeResponse);
        }

    }

    @OnClick({R.id.txt_forgot_password, R.id.img_cross, R.id.txt_login_via_username, R.id.btn_login_layout, R.id.btn_login_username_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                if (canID != null) {
                    SpectraApplication.getInstance().postEvent(CATEGORY_LOGIN, "forgot_password", "username" , canID);
                }else{
                    SpectraApplication.getInstance().postEvent(CATEGORY_LOGIN, "forgot_password", "username", "");
                }
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.img_cross:
                clear_error_data();
                break;
            case R.id.txt_login_via_username:
                login_via_username();
                break;
            case R.id.btn_login_layout:
                if (progress_bar != null && progress_bar.getVisibility() != View.VISIBLE) {
                    login_using_mobile(Objects.requireNonNull(input_mobile.getText()).toString().trim());
                }
                break;
            case R.id.btn_login_username_layout:
                hideKey();
                if (!is_error) {
                    if (Objects.requireNonNull(input_username.getText()).toString().isEmpty()) {
                        Constant.MakeToastMessage(LoginActivity.this, getString(R.string.validation_username));
                    } else if (Objects.requireNonNull(input_password.getText()).toString().isEmpty()) {
                        Constant.MakeToastMessage(LoginActivity.this, getString(R.string.validation_password));
                    } else {
                        if (is_valid) {
                            login_using_username();
                        }
                    }
                }
                break;
        }
    }

    public void clear_error_data() {
        is_error = false;
        layout_error.setVisibility(View.GONE);
        txt_enter_username.setVisibility(View.VISIBLE);
        view_name.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        view_pass.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        Objects.requireNonNull(input_username.getText()).clear();
        Objects.requireNonNull(input_password.getText()).clear();
        input_username.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        input_password.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        btn_login_username_layout.setBackground(getResources().getDrawable(R.drawable.login_background));
    }

    public void login_via_username() {
        if (txt_login_via_username.getText().toString().equalsIgnoreCase(getString(R.string.txt_login_via_username))) {
            layout_mobile_username.setVisibility(View.VISIBLE);
            layout_mobile.setVisibility(View.GONE);
            img_login_layout_short.setVisibility(View.VISIBLE);
            img_login_layout.setVisibility(View.GONE);
            txt_login_via_username.setText(getString(R.string.txt_login_via_mobile));
            layout_mobile_username.animate().setDuration(1000);
        } else {
            layout_mobile.setVisibility(View.VISIBLE);
            layout_mobile_username.setVisibility(View.GONE);
            txt_login_via_username.setText(getString(R.string.txt_login_via_username));
            img_login_layout_short.setVisibility(View.GONE);
            img_login_layout.setVisibility(View.VISIBLE);
        }
    }


    public void hideKey() {
        hideKeyboard(LoginActivity.this);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String latestVersion = null;

            try {
                if (progress_bar != null) {
                    Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.spectra.consumer&hl=en").get();
                    latestVersion = doc.getElementsContainingOwnText("Current Version").parents().first().getAllElements().last().text();
                    return latestVersion;
                } else {
                    return latestVersion;
                }
            } catch (Exception e) {
                return latestVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (progress_bar != null && onlineVersion != null && !onlineVersion.isEmpty()) {
                if (compareVersion(BuildConfig.VERSION_NAME, onlineVersion) < 0) {
                    onUpdateNeeded(onlineVersion, "market://details?id=" + BuildConfig.APPLICATION_ID);
                }
            }
        }
    }

    public int compareVersion(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");

        int i = 0;
        while (i < arr1.length || i < arr2.length) {
            if (i < arr1.length && i < arr2.length) {
                if (Integer.parseInt(arr1[i]) < Integer.parseInt(arr2[i])) {
                    return -1;
                } else if (Integer.parseInt(arr1[i]) > Integer.parseInt(arr2[i])) {
                    return 1;
                }
            } else if (i < arr1.length) {
                if (Integer.parseInt(arr1[i]) != 0) {
                    return 1;
                }
            } else {
                if (Integer.parseInt(arr2[i]) != 0) {
                    return -1;
                }
            }

            i++;
        }

        return 0;
    }

    public void onUpdateNeeded(final String onlineVersion, final String updateUrl) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("New Version Alert").setCancelable(false)
                .setMessage("A new version of My Spectra App is available. Update to version " + onlineVersion + ", now!")
                .setPositiveButton("Update",
                        (dialog1, which) -> redirectStore(updateUrl)).create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3E7B8A"));
        });

        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.cancel();
        }
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        if (dialog != null) {
            dialog.cancel();
        }
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


    private void getUpdateToken() {

        if (TextUtils.isEmpty(TOKEN) || canIDS1 == null || canIDS1.size() == 0) {
            return;
        }
        if (Constant.isInternetConnected(this)) {
            UpdateTokenRequest updateTokenRequest = new UpdateTokenRequest();
            updateTokenRequest.setAuthkey(BuildConfig.AUTH_KEY);
            updateTokenRequest.setAction(DEVICE_SIGN_OUT);
            updateTokenRequest.setDeviceData(setDeviceData());
            PlanAndTopupViewModel spectraViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
            spectraViewModel.getDeviceSignOut(updateTokenRequest).observe(LoginActivity.this, LoginActivity.this::consumeResponse);
        }
    }

    public DeviceData setDeviceData() {
        DeviceData deviceData = new DeviceData();
        ArrayList<String> token = new ArrayList<>();
        ArrayList<String> device = new ArrayList<>();
        for (String s : canIDS1) {
            token.add(TOKEN);
            device.add("Android");
        }
        deviceData.setCanIds(canIDS1);
        deviceData.setDeviceToken(token);
        deviceData.setDeviceType(device);
        CurrentUserData userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        userData.linkedIDs = "";
        userData.linkedIDsList = new ArrayList<>();
        DroidPrefs.apply(this, CurrentuserKey, userData);
        return deviceData;
    }

}
