package com.spectra.consumer.Activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUs extends AppCompatActivity {
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.layout_new_connection)
    RelativeLayout layout_new_connection;
    @BindView(R.id.txt_new_connection)
    AppCompatTextView txt_new_connection;
    private String[] PERMISSIONS;
    @BindView(R.id.layout_support_line)
    RelativeLayout layout_support_line;
    @BindView(R.id.txt_support_line)
    AppCompatTextView txt_support_line;
    @BindView(R.id.txt_email_id)
    AppCompatTextView txt_email_id;
    @BindView(R.id.layout_email_id)
    RelativeLayout layout_email_id;
    int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_us);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.contact_us));
        PERMISSIONS = new String[]{Manifest.permission.CALL_PHONE};
    }


    @OnClick({R.id.img_back, R.id.layout_email_id, R.id.layout_support_line, R.id.layout_new_connection})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_email_id:
                send_email();
                break;
            case R.id.layout_support_line:
                connect_support_line();
                break;
            case R.id.layout_new_connection:
                request_new_connection();
                break;
        }
    }

    private void send_email() {
        if (!Constant.hasPermissions(ContactUs.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(ContactUs.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{txt_email_id.getText().toString()});
            i.putExtra(Intent.EXTRA_SUBJECT, "");
            i.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(i, "Send mail..."));
        }
    }

    private void connect_support_line() {
        if (!Constant.hasPermissions(ContactUs.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(ContactUs.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + txt_support_line.getText().toString()));
            startActivity(callIntent);
        }
    }

    private void request_new_connection() {
        if (!Constant.hasPermissions(ContactUs.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(ContactUs.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + txt_new_connection.getText().toString()));
            startActivity(callIntent);
        }
    }
}
