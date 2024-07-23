package com.spectra.consumer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.IvrNotificationResponse;

import java.util.List;

public class HomeBannerAdapter  extends PagerAdapter {
    private Context context;
    private List<IvrNotificationResponse> ivrNotificationDataList;

    public HomeBannerAdapter(Context context,List<IvrNotificationResponse> ivrNotificationDataList){
        this.context=context;
        this.ivrNotificationDataList=ivrNotificationDataList;
    }
    @Override
    public int getCount() {
        return ivrNotificationDataList.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem( ViewGroup container, final int position) {
        context=container.getContext();
        IvrNotificationResponse iv =ivrNotificationDataList.get(position);
        View imageLayout =  LayoutInflater.from(context).inflate(R.layout.home_banner_item, container, false);
        TextView txt_banner= imageLayout.findViewById(R.id.txt_banner);
        txt_banner.setText(iv.getMessage());
        container.addView(imageLayout);
        return imageLayout;
    }
    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }
}
