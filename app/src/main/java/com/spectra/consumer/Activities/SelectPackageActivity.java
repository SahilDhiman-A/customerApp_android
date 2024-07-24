package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.GetOffersRequest;
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;
import com.spectra.consumer.service.model.Request.PostProDataChangeRequest;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.GetOfferListResponse;
import com.spectra.consumer.service.model.Response.KnowMoreResponse;
import com.spectra.consumer.service.model.Response.OfferListResponse;
import com.spectra.consumer.service.model.Response.ProTopupResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_ALL_MENU;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CHANGE_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.GET_OFFERS;
import static com.spectra.consumer.service.repository.ApiConstant.KNOW_MORE;


public class SelectPackageActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;
    @BindView(R.id.view_change_plan)
    RecyclerView view_change_plan;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    SelectPackageAdapter selectPackageAdapter;
    LinearLayoutManager linearLayoutManager;
    CurrentUserData currentUserData;
    private List<OfferListResponse> offerListItemList = new ArrayList<>();
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
    Intent intent;
    private String plan_id;
    SpectraViewModel spectraViewModel;
    PlanAndTopupViewModel planAndTopupViewModel;

    private String packageID = "";

    @BindView(R.id.rv_know_more)
    RecyclerView knowMoreView;

    @BindView(R.id.layout_know_more)
    RelativeLayout layout_know_more;

    @BindView(R.id.txt_plan_sub)
    TextView knowMoreSubs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_package);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.change_plan));
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        currentUserData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);
        intent = getIntent();
        plan_id = intent.getStringExtra("plan_id");
        linearLayoutManager = new LinearLayoutManager(this);
        view_change_plan.setLayoutManager(linearLayoutManager);
        layout_know_more.setOnClickListener(v -> {
        });
        if (currentUserData.Segment.equals("Home")) {
            getOffersList();
        } else {
            findViewById(R.id.lay_compare).setVisibility(View.GONE);
            select_package(plan_id);
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
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case GET_OFFERS:
                    GetOfferListResponse getOfferListResponse = (GetOfferListResponse) response;
                    if (getOfferListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        offerListItemList = getOfferListResponse.getResponse();
                        if (offerListItemList != null && offerListItemList.size() > 0) {
                            selectPackageAdapter = new SelectPackageAdapter(this, offerListItemList, new SelectPackageAdapter.PackageSelected() {
                                @Override
                                public void onpackageSelected(View view, int position) {
                                    int id = view.getId();
                                    if (id == R.id.layout_select_package) {
                                        confirm_change_dialog(offerListItemList.get(position).getPlanid());
                                    }
                                }

                                @Override
                                public void onitemKnowMore(String packageID) {
                                    if (packageID != null) {
                                        getKnowMore(packageID);
                                    }
                                }

                                @Override
                                public void onItemSelectedForCompare(String packageID, boolean added) {

                                }
                            }, false);
                            view_change_plan.setAdapter(selectPackageAdapter);
                        }
                        view_change_data.setVisibility(View.VISIBLE);
                        no_internet.setVisibility(View.GONE);
                    } else {
                        view_change_data.setVisibility(View.GONE);
                        no_internet.setVisibility(View.VISIBLE);
                        try_again.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand_button, 0);
                        txt_payment.setText(getOfferListResponse.getMessage());
                        txt_retry.setVisibility(View.GONE);
                        try_again.setText(getString(R.string.back));
                    }
                    break;


                case KNOW_MORE:
                    KnowMoreResponse knowMoreResponse = (KnowMoreResponse) response;
                    if (knowMoreResponse != null && knowMoreResponse.getResponse() != null) {
                        if (knowMoreResponse.getResponse().getContentText() != null && knowMoreResponse.getResponse().getContentText().size() > 0) {
                            layout_know_more.setVisibility(View.VISIBLE);
                            if (knowMoreResponse.getResponse().getPlanDescription() != null) {
                                knowMoreSubs.setText(knowMoreResponse.getResponse().getPlanDescription());
                            }
                            KnowMoreAdapter mAdapter = new KnowMoreAdapter(getBaseContext(), knowMoreResponse);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
                            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            knowMoreView.setLayoutManager(mLayoutManager);
                            knowMoreView.setItemAnimator(new DefaultItemAnimator());
                            knowMoreView.setAdapter(mAdapter);
                        }
                    }
                    break;
                case CHANGE_PLAN:
                    ChangePlanResponse changePlanResponse = (ChangePlanResponse) response;
                    plan_change_request_raised(changePlanResponse.getMessage(), changePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS));
                    break;

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
        }

        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        back_to_sr.setOnClickListener(view -> {
            dial.dismiss();
            if (b) {
                Intent intent = new Intent(SelectPackageActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                onBackPressed();
            }
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
    }


    private void getOffersList() {
        if (Constant.isInternetConnected(this)) {
            no_internet.setVisibility(View.GONE);
            GetOffersRequest getOffersRequest = new GetOffersRequest();
            getOffersRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getOffersRequest.setAction(GET_OFFERS);
            getOffersRequest.setBasePlan(plan_id);
            getOffersRequest.setCanID(currentUserData.CANId);
            spectraViewModel.getOffers(getOffersRequest).observe(SelectPackageActivity.this, SelectPackageActivity.this::consumeResponse);
        }
    }

    public void select_package(String pkgName) {
        if (Constant.isInternetConnected(this)) {
            PlanAndTopupViewModel planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);

            ChangePlanRequest changePlanRequest = new ChangePlanRequest();
            changePlanRequest.setAuthkey(BuildConfig.AUTH_KEY);
            changePlanRequest.setAction(CHANGE_PLAN);
            changePlanRequest.setCanId(currentUserData.CANId);
            changePlanRequest.setPkgName(pkgName);
            planAndTopupViewModel.changePlan(changePlanRequest).observe(SelectPackageActivity.this, SelectPackageActivity.this::consumeResponse);
        }
    }

    private void getProDataCharges(String topUpId) {
        Intent intent = new Intent(SelectPackageActivity.this, CangePlanSelectionActivity.class);
        intent.putExtra("plan_id", plan_id);
        intent.putExtra("packageID", topUpId);
        startActivity(intent);


    }


    public void getKnowMore(String pkgName) {
        if (Constant.isInternetConnected(this)) {
            PostKnowMoreRequest postKnowMoreRequest = new PostKnowMoreRequest();
            postKnowMoreRequest.setAuthkey(BuildConfig.AUTH_KEY);
            postKnowMoreRequest.setAction(KNOW_MORE);
            postKnowMoreRequest.setPlanId(pkgName);
            planAndTopupViewModel.getKnowMore(postKnowMoreRequest).observe(SelectPackageActivity.this, SelectPackageActivity.this::consumeResponse);
        }
    }

    public void confirm_change_dialog(String pkgName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.layout_confirm_change, null);
        AppCompatTextView layout_change = v.findViewById(R.id.layout_change);
        AppCompatTextView layout_cancel = v.findViewById(R.id.layout_cancel);
        dialog.setView(v);
        dialog.setCancelable(true);
        final AlertDialog dial = dialog.create();
        dial.show();
        layout_cancel.setOnClickListener(view -> dial.dismiss());
        layout_change.setOnClickListener(view -> {
            getProDataCharges(pkgName);
            dial.dismiss();
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick({R.id.try_again, R.id.img_back, R.id.lay_compare, R.id.txt_cross, R.id.img_back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back_btn:
            case R.id.try_again:
            case R.id.img_back:
                finish();
                break;

            case R.id.lay_compare:
                SpectraApplication.getInstance().postEvent(CATEGORY_ALL_MENU, "Compare_Plan", "Compare plan clicked",currentUserData.CANId);
                Intent intent = new Intent(SelectPackageActivity.this, ComparePackageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("plan_id", plan_id);
                startActivity(intent);
                finish();
                break;

            case R.id.txt_cross:
                layout_know_more.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (layout_know_more.getVisibility() == View.VISIBLE) {
            layout_know_more.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}

