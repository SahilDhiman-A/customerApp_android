package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.components.Section;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.Utils.SiUtils;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
import com.spectra.consumer.service.model.Request.GetSrRequest;
import com.spectra.consumer.service.model.Request.PostFDSSFacingIssueRequest;
import com.spectra.consumer.service.model.Request.PostFDSSFluctuatingLightRequest;
import com.spectra.consumer.service.model.Request.PostFUPFlagRequest;
import com.spectra.consumer.service.model.Request.PostInternetWorkingFDSS;
import com.spectra.consumer.service.model.Request.PostMRTGRequest;
import com.spectra.consumer.service.model.Response.GetSrStatusResponse;
import com.spectra.consumer.service.model.Response.InternetNotWorkModelDTO;
import com.spectra.consumer.service.model.Response.InvoiceDTO;
import com.spectra.consumer.service.model.Response.ResponseDTO;
import com.spectra.consumer.service.model.Response.SrReponse;
import com.spectra.consumer.viewModel.FDSSViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.CHECK_SR;
import static com.spectra.consumer.service.repository.ApiConstant.CREATE_SR;
import static com.spectra.consumer.service.repository.ApiConstant.GET_SR_STATUS;
import static com.spectra.consumer.service.repository.ApiConstant.NO_FDSS_INTERNET;
import static com.spectra.consumer.service.repository.ApiConstant.NO_INTERNET;

public class FDSSInternet extends AppCompatActivity {

    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvTROUBLESHOOT)
    TextView tvTROUBLESHOOT;
    @BindView(R.id.llMyInternetNotWorking)
    RelativeLayout llMyInternetNotWorking;
    @BindView(R.id.rlTrubbleShootProgressbar)
    RelativeLayout rlTrubbleShootProgressbar;
    @BindView(R.id.rlAcountDeactive)
    RelativeLayout rlAcountDeactive;
    @BindView(R.id.llSr)
    RelativeLayout llSr;
    @BindView(R.id.tvSelectOptTitle)
    TextView tvSelectOptTitle;
    @BindView(R.id.etSrMessage)
    EditText etSrMessage;
    @BindView(R.id.tvSubmitSr)
    TextView tvSubmitSr;
    @BindView(R.id.tvNo)
    TextView tvNo;
    @BindView(R.id.tvYes)
    TextView tvYes;
    @BindView(R.id.ivProgress)
    ImageView ivProgress;
    @BindView(R.id.tvBackToHomeTitle)
    TextView tvBackToHomeTitle;
    @BindView(R.id.tvBackToHome)
    TextView tvBackToHome;
    @BindView(R.id.llBackToHome)
    RelativeLayout llBackToHome;
    @BindView(R.id.rlInvoiceDetail)
    RelativeLayout rlInvoiceDetail;

    @BindView(R.id.fd_trouble)
    RelativeLayout fd_trouble;

    @BindView(R.id.ss_trouble)
    RelativeLayout ss_trouble;

    @BindView(R.id.tv_fd_trouble)
    TextView tvFDTrouble;

    @BindView(R.id.single_box_layout)
    RelativeLayout single_box_layout;

    @BindView(R.id.multi_box_layout)
    RelativeLayout multi_box_layout;

    @BindView(R.id.img_info)
    ImageView imgInfo;

    @BindView(R.id.txt_cross)
    TextView txtCrossImageInfo;

    @BindView(R.id.txt_speed_cross)
    TextView speedTxtCrossView;

    @BindView(R.id.layout_box_info)
    RelativeLayout layoutBoxImageInfo;

    @BindView(R.id.tvDisHint)
    TextView tvDisHint;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    @BindView(R.id.tv_ss_trouble)
    TextView tvSSTrouble;

/*    @BindView(R.id.img_payment_status)
    AppCompatImageView imgCatFDSSImageView;*/

    @BindView(R.id.img_payment_status_ss)
    AppCompatImageView imgCatFDSSImageViewSS;

    @BindView(R.id.iv_single_box)
    ImageView ivSingleBoxView;

    @BindView(R.id.tv_single_box)
    TextView tvSingleBox;


    @BindView(R.id.fdss_txt_cross)
    TextView fdssCrossTextView;

    @BindView(R.id.rlFdssLayout)
    RelativeLayout fdss_layout;

    @BindView(R.id.fd_ss_box_info)
    RelativeLayout fdssBoxInfo;

    @BindView(R.id.fd_ss_img_info)
    ImageView fdssImageInfo;

    @BindView(R.id.layyesno)
    RelativeLayout layyesno;
    @BindView(R.id.tvOkay)
    TextView tvOkay;

    @BindView(R.id.speedView)
    SpeedView speedView;

    @BindView(R.id.tvSpeedOkay)
    TextView tvSpeedOkay;
    @BindView(R.id.tvSpeedNo)
    TextView tvSpeedNo;
    @BindView(R.id.tvSpeedYes)
    TextView tvSpeedYes;
    @BindView(R.id.laySpeedyesno)
    RelativeLayout laySpeedyesno;

    @BindView(R.id.layout_speed_box_info)
    RelativeLayout layout_speed_box_info;

    @BindView(R.id.speed_img_info)
    ImageView speed_img_info;

    @BindView(R.id.rlFdssSpeedLayout)
    RelativeLayout rlFdssSpeedLayout;

    @BindView(R.id.tvSpeedTitle)
    TextView speedTitle;

    @BindView(R.id.txt_end_date)
    TextView txt_end_date;
    @BindView(R.id.txt_invoice_amount)
    TextView txt_invoice_amount;
    @BindView(R.id.txt_invoice_number)
    TextView txt_invoice_number;
    @BindView(R.id.txt_invoice_date)
    TextView txt_invoice_date;
    @BindView(R.id.txt_start_date)
    TextView txt_start_date;
    @BindView(R.id.txt_due_date)
    TextView txt_due_date;
    @BindView(R.id.progressTitle)
    TextView progressTitle;
    @BindView(R.id.progressTitleBelow)
    TextView progressTitleBelow;

    @BindView(R.id.tvBackToHomeSr)
    TextView tvBackToHomeSr;
    @BindView(R.id.txtExpectedResolutionTime)
    TextView txtExpectedResolutionTime;
    @BindView(R.id.txt_sr_number)
    TextView txt_sr_number;
    @BindView(R.id.txt_Created_date)
    TextView txt_Created_date;
    @BindView(R.id.txtProblemType)
    TextView txtProblemType;
    @BindView(R.id.txtCreatedTime)
    TextView txtCreatedTime;
    @BindView(R.id.rlSrDetail)
    RelativeLayout rlSrDetail;
    @BindView(R.id.txCat)
    TextView txCat;

    @BindView(R.id.txt_prob_sub_type)
    TextView txt_prob_sub_type;

    @BindView(R.id.layout_ss_box_info)
    RelativeLayout layoutSSBoxInfo;

    @BindView(R.id.txt_ss_cross)
    TextView tvSSCrossInfo;

    @BindView(R.id.img_ss_info)
    ImageView imgSSInfo;

    @BindView(R.id.pro2)
    ProgressBar pro2;
    @BindView(R.id.img_cross)
    ImageView img_cross;
    private CurrentUserData userData;
    private String canId;
    SpectraViewModel spectraViewModel;
    FDSSViewModel fdssViewModel;
    private String productType = "", safeCastdyAmount = "";
    private int pStatus = 0;
    private Handler handler = new Handler();
    ResponseDTO response1;
    boolean isPatner = false;
    int codeAPi = 0;
    private int vocNumber = 2;
    private String url = null;
    //    private String url = "getInternetNotWorking/authkey/01a62ae9623beb096ec88dca3836858c/canId/";
    private boolean openImageInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fdss_layout);
        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        findViewById(R.id.img_back).setVisibility(View.GONE);
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        canId = userData.CANId;//"167238";//"";
//        CAN ID	Type
//        9056012	OpenSR Done
//        188705	FUPActive Done
//        9056012	Mass Outage Done
//        9019453	checkMRTG
//        188500	Gpon with -9.99 LOS
//        188346	Gpon with -30 LOSI
//        160915	Gpon with -40 non dgi (LOS)
//        188526	Gpon with -40 dgi
//        167238	Gpon with -10 LOS
//        9053646	Non Gpon
//        209903	Non Gpon
//        9066601   80%
//        canId = 9056012+"";
        if(getIntent()!=null && getIntent().hasExtra("VOC")) {
            vocNumber = getIntent().getIntExtra("VOC",2);
        }

        if(vocNumber==2){
            fd_trouble.setVisibility(View.VISIBLE);
            tvFDTrouble.setOnClickListener( v -> {
                        if(!openImageInfo) {
                            getIssue();
                        }
                    }
            );
            imgInfo.setOnClickListener(v -> fdssImageInfo(true));
            fdssCrossTextView.setOnClickListener(v -> {
                fdssImageInfo(false);
            });
        }else if(vocNumber==3){
            ss_trouble.setVisibility(View.VISIBLE);
            tvSSTrouble.setOnClickListener( v -> {
                        if(!openImageInfo) {
                            getIssue();
                        }
                    }
            );
            imgSSInfo.setOnClickListener(v -> fdssImageInfo(true));
            tvSSCrossInfo.setOnClickListener(v -> fdssImageInfo(false));
        }
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        fdssViewModel  = ViewModelProviders.of(this).get(FDSSViewModel.class);
        url = "getfdss/canId/"+canId+"/voc/"+vocNumber;
    }

    private void fdssImageInfo(boolean value){

        if(vocNumber==2) {
            if (value) {
                layoutBoxImageInfo.setVisibility(View.VISIBLE);
            } else {
                layoutBoxImageInfo.setVisibility(View.GONE);
            }
        } else if(vocNumber==3) {
            if (value) {
                layoutSSBoxInfo.setVisibility(View.VISIBLE);
            } else {
                layoutSSBoxInfo.setVisibility(View.GONE);
            }
        }
        openImageInfo = value;
    }

    private void setprogressBar() {
        llMyInternetNotWorking.setVisibility(View.GONE);
        rlTrubbleShootProgressbar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            while (pStatus <= 100) {
                handler.post(() -> {
                    progressBar.setProgress(pStatus);
                    float rotat = (float) (3.6 * pStatus);
                    ivProgress.setRotation(rotat);
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pStatus < 75) {
                    pStatus++;
                }
            }
        }).start();
    }

    public void getIssue() {
        if (Constant.isInternetConnected(this)) {
            fdssViewModel.getFDSSNoInternet(url, NO_FDSS_INTERNET).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse2);
        }
    }

    private void consumeResponse2(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                setprogressBar();
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                break;
            default:
                break;
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

    @SuppressLint("SetTextI18n")
    private void renderSuccessResponse(Object response, String code) {
        GetSrStatusResponse getSrStatusResponse;
        SrReponse srReponse;
        String massage;
        if (response != null) {
            switch (code) {

                case GET_SR_STATUS:
                    getSrStatusResponse = (GetSrStatusResponse) response;
                    if (getSrStatusResponse.getResponse().size() > 0) {
                        srReponse = getSrStatusResponse.getResponse().get(0);
                        massage = getSrDate(srReponse.getETR());
                        massage = "<big><b>" + massage + "<b></big> ";
                        if (codeAPi == 216) {
                            massage = "Oops.. we have found an issue<br/>in your connectivity.<br/>Service Request No." + srReponse.getSrNumber() + " of<br/> " + srReponse.getProblemType() + "-" + srReponse.getSubType() + " has been<br/>raised and dispatched to our<br/>technical team for quick<br/>resolution. The resolution time is <br/> " + massage;
                        } else {
                            massage = "There seems to be an issue<br/>which needs to be<br/>investigated by our technical<br/>experts.<br/><br/>Service Request No." + srReponse.getSrNumber() + " of<br/> " + srReponse.getProblemType() + "-" + srReponse.getSubType() + " has been<br/>raised and dispatched to our<br/>technical team for quick<br/>resolution. The resolution time is <br/> " + massage;
                        }
                        backToHome(massage);
                    } else {
                        backToHome("Oops. Something went wrong. Please try again later.");
                    }
                    break;

                case CHECK_SR:
                    getSrStatusResponse = (GetSrStatusResponse) response;
                    if (getSrStatusResponse.getResponse().size() > 0) {
                        srReponse = getSrStatusResponse.getResponse().get(0);
                        srDetails(srReponse);
                    } else {
                        backToHome("Oops. Something went wrong. Please try again later.");
                    }
                    break;

                case CREATE_SR:
                    goToHome();
                    break;

                case NO_FDSS_INTERNET:
                    InternetNotWorkModelDTO responseDTO = (InternetNotWorkModelDTO) response;
                    if (responseDTO.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        pStatus = 100;
                        progressBar.setProgress(100);
                        ResponseDTO response1 = responseDTO.getResponse();
//                        {"responseCode":"210","status":"success","response":{"problemType":"IssueNotResolved","problemStatus":"True","srNo":"SR20092529688","type":"Network","subType":"Frequent Disconnection","etr":"01\/01\/1970 05:30 AM","messageCode":"249","messageDescription":"Service Request no. SR20092529688 for Network-Frequent Disconnection registered and assigned to Technical team. Resolution time 01\/01\/1970 05:30 AM"}}
//                        {"responseCode":"210","status":"success","response":{"problemType":"IssueNotResolved","problemStatus":"True","srNo":"SR20092529688","type":"Network","subType":"Frequent Disconnection","etr":"01\/01\/1970 05:30 AM","messageCode":"243","messageDescription":"Service Request no. SR20092529688 for Network-Frequent Disconnection registered and assigned to Technical team. Resolution time 01\/01\/1970 05:30 AM"}}
//                        response1.setMessageCode("252");
//                        response1.setUtilizationPercentage("85");
//                        response1.setProblemType("IssueNotResolved");
//                        response1.setSrNo("SR20092529688");
//                        response1.setEtr("01/01/1970 05:30 AM");
                        int problumCode = Integer.parseInt(response1.getMessageCode());
                        if (problumCode > 299) {
                            backToHome(response1.getMessageDescription());
                            return;
                        }
                        progressTitle.setText("");
                        progressTitleBelow.setText("Completed");
                        ivProgress.setRotation(0);
                        ivProgress.setImageResource(R.drawable.ic_done_arrow);
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            String text ;
                            rlTrubbleShootProgressbar.setVisibility(View.GONE);
                            switch (problumCode) {
                                case 214:
                                    codeAPi = 214;
                                    getSRDetails(response1);
                                    break;

                                case 241: // Problem Type : FUPFlag
                                    String type1 = "";
                                    if(vocNumber==2){
                                        type1 = "Frequent Disconnection";
                                        txCat.setText("Frequent Disconnection");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_unlink);
                                    }else if(vocNumber==3){
                                        type1 = "Slow Speed";
                                        txCat.setText("Slow Speed");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_slow);
                                    }
                                    layyesno.setVisibility(View.VISIBLE);
                                    tvOkay.setVisibility(View.GONE);
                                    tvYes.setText("YES");
                                    tvNo.setText("NO");
                                    tvYes.setOnClickListener(v -> fuPNoAPI("Yes"));
                                    tvNo.setOnClickListener(v ->  fuPNoAPI("No"));
                                    fdss_layout.setVisibility(View.VISIBLE);
                                    if(response1.getConsumedVolume()!=null){
                                        text = "You have exhausted your data quota of "+response1.getConsumedVolume()+ " hence you are facing frequent disconnection. Would you like to purchase a Top Up to avoid "+type1+"?";
                                    }
                                    else{
                                        text = "You have exhausted your data quota of hence you are facing frequent disconnection. Would you like to purchase a Top Up to avoid "+type1+"?";
                                    }
//                                  text = "Your bandwidth is overutilized. As suggested please purchase TOP UP to avoid any further usage issues.<br/><br/><br/>Do you want to purchase a TOP UP now?";
                                    tvSelectOptTitle.setText(Html.fromHtml(text));
                                    break;

                                case 242: // Handle NO case for getTopupNo (Problem Type : FUPFlag)
//                                    imgCatFDSSImageViewSS.setVisibility(View.INVISIBLE);
//                                    txCat.setVisibility(View.INVISIBLE);
                                    if(vocNumber==2){
                                        txCat.setText("Frequent Disconnection");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_unlink);
                                    }else if(vocNumber==3){
                                        txCat.setText("Slow Speed");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_slow);
                                    }
                                    tvOkay.setVisibility(View.VISIBLE);
                                    layyesno.setVisibility(View.GONE);
                                    tvOkay.setOnClickListener(v -> goToHome());
                                    fdss_layout.setVisibility(View.VISIBLE);
                                    String message = "Your data quota is currently exhausted and will be reset on your next bill cycle date. Your speed will continue to be "+ response1.getFupSpeed() +" till then.Kindly purchase our attractive Top-ups to enjoy enhanced speeds";
                                    tvSelectOptTitle.setText(Html.fromHtml(message));
                                    break;

                                case 240: // Handle Yes case for getTopupYes (Problem Type : FUPFlag)
                                    Intent selectplanintent = new Intent(FDSSInternet.this, TopUpActivity.class);
                                    startActivity(selectplanintent);
                                    finish();
                                    break;

                                //Massoutage
                                case 213:
                                    text = getSrDate213(response1.getEtr());
                                    text = "There is an outage in your<br/> area which is expected to<br/> be restored by<br/>" + text + "<br/><br/>Your internet will start working<br/> automatically after this time .<br/> Sorry for the inconvenience<br/> caused.";
                                    backToHome(text);
                                    break;

                                //checkMRTG
                                case 243: //Speed layout checked
                                    Float perFloat = Float.valueOf(response1.getUtilizationPercentage()).floatValue();
                                    int percentage = Math.round(perFloat);
                                    speedView.getSections().clear();
                                    speedView.addSections(new Section(0f, .4f, getResources().getColor(R.color.speedometer_green), speedView.dpTOpx(20f))
                                            , new Section(.4f, .7f, getResources().getColor(R.color.speedometer_yellow), speedView.dpTOpx(20f))
                                            , new Section(.7f, 1f, getResources().getColor(R.color.speedometer_red), speedView.dpTOpx(20f)));
                                    speedView.setWithTremble(false);
                                    speedView.speedTo(percentage, 4000);
                                    tvSpeedOkay.setVisibility(View.GONE);
                                    laySpeedyesno.setVisibility(View.VISIBLE);
                                    tvSpeedYes.setText("YES");
                                    tvSpeedNo.setText("NO");
                                    speed_img_info.setVisibility(View.GONE);
                                    tvSpeedYes.setOnClickListener(v -> apiHitForMRTG("Yes"));
                                    tvSpeedNo.setOnClickListener(v ->  apiHitForMRTG("No"));
                                    rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text = "Current bandwidth utilization more than 80%.<br/><br/><br/>Upgrade?";
                                    speedTitle.setText(Html.fromHtml(text));
                                    break;

                                // checkMRTG upgradeBandwidthYes
                                case 244://Speed layout
                                    speedView.setVisibility(View.GONE);
                                    laySpeedyesno.setVisibility(View.GONE);
                                    speed_img_info.setVisibility(View.GONE);
                                    tvSpeedOkay.setVisibility(View.VISIBLE);
                                    tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    String srNo = response1.getSrNo();
                                    text = "Thanks! <br/><br/><br/> Your Service request no is "+ srNo +" <br/><b>Team Spectra</b> <br/><br/><br/>We will call you with best offers at the earliest.";
                                    speedTitle.setText(Html.fromHtml(text));
                                    break;

                                // checkMRTG upgradeBandwidthNo
                                case 245://Speed layout Checked
                                    speedView.setVisibility(View.GONE);
                                    laySpeedyesno.setVisibility(View.GONE);
                                    speed_img_info.setVisibility(View.GONE);
                                    tvSpeedOkay.setVisibility(View.VISIBLE);
                                    tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text = "Your bandwidth utilisation is high.<br/><br/><br/> Upgrade the link to enjoy proper speed.";
                                    speedTitle.setText(Html.fromHtml(text));
                                    break;

                                //GPON “powerLevel”: “-9.99”,
                                case 249:
                                    String subtype = "";
                                    if(response1.getSubType()!=null && response1.getType()!=null)
                                        subtype = response1.getType()+"-"+response1.getSubType();
//                                    String subtype = null;
//                                    if(vocNumber==2){
//                                        subtype = "Frequent Disconnection";
//                                    }else if(vocNumber==3){
//                                        subtype = "Slow Speed";
//                                    }
                                    //if(response1.getProblemType()!=null && response1.getProblemType().equalsIgnoreCase("GPON")){
//                                        txCat.setText(subtype);
//                                        layyesno.setVisibility(View.GONE);
//                                        tvOkay.setVisibility(View.VISIBLE);
//                                        tvOkay.setOnClickListener(v -> goToHome());
//                                        fdss_layout.setVisibility(View.VISIBLE);
////                                      float powerLevel = Float.valueOf(response1.getPowerLevel()).floatValue();
//                                        text = "<b>Oops..</b> <br>We have found an issue in your connectivity. Service Request no. "+ response1.getSrNo() +" of "+subtype+" has been raised and dispatched to our technical team for quick resolution.The resolution time is <b>"+response1.getEtr()+"</b>";
//                                        tvSelectOptTitle.setText(Html.fromHtml(text));
                                    //if ( ( powerLevel >= -9.99) && (powerLevel<=0)) {
//                                        text = "Service Request no. "+ response1.getSrNo() +" for "+subtype+" registered and assigned to Technical team. The ETR is "+response1.getEndDate();
//                                        }else if(powerLevel>= -39.99 && powerLevel<= -30){
//                                            text = "Oops.. We have found an issue in your connectivity. Service Request no. "+ response1.getSrNo() +" of "+subtype+" has been raised and dispatched to our technical team for quick resolution.The resolution time is "+response1.getEndDate();
//                                        }else if(powerLevel<=-40){
                                    //
                                    // }else{
//                                            text = "There seems to be an issue which needs to be investigated by our technical experts. Service Request no. "+ response1.getSrNo() +" of "+subtype+" has been raised and dispatched to our technical team for quick resolution.The resolution time is "+response1.getEndDate();
//                                        }
                                    //  }else if(response1.getProblemType()!=null && response1.getProblemType().equalsIgnoreCase("IssueNotResolved")){
                                    speedView.setVisibility(View.GONE);
                                    laySpeedyesno.setVisibility(View.GONE);
                                    speed_img_info.setVisibility(View.GONE);
                                    tvSpeedOkay.setVisibility(View.VISIBLE);
                                    tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text = "Service Request no. "+response1.getSrNo()+" for "+subtype+" registered and assigned to Technical team.\nResolution time <b>"+ SiUtils.getETRDate(response1.getEtr())+"</b>"; //dd/mm/yy hh:mm hours
                                    //text ="There seems to be an issue which needs to be investigated by our technical experts. Service Request no. "+ response1.getSrNo() +" of "+subtype+" has been raised and dispatched to our technical team for quick resolution. The resolution time is <b>"+response1.getEtr()+"</b>";
                                    speedTitle.setText(Html.fromHtml(text));
                                    //}
                                    break;

                                //GPON “powerLevel”: -40 DGI,
                                case 247:
                                    if(vocNumber==2){
                                        txCat.setText("Frequent Disconnection");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_unlink);
                                    }else if(vocNumber==3){
                                        txCat.setText("Slow Speed");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_slow);
                                    }
                                    fdssImageInfo.setVisibility(View.VISIBLE);

                                    single_box_layout.setVisibility(View.GONE);
                                    multi_box_layout.setVisibility(View.VISIBLE);

                                    fdssImageInfo.setOnClickListener(v -> {
                                        openImageInfo = true;
                                        fdssBoxInfo.setVisibility(View.VISIBLE);
                                    });
                                    txtCrossImageInfo.setOnClickListener(v -> {
                                        openImageInfo = false;
                                        fdssBoxInfo.setVisibility(View.GONE);
                                    });

                                    layyesno.setVisibility(View.VISIBLE);
                                    tvOkay.setVisibility(View.GONE);
                                    tvYes.setVisibility(View.INVISIBLE);
                                    tvNo.setVisibility(View.INVISIBLE);
                                    tvYes.setOnClickListener(v -> {
                                        if(!openImageInfo)
                                            apiIsInternetWorking("Yes");
                                    });
                                    tvNo.setOnClickListener(v ->  {
                                        if(!openImageInfo)
                                            apiIsInternetWorking("No");
                                    });
                                    fdss_layout.setVisibility(View.VISIBLE);
                                    text = "ONT not plugged in properly. Replug the white ONT box and reboot Wifi Router/Switch and wait for a minute.";
                                    tvSelectOptTitle.setText(Html.fromHtml(text));
                                    timerWork(text, problumCode);
                                    break;

                                //GPON -40 DGI : isInternetWorking Yes
                                case 248://Speed layout
                                    speedView.setVisibility(View.GONE);
                                    speed_img_info.setVisibility(View.GONE);
                                    laySpeedyesno.setVisibility(View.GONE);
                                    tvSpeedOkay.setVisibility(View.VISIBLE);
                                    tvSpeedOkay.setOnClickListener(v -> goToHome());
                                    rlFdssSpeedLayout.setVisibility(View.VISIBLE);
                                    text = "<br><b>Great!</b><br><br> Please continue to enjoy Spectra’s fastest internet services.</br>";
                                    speedTitle.setText(Html.fromHtml(text));
                                    break;

                                //Partner
                                case 250:
                                    if(vocNumber==2){
                                        txCat.setText("Frequent Disconnection");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_unlink);
                                    }else if(vocNumber==3){
                                        txCat.setText("Slow Speed");
                                        imgCatFDSSImageViewSS.setBackgroundResource(R.drawable.ic_slow);
                                    }

                                    fdssImageInfo.setVisibility(View.VISIBLE);
                                    single_box_layout.setVisibility(View.VISIBLE);
                                    multi_box_layout.setVisibility(View.GONE);

                                    ivSingleBoxView.setBackgroundResource(R.drawable.wifi_light);
                                    tvSingleBox.setText(getString(R.string.wan_light));

                                    fdssImageInfo.setOnClickListener(v -> {
                                        openImageInfo = true;
                                        fdssBoxInfo.setVisibility(View.VISIBLE);
                                    });

                                    txtCrossImageInfo.setOnClickListener(v -> {
                                        openImageInfo = false;
                                        fdssBoxInfo.setVisibility(View.GONE);
                                    });

                                    layyesno.setVisibility(View.VISIBLE);
                                    tvOkay.setVisibility(View.GONE);
                                    tvYes.setOnClickListener(v -> {
                                        if(!openImageInfo)
                                            apiFluctuatingLight("Yes");
                                    });
                                    tvNo.setOnClickListener(v ->  {
                                        if(!openImageInfo)
                                            apiFluctuatingLight("No");
                                    });
                                    fdss_layout.setVisibility(View.VISIBLE);
                                    text = "<br>Is the WAN light on your WiFi Router fluctuating?<br>";
                                    tvSelectOptTitle.setText(Html.fromHtml(text));
                                    break;

                                case 251: // Partner No
                                    speedView.setVisibility(View.GONE);
                                    speed_img_info.setVisibility(View.VISIBLE);
                                    laySpeedyesno.setVisibility(View.VISIBLE);
                                    tvSpeedOkay.setVisibility(View.GONE);
                                    tvSpeedYes.setVisibility(View.GONE);
                                    tvSpeedNo.setVisibility(View.GONE);
                                    rlFdssSpeedLayout.setVisibility(View.VISIBLE);

                                    speed_img_info.setOnClickListener(v -> {
                                        openImageInfo = true;
                                        layout_speed_box_info.setVisibility(View.VISIBLE);
                                    });

                                    speedTxtCrossView.setOnClickListener(v -> {
                                        openImageInfo = false;
                                        layout_speed_box_info.setVisibility(View.GONE);
                                    });

                                    text = "Spectra connectivity to your premises is fine.<br><br> Please reboot your Wi-Fi router and wait";
                                    speedTitle.setText(Html.fromHtml(text));
                                    timerWork(text, problumCode);
                                    break;

                                case 252: // Slow Speed
                                    gotoSlowSpeedTrobleShootScreen();
                                    break;
                            }
                        }, 1500);
                    }
                    break;
            }
        }
    }

    private String getAmount(String amount) {
        try {
            double amountValue = Double.parseDouble(amount);
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            amount = nf.format(amountValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    @SuppressLint("SetTextI18n")
    private void timerWork(final String text2, int code) {
        long time = 60000;
        //long time = 60;
        tvYes.setVisibility(View.GONE);
        tvNo.setVisibility(View.GONE);
        tvYes.setText("YES");
        tvNo.setText("NO");
        switch (code) {
            case 247:
                tvYes.setOnClickListener(v -> {
                    if(!openImageInfo)
                        apiIsInternetWorking( "Yes");
                });
                tvNo.setOnClickListener(v -> {
                    if(!openImageInfo)
                        apiIsInternetWorking( "No");
                });
                break;

            case 251:
                tvSpeedYes.setOnClickListener(v -> {
                    if(!openImageInfo)
                        apiFacingIssue( "Yes");
                });
                tvSpeedNo.setOnClickListener(v -> {
                    if(!openImageInfo)
                        apiFacingIssue( "No");
                });
                break;

            case 215:
                if (!productType.equalsIgnoreCase("Partner")) {
                    tvYes.setText("ON");
                    tvNo.setText("OFF");
                } else {

                    tvYes.setText("GREEN");
                    tvNo.setText("RED");
                }
                tvYes.setOnClickListener(v -> updateStatus("statusof9F/10Flight", "ON"));
                tvNo.setOnClickListener(v -> updateStatus("statusof9F/10Flight", "OFF"));
                break;

            case 224:
                if (!isPatner) {
                    time = time * 2;
                } else {
                    isPatner = false;
                }
                tvYes.setOnClickListener(v -> updateStatus("isInternetWorking", "Yes"));
                tvNo.setOnClickListener(v -> updateStatus("isInternetWorking", "No"));
                break;
        }

        String text = "<br><br/><h1 style=\"color:red\">01:00</h1>";
        text = "<b>" + text + "</b> ";
        tvSelectOptTitle.setText(Html.fromHtml(text2 + text));
        final String[] text5 = {""};
        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String text = text2 + "<br><h1><font color='#F67A71'>" + secondCounter(seconds) + "</font></h1>";
                if(code==247) {
                    tvSelectOptTitle.setText(Html.fromHtml(text));
                }else if(code ==251){
                    speedTitle.setText(Html.fromHtml(text));
                }
            }
            public void onFinish() {
                switch (code) {
                    case 247:
                        String text = text2 + "<br><h1><font color='#F67A71'>00:00</font></h1><br>Is Internet working now ?";
                        tvSelectOptTitle.setText(Html.fromHtml(text));
                        tvYes.setVisibility(View.VISIBLE);
                        tvNo.setVisibility(View.VISIBLE);
                        break;

                    case 251:
                        String type1 = "";
                        if(vocNumber==2){
                            type1 = "Frequent Disconnection";
                        }else if(vocNumber==3){
                            type1 = "Slow Speed";
                        }
                        String text1 = text2 + "<br><h1><font color='#F67A71'>00:00</font></h1><br>Are you still facing "+type1+"?";
                        speedTitle.setText(Html.fromHtml(text1));
                        tvSpeedYes.setVisibility(View.VISIBLE);
                        tvSpeedNo.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }.start();
    }

    private String secondCounter(long seconds){
        if(seconds<10){
            return "00:0"+seconds;
        }else {
            return "00:"+seconds;
        }
    }


    public void viewBill(InvoiceDTO invoiceDTO) {
        rlInvoiceDetail.setVisibility(View.VISIBLE);
        img_cross.setOnClickListener(v -> rlInvoiceDetail.setVisibility(View.GONE));
        txt_end_date.setText(invoiceDTO.getToDate());
        txt_invoice_amount.setText(invoiceDTO.getInvoiceAmount().toString());
        txt_invoice_number.setText(invoiceDTO.getInvoiceNo());
        txt_invoice_date.setText(invoiceDTO.getInvoiceDate());
        txt_start_date.setText(invoiceDTO.getFromDate());
        txt_due_date.setText(invoiceDTO.getDueDate());
    }

    public void gotoSlowSpeedTrobleShootScreen() {
        Intent intent_pay = new Intent(FDSSInternet.this, SlowSpeedTroubleShootActivity.class);
        intent_pay.putExtra("email", userData.Email);
        intent_pay.putExtra("mobile", userData.Number);
        intent_pay.putExtra("canID", canId);
        intent_pay.putExtra("voc",vocNumber);
        startActivity(intent_pay);
        finish();
    }

    public void gotoPayment(String amount, String canID) {
        if (!TextUtils.isEmpty(amount)) {
            double amountValue = Double.parseDouble(amount);
            if (amountValue > 0) {
                Intent intent_pay = new Intent(FDSSInternet.this, PayNowActivity.class);
                intent_pay.putExtra("payableAamount", amount);
                intent_pay.putExtra("type", "unpaid");
                intent_pay.putExtra("email", userData.Email);
                intent_pay.putExtra("mobile", userData.Number);
                intent_pay.putExtra("subType", "topup");
                intent_pay.putExtra("come", "Internet");
                intent_pay.putExtra("canID", canID);
                startActivity(intent_pay);
            } else {
                Constant.MakeToastMessage(this, "Payable amount can't be 0");
            }
        } else {
            Constant.MakeToastMessage(this, "Payable amount can't be 0");
        }
    }

    public void updateStatus(String type, String status) {
        if (Constant.isInternetConnected(this)) {
            spectraViewModel.updateStatus(url + canId, NO_INTERNET, type, status).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        }
    }

    private void backToHome(String message) {
        rlAcountDeactive.setVisibility(View.GONE);
        llBackToHome.setVisibility(View.VISIBLE);
        tvBackToHomeTitle.setText(Html.fromHtml(message));
        tvBackToHome.setOnClickListener(v -> goToHome());
    }

    private void goToHome() {
        Intent intent = new Intent(FDSSInternet.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void srDetails(SrReponse srReponse) {
        rlAcountDeactive.setVisibility(View.GONE);
        rlSrDetail.setVisibility(View.VISIBLE);
        tvBackToHomeSr.setOnClickListener(v -> {
            Intent intent = new Intent(FDSSInternet.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        String massage = SiUtils.getSrDate(srReponse.getsRstatusETR(), true) + " at " + SiUtils.getSrDate(srReponse.getsRstatusETR(), false);
        txtExpectedResolutionTime.setText(massage);
        txt_Created_date.setText(SiUtils.getSrDate(srReponse.getCreatedon(), true));
        txtProblemType.setText(srReponse.getProblemType());
        txt_prob_sub_type.setText(srReponse.getSubType());
        txtCreatedTime.setText(SiUtils.getSrDate(srReponse.getCreatedon(), false));
        txt_sr_number.setText(srReponse.getSrNumber());
        txtProblemType.setText(srReponse.getProblemType());
        if (TextUtils.isEmpty(srReponse.getMessageTemplate())) {
            tvDisHint.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);
        } else {
            txtDescription.setText(srReponse.getMessageTemplate());
        }
    }




    public void createSr() {
        llSr.setVisibility(View.VISIBLE);
        tvSubmitSr.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etSrMessage.getText())) {
                Constant.MakeToastMessage(this, "Please add comment first");
                return;
            }
            CreateSrRequest createSrRequest = new CreateSrRequest();
            createSrRequest.setAuthkey(BuildConfig.AUTH_KEY);
            createSrRequest.setAction(CREATE_SR);
            createSrRequest.setCanID(canId);
            createSrRequest.setCaseType("9");
            createSrRequest.setComment(etSrMessage.getText().toString());
            spectraViewModel.createSR(createSrRequest).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        });
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            pro2.setVisibility(View.VISIBLE);
        } else {
            pro2.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.tvTROUBLESHOOT})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.tvTROUBLESHOOT:
                getIssue();
                break;
        }
    }

    private void getSRDetails(ResponseDTO response1) {
        if (!TextUtils.isEmpty(response1.getSrNo())) {
            if (codeAPi == 214) {
                GetSrRequest getSrRequest = new GetSrRequest();
                getSrRequest.setAuthkey(BuildConfig.AUTH_KEY);
                getSrRequest.setAction(CHECK_SR);
                getSrRequest.setCanID2(canId);
                getSrRequest.setSrNumber(response1.getSrNo());
                spectraViewModel.getCheckSrStatus(getSrRequest).observe(this, this::consumeResponse);
            } else {
                GetSrRequest getSrRequest = new GetSrRequest();
                getSrRequest.setAuthkey(BuildConfig.AUTH_KEY);
                getSrRequest.setAction(GET_SR_STATUS);
                getSrRequest.setCanID(canId);
                getSrRequest.setSrNumber(response1.getSrNo());
                spectraViewModel.getSrStatus(getSrRequest).observe(this, this::consumeResponse);
            }

        } else {
            rlAcountDeactive.setVisibility(View.GONE);
            llBackToHome.setVisibility(View.VISIBLE);
            tvBackToHome.setVisibility(View.INVISIBLE);
            tvBackToHomeTitle.setText(Html.fromHtml(response1.getMessageDescription()));
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(FDSSInternet.this, FDSSInternet.class));
                finish();
            }, 1000);
        }
    }


    private String getSrDate(String dateString) {
        Date date;
        String str = "";
        if (TextUtils.isEmpty(dateString)) {
            return str;
        }

        String inputPattern = "MM/dd/yyyy hh:mm:ss a";
        String outputPattern = "dd/MM/yyyy hh:mm a";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            date = inputFormat.parse(dateString);
            assert date != null;
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            str = "";
        }
        return str;
    }

    private String getSrDate213(String dateString) {
        Date date ;
        String str = "";
        if (TextUtils.isEmpty(dateString)) {
            return str;
        }
        String inputPattern = "dd/MM/yyyy HH:mm a";
        String outputPattern = "dd/MM/yyyy hh:mm a";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            date = inputFormat.parse(dateString);
            assert date != null;
            str = outputFormat.format(date);
            str = "<big><b>" + str + "<b></big> ";
        } catch (ParseException e) {
            e.printStackTrace();
            str = "";
        }
        return str;
    }

    private String getSrDate214(String dateString, boolean isDate, boolean isAM) {

        Date date;
        String str = "";
        if (TextUtils.isEmpty(dateString)) {
            return str;
        }
        String inputPattern = "dd/MM/yyyy HH:mm:ss";
        if (isAM) {
            inputPattern = "MM/dd/yyyy hh:mm:ss a";
        }

        String outputPattern = "hh:mm a";
        if (isDate) {
            outputPattern = "dd-MM-yyyy";
        }


        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            date = inputFormat.parse(dateString);
            assert date != null;
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            str = "";
        }
        return str.toUpperCase();
    }


    public void fuPNoAPI(String value) {
        if (Constant.isInternetConnected(this)) {
            PostFUPFlagRequest postFUPFlagRequest = new PostFUPFlagRequest();
            postFUPFlagRequest.setGetTopup(value);
            fdssViewModel.fupYesORNO(url, postFUPFlagRequest, NO_FDSS_INTERNET).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        }
    }

    public void apiHitForMRTG(String value) {
        if (Constant.isInternetConnected(this)) {
            PostMRTGRequest postMRTGRequest = new PostMRTGRequest();
            postMRTGRequest.setIsUpgradeBandwidth(value);
            fdssViewModel.fupYesORNO(url, postMRTGRequest, NO_FDSS_INTERNET).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        }
    }

    public void apiIsInternetWorking(String value) {
        if (Constant.isInternetConnected(this)) {
            PostInternetWorkingFDSS postInternetWorkingFDSS = new PostInternetWorkingFDSS();
            postInternetWorkingFDSS.setIsInternetWorking(value);
            fdssViewModel.fupYesORNO(url, postInternetWorkingFDSS, NO_FDSS_INTERNET).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        }
    }

    public void apiFluctuatingLight(String value) {
        if (Constant.isInternetConnected(this)) {
            PostFDSSFluctuatingLightRequest postInternetWorkingFDSS = new PostFDSSFluctuatingLightRequest();
            postInternetWorkingFDSS.setFluctuatingLight(value);
            fdssViewModel.fupYesORNO(url, postInternetWorkingFDSS, NO_FDSS_INTERNET).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        }
    }

    public void apiFacingIssue(String value) {
        if (Constant.isInternetConnected(this)) {
            PostFDSSFacingIssueRequest postFDSSFacingIssueRequest = new PostFDSSFacingIssueRequest();
            postFDSSFacingIssueRequest.setFacingIssue(value);
            fdssViewModel.fupYesORNO(url, postFDSSFacingIssueRequest, NO_FDSS_INTERNET).observe(FDSSInternet.this, FDSSInternet.this::consumeResponse);
        }
    }
}