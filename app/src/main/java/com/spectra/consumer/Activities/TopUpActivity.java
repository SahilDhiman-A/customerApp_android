package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.spectra.consumer.service.model.Request.TopupRequest;
import com.spectra.consumer.service.model.Response.TopUpListResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.GET_TOPUP;

public class TopUpActivity extends AppCompatActivity {
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
    @BindView(R.id.txt_oneTime)
    TextView txt_oneTime;
    @BindView(R.id.txt_recurring)
    TextView txt_recurring;

    @BindView(R.id.tvMessage)
    TextView tvMessage;

    @BindView(R.id.view_recurring)
    View view_recurring;
    @BindView(R.id.view_oneTime)
    View view_oneTime;
    @BindView(R.id.txt_payment)
    TextView txt_payment;
    LinearLayoutManager linearLayoutManager;
    TopUpAdapter topUpListAdapter;
    SpectraViewModel spectraViewModel;
    List<TopUpResponse> topUpRC = new ArrayList<>();
    List<TopUpResponse> topUpNRC = new ArrayList<>();
    public static TopUpResponse topUpResponse;
    CurrentUserData userData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_topuplist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.topup));

        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        findViewById(R.id.img_payment_status).setVisibility(View.GONE);
        findViewById(R.id.try_again).setVisibility(View.GONE);
        findViewById(R.id.layout_buyTopup).setVisibility(View.VISIBLE);
        findViewById(R.id.img_logout).setVisibility(View.GONE);

        tvMessage.setText(R.string.oneTimePopMessage);
        txt_payment.setTextColor(getResources().getColor(R.color.back_color));
        linearLayoutManager = new LinearLayoutManager(this);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        view_topups.setLayoutManager(linearLayoutManager);
        getTopUpList();
        selectTopup(getResources().getColor(R.color.back_color), getResources().getColor(R.color.not_selected_color));
    }

    private void getTopUpList() {
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        if (Constant.isInternetConnected(this)) {
            TopupRequest topupRequest = new TopupRequest();
            topupRequest.setAuthkey(BuildConfig.AUTH_KEY);
            topupRequest.setCanID(userData.CANId);
            topupRequest.setAction(GET_TOPUP);
            topupRequest.setBasePlan(userData.Product);
            spectraViewModel.getTopUpList(topupRequest).observe(TopUpActivity.this, TopUpActivity.this::consumeResponse);

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
                Constant.MakeToastMessage(TopUpActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            if (GET_TOPUP.equals(code)) {
                TopUpListResponse topUpListResponse = (TopUpListResponse) response;
                if (topUpListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    if (topUpListResponse.getResponse().size() > 0) {

                        for (TopUpResponse topUpResponse : topUpListResponse.getResponse()) {
                            if (topUpResponse.getType().equals("NRC")) {
                                topUpRC.add(topUpResponse);
                            } else {
                                topUpNRC.add(topUpResponse);
                            }
                        }
                        topUpListAdapter = new TopUpAdapter(this, topUpRC, false);
                        view_topups.setAdapter(topUpListAdapter);
                        if (topUpRC.size() > 0) {
                            showEmptyMassage("", false);
                        } else {
                            showEmptyMassage("No Record Found", true);
                        }
                    } else {
                        showEmptyMassage(topUpListResponse.getMessage(), true);
                    }
                } else {
                    Constant.MakeToastMessage(TopUpActivity.this, topUpListResponse.getMessage());
                }
            }
        }
    }

    public void showEmptyMassage(String message, boolean isShow) {
        if(isShow) {
            no_internet.setVisibility(View.VISIBLE);
            view_topups.setVisibility(View.GONE);
            txt_payment.setText(message);
        }else {
            no_internet.setVisibility(View.GONE);
            view_topups.setVisibility(View.VISIBLE);
        }
    }

    public void pay(TopUpResponse topUpResponse) {
        TopUpActivity.topUpResponse = topUpResponse;
        if (!TextUtils.isEmpty(topUpResponse.getPg_price())) {
                Intent intent_pay = new Intent(TopUpActivity.this, PayNowActivity.class);
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

    @OnClick({R.id.img_back, R.id.layout_oneTime, R.id.layout_recurring})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }

        if (view.getId() == R.id.layout_oneTime) {
            tvMessage.setText(R.string.oneTimePopMessage);
            selectTopup(getResources().getColor(R.color.back_color), getResources().getColor(R.color.not_selected_color));
            if (topUpRC.size() > 0) {
                showEmptyMassage("",false);
                topUpListAdapter.updateList(topUpRC);
            }else {
                tvMessage.setText(R.string.oneTimePopMessage);
                showEmptyMassage("No offer available",true);
            }
        }
        if (view.getId() == R.id.layout_recurring) {
            tvMessage.setText(R.string.recuringMassage);
            selectTopup(getResources().getColor(R.color.not_selected_color),getResources().getColor(R.color.back_color));
            if (topUpNRC.size() > 0) {
                showEmptyMassage("",false);
                topUpListAdapter.updateList(topUpNRC);

            }else {
                showEmptyMassage("No offer available",true);
            }
        }

    }


    private void selectTopup(int color, int color2) {
        txt_oneTime.setTextColor(color);
        view_oneTime.setBackgroundColor(color);
        txt_recurring.setTextColor(color2);
        view_recurring.setBackgroundColor(color2);
        tvMessage.setTextColor(getResources().getColor(R.color.back_color));
    }


}
