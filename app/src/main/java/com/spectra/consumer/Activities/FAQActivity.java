package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Adapters.FaqAdapter;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.alertDailogs.ThanksFeedbackDailog;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.RequestThumbsDown;
import com.spectra.consumer.service.model.Response.Category;
import com.spectra.consumer.service.model.Response.FAQ;
import com.spectra.consumer.service.model.Response.FAQResponseBase;
import com.spectra.consumer.service.model.Response.FaqInfo;
import com.spectra.consumer.service.model.Response.FaqLikeUnlikeUpdate;
import com.spectra.consumer.service.model.Response.Segment;
import com.spectra.consumer.service.model.Response.ThoumbsCountResponse;
import com.spectra.consumer.service.model.SegmentResponseBase;
import com.spectra.consumer.service.repository.ApiConstant;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.net.UnknownHostException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_CODE;


public class FAQActivity extends AppCompatActivity implements FaqAdapter.FAQItemClickListener {
    private static final String TAG = "FAQActivity:::";

    private int lastExpandedPosition = -1;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;

    //  @BindView(R.id.expandableListView)
//    ExpandableListView expandableListView;
//
    @BindView(R.id.rv_faq)
    RecyclerView rv_faq;

//    @BindView(R.id.toolbar_head)
//    Toolbar toolbar_head;

    @BindView(R.id.txt_head)
    TextView txt_head;

    @BindView(R.id.progressBar)
    ProgressBar progress_bar;

    @BindView(R.id.iv_search)
    ImageView iv_search;

    @BindView(R.id.group_empty_faq)
    Group group_empty_faq;

    private SpectraViewModel spectraViewModel;
    private FaqAdapter faqAdapter;
    private int ClickedPosition;
    private Category mCategory;
    private String SegmentId = "";
    private String CanId = "";
    private ArrayList<FAQ> faqlist = new ArrayList<>();
    private int position = -1;
    private int childPosition = -1;
    private int groupPosition = -1;
//    private SimpleExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_faq);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar_head);
        CurrentUserData Data = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        CanId = Data.CANId;
        txt_head.setText(getString(R.string.faq));
        setRecyclerView();
        if (Constant.isInternetConnected(this)) {
            spectraViewModel.getSegmentlist(ApiConstant.ACTION_SEGMENT).observe(this, this::consumeResponse);
        }
    }


    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                showLoadingView(false);
                break;
            case ERROR:
                showLoadingView(false);
                assert apiResponse.error != null;
                if (apiResponse.error instanceof UnknownHostException) {
                    Constant.MakeToastMessage(this, "No internet Connection");
                } else {
                    Constant.MakeToastMessage(this, apiResponse.error.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object data, String action) {
        Log.d(TAG, "renderSuccessResponse:" + data);
        switch (action) {
            case ApiConstant.ACTION_SEGMENT:
                SegmentResponseBase mSegmentResponseBase = (SegmentResponseBase) data;
                CurrentUserData Data = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
                for (Segment mSegment : mSegmentResponseBase.getData()) {
                    if (mSegment.name.equalsIgnoreCase(Data.Segment)) {
                        SegmentId = mSegment._id;
                        //get Faq list accoding to the sagment
                        spectraViewModel.getFAQListbySegment(mSegment._id, "", CanId, ApiConstant.FAQCATEGORY).observe(this, this::consumeResponse);
                    }
                }
                break;

            case ApiConstant.FAQCATEGORY:
                FAQResponseBase fAQResponseBase = (FAQResponseBase) data;
                faqlist.clear();
                if (fAQResponseBase.statusCode == STATUS_CODE) {
                    for (FAQ faq : fAQResponseBase.data) {
                        if (faq.getFaq_info().size() > 0 && faq.getCategory_info().getIs_active()) {
                            for (FaqInfo faqInfo : faq.getFaq_info()) {
                                if (faqInfo.getIs_active()) {
                                    faqlist.add(faq);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (faqlist.size() == 0) {
                    Constant.MakeToastMessage(this, "No Data.");
                    group_empty_faq.setVisibility(View.VISIBLE);
                } else {
                    group_empty_faq.setVisibility(View.GONE);
                }
                faqAdapter.notifyDataSetChanged(faqlist);
                break;

            case ApiConstant.LIKE_FAQ:
                FaqLikeUnlikeUpdate mFaqLikeUnlikeUpdate = (FaqLikeUnlikeUpdate) data;
                //Nikhil - msg was null in response
                Toast.makeText(this, "FAQ Feedback Captured!!", Toast.LENGTH_SHORT).show();
//                Constant.MakeToastMessage(this, mFaqLikeUnlikeUpdate.message);
                break;

            case ApiConstant.GETTHOUMBCOUNT:
                ThoumbsCountResponse mThoumbsCountResponse = (ThoumbsCountResponse) data;
                if (mThoumbsCountResponse.getAdditionalInfo().equalsIgnoreCase("1")) {
                    faqAdapter.updateExpendableList(position, childPosition);
                }
                break;

            case ApiConstant.UNLIKE_FAQ:
                FaqLikeUnlikeUpdate faqLikeUnlikeUpdate = (FaqLikeUnlikeUpdate) data;
                new ThanksFeedbackDailog(this).show();
//                Constant.MakeToastMessage(this, faqLikeUnlikeUpdate.message);
                break;
            default:
                break;
        }
    }

    private void showLoadingView(boolean b) {
        progress_bar.setVisibility(b ? View.VISIBLE : View.GONE);
    }


    private void setRecyclerView() {
        rv_faq.setItemAnimator(null);
        faqAdapter = new FaqAdapter(this);
        rv_faq.setAdapter(faqAdapter);
        rv_faq.setHasFixedSize(true);
    }

    @OnClick({R.id.img_back, R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.iv_search:
                startActivityForResult(new Intent(this, SearchActivity.class), 100);
                break;
        }
    }


    @Override
    public void onLikeClick(String faqId) {
        Log.d(TAG, "onLikeClick: ");
        spectraViewModel.likeFaq(faqId, CanId, ApiConstant.LIKE_FAQ).observe(this, this::consumeResponse);
    }

    @Override
    public void onUnLikeClick(String faqId, String reason) {
        RequestThumbsDown requestThumbsDown = new RequestThumbsDown();
        requestThumbsDown.can_id = CanId;
        requestThumbsDown.faq_id = faqId;
        requestThumbsDown.reason = reason;
        spectraViewModel.unlikeFaq(requestThumbsDown, ApiConstant.UNLIKE_FAQ).observe(FAQActivity.this, FAQActivity.this::consumeResponse);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 100) {
            String quary = data.getStringExtra("search");
            spectraViewModel.getFAQListbySegment(SegmentId, quary, CanId, ApiConstant.FAQCATEGORY).observe(FAQActivity.this, FAQActivity.this::consumeResponse);
        }
    }

    @Override
    public void onVideoClick(String url) {
        Intent intent = new Intent(this, NotPDFActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("TYPE", "video");
        startActivity(intent);
    }


    @Override
    public void onGroupExpend(String faqId, int position) {
        spectraViewModel.addViewCountinFaq(faqId, ApiConstant.VIEWCOUNT).observe(FAQActivity.this, FAQActivity.this::consumeResponse);
    }

    @Override
    public void onChildLoad(String faqId, int position, int childPosition) {
        this.position = position;
        this.childPosition = childPosition;
        spectraViewModel.getThumbsCount(faqId, CanId, ApiConstant.GETTHOUMBCOUNT).observe(FAQActivity.this, FAQActivity.this::consumeResponse);
    }

    @Override
    protected void onDestroy() {
        if (faqAdapter != null) {
            faqAdapter.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (faqAdapter != null) {
            faqAdapter.pauseVideo();
        }
        super.onPause();
    }
}
