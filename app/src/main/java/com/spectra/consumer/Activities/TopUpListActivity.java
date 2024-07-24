package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Adapters.TopUpAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.ConsumedTopupRequest;
import com.spectra.consumer.service.model.Response.TopUpListResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CONSUMED_TOPUP;

public class TopUpListActivity extends AppCompatActivity {
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.view_topups)
    RecyclerView view_topups;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_head)
    TextView txt_head;

    @BindView(R.id.txt_payment)
    TextView txt_payment;

    @BindView(R.id.layout_head_tabs)
    LinearLayout layout_head_tabs;
    @BindView(R.id.layout_buyTopup)
    LinearLayout layout_buyTopup;

    LinearLayoutManager linearLayoutManager;
    TopUpAdapter topUpListAdapter;
    SpectraViewModel spectraViewModel;
    PlanAndTopupViewModel planAndTopupViewModel;
    List<TopUpResponse> topUpResponseList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_topuplist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.topup));
        linearLayoutManager=new LinearLayoutManager(this);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
        view_topups.setLayoutManager(linearLayoutManager);
        layout_head_tabs.setVisibility(View.GONE);
        layout_buyTopup.setVisibility(View.VISIBLE);
        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        findViewById(R.id.img_payment_status).setVisibility(View.GONE);
        findViewById(R.id.try_again).setVisibility(View.GONE);
        txt_payment.setTextColor(getResources().getColor(R.color.back_color));
        getTopUpList();
    }

    private void getTopUpList(){
        CurrentUserData userData= DroidPrefs.get(this,CurrentuserKey,CurrentUserData.class);
        if(Constant.isInternetConnected(this)){
            ConsumedTopupRequest consumedTopupRequest=new ConsumedTopupRequest();
            consumedTopupRequest.setAuthkey(BuildConfig.AUTH_KEY);
            consumedTopupRequest.setCanID(userData.CANId);
            consumedTopupRequest.setAction(CONSUMED_TOPUP);
            planAndTopupViewModel.consumedTopup(consumedTopupRequest).observe(TopUpListActivity.this, TopUpListActivity.this::consumeResponse);
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
                assert apiResponse.error != null;
                Constant.MakeToastMessage(TopUpListActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }
    private void renderSuccessResponse(Object response,String code) {
        if (response != null) {
            if (CONSUMED_TOPUP.equals(code)) {
                TopUpListResponse topUpListResponse = (TopUpListResponse) response;
                if (topUpListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    if (topUpListResponse.getResponse().size() > 0) {
                        topUpResponseList = topUpListResponse.getResponse();
                        no_internet.setVisibility(View.GONE);
                        topUpListAdapter = new TopUpAdapter(this, topUpResponseList, true);
                        view_topups.setAdapter(topUpListAdapter);
                    } else {
                        txt_payment.setText(topUpListResponse.getMessage());
                        no_internet.setVisibility(View.VISIBLE);
                    }
                } else {
                    Constant.MakeToastMessage(TopUpListActivity.this, topUpListResponse.getMessage());
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
    @OnClick({R.id.img_back,R.id.layout_buyTopup})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
        if (view.getId() == R.id.layout_buyTopup) {
            Intent selectplanintent=new Intent(this, TopUpActivity.class);
            startActivity(selectplanintent);
        }

    }




}
