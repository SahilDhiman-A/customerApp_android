package com.spectra.consumer.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.spectra.consumer.Activities.TopUpActivity;
import com.spectra.consumer.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopupPaymentDoneFragment extends Fragment {

    private View view;
    @BindView(R.id.tv_back_home)
    TextView tv_back_home;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topup_paymentdone, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        setInitView(bundle);
        return view;
    }

    private void setInitView(Bundle bundle) {
        tv_back_home.setOnClickListener(v -> {
            if(getContext() instanceof TopUpActivity){
                ((TopUpActivity)getContext()).finish();
            }
        });
    }
}
