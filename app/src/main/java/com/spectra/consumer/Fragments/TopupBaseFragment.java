package com.spectra.consumer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Adapters.TopUpAdapter;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.TopUpResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopupBaseFragment extends Fragment {

    @BindView(R.id.view_topups)
    RecyclerView view_topups;

    private TopUpAdapter topUpListAdapter;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topbase, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        setInitView(bundle);
        return view;
    }

    private void setInitView(Bundle bundle){
        if(bundle!=null) {
            List<TopUpResponse> topUpRC = (List<TopUpResponse>) bundle.getSerializable("DATA");
            if(topUpRC!=null && topUpRC.size()>0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                view_topups.setLayoutManager(linearLayoutManager);
                topUpListAdapter = new TopUpAdapter(getContext(), topUpRC, false);
                view_topups.setAdapter(topUpListAdapter);
            }
        }
    }
}
