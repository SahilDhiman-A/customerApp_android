package com.spectra.consumer.alertDailogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.spectra.consumer.R

class ThanksFeedbackDailog constructor(context: Context) : Dialog(context) {

    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dailog_thaks_feedback)

        val cancel = findViewById(R.id.cancel) as ImageView
        cancel.setOnClickListener {
            dismiss()
        }
    }


}