package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Adapters.NotificationChildAdapter;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationInfoData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponseBase;
import com.spectra.consumer.service.model.Response.noticfication.NotificationSearchResponse;
import com.spectra.consumer.viewModel.SpectraNotificationViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.STATUS_CODE;
import static com.spectra.consumer.Utils.Constant.allCan;
import static com.spectra.consumer.service.repository.ApiConstant.READ_NOTIFICATION;
import static com.spectra.consumer.service.repository.ApiConstant.SEARCH_NOTIFICATION;

public class NotificationSearchActivity extends AppCompatActivity {
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_payment)
    TextView noContactMassage;

    @BindView(R.id.try_again)
    AppCompatTextView try_again;
    @BindView(R.id.img_payment_status)
    AppCompatImageView ivIcon;
    @BindView(R.id.img_cross)
    AppCompatImageView img_cross;
    @BindView(R.id.etSearchBar)
    EditText etSearchBar;
    private NotificationChildAdapter notificationListAdapter;
    private SpectraNotificationViewModel spectraViewModel;
    private ArrayList<NotificationInfoData> notificationDataList = new ArrayList<>();
    int type;
    int pos = 0;
    private boolean canEvent = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_search);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvContacts.setLayoutManager(linearLayoutManager);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraNotificationViewModel.class);
        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        try_again.setVisibility(View.GONE);
        noContactMassage.setText(R.string.noNotification);
        rvContacts.setVisibility(View.VISIBLE);
        ivIcon.setImageResource(R.drawable.ic_noti_empty);
        no_internet.setVisibility(View.GONE);
        searchWork();
        //getNotificationList("Dear");
    }

    @Override
    protected void onResume() {
        super.onResume();
        canEvent = true;
    }

    private void getNotificationList(String text) {
        if (Constant.isInternetConnected(this)) {
            spectraViewModel.searchNotification(SEARCH_NOTIFICATION, allCan, text, 0).observe(NotificationSearchActivity.this, NotificationSearchActivity.this::consumeResponse);
        }
    }

    private void searchWork() {
        etSearchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2)
                    getNotificationList(s.toString());
            }
        });
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
                Constant.MakeToastMessage(NotificationSearchActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            NotificationResponseBase notificationResponseBase;
            switch (code) {
                case SEARCH_NOTIFICATION:
                    NotificationSearchResponse notificationResponse = (NotificationSearchResponse) response;
                    if (notificationResponse.getStatus() == STATUS_CODE) {
                        notificationDataList = notificationResponse.getResponse();
                        setContent(false);
                        if (notificationDataList != null && notificationDataList.size() > 0) {
                            if (notificationListAdapter == null) {
                                notificationListAdapter = new NotificationChildAdapter(this, notificationDataList, false,  false, true);
                                rvContacts.setAdapter(notificationListAdapter);
                            } else {
                                notificationListAdapter.updateList(notificationDataList, false);
                            }
                        }
                    } else {
                        notificationDataList = new ArrayList<>();
                        setContent(true);
                        Constant.MakeToastMessage(NotificationSearchActivity.this, notificationResponse.getMessage());
                    }
                    break;
                case READ_NOTIFICATION:
                    notificationResponseBase = (NotificationResponseBase) response;
                    if (notificationResponseBase.getStatus() == STATUS_CODE) {
                        readAction();
                    } else
                        Constant.MakeToastMessage(NotificationSearchActivity.this, notificationResponseBase.getMessage());
                    break;
            }
        }
    }

    private void setContent(boolean isUpdate) {
        no_internet.setVisibility(View.GONE);
        if (notificationDataList.size() > 0) {
            rvContacts.setVisibility(View.VISIBLE);
            if (isUpdate) {
                notificationListAdapter.updateList(notificationDataList, false);
            }
        } else {
            rvContacts.setVisibility(View.GONE);
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            canEvent=false;
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> canEvent = true, 1000);
        }
    }

    public Boolean isProgress() {
        return canEvent;
    }

    @OnClick({R.id.img_cross})
    public void onClick(View view) {
        if (view.getId() == R.id.img_cross) {
            Intent intent = new Intent(NotificationSearchActivity.this, NotificationLActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void readNotification(ArrayList<NotificationInfoData> list, boolean isRead, int pos, int type) {
        this.type = type;
        this.pos = pos;

        if (Constant.isInternetConnected(this)) {
            if (!isRead) {
                String ids = list.get(0).get_id();
                spectraViewModel.readNotification(READ_NOTIFICATION, ids).observe(NotificationSearchActivity.this, NotificationSearchActivity.this::consumeResponse);
            } else {
                readAction();
            }
        }
    }

    private void readAction() {
        if (type != 1) {
            Intent intent = new Intent(NotificationSearchActivity.this, HomeActivity.class);
            if (type == 2) {
                intent.putExtra("cameFromScreen", "SR_TAB");
            }
            if (type == 3) {
                intent.putExtra("cameFromScreen", "INVOICE_TAB");
            }
            startActivity(intent);
            finish();
        }
    }
}
