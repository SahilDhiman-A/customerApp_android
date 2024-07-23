package com.spectra.consumer.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import com.spectra.consumer.Adapters.ExpandableListAdapter;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.SEGMENT_HOME;

public class FAQActivity extends AppCompatActivity {
    private int lastExpandedPosition = -1;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.txt_head)
    TextView txt_head;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_faq);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        txt_head.setText(getString(R.string.faq));
        prepareListData();
    }


    @OnClick({R.id.img_back})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
    }

    private void prepareListData() {
        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild=new HashMap<>();
        ExpandableListAdapter listAdapter;
        List<String> data_child;
        CurrentUserData  currentUserData= DroidPrefs.get(this, Constant.CurrentuserKey,CurrentUserData.class);
        if(currentUserData.Segment.equalsIgnoreCase(SEGMENT_HOME)){
            listDataHeader= Arrays.asList(getResources().getStringArray(R.array.faq_question_b2c));
            data_child=Arrays.asList(getResources().getStringArray(R.array.faq_answer_b2c));
        }
        else{
            listDataHeader= Arrays.asList(getResources().getStringArray(R.array.faq_question_b2b));
            data_child=Arrays.asList(getResources().getStringArray(R.array.faq_answer_b2b));
        }
        if(listDataHeader.size()==data_child.size()) {
            for (int i = 0; i < listDataHeader.size(); i++) {
                List<String> one = new ArrayList<>();
                one.add(data_child.get(i));
                listDataChild.put(listDataHeader.get(i), one);

            }
        }
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if (lastExpandedPosition != -1
                    && groupPosition != lastExpandedPosition) {
                expandableListView.collapseGroup(lastExpandedPosition);
            }
            lastExpandedPosition = groupPosition;
        });

    }
}
