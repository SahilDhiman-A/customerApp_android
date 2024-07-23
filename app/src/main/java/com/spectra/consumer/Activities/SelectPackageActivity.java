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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Adapters.SelectPackageAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.ChangePlanRequest;
import com.spectra.consumer.service.model.Request.GetOffersRequest;
import com.spectra.consumer.service.model.Response.ChangePlanResponse;
import com.spectra.consumer.service.model.Response.GetOfferListResponse;
import com.spectra.consumer.service.model.Response.OfferListResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CHANGE_PLAN;
import static com.spectra.consumer.service.repository.ApiConstant.GET_OFFERS;

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
    Intent intent;
    private   String plan_id;
    SpectraViewModel spectraViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_package);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.change_plan));
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        currentUserData= DroidPrefs.get(this,Constant.CurrentuserKey,CurrentUserData.class);
        intent=getIntent();
        plan_id=intent.getStringExtra("plan_id");
        linearLayoutManager=new LinearLayoutManager(this);
        view_change_plan.setLayoutManager(linearLayoutManager);
        getOffersList();
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
                            selectPackageAdapter=new SelectPackageAdapter(this, offerListItemList, (view, position) -> {
                                int id =view.getId();
                                if(id==R.id.layout_select_package){
                                    confirm_change_dialog(offerListItemList.get(position).getPlanid());
                                }
                            });
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
            spectraViewModel.getOffers(getOffersRequest).observe(SelectPackageActivity.this, SelectPackageActivity.this::consumeResponse);
        }
    }
    public void select_package(String pkgName){
        if(Constant.isInternetConnected(this)){
            ChangePlanRequest changePlanRequest=new ChangePlanRequest();
            changePlanRequest.setAuthkey(BuildConfig.AUTH_KEY);
            changePlanRequest.setAction(CHANGE_PLAN);
            changePlanRequest.setCanId(currentUserData.CANId);
            changePlanRequest.setPkgName(pkgName);
            spectraViewModel.changePlan(changePlanRequest).observe(SelectPackageActivity.this, SelectPackageActivity.this::consumeResponse);
        }
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
            Intent intent=new Intent(SelectPackageActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    @OnClick({R.id.try_again,R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.try_again:
            case R.id.img_back:
                finish();
                break;
        }
    }
}
