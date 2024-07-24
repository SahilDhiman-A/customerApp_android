package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.Models.UserImage;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.TrackOrderRequest;
import com.spectra.consumer.service.model.Response.TrackOrderResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.USER_IMAGE;
import static com.spectra.consumer.service.repository.ApiConstant.TRACK_ORDER;

public class TrackActivity extends AppCompatActivity {


    @BindView(R.id.img_back)
    AppCompatImageView imgBack;
    @BindView(R.id.txt_head)
    TextView txtHead;
    @BindView(R.id.txt_share)
    TextView txtShare;
    @BindView(R.id.toolbar_head)
    Toolbar toolbarHead;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvCustNo)
    AppCompatTextView tvCustNo;
    @BindView(R.id.imgCAF)
    ImageView imgCAF;
    @BindView(R.id.tvCAFSubmitted)
    AppCompatTextView tvCAFSubmitted;
    @BindView(R.id.imgDocumentsVerified)
    ImageView imgDocumentsVerified;
    @BindView(R.id.tvDocumentsVerified)
    AppCompatTextView tvDocumentsVerified;
    @BindView(R.id.imgInstallationCompleted)
    ImageView imgInstallationCompleted;
    @BindView(R.id.tvInstallationCompleted)
    AppCompatTextView tvInstallationCompleted;
    @BindView(R.id.imgHappyBrowsing)
    ImageView imgHappyBrowsing;
    @BindView(R.id.tvHappyBrowsing)
    AppCompatTextView tvHappyBrowsing;
    @BindView(R.id.layoutCv2)
    CardView layoutCv2;
    private Context context;
    int active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_order);
        ButterKnife.bind(this);
        context = TrackActivity.this;
        txtHead.setText(R.string.TrackYourOrder);
        CurrentUserData userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        SpectraViewModel spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        TrackOrderRequest trackOrderRequest = new TrackOrderRequest();
        trackOrderRequest.setAction(TRACK_ORDER);
        trackOrderRequest.setAuthkey(BuildConfig.AUTH_KEY);
        trackOrderRequest.setCanID(userData.CANId);
        imgBack.setVisibility(View.GONE);
        spectraViewModel.trackOrder(trackOrderRequest).observe(this, this::consumeResponse);
        findViewById(R.id.llMyAccount).setOnClickListener(view -> {
            Intent intent = new Intent(TrackActivity.this, MyAccountActivity.class);
            startActivity(intent);
        });
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
                assert apiResponse.error != null;
                Constant.MakeToastMessage(context, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }


    private void renderSuccessResponse(Object response) {
        TrackOrderResponse trackOrderResponse = (TrackOrderResponse) response;
        if (response != null && trackOrderResponse.getStatus().equals("success")) {

            String name;
            name = trackOrderResponse.getResponse().getName();
            if (name.length() > 20) {
                name = name.substring(0, 17);
                name = name + "...";
            }
            tvName.setText(name);
            tvCustNo.setText(trackOrderResponse.getResponse().getCANID());
            setTrackList(trackOrderResponse.getResponse().getStatusDates(), trackOrderResponse.getResponse().getStatus());
        } else {
            Constant.MakeToastMessage(context, trackOrderResponse.getMessage());
        }

    }


    @SuppressLint("SetTextI18n")
    private void setTrackList(String trackList, String status) {

        String[] trackArray = trackList.split(",");
        String[] statusArray = status.split(",");
        String[] dates = getDate(trackArray, statusArray);
        int color = R.color.payment_success;
        int n = dates.length - 1;
        active = R.drawable.green_toggle;
        switch (n) {
            case 0:


                tvCAFSubmitted.setTextColor(ContextCompat.getColor(context, color));

                tvCAFSubmitted.setText(dates[0]);


                setTextViewDrawableColor(imgCAF, tvCAFSubmitted, color);
                break;
            case 1:

                tvCAFSubmitted.setTextColor(ContextCompat.getColor(context, color));
                tvDocumentsVerified.setTextColor(ContextCompat.getColor(context, color));

                tvCAFSubmitted.setText(dates[0]);
                tvDocumentsVerified.setText(dates[1]);

                setTextViewDrawableColor(imgCAF, tvCAFSubmitted, color);
                setTextViewDrawableColor(imgDocumentsVerified, tvDocumentsVerified, color);

                break;
            case 2:


                tvCAFSubmitted.setTextColor(ContextCompat.getColor(context, color));
                tvDocumentsVerified.setTextColor(ContextCompat.getColor(context, color));
                tvInstallationCompleted.setTextColor(ContextCompat.getColor(context, color));

                tvCAFSubmitted.setText(dates[0]);
                tvDocumentsVerified.setText(dates[1]);
                tvInstallationCompleted.setText(dates[2]);

                setTextViewDrawableColor(imgCAF, tvCAFSubmitted, color);
                setTextViewDrawableColor(imgDocumentsVerified, tvDocumentsVerified, color);
                setTextViewDrawableColor(imgInstallationCompleted, tvInstallationCompleted, color);

                break;
            case 3:


                tvCAFSubmitted.setTextColor(ContextCompat.getColor(context, color));
                tvDocumentsVerified.setTextColor(ContextCompat.getColor(context, color));
                tvInstallationCompleted.setTextColor(ContextCompat.getColor(context, color));
                tvHappyBrowsing.setTextColor(ContextCompat.getColor(context, color));

                tvCAFSubmitted.setText(dates[0]);
                tvDocumentsVerified.setText(dates[1]);
                tvInstallationCompleted.setText(dates[2]);
                tvHappyBrowsing.setText(dates[3]);


                setTextViewDrawableColor(imgCAF, tvCAFSubmitted, color);
                setTextViewDrawableColor(imgDocumentsVerified, tvDocumentsVerified, color);
                setTextViewDrawableColor(imgInstallationCompleted, tvInstallationCompleted, color);
                setTextViewDrawableColor(imgHappyBrowsing, tvHappyBrowsing, color);
                break;
        }
        layoutCv2.setVisibility(View.VISIBLE);


    }

    private String[] getDate(String[] trackArray, String[] status) {
        String[] dates = new String[trackArray.length];
        for (int i = 0; i < dates.length; i++) {
            dates[i] = status[i] + "\n" + parsDate(trackArray[i]);
        }
        return dates;
    }


    private String parsDate(String s) {
        String date1;
        try {
            date1 = s.split("[|]")[1];
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        try {
            DateFormat originalFormat = new SimpleDateFormat("M/dd/yyyy hh:mm:ss aa", Locale.ENGLISH);
            @SuppressLint("SimpleDateFormat") DateFormat targetFormat = new SimpleDateFormat("dd-M-yyyy");
            Date date = originalFormat.parse(date1);
            assert date != null;
            return targetFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private void setTextViewDrawableColor(ImageView image, TextView textView, int color) {
        image.setImageResource(active);
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    drawable.setColorFilter(new PorterDuffColorFilter(getColor(color), PorterDuff.Mode.SRC_IN));
                }
            }
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @OnClick({R.id.img_back, R.id.layout_logout, R.id.llCall})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                break;
            case R.id.layout_logout:
                logout_user();
                break;

            case R.id.llCall:
                dialPhoneNumber("011 4003 3100");
                break;
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

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
