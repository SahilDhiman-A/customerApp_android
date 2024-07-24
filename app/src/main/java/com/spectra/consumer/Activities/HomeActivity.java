package com.spectra.consumer.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Fragments.GetHelpFragment;
import com.spectra.consumer.Fragments.HomeFragment;
import com.spectra.consumer.Fragments.InvoiceFragment;
import com.spectra.consumer.Fragments.MoreFragment;
import com.spectra.consumer.Fragments.SRFragment;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.Models.UserDataDB;
import com.spectra.consumer.Models.UserImage;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.DeviceData;
import com.spectra.consumer.service.model.Request.GetAccountDataRequest;
import com.spectra.consumer.service.model.Request.GetLinkAccountRequest;
import com.spectra.consumer.service.model.Request.UpdateTokenRequest;
import com.spectra.consumer.service.model.Response.CanResponse;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.service.model.Response.LoginViaMobileResponse;
import com.spectra.consumer.viewModel.PlanAndTopupViewModel;
import com.spectra.consumer.viewModel.SpectraNotificationViewModel;
import com.spectra.consumer.viewModel.SpectraViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.spectra.consumer.Utils.CommonUtils.saveUser;
import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD_MENU;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.USER_DB;
import static com.spectra.consumer.Utils.Constant.USER_IMAGE;
import static com.spectra.consumer.service.repository.ApiConstant.DEVICE_SIGN_IN;
import static com.spectra.consumer.service.repository.ApiConstant.GET_ACCOUNT_DATA;
import static com.spectra.consumer.service.repository.ApiConstant.GET_LINK_ACCOUNT;
import static com.spectra.consumer.service.repository.ApiConstant.READ_NOTIFICATION;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    public BottomNavigationView layout_tabs;
    Intent intent;
    CurrentUserData currentUserData;
    @BindView(R.id.layout_cancelled)
    LinearLayout layout_cancelled;
    @BindView(R.id.create_new_sr)
    AppCompatTextView create_new_sr;
    @BindView(R.id.layout_activation_in_progress)
    LinearLayout layout_activation_in_progress;
    @BindView(R.id.close_app_layout)
    AppCompatTextView close_app_layout;
    private int SelectedID = 0;
    @BindView(R.id.layout_logout)
    LinearLayout layout_logout;
    @BindView(R.id.llMyAccount)
    LinearLayout llMyAccount;
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    private Context context;
    AlertDialog dialog;
    FirebaseAnalytics firebaseAnalytics;
    private String cameFromScreen = null, Notid, NotcanId;
    private ArrayList<String> canIDS = new ArrayList<>();
    public static ArrayList<String> canIDS1 = new ArrayList<>();
    public static String canID;
    public static String TOKEN;
    String token;
    String canIdAnalytics;
    AlertDialog dial;
    boolean isSameCan = false;
    private boolean isFromMenu= false;
    private static final int NOTIFICATION_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
        ButterKnife.bind(this);
        context = HomeActivity.this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestPermissions(new String[]{
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            },NOTIFICATION_CODE);
        }
        Bundle bundle = getIntent().getExtras();
        Checkout.preload(getApplicationContext());
        CAN_ID canIdNik = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        canIdAnalytics = canIdNik.baseCanID;
        Log.d("====checking Can ID", canIdAnalytics);
        ////TODO SP3
        try {
            firebaseAnalytics = FirebaseAnalytics.getInstance(this);
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        token = task.getResult();
                        TOKEN = token;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        create_new_sr.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CreateSrActivity.class);
            startActivity(intent);
        });
        close_app_layout.setOnClickListener(view -> {
            DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(HomeActivity.this);
            droidPrefs.clear();
            UserData.DeleteUsersInfo();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        layout_logout.setOnClickListener(view -> {
            logout_user();
        });
        llMyAccount.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MyAccountActivity.class);
            startActivity(intent);
        });

        if (bundle != null && bundle.containsKey("NOT_ID")) {
            Notid = bundle.getString("NOT_ID");
            NotcanId = bundle.getString("CAN_ID");
        }
        if (bundle != null && bundle.containsKey("cameFromScreen")) {
            cameFromScreen = bundle.getString("cameFromScreen");

        } else {
            cameFromScreen = null;
        }
        getAccountDetails();
        setupBottomNavigation();
        setTabThroughIntent();
        setRequestForLocationPermission();
    }

    //this fun is when user go in  genral seach and search some quary and it need to be change tab
    private void setTabThroughIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            boolean hasKey = intent.hasExtra("Key");
            if (hasKey) {

                switch (intent.getStringExtra("Key")) {
                    case "SR":
                        layout_tabs.setSelectedItemId(R.id.navigation_Sr);
                        break;
                    case "Home":
                        layout_tabs.setSelectedItemId(R.id.navigation_Home);
                        break;
                    case "GetInvoice":
                        layout_tabs.setSelectedItemId(R.id.navigation_invoice);
                        break;
                }
            }
        }
    }

    private void setRequestForLocationPermission() {
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 121);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = getIntent().getExtras();
        isSameCan = true;
        if (bundle != null && bundle.containsKey("cameFromScreen")) {
            if (bundle.containsKey("NOT_ID")) {
                Notid = bundle.getString("NOT_ID");
                NotcanId = bundle.getString("CAN_ID");
            }
            cameFromScreen = bundle.getString("cameFromScreen");
        } else {
            cameFromScreen = null;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("======NP", "Allowed");
            } else {
                Log.d("======NP", "denied");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermissions(new String[]{
                            Manifest.permission.POST_NOTIFICATIONS,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    }, NOTIFICATION_CODE);
                }
            }
        }
    }

    public void updateUser(LoginMobileResponse data) {
        boolean isDot = false;
        try {
            CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
            isDot = Data.isNot;
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveUser(context, data, isDot);
        if (!data.getActInProgressFlag().equals("false")) {
            Intent intent = new Intent(HomeActivity.this, TrackActivity.class);
            startActivity(intent);
            finish();
        }

        if (data.getCancellationFlag().equalsIgnoreCase("true")) {
            layout_cancelled.setVisibility(View.VISIBLE);
            layout_tabs.setVisibility(View.GONE);
            layout_activation_in_progress.setVisibility(View.GONE);
        } else {
            layout_cancelled.setVisibility(View.GONE);
            layout_activation_in_progress.setVisibility(View.GONE);
            layout_tabs.setVisibility(View.VISIBLE);
            if (SelectedID == 0) {
                setSelectedID(R.id.navigation_Home, false);
            }
        }
    }

    private void getAccountDetails() {
//        try {
//            SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD, "view_all_service_requests", "view_all_service_request_click",canIdAnalytics);
//            SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD, "view_details", "view_details",canIdAnalytics);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            if (Constant.isInternetConnected(context)) {
                CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
                no_internet.setVisibility(View.GONE);
                canID = Data.CANId;
                GetAccountDataRequest getAccountDataRequest = new GetAccountDataRequest();
                getAccountDataRequest.setAuthkey(BuildConfig.AUTH_KEY);
                getAccountDataRequest.setAction(GET_ACCOUNT_DATA);
                getAccountDataRequest.setCanID(Data.CANId);
                spectraViewModel.getAccountByCanId(getAccountDataRequest).observe(this, this::consumeResponse);
            } else {
                no_internet.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                case GET_LINK_ACCOUNT:
                    GetLinkAccountResponse getLinkAccountResponse = (GetLinkAccountResponse) response;
                    if (getLinkAccountResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        ArrayList<CanResponse> canResponseArrayList = new ArrayList<>();
                        canResponseArrayList = getLinkAccountResponse.getResponse();
                        if (canResponseArrayList != null && canResponseArrayList.size() > 0) {
                            for (CanResponse canResponse : canResponseArrayList) {
                                canIDS.add(canResponse.getLink_canid());
                            }
                        }
                    }
                    getListId(canIDS);
                    try {
                        Intent myIntent = new Intent("NOT_CALL");
                        this.sendBroadcast(myIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getUpdateToken();
                    break;
                case GET_ACCOUNT_DATA:
                    LoginViaMobileResponse loginViaMobileResponse = (LoginViaMobileResponse) response;
                    if (loginViaMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && loginViaMobileResponse.response != null) {
                        List<LoginMobileResponse> loginMobileResponses = loginViaMobileResponse.response;
                        if (loginMobileResponses.size() > 0) {
                            LoginMobileResponse data = loginMobileResponses.get(0);
                            data.setToken(token);
                            setLIst();
                            TOKEN = token;
                            updateUser(data);
                            //TODO SP3
                            getContactList();
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

    private void logout_user() {
        new AlertDialog.Builder(context).setTitle(getString(R.string.txt_logout)).setMessage(getString(R.string.logout_msg)).setPositiveButton("Ok", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            UserImage userImage = DroidPrefs.get(context, USER_IMAGE, UserImage.class);
            DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(context);
            droidPrefs.clear();
            UserData.DeleteUsersInfo();
            DroidPrefs.apply(context, USER_IMAGE, userImage);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
    }

    private void autoLogoutUser() {
        DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(context);
        droidPrefs.clear();
        UserData.DeleteUsersInfo();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void setSelectedID(int id, boolean isFromMenu) {
        this.isFromMenu = isFromMenu;
        layout_tabs.setSelectedItemId(id);
    }


    private void setupBottomNavigation() {
        layout_tabs.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_invoice:
                    if (SelectedID != R.id.navigation_invoice) {
                        SelectedID = item.getItemId();
                        SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD_MENU, "menu_click_payments", "menu_payments", canIdAnalytics);
                        select_invoice();
                    }
                    return true;

                case R.id.navigation_more:
                    if (SelectedID != R.id.navigation_more) {
                        SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD_MENU, "menu_click_menu", "menu_all_menu", canIdAnalytics);
                        SelectedID = item.getItemId();
                        select_more();
                    }
                    return true;

                case R.id.navigation_Sr:
                    if (SelectedID != R.id.navigation_Sr) {
                        SelectedID = item.getItemId();
                        select_sr();
                    }

                    /*CurrentUserData  Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                    Intent intent_pay = new Intent(this, PayNowActivity.class);
                    String amount = Data.OutStandingAmount;
                    if (TextUtils.isEmpty(amount)) {
                        amount = "0";
                    }
                    if (!TextUtils.isEmpty(amount)) {
                        intent_pay.putExtra("email", Data.Email);
                        intent_pay.putExtra("mobile", Data.Number);
                        intent_pay.putExtra("payableAamount", amount);
                        intent_pay.putExtra("canID", Data.CANId);
                        intent_pay.putExtra("type", "unpaid");
                        intent_pay.putExtra("subType", "normal");
                        startActivity(intent_pay);
                    } else {
                        Constant.MakeToastMessage(context, "Payable amount can't be 0");
                    }*/
                    return true;

                case R.id.navigation_gethelp:
                    if(isFromMenu){
                        Log.d("----", "true HA else");
                        isFromMenu = false;
                        SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD_MENU, "menu_click_faq", "menu_get_help_faq", canIdAnalytics);
                    }else{
                        Log.d("----", "true HA");
//                        SpectraApplication.getInstance().postEvent(CATEGORY_GET_HELP, "faq_selected", "faq_selected", canIdAnalytics);
                    }
                    if (SelectedID != R.id.navigation_gethelp) {
                        SelectedID = item.getItemId();
                        select_getHelp();
                    }
                    return true;

                case R.id.navigation_Home:
                    if (SelectedID != R.id.navigation_Home) {
                        SelectedID = item.getItemId();
                        SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD_MENU, "menu_click_home", "menu_home", canIdAnalytics);
                        select_home();
                    }

                    return true;
            }
            return false;
        });
    }

    private void addFragment(Fragment fragment, String tag) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            new GetVersionCode().execute();
        }
        isSameCan = false;
        try {
            if (!TextUtils.isEmpty(Notid)) {
                readNotification(Notid);
                if (!canID.equals(NotcanId)) {
                    isSameCan = true;
                    onUpdateNeeded(HomeActivity.this, NotcanId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isSameCan && cameFromScreen != null) {
            if (cameFromScreen.equalsIgnoreCase("SR_TAB")) {
                setSelectedID(R.id.navigation_Sr, false);
            } else {
                if (cameFromScreen.equalsIgnoreCase("INVOICE_TAB")) {
                    setSelectedID(R.id.navigation_invoice, false);
                }
            }
        }
        cameFromScreen = null;

    }


    public void select_home() {
        addFragment(new HomeFragment(), "home");
    }

    public void select_getHelp() {
        addFragment(new GetHelpFragment(), "home");
    }

    public void select_invoice() {
        addFragment(new InvoiceFragment(), "invoice");
    }

    public void select_more() {
        addFragment(new MoreFragment(), "more");
    }

    public void select_sr() {
        addFragment(new SRFragment(), "sr");
    }

    @Override
    public void onBackPressed() {
        int index = getFragmentManager().getBackStackEntryCount();
        if (index == 1) {
            exit();
        }
        if (getIntent().hasExtra("Key")) {
            finish();
        }
    }

    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(getString(R.string.exit_msg))
                .setCancelable(false).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).setPositiveButton("OK", (dialog, user_id) -> {
                    finish();
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @SuppressLint("StaticFieldLeak")
    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String latestVersion = null;

            try {
                if (progress_bar != null) {
                    Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.spectra.consumer&hl=en").get();
                    latestVersion = doc.getElementsContainingOwnText("Current Version").parents().first().getAllElements().last().text();
                    return latestVersion;
                } else {
                    return latestVersion;
                }
            } catch (Exception e) {
                return latestVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (progress_bar != null && onlineVersion != null && !onlineVersion.isEmpty()) {
                if (compareVersion(BuildConfig.VERSION_NAME, onlineVersion) < 0) {
                    onUpdateNeeded(onlineVersion, "market://details?id=" + BuildConfig.APPLICATION_ID);
                }
            }
        }
    }

    public int compareVersion(String version1, String version2) {
        String[] arr1 = version1.split("\\.");
        String[] arr2 = version2.split("\\.");

        int i = 0;
        while (i < arr1.length || i < arr2.length) {
            if (i < arr1.length && i < arr2.length) {
                if (Integer.parseInt(arr1[i]) < Integer.parseInt(arr2[i])) {
                    return -1;
                } else if (Integer.parseInt(arr1[i]) > Integer.parseInt(arr2[i])) {
                    return 1;
                }
            } else if (i < arr1.length) {
                if (Integer.parseInt(arr1[i]) != 0) {
                    return 1;
                }
            } else {
                if (Integer.parseInt(arr2[i]) != 0) {
                    return -1;
                }
            }

            i++;
        }

        return 0;
    }

    public void onUpdateNeeded(final String onlineVersion, final String updateUrl) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("New Version Alert").setCancelable(false)
                .setMessage("A new version of My Spectra App is available. Update to version" + onlineVersion + ", now!")
                .setPositiveButton("Update",
                        (dialog1, which) -> redirectStore(updateUrl)).create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3E7B8A"));
        });

        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.cancel();
        }
    }

    private void getContactList() {
        CAN_ID can_id = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        if (Constant.isInternetConnected(this)) {
            GetLinkAccountRequest linkAccountRequest = new GetLinkAccountRequest();
            linkAccountRequest.setAuthkey(BuildConfig.AUTH_KEY);
            linkAccountRequest.setAction(GET_LINK_ACCOUNT);
            Log.d("Nik Can", can_id.baseCanID);
            if (can_id.isMobile) {
                linkAccountRequest.setMobileNo(can_id.mobile);
            } else {
                linkAccountRequest.setCanID(can_id.baseCanID);
            }
            SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            spectraViewModel.getLinkAccount(linkAccountRequest).observe(HomeActivity.this, HomeActivity.this::consumeResponse);
        }
    }

    private void getListId(ArrayList<String> list) {
        String ids = "";
        for (String id : list) {
            if (!TextUtils.isEmpty(ids)) {
                ids = ids + ",";
            }
            ids = ids + id;
        }
        Constant.allCan = ids;

    }

    public void setLIst() {
        canIDS = new ArrayList<>();
        UserDataDB userDataDB = DroidPrefs.get(this, USER_DB, UserDataDB.class);
        HashMap<String, LoginMobileResponse> mp = userDataDB.getResponseHashMap();
        for (Map.Entry<String, LoginMobileResponse> stringLoginMobileResponseEntry : mp.entrySet()) {
            canIDS.add(stringLoginMobileResponseEntry.getKey());
        }
    }

    private void getUpdateToken() {
        if (Constant.isInternetConnected(this)) {
            UpdateTokenRequest updateTokenRequest = new UpdateTokenRequest();
            updateTokenRequest.setAuthkey(BuildConfig.AUTH_KEY);
            updateTokenRequest.setAction(DEVICE_SIGN_IN);
            updateTokenRequest.setDeviceData(setDeviceData());
            PlanAndTopupViewModel spectraViewModel = ViewModelProviders.of(this).get(PlanAndTopupViewModel.class);
            spectraViewModel.getDeviceSign(updateTokenRequest).observe(HomeActivity.this, HomeActivity.this::consumeResponse);
        }
    }

    public DeviceData setDeviceData() {
        DeviceData deviceData = new DeviceData();
        ArrayList<String> token = new ArrayList<>();
        ArrayList<String> device = new ArrayList<>();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        canIDS1 = canIDS;
        for (String s : canIDS) {
            token.add(TOKEN);
            device.add("Android");
        }
        deviceData.setCanIds(canIDS);
        deviceData.setDeviceToken(token);
        deviceData.setDeviceType(device);
        CurrentUserData userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        userData.linkedIDs = Constant.allCan;
        userData.linkedIDsList = canIDS;
        DroidPrefs.apply(this, CurrentuserKey, userData);
        return deviceData;
    }

    public void onUpdateNeeded(Context context, String can_id) {
        String message = MessageFormat.format("This notification is associated with another CAN ID: {0}. You need to switch CAN ID to proceed.", can_id);
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            View v = LayoutInflater.from(context).inflate(R.layout.switch_dialog, null);
            AppCompatTextView back_to_sr = v.findViewById(R.id.title);
            back_to_sr.setText(message);
            TextView txtCancel = v.findViewById(R.id.cancel);
            TextView txtSwitch = v.findViewById(R.id.switchb);
            txtCancel.setOnClickListener(v12 -> dial.dismiss());
            txtSwitch.setOnClickListener(v1 -> {
                dial.dismiss();
                callAccountScreen(context);
            });
            dialog.setView(v);
            dialog.setCancelable(true);
            dial = dialog.create();
            dial.show();
            back_to_sr.setOnClickListener(view -> {

            });
            dial.show();
            Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callAccountScreen(Context context) {
        Intent intent = new Intent(context, MyAccountActivity.class);
        context.startActivity(intent);
    }

    public void readNotification(String id) {
        if (Constant.isInternetConnected(this)) {
            SpectraNotificationViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraNotificationViewModel.class);
            spectraViewModel.readNotification(READ_NOTIFICATION, id).observe(HomeActivity.this, HomeActivity.this::consumeResponse);
        }
    }
}
