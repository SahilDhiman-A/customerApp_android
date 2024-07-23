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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;

import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.Utils.SiUtils;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.CreateSrRequest;
import com.spectra.consumer.service.model.Request.GetSrRequest;
import com.spectra.consumer.service.model.Response.GetSrStatusResponse;
import com.spectra.consumer.service.model.Response.InternetNotWorkModelDTO;
import com.spectra.consumer.service.model.Response.InvoiceDTO;
import com.spectra.consumer.service.model.Response.ResponseDTO;
import com.spectra.consumer.service.model.Response.SrReponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.MessageFormat;
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
import static com.spectra.consumer.service.repository.ApiConstant.NO_INTERNET;

public class NoInternet extends AppCompatActivity {
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
    @BindView(R.id.tvDisHint)
    TextView tvDisHint;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    @BindView(R.id.txtCreatedTime)
    TextView txtCreatedTime;
    @BindView(R.id.rlSrDetail)
    RelativeLayout rlSrDetail;

    @BindView(R.id.txt_prob_sub_type)
    TextView txt_prob_sub_type;

    @BindView(R.id.pro2)
    ProgressBar pro2;
    @BindView(R.id.img_cross)
    ImageView img_cross;
    private CurrentUserData userData;
    private String canId;
    SpectraViewModel spectraViewModel;
    private String productType = "", safeCastdyAmount = "";
    private int pStatus = 0;
    private Handler handler = new Handler();
    ResponseDTO response1;
    boolean isPatner = false;
    int codeAPi = 0;
    private String url = "getInternetNotWorking/authkey/01a62ae9623beb096ec88dca3836858c/canId/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_internet_not_working);
        ButterKnife.bind(this);
        findViewById(R.id.img_back).setVisibility(View.GONE);
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        canId = userData.CANId;
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
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
            spectraViewModel.getNoInternet(url + canId, NO_INTERNET).observe(NoInternet.this, NoInternet.this::consumeResponse2);
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
                            massage = "Oops.. we have found an issue<br/>in your connectivity.<br/>Service Request No." + srReponse.getSrNumber() + " of<br/> " + srReponse.getProblemType() + "-" + srReponse.getSubType() + " has been<br/>raised and dispatched to our<br/>technical team for quick<br/>resolution. The ETR is <br/> " + massage;
                        } else {
                            massage = "There seems to be an issue<br/>which needs to be<br/>investigated by our technical<br/>experts.<br/><br/>Service Request No." + srReponse.getSrNumber() + " of<br/> " + srReponse.getProblemType() + "-" + srReponse.getSubType() + " has been<br/>raised and dispatched to our<br/>technical team for quick<br/>resolution. The ETR is <br/> " + massage;
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
                case NO_INTERNET:

                    InternetNotWorkModelDTO responseDTO = (InternetNotWorkModelDTO) response;
                    if (responseDTO.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {

                        pStatus = 100;
                        progressBar.setProgress(100);
                        ResponseDTO response1 = responseDTO.getResponse();

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
                            String text;
                            rlTrubbleShootProgressbar.setVisibility(View.GONE);
                            switch (problumCode) {

                                case 211:
                                    tvYes.setText("yes");
                                    tvNo.setText("no");
                                    tvYes.setOnClickListener(v -> updateStatus("reactivateAccount", "True"));
                                    tvNo.setOnClickListener(v -> createSr());
                                    rlAcountDeactive.setVisibility(View.VISIBLE);
                                    text = "Great !! Welcome Back";
                                    text = "<b>" + text + "</b> ";
                                    if (!TextUtils.isEmpty(response1.getProduct())) {
                                        text = text + "<br/>Your old plan was<br/>" + "<b>" + response1.getProduct() + "</b> " + "<br/>with it ?";
                                    }
                                    text = text + "<br/><br/>Would you like to continue";
                                    if (!TextUtils.isEmpty(response1.getProduct())) {
                                        text = text + "<br/>with it ?";
                                    } else {
                                        text = text + " ?";
                                    }
                                    tvSelectOptTitle.setText(Html.fromHtml(text));
                                    break;
                                case 212:
                                    findViewById(R.id.topMeassge).setVisibility(View.VISIBLE);
                                    tvYes.setText("PAY NOW");
                                    tvNo.setText("VIEW BILL");
                                    tvNo.setOnClickListener(v -> viewBill(response1.getInvoiceList()));
                                    tvYes.setOnClickListener(v -> gotoPayment(response1.getOutstandingAmount(), canId));
                                    rlAcountDeactive.setVisibility(View.VISIBLE);
                                    text = " Rs " + getAmount(response1.getOutstandingAmount());
                                    text = "<b>" + text + "</b> ";
                                    String date = response1.getDueDate();
                                    date = "<b>" + date + "</b>";
                                    text = "Total Amount Due <br/><br/>" + text + "<br/><br/>Due on " + "<font color=red>" + date + "</font><br><br><br/>" + "Kindly clear this to get account activated and enjoy uninterrupted services.";
                                    tvSelectOptTitle.setText(Html.fromHtml(text));
                                    break;

                                case 213:
                                    text = getSrDate213(response1.getEtr());
                                    text = "There is an outage in your<br/> area which is expected to<br/> be restored by<br/>" + text + "<br/><br/>Your internet will start working<br/> automatically after this time.<br/> Sorry for the inconvenience<br/> caused.";
                                    backToHome(text);
                                    break;

                                case 214:
                                    codeAPi = 214;
                                    getSRDetails(response1);
                                    break;

                                case 215:
                                    rlAcountDeactive.setVisibility(View.VISIBLE);
                                    isPatner = response1.getProductSegment().equalsIgnoreCase("Partner");
                                    if (isPatner) {
                                        productType = response1.getProductSegment();
                                        text = "Can you please check if the<br/>WAN light on your WiFi Router is <br/><big>RED</big> or <big>GREEN</big> ?";
                                        tvYes.setText("GREEN");
                                        tvNo.setText("RED");
                                        tvYes.setOnClickListener(v -> updateStatus("statusof9F/10Flight", "ON"));
                                        tvNo.setOnClickListener(v -> updateStatus("statusof9F/10Flight", "OFF"));
                                        rlAcountDeactive.setVisibility(View.VISIBLE);
                                        tvSelectOptTitle.setText(Html.fromHtml(text));

                                    } else {
                                        text = "Can you please check if the<br/>9F/10F light on your Switch is<br/><big>ON</big> or <big>OFF</big> ?";
                                        timerWork(text, problumCode);
                                    }

                                    break;
                                case 216:
                                    codeAPi = 216;
                                    getSRDetails(response1);

                                case 218:
                                    getSRDetails(response1);

                                    break;
                                case 219:
                                    tvYes.setText("PAY NOW");
                                    tvNo.setText("NOT NOW");
                                    tvYes.setOnClickListener(v -> gotoPayment(response1.getOutstandingAmount(), canId));
                                    tvNo.setOnClickListener(v -> createSr());
                                    rlAcountDeactive.setVisibility(View.VISIBLE);
                                    text = " Rs " + getAmount(response1.getOutstandingAmount());
                                    text = "<b>" + text + "</b> ";
                                    String message = "There is an outstanding of<br/><br/>" + text + "<br/><br/>in your account.<br/><br/><br/>Kindly clear this to get account activated and enjoy uninterrupted services.";
                                    tvSelectOptTitle.setText(Html.fromHtml(message));
                                    break;
                                case 220:
                                    text = "Your account has been activated now.<br/><br/>Please continue enjoying Spectra services.";
                                    String amount = safeCastdyAmount;
                                    if (!TextUtils.isEmpty(amount)) {
                                        double amountValue = Double.parseDouble(amount);
                                        if (amountValue > 0) {
                                            tvYes.setText("PAY NOW");
                                            tvNo.setText("NOT NOW");
                                            tvYes.setOnClickListener(v -> gotoPayment(amount, canId));
                                            tvNo.setOnClickListener(v -> goToHome());
                                            rlAcountDeactive.setVisibility(View.VISIBLE);
                                            text = " Rs " + getAmount(amount);
                                            text = "<b>" + text + "</b> ";
                                            text = "There is an outstanding of<br/>" + text + "in your account. Kindly clear this to get account activated and enjoy uninterrupted services.";
                                            String text2 = "Your account has been activated now.<br/><br/>Please continue enjoying Spectra services.<br/><br/>";
                                            text = text2 + text;
                                            tvSelectOptTitle.setText(Html.fromHtml(text));
                                        } else {
                                            backToHome(text);
                                        }
                                    } else {
                                        backToHome(text);
                                    }


                                    break;
                                case 222:

                                    tvYes.setText("yes");
                                    tvNo.setText("no");
                                    tvYes.setOnClickListener(v -> updateStatus("enableSafeCustodytoActive", "True"));
                                    tvNo.setOnClickListener(v -> createSr());
                                    safeCastdyAmount = response1.getOutstandingAmount();
                                    rlAcountDeactive.setVisibility(View.VISIBLE);
                                    text = response1.getEndDate();
                                    text = "<b>" + text + "</b> ";
                                    text = "Your account is under<br/> safe custody till<br/>" + text + ".<br/><br/>Would you like to remove your<br/> account from Safe Custody ?";

                                    tvSelectOptTitle.setText(Html.fromHtml(text));
                                    break;

                                case 224:
                                    response1.setProductSegment(productType);
                                    if (isPatner) {
                                        text = "While our connectivity to your<br/>premises is fine, we have found an<br/>issue in the equipment at<br/>your premises. Please reboot<br/>your WiFi Router and wait<br/>for 1 minute.";
                                        timerWork(text, problumCode);
                                    } else {
                                        text = "Please hold on while we<br/>check our connectivity to your<br/>switch again.<br/><br/>We are able to successfully<br/>reach your switch. Please<br/>check if your internet is<br/>working now.<br/>";
                                        tvYes.setText("yes");
                                        tvNo.setText("no");
                                        tvYes.setOnClickListener(v -> updateStatus("isInternetWorking", "Yes"));
                                        String finalText = "Sorry… just one final step<br/>before we reach our<br/>conclusion<br/>Kindly check if the Ethernet<br/>cable is connected to Port 1<br/>on the Switch and reboot the<br/>Switch. Wait for 2 minutes.";
                                        tvNo.setOnClickListener(v -> timerWork(finalText, 224));
                                        tvSelectOptTitle.setText(Html.fromHtml(text));
                                    }
                                    rlAcountDeactive.setVisibility(View.VISIBLE);

                                    break;
                                case 226:
                                    getSRDetails(response1);
                                    break;
                                case 227:
                                    text = "<big><b>" + "Great!" + "<b></big> ";
                                    text = "<font color=black>" + text + "</font>";
                                    text = text + "<br/><br/>Please continue to enjoy<br/>Spectra’s fastest internet<br/>services.";
                                    backToHome(text);
                                    break;

                                case 228:
                                    getSRDetails(response1);
                                    break;
                                case 238:
                                    this.response1 = response1;
                                    if (response1.getAlarmType().equals("DGI") && response1.getPowerLevel().equals("-40")) {
                                        text = "While our connectivity to your<br/>premises is fine, it seems that<br/>your WiFi Router/Switch is<br/>powered OFF. Kindly switch<br/>on the power supply and wait<br/>for 1 minute.";
                                    } else {
                                        text = "While our connectivity to your<br/> premises is fine, we have found<br/> an issue in the equipment at your premises.<br/> Please reboot your ONT (white box)<br/> and your WiFi Router/Switch and <br/>wait for 1 minute.";
                                    }
                                    timerWork(text, problumCode);
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
    private void timerWork(String text2, int code) {
        long time = 60000;
        //long time = 60;
        tvYes.setVisibility(View.GONE);
        tvNo.setVisibility(View.GONE);
        tvYes.setText("yes");
        tvNo.setText("no");

        switch (code) {
            case 0:
                tvYes.setOnClickListener(v -> updateStatus("isInternetWorking", "Yes"));
                tvNo.setOnClickListener(v -> updateStatus("isInternetWorking", "No"));
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


        rlAcountDeactive.setVisibility(View.VISIBLE);
        String text = "<br><br/><font color=red size=23>01:00</font>";
        text = "<b>" + text + "</b> ";
        tvSelectOptTitle.setText(Html.fromHtml(text2 + text));
        final String[] text5 = {""};

        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                @SuppressLint("DefaultLocale") String text = MessageFormat.format("{0}:{1}", String.format("%02d", seconds / 60), String.format("%02d", seconds % 60));
                text = "<br><br/><font color=red size=23>" + text + "</font>";
                text = "<big><big><b>" + text + "</b></big></big> ";
                text5[0] = text2 + text;
                tvSelectOptTitle.setText(Html.fromHtml(text5[0]));
            }

            public void onFinish() {
                if (code != 215) {
                    String text3 = text5[0] + "<br><br/>Has internet started working ?";
                    tvSelectOptTitle.setText(Html.fromHtml(text3));
                }
                tvYes.setVisibility(View.VISIBLE);
                tvNo.setVisibility(View.VISIBLE);
            }
        }.

                start();

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

    public void gotoPayment(String amount, String canID) {
        if (!TextUtils.isEmpty(amount)) {
            double amountValue = Double.parseDouble(amount);
            if (amountValue > 0) {
                Intent intent_pay = new Intent(NoInternet.this, PayNowActivity.class);
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
            spectraViewModel.updateStatus(url + canId, NO_INTERNET, type, status).observe(NoInternet.this, NoInternet.this::consumeResponse);
        }
    }

    private void backToHome(String message) {
        rlAcountDeactive.setVisibility(View.GONE);
        llBackToHome.setVisibility(View.VISIBLE);
        tvBackToHomeTitle.setText(Html.fromHtml(message));
        tvBackToHome.setOnClickListener(v -> goToHome());
    }

    private void goToHome() {
        Intent intent = new Intent(NoInternet.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void srDetails(SrReponse srReponse) {
        rlAcountDeactive.setVisibility(View.GONE);
        rlSrDetail.setVisibility(View.VISIBLE);
        tvBackToHomeSr.setOnClickListener(v -> {
            Intent intent = new Intent(NoInternet.this, HomeActivity.class);
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
            spectraViewModel.createSR(createSrRequest).observe(NoInternet.this, NoInternet.this::consumeResponse);
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
            if (Constant.isInternetConnected(this)) {
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

            }
        } else {
            rlAcountDeactive.setVisibility(View.GONE);
            llBackToHome.setVisibility(View.VISIBLE);
            tvBackToHome.setVisibility(View.INVISIBLE);
            tvBackToHomeTitle.setText(Html.fromHtml(response1.getMessageDescription()));
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                startActivity(new Intent(NoInternet.this, NoInternet.class));
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
        Date date;
        String str = "";
        if (TextUtils.isEmpty(dateString)) {
            return str;
        }

        String inputPattern = "dd/MM/yyyy HH:mm:ss";
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

}
//215