package com.spectra.consumer.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Adapters.SelectCanAdapter;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserDataDB;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.USER_DB;

public class SelectCanIdActivity extends AppCompatActivity {

    @BindView(R.id.img_cross)
    ImageView img_cross;
    SelectCanAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.view_can_ids)
    RecyclerView view_can_ids;
    List<LoginMobileResponse> user_data_list = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_can_layout);
        ButterKnife.bind(this);
        linearLayoutManager=new LinearLayoutManager(this);
        view_can_ids.setLayoutManager(linearLayoutManager);
        UserDataDB  userDataDB = DroidPrefs.get(this,USER_DB, UserDataDB.class);
        setLIst(userDataDB.getResponseHashMap());
        img_cross.setOnClickListener(view -> onBackPressed());
        adapter=new SelectCanAdapter(this, user_data_list, (view, position) -> {
            int id =view.getId();
            if(id==R.id.layout_account){
                CurrentUserData currentUserData= DroidPrefs.get(SelectCanIdActivity.this, CurrentuserKey,CurrentUserData.class);
                currentUserData.CANId=user_data_list.get(position).getCANId();
                currentUserData.AccountName=user_data_list.get(position).getAccountName();
                currentUserData.Segment=user_data_list.get(position).getSegment();
                currentUserData.CancellationFlag=user_data_list.get(position).getCancellationFlag();
                currentUserData.actInProgressFlag=user_data_list.get(position).getActInProgressFlag();
                DroidPrefs.apply(SelectCanIdActivity.this,CurrentuserKey,currentUserData);
                Intent intent=new Intent(SelectCanIdActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();


            }
        });
        view_can_ids.setAdapter(adapter);
    }

    public void setLIst(LinkedHashMap<String,LoginMobileResponse> mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            user_data_list.add((LoginMobileResponse)pair.getValue());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
