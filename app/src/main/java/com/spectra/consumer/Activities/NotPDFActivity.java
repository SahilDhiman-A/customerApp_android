package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spectra.consumer.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class NotPDFActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    WebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pdfview);
        ButterKnife.bind(this);
        webView = findViewById(R.id.webView);

        findViewById(R.id.img_back).setOnClickListener(v -> onBackPressed());
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String type = intent.getStringExtra("TYPE");
        progress_bar.setVisibility(View.VISIBLE);
        if (type.equals("PDF")) {
            showPdfFile(url);
        } else {
            showVideo(url);
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        webView.onPause();
    }

    private void showPdfFile(final String imageString) {
        final boolean[] isLoading = {false};
        String removePdfTopIcon = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";
        webView.invalidate();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + imageString);
        webView.setWebViewClient(new WebViewClient() {
            boolean checkOnPageStartedCalled = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkOnPageStartedCalled = true;
                progress_bar.setVisibility(View.VISIBLE);
                isLoading[0] = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                isLoading[0] = false;
                if (checkOnPageStartedCalled) {
                    webView.loadUrl(removePdfTopIcon);
                } else {
                    showPdfFile(imageString);
                }

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isLoading[0]) {
                            progress_bar.setVisibility(View.GONE);
                        }
                    }
                }, 3000);

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.onPause();
    }

    private void showVideo(String response) {
        try {

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setVisibility(View.VISIBLE);
            if (response.contains("youtube")) {
                webView.getSettings().setLightTouchEnabled(true);
                webView.getSettings().setGeolocationEnabled(true);
                webView.setSoundEffectsEnabled(true);
                webView.loadUrl(response);
                webView.setWebChromeClient(new WebChromeClient() {
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress < 100) {
                            progress_bar.setVisibility(View.VISIBLE);
                        }
                        if (progress == 100) {
                            progress_bar.setVisibility(View.GONE);
                            finish();
                        }
                    }
                });

            } else {
                webView.setWebViewClient(new WebViewClient1());
                webView.loadUrl(response);
            }
        } catch (Exception e) {
            Log.i("converter", e.getMessage());
            e.printStackTrace();
        }
    }


    private class WebViewClient1 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress_bar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress_bar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
            Toast.makeText(NotPDFActivity.this, "Your Internet Connection May not be active Or " + description, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webView.destroy();
        finish();
    }

}
