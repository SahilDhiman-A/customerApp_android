package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.Adapters.SelectCanLinkedAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserDataDB;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.GetLinkAccountRequest;
import com.spectra.consumer.service.model.Request.RemoveLinkAccountRequest;
import com.spectra.consumer.service.model.Response.BaseResponse;
import com.spectra.consumer.service.model.Response.CanResponse;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_ACCOUNT;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_CHANGE_PLANE;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_INVOICE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.USER_DB;
import static com.spectra.consumer.service.repository.ApiConstant.GET_LINK_ACCOUNT;
import static com.spectra.consumer.service.repository.ApiConstant.REMOVE_LINK_ACCOUNT;

public class LinkedCanLdListActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    AppCompatImageView imgBack;
    @BindView(R.id.txt_head)
    TextView txtHead;
    @BindView(R.id.txt_share)
    TextView txtShare;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.rvCanId)
    RecyclerView rvCanId;
    @BindView(R.id.no_internet)
    LinearLayout noInternet;
    @BindView(R.id.img_payment_status)
    AppCompatImageView ivIcon;
    @BindView(R.id.txt_payment)
    TextView noContactMassage;
    @BindView(R.id.txt_retry)
    TextView tvAddNewContact;
    @BindView(R.id.try_again)
    AppCompatTextView tryAgain;
    private SpectraViewModel spectraViewModel;
    private Context context;
    private int pos = -1;
    boolean isChange = false, isLink = false;
    private SelectCanLinkedAdapter selectCanLinkedAdapter;
    private ArrayList<CanResponse> canResponseArrayList = new ArrayList<>();
    private ArrayList<CanResponse> canResponseArrayListMain = new ArrayList<>();
    CurrentUserData userData;
    AlertDialog dial;
    CAN_ID can_id;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linked_canid_activity);
        ButterKnife.bind(this);
        context = LinkedCanLdListActivity.this;
        txtShare.setVisibility(View.VISIBLE);
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        can_id = DroidPrefs.get(LinkedCanLdListActivity.this, BASE_CAN, CAN_ID.class);
        UserDataDB userDataDB = DroidPrefs.get(this,USER_DB, UserDataDB.class);
        setLIst(userDataDB.getResponseHashMap());

        txtShare.setTextColor(ContextCompat.getColor(context, R.color.back_color));
        txtShare.setVisibility(View.GONE);
        txtHead.setText("Account List");
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        getContactList();
    }

    private void getContactList() {
        CAN_ID can_id = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        if (Constant.isInternetConnected(this)) {
            noInternet.setVisibility(View.GONE);
            GetLinkAccountRequest linkAccountRequest = new GetLinkAccountRequest();
            linkAccountRequest.setAuthkey(BuildConfig.AUTH_KEY);
            linkAccountRequest.setAction(GET_LINK_ACCOUNT);
            if(can_id.isMobile) {
                linkAccountRequest.setMobileNo(can_id.mobile);
            }else {
                linkAccountRequest.setCanID(can_id.baseCanID);
            }
            spectraViewModel.getLinkAccount(linkAccountRequest).observe(LinkedCanLdListActivity.this, LinkedCanLdListActivity.this::consumeResponse);
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                showLoadingView(false);
                renderSuccessResponse(apiResponse.data, apiResponse.code);

                break;
            case ERROR:
                showLoadingView(false);
                assert apiResponse.error != null;
                Constant.MakeToastMessage(LinkedCanLdListActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        noInternet.setVisibility(View.GONE);
        if (response != null) {
            if (code.equalsIgnoreCase(GET_LINK_ACCOUNT)) {
                GetLinkAccountResponse getLinkAccountResponse = (GetLinkAccountResponse) response;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                rvCanId.setLayoutManager(linearLayoutManager);
                if (getLinkAccountResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    canResponseArrayList = getLinkAccountResponse.getResponse();
                    canResponseArrayListMain.addAll(canResponseArrayList);
                    if (canResponseArrayListMain != null && canResponseArrayListMain.size() > 0) {
                        CAN_ID can_id = DroidPrefs.get(LinkedCanLdListActivity.this, BASE_CAN, CAN_ID.class);
                        can_id.isLinked = true;
                        DroidPrefs.apply(LinkedCanLdListActivity.this, BASE_CAN, can_id);
                    }
                }
                selectCanLinkedAdapter = new SelectCanLinkedAdapter(context, canResponseArrayListMain, can_id.Linked);
                rvCanId.setAdapter(selectCanLinkedAdapter);
            } else {
                if (code.equalsIgnoreCase(REMOVE_LINK_ACCOUNT)) {
                    BaseResponse baseResponse = (BaseResponse) response;
                    if (baseResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                        CAN_ID can_id = DroidPrefs.get(LinkedCanLdListActivity.this, BASE_CAN, CAN_ID.class);
                        can_id.Linked = "";
                        Data.CANId = can_id.baseCanID;
                        DroidPrefs.apply(LinkedCanLdListActivity.this, BASE_CAN, can_id);
                        DroidPrefs.apply(context, CurrentuserKey, Data);
                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Constant.MakeToastMessage(context, baseResponse.getMessage());
                }

            }
        }
}

    private void noData() {
        ivIcon.setImageResource(R.drawable.ghost);
        noContactMassage.setText(R.string.no_linked);
        tvAddNewContact.setVisibility(View.GONE);
        tryAgain.setVisibility(View.GONE);
        noInternet.setVisibility(View.VISIBLE);
        CAN_ID can_id = DroidPrefs.get(LinkedCanLdListActivity.this, BASE_CAN, CAN_ID.class);
        can_id.Linked = "";
        can_id.isLinked = false;
        DroidPrefs.apply(LinkedCanLdListActivity.this, BASE_CAN, can_id);
        CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
        Data.CANId = can_id.baseCanID;
        DroidPrefs.apply(context, CurrentuserKey, Data);
    }


    private void showLoadingView(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void removeAccount(int pos) {
        CAN_ID can_id = DroidPrefs.get(LinkedCanLdListActivity.this, BASE_CAN, CAN_ID.class);
        CanResponse canResponse = canResponseArrayListMain.get(pos);
        RemoveLinkAccountRequest removeLinkAccountRequest = new RemoveLinkAccountRequest();
        removeLinkAccountRequest.setAuthkey(BuildConfig.AUTH_KEY);
        removeLinkAccountRequest.setAction(REMOVE_LINK_ACCOUNT);
        removeLinkAccountRequest.setuserName(canResponse.getUsername());
        removeLinkAccountRequest.setlinkCanID(canResponse.getLink_canid());
        if(can_id.isMobile) {
            removeLinkAccountRequest.setmobileNo(can_id.mobile);
        }else {
            removeLinkAccountRequest.setCanID(can_id.baseCanID);
        }
        spectraViewModel.removeLinkAccountRequest(removeLinkAccountRequest).observe(LinkedCanLdListActivity.this, LinkedCanLdListActivity.this::consumeResponse);
    }

    @OnClick({R.id.img_back, R.id.txt_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_share:
                Intent intent = new Intent(context, AddCanIdActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isChange) {
            Intent intent = new Intent(context, MyAccountActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void setLIst(HashMap<String, LoginMobileResponse> mp) {
        for (Map.Entry<String, LoginMobileResponse> stringLoginMobileResponseEntry : mp.entrySet()) {
            CanResponse canResponse = new CanResponse();
            canResponse.setLink_canid(stringLoginMobileResponseEntry.getKey());
            canResponse.setBase_canid(can_id.baseCanID);
            canResponse.setMobile(can_id.mobile);
            canResponse.setLinked(false);
            canResponseArrayListMain.add(canResponse);
        }
    }
    public void showPopupMenu(View v, int pos,boolean isLink , boolean isSelect) {
        this.pos = pos;
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        if (!isLink) {
            inflater.inflate(R.menu.my_popup, popupMenu.getMenu());
        } else {
            if(isSelect){
                inflater.inflate(R.menu.select_popup, popupMenu.getMenu());
            }else {
                inflater.inflate(R.menu.unlink_popup, popupMenu.getMenu());
            }
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.link:
//                    SpectraApplication.getInstance().postEvent(CATEGORY_ACCOUNT,"change_account","change_account", can_id.baseCanID);
                    CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                    CAN_ID can_id = DroidPrefs.get(LinkedCanLdListActivity.this, BASE_CAN, CAN_ID.class);
                    Data.CANId = canResponseArrayListMain.get(pos).getLink_canid();
                    can_id.Linked = Data.CANId;
                    DroidPrefs.apply(LinkedCanLdListActivity.this, BASE_CAN, can_id);
                    DroidPrefs.apply(context, CurrentuserKey, Data);
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.remove:
                    removeDialogDialog();
                    //removeAccount(pos);
                    break;
            }
            return false;
        });
    }
    public void removeDialogDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.remove_dialog, null);
        RelativeLayout tvCancel = v.findViewById(R.id.rlCancel);
        RelativeLayout tvSave = v.findViewById(R.id.rlRemove);
        dialog.setView(v);
        dialog.setCancelable(true);
        dial = dialog.create();
        dial.show();

        tvCancel.setOnClickListener(view -> {
            dial.dismiss();
            dial = null;
        });
        tvSave.setOnClickListener(view -> {
            removeAccount(pos);
        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
