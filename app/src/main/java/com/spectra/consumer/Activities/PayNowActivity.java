package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.CreateTransactionRequest;
import com.spectra.consumer.service.model.Request.ResponsePaymentStatusRequest;
import com.spectra.consumer.service.model.Response.AddTopUpResponse;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.CreateTransactionResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.service.model.Response.UpdatePaymentStatusResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Activities.CangePlanSelectionActivity.currentPlan;
import static com.spectra.consumer.Activities.CangePlanSelectionActivity.newPlan;
import static com.spectra.consumer.BuildConfig.BUILD_SEGMENT;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_CHANGE_PLANE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.ADD_TOPUP;
import static com.spectra.consumer.service.repository.ApiConstant.CHANGE_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.CREATE_TRANSACTION;
import static com.spectra.consumer.service.repository.ApiConstant.PAGE_ID;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_PAYMENT_STATUS;

@SuppressLint("NonConstantResourceId")
public class PayNowActivity extends AppCompatActivity implements PaymentResultWithDataListener {

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
    private String packageID;
    @BindView(R.id.txt_isu_type)
    TextView txt_isu_type;
    private TopUpResponse mTopUpResponse;
    Snackbar snackbar;
    Checkout co;
    private boolean isTopUpActivity = false;
    SpectraViewModel spectraViewModel;
    PlanAndTopupViewModel spectraViewModel2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_paynow);
        ButterKnife.bind(this);
        Checkout.preload(getApplicationContext());
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        spectraViewModel2 = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        currentUserData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);
        intent = getIntent();
        type = intent.getStringExtra("type");
        email = intent.getStringExtra("email");
        mobile = intent.getStringExtra("mobile");
        subType = intent.getStringExtra("subType");
        packageID = intent.getStringExtra("packageID");

        if (intent.getExtras() != null && intent.getExtras().containsKey("DATA")) {
            mTopUpResponse = (TopUpResponse) intent.getExtras().getSerializable("DATA");
        }

        context = PayNowActivity.this;
        canId = intent.getStringExtra("canID");
        isTopUpActivity = intent.hasExtra("TopUpActivity");
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
                } else input_amount.setEnabled(!subType.equalsIgnoreCase("changePlan"));
            }
            if (payable_amount != null) {
                payable_amount = "" + Constant.Round(Float.parseFloat(payable_amount), 2);
                if (payable_amount.equals("0.00")) {
                    payable_amount = "0";
                }
                outstanding_amt.setText("₹ " + payable_amount);
                input_amount.setText(payable_amount);
                input_amount2.setText(payable_amount);
            }
            input_amount.setSelection(Objects.requireNonNull(input_amount.getText()).length());
            input_amount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});
            input_tds.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(6, 2)});
            tds_value_amount = Objects.requireNonNull(input_tds.getText()).toString();
        } else {

            // startPayment();
            doPayment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
//            Log.d("pay_in_advance", "doPayment: check");
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

        if (TextUtils.isEmpty(canId)) {
            canId = currentUserData.CANId;
        }

        createTransaction(payable_amount, canId, mobile, email);
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
            case R.id.backToHomeFail:
                onBackPressed();
                break;
        }
    }


    private void setPaymentStatus(boolean status, String value) {
        try {
            if (status) {
                try {
                    paymentAmount.setText(MessageFormat.format("{0} {1}", getString(R.string.payment_amount), value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (subType.equalsIgnoreCase("topup")) {
                    addTopUpToInVoice(mTopUpResponse);
                } else if (subType.equalsIgnoreCase("changePlan")) {
                    showSnackbar(true);
                    select_package(packageID);
                } else {
                    paymentDone.setVisibility(View.VISIBLE);
                    paymentFail.setVisibility(View.GONE);
                }
            } else {
                if (subType.equalsIgnoreCase("changePlan")) {
                    SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_failed", "change_plan_failed",canId);
                }
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
                assert apiResponse.error != null;
                Constant.MakeToastMessage(PayNowActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case CREATE_TRANSACTION:
                    CreateTransactionResponse responseData = (CreateTransactionResponse) response;
                    if (responseData.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        startPayment(responseData.getResponse().getKeyId(),
                                responseData.getResponse().getOrderId(),
                                payable_amount, mobile, email);

                    } else {
                        Constant.MakeToastMessage(PayNowActivity.this, responseData.getMessage());
                    }
                    break;
                case UPDATE_PAYMENT_STATUS:
                    UpdatePaymentStatusResponse paymentStatusResponse = (UpdatePaymentStatusResponse) response;
                    if (paymentStatusResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        setPaymentStatus(paymentStatusResponse.getResponse().getPaymentStatus().equalsIgnoreCase("SUCCESS"), payable_amount);
                    } else {
                        Constant.MakeToastMessage(PayNowActivity.this, paymentStatusResponse.getMessage());
                    }

                    break;

                case ADD_TOPUP:
                    AddTopUpResponse addTopUpResponse = (AddTopUpResponse) response;
                    if (addTopUpResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        if (isTopUpActivity) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            paymentDone.setVisibility(View.VISIBLE);
                        }
                    }
                    Constant.MakeToastMessage(PayNowActivity.this, addTopUpResponse.getMessage());
                    break;

                case CHANGE_PLAN:
                    showSnackbar(false);
                    ChangePlanResponse changePlanResponse = (ChangePlanResponse) response;
                    try {
                        if (changePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                            if (currentPlan != null && newPlan != null) {
                                try {
                                    SpectraApplication.getInstance().addKey("current_plan_data", currentPlan.getData());
                                    SpectraApplication.getInstance().addKey("current_plan_speed", currentPlan.getSpeed());
                                    SpectraApplication.getInstance().addKey("current_plan_frequency", currentPlan.getFrequency());
                                    SpectraApplication.getInstance().addKey("new_plan_charges", newPlan.getCharges());
                                    SpectraApplication.getInstance().addKey("new_plan_data", newPlan.getCharges());
                                    SpectraApplication.getInstance().addKey("new_plan_speed", newPlan.getSpeed());
                                    SpectraApplication.getInstance().addKey("new_plan_frequency", newPlan.getFrequency());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_success", "change_plan_success",canId);
                        } else {
                            SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_failed", "change_plan_failed",canId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    plan_change_request_raised(changePlanResponse.getMessage(), changePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS));
                    break;
            }
        }
    }

    private void addTopUpToInVoice(TopUpResponse topUpResponse) {
        if (Constant.isInternetConnected(this) && mTopUpResponse != null) {
            SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            CurrentUserData user = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
            AddTopUpRequest addTopUpRequest = new AddTopUpRequest();
            addTopUpRequest.setAuthkey(BuildConfig.AUTH_KEY);
            addTopUpRequest.setAction(ADD_TOPUP);
            addTopUpRequest.setAmount(topUpResponse.getPrice());
            addTopUpRequest.setCanID(user.CANId);
            addTopUpRequest.setTopupName(topUpResponse.getTopup_name());
            addTopUpRequest.setTopupType(topUpResponse.getType());
            spectraViewModel.addTopUp(addTopUpRequest).observe(PayNowActivity.this, PayNowActivity.this::consumeResponse);
        }
    }

    private void select_package(String pkgName) {
        if (Constant.isInternetConnected(this)) {
            PlanAndTopupViewModel planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);

            ChangePlanRequest changePlanRequest = new ChangePlanRequest();
            changePlanRequest.setAuthkey(BuildConfig.AUTH_KEY);
            changePlanRequest.setAction(CHANGE_PLAN);
            changePlanRequest.setCanId(currentUserData.CANId);
            changePlanRequest.setPkgName(pkgName);
            planAndTopupViewModel.changePlan(changePlanRequest).observe(PayNowActivity.this, PayNowActivity.this::consumeResponse);
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showSnackbar(boolean visible) {
        if (visible) {
            snackbar = Snackbar.make(progressBar, R.string.payment_successful, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else {
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    }

    public void plan_change_request_raised(String message, boolean b) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_sr_dialog, null);
        AppCompatTextView back_to_sr = v.findViewById(R.id.back_to_sr);
        TextView txt_heading = v.findViewById(R.id.txt_heading);
        txt_heading.setText(message);
        if (b) {
            back_to_sr.setText("back to home");
        } else back_to_sr.setText(getString(R.string.back));

        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        back_to_sr.setOnClickListener(view -> {
            dial.dismiss();
            if (b) {
                Intent intent = new Intent(PayNowActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                onBackPressed();
            }
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void createTransaction(String payable_amount, String canId, String mobile, String email) {
        if (Constant.isInternetConnected(this)) {
            CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
            createTransactionRequest.setAuthkey(BuildConfig.AUTH_KEY);
            createTransactionRequest.setAction(CREATE_TRANSACTION);
            createTransactionRequest.setCanId(canId);
            createTransactionRequest.setAmount(payable_amount);
            createTransactionRequest.setEmailId(email);
            createTransactionRequest.setMobileNo(mobile);
            createTransactionRequest.setPgId(PAGE_ID);
            createTransactionRequest.setRequestType(BUILD_SEGMENT);
            String textID = canId + System.currentTimeMillis();
            createTransactionRequest.setTxnId(textID);
            spectraViewModel2.createTransaction(createTransactionRequest).observe(PayNowActivity.this, PayNowActivity.this::consumeResponse);
        }
    }

    public void updatePaymentStatus(String orderId) {
        if (Constant.isInternetConnected(this)) {
            ResponsePaymentStatusRequest request = new ResponsePaymentStatusRequest();
            request.setAuthkey(BuildConfig.AUTH_KEY);
            request.setAction(UPDATE_PAYMENT_STATUS);
            request.setOrderId(orderId);
            spectraViewModel2.updatePaymentStatus(request).observe(PayNowActivity.this, PayNowActivity.this::consumeResponse);
        }
    }


    public void startPayment(String key, String orderID, String payable_amount, String mobile, String email) {
        CurrentUserData user = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        final Activity activity = this;
        Double amount = Double.parseDouble(payable_amount);
        amount = (amount * 100);
        co = new Checkout();
        co.setImage(R.mipmap.app_icon);
        co.setKeyID(key);
        try {
            JSONObject options = new JSONObject();
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            options.put("currency", "INR");
            options.put("amount", "" + amount);
            options.put("theme.color", "#000000");

            options.put("order_id", orderID);
            options.put("name", "Spectra");
            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", mobile);
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
            updatePaymentStatus(paymentData.getOrderId());
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentError", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            setPaymentStatus(false, payable_amount);
        } catch (Exception e) {
            Log.e("TAG", "Exception in onPaymentSuccess", e);
        }
    }


}
