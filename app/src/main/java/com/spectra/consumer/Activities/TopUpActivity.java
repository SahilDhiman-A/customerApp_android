package com.spectra.consumer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Fragments.TopupAmountToPayFragment;
import com.spectra.consumer.Fragments.TopupBaseFragment;
import com.spectra.consumer.Fragments.TopupPaymentDoneFragment;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.AddTopUpRequest;
import com.spectra.consumer.service.model.Request.PostProDataTopUpRequest;
import com.spectra.consumer.service.model.Request.TopupRequest;
import com.spectra.consumer.service.model.Response.AddTopUpResponse;
import com.spectra.consumer.service.model.Response.ProTopupResponse;
import com.spectra.consumer.service.model.Response.TopUpListResponse;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.ADD_TOPUP;
import static com.spectra.consumer.service.repository.ApiConstant.GET_TOPUP;
import static com.spectra.consumer.service.repository.ApiConstant.PRO_DATA_CHARGES_TOPUP_PLAN;

public class TopUpActivity extends AppCompatActivity {

    @BindView(R.id.no_internet)
    LinearLayout no_internet;

    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;

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

    @BindView(R.id.lay_frame)
    FrameLayout frameLayout;


    @BindView(R.id.tv_step_one)
    TextView tv_step_one;

    @BindView(R.id.view_first)
    View view_first;

    @BindView(R.id.tv_step_two)
    TextView tv_step_two;

    @BindView(R.id.view_sec)
    View view_sec;

    @BindView(R.id.tv_step_three)
    TextView tv_step_three;


    private Fragment fragment;
    private SpectraViewModel spectraViewModel;
    private PlanAndTopupViewModel planAndTopupViewModel;
    private ArrayList<TopUpResponse> topUpRC = new ArrayList<>();
    private ArrayList<TopUpResponse> topUpNRC = new ArrayList<>();
//    public static TopUpResponse topUpResponse;
    private CurrentUserData userData;

    private TopUpResponse mTopUpResponse;

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

        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        planAndTopupViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);

        getTopUpList();
        selectTopup(getResources().getColor(R.color.back_color), getResources().getColor(R.color.not_selected_color));
    }

    public void addTopUpToInVoice(String amount) {
        if (Constant.isInternetConnected(this) && mTopUpResponse!=null) {
            SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            CurrentUserData user = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
            AddTopUpRequest addTopUpRequest = new AddTopUpRequest();
            addTopUpRequest.setAuthkey(BuildConfig.AUTH_KEY);
            addTopUpRequest.setAction(ADD_TOPUP);
            addTopUpRequest.setAmount(amount);
            addTopUpRequest.setCanID(user.CANId);
            addTopUpRequest.setTopupName(mTopUpResponse.getTopup_name());
            addTopUpRequest.setTopupType(mTopUpResponse.getType());
            spectraViewModel.addTopUp(addTopUpRequest).observe(TopUpActivity.this, TopUpActivity.this::consumeResponse);
        }
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

    private void getProDataTopup(String topUpId) {
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        if (Constant.isInternetConnected(this)) {
            PostProDataTopUpRequest postProDataTopUpRequest = new PostProDataTopUpRequest();
            postProDataTopUpRequest.setAuthkey(BuildConfig.AUTH_KEY);
            postProDataTopUpRequest.setCanId(userData.CANId);
            postProDataTopUpRequest.setAction(PRO_DATA_CHARGES_TOPUP_PLAN);
            postProDataTopUpRequest.setTopupId(topUpId);
            planAndTopupViewModel.getProDataTopUp(postProDataTopUpRequest).observe(TopUpActivity.this, TopUpActivity.this::consumeResponse);
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
                        pushFragments("TopupBaseFragmentRC",true,false);
                        setCounter(1);

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
            else if(PRO_DATA_CHARGES_TOPUP_PLAN.equals(code)){
                setCounter(2);
                ProTopupResponse proTopupResponse= (ProTopupResponse) response;
                if(proTopupResponse!=null && proTopupResponse.getResponse()!=null) {
                    TopUpResponse topUpResponse = proTopupResponse.getResponse();
                    if(proTopupResponse!=null){
                        mTopUpResponse.setPgDataCharges(topUpResponse.getPgDataCharges());
                        mTopUpResponse.setProDataCharges(topUpResponse.getProDataCharges());
                    }
                    setCounter(2);
                    pushFragments("TopupAmountToPay", true, false);
                }
            }else if(ADD_TOPUP.equals(code)){
                AddTopUpResponse addTopUpResponse = (AddTopUpResponse) response;
                if (addTopUpResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    setCounter(3);
                    pushFragments("TopupPaymentDone",true,false);
                }
                Constant.MakeToastMessage(TopUpActivity.this, addTopUpResponse.getMessage());
            }
        }
    }


    public void showEmptyMassage(String message, boolean isShow) {
        if(isShow) {
            no_internet.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            txt_payment.setText(message);
        }else {
            no_internet.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }
    }

    public void pay(TopUpResponse topUpResponse) {
        mTopUpResponse = topUpResponse;
        getProDataTopup(topUpResponse.getTopup_id());
    }

    public void gotoPayment(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("DATA",mTopUpResponse);
            Intent intent_pay = new Intent(TopUpActivity.this, PayNowActivity.class);
            intent_pay.putExtra("email", userData.Email);// "vineet.17feb@gmail.com"
            intent_pay.putExtra("mobile", userData.Number);
            intent_pay.putExtra("type", "unpaid");
            intent_pay.putExtra("payableAamount", amount);
            intent_pay.putExtra("subType", "topup");
            intent_pay.putExtra("TopUpActivity","TopUpActivity");
            intent_pay.putExtra("canID", userData.CANId);
            intent_pay.putExtras(bundle);
            startActivityForResult(intent_pay, 121);
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

    @OnClick({R.id.img_back_btn, R.id.layout_oneTime, R.id.layout_recurring})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back_btn) {
            finish();
        }
        else if(view.getId() == R.id.layout_oneTime) {
            setCounter(1);
            tvMessage.setText(R.string.oneTimePopMessage);
            selectTopup(getResources().getColor(R.color.back_color), getResources().getColor(R.color.not_selected_color));
            if (topUpRC.size() > 0) {
                showEmptyMassage("",false);
                pushFragments("TopupBaseFragmentRC",true,false);
            }else {
                tvMessage.setText(R.string.oneTimePopMessage);
                showEmptyMassage("No offer available",true);
            }
        }
        else if(view.getId() == R.id.layout_recurring) {
            setCounter(1);
            tvMessage.setText(R.string.recuringMassage);
            selectTopup(getResources().getColor(R.color.not_selected_color),getResources().getColor(R.color.back_color));
            if (topUpNRC.size() > 0) {
                showEmptyMassage("",false);
                pushFragments("TopupBaseFragmentNRC",true,false);
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
    }


    public void pushFragments(String fragmentName, boolean animate, boolean shouldAdd) {
        Bundle bundle;
        switch (fragmentName){

            case "TopupBaseFragmentRC":
                bundle = new Bundle();
                bundle.putSerializable("DATA",topUpRC);
                fragment = new TopupBaseFragment();
                fragment.setArguments(bundle);
                break;

            case "TopupBaseFragmentNRC":
                bundle = new Bundle();
                bundle.putSerializable("DATA",topUpNRC);
                fragment = new TopupBaseFragment();
                fragment.setArguments(bundle);
                break;


            case "TopupAmountToPay":
                bundle = new Bundle();
                bundle.putSerializable("DATA",mTopUpResponse);
                fragment = new TopupAmountToPayFragment();
                fragment.setArguments(bundle);
                break;

            case "TopupPaymentDone":
                bundle = new Bundle();
                fragment = new TopupPaymentDoneFragment();
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

    public void pullFragments(FragmentActivity activity, boolean animate, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (animate) { //For animation
            ft.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        }
        manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragment = null;
    }

    private void setCounter(int step){
        switch (step){
            case 1:
                tv_step_one.setBackground(getDrawable(R.drawable.login_background));
                tv_step_one.setTextColor(getResources().getColor(R.color.white));
                view_first.setBackgroundColor(getResources().getColor(R.color.white));
                tv_step_two.setBackground(getDrawable(R.drawable.login_background_white));
                tv_step_two.setTextColor(getResources().getColor(R.color.timer_color));
                view_sec.setBackgroundColor(getResources().getColor(R.color.white));
                tv_step_three.setBackground(getDrawable(R.drawable.login_background_white));
                tv_step_three.setTextColor(getResources().getColor(R.color.timer_color));
                findViewById(R.id.layout_buyTopup).setVisibility(View.VISIBLE);
                break;

            case 2:
                tv_step_one.setBackground(getDrawable(R.drawable.login_background));
                tv_step_one.setTextColor(getResources().getColor(R.color.white));
                view_first.setBackgroundColor(getResources().getColor(R.color.timer_color));
                tv_step_two.setBackground(getDrawable(R.drawable.login_background));
                tv_step_two.setTextColor(getResources().getColor(R.color.white));
                view_sec.setBackgroundColor(getResources().getColor(R.color.white));
                tv_step_three.setBackground(getDrawable(R.drawable.login_background_white));
                findViewById(R.id.layout_buyTopup).setVisibility(View.GONE);
                break;

            case 3:
                tv_step_one.setBackground(getDrawable(R.drawable.login_background));
                tv_step_one.setTextColor(getResources().getColor(R.color.white));
                view_first.setBackgroundColor(getResources().getColor(R.color.timer_color));
                tv_step_two.setBackground(getDrawable(R.drawable.login_background));
                tv_step_two.setTextColor(getResources().getColor(R.color.white));
                view_sec.setBackgroundColor(getResources().getColor(R.color.timer_color));
                tv_step_three.setBackground(getDrawable(R.drawable.login_background));
                tv_step_three.setTextColor(getResources().getColor(R.color.white));
                findViewById(R.id.layout_buyTopup).setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==121){
            if(resultCode == Activity.RESULT_OK){
                setCounter(3);
                pushFragments("TopupPaymentDone",true,false);
            }
        }

    }
}


  /*  public void startPayment(String amount){
        if (!TextUtils.isEmpty(topUpResponse.getPg_price())) {
          *//*  Intent intent_pay = new Intent(TopUpActivity.this, PayNowActivity.class);
            intent_pay.putExtra("email", userData.Email);
            intent_pay.putExtra("mobile", userData.Number);
            intent_pay.putExtra("type", "unpaid");
            intent_pay.putExtra("payableAamount", topUpResponse.getPg_price());
            intent_pay.putExtra("subType", "topup");
            intent_pay.putExtra("canID", userData.CANId);
            intent_pay.putExtra("TopUpActivity","TopUpActivity");
            startActivityForResult(intent_pay, 121);*//*
            Intent intent_pay = new Intent(TopUpActivity.this, PayNowActivity.class);
            if (!TextUtils.isEmpty(amount)) {
                intent_pay.putExtra("email", userData.Email);
                intent_pay.putExtra("mobile", userData.Number);
                intent_pay.putExtra("payableAamount", amount);
                intent_pay.putExtra("canID", userData.CANId);
                intent_pay.putExtra("type", "unpaid");
                intent_pay.putExtra("subType", "topup");
                intent_pay.putExtra("TopUpActivity","TopUpActivity");
                startActivityForResult(intent_pay,121);
            }
        } else {
            Constant.MakeToastMessage(this, "Payable amount can't be 0");
        }
    }*/