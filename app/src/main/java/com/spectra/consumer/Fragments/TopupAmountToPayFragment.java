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
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;

public class TopupAmountToPayFragment extends Fragment {

    private View view;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_pay_now)
    TextView tv_pay_now;
    @BindView(R.id.tv_pay_msg)
    TextView tv_pay_msg;
//    private boolean enablePayment = false;
    private double price = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topup_amount_pay, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        setInitView(bundle);
        return view;
    }

    private void setInitView(Bundle bundle) {
        if(bundle!=null) {
            CurrentUserData userdata = DroidPrefs.get(getContext(), CurrentuserKey, CurrentUserData.class);
            TopUpResponse proTopupResponse = (TopUpResponse) bundle.getSerializable("DATA");
            if(proTopupResponse!=null && proTopupResponse.getPgDataCharges()!=null) {
               Date date =  Calendar.getInstance().getTime();
               try {
                   SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
                   String todayDate = dateFormat.format(date);
                   String fupDate = userdata.FUPResetDate;
                   String lastdate = "";
                   try{
                       SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                       Date dateLast = dateFormat1.parse(fupDate);
                       lastdate = dateFormat.format(dateLast);
                   }catch (Exception e){
                       e.printStackTrace();
                   }

                   String msg = proTopupResponse.getTopup_name() + " will be activated effective on "+todayDate+" and the purchased data will be available till "+lastdate;
                   tv_pay_msg.setText(msg);
               }catch (Exception e){
                   e.printStackTrace();
               }
                try {
//                  price = 1;
                    price = Double.parseDouble(proTopupResponse.getPgDataCharges());
                    tv_price.setText("₹ "+price);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        tv_pay_now.setOnClickListener(v -> {
                if (getContext() instanceof TopUpActivity) {
                    if(price>0) {
                        ((TopUpActivity) getContext()).gotoPayment("" + price);
                    }else{
                        ((TopUpActivity) getContext()).addTopUpToInVoice("" + price);
                    }
                }
        });
    }
}