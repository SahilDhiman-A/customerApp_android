package com.spectra.consumer.Utils;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.spectra.consumer.Activities.NotPDFActivity;

public class BindinAadapter {

    @BindingAdapter("imageUrl")
    public static void setImageurlinImageview(ImageView imageview, String url) {
        Glide.with(imageview.getContext()).load(url).into(imageview);
    }

    @BindingAdapter("onClickLink")
    public static void setOnClickLink(View view, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        view.getContext().startActivity(intent);
    }

    @BindingAdapter("loadUrl")
    public static void setUrlInWebView(WebView mWebView, String url) {
        if(!url.isEmpty()){
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);
        }else{
            mWebView.setVisibility(View.GONE);
        }

    }
}
