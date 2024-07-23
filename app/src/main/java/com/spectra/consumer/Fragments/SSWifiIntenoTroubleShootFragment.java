package com.spectra.consumer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.SlowSpeedTroubleShootActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.databinding.FragmentSsWifiRestartBinding;

public class SSWifiIntenoTroubleShootFragment extends Fragment {
    private String categoryNetworkBand = "LAN";//TRANSPORT_WIFI,
    private String frequencyWifiBand = "2.4Ghz";
    private static String TAG = "SSEnterSpeedAndResultFragment";
    FragmentSsWifiRestartBinding binding;
//    private boolean imageInfo = false;

    public SSWifiIntenoTroubleShootFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static SSWifiIntenoTroubleShootFragment newInstance() {
        SSWifiIntenoTroubleShootFragment fragment = new SSWifiIntenoTroubleShootFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ss_wifi_restart, container, false);
        View view = binding.getRoot();

        binding.tvTitle.setText(getString(R.string.ss_inteno_restart_trouble_shoot_msg));

//        binding.imgInfo.setOnClickListener(v -> {
//            imageInfo  = true;
//            binding.fdSsBoxInfo.setVisibility(View.VISIBLE);
//        });
//
//        binding.txtCross.setOnClickListener(v -> {
//            imageInfo  = false;
//            binding.fdSsBoxInfo.setVisibility(View.GONE);
//        });

        binding.tvOkay.setOnClickListener(v -> {
//            if(!imageInfo) {
                if (getActivity()!=null && getActivity() instanceof SlowSpeedTroubleShootActivity) {
                    Bundle bundle = new Bundle();
                    bundle.putString("cameFromScreen","SR_TAB");
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ((SlowSpeedTroubleShootActivity) getActivity()).finish();
                }
//            }
        });
        return view;
    }
}