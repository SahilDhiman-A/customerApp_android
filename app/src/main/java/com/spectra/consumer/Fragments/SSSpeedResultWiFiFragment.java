package com.spectra.consumer.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.spectra.consumer.Activities.SlowSpeedTroubleShootActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.databinding.FragmentSsWifiSpeedResultBinding;
import com.spectra.consumer.service.model.NetworkStateModel;
import com.spectra.consumer.viewModel.NetworkStateViewModel;

public class SSSpeedResultWiFiFragment extends Fragment {

    private FragmentSsWifiSpeedResultBinding binding;
    private String categoryNetworkBand = "LAN";//TRANSPORT_WIFI,
    private String frequencyWifiBand = "2.4Ghz";
    private boolean fiveGHzFrequecyRunning = false;
    private boolean twoGHzFrequecyRunning = false;
    private boolean isConnectionStregthExcellent = false;
    private static String TAG = "SSSpeedResultWiFiFragment";
    private String selectedRouter;
    private NetworkStateViewModel networkStateViewModel;
    private String status = "BEFORE_TIMER";
    private boolean enableOnContinue = false;
    private CountDownTimer countDownTimer;

    public SSSpeedResultWiFiFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static SSSpeedResultWiFiFragment newInstance() {
        SSSpeedResultWiFiFragment fragment = new SSSpeedResultWiFiFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ss_wifi_speed_result, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        selectedRouter = bundle.getString("ROUTER");

        binding.tvContinue.setAlpha(.5f);

        networkStateViewModel = ViewModelProviders.of(this).get(NetworkStateViewModel.class);
        networkStateViewModel.getSingleNetworkState().observe(this, networkStateModel -> {
            setNetworkStatus(networkStateModel, status);
        });

        networkStateViewModel.getSingleNetwork();

        binding.edMbps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    enableOnContinue = false;
                    binding.tvContinue.setAlpha(.5f);
                } else {
                    if (Integer.parseInt(s.toString()) > 0) {
                        enableOnContinue = true;
                        binding.tvContinue.setAlpha(1f);
                    } else {
                        enableOnContinue = false;
                        binding.tvContinue.setAlpha(.5f);
                    }
                }
            }
        });

        binding.laySpeedTest.setOnClickListener(v -> {
            if (getActivity() != null && getActivity() instanceof SlowSpeedTroubleShootActivity) {
                ((SlowSpeedTroubleShootActivity) getActivity()).pushFragmentForSpeedTest();
            }
        });

        binding.tvContinue.setOnClickListener(v -> {
            if (enableOnContinue) {
                if (getActivity() != null && getActivity() instanceof SlowSpeedTroubleShootActivity) {
                    if (fiveGHzFrequecyRunning) {
                        //Nikhil -> 2.5 and 5 ghz have comments
                        ((SlowSpeedTroubleShootActivity) getActivity()).apiTrobleShootForWIFI5GHz(binding.edMbps.getText().toString(),
                                binding.etRemark.getText().toString());
                    } else if (twoGHzFrequecyRunning) {
                        ((SlowSpeedTroubleShootActivity) getActivity()).apiTrobleShootForWIFI2_4GHz(binding.edMbps.getText().toString(),
                                binding.etRemark.getText().toString());
                    }
                }
            }
        });
        return view;
    }


    private void setNetworkStatus(NetworkStateModel networkStateModel, String status) {
        if (networkStateModel != null) {
            if (networkStateModel.getFrequency() != null && networkStateModel.getFrequency().startsWith("5")) {
                fiveGHzFrequecyRunning = true;
                twoGHzFrequecyRunning = false;
                binding.tvBand.setText("5Ghz");
            } else {
                fiveGHzFrequecyRunning = false;
                twoGHzFrequecyRunning = true;
                binding.tvBand.setText("2.4Ghz");
            }
            if (networkStateModel.getRssi() >= -50 && networkStateModel.getRssi() < 0) {
                isConnectionStregthExcellent = true;
                binding.tvStrengthStatus.setText(getString(R.string.signal_strength_excellent));
            } else {
                isConnectionStregthExcellent = false;
                binding.tvStrengthStatus.setText(getString(R.string.signal_strength_weak));
            }
            binding.tvSsid.setText(networkStateModel.getBssid());

            if (fiveGHzFrequecyRunning && isConnectionStregthExcellent) {
                binding.layspeed.setVisibility(View.VISIBLE);
                binding.tvMessage.setVisibility(View.GONE);
                binding.tvTimer.setVisibility(View.GONE);
            } else if (twoGHzFrequecyRunning && isConnectionStregthExcellent) {
                binding.layspeed.setVisibility(View.VISIBLE);
                binding.tvMessage.setVisibility(View.GONE);
                binding.tvTimer.setVisibility(View.GONE);
            } else {
                binding.layspeed.setVisibility(View.GONE);
                binding.tvMessage.setVisibility(View.VISIBLE);
                binding.tvTimer.setVisibility(View.VISIBLE);

                if (status.equalsIgnoreCase("AFTER_TIMER")) {
                    if (getActivity() != null && getActivity() instanceof SlowSpeedTroubleShootActivity) {
                        ((SlowSpeedTroubleShootActivity) getActivity()).pushFragmentForSSWifiRestart();
                    }
                } else {
                    if (fiveGHzFrequecyRunning) {
                        if (!isConnectionStregthExcellent) {
                            binding.tvMessage.setText(getString(R.string.ss_5_weak_signal_msg));
                        }
                    } else if (twoGHzFrequecyRunning) {
                        if (isConnectionStregthExcellent) {
                            binding.tvMessage.setText(getString(R.string.ss_2_4_excellent_signal_msg));
                        } else {
                            binding.tvMessage.setText(getString(R.string.ss_2_4_weak_signal_msg));
                        }
                    }
                    timerWork();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @SuppressLint("SetTextI18n")
    private void timerWork() {
        long time = 60000 * 2;
        //long time = 60;
        String text = "02:00";
        binding.tvTimer.setText(text);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String text = secondCounter(seconds);
                binding.tvTimer.setText(text);
            }

            public void onFinish() {
                String text = "00:00";
                binding.tvTimer.setText(text);
                status = "AFTER_TIMER";
                networkStateViewModel.getSingleNetwork();
            }
        }.start();
    }

    private long value;

    private String secondCounter(long seconds) {
        if (seconds >= 60) {
            value = seconds - 60;
            if (value < 10) {
                return "01:0" + value;
            } else {
                return "01:" + value;
            }
        } else if (seconds < 10) {
            return "00:0" + seconds;
        } else {
            return "00:" + seconds;
        }
    }
}