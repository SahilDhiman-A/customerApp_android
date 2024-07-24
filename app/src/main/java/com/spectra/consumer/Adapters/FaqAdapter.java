package com.spectra.consumer.Adapters;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EVENT.CATEGORY_DASHBOARD;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Activities.SpectraApplication;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.customView.VideoPlayerRecyclerView;
import com.spectra.consumer.databinding.AdapterFaqBinding;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Response.FAQ;
import com.spectra.consumer.service.model.Response.FaqInfo;
import java.util.ArrayList;
import java.util.List;

import activeandroid.util.Log;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    final private Context context;

    FaqAdapter.FAQItemClickListener mFAQItemClickListener;
    private int lastExpandedPosition = -1;
    private List<FAQ> data;
    private int groupPosition = -1;
    AdapterFaqBinding adapterFaqBinding;
    //    FaqChildAdapter mFaqChildAdapter;
    private ArrayList<VideoPlayerRecyclerView> recyclerViewsList = new ArrayList();
    private String canIdAnalytics;

    private RecyclerView.RecycledViewPool viewPool
            = new RecyclerView.RecycledViewPool();

    public void notifyDataSetChanged(List<FAQ> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public FaqAdapter(Context context) {
        this.context = context;
        CAN_ID canIdNik = DroidPrefs.get(context, BASE_CAN, CAN_ID.class);
        Log.d("Nik Can", canIdNik.baseCanID);
        canIdAnalytics = canIdNik.baseCanID;
        mFAQItemClickListener = (FaqAdapter.FAQItemClickListener) context;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_faq, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        }else{
            if ((Integer) payloads.get(0) != -1) {
                FAQ mFAQ = data.get(position);
                if(holder.mFaqChildAdapter != null) {
                    holder.mFaqChildAdapter.notifyItemChanged((Integer) payloads.get(0), true);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FAQ mFAQ = data.get(position);
        holder.binding.expandedListheader.setText(mFAQ.getCategory_info().getName());
//        prepareListData(holder.binding.recyclerView, data, position);
//        holder.binding.recyclerView.releasePlayer();
        loadFaqChild(holder, mFAQ, position);
        holder.binding.controllList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupPosition = -1;
                lastExpandedPosition = -1;
                holder.binding.expandedListheader.setTextColor(holder.binding.recyclerView.getVisibility() == View.GONE ? context.getColor(R.color.bitter_sweet) : context.getColor(R.color.black));
                holder.binding.controllList.setImageResource(holder.binding.recyclerView.getVisibility() == View.GONE ? R.drawable.ic_remove : R.drawable.ic_add);
                holder.binding.sparator.setVisibility(holder.binding.recyclerView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                holder.binding.recyclerView.setVisibility(holder.binding.recyclerView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                //Adapter Analytics
                SpectraApplication.getInstance().postEvent(CATEGORY_DASHBOARD, "FAQ_topic_Clicked", mFAQ.getName(),canIdAnalytics);
            }
        });
    }

    private void loadFaqChild(FaqViewHolder holder, FAQ fAQ, int parentPosition) {
        ArrayList<FaqInfo> faqInfos = new ArrayList<>();
        for (FaqInfo faqInfo : fAQ.getFaq_info()) {
            if(faqInfo.getIs_active()){
                faqInfos.add(faqInfo);
            }
        }
        holder.binding.recyclerView.setMediaObjects(fAQ.getFaq_info());
        holder.binding.recyclerView.setHasFixedSize(false);
        holder.mFaqChildAdapter = new FaqChildAdapter(context, parentPosition, mFAQItemClickListener, holder.binding.recyclerView );
        holder.binding.recyclerView.setAdapter(holder.mFaqChildAdapter);
        holder.binding.recyclerView.setRecycledViewPool(viewPool);
        holder.mFaqChildAdapter.mDiffer.submitList(faqInfos);
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    class FaqViewHolder extends RecyclerView.ViewHolder {
        AdapterFaqBinding binding;
        FaqChildAdapter mFaqChildAdapter;

        public FaqViewHolder(@NonNull AdapterFaqBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            adapterFaqBinding = binding;
            recyclerViewsList.add(adapterFaqBinding.recyclerView);
        }
    }

    public void updateExpendableList(int position, int childPosition) {
        data.get(position).getFaq_info().get(childPosition).setLike(true);
        data.get(position).getFaq_info().get(childPosition).setUnLike(true);
        this.notifyItemChanged(position, childPosition);
    }


    public interface FAQItemClickListener {
        void onLikeClick(String faqId);

        void onUnLikeClick(String faqId, String reason);

        void onVideoClick(String url);

        void onGroupExpend(String faqId, int position);

        void onChildLoad(String faqId, int position, int childPosition);
    }

    //this method is used for release the player
    public void release() {
        for (int postion = 0; postion < recyclerViewsList.size(); postion++) {
            recyclerViewsList.get(postion).releasePlayer();
        }
    }

    //this method is used  for  pause the Video
    public void pauseVideo() {
        for (int postion = 0; postion < recyclerViewsList.size(); postion++) {
            recyclerViewsList.get(postion).pauseVideo();
        }
    }
}
