package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
import com.spectra.consumer.service.model.Response.CreateSrResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.lang.reflect.AnnotatedElement;
import java.util.Objects;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_SERVICE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CREATE_SR;

public class CreateSrActivity extends AppCompatActivity {
    @BindView(R.id.input_description)
    AppCompatEditText input_description;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;
    @BindView(R.id.layout_select_issue)
    RelativeLayout layout_select_issue;
    @BindView(R.id.layout_submit)
    AppCompatTextView layout_submit;
    @BindView(R.id.txt_issue_type)
    AppCompatTextView txt_issue_type;
    @BindView(R.id.txt_isu_type)
    AppCompatTextView txt_isu_type;
    private CurrentUserData userData;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    Intent intent;
    private String type, id;
    SpectraViewModel spectraViewModel;
    String canIdAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_sr);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.raise_new_sr));
        userData = DroidPrefs.get(CreateSrActivity.this, Constant.CurrentuserKey, CurrentUserData.class);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        intent = getIntent();
        CAN_ID canIdNik = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        Log.d("Nik Can", canIdNik.baseCanID);
        canIdAnalytics = canIdNik.baseCanID;
        android.util.Log.d("====checking Can ID", canIdAnalytics);
        if (intent != null) {
            id = intent.getStringExtra("id");
            type = intent.getStringExtra("type");
        }
        if (type != null) {
            txt_issue_type.setText(type);
            txt_issue_type.setTextColor(getResources().getColor(R.color.white));
            txt_isu_type.setVisibility(View.VISIBLE);
            if (type.equalsIgnoreCase("My Internet is not working") || type.equalsIgnoreCase("I am getting slow speed")
                    || type.equalsIgnoreCase("I am getting frequent disconnection")) {
                findViewById(R.id.TextInput_description).setVisibility(View.GONE);
            } else {
                findViewById(R.id.TextInput_description).setVisibility(View.VISIBLE);
            }
        } else {
            txt_isu_type.setVisibility(View.GONE);
        }
        input_description.requestFocus();
    }

    public void create_Sr() {
        Constant.hideKeyboard(CreateSrActivity.this);
        if (Constant.isInternetConnected(CreateSrActivity.this)) {
            progress_bar.setVisibility(View.VISIBLE);
            CreateSrRequest createSrRequest = new CreateSrRequest();
            createSrRequest.setAuthkey(BuildConfig.AUTH_KEY);
            createSrRequest.setAction(CREATE_SR);
            createSrRequest.setCanID(userData.CANId);
            createSrRequest.setCaseType(id);
            if (Objects.requireNonNull(input_description.getText()).toString().isEmpty()) {
                createSrRequest.setComment("");
            } else {
                createSrRequest.setComment(input_description.getText().toString());
            }
            spectraViewModel.createSR(createSrRequest).observe(CreateSrActivity.this, CreateSrActivity.this::consumeResponse);
        }
    }

    @SuppressLint("SetTextI18n")
    public void show_back_to_sr(String sr, String type) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_sr_dialog, null);
            AppCompatTextView back_to_sr = v.findViewById(R.id.back_to_sr);
            TextView txt_heading = v.findViewById(R.id.txt_heading);
            if (userData.CancellationFlag != null && userData.CancellationFlag.equalsIgnoreCase("true")) {
                back_to_sr.setText(R.string.txt_logout);
                txt_heading.setText(sr);
            } else {
                if (type.equalsIgnoreCase("fail")) {
                    back_to_sr.setText(R.string.back_to_sr);
                    txt_heading.setText(sr);
                } else {
                    back_to_sr.setText(R.string.back_to_sr);
                    txt_heading.setText(getString(R.string.sr_requested) + " " + sr);
                }
            }
            dialog.setView(v);
            dialog.setCancelable(true);
            final AlertDialog dial = dialog.create();
            dial.show();
            back_to_sr.setOnClickListener(view -> {
                if (userData.CancellationFlag != null && userData.CancellationFlag.equalsIgnoreCase("true")) {
                    dial.dismiss();
                    DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(this);
                    droidPrefs.clear();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    dial.dismiss();
                    finish();
                }
            });
            if (!isFinishing())
                dial.show();
            Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.layout_submit, R.id.img_back, R.id.layout_select_issue})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_submit:
                if (txt_issue_type.getText().toString().equalsIgnoreCase("Issue Type")) {
                    Constant.MakeToastMessage(CreateSrActivity.this, getString(R.string.select_issue_type));
                } else {
                    SpectraApplication.getInstance().postEvent(CATEGORY_SERVICE, "raise_new_service_request", "raise_new_service_request", canIdAnalytics);
                    if (type != null && type.equalsIgnoreCase("My Internet is not working")) {
                        Intent intent = new Intent(CreateSrActivity.this, NoInternet.class);
                        startActivity(intent);
                        finish();
                    } else if (type != null && type.equalsIgnoreCase("I am getting slow speed")) {
                        //TODO
                        Intent intent = new Intent(CreateSrActivity.this, FDSSInternet.class);
                        intent.putExtra("VOC", 3);
                        startActivity(intent);
                        finish();
                    } else if (type != null && type.equalsIgnoreCase("I am getting frequent disconnection")) {
                        //TODO
                        Intent intent = new Intent(CreateSrActivity.this, FDSSInternet.class);
                        intent.putExtra("VOC", 2);
                        startActivity(intent);
                        finish();
                    } else {
                        create_Sr();
                    }
                }
                break;

            case R.id.img_back:
                finish();
                break;
            case R.id.layout_select_issue:
                Intent intent = new Intent(CreateSrActivity.this, SelectcaseTypeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
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
            CreateSrResponse createSrResponse = (CreateSrResponse) response;
            if (createSrResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                String sr_no = createSrResponse.getResponse();
                try {
                    SpectraApplication.getInstance().addKey("sr_number", createSrResponse.getResponse());
                    SpectraApplication.getInstance().addKey("request_type", "type");
                    SpectraApplication.getInstance().addKey("descreption", "");
                    SpectraApplication.getInstance().postEvent(CATEGORY_SERVICE,
                            "raise_new_service_request_Submit",
                            "raise_new_service_request_Submit",canIdAnalytics);
                    show_back_to_sr(sr_no, STATUS_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                show_back_to_sr(createSrResponse.getMessage(), "fail");
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
}
