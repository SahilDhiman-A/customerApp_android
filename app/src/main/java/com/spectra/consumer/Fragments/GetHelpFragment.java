package com.spectra.consumer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.spectra.consumer.Activities.CreateSrActivity;
import com.spectra.consumer.Activities.FAQActivity;
import com.spectra.consumer.Activities.KnowYourBillActivity;
import com.spectra.consumer.Activities.NotPDFActivity;
import com.spectra.consumer.Activities.SpectraApplication;
import com.spectra.consumer.Adapters.FAQExpandableListAdapter;
import com.spectra.consumer.Adapters.FaqAdapter;
import com.spectra.consumer.Adapters.FaqChildAdapter;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.alertDailogs.ThanksFeedbackDailog;
import com.spectra.consumer.databinding.FragmentGetHelpBinding;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.RequestThumbsDown;
import com.spectra.consumer.service.model.Response.FaqInfo;
import com.spectra.consumer.service.model.Response.FaqLikeUnlikeUpdate;
import com.spectra.consumer.service.model.Response.Segment;
import com.spectra.consumer.service.model.Response.ThoumbsCountResponse;
import com.spectra.consumer.service.model.Response.Top5FAQResponse;
import com.spectra.consumer.service.model.SegmentResponseBase;
import com.spectra.consumer.service.repository.ApiConstant;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;

public class GetHelpFragment extends Fragment implements View.OnClickListener, FaqAdapter.FAQItemClickListener {

    public final String TAG = "GetHelpFragment::";

    private FragmentGetHelpBinding binding;
    private SpectraViewModel mSpectraViewModel;
    private int lastExpandedPosition = -1;
    private FaqAdapter.FAQItemClickListener mFAQItemClickListener;
    private String CanId = "";
    private String SegmentId = "";
    private FAQExpandableListAdapter mFAQExpandableListAdapter;
    HashMap<FaqInfo, FaqInfo> listData = new HashMap<>();
    ArrayList<FaqInfo> titleList = new ArrayList();
    private int childPosition = -1;
    private int groupPosition = -1;
    private int position = -1;
    private FaqChildAdapter faqChildAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_get_help, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        binding.cvViewAllTopic.setOnClickListener(this);
        binding.cvRaiseNewSr.setOnClickListener(this);
        binding.cvKnowYourBill.setOnClickListener(this);

        CurrentUserData Data = DroidPrefs.get(getActivity(), Constant.CurrentuserKey, CurrentUserData.class);
        CanId = Data.CANId;

        /// use for get all  segment  like ( Home ,Business )   for get the faq according to the segment
        mSpectraViewModel.getSegmentlist(ApiConstant.ACTION_SEGMENT).observe(this, this::consumeResponse);


        // use for on click of Action Search
        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(binding.etSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        faqChildAdapter = new FaqChildAdapter(getActivity(), 0, this, binding.recyclerViewFaq);
        binding.recyclerViewFaq.setAdapter(faqChildAdapter);
    }

    //Get top 5 FAQ's Result according to the search quary
    private void performSearch(String search) {
        SpectraApplication.getInstance().postEvent(Constant.EVENT.CATEGORY_GET_HELP, "Help_Search", "Search "+binding.etSearch.getText().toString(), CanId);
        mSpectraViewModel.getTop5FAQCategorySegment(SegmentId, search, ApiConstant.FAQCATEGORY).observe(this, this::consumeResponse);
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                showLoadingView(false);
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                break;
            case ERROR:
                showLoadingView(false);
                assert apiResponse.error != null;

                if (apiResponse.error instanceof UnknownHostException) {
                    Constant.MakeToastMessage(getActivity(), "No internet Connection");
                } else {
                    Constant.MakeToastMessage(getActivity(), apiResponse.error.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object data, String action) {
        switch (action) {
            case ApiConstant.ACTION_SEGMENT:
                SegmentResponseBase mSegmentResponseBase = (SegmentResponseBase) data;
                CurrentUserData Data = DroidPrefs.get(getActivity(), CurrentuserKey, CurrentUserData.class);
                for (Segment mSegment : mSegmentResponseBase.getData()) {
                    if (mSegment.name.equalsIgnoreCase(Data.Segment)) {
                        SegmentId = mSegment._id;
                        mSpectraViewModel.getTop5FAQCategorySegment(mSegment._id, "", ApiConstant.FAQCATEGORY).observe(this, this::consumeResponse);
                    }
                }
                break;

            case ApiConstant.FAQCATEGORY:
                Top5FAQResponse top5FAQResponse = (Top5FAQResponse) data;
                if (top5FAQResponse.statusCode.equalsIgnoreCase("200")) {
                    titleList.clear();
                    ArrayList<FaqInfo> faqs = new ArrayList<>();
                    listData.clear();
                    for (FaqInfo faq : top5FAQResponse.data) {
                        Log.d("FAQ--", "prepareListData: " + faq);
                        listData.put(faq, faq);
                        if (faq.getIs_active()) {
                            faqs.add(faq);
                        }
                    }
                    if (faqs.size() == 0) {
                        Constant.MakeToastMessage(getActivity(), "No data.");
                        faqChildAdapter.mDiffer.submitList(faqs);
                        binding.tvNoRecordFund.setVisibility(View.VISIBLE);
                    } else {
                        binding.recyclerViewFaq.setMediaObjects(faqs);
                        faqChildAdapter.mDiffer.submitList(faqs);
                        binding.tvNoRecordFund.setVisibility(View.GONE);
                    }

//                    titleList.addAll(listData.keySet());

//                    mFAQExpandableListAdapter = new FAQExpandableListAdapter(getActivity(), titleList, listData, lastExpandedPosition);

//                    mFAQExpandableListAdapter.setFAQItemClickListener(this);
//                    binding.ExpandableListViewFaq.setAdapter(mFAQExpandableListAdapter);
//                    binding.ExpandableListViewFaq.setGroupIndicator(null);
//                    binding.ExpandableListViewFaq.collapseGroup(0);
//                    binding.ExpandableListViewFaq.setDividerHeight(0);

//                    binding.ExpandableListViewFaq.setOnGroupExpandListener(groupPosition -> {
//                        if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
//                            binding.ExpandableListViewFaq.collapseGroup(lastExpandedPosition);
//                        }
//                        lastExpandedPosition = groupPosition;
//                    });
                } else {
                    Constant.MakeToastMessage(getActivity(), top5FAQResponse.message);
                }

                break;
            case ApiConstant.LIKE_FAQ:
                FaqLikeUnlikeUpdate faqLikeUnlikeUpdate = (FaqLikeUnlikeUpdate) data;
                //Nikhil - msg was null in response
                Toast.makeText(requireContext(), "FAQ Feedback Captured!!", Toast.LENGTH_SHORT).show();
//                Constant.MakeToastMessage(getActivity(), faqLikeUnlikeUpdate.message);
                break;

            case ApiConstant.UNLIKE_FAQ:
                new ThanksFeedbackDailog(getActivity()).show();
                break;
            case ApiConstant.GETTHOUMBCOUNT:
                ThoumbsCountResponse mThoumbsCountResponse = (ThoumbsCountResponse) data;
                if (mThoumbsCountResponse.getAdditionalInfo().equalsIgnoreCase("1")) {
//                  Constant.MakeToastMessage(getActivity(), mThoumbsCountResponse.getMessage());
//                  mFAQExpandableListAdapter.updateExpendableList(position, childPosition, groupPosition);
                    faqChildAdapter.updateList(position, childPosition, groupPosition);
                }
//                listData.get(childPosition).setLike(true);
//                listData.get(childPosition).setUnLike(true);
//                mFAQExpandableListAdapter.notifyDataSetChanged(listData);
                break;
        }
    }


    //Show ProgressBar on behalf of boolean value
    private void showLoadingView(boolean b) {
        binding.progress.setVisibility(b ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onStop() {
        super.onStop();
        binding.recyclerViewFaq.pauseVideo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_view_all_topic:
                SpectraApplication.getInstance().postEvent(Constant.EVENT.CATEGORY_GET_HELP, "Help_View_Topics", "View all topics clicked", CanId);
                startActivity(new Intent(getActivity(), FAQActivity.class));
                break;
            case R.id.cv_raise_new_sr:
                SpectraApplication.getInstance().postEvent(Constant.EVENT.CATEGORY_GET_HELP, "raise_new_service_request", "Service request clicked", CanId);
                startActivity(new Intent(getActivity(), CreateSrActivity.class));
                break;
            case R.id.cv_know_your_bill:
                startActivity(new Intent(getActivity(), KnowYourBillActivity.class));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }


    //override method use for on like click over the Like
    @Override
    public void onLikeClick(String FAQ_id) {
        Log.d(TAG, "onLikeClick: FAQ_id " + FAQ_id);
        Log.d(TAG, "onLikeClick:  CanId " + CanId);
        mSpectraViewModel.likeFaq(FAQ_id, CanId, ApiConstant.LIKE_FAQ).observe(this, this::consumeResponse);
    }

    //override method use for on like click over the Like

    @Override
    public void onUnLikeClick(String FAQ_id, String reason) {
        Log.d(TAG, "onUnLikeClick: " + FAQ_id);
        Log.d(TAG, "onUnLikeClick:  CanId " + CanId);

        RequestThumbsDown requestThumbsDown = new RequestThumbsDown();
        requestThumbsDown.can_id = CanId;
        requestThumbsDown.faq_id = FAQ_id;
        requestThumbsDown.reason = reason;
        mSpectraViewModel.unlikeFaq(requestThumbsDown, ApiConstant.UNLIKE_FAQ).observe(getActivity(), GetHelpFragment.this::consumeResponse);
    }

    @Override
    public void onVideoClick(String url) {
        Intent intent = new Intent(getActivity(), NotPDFActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("TYPE", "video");
        getActivity().startActivity(intent);
    }

    @Override
    public void onGroupExpend(String faqId, int position) {

        mSpectraViewModel.addViewCountinFaq(faqId, ApiConstant.VIEWCOUNT).observe(getActivity(), this::consumeResponse);
    }

    @Override
    public void onChildLoad(String faqId, int position, int childPosition) {
        this.position = position;
        this.childPosition = childPosition;

        //this api is use for get the status for user alread submit the feedback or not
        mSpectraViewModel.getThumbsCount(faqId, CanId, ApiConstant.GETTHOUMBCOUNT).observe(this, this::consumeResponse);
    }

}
