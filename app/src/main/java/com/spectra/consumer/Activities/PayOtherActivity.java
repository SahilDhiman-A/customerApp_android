package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.GetAccountDataRequest;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.service.model.Response.LoginViaMobileResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import activeandroid.PaymentUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.CommonUtils.saveUser;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.hideKeyboard;
import static com.spectra.consumer.service.repository.ApiConstant.GET_ACCOUNT_DATA;

public class PayOtherActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    AppCompatImageView imgBack;
    @BindView(R.id.txt_head)
    TextView txtHead;
    @BindView(R.id.txt_share)
    TextView txtShare;
    @BindView(R.id.toolbar_head)
    Toolbar toolbarHead;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.txtUpdateHeading)
    AppCompatTextView txtUpdateHeading;
    @BindView(R.id.btn_update)
    FloatingActionButton btnUpdate;
    @BindView(R.id.btn_updateLayout)
    RelativeLayout btnUpdateLayout;
    @BindView(R.id.input_update_field)
    TextInputEditText inputUpdateField;
    @BindView(R.id.tiL)
    TextInputLayout tiL;
    @BindView(R.id.llPayDialog)
    RelativeLayout llPayDialog;
    @BindView(R.id.etCanID)
    TextInputEditText etCanID;
    @BindView(R.id.inlCanID)
    TextInputLayout inlCanID;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.inlName)
    TextInputLayout inlName;
    @BindView(R.id.etPayAmount)
    TextInputEditText etPayAmount;
    @BindView(R.id.inlPayAmount)
    TextInputLayout inlPayAmount;
    @BindView(R.id.tvPay)
    AppCompatTextView tvPay;
    @BindView(R.id.layout_plan)
    CardView layoutPlan;
    @BindView(R.id.no_internet)
    LinearLayout no_internet;

    String canID = "", mobile="", email="";
    String amount = "";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_bill);
        ButterKnife.bind(this);
        context = PayOtherActivity.this;
        txtHead.setText("Pay Bill");
        layoutPlan.setVisibility(View.GONE);
    }

    private void getAccountDetails(String canID) {

        if (Constant.isInternetConnected(context)) {
            no_internet.setVisibility(View.GONE);
            GetAccountDataRequest getAccountDataRequest = new GetAccountDataRequest();
            getAccountDataRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getAccountDataRequest.setAction(GET_ACCOUNT_DATA);
            getAccountDataRequest.setCanID(canID);
            this.canID = canID;
            SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            spectraViewModel.getAccountByCanId(getAccountDataRequest).observe(this, this::consumeResponse);
        } else {
            no_internet.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
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
                hideKeyboard(PayOtherActivity.this);
                break;
            case ERROR:
                Constant.MakeToastMessage(PayOtherActivity.this, apiResponse.error.getMessage());
                showLoadingView(false);
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response) {
        if (response != null) {
            LoginViaMobileResponse loginViaMobileResponse = (LoginViaMobileResponse) response;
            if (loginViaMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && loginViaMobileResponse.response != null) {
                List<LoginMobileResponse> loginMobileResponses = loginViaMobileResponse.response;
                if (loginMobileResponses.size() > 0) {
                    LoginMobileResponse data = loginMobileResponses.get(0);
                    setUserData(data);
                }

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

    @SuppressLint("SetTextI18n")
    private void setUserData(LoginMobileResponse data) {
            email=data.getEmail();
            mobile=data.getMobile();
            etCanID.setText(data.getCANId());
            String name=data.getAccountName();
            etName.setText(changeText(name));
            etPayAmount.setText("₹");
            layoutPlan.setVisibility(View.VISIBLE);
            llPayDialog.setVisibility(View.GONE);
            etPayAmount.setText("₹ ");
            etPayAmount.setSelection(2);
            amount = data.getOutStandingAmount();
         etPayAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPayAmount.length() == 1) {
                    etPayAmount.setText("₹ ");
                    etPayAmount.setSelection(2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public String changeText(String str){
        StringBuilder name= new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
          if(i==0||i==str.length()-1) {
              name.append(str.charAt(i));
          }else {
              String s=""+str.charAt(i);
              if (s.equals(" ")) {
                  name.append(" ");
              }else {
                  name.append("*");
              }
          }
        }
        return name.toString();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @OnClick({R.id.img_back, R.id.tvPay, R.id.btn_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tvPay:
                amount= (etPayAmount.getText()).toString().replace("₹ ","");
                if (!TextUtils.isEmpty(amount)) {
                    double amountValue = Double.parseDouble(amount);
                    if (amountValue > 0) {
                        Intent intent_pay = new Intent(PayOtherActivity.this, PayNowActivity.class);
                        intent_pay.putExtra("payableAamount", amount);
                        intent_pay.putExtra("email", email);
                        intent_pay.putExtra("mobile", mobile);
                        intent_pay.putExtra("type", "unpaid");
                        intent_pay.putExtra("subType", "normal");
                        intent_pay.putExtra("canID", canID);
                        //Nikhil- Commented For payment activity was not opening
//                        intent_pay.putExtra("ComeFrom", "home");
                        startActivity(intent_pay);
                    } else {
                       Constant.MakeToastMessage(context, "Payable amount can't be 0");
                    }
                }
                else {
                    Constant.MakeToastMessage(context, "Payable amount can't be 0");
                }
                break;
            case R.id.btn_update:
                if (TextUtils.isEmpty(inputUpdateField.getText())) {
                    Constant.MakeToastMessage(PayOtherActivity.this, "CanID can't be blank");
                    return;
                }
                if (inputUpdateField.getText().length() < 5) {
                    Constant.MakeToastMessage(PayOtherActivity.this, "Please enter valid CAN Id");
                    return;
                }
                getAccountDetails(inputUpdateField.getText().toString());
                break;
        }
    }
}
