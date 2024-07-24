package com.spectra.consumer.Utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EncodingUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SiUtils {
    public static final String DISABLE_URL="https://epay.spectra.co/autopay/disable.php";
    public static final String URL = "https://epay.spectra.co/autopay/pay.php";
    public static final String KEY = "b77de68DAecd823Bad3Edb1c";
    public static final String RETURN_URL = "https://my.spectra.co/index.php/xml/sipayment";
    public static byte[] getPaymentParam(String payable_amount,String canID,String type) {
        String postData = "";
        try { postData = "secretKey="+KEY+"&canID=" + canID  + "&billAmount="
                        + payable_amount + "&returnUrl="+RETURN_URL+"&requetType=" + type ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EncodingUtils.getBytes(postData, "base64");
    }

    public static String getSrDate(String dateString, boolean isDate) {
        Date date = null;
        String str = "";
        if (TextUtils.isEmpty(dateString)) {
            return str;
        }
        String inputPattern = "dd/MM/yyyy hh:mm aa";
        String outputPattern = "hh:mm aa";
        if (isDate) {
            outputPattern = "dd-MM-yyyy";
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            date = inputFormat.parse(dateString);
            assert date != null;
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            str = "";
        }
        return str.toUpperCase();
    }


//    HelpingClass.sharedInstance.convert(time: ETRvalue, fromFormate: "dd/MM/yyyy hh:mm a", toFormate:"dd/MM/yyyy" ),let valueTime = HelpingClass.sharedInstance.convert(time: ETRvalue, fromFormate: "dd/MM/yyyy hh:mm a", toFormate:"hh:mm a" ){
//        let attributedDate = NSAttributedString(string: " \(value) by \(valueTime)", attributes: [NSAttributedString.Key.font: UIFont(name: "HelveticaNeue-Bold", size: 20)!])
//        attributedText.append(attributedDate)
//    }

    public static String getETRDate(String dateString) {
        Date date = null;
        String dateOutputString = "";
        String timeString = "";
        if (TextUtils.isEmpty(dateString)) {
            return timeString;
        }
        String inputPattern = "dd/MM/yyyy hh:mm aa";
        String outPatternDate = "dd/MM/yyyy";
        String outputPattern = "hh:mm aa";

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormatDate = new SimpleDateFormat(outPatternDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        try {
            date = inputFormat.parse(dateString);
            assert date != null;
            dateOutputString = outputFormatDate.format(date);
            timeString = outputFormat.format(date);

            dateOutputString = dateOutputString+" by "+timeString;
        } catch (ParseException e) {
            e.printStackTrace();
            timeString = "";
        }
        return dateOutputString.toUpperCase();
    }

    public static boolean isInternetViaWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return false;
            }
        } else {
            // not connected to the internet
            return false;
        }
        return false;
    }
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}
