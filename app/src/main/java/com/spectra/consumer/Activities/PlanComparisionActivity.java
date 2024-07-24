package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Adapters.ComparePlanAdapter;
import com.spectra.consumer.Adapters.KnowMoreAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.PostKnowMoreRequest;
import com.spectra.consumer.service.model.Response.ComparePlanItem;
import com.spectra.consumer.service.model.Response.ComparePlanResponse;
import com.spectra.consumer.service.model.Response.KnowMoreResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.service.repository.ApiConstant.KNOW_MORE;

public class PlanComparisionActivity extends AppCompatActivity {

    private ComparePlanResponse comparePlanResponse;

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    @BindView(R.id.view_change_plan)
    RecyclerView planRecyclerView;


    @BindView(R.id.rv_know_more)
    RecyclerView knowMoreView;

    @BindView(R.id.layout_know_more)
    RelativeLayout layout_know_more;

    @BindView(R.id.txt_plan_sub)
    TextView knowMoreSubs;



    PlanAndTopupViewModel planAndTopupViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparision);
        ButterKnife.bind(this);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        Bundle bundle =  getIntent().getExtras();
        if(bundle!=null && bundle.containsKey("DATA")) {
            comparePlanResponse = (ComparePlanResponse) bundle.getSerializable("DATA");
            setInItViews(comparePlanResponse);
        }
    }

    private void setInItViews(ComparePlanResponse response){
        if(response!=null && response.getResponse()!=null){
            ComparePlanAdapter mAdapter = new ComparePlanAdapter(PlanComparisionActivity.this,response);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            planRecyclerView.setLayoutManager(mLayoutManager);
            planRecyclerView.setItemAnimator(new DefaultItemAnimator());
            planRecyclerView.setAdapter(mAdapter);
        }
    }

    public void callKnowMore(ComparePlanItem planItem){
        if(planItem!=null){
            if(Constant.isInternetConnected(this)){
                PostKnowMoreRequest postKnowMoreRequest=new PostKnowMoreRequest();
                postKnowMoreRequest.setAuthkey(BuildConfig.AUTH_KEY);
                postKnowMoreRequest.setAction(KNOW_MORE);
                postKnowMoreRequest.setPlanId(planItem.getPlanid());
                planAndTopupViewModel.getKnowMore(postKnowMoreRequest).observe(PlanComparisionActivity.this, PlanComparisionActivity.this::consumeResponse);
            }
        }
    }
    public void SelectPlan(ComparePlanItem planItem){
        if(planItem!=null){
            if(Constant.isInternetConnected(this)){
                confirm_change_dialog(planItem.getPlanid());
            }
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
    private void getProDataCharges(String topUpId) {
        Intent intent = new Intent(PlanComparisionActivity.this, CangePlanSelectionActivity.class);
        intent.putExtra("plan_id", topUpId);
        intent.putExtra("packageID", topUpId);
        startActivity(intent);


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
            switch (code) {
                case KNOW_MORE:
                    KnowMoreResponse knowMoreResponse = (KnowMoreResponse) response;
                    if (knowMoreResponse != null && knowMoreResponse.getResponse() != null) {
                        if (knowMoreResponse.getResponse().getContentText() != null && knowMoreResponse.getResponse().getContentText().size() > 0) {
                            layout_know_more.setVisibility(View.VISIBLE);

                            if(knowMoreResponse.getResponse().getPlanDescription()!=null) {
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


    @OnClick({R.id.img_back, R.id.txt_cross,R.id.layout_know_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
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
