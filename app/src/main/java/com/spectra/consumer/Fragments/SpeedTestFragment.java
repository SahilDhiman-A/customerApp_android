package com.spectra.consumer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.spectra.consumer.Activities.SlowSpeedTroubleShootActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.databinding.FragmentSpeedTestBinding;

public class SpeedTestFragment extends Fragment {

    private FragmentSpeedTestBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_speed_test, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        setWebView();

        binding.imgBack.setOnClickListener(v-> {
            if(getActivity()!=null && getActivity() instanceof SlowSpeedTroubleShootActivity){
                ((SlowSpeedTroubleShootActivity)getActivity()).pullFragments(getActivity(),true,"SpeedTestFragment");
            }
        } );

        return view;
    }

    private void setWebView(){
        WebSettings webSettings = binding.webviewSpeed.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.webviewSpeed.loadUrl("https://www.speedtest.net/");
    }
}