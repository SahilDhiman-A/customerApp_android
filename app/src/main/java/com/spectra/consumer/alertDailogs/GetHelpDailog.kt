package com.spectra.consumer.alertDailogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.spectra.consumer.R
import com.spectra.consumer.Utils.Constant
import kotlinx.android.synthetic.main.dailog_help_us_improve.*


class GetHelpDailog constructor(context: Context) : Dialog(context) {


    init {
        setCancelable(true)
    }

    lateinit var feedBackSubmitListener: FeedBackSubmitListener

    interface FeedBackSubmitListener {
        fun onFeedbackSubmit(feedback: String);
    }

    fun setSubmitListener(mFeedBackSubmitListener: FeedBackSubmitListener) {
        this.feedBackSubmitListener = mFeedBackSubmitListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dailog_help_us_improve)
        submit.setOnClickListener {
            if (!et_quary.text.isNullOrEmpty()) {
                dismiss()
                feedBackSubmitListener.onFeedbackSubmit(et_quary.text.toString())
            } else {
                Constant.MakeToastMessage(context, context.resources.getString(R.string.please_enter_the_remarks));
            }
        }
    }

}