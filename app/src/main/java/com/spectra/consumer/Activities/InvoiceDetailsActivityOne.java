package com.spectra.consumer.Activities;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.GET_INVOICE_DETAIL;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import com.github.barteksc.pdfviewer.PDFView;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.InvoiceDetailRequest;
import com.spectra.consumer.service.model.Response.InvoiceDetailResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.lucasfsc.html2pdf.Html2Pdf;

public class InvoiceDetailsActivityOne extends AppCompatActivity implements Html2Pdf.OnCompleteConversion {

    private final int PERMISSION_ALL = 1;
    private final int PDFREQUESTCODE = 102;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    PDFView pdfView;
    File file;
    String displayno, invoiceno;
    int increment;
    InvoiceDetailResponse detailResponse;
    Context context;
    private String canIdAnalytics;
    private String[] PERMISSIONS;
    private String documentType;
    private String pdfPath = null;
    String fileName;
    private File createFile;
    final private int OPEN_FILE_REQUEST_CODE = 108;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_invoice_details);
        ButterKnife.bind(this);
        pdfView = findViewById(R.id.pdfview);
        context = InvoiceDetailsActivityOne.this;
        CAN_ID canIdNik = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        canIdAnalytics = canIdNik.baseCanID;
        PERMISSIONS = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        findViewById(R.id.img_back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.txt_share).setVisibility(View.GONE);
        findViewById(R.id.txt_share).setOnClickListener(v -> {
                sharePdf();
        });
        Intent intent = getIntent();
        invoiceno = intent.getStringExtra("invoice_no");
        displayno = intent.getStringExtra("display_no");
        progress_bar.setVisibility(View.VISIBLE);
        fileName = "INV_" + displayno + ".pdf";
        requestRequiredPermission();
        getBill();
    }

    public void requestRequiredPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            requestPermissions(PERMISSIONS, PDFREQUESTCODE);
        } else {
            createPDFRequiredFile();
        }
    }

    public void createPDFRequiredFile() {
        String fileName = "INV_" + displayno + ".pdf";
        file = new File(getPathForClaim() + fileName);
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // file exsists do nothing
        }
    }

    public void getBill() {
        InvoiceDetailRequest request = new InvoiceDetailRequest();
        request.setAuthkey(BuildConfig.AUTH_KEY);
        request.setAction(GET_INVOICE_DETAIL);
        request.setInvoiceNo(invoiceno);
        SpectraViewModel spectraViewModel = ViewModelProviders.
                of(this).get(SpectraViewModel.class);
        spectraViewModel.getInvoiceDetail(request).
                observe(InvoiceDetailsActivityOne.this,
                        InvoiceDetailsActivityOne.this::consumeResponse);
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                break;
            case ERROR:
                progress_bar.setVisibility(View.GONE);
                assert apiResponse.error != null;
                Constant.MakeToastMessage(InvoiceDetailsActivityOne.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            detailResponse = (InvoiceDetailResponse) response;
            if (detailResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                doPrint(detailResponse.getResponse());
            } else {
                Constant.MakeToastMessage(InvoiceDetailsActivityOne.this, detailResponse.getMessage());
            }
        }
    }

    public static String getPathForClaim() {
        String MAX_DIRECTORY_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Spectra";
        return MAX_DIRECTORY_PATH + File.separator;
    }

//    private void beginDownload() {
//        doPrint(detailResponse.getResponse());
//    }

    private void doPrint(String response) {
        try {
            Html2Pdf converter = new Html2Pdf.Companion.Builder()
                    .context(this)
                    .html(response)
                    .file(new File(file.getAbsolutePath()))
                    .build();
            try {
                converter.convertToPdf(this);
            } catch (Exception e) {
                Log.i("converter", Objects.requireNonNull(e.getMessage()));
            }
        } catch (Exception e) {
            Log.i("converter", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public void onFailed() {
        Log.i("converter", "onFailed");
    }

    @Override
    public void onSuccess() {
        findViewById(R.id.txt_share).setVisibility(View.VISIBLE);
        setPdfView();
    }

    public void setPdfView() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        ///this code is use for View pdf
        if (file != null) {
            pdfView.fromFile(file).defaultPage(0).enableSwipe(true).onLoad(nbPages -> {
                Log.d("TAG", "loadComplete: ");
                progress_bar.setVisibility(View.GONE);
            }).load();
        }
    }

    private void sharePdf() {
        Constant.shareFile(context, file, displayno);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PDFREQUESTCODE: {
                for (String permission : permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        //denied
                        Log.e("denied", permission);
                        if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE")
                                || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                            Log.d("------", "onRequestPermissionsResult: IF");
                        }
                    } else {
                        if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                            //allowed
                            Log.e("allowed", permission);
                            createPDFRequiredFile();
                        } else {
                            //set to never ask again
                            Log.e("set to never ask again", permission);
                            if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE")
                                    || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                            }
                        }
                    }
                }
                break;
            }
            case PERMISSION_ALL: {
                for (String permission : permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        //denied
                        Log.e("denied", permission);
                        if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE")
                                || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                            Log.d("------", "onRequestPermissionsResult: IF");
                        }
                    } else {
                        if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                            //allowed
                            Log.e("allowed", permission);
                            sharePdf();
                        } else {
                            //set to never ask again
                            Log.e("set to never ask again", permission);
                            if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE")
                                    || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                            }
                        }
                    }
                }
                break;
            }
        }
    }
}