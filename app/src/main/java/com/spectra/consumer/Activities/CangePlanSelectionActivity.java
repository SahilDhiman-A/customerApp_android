package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.PostPlanComparisionRequest;
import com.spectra.consumer.service.model.Request.PostProDataChangeRequest;
import com.spectra.consumer.service.model.Request.TopupRequest;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.ComparePlanItem;
import com.spectra.consumer.service.model.Response.ComparePlanResponse;
import com.spectra.consumer.service.model.Response.GetDataChrgesResponse;
import com.spectra.consumer.service.model.Response.OfferListResponse;
import com.spectra.consumer.service.model.Response.ProTopupResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_CHANGE_PLANE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CHANGE_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.COMPARISION_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.PRO_DATA_CHARGES_PLAN;

public class CangePlanSelectionActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    ;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_head)
    TextView txt_head;

    @BindView(R.id.txtData)
    TextView txtData;

    @BindView(R.id.txtDuration)
    TextView txtDuration;

    @BindView(R.id.txtRevesedBalnce)
    TextView txtRevesedBalnce;
    @BindView(R.id.txtNewPlan)
    TextView txtNewPlan;
    @BindView(R.id.txtPayable)
    TextView txtPayable;
    @BindView(R.id.txtTex)
    TextView txtTex;
    @BindView(R.id.txtTotal)
    TextView txtTotal;
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.tvBackToHomeSr)
    TextView tvBackToHomeSr;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    SpectraViewModel spectraViewModel;
    public static TopUpResponse topUpResponse;
    CurrentUserData userData;
    PlanAndTopupViewModel planAndTopupViewModel;
    String plan_id, packageID;
    int amount = 0;
    String canIdAnalytics;
    public static ComparePlanItem currentPlan, newPlan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_charge);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText("Current Plan");
        toolbar_head.setVisibility(View.VISIBLE);
        userData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);
        Intent intent = getIntent();
        plan_id = intent.getStringExtra("plan_id");
        packageID = intent.getStringExtra("packageID");
        Log.d("packageID: ", packageID);
        CAN_ID canIdNik = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        Log.d("Nik Can", canIdNik.baseCanID);
        canIdAnalytics = canIdNik.baseCanID;
        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        findViewById(R.id.img_payment_status).setVisibility(View.GONE);
        findViewById(R.id.try_again).setVisibility(View.GONE);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        getCompareScreen();
        getPlanCharge();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void getPlanCharge() {
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        if (Constant.isInternetConnected(this)) {
            PostProDataChangeRequest proDataChangeRequest = new PostProDataChangeRequest();
            proDataChangeRequest.setAuthkey(BuildConfig.AUTH_KEY);
            proDataChangeRequest.setCanId(userData.CANId);
            proDataChangeRequest.setAction(PRO_DATA_CHARGES_PLAN);
            proDataChangeRequest.setPlanId(packageID);
            planAndTopupViewModel.getProDataChange(proDataChangeRequest).observe(CangePlanSelectionActivity.this, CangePlanSelectionActivity.this::consumeResponse);
        }
    }

    public void getCompareScreen() {
        if (Constant.isInternetConnected(this)) {
            CurrentUserData userdata = DroidPrefs.get(CangePlanSelectionActivity.this, CurrentuserKey, CurrentUserData.class);
            ArrayList<String> plans = new ArrayList<>();
            if (userdata != null) {
                plans.add(0, userdata.Product);
            }
            plans.add(packageID);
            PostPlanComparisionRequest postPlanComparisionRequest = new PostPlanComparisionRequest();
            postPlanComparisionRequest.setAuthkey(BuildConfig.AUTH_KEY);
            postPlanComparisionRequest.setAction(COMPARISION_PLAN);
            postPlanComparisionRequest.setPlanId(plans);
            planAndTopupViewModel.getCompareData(postPlanComparisionRequest).observe(CangePlanSelectionActivity.this, CangePlanSelectionActivity.this::consumeResponse);
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
                Constant.MakeToastMessage(CangePlanSelectionActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case PRO_DATA_CHARGES_PLAN:
                    GetDataChrgesResponse topUpListResponse = (GetDataChrgesResponse) response;
                    if (topUpListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        GetDataChrgesResponse.ResponseData responseData = topUpListResponse.getResponse();
                        if (responseData != null) {
                            try {
                                DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                                @SuppressLint("SimpleDateFormat")
                                DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
                                Date date = originalFormat.parse(responseData.getDate());
                                String formattedDate = targetFormat.format(date);
                                txtData.setText(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            txtDuration.setText("₹" + responseData.getDuration());

                            txtPayable.setText("₹" + responseData.getDifferenceAmountPayable());
                            txtTex.setText("₹" + responseData.getTaxes());
                            txtNewPlan.setText("₹" + responseData.getNewPlanCharges());
                            txtRevesedBalnce.setText("₹" + responseData.getReversedBalance());
                            txtTex.setText("₹" + responseData.getTaxes());
                            amount = responseData.getPgDataCharges();
                            txtTotal.setText("₹" + amount);
                            tvRemark.setText("Remarks: " + responseData.getRemarks());
                            try {
                                if (amount > 0) {
                                    tvBackToHomeSr.setText("Pay now");
                                } else {
                                    tvBackToHomeSr.setText("Change Plan");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        Constant.MakeToastMessage(CangePlanSelectionActivity.this, topUpListResponse.getMessage());
                    }
                    break;
                case COMPARISION_PLAN:
                    ComparePlanResponse comparePlanResponse = (ComparePlanResponse) response;
                    if (comparePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        if (comparePlanResponse.getResponse() != null && comparePlanResponse.getResponse().size() > 1) {

                            if (comparePlanResponse.getResponse().get(0).getPlanid().equals(packageID)) {
                                newPlan = comparePlanResponse.getResponse().get(0);
                                currentPlan = comparePlanResponse.getResponse().get(1);
                            } else {
                                newPlan = comparePlanResponse.getResponse().get(1);
                                currentPlan = comparePlanResponse.getResponse().get(0);
                            }
                            if (currentPlan != null && newPlan != null) {
                                try {
                                    SpectraApplication.getInstance().addKey("new_plan_charges", newPlan.getCharges());
                                    SpectraApplication.getInstance().addKey("new_plan_data", newPlan.getData());
                                    SpectraApplication.getInstance().addKey("new_plan_speed", newPlan.getSpeed());
                                    SpectraApplication.getInstance().addKey("new_plan_frequency", newPlan.getFrequency());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_select", "change_plan_select", canIdAnalytics);
                        }
                    }
                    break;
                case CHANGE_PLAN:
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


                            SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_success", "change_plan_success",canIdAnalytics);

                        } else {
                            SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_failed", "change_plan_failed", canIdAnalytics);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    plan_change_request_raised(changePlanResponse.getMessage(), changePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS));

                    break;
            }

        }
    }

    public void select_package(String pkgName) {
        if (Constant.isInternetConnected(this)) {
            ChangePlanRequest changePlanRequest = new ChangePlanRequest();
            changePlanRequest.setAuthkey(BuildConfig.AUTH_KEY);
            changePlanRequest.setAction(CHANGE_PLAN);
            changePlanRequest.setCanId(userData.CANId);
            changePlanRequest.setPkgName(pkgName);
            planAndTopupViewModel.changePlan(changePlanRequest).observe(CangePlanSelectionActivity.this, CangePlanSelectionActivity.this::consumeResponse);
        }
    }

    public void startPayment(String amount, String packageID) {
        CurrentUserData Data = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        Intent intent_pay = new Intent(CangePlanSelectionActivity.this, PayNowActivity.class);
        if (TextUtils.isEmpty(amount)) {
            amount = "0";
        }
        if (!TextUtils.isEmpty(amount)) {
            intent_pay.putExtra("email", Data.Email);
            intent_pay.putExtra("mobile", Data.Number);
            intent_pay.putExtra("payableAamount", amount);
            intent_pay.putExtra("canID", Data.CANId);
            intent_pay.putExtra("type", "unpaid");
            intent_pay.putExtra("packageID", packageID);
            intent_pay.putExtra("subType", "changePlan");
            startActivity(intent_pay);

        } else {
            Constant.MakeToastMessage(CangePlanSelectionActivity.this, "Payable amount can't be 0");
        }
    }


    public void pay(TopUpResponse topUpResponse) {
        CangePlanSelectionActivity.topUpResponse = topUpResponse;
        if (!TextUtils.isEmpty(topUpResponse.getPg_price())) {
            Intent intent_pay = new Intent(CangePlanSelectionActivity.this, PayNowActivity.class);
            intent_pay.putExtra("email", userData.Email);
            intent_pay.putExtra("mobile", userData.Number);
            intent_pay.putExtra("type", "unpaid");
            intent_pay.putExtra("payableAamount", topUpResponse.getPg_price());
            intent_pay.putExtra("subType", "topup");
            intent_pay.putExtra("canID", userData.CANId);
            startActivityForResult(intent_pay, 1);

        } else {
            Constant.MakeToastMessage(this, "Payable amount can't be 0");
        }


    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.cancle, R.id.tvBackToHomeSr})
    public void onClick(View view) {
        if (view.getId() == R.id.cancle) {

            if (currentPlan != null) {
                try {
                    SpectraApplication.getInstance().addKey("current_plan_data", currentPlan.getData());
                    SpectraApplication.getInstance().addKey("current_plan_speed", currentPlan.getSpeed());
                    SpectraApplication.getInstance().addKey("current_plan_frequency", currentPlan.getFrequency());
                    SpectraApplication.getInstance().addKey("current_plan_charges", currentPlan.getCharges());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_canceled", "change_plan_canceled", canIdAnalytics);

            finish();
        }

        if (view.getId() == R.id.tvBackToHomeSr) {
            try {
                if (amount > 0) {
                    startPayment(amount + "", packageID);
                } else {
                    select_package(packageID);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        } else {
            back_to_sr.setText(getString(R.string.back));
            ;
        }

        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        back_to_sr.setOnClickListener(view -> {
            dial.dismiss();
            if (b) {
                Intent intent = new Intent(CangePlanSelectionActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                onBackPressed();
            }
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
