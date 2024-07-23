package com.spectra.consumer.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.spectra.consumer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SSWifiTroubleShootFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SSWifiTroubleShootFragment extends Fragment {



    public SSWifiTroubleShootFragment() {
        // Required empty public constructor
    }

    public static SSWifiTroubleShootFragment newInstance() {
        SSWifiTroubleShootFragment fragment = new SSWifiTroubleShootFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ss_wifi_trouble_shoot, container, false);
    }
}