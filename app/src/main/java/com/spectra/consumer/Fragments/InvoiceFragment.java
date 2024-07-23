package com.spectra.consumer.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.InvoiceDetailsActivity;
import com.spectra.consumer.Activities.PayNowActivity;
import com.spectra.consumer.Adapters.InvoiceAdapter;
import com.spectra.consumer.Adapters.InvoiceB2BAdapter;
import com.spectra.consumer.Adapters.TransactionAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.GetInvoiceContentRequest;
import com.spectra.consumer.service.model.Request.GetInvoiceListRequest;
import com.spectra.consumer.service.model.Request.GetTransactionListRequest;
import com.spectra.consumer.service.model.Response.GetInvoicelistResponse;
import com.spectra.consumer.service.model.Response.GetTransactionListResponse;
import com.spectra.consumer.service.model.Response.InvoiceListResponse;
import com.spectra.consumer.service.model.Response.TransactionListResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.lucasfsc.html2pdf.Html2Pdf;

import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.GET_INVOICE_CONTENT;
import static com.spectra.consumer.service.repository.ApiConstant.GET_INVOICE_LIST;
import static com.spectra.consumer.service.repository.ApiConstant.PAYMENT_TRANSACTIONDETAIL;

public class InvoiceFragment extends Fragment implements Html2Pdf.OnCompleteConversion {
    private static final String ARG_PARAM1 = "param1";
    private HomeActivity homeActivity;
    private View view;
    private Context context;
    @BindView(R.id.view_invoiceList)
    RecyclerView view_invoiceList;
    @BindView(R.id.select_from_date)
    RelativeLayout select_from_date;
    @BindView(R.id.select_to_date)
    RelativeLayout select_to_date;
    @BindView(R.id.txt_from_date)
    TextView txt_from_date;
    @BindView(R.id.view_invoice)
    View view_invoice;
    @BindView(R.id.txt_invoice)
    TextView txt_invoice;
    @BindView(R.id.txt_to_date)
    TextView txt_to_date;
    @BindView(R.id.view_ledger)
    View view_ledger;
    @BindView(R.id.txt_transaction)
    TextView txt_transaction;
    @BindView(R.id.txt_outstanding_amount)
    TextView txt_outstanding_amount;
    @BindView(R.id.due_date)
    TextView due_date;
    @BindView(R.id.layout_select_ledger)
    LinearLayout layout_select_ledger;
    @BindView(R.id.layout_mobile)
    CardView layout_mobile;
    @BindView(R.id.img_submit)
    ImageView img_submit;
    @BindView(R.id.img_reset)
    ImageView img_reset;
    @BindView(R.id.layout_submit_date)
    RelativeLayout layout_submit_date;
    private List<InvoiceListResponse> invoiceDataList = new ArrayList<>();
    private CurrentUserData userData;
    private String from_date, to_date;
    @BindView(R.id.view_transactions)
    RecyclerView view_transactions;
    @BindView(R.id.txt_pay)
    AppCompatTextView txt_pay;
    private boolean is_valid_invoice_date;
    private String type;
    @BindView(R.id.txt_due)
    TextView txt_due;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    private String[] PERMISSIONS;
    private int PERMISSION_ALL = 1;
    private File file;
    private SpectraViewModel spectraViewModel;
    private static String INVOICE_NO;
    private static String INVOICE_DISPLAY_NO;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) context;
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
        }
    }


    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_invoice, null);
            ButterKnife.bind(this, view);
            userData = DroidPrefs.get(context, Constant.CurrentuserKey, CurrentUserData.class);
            spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
            PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            LinearLayoutManager invoice_manager = new LinearLayoutManager(context);
            LinearLayoutManager ledger_manager = new LinearLayoutManager(context);
            view_invoiceList.setLayoutManager(invoice_manager);
            view_invoiceList.setNestedScrollingEnabled(false);
            view_transactions.setLayoutManager(ledger_manager);
            view_transactions.setNestedScrollingEnabled(false);
            if (userData.OutStandingAmount.equalsIgnoreCase("0")) {
                txt_outstanding_amount.setText(R.string.no_dues);
                txt_pay.setVisibility(View.GONE);
                due_date.setVisibility(View.GONE);
                txt_due.setVisibility(View.GONE);
            } else {
                txt_outstanding_amount.setText("₹ " + Constant.Round(Float.parseFloat(userData.OutStandingAmount), 2));
                txt_pay.setVisibility(View.VISIBLE);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date parse = null;
                try {
                    parse = sdf.parse(userData.DueDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String myFormat = "MMM dd, yyyy";
                SimpleDateFormat my = new SimpleDateFormat(myFormat, Locale.US);
                txt_due.setVisibility(View.VISIBLE);
                if (parse != null) {
                    due_date.setText(my.format(parse.getTime()));
                }
                if (userData.PreBarredFlag.equalsIgnoreCase("true")) {
                    due_date.setTextColor(getResources().getColor(R.color.back_color));
                } else {
                    due_date.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }

            select_invoice();
        }


        txt_pay.setOnClickListener(view -> {

            String amount = userData.OutStandingAmount;
            if (TextUtils.isEmpty(amount)) {
                amount = "0";
            }
            if (!TextUtils.isEmpty(amount)) {
                Intent intent = new Intent(homeActivity, PayNowActivity.class);
                intent.putExtra("payableAamount", amount);
                intent.putExtra("type", "unpaid");
                intent.putExtra("email", userData.Email);
                intent.putExtra("canID", userData.CANId);
                intent.putExtra("mobile", userData.Number);
                intent.putExtra("subType", "normal");
                startActivity(intent);
            } else {
                Constant.MakeToastMessage(context, "Payable amount can't be 0");
            }
        });
        layout_select_ledger.setOnClickListener(view -> {
            from_date = "";
            to_date = "";
            select_ledger();
        });

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
                            is_valid_invoice_date = true;
                        } else {
                            is_valid_invoice_date = false;
                            txt_from_date.setText(R.string.select_date);
                            Constant.MakeToastMessage(homeActivity, getString(R.string.can_select_max_one));
                        }
                    } else {
                        is_valid_invoice_date = false;
                        txt_from_date.setText(R.string.select_date);
                        Constant.MakeToastMessage(homeActivity, getString(R.string.no_blank_date));
                    }
                } else {
                    is_valid_invoice_date = false;
                }
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
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
                            is_valid_invoice_date = true;
                        } else {
                            is_valid_invoice_date = false;
                            txt_to_date.setText(R.string.select_date);
                            Constant.MakeToastMessage(homeActivity, getString(R.string.can_select_max_one));
                        }
                    } else {
                        is_valid_invoice_date = false;
                        txt_to_date.setText(R.string.select_date);
                        Constant.MakeToastMessage(homeActivity, getString(R.string.no_blank_date));
                    }

                } else {
                    is_valid_invoice_date = false;
                }
            };
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        return view;
    }


    private void getTransactionDetails() {
        if (Constant.isInternetConnected(context)) {
            view_transactions.setVisibility(View.GONE);
            GetTransactionListRequest getTransactionListRequest = new GetTransactionListRequest();
            getTransactionListRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getTransactionListRequest.setAction(PAYMENT_TRANSACTIONDETAIL);
            getTransactionListRequest.setCanID(userData.CANId);
            getTransactionListRequest.setFromDate(from_date);
            getTransactionListRequest.setToDate(to_date);
            spectraViewModel.getTransactionList(getTransactionListRequest).observe(this, this::consumeResponse);
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
                case GET_INVOICE_CONTENT:
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String status = jsonObject.getString("status");
                        if (status.contains(STATUS_SUCCESS)) {
                            String data = jsonObject.getString("response");
                            if (!TextUtils.isEmpty(data)) {
                                createWebPrintJob(data, INVOICE_DISPLAY_NO);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_INVOICE_LIST:
                    GetInvoicelistResponse getInvoicelistResponse = (GetInvoicelistResponse) response;
                    if (getInvoicelistResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        invoiceDataList = getInvoicelistResponse.getResponse();
                        if (invoiceDataList != null && invoiceDataList.size() > 0) {
                            if (userData.Segment.equalsIgnoreCase("Home")) {
                                setInvoiceAdapter();
                            } else {
                                setInvoiceAdapterB2B();
                            }
                        }
                    } else {
                        view_invoiceList.setVisibility(View.GONE);
                        Constant.MakeToastMessage(context, getInvoicelistResponse.getMessage());
                    }
                    break;
                case PAYMENT_TRANSACTIONDETAIL:
                    GetTransactionListResponse getTransactionListResponse = (GetTransactionListResponse) response;
                    if (getTransactionListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        List<TransactionListResponse> transactionDataList = getTransactionListResponse.getResponse();
                        if (transactionDataList != null && transactionDataList.size() > 0) {
                            Collections.reverse(transactionDataList);
                            view_invoiceList.setVisibility(View.GONE);
                            view_transactions.setVisibility(View.VISIBLE);
                            TransactionAdapter transactionAdapter = new TransactionAdapter(context, transactionDataList);
                            view_transactions.setAdapter(transactionAdapter);
                        }

                    } else {
                        Constant.MakeToastMessage(context, getTransactionListResponse.getMessage());
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

    private void getInvoicedetails(String type) {
        if (Constant.isInternetConnected(context)) {
            view_invoiceList.setVisibility(View.GONE);
            GetInvoiceListRequest getInvoiceListRequest = new GetInvoiceListRequest();
            getInvoiceListRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getInvoiceListRequest.setAction(GET_INVOICE_LIST);
            getInvoiceListRequest.setCanID(userData.CANId);
            if (type.equalsIgnoreCase("filter")) {
                getInvoiceListRequest.setEndDate(to_date);
                getInvoiceListRequest.setStartDate(from_date);
            } else {
                getInvoiceListRequest.setEndDate("");
                getInvoiceListRequest.setStartDate("");
            }
            spectraViewModel.getInvoiceList(getInvoiceListRequest).observe(this, this::consumeResponse);
        }

    }

    private void select_invoice() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        view_invoiceList.setLayoutManager(linearLayoutManager);
        type = "invoice";
        layout_mobile.setVisibility(View.VISIBLE);
        view_invoiceList.setVisibility(View.VISIBLE);
        txt_invoice.setTextColor(getResources().getColor(R.color.back_color));
        view_invoice.setBackgroundColor(getResources().getColor(R.color.back_color));
        txt_transaction.setTextColor(getResources().getColor(R.color.not_selected_color));
        view_ledger.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        view_transactions.setVisibility(View.GONE);
        getInvoicedetails("normal");
    }


    private void select_ledger() {
        view_invoiceList.setVisibility(View.GONE);
        type = "ledger";
        is_valid_invoice_date = false;
        txt_from_date.setText(getString(R.string.select_date));
        txt_to_date.setText(getString(R.string.select_date));
        String parse_format = getString(R.string.date_format_yyyy_MM_dd);
        SimpleDateFormat form = new SimpleDateFormat(parse_format, Locale.US);
        Calendar calendar = Calendar.getInstance();
        to_date = form.format(calendar.getTime());
        Calendar to_cal = Calendar.getInstance();
        to_cal.add(Calendar.MONTH, -6);
        from_date = form.format(to_cal.getTime());
        img_submit.setVisibility(View.VISIBLE);
        img_reset.setVisibility(View.GONE);
        getTransactionDetails();
        layout_mobile.setVisibility(View.GONE);
        txt_transaction.setTextColor(getResources().getColor(R.color.back_color));
        view_ledger.setBackgroundColor(getResources().getColor(R.color.back_color));
        txt_invoice.setTextColor(getResources().getColor(R.color.not_selected_color));
        view_invoice.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(homeActivity, permission)) {
                //denied
                Log.e("denied", permission);

                if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE") || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                }

            } else {
                if (ActivityCompat.checkSelfPermission(homeActivity, permission) == PackageManager.PERMISSION_GRANTED) {
                    //allowed
                    Log.e("allowed", permission);

                } else {
                    //set to never ask again
                    Log.e("set to never ask again", permission);

                    if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE") || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    }
                }

            }
        }

    }

    //getting invoice content
    private void share_data(String invoice_no, String display_no) {
        INVOICE_NO = invoice_no;
        INVOICE_DISPLAY_NO = display_no;
        GetInvoiceContentRequest getInvoiceListRequest = new GetInvoiceContentRequest();
        getInvoiceListRequest.setAuthkey(BuildConfig.AUTH_KEY);
        getInvoiceListRequest.setAction(GET_INVOICE_CONTENT);
        getInvoiceListRequest.setInvoiceNo(invoice_no);
        spectraViewModel.getInvoiceContent(getInvoiceListRequest).observe(this, this::consumeResponse);
    }


    private void createWebPrintJob(String invoiceData, String display_no) {
        try {
            File myDir = new File(Objects.requireNonNull(getActivity()).getCacheDir(), "folder");
            final boolean mkdir = myDir.mkdir();
            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/SPECTRA/";
            File root = new File(rootPath);
            if (!root.exists()) {
                boolean mkdirs = root.mkdirs();
            }
            file = new File(rootPath + "INV_" + display_no + ".pdf");
            if (file.exists()) {
                final boolean delete = file.delete();
            }
            final boolean newFile = file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Html2Pdf converter = new Html2Pdf.Companion.Builder()
                    .context(Objects.requireNonNull(getActivity()))
                    .html(invoiceData)
                    .file(file)
                    .build();
            converter.convertToPdf(this);
            converter.convertToPdf();
        } catch (Exception e) {
            Log.i("converter", e.getMessage());
            e.printStackTrace();
        }
    }

    //sharing pdf via email
    private void share_file(File file, String display_no) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (file != null && file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_EMAIL, "");
            share.putExtra(Intent.EXTRA_SUBJECT, "Spectra Invoice - " + display_no);
            share.putExtra(Intent.EXTRA_TEXT, "");
            share.setType("application/pdf");
            share.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(share);
        }
    }


    //setting invoice list for B2C user

    private void setInvoiceAdapter() {
        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(context, invoiceDataList, (view, position) -> {
            int id = view.getId();
            if (!Constant.isInternetConnected(context)) {
                Constant.MakeToastMessage(context, context.getString(R.string.no_internet));
                return;
            }

            if (id == R.id.view_invoice) {
                if (!Constant.hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(homeActivity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent intent = new Intent(homeActivity, InvoiceDetailsActivity.class);
                    intent.putExtra("invoice_no", invoiceDataList.get(position).getInvoiceNo());
                    intent.putExtra("display_no", invoiceDataList.get(position).getDisplayInvNo());
                    startActivity(intent);
                }


            } else if (id == R.id.email_invoice) {
                if (!Constant.hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(homeActivity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    share_data(invoiceDataList.get(position).getInvoiceNo(), invoiceDataList.get(position).getDisplayInvNo());
                }
            }
        });

        view_invoiceList.setVisibility(View.VISIBLE);
        view_invoiceList.setAdapter(invoiceAdapter);
    }


    //setting invoice list for B2B user
    private void setInvoiceAdapterB2B() {
        InvoiceB2BAdapter invoiceB2BAdapter = new InvoiceB2BAdapter(context, invoiceDataList, (view, position) -> {
            int id = view.getId();
            if (id == R.id.view_invoice) {
                if (!Constant.hasPermissions(context, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(homeActivity, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent intent = new Intent(homeActivity, InvoiceDetailsActivity.class);
                    intent.putExtra("invoice_no", invoiceDataList.get(position).getInvoiceNo());
                    intent.putExtra("display_no", invoiceDataList.get(position).getDisplayInvNo());
                    startActivity(intent);
                }

            } else if (id == R.id.pay_now_invoice) {
                String amount = invoiceDataList.get(position).getUnPaidBalance();
                if (TextUtils.isEmpty(amount)) {
                    amount = "0";
                }
                if (!TextUtils.isEmpty(amount)) {
                    Intent intent = new Intent(homeActivity, PayNowActivity.class);
                    intent.putExtra("email", userData.Email);
                    intent.putExtra("mobile", userData.Number);
                    intent.putExtra("payableAamount", amount);
                    intent.putExtra("type", "tds");
                    intent.putExtra("canID", userData.CANId);
                    intent.putExtra("tdsAmount", String.valueOf(invoiceDataList.get(position).getTdsAmount()));
                    intent.putExtra("tdsSlab", invoiceDataList.get(position).getTdsSlab());
                    intent.putExtra("subType", "normal");
                    startActivity(intent);

                } else {
                    Constant.MakeToastMessage(context, "Payable amount can't be 0");
                }


            }
        });
        view_invoiceList.setVisibility(View.VISIBLE);
        view_invoiceList.setAdapter(invoiceB2BAdapter);
    }

    @OnClick({R.id.layout_select_invoice, R.id.img_reset, R.id.img_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_select_invoice:
                select_invoice();
                break;
            case R.id.img_reset:
                is_valid_invoice_date = false;
                txt_from_date.setText(getString(R.string.select_date));
                txt_to_date.setText(getString(R.string.select_date));
                if (type.equalsIgnoreCase("invoice")) {
                    from_date = "";
                    to_date = "";
                    getInvoicedetails("normal");
                } else {
                    select_ledger();
                }
                img_submit.setVisibility(View.VISIBLE);
                img_reset.setVisibility(View.GONE);
                break;
            case R.id.img_submit:
                if (is_valid_invoice_date) {
                    if (type.equalsIgnoreCase("invoice")) {
                        getInvoicedetails("filter");
                    } else {
                        getTransactionDetails();
                    }
                    img_submit.setVisibility(View.GONE);
                    img_reset.setVisibility(View.VISIBLE);

                } else {
                    Constant.MakeToastMessage(homeActivity, getString(R.string.please_select_dates));
                }
                break;

        }
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onSuccess() {
        share_file(file, INVOICE_DISPLAY_NO);
    }
}