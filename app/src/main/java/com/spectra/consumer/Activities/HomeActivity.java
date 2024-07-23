package com.spectra.consumer.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Fragments.HomeFragment;
import com.spectra.consumer.Fragments.InvoiceFragment;
import com.spectra.consumer.Fragments.MoreFragment;
import com.spectra.consumer.Fragments.SRFragment;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.Models.UserImage;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.GetAccountDataRequest;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.service.model.Response.LoginViaMobileResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.List;
import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.spectra.consumer.Utils.CommonUtils.saveUser;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.USER_IMAGE;
import static com.spectra.consumer.service.repository.ApiConstant.GET_ACCOUNT_DATA;

public class HomeActivity extends AppCompatActivity {

  @BindView(R.id.navigation)
  public BottomNavigationView layout_tabs;
  Intent intent;
  CurrentUserData currentUserData;
  @BindView(R.id.layout_cancelled)
  LinearLayout layout_cancelled;
  @BindView(R.id.create_new_sr)
  AppCompatTextView create_new_sr;
  @BindView(R.id.layout_activation_in_progress)
  LinearLayout layout_activation_in_progress;
  @BindView(R.id.close_app_layout)
  AppCompatTextView close_app_layout;
  @BindView(R.id.layout_logout)
  LinearLayout layout_logout;
  @BindView(R.id.llMyAccount)
  LinearLayout llMyAccount;
  @BindView(R.id.no_internet)
  LinearLayout no_internet;
  @BindView(R.id.progress_bar)
  ProgressBar progress_bar;
  AlertDialog dialog;
  private int SelectedID = 0;
  private Context context;
  private String cameFromScreen = null;
  private static final int NOTIFICATION_CODE = 99;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_home);
    ButterKnife.bind(this);
    Bundle bundle = getIntent().getExtras();
    context = HomeActivity.this;
    getAccountDetails();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
      requestPermissions(new String[]{
        Manifest.permission.POST_NOTIFICATIONS
      },NOTIFICATION_CODE);
    }
    create_new_sr.setOnClickListener(view -> {
      Intent intent = new Intent(HomeActivity.this, CreateSrActivity.class);
      startActivity(intent);
    });
    close_app_layout.setOnClickListener(view -> {
      DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(HomeActivity.this);
      droidPrefs.clear();
      UserData.DeleteUsersInfo();
      Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
      startActivity(intent);
      finish();
    });
    layout_logout.setOnClickListener(view -> {
      logout_user();
    });
    llMyAccount.setOnClickListener(view -> {
      Intent intent = new Intent(HomeActivity.this, MyAccountActivity.class);
      startActivity(intent);
    });

    if (bundle != null && bundle.containsKey("cameFromScreen")) {
      cameFromScreen = bundle.getString("cameFromScreen");
    } else {
      cameFromScreen = null;
    }
    setupBottomNavigation();
    setRequestForLocationPermission();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == NOTIFICATION_CODE){
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
        Log.d("======NP", "Allowed");
      }else{
        Log.d("======NP", "denied");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          requestPermissions(new String[]{
            Manifest.permission.POST_NOTIFICATIONS
          }, NOTIFICATION_CODE);
        }
      }
    }
  }

  private void setRequestForLocationPermission() {
    if (ActivityCompat.checkSelfPermission(HomeActivity.this,
      Manifest.permission.ACCESS_COARSE_LOCATION)
      != PackageManager.PERMISSION_GRANTED)  {
      ActivityCompat.requestPermissions(HomeActivity.this,
        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
        121);
    }
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null && bundle.containsKey("cameFromScreen")) {
      cameFromScreen = bundle.getString("cameFromScreen");
    } else {
      cameFromScreen = null;
    }
  }

  public void updateUser(LoginMobileResponse data) {
    saveUser(context, data);
    if (!data.getActInProgressFlag().equals("false")) {
      Intent intent = new Intent(HomeActivity.this, TrackActivity.class);
      startActivity(intent);
      finish();
    }
    if (data.getCancellationFlag().equalsIgnoreCase("true")) {
      layout_cancelled.setVisibility(View.VISIBLE);
      layout_tabs.setVisibility(View.GONE);
      layout_activation_in_progress.setVisibility(View.GONE);
    } else {
      layout_cancelled.setVisibility(View.GONE);
      layout_activation_in_progress.setVisibility(View.GONE);
      layout_tabs.setVisibility(View.VISIBLE);
      if (SelectedID == 0) {
        setSelectedID(R.id.navigation_Home);
      }
    }
  }


  private void getAccountDetails() {
    if (Constant.isInternetConnected(context)) {
      CurrentUserData Data = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
      SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
      no_internet.setVisibility(View.GONE);
      GetAccountDataRequest getAccountDataRequest = new GetAccountDataRequest();
      getAccountDataRequest.setAuthkey(BuildConfig.AUTH_KEY);
      getAccountDataRequest.setAction(GET_ACCOUNT_DATA);
      getAccountDataRequest.setCanID(Data.CANId);
      spectraViewModel.getAccountByCanId(getAccountDataRequest).observe(this, this::consumeResponse);
    } else {
      no_internet.setVisibility(View.VISIBLE);
      progress_bar.setVisibility(View.GONE);
    }
  }

  private void consumeResponse(ApiResponse apiResponse) {
    switch (apiResponse.status) {
      case LOADING:
        showLoadingView(true);
        break;
      case SUCCESS:
        renderSuccessResponse(apiResponse.data);
        showLoadingView(false);
        break;
      case ERROR:
        showLoadingView(false);
        break;
      default:
        break;
    }
  }

  private void renderSuccessResponse(Object response) {
    if (response != null) {
      LoginViaMobileResponse loginViaMobileResponse = (LoginViaMobileResponse) response;
      if (loginViaMobileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS) && loginViaMobileResponse.response != null) {
        List<LoginMobileResponse> loginMobileResponses = loginViaMobileResponse.response;
        if (loginMobileResponses.size() > 0) {
          LoginMobileResponse data = loginMobileResponses.get(0);
          updateUser(data);

        }
      }
    }
  }

  private void showLoadingView(boolean visible) {
    if (visible) {
      progress_bar.setVisibility(View.VISIBLE);
    } else {
      progress_bar.setVisibility(View.GONE);
    }
  }

  private void logout_user() {
    new AlertDialog.Builder(context).setTitle(getString(R.string.txt_logout)).setMessage(getString(R.string.logout_msg)).setPositiveButton("Ok", (dialogInterface, i) -> {
      dialogInterface.dismiss();
      UserImage userImage = DroidPrefs.get(context, USER_IMAGE, UserImage.class);
      DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(context);
      droidPrefs.clear();
      UserData.DeleteUsersInfo();
      DroidPrefs.apply(context, USER_IMAGE, userImage);
      Intent intent = new Intent(context, LoginActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
      finish();
    }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
  }

  public void setSelectedID(int id) {
    layout_tabs.setSelectedItemId(id);
  }

  private void setupBottomNavigation() {
    layout_tabs.setOnNavigationItemSelectedListener(item -> {
      switch (item.getItemId()) {
        case R.id.navigation_invoice:
          if (SelectedID != R.id.navigation_invoice) {
            SelectedID = item.getItemId();
            select_invoice();
          }
          return true;

        case R.id.navigation_more:
          if (SelectedID != R.id.navigation_more) {
            SelectedID = item.getItemId();
            select_more();
          }
          return true;

        case R.id.navigation_Sr:
          if (SelectedID != R.id.navigation_Sr) {
            SelectedID = item.getItemId();
            select_sr();
          }
          return true;

        case R.id.navigation_Home:
          if (SelectedID != R.id.navigation_Home) {
            SelectedID = item.getItemId();
            select_home();
          }
          return true;
      }
      return false;
    });
  }

  private void addFragment(Fragment fragment, String tag) {
    //switching fragment
    if (fragment != null) {
      getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, fragment)
        .addToBackStack(tag)
        .commit();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    new GetVersionCode().execute();
    if (cameFromScreen != null && cameFromScreen.equalsIgnoreCase("SR_TAB")) {
      setSelectedID(R.id.navigation_Sr);
      cameFromScreen = null;
    }
  }

  public void select_home() {
    addFragment(new HomeFragment(), "home");

  }

  public void select_invoice() {
    addFragment(new InvoiceFragment(), "invoice");
  }

  public void select_more() {
    addFragment(new MoreFragment(), "more");

  }

  public void select_sr() {
    addFragment(new SRFragment(), "sr");
  }

  @Override
  public void onBackPressed() {
    int index = getFragmentManager().getBackStackEntryCount();
    if (index == 1) {
      exit();
    }

  }

  public void exit() {
    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
    builder.setMessage(getString(R.string.exit_msg))
      .setCancelable(false).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).setPositiveButton("OK", (dialog, user_id) -> {
        finish();
        dialog.dismiss();
      });
    AlertDialog alert = builder.create();
    alert.show();
  }

  public int compareVersion(String version1, String version2) {
    String[] arr1 = version1.split("\\.");
    String[] arr2 = version2.split("\\.");

    int i = 0;
    while (i < arr1.length || i < arr2.length) {
      if (i < arr1.length && i < arr2.length) {
        if (Integer.parseInt(arr1[i]) < Integer.parseInt(arr2[i])) {
          return -1;
        } else if (Integer.parseInt(arr1[i]) > Integer.parseInt(arr2[i])) {
          return 1;
        }
      } else if (i < arr1.length) {
        if (Integer.parseInt(arr1[i]) != 0) {
          return 1;
        }
      } else {
        if (Integer.parseInt(arr2[i]) != 0) {
          return -1;
        }
      }
      i++;
    }
    return 0;
  }

  public void onUpdateNeeded(final String onlineVersion, final String updateUrl) {
    dialog = new AlertDialog.Builder(this)
      .setTitle("New Version Alert").setCancelable(false)
      .setMessage("A new version of My Spectra App is available. Update to version " + onlineVersion + ", now!")
      .setPositiveButton("Update",
        (dialog1, which) -> redirectStore(updateUrl)).create();
    dialog.setOnShowListener(arg0 -> {
      dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#3E7B8A"));
    });

    dialog.show();
  }

  private void redirectStore(String updateUrl) {
    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (dialog != null) {
      dialog.cancel();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (dialog != null) {
      dialog.cancel();
    }
  }

  @SuppressLint("StaticFieldLeak")
  private class GetVersionCode extends AsyncTask<Void, String, String> {
    @Override
    protected String doInBackground(Void... voids) {
      String latestVersion = null;

      try {
        if (progress_bar != null) {
          Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=com.spectra.consumer&hl=en").get();
          latestVersion = doc.getElementsContainingOwnText("Current Version").parents().first().getAllElements().last().text();
          return latestVersion;
        } else {
          return latestVersion;
        }
      } catch (Exception e) {
        return latestVersion;
      }
    }

    @Override
    protected void onPostExecute(String onlineVersion) {
      super.onPostExecute(onlineVersion);
      if (progress_bar != null && onlineVersion != null && !onlineVersion.isEmpty()) {
        if (compareVersion(BuildConfig.VERSION_NAME, onlineVersion) < 0) {
          onUpdateNeeded(onlineVersion, "market://details?id=" + BuildConfig.APPLICATION_ID);
        }
      }
    }
  }
}
