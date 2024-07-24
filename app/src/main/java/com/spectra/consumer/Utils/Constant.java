package com.spectra.consumer.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;

import com.spectra.consumer.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public static String CurrentuserKey = "userdata";
    public static final String SEGMENT_HOME = "Home";
    public static final String MOBILE = "mobile";
    public static final String GSTN = "gstn";
    public static final String TAN = "tan";
    public static final String USER_NAME = "userName";
    public static final String EMAIL = "email";
    public static String allCan;
    public static final String BASE_CAN = "baseCan";
    public static final String USER_DB = "userDB";
    public static final String USER_IMAGE = "userImage";
    public static final String SEGMENT_BUSINESS = "Business";
    public static final String PROFILE_VERIFY_TYPE_MOBILE = "profile_mobile";
    public static final String PROFILE_VERIFY_TYPE_EMAIL = "profile_email";
    public static final String LINKED_ACCOUNT = "linked_account";
    public static final String LOGIN_VERIFY_TYPE = "login";
    public static final String UPGRADE_PLAN = "upgrade Plan";
    public static final String UPGRADE = "upgrade";
    public static final String CHANGE_PLAN_REQUEST = "changeplanrequest";
    public static final String STATUS_SUCCESS = "success";
    public static final int STATUS_CODE = 200;
    public static final String SOURCE = "android";
    public static final String STATUS_FAIl = "failure";
    public static final String VIEW_SR_STATUS = "View SR Status";
    public static final String VIEW_INVOICE_LIST = "View Invoice List";
    public static final String HOME = "Home";
    public static final String MY_SR = "My SR";
    public static final String MY_ACCOUNT = "My Account";
    public static final String VIEW_USAGE = "View Usage";
    public static final String MY_PLAN = "My Plan";
    public static final String SR = "SR";
    public static final String RAISE_NEW_SR = "Raise new SR";
    public static final String NEW_SR = "new SR";
    public static final String COMPLAINT = "Complaint";
    public static final String SERVICE_REQUEST = "Service request";
    public static final String VIEW_TRANSECTION = "View transaction";
    public static final String VIEW_TOP_UP_OFFERS = "View Top-Up offers";
    public static final String TOP_UP_AVAILED = "Top up availed";
    public static final String CHECK_AUTO_PAY_STATUS = "Check Auto Pay Status";
    public static final String ADD_AUTO_PAY = "Add Autopay";
    public static final String VIEW_CONTACT = "View Contact";
    public static final String VIEW_MRTG = "View MRTG";
    public static final String VIEW_PLAN_CHANGE_OFFER = "View Plan Change Offer";
    public static final String FORGOT_PASSWORD = "Forgot Password";
    public static final String CREATE_SR = "Create SR";
    public static final String TRACK_MY_ORDER = "Track My Order";
    public static final String CHANGE_PASSWORD = "Change Password";
    public static final String UPDATE_PASSWORD = "Update Password";
    public static final String UPDATE_MOBILE_NUMBER = "Update Mobile Number";
    public static final String ADD_CONTACT = "Add Contact";
    public static final String UPDATE_EMAIL_ID = "Update Email id";
    public static final String LINK_MULTIPLE_ACCOUNT = "Link multiple accounts";
    public static final String LOGIN_VIA_USER_NAME_PASSWORD = "Login via User name & Password";
    public static final String KEY = "key";
    public static final String KEYWORD = "keyword";
    public static final String ENDPAGE = "endpage";
    public static final String UPDATE_ACCOUNT = "Update_Account";
    public static final int UICODE = 16;
    public static final String FAQ = "FAQ";
    public static final String ISSUE = "Issue";
    public static final String RAISE_CONCERN = "Raise Concern";
    public static final String ACCOUNT = "Account";
    public static final String BILL = "Bill";
    public static final String INVOICE = "Invoice";
    public static final String PAYMENT = "Payment";
    public static final String PAYMENT_RECEIPT = "Payment receipt";
    public static final String DATA = "data";
    public static final String DATA_CONSUMED = "data consumed";
    public static final String DATA_CHECK = "Data check";
    public static final String DATA_VOLUME = "Data Volume";
    public static final String MOBILE_NO = "Mobile no";
    public static final String MY_ADDRESS = "My Address";
    public static final String RESET_PASSWORD = "Reset password";
    public static final String MANAGE_CONTACT = "Manage Contact";
    public static final String ANOTHER_ACCOUNT = "Another account";
    public static final String REGISTERED_MOBILE_NO = "Registered Mobile no.";
    public static final String REGISTERED_EMAIL_ID = "Registered Email id.";
    public static final String CHANGE_EMAIL_ID = "Change Email id.";
    public static final String NEW_EMAIL_ID = "New Email id";
    public static final String CUSTOMER_CARE = "Costomer care";
    public static final String REGISTERED_NO = "Registered no.";
    public static final String RMN = "RMN";
    public static final String CONTACT_DETAILS = "Contact details";
    public static final String UPDATE_NEW_NUMBER = "Update new Number";
    public static final String UPDATE_PHONE_NUMBER = "Update phone Number";
    public static final String UPDATE_CONTACT_DETAILS = "Update Contact details";
    public static final String CONTACT = "Contact";
    public static final String CONTACT_NO = "Contact no";
    public static final String ADDRESS = "address";
    public static final String PASSWORD = "password";
    public static final String OTP_PASSWORD = "otp password";
    public static final String PLAN_DETAILS = "plan details";
    public static final String SPEED = "Speed";
    public static final String NEW_PLAN = "new plan";
    public static final String CUSTOMER_NAME = "Customer name";
    public static final String CAN_ID = "Can Id";
    public static final String PHONE_NO = "Phone No";
    public static final String EMAIL_ID = "Email id";
    public static final String CHANGE = "Change";
    public static final String TOP_UP = "top up";
    public static final String NEW_TOP_UP = "new top up";
    public static final String DATA_REQUIRED = "data required";
    public static final String TOP_UP_STATUS = "top up status";
    public static final String ADD_ANOTHER_ACCOUNT = "add another account";
    public static final String ADD_ANOTHER_CAN_ID = "add another can id";
    public static final String ATTACHED_ANOTHER_ACCOUNT = "attached another account";
    public static final String NEW_ACCOUNT = "new account";
    public static final String ADD_CAN_ID = "add can id";
    public static final String PAY = "Pay";
    public static final String AUTO_PAY = "auto pay";
    public static final String SET_UP = "set up";
    public static final String AUTO_DEBIT = "Auto debit";
    public static final String MY_PROFILE = "My Profile";
    public static final String USAGES = "Usage";
    public static final String VIEW_DATA = "View data";
    public static final String OPTION = "option";
    public static final String BILL_CYCLE = "bill cycle";
    public static final String CHECK_MY_BILL = "check my bill";
    public static final String CHANGE_PLAN = "change plan";
    public static final String NEW_OFFER = "New offer";
    public static final String TARIFF = "tariff";
    public static final String SELF_CARE_PORTAL = "self care portal";
    public static final String SELF_CARE = "self care";
    public static final String SHIFT_DIFFERENT_ROOM = "shift different room";
    public static final String SHIFT_WITHIN_THE_SOCIETY = "shift within the society";
    public static final String TRANSFER_OWNERSHIP = "transfer ownership";
    public static final String TRANSFER = "transfer";
    public static final String FUP = "FUP";
    public static final String FTTH = "FTTH";
    public static final String SYMMETRIC_SPEED = "symmetric speed";
    public static final String ASYMMETRIC_BANDWIDTH = "asymmetric bandwidth";
    public static final String IP_ADDRESS = "IP address";
    public static final String STATIC = "static";
    public static final String DYNAMIC = "dynamic";
    public static final String SERVICE_AVAILABILITY = "service availability";
    public static final String GB_DATA = "GB data";
    public static final String IDENTIFY_PROOF = "identify proof";
    public static final String WI_FI = "Wi-Fi";
    public static final String ROUTER = "router";
    public static final String SPECTRA_VOICE = "spectra voice";
    public static final String SWITCH_ACCOUNT = "Switch Account";
    public static final String MENU_MY_ACCOUNT = "Menu- My Account";
    public static final String DATA_USAGE = "Data usage";
    public static final String UTILIZATION = "Utilisation";
    public static final String BANDWIDTH_USAGE = "Bandwidth usage";
    public static final String LOGIN_VIA_MOBILE_NUMBER = "Login via Mobile Number";
    public static final String NEW_SCHEME = "New scheme";
    public static final String ACCOUNT_STATUS = "Account Status";
    public static final String LOGIN = "login";
    public static final String CREDENTIAL = "credential";
    public static final String USERNAME = "username";
    public static final String SEARCHSOURCE = "username";


    public static boolean isInternetConnected(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
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

        } else {
            return false;
        }

    }

    public static Bitmap convert(String res) {
        Bitmap bitmap_img = null;
        try {
            byte[] encodeByte = Base64.decode(res, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            bitmap_img = Bitmap.createScaledBitmap(bitmap, 2000, 1400, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap_img;
    }

    public static String getimage(Bitmap bitmap) {
        String base64 = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            base64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
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

    public static void shareFile(Context context, File file, String display_no) {
        Uri uri;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (file != null && file.exists()) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                uri = FileProvider.getUriForFile(context, context.getPackageName()+".provider",file);
            } else {
                uri = Uri.fromFile(file);
            }
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_EMAIL, "");
            share.putExtra(Intent.EXTRA_SUBJECT, "Spectra Invoice - " + display_no);
            share.putExtra(Intent.EXTRA_TEXT, "");
            share.setType("application/pdf");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(share, "Share File"));
        }
    }

    public static void ShowPassword(final EditText editText, final TextView textView) {
        textView.setOnClickListener(view -> {
            if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                editText.setInputType(129);
                textView.setText(R.string.txt_show);
            } else {
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                textView.setText(R.string.txt_hide);
            }
        });
    }

    public static void greeting_msg(final TextView txt_greeting) {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay < 4) {
            txt_greeting.setText(R.string.gd_eve);
        } else if (timeOfDay < 12) {
            txt_greeting.setText(R.string.gd_mrng);
        } else if (timeOfDay < 18) {
            txt_greeting.setText(R.string.gd_afternoon);
        } else if (timeOfDay < 21) {
            txt_greeting.setText(R.string.gd_eve);
        } else {
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

    public static void MakeToastMessage(Context context, String Message) {
        Toast toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
        View view = toast.getView();
        //Nikhil- firebase issue
//        view.setBackgroundResource(R.drawable.error_background);
//        TextView text = view.findViewById(android.R.id.message);
//        text.setTextColor(context.getResources().getColor(R.color.white));
//        text.setGravity(Gravity.CENTER);
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

    public static boolean isValidEmailId(String email) {

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

    public boolean isPositive(int num) {
        return 0 < num;
    }

    public boolean deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file.delete();
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public class EVENT {
        public static final String EVENT_TYPE = "click event";
        public static final String CATEGORY_LOGIN = "login";
        public static final String CATEGORY_DASHBOARD = "dashboard";
        public static final String CATEGORY_DASHBOARD_MENU = "dashboard_menu";
        public static final String CATEGORY_SERVICE = "service_request";
        public static final String CATEGORY_INVOICE = "payment_invoices";
        public static final String CATEGORY_TRANSACTION = "payment_transactions";
        public static final String CATEGORY_CHANGE_PLANE = "change_plan";
        public static final String CATEGORY_ACCOUNT = "my_account";
        public static final String CATEGORY_MANAGE_CONTACT = "my_account_manage_contact";
        public static final String CATEGORY_GET_HELP = "get_help";
        public static final String CATEGORY_PAYMENTS = "Payments";
        public static final String CATEGORY_HOME = "Home";
        public static final String CATEGORY_ALL_MENU = "All Menu";
        public static final String CATEGORY_MY_SR = "My SR";

    }

    public static int getEvent(String event) {
        int type = 1;
        switch (event.toLowerCase()) {
            case "spectra service bar action":
            case "service bar":
            case "quotabasedalert":
            case "spectra disconnection notice":
            case "spectra payment remainder":
                type = 0;
                break;
            case "network related":
            case "offer":
            case "payment":
            case "general":
                type = 1;
                break;
            case "bulk case closure":
            case "notify customer email and sms":
            case "sr creation":
                type = 2;
                break;
            case "invoice generation":
                type = 3;
                break;

        }
        return type;
    }


}
