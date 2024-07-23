package com.spectra.consumer.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import com.spectra.consumer.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.lucasfsc.html2pdf.Html2Pdf;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Constant {
public static String CurrentuserKey="userdata";
public static final String SEGMENT_HOME="Home";

    public static final String MOBILE="mobile";
    public static final String GSTN="gstn";
    public static final String TAN="tan";
    public static final String USER_NAME="userName";
    public static final String EMAIL="email";


    public static final String BASE_CAN="baseCan";
    public static final String USER_DB="userDB";
    public static final String USER_IMAGE="userImage";
public static final String SEGMENT_BUSINESS="Business";
public static final String PROFILE_VERIFY_TYPE_MOBILE="profile_mobile";
    public static final String PROFILE_VERIFY_TYPE_EMAIL="profile_email";
    public static final String LINKED_ACCOUNT="linked_account";
    public static final String LOGIN_VERIFY_TYPE="login";
    public static final String UPGRADE_PLAN="upgradePlan";
    public static final String CHANGE_PLAN_REQUEST="changeplanrequest";
    public static final String STATUS_SUCCESS="success";
    public static final String STATUS_FAIl="failure";
    public static boolean isInternetConnected(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm!=null) {
            // test for connection
            if (cm.getActiveNetworkInfo() != null
                    && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()) {
                Log.v("", "Internet is working");
                // txt_status.setText("Internet is working");
                return true;
            } else {
                // txt_status.setText("Internet Connection Not Present");
                Log.v("", "Internet Connection Not Present");
                return false;
            }

        }
        else {
            return false;
        }

    }
    public static Bitmap convert(String res) {
        Bitmap bitmap_img=null;
        try {
            byte[] encodeByte = Base64.decode(res, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            bitmap_img = Bitmap.createScaledBitmap(bitmap, 2000, 1400, false);
        }catch (Exception e)
        {e.printStackTrace();}
        return bitmap_img;
    }
    public static String getimage(Bitmap bitmap) {
        String base64="";
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }catch (Exception e){
                    e.printStackTrace();
                }
        return base64;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void ShowPassword(final EditText editText, final TextView textView){
        textView.setOnClickListener(view -> {
            if(editText.getInputType()== InputType.TYPE_TEXT_VARIATION_PASSWORD){
                editText.setInputType(129);
                textView.setText(R.string.txt_show);
            }
            else{
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                textView.setText(R.string.txt_hide);
            }
        });
    }
    public static void greeting_msg(final  TextView txt_greeting){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if(timeOfDay < 4){
            txt_greeting.setText(R.string.gd_eve);
        }
        else if(timeOfDay < 12){
            txt_greeting.setText(R.string.gd_mrng);
        }else if(timeOfDay < 18){
            txt_greeting.setText(R.string.gd_afternoon);
        }else if(timeOfDay < 21){
            txt_greeting.setText(R.string.gd_eve);
        }
        else {
            txt_greeting.setText(R.string.gd_eve);
        }
    }
    public static float round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    public static BigDecimal Round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static void MakeToastMessage(Context context,String Message){
        Toast toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.error_background);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(context.getResources().getColor(R.color.white));
        text.setGravity(Gravity.CENTER);

        toast.show();
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
   public static boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean isPositive(int num){
        return 0<num;
    }

    public boolean deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                    return file.delete();
            }else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
