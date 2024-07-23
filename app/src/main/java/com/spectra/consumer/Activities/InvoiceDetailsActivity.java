package com.spectra.consumer.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.InvoiceDetailRequest;
import com.spectra.consumer.service.model.Response.InvoiceDetailResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.lucasfsc.html2pdf.Html2Pdf;

import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.GET_INVOICE_DETAIL;


public class InvoiceDetailsActivity extends AppCompatActivity implements Html2Pdf.OnCompleteConversion {

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    PDFView pdfView;
    File file;
    String displayno, invoiceno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_invoice_details);
        ButterKnife.bind(this);
        pdfView = findViewById(R.id.pdfview);
        findViewById(R.id.img_back).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.txt_share).setVisibility(View.GONE);
        findViewById(R.id.txt_share).setOnClickListener(v -> {
            if (file != null) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Uri uri = Uri.fromFile(file);
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_SUBJECT, "Spectra Invoice - " + displayno);
                startActivity(Intent.createChooser(share, ""));
            }
        });

        Intent intent = getIntent();
        invoiceno = intent.getStringExtra("invoice_no");
        displayno = intent.getStringExtra("display_no");
        Log.d("invoice_no", invoiceno);
        Log.d("display_no", displayno);
        progress_bar.setVisibility(View.VISIBLE);
        getBill(this);
        //askPermission();
    }


    private void doPrint(String response) {
        try {
            File myDir = new File(getCacheDir(), "folder");
            if(!myDir.mkdir()){
                return;
            }
            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/SPECTRA/";
            File root = new File(rootPath);
            if (!root.exists()) {
                if (!root.mkdir()) {
                    return;
                }
            }
            file = new File(rootPath + "INV_" + displayno + ".pdf");
            if (file.exists()) {
                if (!file.delete()) {
                    return;
                }
            }
            if (!file.createNewFile()) {
                return;
            }

            FileOutputStream out = new FileOutputStream(file);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Html2Pdf converter = new Html2Pdf.Companion.Builder()
                    .context(Objects.requireNonNull(this))
                    .html(response)
                    .file(file)
                    .build();
            converter.convertToPdf(this);
            converter.convertToPdf();
        } catch (Exception e) {
            Log.i("converter", e.getMessage());
            e.printStackTrace();
        }
    }


    public void setPdfView() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pdfView.fromFile(file)
                .defaultPage(0)
                .enableSwipe(true)
                .showMinimap(false).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                progress_bar.setVisibility(View.GONE);
            }
        }).swipeVertical(true)
                .load();
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


    public void getBill(Context context) {
        InvoiceDetailRequest request = new InvoiceDetailRequest();
        request.setAuthkey(BuildConfig.AUTH_KEY);
        request.setAction(GET_INVOICE_DETAIL);
        request.setInvoiceNo(invoiceno);
        SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        spectraViewModel.getInvoiceDetail(request).observe(InvoiceDetailsActivity.this, InvoiceDetailsActivity.this::consumeResponse);
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
                Constant.MakeToastMessage(InvoiceDetailsActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {
            InvoiceDetailResponse detailResponse;
            detailResponse = (InvoiceDetailResponse) response;
            if (detailResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                doPrint(detailResponse.getResponse());
            } else {
                Constant.MakeToastMessage(InvoiceDetailsActivity.this, detailResponse.getMessage());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
