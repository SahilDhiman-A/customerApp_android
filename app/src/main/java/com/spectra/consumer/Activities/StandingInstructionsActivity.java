package com.spectra.consumer.Activities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.Utils.SiUtils;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.DisableAutoPayRequest;
import com.spectra.consumer.service.model.Request.SiStatusRequest;
import com.spectra.consumer.service.model.Response.DisableAutoPayResponse;
import com.spectra.consumer.service.model.Response.SiStatusResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_FAIl;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.GET_STATUS_AUTOPAY;

public class StandingInstructionsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.layoutChangeDisable)
    RelativeLayout layoutChangeDisable;
    @BindView(R.id.check_termsAndConditions)
    AppCompatCheckBox check_termsAndConditions;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.layout_content)
    RelativeLayout layout_content;
    @BindView(R.id.setup_Autopay)
    AppCompatTextView setup_Autopay;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_terms)
    AppCompatTextView txt_terms;
    SpectraViewModel spectraViewModel;
    CurrentUserData user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_standing_instructions);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        user=DroidPrefs.get(this,CurrentuserKey,CurrentUserData.class);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        getSiStatus();
    }

    @OnClick({R.id.img_back,R.id.setup_Autopay,R.id.change_si,R.id.disable,R.id.txt_terms})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                if(webview.isShown()){
                    webview.setVisibility(View.GONE);
                    layout_content.setVisibility(View.VISIBLE);
                }
                else{
                    onBackPressed();
                }
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
    private void checkterms(){
        if(check_termsAndConditions.isChecked()){
             setUpAutopay();
        }
        else{
            Constant.MakeToastMessage(StandingInstructionsActivity.this,getString(R.string.selectTerms));
        }
    }
    private void setUpAutopay(){
        CurrentUserData userData= DroidPrefs.get(this, CurrentuserKey,CurrentUserData.class);
        webview.setVisibility(View.VISIBLE);
        layout_content.setVisibility(View.GONE);
        webview.setWebViewClient(new WebviewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.postUrl(SiUtils.URL, SiUtils.getPaymentParam("10",userData.CANId,"1"));
    }
    private class WebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           // webview.loadUrl(url);
          setPaymentStatus(url);
            return true;

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("urlResponse", error.toString());
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }
    private void setPaymentStatus(String url){
        String text;
        Log.d("urlResponse",url);

        if(TextUtils.isEmpty(url)){
            return;
        }


        if(!url.contains("https://my.spectra.co/index.php/xml/sipayment?result=")){
            webview.loadUrl(url);
        }
        else {
            url = url.replace("https://my.spectra.co/index.php/xml/sipayment?result=", "");
            try {
                text = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            Gson gson = new Gson();
            PaymentRespons paymentRespons= gson.fromJson(text,PaymentRespons.class);
            if (paymentRespons.paymentStaus) {
                statusDialog(Constant.STATUS_SUCCESS,"Enable");
                webview.destroy();
                webview.setVisibility(View.GONE);
            } else {

                statusDialog(STATUS_FAIl,"Enable");
                webview.destroy();
                webview.setVisibility(View.GONE);

            }
        }
    }
    private void getSiStatus(){
        SiStatusRequest siStatusRequest=new SiStatusRequest();
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
                renderSuccessResponse(apiResponse.data,apiResponse.code);
                showLoadingView(false);
                break;
            case ERROR:
                showLoadingView(false);
                break;
            default:
                break;
        }
    }
    private void renderSuccessResponse(Object response,String code) {
        if (response != null) {

            switch (code){
                case "0":
                    DisableAutoPayResponse disableAutoPayResponse= (DisableAutoPayResponse) response;
                    if(disableAutoPayResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)){
                        statusDialog(Constant.STATUS_SUCCESS,"Disable");
                    }
                    else{
                        statusDialog(STATUS_FAIl,"Disable");
                    }
                    break;
                case GET_STATUS_AUTOPAY:
                    SiStatusResponse siStatusResponse= (SiStatusResponse) response;
                    if(siStatusResponse.getResponse().getSiStatus().equalsIgnoreCase("Enable")){
                        check_termsAndConditions.setVisibility(View.GONE);
                        setup_Autopay.setVisibility(View.GONE);
                        layoutChangeDisable.setVisibility(View.VISIBLE);
                        txt_terms.setVisibility(View.GONE);
                    }
                    else{
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
    private void showTermsDialog(){
        AlertDialog.Builder dialog=new AlertDialog.Builder(StandingInstructionsActivity.this);
        View view= LayoutInflater.from(this).inflate(R.layout.info_item,null);
        ImageView img_cross=view.findViewById(R.id.img_cross);
        WebView web=view.findViewById(R.id.web);
        String url="https://custappmw.spectra.co/autopay_t_c.html";
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
    private void statusDialog(String type,String caseType){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_sr_dialog, null);
        AppCompatTextView back_to_sr= v.findViewById(R.id.back_to_sr);
        back_to_sr.setCompoundDrawables(null,null,null,null);
        TextView txt_heading=v.findViewById(R.id.txt_heading);
        if(caseType.equalsIgnoreCase("Enable")){
            if(type.equalsIgnoreCase(STATUS_SUCCESS)){
                txt_heading.setText(getString(R.string.autopayEnabled));
            }
            else{
                txt_heading.setText(getString(R.string.autopayEnabledfail));
            }
        }
        else{
            if(type.equalsIgnoreCase(STATUS_SUCCESS)){
                txt_heading.setText(getString(R.string.autopayDisabled));
            }
            else{
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
            switch (caseType){
                case "Enable":
                    if(type.equalsIgnoreCase(STATUS_SUCCESS)){
                        Intent intent=new Intent(StandingInstructionsActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        getSiStatus();
                    }
                    break;
                case "Disable":
                    if(type.equalsIgnoreCase(STATUS_SUCCESS)){
                        getSiStatus();
                    }
                    break;
            }
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public class PaymentRespons implements Serializable
    {

        @SerializedName("errorCode")
        @Expose
        private String errorCode;
        @SerializedName("errorMsg")
        @Expose
        private String errorMsg;
        @SerializedName("paymentStaus")
        @Expose
        private Boolean paymentStaus;
        @SerializedName("pgRefNo")
        @Expose
        private String pgRefNo;
        @SerializedName("paymentAmount")
        @Expose
        private Integer paymentAmount;
        @SerializedName("StandardInstrunction")
        @Expose
        private String standardInstrunction;
        private final static long serialVersionUID = 31595700692743928L;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public Boolean getPaymentStaus() {
            return paymentStaus;
        }

        public void setPaymentStaus(Boolean paymentStaus) {
            this.paymentStaus = paymentStaus;
        }

        public String getPgRefNo() {
            return pgRefNo;
        }

        public void setPgRefNo(String pgRefNo) {
            this.pgRefNo = pgRefNo;
        }

        public Integer getPaymentAmount() {
            return paymentAmount;
        }

        public void setPaymentAmount(Integer paymentAmount) {
            this.paymentAmount = paymentAmount;
        }

        public String getStandardInstrunction() {
            return standardInstrunction;
        }

        public void setStandardInstrunction(String standardInstrunction) {
            this.standardInstrunction = standardInstrunction;
        }

    }
    private void disableAutopay(){
        DisableAutoPayRequest disableAutoPayRequest=new DisableAutoPayRequest();
        disableAutoPayRequest.setSecretKey(SiUtils.KEY);
        disableAutoPayRequest.setBillAmount("10");
        disableAutoPayRequest.setCanID(user.CANId);
        disableAutoPayRequest.setRequetType("0");
        disableAutoPayRequest.setReturnUrl("returnUrl");
        spectraViewModel.disableAutoPay(disableAutoPayRequest).observe(StandingInstructionsActivity.this, StandingInstructionsActivity.this::consumeResponse);

    }
}
