package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spectra.consumer.Adapters.NotificationChildAdapter;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.EndlessRecyclerViewScrollListener;
import com.spectra.consumer.service.model.ApiResponse;

import com.spectra.consumer.service.model.Response.noticfication.NotificationData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationInfoData;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponse;
import com.spectra.consumer.service.model.Response.noticfication.NotificationResponseBase;
import com.spectra.consumer.viewModel.SpectraNotificationViewModel;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Activities.HomeActivity.canID;
import static com.spectra.consumer.Utils.Constant.STATUS_CODE;
import static com.spectra.consumer.Utils.Constant.allCan;
import static com.spectra.consumer.service.repository.ApiConstant.ALL_NOTIFICATION;
import static com.spectra.consumer.service.repository.ApiConstant.ARCHIVED_NOTIFICATION;
import static com.spectra.consumer.service.repository.ApiConstant.DELETE_NOTIFICATION;
import static com.spectra.consumer.service.repository.ApiConstant.READ_NOTIFICATION;

public class NotificationLActivity extends AppCompatActivity {
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar;
    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_head)
    TextView txt_head;
    @BindView(R.id.txt_Pull)
    TextView txtPull;


    @BindView(R.id.txt_payment)
    TextView noContactMassage;

    @BindView(R.id.try_again)
    AppCompatTextView try_again;

    @BindView(R.id.img_payment_status)
    AppCompatImageView ivIcon;
    @BindView(R.id.imgAchive)
    AppCompatImageView imgAchive;
    @BindView(R.id.imgSearch)
    AppCompatImageView imgSearch;
    int Skip = 0, SLAP = 20, maxCount = 0;
    boolean isRefress = false;
    int readPos = -1, type;
    boolean isEdit = false;
    private SwipeRefreshLayout pullToRefresh;
    private NotificationChildAdapter notificationListAdapter;
    private SpectraNotificationViewModel spectraViewModel;

    LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    ArrayList<Integer> integers;
    boolean isScroll;
    int n = 0;
    AlertDialog dial;
    private EndlessRecyclerViewScrollListener scrollListener;
    public static ArrayList<NotificationInfoData> notificationSelected = new ArrayList<>();
    private ArrayList<NotificationInfoData> notificationInfoData = new ArrayList<>();
    private ArrayList<String> dateMap = new ArrayList<>();
    private boolean canEvent = true;
    String today = "", Yesterday = "";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onResume() {
        super.onResume();
        canEvent = true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_contect);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        txt_head.setText(R.string.notification);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraNotificationViewModel.class);
        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        try_again.setVisibility(View.GONE);
        noContactMassage.setText(R.string.noNotification);
        ivIcon.setImageResource(R.drawable.ic_noti_empty);
        try {
            Calendar calendar = Calendar.getInstance();
            today = originalFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, -1);
            Yesterday = originalFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        swipeRefresh();
        editClick(false);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("NOT_ID")) {
            String id = bundle.getString("NOT_ID");
            String canId = bundle.getString("CAN_ID");
           if(!TextUtils.isEmpty(id)) {
               readPos = -1;
               if (!canID.equals(canId)) {
                   onUpdateNeeded(NotificationLActivity.this, canId);
               }
               spectraViewModel.readNotification(READ_NOTIFICATION, id).observe(NotificationLActivity.this, NotificationLActivity.this::consumeResponse);
           }
        } else {
            reset();
        }

    }

    private void getNotificationList() {
        if (Constant.isInternetConnected(this)) {
            editClick(isEdit);
            if (!integers.contains(Skip) && progress_bar.getVisibility() == View.GONE) {
                integers.add(Skip);
                spectraViewModel.getAllNotification(ALL_NOTIFICATION, allCan, Skip, SLAP).observe(NotificationLActivity.this, NotificationLActivity.this::consumeResponse);
                if (!integers.contains(Skip)) {
                    integers.add(Skip);
                }
            }

        }
    }

    public void deleteNotification(ArrayList<NotificationInfoData> list, int pos) {
        if (Constant.isInternetConnected(this)) {
            String ids = getListId(list);
            readPos = pos;
            isScroll = false;
            spectraViewModel.deleteNotification(DELETE_NOTIFICATION, ids).observe(NotificationLActivity.this, NotificationLActivity.this::consumeResponse);
        }
    }

    public void archivedNotification(ArrayList<NotificationInfoData> list, int pos) {
        if (Constant.isInternetConnected(this)) {
            String ids = getListId(list);
            readPos = pos;
            isScroll = false;
            spectraViewModel.archivedNotification(ARCHIVED_NOTIFICATION, ids).observe(NotificationLActivity.this, NotificationLActivity.this::consumeResponse);
        }
    }

    public void editClick(boolean isEdit) {
        if (notificationListAdapter != null) {
            notificationListAdapter.updateList(notificationInfoData, isEdit);
        }
        this.isEdit = isEdit;
        if (Constant.isInternetConnected(this)) {
            if (isEdit) {
                txtPull.setVisibility(View.INVISIBLE);
                txt_head.setVisibility(View.INVISIBLE);
                imgSearch.setImageResource(R.drawable.ic_delete_white);
                imgSearch.setOnClickListener(v -> {

                    if (notificationListAdapter != null && progress_bar.getVisibility() == View.GONE) {
                        notificationSelected = notificationListAdapter.getGetArray();
                        if (notificationSelected != null && notificationSelected.size() > 0) {

                            deleteNotification(notificationSelected, -1);
                        }
                    }
                });
                imgAchive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (notificationListAdapter != null && progress_bar.getVisibility() == View.GONE) {
                            notificationSelected = notificationListAdapter.getGetArray();
                            if (notificationSelected != null && notificationSelected.size() > 0) {
                                archivedNotification(notificationSelected, -1);
                            }
                        }
                    }
                });
            } else {
                txtPull.setVisibility(View.VISIBLE);
                txt_head.setVisibility(View.VISIBLE);
                imgSearch.setImageResource(R.drawable.ic_search_white);
                imgSearch.setOnClickListener(v -> {
                    Intent intent = new Intent(NotificationLActivity.this, NotificationSearchActivity.class);
                    startActivity(intent);
                    finish();
                });
                imgAchive.setOnClickListener(v -> {
                    Intent intent = new Intent(NotificationLActivity.this, NotificationArchivedActivity.class);
                    startActivity(intent);
                });
            }
        }
    }

    public void readNotification(ArrayList<NotificationInfoData> list, boolean isRead, int pos, int type) {
        this.type = type;
        if (Constant.isInternetConnected(this)) {
            readPos = pos;
            if (!isRead) {
                String ids = getListId(list);
                isScroll = false;
                spectraViewModel.readNotification(READ_NOTIFICATION, ids).observe(NotificationLActivity.this, NotificationLActivity.this::consumeResponse);
            } else {
                readAction();
            }
        }
    }

    private void readAction() {
        if (type != 1) {
            Intent intent = new Intent(NotificationLActivity.this, HomeActivity.class);
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

    protected void swipeRefresh() {
        pullToRefresh = findViewById(R.id.pullToRefresh);
        if (!isEdit) {

            pullToRefresh.setOnRefreshListener(() -> {
                isRefress = false;
                pullToRefresh.setRefreshing(false);
                if (isEdit) {
                    return;
                }
                reset();
            });
        }
    }

    public void reset() {
        Skip = 0;
        SLAP = 20;
        maxCount = 0;
        loading = false;
        readPos = -1;
        integers = new ArrayList<>();
        notificationInfoData = new ArrayList<>();
        dateMap.clear();
        notificationSelected = new ArrayList<>();
        isScroll = false;
        getNotificationList();
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
                Constant.MakeToastMessage(NotificationLActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            NotificationResponseBase notificationResponseBase;
            switch (code) {
                case ALL_NOTIFICATION:
                    NotificationResponse notificationResponse = (NotificationResponse) response;
                    Skip = Skip + SLAP;
                    if (notificationResponse.getStatus() == STATUS_CODE) {
                        maxCount = notificationResponse.getMaxCount();
                        if (notificationInfoData == null || notificationInfoData.isEmpty()) {
                            dateMap.clear();
                        }

                        ArrayList<NotificationData> notificationDataList1 = notificationResponse.getResponse();
                        if (notificationDataList1 != null && notificationDataList1.size() > 0) {
                            if (notificationListAdapter == null) {
                                linearLayoutManager = new LinearLayoutManager(this);
                                rvContacts.setLayoutManager(linearLayoutManager);
                                rvContacts.setVisibility(View.VISIBLE);
                                for (NotificationData notificationData : notificationDataList1) {
                                    final NotificationData.DateData dateData = notificationData.getData();
                                    if (!dateMap.contains(dateData.getDate())) {
                                        final String dateString = getDate(dateData.getYear(), dateData.getMonth(), dateData.getDay());
                                        notificationData.getResponse().get(0).setDate(dateString);
                                        dateMap.add(dateData.getDate());
                                    }
                                    notificationInfoData.addAll(notificationData.getResponse());
                                }
                                notificationListAdapter = new NotificationChildAdapter(this, notificationInfoData, isEdit, false, false);
                                rvContacts.setAdapter(notificationListAdapter);
                                pagination();
                            } else {

                                for (NotificationData notificationData : notificationDataList1) {
                                    final NotificationData.DateData dateData = notificationData.getData();
                                    if (!dateMap.contains(dateData.getDate())) {
                                        final String dateString = getDate(dateData.getYear(), dateData.getMonth(), dateData.getDay());
                                        notificationData.getResponse().get(0).setDate(dateString);
                                        dateMap.add(dateData.getDate());
                                    }
                                    notificationInfoData.addAll(notificationData.getResponse());
                                }


                                notificationListAdapter.updateList(notificationInfoData, isEdit);
                            }
                            loading = false;
                        }
                        setContent(false);
                    } else {
                        notificationInfoData = new ArrayList<>();
                        dateMap.clear();
                        setContent(false);
                        Constant.MakeToastMessage(NotificationLActivity.this, notificationResponse.getMessage());
                    }

                    break;
                case READ_NOTIFICATION:
                    notificationResponseBase = (NotificationResponseBase) response;
                    if (notificationResponseBase.getStatus() == STATUS_CODE) {
                        if (readPos == -1) {
                            if (notificationListAdapter == null) {
                                reset();
                            }
                        } else {
                            readAction();
                        }
                    } else
                        Constant.MakeToastMessage(NotificationLActivity.this, notificationResponseBase.getMessage());
                    break;
                case DELETE_NOTIFICATION:
                case ARCHIVED_NOTIFICATION:
                    notificationResponseBase = (NotificationResponseBase) response;
                    if (notificationResponseBase.getStatus() == STATUS_CODE) {
                        if (readPos == -1) {
                            reset();
                        } else {
                            if (notificationInfoData != null) {
                                if (notificationInfoData.size() > readPos && TextUtils.isEmpty(notificationInfoData.get(readPos).getDate())) {
                                    notificationInfoData.remove(readPos);
                                    NotificationLActivity.notificationSelected.clear();
                                    notificationListAdapter.updateList(notificationInfoData, false);
                                    setContent(true);
                                } else {
                                    int next = readPos + 1;
                                    if (notificationInfoData.size() > next && TextUtils.isEmpty(notificationInfoData.get(next).getDate())) {
                                        notificationInfoData.get(next).setDate(notificationInfoData.get(readPos).getDate());
                                        notificationInfoData.remove(readPos);
                                        NotificationLActivity.notificationSelected.clear();
                                        notificationListAdapter.updateList(notificationInfoData, false);
                                        setContent(true);
                                    } else {
                                        if (notificationInfoData.size() > readPos) {
                                            notificationInfoData.remove(readPos);
                                            NotificationLActivity.notificationSelected.clear();
                                            notificationListAdapter.updateList(notificationInfoData, false);
                                            setContent(true);
                                        } else {
                                            reset();
                                        }
                                    }

                                }
                            }
                        }
                    } else
                        Constant.MakeToastMessage(NotificationLActivity.this, notificationResponseBase.getMessage());
                    break;
            }
        }
    }

    private void setContent(boolean isUpdate) {
        if (notificationInfoData.size() > 0) {
            rvContacts.setVisibility(View.VISIBLE);
            no_internet.setVisibility(View.GONE);
            if (isUpdate) {
                notificationListAdapter.updateList(notificationInfoData, isEdit);
            }
        } else {
            rvContacts.setVisibility(View.GONE);
            no_internet.setVisibility(View.VISIBLE);
        }
    }

    public Boolean isProgress() {
        return canEvent;
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
            canEvent = false;
        } else {
            progress_bar.setVisibility(View.GONE);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> canEvent = true, 1000);

        }
    }

    public void pagination() {
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!loading) {
                    loading = true;
                    if (Skip < maxCount) {
                        isScroll = true;
                        getNotificationList();
                    }
                }
            }
        };
        rvContacts.addOnScrollListener(scrollListener);
    }


    @OnClick({R.id.img_back, R.id.try_again})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            onBackPressed();
        }

    }

    private String getListId(ArrayList<NotificationInfoData> list) {
        String ids = "";
        for (NotificationInfoData notificationData : list) {
            if (!TextUtils.isEmpty(ids)) {
                ids = ids + ",";
            }
            ids = ids + notificationData.get_id();
        }
        return ids;
    }

    @Override
    public void onBackPressed() {
        if (isEdit && notificationListAdapter != null) {
            editClick(false);

        } else {
            Intent intent = new Intent(NotificationLActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public String getDate(int year, int month, int dayOfMonth) {
        try {
            DecimalFormat formatter = new DecimalFormat("00");
            String m = formatter.format(month);
            String d = formatter.format(dayOfMonth);
            String dateNot = d + "/" + m + "/" + year;
            if (dateNot.equals(today)) {
                return "Today";
            } else {
                if (dateNot.equals(Yesterday)) {
                    return "Yesterday";
                } else {
                    try {
                        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy");
                        Date date = originalFormat.parse(dateNot);
                        return targetFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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


}
