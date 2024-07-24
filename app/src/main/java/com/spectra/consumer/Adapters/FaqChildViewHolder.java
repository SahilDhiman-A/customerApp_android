package com.spectra.consumer.Adapters;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.exoplayer2.Player;
import com.spectra.consumer.databinding.AdapterFaqChildBinding;
import com.spectra.consumer.service.model.Response.FaqInfo;

 public class FaqChildViewHolder extends RecyclerView.ViewHolder {

     public AdapterFaqChildBinding binding;
     public RequestManager requestManager;

    public FaqChildViewHolder(@NonNull AdapterFaqChildBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                binding.child.setVisibility(binding.child.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

            }
        });
    }


    public void onBind(FaqInfo faqInfo, RequestManager requestManager) {
        this.requestManager = requestManager;
        binding.getRoot().setTag(this);

//        this.requestManager
//                .load(faqInfo.getImage_url())
//                .into(binding.thumbnail);
//

        binding.expandedListItem.setText(faqInfo.getAnswer());
        binding.link.setText(faqInfo.getLink());
        binding.link.setVisibility(TextUtils.isEmpty(faqInfo.getLink()) ? View.GONE : View.VISIBLE);
        binding.faqImage.setVisibility(TextUtils.isEmpty(faqInfo.getImage_url()) ? View.GONE : View.VISIBLE);
//        Glide.with(context).load(faqInfo.getImage_url()).into(binding.faqImage);
    }

}


