package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.ForgotPasswordRequest;
import com.spectra.consumer.service.model.Response.ForgotPasswordResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.FORGOT_PASSWORD;

public class ForgotPasswordActivity extends AppCompatActivity {
    @BindView(R.id.input_canid)
    TextInputEditText input_canid;
    @BindView(R.id.img_question)
    ImageView img_question;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.txt_enter_can_username)
    TextView txt_enter_can_username;
    @BindView(R.id.txt_can)
    TextView txt_can;
    @BindView(R.id.view_can)
    View view_can;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.view_username)
    View view_username;
    @BindView(R.id.input_username)
    TextInputEditText input_username;
    @BindView(R.id.input_layout_can_id)
    TextInputLayout input_layout_can_id;
    @BindView(R.id.input_layout_username)
    TextInputLayout input_layout_username;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    SpectraViewModel spectraViewModel;
    String type;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);
        ButterKnife.bind(this);
        can_selected();
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);

    }
    public void can_selected(){
        txt_can.setTextColor(getResources().getColor(R.color.back_color));
        view_can.setBackgroundColor(getResources().getColor(R.color.back_color));
        view_username.setVisibility(View.GONE);
        view_can.setVisibility(View.VISIBLE);
        input_layout_can_id.setVisibility(View.VISIBLE);
        input_layout_username.setVisibility(View.GONE);
        txt_user_name.setTextColor(getResources().getColor(R.color.not_selected_color));
        img_question.setVisibility(View.VISIBLE);
        type="canID";
        txt_enter_can_username.setText(getString(R.string.txt_enter_registered_canid));
    }
    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                showLoadingView(false);
                break;
            case ERROR:
                showLoadingView(false);
                break;
            default:
                break;
        }
    }
    private void renderSuccessResponse(Object response) {
        if (response != null) {
            ForgotPasswordResponse forgotPasswordResponse= (ForgotPasswordResponse) response;
            if(forgotPasswordResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)){
                show_back_to_login();
            }
            else{
                Constant.MakeToastMessage(ForgotPasswordActivity.this,forgotPasswordResponse.getMessage());
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
    public void submit_details(){

        if(!checkValidation()){
            return;
        }
        if(Constant.isInternetConnected(this)){
            ForgotPasswordRequest forgotPasswordRequest=new ForgotPasswordRequest();
            forgotPasswordRequest.setAuthkey(BuildConfig.AUTH_KEY);
            forgotPasswordRequest.setAction(FORGOT_PASSWORD);
            if(type.equalsIgnoreCase("canID")) {
                forgotPasswordRequest.setCanID(Objects.requireNonNull(input_canid.getText()).toString().trim());
                forgotPasswordRequest.setUsername("");
            }
            else{
                forgotPasswordRequest.setCanID("");
                forgotPasswordRequest.setUsername(Objects.requireNonNull(input_username.getText()).toString().trim());
            }
            spectraViewModel.forgotPassword(forgotPasswordRequest).observe(ForgotPasswordActivity.this, ForgotPasswordActivity.this::consumeResponse);
        }
    }
    public void show_back_to_login(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_login_dialog, null);
        AppCompatTextView txt_login= v.findViewById(R.id.txt_login);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        txt_login.setOnClickListener(view -> {
            dial.dismiss();
            finish();
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public boolean checkValidation(){
        if(type.equals("canID"))
        {
            if(TextUtils.isEmpty(Objects.requireNonNull(input_canid.getText()).toString().trim())){
                Constant.MakeToastMessage(ForgotPasswordActivity.this,getString(R.string.please_enter_can));
                return false;
            }else {
                return true;
            }
        }else {
            if(TextUtils.isEmpty(Objects.requireNonNull(input_username.getText()).toString().trim())){
                Constant.MakeToastMessage(ForgotPasswordActivity.this,getString(R.string.validation_username));
                return false;
            }
            else {
                return true;
            }
        }
    }

    @OnClick({R.id.layout_submit,R.id.layout_select_canid,R.id.layout_select_username,R.id.img_question,R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_submit:
                submit_details();
                break;
            case R.id.layout_select_canid:
                can_selected();
                break;
            case R.id.layout_select_username:
                username_tab_selected();
                break;
            case R.id.img_question:
                can_id_information_dialog();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
    public void username_tab_selected(){
        txt_user_name.setTextColor(getResources().getColor(R.color.back_color));
        view_username.setBackgroundColor(getResources().getColor(R.color.back_color));
        view_can.setVisibility(View.GONE);
        view_username.setVisibility(View.VISIBLE);
        input_layout_username.setVisibility(View.VISIBLE);
        input_layout_can_id.setVisibility(View.GONE);
        txt_can.setTextColor(getResources().getColor(R.color.not_selected_color));
        img_question.setVisibility(View.GONE);
        type="username";
        txt_enter_can_username.setText(getString(R.string.txt_enter_registered_username));
    }
    public void can_id_information_dialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ForgotPasswordActivity.this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.can_id_info, null);
        AppCompatImageView img_cross= v.findViewById(R.id.img_cross);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        img_cross.setOnClickListener(vw -> dial.dismiss());
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
