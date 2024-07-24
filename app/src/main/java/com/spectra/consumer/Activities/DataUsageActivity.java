package com.spectra.consumer.Activities;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.spectra.consumer.Adapters.SelectMrtgTypeAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.MrtgType;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.GetMrtgRequest;
import com.spectra.consumer.service.model.Request.GetSessionHistoryRequest;
import com.spectra.consumer.service.model.Response.DataUsageResponse;
import com.spectra.consumer.service.model.Response.GetMrtgResponse;
import com.spectra.consumer.service.model.Response.GetSessionHistoryResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.spectra.consumer.Utils.Constant.SEGMENT_BUSINESS;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.GET_MRTG_BY_CANID;
import static com.spectra.consumer.service.repository.ApiConstant.GET_SESSION_HISTORY;


public class DataUsageActivity extends AppCompatActivity {
    public static final String TAG = "DataUsageActivity::";

    @BindView(R.id.linechart)
    LineChart linechart;
    @BindView(R.id.linechartDummy)
    TextView linechartDummy;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;
    @BindView(R.id.layout_select_custom)
    LinearLayout layout_select_custom;
    @BindView(R.id.layout_select_date)
    RelativeLayout layout_select_date;
    @BindView(R.id.select_from_date)
    RelativeLayout select_from_date;
    @BindView(R.id.txt_from_date)
    TextView txt_from_date;
    @BindView(R.id.select_to_date)
    RelativeLayout select_to_date;
    @BindView(R.id.txt_to_date)
    TextView txt_to_date;
    @BindView(R.id.txt_custom)
    TextView txt_custom;
    @BindView(R.id.view_custom)
    View view_custom;
    @BindView(R.id.layout_six_month)
    LinearLayout layout_six_month;
    @BindView(R.id.txt_six_month)
    TextView txt_six_month;
    @BindView(R.id.view_six_month)
    View view_six_month;
    @BindView(R.id.layout_select_one_month)
    LinearLayout layout_select_one_month;
    @BindView(R.id.txt_one_month)
    TextView txt_one_month;
    @BindView(R.id.view_one_month)
    View view_one_month;
    private String from_date, to_date;
    @BindView(R.id.img_mrtg)
    ImageView img_mrtg;
    @BindView(R.id.data_usage)
    CardView data_usage;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.progress_graph)
    ProgressBar progress_graph;
    boolean is_valid;
    CurrentUserData currentUserData;
    @BindView(R.id.txt_month)
    TextView txt_month;
    @BindView(R.id.txt_data_used)
    TextView txt_data_used;
    @BindView(R.id.view_select_mrtg_type)
    RelativeLayout view_select_mrtg_type;
    @BindView(R.id.view_select_type)
    RecyclerView view_select_type;
    private List<MrtgType> mrtgTypeList = new ArrayList<>();
    SelectMrtgTypeAdapter adapter;
    String data_type;
    @BindView(R.id.txt_type)
    AppCompatTextView txt_type;
    @BindView(R.id.txt_total)
    TextView txt_total;
    LinearLayoutManager linearLayoutManager;
    PhotoViewAttacher pAttacher;
    SpectraViewModel spectraViewModel;
    private static String dataUsageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_data_usage);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        linearLayoutManager = new LinearLayoutManager(this);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        view_select_type.setLayoutManager(linearLayoutManager);
        currentUserData = DroidPrefs.get(DataUsageActivity.this, Constant.CurrentuserKey, CurrentUserData.class);

        if (currentUserData.Segment.equalsIgnoreCase(SEGMENT_BUSINESS)) {
            txt_type.setText(getString(R.string.txt_24_hours));
            get_mrtg_data("1");
            txt_head.setText(getString(R.string.mrtg_graph));
            view_select_mrtg_type.setVisibility(View.VISIBLE);
        } else {
            select_one_month_tab();
            view_select_mrtg_type.setVisibility(View.GONE);
            txt_head.setText(getString(R.string.data_usage));
            img_mrtg.setVisibility(View.GONE);
            linechart.getAxisLeft().setDrawGridLines(false);
            linechart.getAxisRight().setShowOnlyMinMax(true);
            linechart.getXAxis().setDrawGridLines(false);
            linechart.getLegend().setEnabled(false);
            linechart.setDescription("");
            linechart.getAxisRight().setEnabled(false);
            linechart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            linechart.setExtraOffsets(10, 10, 10, 10);
        }

        select_from_date.setOnClickListener(view -> {
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = getString(R.string.date_format_MMM_yyyy);
                String format = getString(R.string.date_format_yyyy_MM_dd);
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txt_from_date.setText(sdf.format(myCalendar.getTime()));
                SimpleDateFormat form = new SimpleDateFormat(format, Locale.US);
                from_date = form.format(myCalendar.getTime());
                if (to_date != null && !to_date.equalsIgnoreCase("")) {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date frm_dt = null;
                    Date to_dt = null;
                    try {
                        frm_dt = sdff.parse(from_date);
                        to_dt = sdff.parse(to_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diff = 0;
                    if (to_dt != null) {
                        if (frm_dt != null) {
                            diff = to_dt.getTime() - frm_dt.getTime();
                        }
                    }

                    if (diff >= 0) {
                        long days = diff / (24 * 60 * 60 * 1000);
                        if (days <= 365) {
                            is_valid = true;
                        } else {
                            is_valid = false;
                            txt_from_date.setText(R.string.select_date);
                            Constant.MakeToastMessage(DataUsageActivity.this, getString(R.string.can_select_max_one));
                        }
                    } else {
                        is_valid = false;
                        txt_from_date.setText(R.string.select_date);
                        Constant.MakeToastMessage(DataUsageActivity.this, getString(R.string.no_blank_date));
                    }
                } else {
                    is_valid = false;
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();

        });

        select_to_date.setOnClickListener(view -> {
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = getString(R.string.date_format_MMM_yyyy);
                String format = getString(R.string.date_format_yyyy_MM_dd);
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txt_to_date.setText(sdf.format(myCalendar.getTime()));
                SimpleDateFormat form = new SimpleDateFormat(format, Locale.US);
                to_date = form.format(myCalendar.getTime());
                if (from_date != null && !from_date.equalsIgnoreCase("")) {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date frm_dt = null;
                    Date to_dt = null;
                    try {
                        frm_dt = sdff.parse(from_date);
                        to_dt = sdff.parse(to_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diff = 0;
                    if (to_dt != null) {
                        if (frm_dt != null) {
                            diff = to_dt.getTime() - frm_dt.getTime();
                        }
                    }

                    if (diff >= 0) {

                        long days = diff / (24 * 60 * 60 * 1000);
                        if (days <= 365) {
                            is_valid = true;
                        } else {
                            is_valid = false;
                            txt_to_date.setText(R.string.select_date);
                            Constant.MakeToastMessage(DataUsageActivity.this, getString(R.string.can_select_max_one));
                        }

                    } else {
                        is_valid = false;
                        txt_to_date.setText(R.string.select_date);
                        Constant.MakeToastMessage(DataUsageActivity.this, getString(R.string.no_blank_date));
                    }

                } else {
                    is_valid = false;
                }
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

    }

    private ArrayList<String> setXAxisValues(List<DataUsageResponse> dataUsageResponses, String type) {
        ArrayList<String> xVals = new ArrayList<>();
        if (xVals.size() > 0) {
            xVals.clear();
        }
        if (dataUsageResponses != null && dataUsageResponses.size() > 0) {
            String typeDta = "data";
            if (type.equalsIgnoreCase("six_month")) {
                typeDta = "six_month";
            }
            for (DataUsageResponse data : dataUsageResponses) {
                xVals.add(parse_x_data(data, typeDta));
            }
        }
        Set<String> set = new LinkedHashSet<>(xVals);
        xVals.clear();
        xVals.addAll(set);
        return xVals;
    }


    @SuppressLint("SetTextI18n")
    private ArrayList<Entry> setYAxisValues(List<Float> list) {

        ArrayList<Entry> yVals = new ArrayList<>();
        if (yVals.size() > 0) {
            yVals.clear();
        }
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                yVals.add(new Entry(list.get(i), i));
            }
            float sum = 0;
            for (int i = 0; i < list.size(); i++) {
                sum += list.get(i);
            }
            float sum_val = Constant.round(sum);
            txt_total.setVisibility(View.VISIBLE);
            txt_data_used.setVisibility(View.VISIBLE);
            txt_data_used.setText("" + sum_val + " GB");
        }
        return yVals;
    }


    private void setData(List<DataUsageResponse> data_usage, List<Float> list, String type) {
        ArrayList<String> xVals = setXAxisValues(data_usage, type);
        ArrayList<String> xvals_months = new ArrayList<>();
        LineData data;
        ArrayList<Entry> yVals = setYAxisValues(list);
        LineDataSet set1;
        set1 = new LineDataSet(yVals, null);
        set1.setFillAlpha(110);
        set1.setColor(getResources().getColor(R.color.back_color));
        set1.setCircleColor(getResources().getColor(R.color.back_color));
        set1.setLineWidth(3f);
        set1.setCircleRadius(6f);
        set1.setDrawCircleHole(true);
        set1.setDrawValues(false);
        set1.setDrawFilled(false);
        set1.disableDashedLine();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        if (type.equalsIgnoreCase("six_month")) {
            String value = "";
            for (int i = 0; i < xVals.size(); i++) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
                Date parse_data = null;
                String myFormat = "MMM";
                try {
                    parse_data = sdf.parse(xVals.get(i));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat my_format = new SimpleDateFormat(myFormat, Locale.US);
                if (parse_data != null) {
                    value = my_format.format(parse_data.getTime());
                }
                xvals_months.add(value);

            }
            data = new LineData(xvals_months, dataSets);
        } else {
            data = new LineData(xVals, dataSets);
        }
        linechart.setVisibility(View.VISIBLE);
        linechartDummy.setVisibility(View.GONE);
        linechart.setData(data);
    }

    public void get_mrtg_data(String dateType) {
        if (Constant.isInternetConnected(this)) {
            img_mrtg.setVisibility(View.GONE);
            GetMrtgRequest getMrtgRequest = new GetMrtgRequest();
            getMrtgRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getMrtgRequest.setAction(GET_MRTG_BY_CANID);
            getMrtgRequest.setCanID(currentUserData.CANId);
            getMrtgRequest.setDateType(dateType);
            spectraViewModel.getMrtg(getMrtgRequest).observe(DataUsageActivity.this, DataUsageActivity.this::consumeResponse);
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
                case GET_MRTG_BY_CANID:
                    GetMrtgResponse getMrtgResponse = (GetMrtgResponse) response;
                    if (getMrtgResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        String res = getMrtgResponse.getResponse();
                        convert(res);
                        img_mrtg.setVisibility(View.VISIBLE);
                        data_usage.setVisibility(View.GONE);
                    } else {
                        Constant.MakeToastMessage(DataUsageActivity.this, getMrtgResponse.getMessage());
                    }
                    break;
                case GET_SESSION_HISTORY:
                    GetSessionHistoryResponse getSessionHistoryResponse = (GetSessionHistoryResponse) response;
                    if (getSessionHistoryResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        progress_graph.setVisibility(View.GONE);
                        List<DataUsageResponse> dataUsageResponses = getSessionHistoryResponse.getResponse();
                        if (dataUsageResponses != null && dataUsageResponses.size() > 0) {
                            Sort_data(dataUsageResponses, dataUsageType);
                        }
                    } else {
                        Constant.MakeToastMessage(DataUsageActivity.this, getSessionHistoryResponse.getMessage());
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

    private void convert(String res) {
        if (TextUtils.isEmpty(res)) {
            return;
        }
        try {
            byte[] encodeByte = Base64.decode(res, Base64.DEFAULT);
            if (encodeByte == null) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if (bitmap == null) {
                return;
            }
            Bitmap bitmap_img = Bitmap.createScaledBitmap(bitmap, 2000, 1400, false);
            if (bitmap_img == null) {
                return;
            }
            if (img_mrtg == null) {
                return;
            }
            img_mrtg.setImageBitmap(bitmap_img);
            pAttacher = new PhotoViewAttacher(img_mrtg);
            pAttacher.update();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void get_data_usage() {
        if (Constant.isInternetConnected(this)) {
            img_mrtg.setVisibility(View.GONE);
            progress_graph.setVisibility(View.VISIBLE);
            linechart.setVisibility(View.GONE);
            linechartDummy.setVisibility(View.VISIBLE);
            txt_total.setVisibility(View.GONE);
            txt_data_used.setVisibility(View.GONE);

            GetSessionHistoryRequest getSessionHistoryRequest = new GetSessionHistoryRequest();
            getSessionHistoryRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getSessionHistoryRequest.setAction(GET_SESSION_HISTORY);
            getSessionHistoryRequest.setCanID(currentUserData.CANId);
            getSessionHistoryRequest.setFromDate(from_date);
            getSessionHistoryRequest.setToDate(to_date);
            spectraViewModel.getSessionHistory(getSessionHistoryRequest).observe(DataUsageActivity.this, DataUsageActivity.this::consumeResponse);
        }
    }

    public void select_month(String type){

        String parse_format = getString(R.string.date_format_yyyy_MM_dd);
        String month_format = "dd MMMM yyyy";
        SimpleDateFormat form = new SimpleDateFormat(parse_format, Locale.US);
        SimpleDateFormat usemonth = new SimpleDateFormat(month_format, Locale.US);
        String monthname = (String) android.text.format.DateFormat.format("MMMM - yyyy", new Date());
        Calendar calendar = Calendar.getInstance();
        to_date = form.format(calendar.getTime());
        Log.d(TAG, "select_month: " + to_date);
        Calendar to_cal = Calendar.getInstance();
        if (!type.equalsIgnoreCase("month")) {
            to_cal.add(Calendar.MONTH, -5);
            to_cal.set(Calendar.DATE, 1);
        } else {
//            to_cal.set(Calendar.DATE, 1);
            to_cal.add(Calendar.MONTH, -1);
//            to_cal.add(Calendar.DATE, -30);
        }


        from_date = form.format(to_cal.getTime());
        Log.d(TAG, "select_month: " + from_date);

        String usedmonth=usemonth.format(to_cal.getTime())+"-"+usemonth.format(calendar.getTime());
//        txt_month.setText(monthname);
        txt_month.setText(usedmonth);
        dataUsageType = type;
        get_data_usage();
    }

    public void Sort_data(List<DataUsageResponse> dataUsageResponses, String type) {
        data_usage.setVisibility(View.VISIBLE);
        ArrayList<Float> list = new ArrayList<>();
        if (list.size() > 0) {
            list.clear();
        }
        Collections.sort(dataUsageResponses, (res1, res2) -> res1.getStartDt().compareTo(res2.getStartDt()));
        String date1 = "";
        float y_axix_value = 0;
        String date;
        int pos;
        for (int i = 0; i < dataUsageResponses.size(); i++) {
            float value = Float.parseFloat(dataUsageResponses.get(i).getTotal());
            float entry_value = value / 1073741824;
            float gb = Constant.round(entry_value);
            if (type.equalsIgnoreCase("month")) {
                date = dataUsageResponses.get(i).getStartDt().substring(0, dataUsageResponses.get(i).getStartDt().lastIndexOf(" "));

                if (date1.equalsIgnoreCase(date)) {
                    pos = list.lastIndexOf(y_axix_value);
                    y_axix_value = gb + y_axix_value;
                    list.set(pos, y_axix_value);

                } else {
                    date1 = date;
                    y_axix_value = gb;
                    list.add(y_axix_value);


                }
            } else {
                date = parse_x_data(dataUsageResponses.get(i), type);
                if (date1.equalsIgnoreCase(date)) {
                    pos = list.lastIndexOf(y_axix_value);
                    y_axix_value = gb + y_axix_value;
                    list.set(pos, y_axix_value);
                } else {
                    date1 = date;
                    y_axix_value = gb;
                    list.add(y_axix_value);
                }
            }

        }

        setData(dataUsageResponses, list, type);
    }

    public String parse_x_data(DataUsageResponse dataUsageResponses, String type) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        Date parse_data = null;
        String myFormat;
        try {
            parse_data = sdf.parse(dataUsageResponses.getStartDt());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (type.equalsIgnoreCase("six_month")) {
            myFormat = "MM-yyyy";
        } else {
            myFormat = "dd";
        }

        SimpleDateFormat my_format = new SimpleDateFormat(myFormat, Locale.US);
        if (parse_data != null) {
            String date3 = my_format.format(parse_data.getTime());
            Log.d("dateGaurav", date3);
            return date3;
        }
        return null;
    }

    public void getTypeList() {
        if (mrtgTypeList != null && mrtgTypeList.size() > 0) {
            mrtgTypeList.clear();
        }
        img_mrtg.setVisibility(View.GONE);
        MrtgType hours = new MrtgType();
        hours.type_id = "1";
        hours.type_description = getString(R.string.txt_24_hours);
        mrtgTypeList.add(hours);
        MrtgType to_day = new MrtgType();
        to_day.type_id = "2";
        to_day.type_description = getString(R.string.txt_today);
        mrtgTypeList.add(to_day);
        MrtgType yesterday = new MrtgType();
        yesterday.type_id = "3";
        yesterday.type_description = getString(R.string.txt_yesterday);
        mrtgTypeList.add(yesterday);
        MrtgType last_7 = new MrtgType();
        last_7.type_id = "4";
        last_7.type_description = getString(R.string.last_seven_days);
        mrtgTypeList.add(last_7);
        MrtgType this_week = new MrtgType();
        this_week.type_id = "5";
        this_week.type_description = getString(R.string.this_week);
        mrtgTypeList.add(this_week);
        MrtgType last_week = new MrtgType();
        last_week.type_id = "6";
        last_week.type_description = getString(R.string.last_week);
        mrtgTypeList.add(last_week);
        MrtgType last_30 = new MrtgType();
        last_30.type_id = "7";
        last_30.type_description = getString(R.string.last_30_days);
        mrtgTypeList.add(last_30);
        MrtgType this_month = new MrtgType();
        this_month.type_id = "8";
        this_month.type_description = getString(R.string.this_month);
        mrtgTypeList.add(this_month);
        MrtgType last_month = new MrtgType();
        last_month.type_id = "9";
        last_month.type_description = getString(R.string.last_month);
        mrtgTypeList.add(last_month);
        MrtgType last_365 = new MrtgType();
        last_365.type_id = "10";
        last_365.type_description = getString(R.string.last_365_days);
        mrtgTypeList.add(last_365);
        MrtgType last_two = new MrtgType();
        last_two.type_id = "11";
        last_two.type_description = getString(R.string.last_2_years);
        mrtgTypeList.add(last_two);
        MrtgType this_year = new MrtgType();
        this_year.type_id = "12";
        this_year.type_description = getString(R.string.this_year);
        mrtgTypeList.add(this_year);
        if (mrtgTypeList != null && mrtgTypeList.size() > 0) {
            adapter = new SelectMrtgTypeAdapter(this, mrtgTypeList, (view, position) -> {
                int id = view.getId();
                if (id == R.id.layout_account) {
                    data_type = mrtgTypeList.get(position).type_id;
                    txt_type.setText(mrtgTypeList.get(position).type_description);
                    view_select_type.setVisibility(View.GONE);
                    get_mrtg_data(mrtgTypeList.get(position).type_id);
                }

            });
            view_select_type.setVisibility(View.VISIBLE);
            view_select_type.setAdapter(adapter);

        }
    }

    @OnClick({R.id.txt_type, R.id.img_back, R.id.layout_select_custom, R.id.layout_six_month, R.id.layout_select_one_month})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_type:
                getTypeList();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_select_custom:
                select_custom_tab();
                break;
            case R.id.layout_six_month:
                select_six_month_tab();
                break;
            case R.id.layout_select_one_month:
                select_one_month_tab();
                break;
        }
    }

    public void select_custom_tab() {
        layout_select_date.setVisibility(View.VISIBLE);
        txt_custom.setTextColor(getResources().getColor(R.color.back_color));
        view_custom.setVisibility(View.VISIBLE);
        txt_one_month.setTextColor(getResources().getColor(R.color.not_selected_color));
        view_one_month.setVisibility(View.GONE);
        view_six_month.setVisibility(View.GONE);
        txt_six_month.setTextColor(getResources().getColor(R.color.not_selected_color));
        txt_month.setVisibility(View.GONE);
    }

    public void select_six_month_tab() {
        select_month("six_month");
        txt_custom.setTextColor(getResources().getColor(R.color.not_selected_color));
        view_custom.setVisibility(View.GONE);
        txt_one_month.setTextColor(getResources().getColor(R.color.not_selected_color));
        view_one_month.setVisibility(View.GONE);
        view_six_month.setVisibility(View.VISIBLE);
        txt_six_month.setTextColor(getResources().getColor(R.color.back_color));
        txt_month.setVisibility(View.VISIBLE);
        txt_month.setText(getString(R.string.months));
    }

    public void select_one_month_tab() {
        select_month("month");
        txt_custom.setTextColor(getResources().getColor(R.color.not_selected_color));
        view_custom.setVisibility(View.GONE);
        txt_one_month.setTextColor(getResources().getColor(R.color.back_color));
        view_one_month.setVisibility(View.VISIBLE);
        view_six_month.setVisibility(View.GONE);
        txt_six_month.setTextColor(getResources().getColor(R.color.not_selected_color));
        txt_month.setVisibility(View.VISIBLE);
    }
}
