package com.spectra.consumer.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;

import activeandroid.util.Log;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    new Handler().postDelayed(() -> {
      Intent intent;
      final CurrentUserData userData = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData.class);
      if (userData != null && userData.is_Login) {
        if (userData.actInProgressFlag.equals("false")) {
          intent = new Intent(SplashActivity.this, HomeActivity.class);
        } else {
          intent = new Intent(SplashActivity.this, TrackActivity.class);
        }
      } else {
        intent = new Intent(SplashActivity.this, LoginActivity.class);
      }
      startActivity(intent);
      finish();
    }, 2500);
  }
}
