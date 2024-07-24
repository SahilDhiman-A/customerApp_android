package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Adapters.KnowMoreAdapter;
import com.spectra.consumer.Adapters.SelectPackageAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.GetOffersRequest;
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;
import com.spectra.consumer.service.model.Request.PostPlanComparisionRequest;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.ComparePlanResponse;
import com.spectra.consumer.service.model.Response.GetOfferListResponse;
import com.spectra.consumer.service.model.Response.KnowMoreResponse;
import com.spectra.consumer.service.model.Response.OfferListResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_ALL_MENU;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CHANGE_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.COMPARISION_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.GET_OFFERS;
import static com.spectra.consumer.service.repository.ApiConstant.KNOW_MORE;

public class ComparePackageActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;
    @BindView(R.id.view_change_plan)
    RecyclerView view_change_plan;
    @BindView(R.id.lay_proceed)
    LinearLayout lay_proceed;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    SelectPackageAdapter selectPackageAdapter;
    LinearLayoutManager linearLayoutManager;
    CurrentUserData currentUserData;
    private List<OfferListResponse> offerListItemList=new ArrayList<>();
    @BindView(R.id.view_change_data)
    RelativeLayout view_change_data;
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.txt_payment)
    TextView txt_payment;
    @BindView(R.id.try_again)
    AppCompatTextView try_again;
    @BindView(R.id.txt_retry)
    TextView txt_retry;
    @BindView(R.id.rv_know_more)
    RecyclerView knowMoreView;
    @BindView(R.id.layout_know_more)
    RelativeLayout layout_know_more;
    @BindView(R.id.txt_plan_sub)
    TextView knowMoreSubs;
    Intent intent;
    private   String plan_id;
    SpectraViewModel spectraViewModel;
    PlanAndTopupViewModel planAndTopupViewModel;
    private List<String> comparePlan;
    private boolean enableOnContinue =false;
    String canIdAnalytics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_plan);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.change_plan));

        CAN_ID canIdNik = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        Log.d("Nik Can", canIdNik.baseCanID);
        canIdAnalytics = canIdNik.baseCanID;

        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        currentUserData= DroidPrefs.get(this,Constant.CurrentuserKey,CurrentUserData.class);
        intent=getIntent();
        plan_id=intent.getStringExtra("plan_id");
        comparePlan = new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(this);
        view_change_plan.setLayoutManager(linearLayoutManager);
        checkProceedButton();
        getOffersList();
    }


    private void checkProceedButton(){
        if(comparePlan!=null && comparePlan.size()>0){
            enableOnContinue = true;
            lay_proceed.setAlpha(1f);
        }else{
            enableOnContinue = false;
            lay_proceed.setAlpha(0.5f);
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
            switch (code){
                case GET_OFFERS:
                    GetOfferListResponse getOfferListResponse= (GetOfferListResponse) response;
                    if(getOfferListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)){
                        offerListItemList=getOfferListResponse.getResponse();
                        if(offerListItemList!=null && offerListItemList.size()>0){
                            selectPackageAdapter=new SelectPackageAdapter(this, offerListItemList,  new SelectPackageAdapter.PackageSelected() {
                                @Override
                                public void onpackageSelected(View view, int position) {
                                    int id =view.getId();
                                    if(id==R.id.layout_select_package){
                                        confirm_change_dialog(offerListItemList.get(position).getPlanid());
                                    }
                                }

                                @Override
                                public void onitemKnowMore(String packageID) {
                                    if(packageID!=null){
                                        getKnowMore(packageID);
                                    }
                                }

                                @Override
                                public void onItemSelectedForCompare(String packageID, boolean added) {
                                    if(added){
                                        comparePlan.add(packageID);
                                    }else{
                                        comparePlan.remove(packageID);
                                    }
                                    checkProceedButton();
                                }
                            },true);
                            view_change_plan.setAdapter(selectPackageAdapter);
                        }
                        view_change_data.setVisibility(View.VISIBLE);
                        no_internet.setVisibility(View.GONE);
                    }
                    else{
                        view_change_data.setVisibility(View.GONE);
                        no_internet.setVisibility(View.VISIBLE);
                        try_again.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_expand_button,0);
                        txt_payment.setText(getOfferListResponse.getMessage());
                        txt_retry.setVisibility(View.GONE);
                        try_again.setText(getString(R.string.back));
                    }
                    break;
                case CHANGE_PLAN:
                    ChangePlanResponse changePlanResponse= (ChangePlanResponse) response;
                    plan_change_request_raised(changePlanResponse.getMessage());
                    break;

                case KNOW_MORE:
                    KnowMoreResponse knowMoreResponse = (KnowMoreResponse)response;
                    if(knowMoreResponse!=null && knowMoreResponse.getResponse()!=null){
                        if(knowMoreResponse.getResponse().getContentText()!=null && knowMoreResponse.getResponse().getContentText().size()>0){
                            layout_know_more.setVisibility(View.VISIBLE);
                            if(knowMoreResponse.getResponse().getPlanDescription()!=null) {
                                knowMoreSubs.setText(knowMoreResponse.getResponse().getPlanDescription());
                            }
                            KnowMoreAdapter mAdapter = new KnowMoreAdapter(getBaseContext(),knowMoreResponse);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            knowMoreView.setLayoutManager(mLayoutManager);
                            knowMoreView.setItemAnimator(new DefaultItemAnimator());
                            knowMoreView.setAdapter(mAdapter);
                        }
                    }
                    break;

                case COMPARISION_PLAN:
                    ComparePlanResponse comparePlanResponse = (ComparePlanResponse)response;
                    if(comparePlanResponse!=null && comparePlanResponse.getResponse()!=null){
                        if(comparePlanResponse.getResponse().size()>0){
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("DATA",comparePlanResponse);
                            Intent intent = new Intent(ComparePackageActivity.this,PlanComparisionActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
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
    private void getOffersList(){
        if(Constant.isInternetConnected(this)){
            no_internet.setVisibility(View.GONE);
            GetOffersRequest getOffersRequest=new GetOffersRequest();
            getOffersRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getOffersRequest.setAction(GET_OFFERS);
            getOffersRequest.setBasePlan(plan_id);
            getOffersRequest.setCanID(currentUserData.CANId);
            spectraViewModel.getOffers(getOffersRequest).observe(ComparePackageActivity.this, ComparePackageActivity.this::consumeResponse);
        }
    }

    public void getKnowMore(String pkgName){
        if(Constant.isInternetConnected(this)){
            PostKnowMoreRequest postKnowMoreRequest=new PostKnowMoreRequest();
            postKnowMoreRequest.setAuthkey(BuildConfig.AUTH_KEY);
            postKnowMoreRequest.setAction(KNOW_MORE);
            postKnowMoreRequest.setPlanId(pkgName);
            planAndTopupViewModel.getKnowMore(postKnowMoreRequest).observe(ComparePackageActivity.this, ComparePackageActivity.this::consumeResponse);
        }
    }

    public void getCompareScreen(){
        if(Constant.isInternetConnected(this)){
            CurrentUserData userdata = DroidPrefs.get(ComparePackageActivity.this, CurrentuserKey, CurrentUserData.class);
            ArrayList<String> plans = new ArrayList<>();
            plans.addAll(comparePlan);
            if(userdata!=null) {
                plans.add(0, userdata.Product);
            }
            PostPlanComparisionRequest postPlanComparisionRequest=new PostPlanComparisionRequest();
            postPlanComparisionRequest.setAuthkey(BuildConfig.AUTH_KEY);
            postPlanComparisionRequest.setAction(COMPARISION_PLAN);
            postPlanComparisionRequest.setPlanId(plans);
            planAndTopupViewModel.getCompareData(postPlanComparisionRequest).observe(ComparePackageActivity.this, ComparePackageActivity.this::consumeResponse);
        }
    }


    public void select_package(String pkgName){
        if(Constant.isInternetConnected(this)){
            ChangePlanRequest changePlanRequest=new ChangePlanRequest();
          /*  changePlanRequest.setAuthkey(BuildConfig.AUTH_KEY);
            changePlanRequest.setAction(CHANGE_PLAN);
            changePlanRequest.setCanId(currentUserData.CANId);
            changePlanRequest.setPkgName(pkgName);
            spectraViewModel.changePlan(changePlanRequest).observe(ComparePackageActivity.this, ComparePackageActivity.this::consumeResponse);
   */
            Intent intent = new Intent(ComparePackageActivity.this, CangePlanSelectionActivity.class);
            intent.putExtra("plan_id", plan_id);
            intent.putExtra("packageID", pkgName);
            startActivity(intent);
        }


    }


    private void getProDataCharges(String topUpId) {

    }






    public void confirm_change_dialog(String pkgName){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.layout_confirm_change, null);
        AppCompatTextView layout_change= v.findViewById(R.id.layout_change);
        AppCompatTextView layout_cancel= v.findViewById(R.id.layout_cancel);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        layout_cancel.setOnClickListener(view -> dial.dismiss());
        layout_change.setOnClickListener(view -> {
            select_package(pkgName);
            dial.dismiss();

        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public  void plan_change_request_raised(String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.back_to_sr_dialog, null);
        AppCompatTextView back_to_sr= v.findViewById(R.id.back_to_sr);
        TextView txt_heading=v.findViewById(R.id.txt_heading);
        txt_heading.setText(message);
        back_to_sr.setText(getString(R.string.back));
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        back_to_sr.setOnClickListener(view -> {
            dial.dismiss();
            Intent intent=new Intent(ComparePackageActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @OnClick({R.id.try_again,R.id.img_back_btn,R.id.lay_proceed, R.id.txt_cross,R.id.layout_know_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.try_again:
            case R.id.img_back_btn:
                finish();
                break;

            case R.id.lay_proceed:
                if(enableOnContinue) {
                    SpectraApplication.getInstance().postEvent(CATEGORY_ALL_MENU, "Compare_Plan_Proceed", "Compare plan proceed clicked",currentUserData.CANId);
                    getCompareScreen();
                }
                break;

            case R.id.txt_cross:
                layout_know_more.setVisibility(View.GONE);
                break;

            case R.id.layout_know_more:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(layout_know_more.getVisibility()==View.VISIBLE){
            layout_know_more.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }
}
