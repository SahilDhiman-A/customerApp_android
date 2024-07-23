package com.spectra.consumer.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.spectra.consumer.Activities.SlowSpeedTroubleShootActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.databinding.FragmentSsLanSpeedAndResultBinding;

public class SSSpeedResultLANFragment extends Fragment {

    private FragmentSsLanSpeedAndResultBinding binding;
    private String categoryNetworkBand = "LAN";//TRANSPORT_WIFI,
    private String frequencyWifiBand = "2.4Ghz";
    private static String TAG = "SSSpeedResultLANFragment";
    private boolean imageInfo = false;
    private boolean enableOnContinue = false;
    private CountDownTimer countDownTimer;

    public SSSpeedResultLANFragment() {
    }
    // TODO: Rename and change types and number of parameters
    public static SSSpeedResultLANFragment newInstance() {
        SSSpeedResultLANFragment fragment = new SSSpeedResultLANFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ss_lan_speed_and_result, container, false);
        View view = binding.getRoot();
        timerWork();

        binding.tvContinue.setAlpha(.5f);

        binding.imgInfo.setOnClickListener(v -> {
            imageInfo = true;
            binding.fdSsBoxInfo.setVisibility(View.VISIBLE);
            binding.edMbps.setVisibility(View.INVISIBLE);
        });

        binding.txtCross.setOnClickListener(v -> {
            imageInfo = false;
            binding.fdSsBoxInfo.setVisibility(View.GONE);
            binding.edMbps.setVisibility(View.VISIBLE);
        });


        binding.edMbps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    enableOnContinue = false;
                    binding.tvContinue.setAlpha(.5f);
                }else{
                    if(Integer.parseInt(s.toString())>0) {
                        enableOnContinue = true;
                        binding.tvContinue.setAlpha(1f);
                    }else{
                        enableOnContinue = false;
                        binding.tvContinue.setAlpha(.5f);
                    }
                }
            }
        });

        binding.laySpeedTest.setOnClickListener(v -> {
            if(!imageInfo) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Please conduct speed test on your system connected with LAN and enter the download speed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.tvContinue.setOnClickListener(v -> {
            if(!imageInfo){
                if(enableOnContinue){
                    if(getActivity()!=null && getActivity() instanceof SlowSpeedTroubleShootActivity){
                        ((SlowSpeedTroubleShootActivity)getActivity()).apiTrobleShootForLAN(binding.edMbps.getText().toString());
                    }
                }
            }
        });
        return view;
    }


    @SuppressLint("SetTextI18n")
    private void timerWork() {
        long time = 60000;
        //long time = 60;
        String text = "01:00";
        binding.tvTimer.setText(text);

        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer =  new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String text =  secondCounter(seconds);
                binding.tvTimer.setText(text);
            }
            public void onFinish() {
                String text ="00:00";
                binding.tvTimer.setText(text);
                binding.layspeed.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private String secondCounter(long seconds){
        if(seconds<10){
            return "00:0"+seconds;
        }else {
            return "00:"+seconds;
        }
    }
}