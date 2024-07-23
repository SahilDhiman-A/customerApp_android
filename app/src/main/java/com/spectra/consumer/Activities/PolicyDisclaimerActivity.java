package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.spectra.consumer.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PolicyDisclaimerActivity extends AppCompatActivity  {
    @BindView(R.id.wv)
    WebView wv;
    Intent intent;
    String type;
    String URL;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy_disclaimer);
        ButterKnife.bind(this);
        intent=getIntent();
        type=intent.getStringExtra("type");
        if (type != null) {
            if(type.equalsIgnoreCase("policy")){
                URL="https://www.spectra.co/privacy-policy";
            }else {
                if(type.equalsIgnoreCase("speed")){
                    URL="http://fiber.spectra.co/";
                }else{
                    URL="https://www.spectra.co/legal-disclaimer";
                }
            }

        }

        wv.setWebViewClient(new WebViewClient() {

                           public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                return false;
                            }

                           @Override
                           public void onPageFinished(WebView view, String url) {
                             progress_bar.setVisibility(View.GONE);

                            }
                        });
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(URL);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @OnClick({R.id.img_back})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
        }
}
