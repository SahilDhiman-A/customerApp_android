package com.spectra.consumer.Activities;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Fragments.MyCosumnedTopupFragment;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Request.GetRatePlanRequest;
import com.spectra.consumer.service.model.Request.PostDeactiveTopupPlan;
import com.spectra.consumer.service.model.Response.DeactivaPlanResponse;
import com.spectra.consumer.service.model.Response.GetRatePlanResponse;
import com.spectra.consumer.service.model.Response.GetplanResponse;
import com.spectra.consumer.service.model.Response.RcChargeResponse;
import com.spectra.consumer.service.model.Response.TopUpListResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_CHANGE_PLANE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CONSUMED_TOPUP;
import static com.spectra.consumer.service.repository.ApiConstant.DEACTIVE_TOPUP;
import static com.spectra.consumer.service.repository.ApiConstant.GET_RATEPLAN_BY_CANID;

public class MyPlanActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.txt_plan_name)
    TextView txt_plan_name;
    @BindView(R.id.txt_charges)
    TextView txt_charges;
    @BindView(R.id.txt_data)
    TextView txt_data;
    @BindView(R.id.txt_speed)
    TextView txt_speed;
    @BindView(R.id.txt_frequency)
    TextView txt_frequency;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.layout_plan)
    CardView layout_plan;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;

    @BindView(R.id.lay_frame)
    FrameLayout frameLayout;

    private CurrentUserData userData;
    private SpectraViewModel spectraViewModel;
    private PlanAndTopupViewModel planAndTopupViewModel;
    private String plan_ID;
    List<RcChargeResponse> rcChargeResponse=new ArrayList<>();
    private ArrayList<TopUpResponse> topUpList = new ArrayList<>();
    private Fragment fragment;
    String canIdAnalytics;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myplan);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.my_plan));
        userData= DroidPrefs.get(this,CurrentuserKey,CurrentUserData.class);
        canIdAnalytics = userData.CANId;
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        getPlan();
    }

    public void getPlan(){
        if(Constant.isInternetConnected(this)){
            GetRatePlanRequest getRatePlanRequest=new GetRatePlanRequest();
            getRatePlanRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getRatePlanRequest.setAction(GET_RATEPLAN_BY_CANID);
            getRatePlanRequest.setCanID(userData.CANId);
            spectraViewModel.getratePlanByCan(getRatePlanRequest).observe(MyPlanActivity.this, MyPlanActivity.this::consumeResponse);
        }
    }


    private void getDeactivatePlan(String topUpID,String topUpType){
        CurrentUserData userData= DroidPrefs.get(this,CurrentuserKey,CurrentUserData.class);
        if(Constant.isInternetConnected(this)){
            PostDeactiveTopupPlan postDeactiveTopupPlan=new PostDeactiveTopupPlan();
            postDeactiveTopupPlan.setAuthkey(BuildConfig.AUTH_KEY);
            postDeactiveTopupPlan.setAction(DEACTIVE_TOPUP);
            postDeactiveTopupPlan.setCanId(userData.CANId);
            postDeactiveTopupPlan.setTopupId(topUpID);
            postDeactiveTopupPlan.setTopupType(topUpType);
            planAndTopupViewModel.deactivatePlan(postDeactiveTopupPlan).observe(MyPlanActivity.this, MyPlanActivity.this::consumeResponse);
        }
    }

    private void getTopUpList(){
        CurrentUserData userData= DroidPrefs.get(this,CurrentuserKey,CurrentUserData.class);
        if(Constant.isInternetConnected(this)){
            ConsumedTopupRequest consumedTopupRequest=new ConsumedTopupRequest();
            consumedTopupRequest.setAuthkey(BuildConfig.AUTH_KEY);
            consumedTopupRequest.setCanID(userData.CANId);
            consumedTopupRequest.setAction(CONSUMED_TOPUP);
            planAndTopupViewModel.consumedTopup(consumedTopupRequest).observe(MyPlanActivity.this, MyPlanActivity.this::consumeResponse);
        }
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
            if (GET_RATEPLAN_BY_CANID.equals(code)) {
                GetRatePlanResponse getRatePlanResponse = (GetRatePlanResponse) response;
                if (getRatePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    GetplanResponse getplanResponse = getRatePlanResponse.getResponse();
                    txt_plan_name.setText(getplanResponse.getPlanName());
                    plan_ID = getplanResponse.getPlanId();
                    rcChargeResponse = getplanResponse.getRcCharge();
                    for (int i = 0; i < rcChargeResponse.size(); i++) {
                        RcChargeResponse chargeResponse = rcChargeResponse.get(0);
                        String amt = String.valueOf(chargeResponse.getAmount());
                        if (amt.equalsIgnoreCase("null") || amt.equalsIgnoreCase("")) {
                            txt_charges.setText("₹0");
                        } else {
                            txt_charges.setText(MessageFormat.format("₹ {0}", Constant.Round(Float.parseFloat(amt), 2)));
                        }

                    }
                    txt_speed.setText(userData.speed);
                    txt_data.setText(userData.planDataVolume);
                    txt_frequency.setText(userData.BillFrequency);
                    layout_plan.setVisibility(View.VISIBLE);

                } else {
                    Constant.MakeToastMessage(MyPlanActivity.this, getRatePlanResponse.getMessage());
                }
                getTopUpList();
            }else if(CONSUMED_TOPUP.equals(code)){
                TopUpListResponse topUpListResponse = (TopUpListResponse) response;
                if (topUpListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    topUpList.clear();
                    if (topUpListResponse.getResponse().size() > 0) {
                        topUpList.addAll(topUpListResponse.getResponse());
                    }
                    pushFragments("MyTopupFragment",true,false);
                } else {
                    Constant.MakeToastMessage(MyPlanActivity.this, topUpListResponse.getMessage());
                }
            }else if(DEACTIVE_TOPUP.equals(code)){
                DeactivaPlanResponse deactivaPlanResponse = (DeactivaPlanResponse)response;
                if(deactivaPlanResponse!=null && deactivaPlanResponse.getStatus()!=null){
                    if (deactivaPlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        getTopUpList();
                        deactivate_done_dialog();
                    }

                }
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
    @OnClick({R.id.img_back,R.id.layout_change_plan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.layout_change_plan:
                SpectraApplication.getInstance().postEvent(CATEGORY_CHANGE_PLANE, "change_plan_click", "change_plan_click", canIdAnalytics);
                Intent intent=new Intent(MyPlanActivity.this,SelectPackageActivity.class);
                intent.putExtra("plan_id",plan_ID);
                startActivity(intent);
                break;
        }
    }

    public void confirm_change_dialog(String topUpID,String topUpType){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.dailog_deactivate_topup, null);
        AppCompatTextView layout_change= v.findViewById(R.id.layout_change);
        AppCompatTextView layout_cancel= v.findViewById(R.id.layout_cancel);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        layout_cancel.setOnClickListener(view -> dial.dismiss());
        layout_change.setOnClickListener(view -> {
            getDeactivatePlan(topUpID,topUpType);
            dial.dismiss();
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void deactivate_done_dialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.dailog_deactivate_topup_done, null);
        AppCompatTextView layout_cancel= v.findViewById(R.id.dia_txt_cross);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        layout_cancel.setOnClickListener(view -> dial.dismiss());
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    public void pushFragments(String fragmentName, boolean animate, boolean shouldAdd) {
        Bundle bundle;
        switch (fragmentName){
            case "MyTopupFragment":
                bundle = new Bundle();
                bundle.putSerializable("DATA",topUpList);
                fragment = new MyCosumnedTopupFragment();
                fragment.setArguments(bundle);
                break;

        }

        if(fragment!=null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (animate) { //For animation
                ft.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
            }
            if (shouldAdd) { //for add in stack
                ft.replace(frameLayout.getId(), fragment, fragmentName);
                ft.addToBackStack(fragmentName);
                ft.commitAllowingStateLoss();
            } else {
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.replace(frameLayout.getId(), fragment, fragmentName);
                ft.commit();
            }
        }
    }
}
