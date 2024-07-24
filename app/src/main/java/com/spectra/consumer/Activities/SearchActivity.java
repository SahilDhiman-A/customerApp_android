package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyInfo;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.spectra.consumer.R;
import com.spectra.consumer.Utils.KeyBoardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.cancel)
    ImageView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        cancel.setOnClickListener(this);
        KeyBoardUtils.openKeyboardWhenFocus(this, etSearch);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch(String quary) {
        Intent intent = new Intent();
        intent.putExtra("search", quary);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel) {
            finish();
        }
    }
}