package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Response.AddTopUpResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import activeandroid.PaymentUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Activities.TopUpActivity.topUpResponse;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.ADD_TOPUP;

public class PayNowActivity extends AppCompatActivity {
    @BindView(R.id.outstanding_amt)
    AppCompatTextView outstanding_amt;
    @BindView(R.id.tvTitleOutstanding)
    AppCompatTextView tvTitleOutstanding;

    @BindView(R.id.input_layout_amount_to_pay)
    TextInputLayout input_layout_amount_to_pay;

    @BindView(R.id.input_layout_amount_to_pay2)
    TextInputLayout input_layout_amount_to_pay2;
    @BindView(R.id.input_amount2)
    TextInputEditText input_amount2;
    @BindView(R.id.input_amount)
    TextInputEditText input_amount;
    @BindView(R.id.input_tds)
    TextInputEditText input_tds;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    String payable_amount;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.llcontent)
    LinearLayout llcontent;
    Context context;
    @BindView(R.id.paymentDone)
    LinearLayout paymentDone;
    @BindView(R.id.paymentFail)
    LinearLayout paymentFail;
    @BindView(R.id.paymentAmount)
    TextView paymentAmount;
    @BindView(R.id.input_layout_tds)
    TextInputLayout input_layout_tds;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    CurrentUserData currentUserData;
    Intent intent;
    String type, tds_amt, tds_slab, canId, email, mobile;
    double tds_value;
    String tds_value_amount;
    String subType;
    @BindView(R.id.txt_isu_type)
    TextView txt_isu_type;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_paynow);
        ButterKnife.bind(this);
        currentUserData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);
        intent = getIntent();
        type = intent.getStringExtra("type");
        email = intent.getStringExtra("email");
        mobile = intent.getStringExtra("mobile");
        subType = intent.getStringExtra("subType");
        context = PayNowActivity.this;
        canId = intent.getStringExtra("canID");
        payable_amount = intent.getStringExtra("payableAamount");
        if (!intent.hasExtra("ComeFrom")) {
            if (currentUserData.Segment.equalsIgnoreCase("home")) {
                input_layout_tds.setVisibility(View.GONE);
                txt_isu_type.setVisibility(View.GONE);
                if (subType.equalsIgnoreCase("topup")) {
                    input_layout_amount_to_pay2.setVisibility(View.VISIBLE);
                    input_layout_amount_to_pay.setVisibility(View.GONE);
                    if (intent.hasExtra("come")) {
                        input_layout_amount_to_pay2.setVisibility(View.GONE);
                        input_layout_amount_to_pay.setVisibility(View.VISIBLE);
                        input_layout_amount_to_pay.setEnabled(false);
                    }

                    input_amount2.setEnabled(false);
                    tvTitleOutstanding.setText("Payable Amount");
                } else {
                    input_amount.setEnabled(true);
                }
            } else {
                if (type != null) {
                    if (type.equalsIgnoreCase("tds")) {
                        tds_amt = intent.getStringExtra("tdsAmount");
                        tds_slab = intent.getStringExtra("tdsSlab");
                        tds_value = Double.parseDouble(tds_amt);
                        tds_value_amount = "" + Constant.Round(Float.parseFloat(tds_amt), 2);
                        input_tds.setText(tds_value_amount);
                        input_layout_tds.setVisibility(View.VISIBLE);
                        txt_isu_type.setVisibility(View.VISIBLE);
                        txt_isu_type.setText(getString(R.string.enter_tds) + " " + tds_slab + " " + getString(R.string.value_percent));
                    } else {
                        input_layout_tds.setVisibility(View.GONE);
                        txt_isu_type.setVisibility(View.GONE);
                    }

                }
                if (subType.equalsIgnoreCase("topup")) {
                    input_amount.setEnabled(false);
                } else {
                    input_amount.setEnabled(true);
                }

            }
            if (payable_amount != null) {
                payable_amount = "" + Constant.Round(Float.parseFloat(payable_amount), 2);
                if (payable_amount.equals("0.00")) {
                    payable_amount = "0";
                }
                outstanding_amt.setText(payable_amount);
                input_amount.setText(payable_amount);
                input_amount2.setText(payable_amount);
            }
            input_amount.setSelection(Objects.requireNonNull(input_amount.getText()).length());
            input_amount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});
            input_tds.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});
            tds_value_amount = input_tds.getText().toString();

        }
        else {
            input_layout_amount_to_pay.setVisibility(View.GONE);
            input_layout_amount_to_pay2.setVisibility(View.GONE);
            input_layout_tds.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            llcontent.setVisibility(View.GONE);
            doPayment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        if (!intent.hasExtra("ComeFrom") && webview.getVisibility() == View.VISIBLE) {
            webview.destroy();
            webview.setVisibility(View.GONE);
            llcontent.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void doPayment() {
        if (input_layout_amount_to_pay.getVisibility() == View.VISIBLE) {
            payable_amount = Objects.requireNonNull(input_amount.getText()).toString();
        } else {
            if (input_layout_amount_to_pay2.getVisibility() == View.VISIBLE) {
                payable_amount = Objects.requireNonNull(input_amount2.getText()).toString();
            }
        }
        double td = 0.0;
        double amount;
        if (TextUtils.isEmpty(payable_amount)) {
            Constant.MakeToastMessage(PayNowActivity.this, getString(R.string.amount_cannot_be_empty));
            return;
        }
        try {
            if (!TextUtils.isEmpty(tds_value_amount)) {
                td = Double.parseDouble(tds_value_amount);
            }
            amount = Double.parseDouble(payable_amount); // Make use of autoboxing.  It's also easier to read.

            if (amount == 0) {
                Constant.MakeToastMessage(PayNowActivity.this, getString(R.string.amount_cannot_be_0));
                return;
            }
            if (td > tds_value) {
                Constant.MakeToastMessage(PayNowActivity.this, getString(R.string.amountExceededLimit));
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        webview.setVisibility(View.VISIBLE);
        llcontent.setVisibility(View.GONE);
        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        if (TextUtils.isEmpty(canId)) {
            canId = currentUserData.CANId;
        }
        webview.postUrl(PaymentUtils.URL, PaymentUtils.getPaymentParam(payable_amount, tds_value_amount, canId, mobile, email));


    }

    @OnClick({R.id.img_back, R.id.btn_proceed, R.id.backToHomeSucsess, R.id.backToHomeFail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_proceed:
                doPayment();
                break;
            case R.id.backToHomeSucsess:
                onBackPressed();
                break;
            case R.id.backToHomeFail:
                onBackPressed();
                break;
        }
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {


                setPaymentStatus(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);

        }
    }

    private void setPaymentStatus(String url) {
        try {
            String text;
            if (!url.contains("https://epay.spectra.co/onlinepayment/returnurl?passkey=")) {
                return;
            }
            url = url.replace("https://epay.spectra.co/onlinepayment/returnurl?passkey=", "");
            byte[] data = Base64.decode(url, Base64.DEFAULT);
            text = new String(data, StandardCharsets.UTF_8);
            if (text.contains("status=SUCCESS")) {

                webview.destroy();
                webview.setVisibility(View.GONE);
                try {
                    String[] output = text.split("amount=");
                    String value = output[1].split("&")[0];
                    paymentAmount.setText(MessageFormat.format("{0} {1}", getString(R.string.payment_amount), value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (subType.equalsIgnoreCase("topup")) {
                    addTopUpToInVoice(topUpResponse);
                } else {
                    paymentDone.setVisibility(View.VISIBLE);
                    paymentFail.setVisibility(View.GONE);
                }

            } else {
                webview.destroy();
                webview.setVisibility(View.GONE);
                paymentDone.setVisibility(View.GONE);
                paymentFail.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

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
                Constant.MakeToastMessage(PayNowActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case ADD_TOPUP:
                    AddTopUpResponse addTopUpResponse = (AddTopUpResponse) response;
                    if (addTopUpResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        paymentDone.setVisibility(View.VISIBLE);
                    }
                    Constant.MakeToastMessage(PayNowActivity.this, addTopUpResponse.getMessage());

                    break;
            }
        }
    }

    private void addTopUpToInVoice(TopUpResponse topUpResponse) {
        if (Constant.isInternetConnected(this)) {
            SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            CurrentUserData user = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
            AddTopUpRequest addTopUpRequest = new AddTopUpRequest();
            addTopUpRequest.setAuthkey(BuildConfig.AUTH_KEY);
            addTopUpRequest.setAction(ADD_TOPUP);
            addTopUpRequest.setAmount(topUpResponse.getPg_price());
            addTopUpRequest.setCanID(user.CANId);
            addTopUpRequest.setTopupName(topUpResponse.getTopup_name());
            addTopUpRequest.setTopupType(topUpResponse.getType());
            spectraViewModel.addTopUp(addTopUpRequest).observe(PayNowActivity.this, PayNowActivity.this::consumeResponse);
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
