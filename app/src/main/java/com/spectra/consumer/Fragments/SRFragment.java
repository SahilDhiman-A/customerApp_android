package com.spectra.consumer.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.Activities.AddCanIdActivity;
import com.spectra.consumer.Activities.CreateSrActivity;
import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.SpectraApplication;
import com.spectra.consumer.Activities.TopUpListActivity;
import com.spectra.consumer.Adapters.SRAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.GetSrRequest;
import com.spectra.consumer.service.model.Response.Contact;
import com.spectra.consumer.service.model.Response.GetSrStatusResponse;
import com.spectra.consumer.service.model.Response.SrReponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_GET_HELP;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_MY_SR;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_SERVICE;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.SiUtils.getSrDate;
import static com.spectra.consumer.service.repository.ApiConstant.CHECK_SR;
import static com.spectra.consumer.service.repository.ApiConstant.GET_SR_STATUS;

public class SRFragment extends Fragment {
    private HomeActivity homeActivity;
    private Context context;
    private View view;
    @BindView(R.id.layout_no_request)
    LinearLayout layout_no_request;
    @BindView(R.id.view_sr_list)
    RecyclerView view_sr_list;
    @BindView(R.id.layout_search)
    RelativeLayout layout_search;
    @BindView(R.id.search)
    AppCompatEditText search;
    private CurrentUserData userData;
    @BindView(R.id.layout_raise_new_request)
    AppCompatTextView layout_raise_new_request;
    @BindView(R.id.txt_no_request)
    TextView txt_no_request;
    @BindView(R.id.layout_view)
    RelativeLayout layout_view;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.img_done)
    AppCompatImageView img_done;
    @BindView(R.id.img_cross)
    AppCompatImageView img_cross;
    private SpectraViewModel spectraViewModel;
    private static String TYPE_SR;
    AlertDialog dial;
    String canIdAnalytics;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) context;
        this.context = context;
        CAN_ID canIdNik = DroidPrefs.get(context, BASE_CAN, CAN_ID.class);
        Log.d("Nik Can", canIdNik.baseCanID);
        canIdAnalytics = canIdNik.baseCanID;
    }

    public static Fragment newInstance(Bundle bundle) {
        SRFragment srFragment = new SRFragment();
        if (bundle != null) {
            srFragment.setArguments(bundle);
        }
        return srFragment;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_sr, null);
            ButterKnife.bind(this, view);
            spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            userData = DroidPrefs.get(context, Constant.CurrentuserKey, CurrentUserData.class);
            getSRDetails("normal");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            view_sr_list.setLayoutManager(linearLayoutManager);
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                img_done.setVisibility(View.GONE);
                check_keyboard();


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    img_done.setVisibility(View.VISIBLE);
                    img_cross.setVisibility(View.GONE);
                } else {
                    img_done.setVisibility(View.GONE);
                }
                check_keyboard();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    img_done.setVisibility(View.VISIBLE);
                    img_cross.setVisibility(View.GONE);
                } else {
                    img_done.setVisibility(View.GONE);
                }
                check_keyboard();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void check_keyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (imm.isActive()) {
                homeActivity.layout_tabs.setVisibility(View.GONE);
                layout_raise_new_request.setVisibility(View.GONE);
            } else {
                homeActivity.layout_tabs.setVisibility(View.VISIBLE);
                layout_raise_new_request.setVisibility(View.VISIBLE);
            }
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
                Constant.MakeToastMessage(getActivity(), apiResponse.error.getMessage());

                break;
            default:
                break;
        }
    }


    private void setSrCheckDetail(SrReponse srReponse) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.sr_check_details_dialog, null);
        AppCompatTextView img_cross = v.findViewById(R.id.img_cross);
        AppCompatTextView txtCreatedTime = v.findViewById(R.id.txtCreatedTime);
        AppCompatTextView txtDescription = v.findViewById(R.id.txtDescription);
        AppCompatTextView txt_Created_date = v.findViewById(R.id.txt_Created_date);
        AppCompatTextView txt_prob_sub_type = v.findViewById(R.id.txt_prob_sub_type);
        AppCompatTextView textStatus = v.findViewById(R.id.textStatus);
        AppCompatTextView txt_sr_number = v.findViewById(R.id.txt_sr_number);
        AppCompatTextView tvDisHint = v.findViewById(R.id.tvDisHint);
        AppCompatTextView txtProblemType = v.findViewById(R.id.txtProblemType);
        AppCompatTextView txtExpectedResolutionTime = v.findViewById(R.id.txtExpectedResolutionTime);
        dialog.setView(v);
        dialog.setCancelable(true);
        img_cross.setOnClickListener(v1 -> {
            if (dial != null) {
                dial.cancel();
            }
            if (dial != null) {
                dial.dismiss();
            }
        });
        if (TextUtils.isEmpty(srReponse.getMessageTemplate())) {
            tvDisHint.setVisibility(View.GONE);
            txtDescription.setVisibility(View.GONE);
        } else {
            txtDescription.setText(srReponse.getMessageTemplate());
        }
        String massage = getSrDate(srReponse.getsRstatusETR(), true) + " at " + getSrDate(srReponse.getsRstatusETR(), false);
        txtExpectedResolutionTime.setText(massage);
        txt_Created_date.setText(getSrDate(srReponse.getCreatedon(), true));
        txtProblemType.setText(srReponse.getProblemType());
        txt_prob_sub_type.setText(srReponse.getSubType());
        txtCreatedTime.setText(getSrDate(srReponse.getCreatedon(), false));
        textStatus.setText(srReponse.getStatus());
        txt_sr_number.setText(srReponse.getSrNumber());
        txtProblemType.setText(srReponse.getProblemType());
        dial = dialog.create();
        dial.show();
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            GetSrStatusResponse getSrStatusResponse = (GetSrStatusResponse) response;
            if (code.equals(CHECK_SR)) {
                if (getSrStatusResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    List<SrReponse> srDataList = getSrStatusResponse.getResponse();
                    if (srDataList != null && srDataList.size() > 0) {
                        setSrCheckDetail(srDataList.get(0));
                    }
                } else {
                    Constant.MakeToastMessage(getContext(), getSrStatusResponse.getMessage());
                }
            } else {
                List<SrReponse> srDataList = null;
                if (getSrStatusResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    srDataList = getSrStatusResponse.getResponse();
                    if (srDataList != null) {
                        SRAdapter srAdapter = new SRAdapter(context, srDataList, SRFragment.this);
                        view_sr_list.setVisibility(View.VISIBLE);
                        layout_search.setVisibility(View.VISIBLE);
                        layout_no_request.setVisibility(View.GONE);
                        layout_raise_new_request.setVisibility(View.VISIBLE);
                        view_sr_list.setAdapter(srAdapter);
                    }
                } else {
                    view_sr_list.setVisibility(View.GONE);
                    layout_search.setVisibility(View.GONE);
                    layout_raise_new_request.setVisibility(View.GONE);
                    layout_no_request.setVisibility(View.VISIBLE);
                    if (TYPE_SR.equalsIgnoreCase("search")) {
                        layout_search.setVisibility(View.VISIBLE);
                        txt_no_request.setText(getString(R.string.no_request_found));
                    } else {
                        layout_search.setVisibility(View.GONE);
                    }
                    layout_view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

  /*  private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            GetSrStatusResponse getSrStatusResponse = (GetSrStatusResponse) response;
            if (code.equals(CHECK_SR)) {
                if (getSrStatusResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    List<SrReponse> srDataList = getSrStatusResponse.getResponse();
                    if (srDataList != null && srDataList.size() > 0) {

                        setSrCheckDetail(srDataList.get(0));
                    }
                } else {
                    Constant.MakeToastMessage(getContext(), getSrStatusResponse.getMessage());

                }

            } else {
                List<SrReponse> srDataList = null;
                if (getSrStatusResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    srDataList = getSrStatusResponse.getResponse();
                    if (srDataList != null && srDataList.size() > 0) {
                        SRAdapter srAdapter = new SRAdapter(context, srDataList, SRFragment.this);
                        view_sr_list.setVisibility(View.VISIBLE);
                        layout_search.setVisibility(View.VISIBLE);
                        layout_no_request.setVisibility(View.GONE);
                        layout_raise_new_request.setVisibility(View.VISIBLE);
                        view_sr_list.setAdapter(srAdapter);
                    }

                } else {

                    view_sr_list.setVisibility(View.GONE);
                    layout_search.setVisibility(View.GONE);
                    layout_raise_new_request.setVisibility(View.GONE);
                    layout_no_request.setVisibility(View.VISIBLE);
                    if (TYPE_SR.equalsIgnoreCase("search")) {
                        layout_search.setVisibility(View.VISIBLE);
                        txt_no_request.setText(getString(R.string.no_request_found));
                    } else {
                        layout_search.setVisibility(View.GONE);
                    }
                    layout_view.setVisibility(View.VISIBLE);

                }
            }
        }
    }*/

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
    }

    private void getSRDetails(String type) {

        if (Constant.isInternetConnected(context)) {
            TYPE_SR = type;
            layout_raise_new_request.setVisibility(View.GONE);
            GetSrRequest getSrRequest = new GetSrRequest();
            getSrRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getSrRequest.setAction(GET_SR_STATUS);
            getSrRequest.setCanID(userData.CANId);
            if (type.equalsIgnoreCase("search")) {
                String key=Objects.requireNonNull(search.getText()).toString().trim();
                SpectraApplication.getInstance().addKey("search_query",key);
                SpectraApplication.getInstance().postEvent(CATEGORY_SERVICE,"search_by_sr_number","search", canIdAnalytics);
                getSrRequest.setSrNumber(Objects.requireNonNull(search.getText()).toString().trim());
            } else {
                getSrRequest.setSrNumber("");
            }
            spectraViewModel.getSrStatus(getSrRequest).observe(this, this::consumeResponse);
        }
    }

    public void getCheckSRDetails(String SrNumber) {
        if (TextUtils.isEmpty(SrNumber)) {
            return;
        }
        SpectraApplication.getInstance().postEvent(CATEGORY_SERVICE,"service_request_know_more","know_more_click",canIdAnalytics);

        if (Constant.isInternetConnected(context)) {
            GetSrRequest getSrRequest = new GetSrRequest();
            getSrRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getSrRequest.setAction(CHECK_SR);
            getSrRequest.setCanID2(userData.CANId);
            getSrRequest.setSrNumber(SrNumber);
            spectraViewModel.getCheckSrStatus(getSrRequest).observe(this, this::consumeResponse);
        }

    }

    @OnClick({R.id.raise_new_request, R.id.layout_raise_new_request, R.id.img_done, R.id.img_cross})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.raise_new_request:
            case R.id.layout_raise_new_request:
                SpectraApplication.getInstance().postEvent(CATEGORY_MY_SR, "raise_new_service_request", "Service request clicked",canIdAnalytics);
                Intent intent = new Intent(homeActivity, CreateSrActivity.class);
                startActivity(intent);
                break;
            case R.id.img_done:
                img_cross.setVisibility(View.VISIBLE);
                img_done.setVisibility(View.GONE);
                getSRDetails("search");
                homeActivity.layout_tabs.setVisibility(View.VISIBLE);
                Constant.hideKeyboard(homeActivity);
                break;
            case R.id.img_cross:
                search.setText("");
                img_cross.setVisibility(View.GONE);
                img_done.setVisibility(View.GONE);
                getSRDetails("normal");
                homeActivity.layout_tabs.setVisibility(View.VISIBLE);
                Constant.hideKeyboard(homeActivity);
                break;
        }
    }
}
