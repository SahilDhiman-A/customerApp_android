package com.spectra.consumer.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.spectra.consumer.Activities.SlowSpeedTroubleShootActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.databinding.FragmentSsWifiNotDetectBinding;

public class SSWifiNotDetectFragment extends Fragment {
    private String categoryNetworkBand = "LAN";//TRANSPORT_WIFI,
    private String frequencyWifiBand = "2.4Ghz";
    private static String TAG = "SSEnterSpeedAndResultFragment";
    FragmentSsWifiNotDetectBinding binding;
    private boolean imageInfo = false;

    public SSWifiNotDetectFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static SSWifiNotDetectFragment newInstance() {
        SSWifiNotDetectFragment fragment = new SSWifiNotDetectFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ss_wifi_not_detect, container, false);
        View view = binding.getRoot();

        binding.imgInfo.setOnClickListener(v -> {
            imageInfo  = true;
            binding.fdSsBoxInfo.setVisibility(View.VISIBLE);
        });

        binding.txtCross.setOnClickListener(v -> {
            imageInfo  = false;
            binding.fdSsBoxInfo.setVisibility(View.GONE);
        });

        binding.tvYes.setOnClickListener(v -> {
            if(!imageInfo) {
                if (getActivity() instanceof SlowSpeedTroubleShootActivity) {
                    ((SlowSpeedTroubleShootActivity) getActivity()).pushFragmentForLan();
                }
            }
        });

        binding.tvNo.setOnClickListener(v -> {
            if(!imageInfo) {
                if(getActivity()!=null && getActivity() instanceof SlowSpeedTroubleShootActivity){
                    ((SlowSpeedTroubleShootActivity)getActivity()).pushFragmentForSSWifiRestart();

                }
//                String text = "Connect your device to Spectra network either through LAN / Wi-Fi and re-start the troubleshooting.";
           /*     binding.tvTitle.setText(text);
                binding.layRouter.setVisibility(View.GONE);
                binding.tvOkay.setVisibility(View.VISIBLE);
                binding.tvOkay.setOnClickListener(v1 -> {
                    if(!imageInfo){
                        if(getActivity()!=null){
                            getActivity().finish();
                        }
                    }
                });*/
            }
        });

        return view;
    }
}