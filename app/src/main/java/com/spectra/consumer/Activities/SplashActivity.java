package com.spectra.consumer.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.viewModel.SpectraNotificationViewModel;

import org.json.JSONObject;

import java.util.Objects;

import butterknife.ButterKnife;

import static com.spectra.consumer.Activities.HomeActivity.TOKEN;
import static com.spectra.consumer.Activities.HomeActivity.canID;
import static com.spectra.consumer.Activities.HomeActivity.canIDS1;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.allCan;
import static com.spectra.consumer.Utils.Constant.getEvent;
import static com.spectra.consumer.service.repository.ApiConstant.READ_NOTIFICATION;

public class SplashActivity extends AppCompatActivity {
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null&&bundle.containsKey("_id")) {
            callNextNot(bundle);
        } else {
            callNext();

        }

    }

    public void callNext() {
        new Handler().postDelayed(() -> {
            Intent intent;
            final CurrentUserData userData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);


            if (userData != null && userData.is_Login) {
                if (TextUtils.isEmpty(userData.CANId) || userData.CANId.equals("218608")) {
                    autoLogoutUser(this);
                } else {

                    if (userData.actInProgressFlag.equals("false")) {

                        intent = new Intent(SplashActivity.this, HomeActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, TrackActivity.class);
                    }
                    startActivity(intent);
                    finish();

                }
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, 2500);
    }

    public void callNextNot(Bundle bundle) {
        new Handler().postDelayed(() -> {
            Intent intent;
            final CurrentUserData userData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);
            if (userData != null && userData.is_Login) {
                if (TextUtils.isEmpty(userData.CANId) || userData.CANId.equals("218608")) {
                    autoLogoutUser(this);
                } else {
                    notWork(bundle);
                }
            } else {
                intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, 1000);
    }


    private void autoLogoutUser(Context context) {
        DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(context);
        droidPrefs.clear();
        UserData.DeleteUsersInfo();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void notWork(Bundle bundle) {
        String _id = bundle.getString("_id");
        String canId = bundle.getString("can_id");
        try {
            JSONObject json = new JSONObject(Objects.requireNonNull(bundle.getString("order_info")));
            type = json.getString("type");
            setData();
            readAction(type,_id,canId);
        } catch (Exception e) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            e.printStackTrace();
        }

    }

    private void setData() {
        try {
            CurrentUserData userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
            if (TextUtils.isEmpty(TOKEN) || TextUtils.isEmpty(canID) || TextUtils.isEmpty(allCan)) {
                TOKEN = userData.TOKEN;
                canIDS1 = userData.linkedIDsList;
                allCan = userData.linkedIDs;
                canID = userData.CANId;
            }
            DroidPrefs.apply(this, CurrentuserKey, userData);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void readAction(String event,String _id, String canId) {
        Intent intent;
        int type = getEvent(event);
        if (type != 1) {
            intent = new Intent(getApplicationContext(), HomeActivity.class);
            if (type == 2) {
                intent.putExtra("cameFromScreen", "SR_TAB");
            }
            if (type == 3) {
                intent.putExtra("cameFromScreen", "INVOICE_TAB");
            }
        } else {
            intent = new Intent(getApplicationContext(), NotificationLActivity.class);
        }
        intent.putExtra("NOT_ID", _id);
        intent.putExtra("CAN_ID", canId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
