package com.spectra.consumer.Activities

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.spectra.consumer.R
import kotlinx.android.synthetic.main.activity_know_your_bill.*

class KnowYourBillActivity : AppCompatActivity() {

    lateinit var URL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_know_your_bill)
        ButterKnife.bind(this)

        URL = "https://www.spectra.co/know-your-bill"

        webView_know_your_bill.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                progress_bar?.visibility = View.GONE
            }
        }
        webView_know_your_bill.settings.javaScriptEnabled = true
        webView_know_your_bill.loadUrl(URL)

        img_back.setOnClickListener {
            if (it.id == R.id.img_back) {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}