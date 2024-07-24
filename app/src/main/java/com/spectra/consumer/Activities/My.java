package com.spectra.consumer.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.spectra.consumer.R;

import butterknife.ButterKnife;

public class My extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_contect1);
        ButterKnife.bind(this);

    }


}
