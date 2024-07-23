package com.spectra.consumer.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.GetRatePlanRequest;
import com.spectra.consumer.service.model.Response.GetRatePlanResponse;
import com.spectra.consumer.service.model.Response.GetplanResponse;
import com.spectra.consumer.service.model.Response.RcChargeResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
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
    private CurrentUserData userData;
    SpectraViewModel spectraViewModel;
    private String plan_ID;
    List<RcChargeResponse> rcChargeResponse=new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_myplan);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.my_plan));
        userData= DroidPrefs.get(this,CurrentuserKey,CurrentUserData.class);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
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

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                showLoadingView(false);
                break;
            case ERROR:
                showLoadingView(false);
                break;
            default:
                break;
        }
    }
    private void renderSuccessResponse(Object response) {
        if (response != null) {
            GetRatePlanResponse getRatePlanResponse= (GetRatePlanResponse) response;
            if(getRatePlanResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)){
                GetplanResponse getplanResponse=getRatePlanResponse.getResponse();
                txt_plan_name.setText(getplanResponse.getPlanName());
                plan_ID=getplanResponse.getPlanId();
                rcChargeResponse=getplanResponse.getRcCharge();
                for(int i=0;i<rcChargeResponse.size();i++) {
                    RcChargeResponse chargeResponse = rcChargeResponse.get(0);
                    String amt= String.valueOf(chargeResponse.getAmount());
                    if(amt.equalsIgnoreCase("null") || amt.equalsIgnoreCase("")){
                        txt_charges.setText("₹0");
                    }
                    else{
                        txt_charges.setText(MessageFormat.format("₹ {0}", Constant.Round(Float.parseFloat(amt), 2)));
                    }

                }
                txt_speed.setText(userData.speed);
                txt_data.setText(userData.planDataVolume);
                txt_frequency.setText(userData.BillFrequency);
                layout_plan.setVisibility(View.VISIBLE);
            }
            else{
                Constant.MakeToastMessage(MyPlanActivity.this,getRatePlanResponse.getMessage());
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
                Intent intent=new Intent(MyPlanActivity.this,SelectPackageActivity.class);
                intent.putExtra("plan_id",plan_ID);
                startActivity(intent);
                break;
        }
    }

}
