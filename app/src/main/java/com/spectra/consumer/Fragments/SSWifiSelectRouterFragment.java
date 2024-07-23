package com.spectra.consumer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.spectra.consumer.Activities.SlowSpeedTroubleShootActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.databinding.FragmentSsWifiSelectRouterBinding;
import com.spectra.consumer.service.model.NetworkStateModel;
import com.spectra.consumer.viewModel.NetworkStateViewModel;

public class SSWifiSelectRouterFragment extends Fragment {

    private boolean fiveGHzFrequecyRunning = false;
    private FragmentSsWifiSelectRouterBinding binding;
    private NetworkStateViewModel networkStateViewModel;
    public SSWifiSelectRouterFragment() {
    }

    public static SSWifiSelectRouterFragment newInstance() {
        SSWifiSelectRouterFragment fragment = new SSWifiSelectRouterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkStateViewModel = ViewModelProviders.of(this).get(NetworkStateViewModel.class);
        networkStateViewModel.isOnlineStatus(getActivity().getApplication());
        networkStateViewModel.getSingleNetworkState().observe(this, networkStateModel -> setNetworkStateViewModel(networkStateModel));
        networkStateViewModel.getSingleNetwork();
    }

    private void setNetworkStateViewModel(NetworkStateModel networkStateModel){
        if(networkStateModel!=null) {
            if (networkStateModel.getFrequency() != null && networkStateModel.getFrequency().startsWith("5")) {
                binding.tvBand.setText("5Ghz");
                fiveGHzFrequecyRunning = true;
            }
            else {
                binding.tvBand.setText("2.4Ghz");
                fiveGHzFrequecyRunning = false;
            }
            if (networkStateModel.getRssi() >= -50 && networkStateModel.getRssi() < 0) {
                binding.tvWifiStrength.setText(getString(R.string.signal_strength_excellent));
            } else {
                binding.tvWifiStrength.setText(getString(R.string.signal_strength_weak));
            }
            binding.tvWifiSsid.setText(networkStateModel.getBssid());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ss_wifi_select_router, container, false);
        View view = binding.getRoot();

        binding.tvInteno.setOnClickListener(v -> {
            if(getActivity()!=null && getActivity() instanceof SlowSpeedTroubleShootActivity){
                if(fiveGHzFrequecyRunning) {
                    ((SlowSpeedTroubleShootActivity) getActivity()).pushFragmentForWifi("INTENO");
                }else{
                    ((SlowSpeedTroubleShootActivity) getActivity()).pushFragmentForIntenoRestart();
                }
            }
        });

        binding.tvDLink.setOnClickListener(v -> {
            if(getActivity()!=null && getActivity() instanceof SlowSpeedTroubleShootActivity){
                ((SlowSpeedTroubleShootActivity)getActivity()).pushFragmentForWifi("D-LINK");
            }
        });
        return view;
    }
}