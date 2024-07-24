package com.spectra.consumer.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spectra.consumer.Activities.NotPDFActivity;
import com.spectra.consumer.Activities.SpectraApplication;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.alertDailogs.GetHelpDailog;
import com.spectra.consumer.customView.VideoPlayerRecyclerView;
import com.spectra.consumer.databinding.AdapterFaqChildBinding;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Response.FaqInfo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FaqChildAdapter extends RecyclerView.Adapter<FaqChildAdapter.FaqChildViewHolder> {

    final private Context context;
    public FaqAdapter.FAQItemClickListener mFAQItemClickListener;
    public static final String TAG = "FaqChildAdapter";
    private int currentExpandedPosition = -1;
    private int previousExpandedPosition = -1;
    private int parentPosition;
    private VideoPlayerRecyclerView recyclerView;
    private String CanId;

    public FaqChildAdapter(Context context, int parentPosition, FaqAdapter.FAQItemClickListener mFAQItemClickListener, VideoPlayerRecyclerView recyclerView) {
        this.parentPosition = parentPosition;
        this.mFAQItemClickListener = mFAQItemClickListener;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public static final DiffUtil.ItemCallback<FaqInfo> DIFF_CALLBACK = new DiffUtil.ItemCallback<FaqInfo>() {

        @Override
        public boolean areItemsTheSame(@NonNull FaqInfo oldFaqInfo, @NonNull FaqInfo newFaqInfo) {
            return oldFaqInfo.get_id().equals(newFaqInfo.get_id());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FaqInfo oldFaqInfo, @NonNull FaqInfo newFaqInfo) {
            return oldFaqInfo.equals(newFaqInfo);
        }

    };

    public final AsyncListDiffer<FaqInfo> mDiffer = new AsyncListDiffer(this, DIFF_CALLBACK);


    @NonNull
    @Override
    public FaqChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqChildViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_faq_child, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqChildViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            if ((Boolean) payloads.get(0)) {
                FaqInfo faqinfo = mDiffer.getCurrentList().get(position);
                if (faqinfo.isLike() || faqinfo.isUnLike()) {
                    holder.binding.userFeedback.setVisibility(View.GONE);
                } else {
                    holder.binding.userFeedback.setVisibility(View.VISIBLE);
                }
            } else {
                holder.binding.imgSelect.setRotation(0);
                holder.binding.child.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FaqChildViewHolder holder, int position) {
        FaqInfo faqinfo = mDiffer.getCurrentList().get(position);


        Log.d(TAG, "onBindViewHolder: " + faqinfo);

        holder.binding.listTitle.setText(faqinfo.getQuestion());

        holder.binding.expandedListItem.setText(faqinfo.getAnswer());

        holder.binding.link.setText(faqinfo.getLink());
        if(TextUtils.isEmpty(faqinfo.getLink())){
            holder.binding.link.setVisibility(View.GONE);
        }else{
            holder.binding.link.setVisibility(faqinfo.getLink().equalsIgnoreCase("null") ? View.GONE:View.VISIBLE);
        }


        holder.binding.faqImage.setVisibility(TextUtils.isEmpty(faqinfo.getImage_url()) ? View.GONE : View.VISIBLE);

        Glide.with(context).load(faqinfo.getImage_url()).into(holder.binding.faqImage);

        holder.binding.child.setVisibility(View.GONE);
        holder.binding.imgSelect.setRotation(0);

        holder.binding.listTitle.setTextColor(context.getResources().getColor(R.color.black));

        if (currentExpandedPosition != -1 && currentExpandedPosition == position) {
            holder.binding.child.setVisibility(View.VISIBLE);
            holder.binding.imgSelect.setRotation(180);
        }

        holder.binding.group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUserData Data = DroidPrefs.get(context, Constant.CurrentuserKey, CurrentUserData.class);
                CanId = Data.CANId;
                SpectraApplication.getInstance().postEvent(Constant.EVENT.CATEGORY_GET_HELP+ "- FAQ" , "faq_topic_clicked", faqinfo.getQuestion(), CanId);
                previousExpandedPosition = currentExpandedPosition;
                recyclerView.pauseVideo();
                if (holder.binding.child.getVisibility() == View.GONE) {
                    if (!holder.isAlreadyExpanded) {
                        holder.isAlreadyExpanded = true;
                        mFAQItemClickListener.onChildLoad(faqinfo.get_id(), parentPosition, holder.getAdapterPosition());
                    }
                    currentExpandedPosition = holder.getAdapterPosition();
                    mFAQItemClickListener.onGroupExpend(faqinfo.get_id(), holder.getAdapterPosition());
                    holder.binding.child.setVisibility(View.VISIBLE);
                    holder.binding.imgSelect.setRotation(180);
                    holder.binding.listTitle.setTextColor(context.getResources().getColor(R.color.bitter_sweet));
                    recyclerView.playVideo(holder, currentExpandedPosition);
                } else {
                    holder.binding.imgSelect.setRotation(0);
                    holder.binding.child.setVisibility(View.GONE);
                    holder.binding.listTitle.setTextColor(context.getResources().getColor(R.color.black));
                }
                if (previousExpandedPosition != -1 && previousExpandedPosition != currentExpandedPosition) {
//                  notifyItemChanged(previousExpandedPosition,false);
                    notifyItemChanged(previousExpandedPosition);
                }
            }
        });


        if (TextUtils.isEmpty(faqinfo.getVideo_url())) {
            holder.binding.rlVideo.setVisibility(View.GONE);
        } else {
            holder.binding.rlVideo.setVisibility(View.VISIBLE);
        }

        holder.binding.like.setImageDrawable(context.getResources().getDrawable(R.drawable.unselected_like));
        holder.binding.unlike.setImageDrawable(context.getResources().getDrawable(R.drawable.unselected_unliked));
        if (faqinfo.isLike() || faqinfo.isUnLike()) {
            holder.binding.userFeedback.setVisibility(View.GONE);
        } else {
            holder.binding.userFeedback.setVisibility(View.VISIBLE);
        }

        holder.binding.separator.setVisibility(position == mDiffer.getCurrentList().size() - 1 ? View.GONE : View.VISIBLE);


        holder.binding.playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        holder.binding.playPause.setImageDrawable(context.getResources().getDrawable(recyclerView.isPlaying() ? R.drawable.exo_controls_pause : R.drawable.exo_controls_play));
                        if (recyclerView.isPlaying()) {
                            recyclerView.pauseVideo();
                        } else {
                            recyclerView.playVideo();
                        }

                        holder.binding.playPause.setVisibility(recyclerView.isPlaying() ? View.GONE : View.VISIBLE);
                    }
                }, 2000);

            }
        });

        holder.binding.rlVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.binding.playPause.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.playPause.setVisibility(recyclerView.isPlaying() ? View.GONE : View.VISIBLE);
                    }
                }, 1000);

            }
        });

        holder.binding.viewFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotPDFActivity.class);
                intent.putExtra("URL", faqinfo.getVideo_url());
                intent.putExtra("TYPE", "VIDEO");
                context.startActivity(intent);
            }
        });

        holder.binding.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faqinfo.setLike(!faqinfo.isLike());
                Log.d(TAG, "Like: " + faqinfo.isLike());
                holder.binding.like.setImageDrawable(context.getResources().getDrawable(R.drawable.selected_like));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.binding.userFeedback.setVisibility(View.GONE);
                    }
                }, 2000);
                if (mFAQItemClickListener != null) {
                    mFAQItemClickListener.onLikeClick(faqinfo.get_id());
                }
            }
        });

        holder.binding.unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faqinfo.setUnLike(!faqinfo.isUnLike());
                recyclerView.pauseVideo();
                Log.d(TAG, "UnLike: " + faqinfo.isUnLike());
                GetHelpDailog mGetHelpDailog = new GetHelpDailog(context);
                mGetHelpDailog.setSubmitListener(new GetHelpDailog.FeedBackSubmitListener() {
                    @Override
                    public void onFeedbackSubmit(@NotNull String feedback) {
                        holder.binding.unlike.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.selected_unlike));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                holder.binding.userFeedback.setVisibility(View.GONE);
                                recyclerView.playVideo();
                            }
                        }, 2000);
                        if (mFAQItemClickListener != null) {
                            mFAQItemClickListener.onUnLikeClick(faqinfo.get_id(), feedback);
                        }
                    }
                });
                mGetHelpDailog.show();
            }
        });

        holder.binding.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(faqinfo.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }


    //use to update the list  feedback
    public void updateList(int position, int childPosition, int groupPosition) {
        mDiffer.getCurrentList().get(childPosition).setLike(true);
        mDiffer.getCurrentList().get(childPosition).setUnLike(true);
        notifyItemChanged(childPosition, true);
    }

    public class FaqChildViewHolder extends RecyclerView.ViewHolder {

        AdapterFaqChildBinding binding;
        boolean isAlreadyExpanded = false;

        public FaqChildViewHolder(@NonNull AdapterFaqChildBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
