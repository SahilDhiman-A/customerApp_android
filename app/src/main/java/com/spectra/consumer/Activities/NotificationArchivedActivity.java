package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Adapters.NotificationChildAdapter;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationInfoData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponseBase;
import com.spectra.consumer.viewModel.SpectraNotificationViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.STATUS_CODE;
import static com.spectra.consumer.Utils.Constant.allCan;
import static com.spectra.consumer.service.repository.ApiConstant.DELETE_NOTIFICATION;
import static com.spectra.consumer.service.repository.ApiConstant.GET_ARCHIVED_NOTIFICATION;
import static com.spectra.consumer.service.repository.ApiConstant.READ_NOTIFICATION;

public class NotificationArchivedActivity extends AppCompatActivity {
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
    int Skip = 0, limit = 20, SLAP = 20, listCount = 0, maxCount = 0;
    private boolean loading = true;
    ArrayList<Integer> integers;
    int readPos, type;
    private NotificationChildAdapter notificationListAdapter;
    private SpectraNotificationViewModel spectraViewModel;
    private ArrayList<NotificationInfoData> notificationInfoData = new ArrayList<>();
    private boolean canEvent = true;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        canEvent = true;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_archived);
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this);
        rvContacts.setLayoutManager(linearLayoutManager);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraNotificationViewModel.class);
        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        try_again.setVisibility(View.GONE);
        noContactMassage.setText(R.string.noNotification);
        rvContacts.setVisibility(View.VISIBLE);
        ivIcon.setImageResource(R.drawable.ic_noti_empty);
        reset();
    }

    private void getNotificationList() {
        if (Constant.isInternetConnected(this)) {
            if (!integers.contains(Skip)) {
                integers.add(Skip);
                spectraViewModel.getAllNotification(GET_ARCHIVED_NOTIFICATION, allCan, Skip, SLAP).observe(NotificationArchivedActivity.this, NotificationArchivedActivity.this::consumeResponse);
                if (!integers.contains(Skip)) {
                    integers.add(Skip);
                }
            }
        }
    }

    public void reset() {
        Skip = 0;
        limit = 20;
        SLAP = 20;
        listCount = 0;
        maxCount = 0;
        notificationListAdapter = null;
        notificationInfoData = new ArrayList<>();
        integers = new ArrayList<>();
        getNotificationList();
    }

    public void deleteNotification(ArrayList<NotificationInfoData> list, int pos) {
        if (Constant.isInternetConnected(this)) {
            String ids = list.get(0).get_id();
            readPos = pos;

            spectraViewModel.deleteNotification(DELETE_NOTIFICATION, ids).observe(NotificationArchivedActivity.this, NotificationArchivedActivity.this::consumeResponse);
        }
    }

    public void readNotification(ArrayList<NotificationInfoData> list,boolean isRead, int pos, int type) {
        this.type = type;
        if (Constant.isInternetConnected(this)) {
            readPos = pos;
            if (!isRead) {
                String ids = list.get(0).get_id();
                spectraViewModel.readNotification(READ_NOTIFICATION, ids).observe(NotificationArchivedActivity.this, NotificationArchivedActivity.this::consumeResponse);
            } else {
                readAction();
            }
        }
    }

    private void readAction() {
        if (type != 1) {
            Intent intent = new Intent(NotificationArchivedActivity.this, HomeActivity.class);
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
                Constant.MakeToastMessage(NotificationArchivedActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            NotificationResponseBase notificationResponseBase;
            switch (code) {
                case GET_ARCHIVED_NOTIFICATION:
                    NotificationResponse notificationResponse = (NotificationResponse) response;
                    Skip = Skip + SLAP;
                    if (notificationResponse.getStatus() == STATUS_CODE) {
                        maxCount = notificationResponse.getMaxCount();
                        ArrayList<NotificationData> notificationDataList1 = notificationResponse.getResponse();
                        if (notificationDataList1 != null && notificationDataList1.size() > 0) {
                            if (notificationListAdapter == null) {
                                linearLayoutManager = new LinearLayoutManager(this);
                                rvContacts.setLayoutManager(linearLayoutManager);
                                rvContacts.setHasFixedSize(true);
                                rvContacts.setVisibility(View.VISIBLE);
                                for (NotificationData notificationData : notificationDataList1) {
                                    notificationInfoData.addAll(notificationData.getResponse());
                                }
                                notificationListAdapter = new NotificationChildAdapter(this, notificationInfoData, false,  true, false);
                                rvContacts.setAdapter(notificationListAdapter);
                                pagination();
                            } else {

                                for (NotificationData notificationData : notificationDataList1) {
                                    final NotificationData.DateData dateData = notificationData.getData();
                                    notificationInfoData.addAll(notificationData.getResponse());
                                }
                                notificationListAdapter.updateList(notificationInfoData, false);
                            }
                            loading = false;
                        }
                        setContent(false);
                    } else {
                        setContent(false);
                        Constant.MakeToastMessage(NotificationArchivedActivity.this, notificationResponse.getMessage());
                    }

                    break;
                case READ_NOTIFICATION:
                    notificationResponseBase = (NotificationResponseBase) response;
                    if (notificationResponseBase.getStatus() == STATUS_CODE) {
                        readAction();
                    } else
                        Constant.MakeToastMessage(NotificationArchivedActivity.this, notificationResponseBase.getMessage());
                    break;
                case DELETE_NOTIFICATION:
                    notificationResponseBase = (NotificationResponseBase) response;
                    if (notificationResponseBase.getStatus() == STATUS_CODE) {
                        if (readPos == -1) {
                            reset();
                        } else {
                            notificationInfoData.remove(readPos);
                            NotificationLActivity.notificationSelected.clear();
                            notificationListAdapter.notifyItemRemoved(readPos);
                            setContent(true);
                        }
                    } else
                        Constant.MakeToastMessage(NotificationArchivedActivity.this, notificationResponseBase.getMessage());
                    break;
            }
        }
    }

    private void setContent(boolean isUpdate) {
        if (notificationInfoData.size() > 0) {
            no_internet.setVisibility(View.GONE);
            if (isUpdate) {
                notificationListAdapter.updateList(notificationInfoData, false);
            }
        } else {
            no_internet.setVisibility(View.VISIBLE);
        }
    }
    public Boolean isProgress(){
        return canEvent;
    }
    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);

        } else {
            progress_bar.setVisibility(View.GONE);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> canEvent=true, 1000);
        }
    }

    public void pagination() {
        if (!loading) {
            loading = true;
            if (Skip < maxCount) {
                getNotificationList();
            }
        }
    }


    @OnClick({R.id.img_cross})
    public void onClick(View view) {
        if (view.getId() == R.id.img_cross) {
            Intent intent = new Intent(NotificationArchivedActivity.this, NotificationLActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
