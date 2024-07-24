package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.CreateOrderRequest;
import com.spectra.consumer.service.model.Request.DisableOrderRequest;
import com.spectra.consumer.service.model.Request.ResponsePaymentAutopayRequest;
import com.spectra.consumer.service.model.Request.SiStatusRequest;
import com.spectra.consumer.service.model.Response.CreateTransactionResponse;
import com.spectra.consumer.service.model.Response.SiStatusResponse;
import com.spectra.consumer.service.model.Response.UpdatePaymentStatusResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;
import org.json.JSONObject;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_FAIl;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CREATE_ORDER;
import static com.spectra.consumer.service.repository.ApiConstant.DISABLE_ORDER;
import static com.spectra.consumer.service.repository.ApiConstant.GET_STATUS_AUTOPAY;
import static com.spectra.consumer.service.repository.ApiConstant.PAGE_ID;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_STATUS_AUTO_PAY;

@SuppressLint("NonConstantResourceId")
public class StandingInstructionsActivity extends AppCompatActivity  implements PaymentResultWithDataListener {

    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.layoutChangeDisable)
    RelativeLayout layoutChangeDisable;
    @BindView(R.id.check_termsAndConditions)
    AppCompatCheckBox check_termsAndConditions;
    @BindView(R.id.layout_content)
    RelativeLayout layout_content;
    @BindView(R.id.setup_Autopay)
    AppCompatTextView setup_Autopay;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_terms)
    AppCompatTextView txt_terms;

    @BindView(R.id.rbCred)
    RadioButton rbCred;

    SpectraViewModel spectraViewModel;
    CurrentUserData user;
    PlanAndTopupViewModel spectraViewModel2;
    String amauntForAutoPay="10";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_standing_instructions);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        user = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        spectraViewModel2 = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        getSiStatus();
    }

    @OnClick({R.id.img_back, R.id.setup_Autopay, R.id.change_si, R.id.disable, R.id.txt_terms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.setup_Autopay:
                checkterms();
                break;
            case R.id.change_si:
                setUpAutopay();
                break;
            case R.id.disable:
                disableAutopay();
                break;
            case R.id.txt_terms:
                showTermsDialog();
                break;
        }
    }

    private void checkterms() {
        if (check_termsAndConditions.isChecked()) {
            setUpAutopay();
        } else {
            Constant.MakeToastMessage(StandingInstructionsActivity.this, getString(R.string.selectTerms));
        }
    }


    private void setPaymentStatus(boolean status) {
        if (status) {
            statusDialog(Constant.STATUS_SUCCESS, "Enable");
        } else {
            statusDialog(STATUS_FAIl, "Enable");
        }
    }

    private void getSiStatus() {
        SiStatusRequest siStatusRequest = new SiStatusRequest();
        siStatusRequest.setAuthkey(BuildConfig.AUTH_KEY);
        siStatusRequest.setAction(GET_STATUS_AUTOPAY);
        siStatusRequest.setBasePlan(user.Product);
        siStatusRequest.setCanID(user.CANId);
        spectraViewModel.getSiStatus(siStatusRequest).observe(StandingInstructionsActivity.this, StandingInstructionsActivity.this::consumeResponse);
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
                case DISABLE_ORDER:
                    CreateTransactionResponse responseData1 = (CreateTransactionResponse) response;
                    if (responseData1.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        statusDialog(Constant.STATUS_SUCCESS, "Disable");
                    } else {
                        statusDialog(STATUS_FAIl, "Disable");
                    }
                    break;
                case CREATE_ORDER:
                    CreateTransactionResponse responseData = (CreateTransactionResponse) response;
                    if (responseData.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        startPayment(responseData.getResponse().getKeyId(),
                                responseData.getResponse().getOrderId(),
                                responseData.getResponse().getCustomerId(),amauntForAutoPay);
                    } else {
                        Constant.MakeToastMessage(this, responseData.getMessage());
                    }
                    break;
                case UPDATE_STATUS_AUTO_PAY:
                    UpdatePaymentStatusResponse paymentStatusResponse = (UpdatePaymentStatusResponse) response;
                    if (paymentStatusResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        setPaymentStatus(paymentStatusResponse.getResponse().getPaymentStatus().equalsIgnoreCase("SUCCESS"));
                    }else {
                        Constant.MakeToastMessage(this, paymentStatusResponse.getMessage());
                    }
                    break;
                case GET_STATUS_AUTOPAY:
                    SiStatusResponse siStatusResponse = (SiStatusResponse) response;
                    if (siStatusResponse.getResponse().getSiStatus().equalsIgnoreCase("Enable")) {
                        check_termsAndConditions.setVisibility(View.GONE);
                        setup_Autopay.setVisibility(View.GONE);
                        layoutChangeDisable.setVisibility(View.VISIBLE);
                        txt_terms.setVisibility(View.GONE);
                    } else {
                        check_termsAndConditions.setVisibility(View.VISIBLE);
                        setup_Autopay.setVisibility(View.VISIBLE);
                        layoutChangeDisable.setVisibility(View.GONE);
                        txt_terms.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
            layout_content.setVisibility(View.GONE);
        } else {
            progress_bar.setVisibility(View.GONE);
            layout_content.setVisibility(View.VISIBLE);
        }
    }

    private void showTermsDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(StandingInstructionsActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.info_item, null);
        ImageView img_cross = view.findViewById(R.id.img_cross);
        WebView web = view.findViewById(R.id.web);
        String url = "https://custappmw.spectra.co/autopay_t_c.html";
        dialog.setView(view);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        web.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress_bar.setVisibility(View.GONE);

            }
        });
        web.loadUrl(url);
        img_cross.setOnClickListener(view1 -> dial.dismiss());
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void statusDialog(String type, String caseType) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_sr_dialog, null);
        AppCompatTextView back_to_sr = v.findViewById(R.id.back_to_sr);
        back_to_sr.setCompoundDrawables(null, null, null, null);
        TextView txt_heading = v.findViewById(R.id.txt_heading);
        if (caseType.equalsIgnoreCase("Enable")) {
            if (type.equalsIgnoreCase(STATUS_SUCCESS)) {
                txt_heading.setText(getString(R.string.autopayEnabled));
            } else {
                txt_heading.setText(getString(R.string.autopayEnabledfail));
            }
        } else {
            if (type.equalsIgnoreCase(STATUS_SUCCESS)) {
                txt_heading.setText(getString(R.string.autopayDisabled));
            } else {
                txt_heading.setText(getString(R.string.autopayEnabledfail));
            }
        }

        back_to_sr.setText(getString(R.string.ok));
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        back_to_sr.setOnClickListener(view -> {
            dial.dismiss();
            switch (caseType) {
                case "Enable":
                    if (type.equalsIgnoreCase(STATUS_SUCCESS)) {
                        Intent intent = new Intent(StandingInstructionsActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        getSiStatus();
                    }
                    break;
                case "Disable":
                    if (type.equalsIgnoreCase(STATUS_SUCCESS)) {
                        getSiStatus();
                    }
                    break;
            }
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setUpAutopay() {
        String paymentType="1";
        if(!rbCred.isChecked()){
            paymentType="2";
        }
        CreateOrderRequest request = new CreateOrderRequest();
        request.setAuthkey(BuildConfig.AUTH_KEY);
        request.setAction(CREATE_ORDER);

        request.setAmount(amauntForAutoPay);
        request.setCustName(user.AccountName);
        request.setEmailId(user.Email);
        request.setMobileNo(user.Number);
        request.setRequestType(paymentType);
        request.setCanId(user.CANId);
        spectraViewModel2.createOrder(request).observe(StandingInstructionsActivity.this, StandingInstructionsActivity.this::consumeResponse);
    }

    public void updateStatusForAutopay(String OrderId, String payableID) {
        ResponsePaymentAutopayRequest request = new ResponsePaymentAutopayRequest();
        request.setAuthkey(BuildConfig.AUTH_KEY);
        request.setAction(UPDATE_STATUS_AUTO_PAY);
        request.setOrderId(OrderId);
        request.setPaymentId(payableID);
        spectraViewModel2.updateStatusForAutopay(request).observe(StandingInstructionsActivity.this, StandingInstructionsActivity.this::consumeResponse);
    }

    private void disableAutopay() {
        DisableOrderRequest disableAutoPayRequest = new DisableOrderRequest();
        disableAutoPayRequest.setAuthkey(BuildConfig.AUTH_KEY);
        disableAutoPayRequest.setAction(DISABLE_ORDER);
        disableAutoPayRequest.setCanId(user.CANId);
        spectraViewModel2.disableOrder(disableAutoPayRequest).observe(StandingInstructionsActivity.this, StandingInstructionsActivity.this::consumeResponse);

    }
    public void startPayment(String key , String orderID  ,String customer_id, String payable_amount) {
        final Activity activity = this;
        final Checkout co = new Checkout();
        int amount=Integer.parseInt(payable_amount);
        if(!rbCred.isChecked()){
            amount=0;
        }
        co.setImage(R.mipmap.app_icon);
        co.setKeyID(key);
        try {
            JSONObject options = new JSONObject();
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            options.put("currency", "INR");
            options.put("amount", ""+100*amount);
            options.put("customer_id", customer_id);
            options.put("name", "Spectra");
            if(amount==0){
                options.put("recurring", 1);
            }
            options.put("theme.color", "#000000");
            options.put("order_id", orderID);



            JSONObject preFill = new JSONObject();
            preFill.put("email", user.Email);
            preFill.put("contact", user.Number);
            preFill.put("name", user.AccountName);
            options.put("prefill", preFill);

            JSONObject notes = new JSONObject();
            notes.put("address", "Gurgaon");
            notes.put("merchant_order_id", orderID);
            options.put("notes", notes);



            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try {
            updateStatusForAutopay(paymentData.getOrderId(),paymentData.getPaymentId());
        } catch (Exception e) {
            android.util.Log.e("TAG", "Exception in onPaymentError", e);
        }
    }
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            setPaymentStatus(false);
        } catch (Exception e) {
            android.util.Log.e("TAG", "Exception in onPaymentSuccess", e);
        }
    }

}
