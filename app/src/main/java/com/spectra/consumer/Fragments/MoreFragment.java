package com.spectra.consumer.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.spectra.consumer.Activities.ContactUs;
import com.spectra.consumer.Activities.CreateSrActivity;
import com.spectra.consumer.Activities.DataUsageActivity;
import com.spectra.consumer.Activities.FAQActivity;
import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.LoginActivity;
import com.spectra.consumer.Activities.MyAccountActivity;
import com.spectra.consumer.Activities.MyPlanActivity;
import com.spectra.consumer.Activities.PayOtherActivity;
import com.spectra.consumer.Activities.PolicyDisclaimerActivity;
import com.spectra.consumer.Activities.SelectPackageActivity;
import com.spectra.consumer.Activities.StandingInstructionsActivity;
import com.spectra.consumer.Activities.TopUpActivity;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.Models.UserImage;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.DroidPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.USER_IMAGE;

public class MoreFragment extends Fragment {
    private HomeActivity homeActivity;
    private Context context;
    private View view;

    @BindView(R.id.layout_get_help_sub)
    LinearLayout layout_get_help_sub;
    @BindView(R.id.arrow_down)
    ImageView arrow_down;
    @BindView(R.id.layout_topup)
    RelativeLayout layout_topup;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        homeActivity = (HomeActivity) context;
        this.context = context;
    }


    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_more, null);
            ButterKnife.bind(this, view);

        }
        CurrentUserData userData = DroidPrefs.get(getActivity(), CurrentuserKey, CurrentUserData.class);
        if (userData.planDataVolume.equalsIgnoreCase("Unlimited")) {
            layout_topup.setVisibility(View.GONE);
        }


        return view;
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
            homeActivity.finish();
        }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
    }


    @OnClick({R.id.layout_pay_bill, R.id.layout_topup, R.id.layout_speed_test, R.id.layout_logout, R.id.layout_create_sr_menu, R.id.layout_data, R.id.layout_get_help, R.id.layout_contact_us, R.id.layout_faq_menu, R.id.layout_disclaimer, R.id.layout_privacy_policy, R.id.layout_my_account, R.id.layout_my_plan, R.id.layout_my_ledger, R.id.layout_standing_instructions, R.id.layout_change_plan,R.id.layout_rate_app})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_logout:
                logout_user();
                break;
            case R.id.layout_create_sr_menu:
                Intent intent = new Intent(homeActivity, CreateSrActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_data:
                Intent intent_data = new Intent(homeActivity, DataUsageActivity.class);
                startActivity(intent_data);
                break;
            case R.id.layout_get_help:
                if (layout_get_help_sub.isShown()) {
                    layout_get_help_sub.setVisibility(View.GONE);
                    arrow_down.setRotation(360);
                } else {
                    layout_get_help_sub.setVisibility(View.VISIBLE);
                    arrow_down.setRotation(180);
                }
                break;
            case R.id.layout_contact_us:
                Intent intent_contact = new Intent(homeActivity, ContactUs.class);
                startActivity(intent_contact);
                break;
            case R.id.layout_faq_menu:
                Intent intent_faq = new Intent(homeActivity, FAQActivity.class);
                startActivity(intent_faq);
                break;
            case R.id.layout_disclaimer:
                Intent intent_disclaimer = new Intent(homeActivity, PolicyDisclaimerActivity.class);
                intent_disclaimer.putExtra("type", "disclaimer");
                startActivity(intent_disclaimer);
                break;
            case R.id.layout_privacy_policy:
                Intent intent_privacy = new Intent(homeActivity, PolicyDisclaimerActivity.class);
                intent_privacy.putExtra("type", "policy");
                startActivity(intent_privacy);
                break;
            case R.id.layout_my_account:
                Intent intent_account = new Intent(homeActivity, MyAccountActivity.class);
                startActivity(intent_account);
                break;
            case R.id.layout_my_plan:
                Intent intent_plan = new Intent(homeActivity, MyPlanActivity.class);
                startActivity(intent_plan);
                break;
            case R.id.layout_my_ledger:
                homeActivity.select_invoice();
                break;
            case R.id.layout_standing_instructions:
                Intent intent_si = new Intent(homeActivity, StandingInstructionsActivity.class);
                startActivity(intent_si);
                break;
            case R.id.layout_change_plan:
                CurrentUserData userdata = DroidPrefs.get(context, CurrentuserKey, CurrentUserData.class);
                Intent changeplanIntent = new Intent(homeActivity, SelectPackageActivity.class);
                changeplanIntent.putExtra("plan_id", userdata.Product);
                startActivity(changeplanIntent);
                break;
            case R.id.layout_speed_test:
                Intent intent_layout_speed_test = new Intent(homeActivity, PolicyDisclaimerActivity.class);
                intent_layout_speed_test.putExtra("type", "speed");
                startActivity(intent_layout_speed_test);
                break;
            case R.id.layout_pay_bill:
                Intent intent_pay = new Intent(homeActivity, PayOtherActivity.class);
                startActivity(intent_pay);
                break;
            case R.id.layout_topup:
                   /* Intent selectplanintent=new Intent(homeActivity, TopUpListActivity.class);
                    startActivity(selectplanintent);*/
                Intent selectplanintent = new Intent(homeActivity, TopUpActivity.class);
                startActivity(selectplanintent);
                break;

            case R.id.layout_rate_app:
                final String appPackageName =getContext().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
        }

    }

}
