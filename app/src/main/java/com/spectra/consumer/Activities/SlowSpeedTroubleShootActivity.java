package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.spectra.consumer.Fragments.SSSpeedResultLANFragment;
import com.spectra.consumer.Fragments.SSSpeedResultWiFiFragment;
import com.spectra.consumer.Fragments.SSWifiIntenoTroubleShootFragment;
import com.spectra.consumer.Fragments.SSWifiNotDetectFragment;
import com.spectra.consumer.Fragments.SSWifiRestartTroubleShootFragment;
import com.spectra.consumer.Fragments.SSWifiSelectRouterFragment;
import com.spectra.consumer.Fragments.SpeedTestFragment;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.Utils.SiUtils;
import com.spectra.consumer.databinding.ActivitySlowspeedTrobleshootBinding;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.PostSS2_4GHzRequest;
import com.spectra.consumer.service.model.Request.PostSS5GHzRequest;
import com.spectra.consumer.service.model.Request.PostSSLANRequest;
import com.spectra.consumer.service.model.Response.InternetNotWorkModelDTO;
import com.spectra.consumer.service.model.Response.ResponseDTO;
import com.spectra.consumer.viewModel.FDSSViewModel;
import com.spectra.consumer.viewModel.NetworkStateViewModel;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.NO_FDSS_INTERNET;

public class SlowSpeedTroubleShootActivity extends FragmentActivity {

    private Fragment fragment = null;
    private ActivitySlowspeedTrobleshootBinding activityBinding;
    private NetworkStateViewModel networkStateViewModel;
    private FDSSViewModel fdssViewModel;
    private String canId;
    private CurrentUserData userData;
    private String productType = "";
    private int pStatus = 0;
    private int vocNumber = 3;
    private String url = null;
    private String selectedRouter;
    private String subtype = "Slow Speed";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slowspeed_trobleshoot);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_slowspeed_trobleshoot);
        networkStateViewModel = ViewModelProviders.of(this).get(NetworkStateViewModel.class);
        fdssViewModel = ViewModelProviders.of(this).get(FDSSViewModel.class);
//        showLoadingView(true);
//        networkStateViewModel.isOnlineStatus(getApplication()).observe(this, s -> {
//            if(fragment==null) {
//                showLoadingView(false);
//                if (s != null && s.equalsIgnoreCase("TRANSPORT_WIFI")) {
//                    pushFragments("SSRouterSelect", true, false);
//                } else {
//                    pushFragments("SSWifiNotDetect", true, false);
//                }
//            }
//        });
        if(SiUtils.isInternetViaWifiConnected(SlowSpeedTroubleShootActivity.this)){
            pushFragments("SSRouterSelect", true, false);
        }else{
            pushFragments("SSWifiNotDetect", true, false);
        }
        networkStateViewModel.getSingleNetworkState();
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        canId = userData.CANId;//"167238";
        url = "getfdss/canId/"+canId+"/voc/"+vocNumber;
    }

    public void pushFragments(String fragmentName, boolean animate, boolean shouldAdd) {
        switch (fragmentName){
            case "SSRouterSelect":
                fragment = new SSWifiSelectRouterFragment();
                break;

            case "SSWifiNotDetect":
                fragment =  new SSWifiNotDetectFragment();
                break;

            case "SSSpeedResultLANFragment":
                fragment =  new SSSpeedResultLANFragment();
                break;

            case "SSSpeedResultWiFiFragment":
                Bundle bundle = new Bundle();
                bundle.putString("ROUTER",selectedRouter);
                fragment =  new SSSpeedResultWiFiFragment();
                fragment.setArguments(bundle);
                break;

            case "SpeedTestFragment":
                fragment =  new SpeedTestFragment();
                break;

            case "SSWifiRestart":
                fragment = new SSWifiRestartTroubleShootFragment();
                break;

            case "SSWifiINTENORestart":
                fragment = new SSWifiIntenoTroubleShootFragment();
                break;
        }

        if(fragment!=null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            if (animate) { //For animation
                ft.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
            }
            if (shouldAdd) { //for add in stack
                ft.replace(activityBinding.layFrame.getId(), fragment, fragmentName);
                ft.addToBackStack(fragmentName);
                ft.commitAllowingStateLoss();
            } else {
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.replace(activityBinding.layFrame.getId(), fragment, fragmentName);
                ft.commit();
            }
        }
    }

    public void pullFragments(FragmentActivity activity,boolean animate, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (animate) { //For animation
            ft.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out);
        }
        manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragment = null;
    }

    private void backToHome(String message) {
        activityBinding.rlAcountDeactive.setVisibility(View.GONE);
        activityBinding.llBackToHome.setVisibility(View.VISIBLE);
        activityBinding.tvBackToHomeTitle.setText(Html.fromHtml(message));
        activityBinding.tvBackToHome.setOnClickListener(v -> goToHome());
    }

    private void goToHome() {
        Intent intent = new Intent(SlowSpeedTroubleShootActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
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

    private void showLoadingView(boolean visible) {
        if (visible) {
            activityBinding.pro2.setVisibility(View.VISIBLE);
        } else {
            activityBinding.pro2.setVisibility(View.GONE);
        }
    }


    @SuppressLint("SetTextI18n")
    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            switch (code) {
                case NO_FDSS_INTERNET:
                    InternetNotWorkModelDTO responseDTO = (InternetNotWorkModelDTO) response;
                    if (responseDTO.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        pStatus = 100;
                        ResponseDTO response1 = responseDTO.getResponse();
                        int problumCode = Integer.parseInt(response1.getMessageCode());
                        if (problumCode > 299) {
                            backToHome(response1.getMessageDescription());
                            return;
                        }

                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            String text ;
                            switch (problumCode) {
                                case 249:
                                    if(response1.getSubType()!=null && response1.getType()!=null)
                                        subtype = response1.getType()+"-"+response1.getSubType();

                                    activityBinding.speedView.setVisibility(View.GONE);
                                    activityBinding.laySpeedyesno.setVisibility(View.GONE);
                                    activityBinding.tvSpeedOkay.setVisibility(View.VISIBLE);
                                    activityBinding.tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    activityBinding.rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text = "Service Request no. "+response1.getSrNo()+" for "+subtype+" registered and assigned to Technical team.\nResolution time <b>"+ SiUtils.getETRDate(response1.getEtr())+"</b>"; //dd/mm/yy hh:mm hours
                                    //text ="There seems to be an issue which needs to be investigated by our technical experts. Service Request no. "+ response1.getSrNo() +" of "+subtype+" has been raised and dispatched to our technical team for quick resolution.The resolution time is <b>"+response1.getEtr()+"</b>";
                                    activityBinding.tvSpeedTitle.setText(Html.fromHtml(text));
                                    break;

                                case 264:
                                    activityBinding.speedView.setVisibility(View.GONE);
                                    activityBinding.laySpeedyesno.setVisibility(View.GONE);
                                    activityBinding.tvSpeedOkay.setVisibility(View.VISIBLE);
                                    activityBinding.tvSpeedOkay.setText(getString(R.string.back_to_home_btn));
                                    activityBinding.tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    activityBinding.rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text =  response1.getMessageDescription();
//                                    text= "You are getting proper speed at current band. Please change to 5Ghz to get more speed.<br><br>Thank you";
                                    activityBinding.tvSpeedTitle.setText(Html.fromHtml(text));
                                    break;

                                case 256:
                                    activityBinding.speedView.setVisibility(View.GONE);
                                    activityBinding.laySpeedyesno.setVisibility(View.GONE);
                                    activityBinding.tvSpeedOkay.setText(getString(R.string.back_to_home_btn));
                                    activityBinding.tvSpeedOkay.setVisibility(View.VISIBLE);
                                    activityBinding.tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    activityBinding.rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text="You are getting proper speed as per your plan. <br><br>Thank you.";
                                    activityBinding.tvSpeedTitle.setText(Html.fromHtml(text));
                                    break;

                            }
                        }, 1500);
                    }
                    break;
            }
        }
    }

    public void pushFragmentForLan(){
        pushFragments("SSSpeedResultLANFragment", true, false);
    }


    public void pushFragmentForWifi(String router){
        selectedRouter = router;
        pushFragments("SSSpeedResultWiFiFragment", true, false);
    }

    public void pushFragmentForSpeedTest(){
        pushFragments("SpeedTestFragment", true, true);
    }


    public void pushFragmentForIntenoRestart(){
        pushFragments("SSWifiINTENORestart",true,false);
    }

    public void pushFragmentForSSWifiRestart(){
        pushFragments("SSWifiRestart", true, false);
    }

    public void apiTrobleShootForLAN(String value) {
        if (Constant.isInternetConnected(this)) {
            PostSSLANRequest postSSLANRequest = new PostSSLANRequest();
            postSSLANRequest.setSpeedOnLan(value);
            fdssViewModel.fupYesORNO(url, postSSLANRequest, NO_FDSS_INTERNET).observe(SlowSpeedTroubleShootActivity.this, SlowSpeedTroubleShootActivity.this::consumeResponse);
        }
    }

    public void apiTrobleShootForWIFI2_4GHz(String value) {
        if (Constant.isInternetConnected(this)) {
            PostSS2_4GHzRequest postSS24GHzRequest = new PostSS2_4GHzRequest();
            postSS24GHzRequest.setSpeedOn2_4Ghz(value);
            fdssViewModel.fupYesORNO(url, postSS24GHzRequest, NO_FDSS_INTERNET).observe(SlowSpeedTroubleShootActivity.this, SlowSpeedTroubleShootActivity.this::consumeResponse);
        }
    }

    public void apiTrobleShootForWIFI5GHz(String value) {
        if (Constant.isInternetConnected(this)) {
            PostSS5GHzRequest postSS5GHzRequest = new PostSS5GHzRequest();
            postSS5GHzRequest.setSpeedOn5Ghz(value);
            fdssViewModel.fupYesORNO(url, postSS5GHzRequest, NO_FDSS_INTERNET).observe(SlowSpeedTroubleShootActivity.this, SlowSpeedTroubleShootActivity.this::consumeResponse);
        }
    }


    @Override
    public void onBackPressed() {
        if(fragment!=null && fragment instanceof SpeedTestFragment){
            pullFragments(this,true,"SpeedTestFragment");
        }else {
            super.onBackPressed();
        }
    }
}
