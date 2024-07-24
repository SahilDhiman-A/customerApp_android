package com.spectra.consumer.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.KnowMoreItems;
import com.spectra.consumer.service.model.Response.KnowMoreResponse;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowMoreAdapter extends RecyclerView.Adapter<KnowMoreAdapter.KnowMoreViewHolder>{

    private KnowMoreResponse knowMoreResponse;
    private ArrayList<KnowMoreItems> knowMoreItems;
    private Context mContext;

    public KnowMoreAdapter(Context context, KnowMoreResponse response){
        this.mContext = context;
        this.knowMoreResponse = response;
        this.knowMoreItems = (ArrayList<KnowMoreItems>) knowMoreResponse.getResponse().getContentText();
    }

    @NonNull
    @Override
    public KnowMoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_know_more_item, parent, false);
        return new KnowMoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KnowMoreViewHolder holder, int position) {
        KnowMoreItems knowMoreItem = knowMoreItems.get(position);
        holder.km_header.setText(knowMoreItem.getTitle());
        holder.km_des.setText(knowMoreItem.getContent());
        holder.km_icon.setBackground(getImageResouse(knowMoreItem.getIconId()));
    }

    @Override
    public int getItemCount() {
        if(knowMoreItems!=null)
            return knowMoreItems.size();
        else
            return 0;
    }

    class KnowMoreViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.km_icon)
        ImageView km_icon;

        @BindView(R.id.km_header)
        TextView km_header;

        @BindView(R.id.km_des)
        TextView km_des;

        public KnowMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private Drawable getImageResouse(String id){

        switch (id) {
            case "1":
                return mContext.getResources().getDrawable(R.drawable.spectra_1);

            case "2":
                return mContext.getResources().getDrawable(R.drawable.spectra_2);

            case "3":
                return mContext.getResources().getDrawable(R.drawable.spectra_3);

            case "4":
                return mContext.getResources().getDrawable(R.drawable.spectra_4);

            case "5":
                return mContext.getResources().getDrawable(R.drawable.spectra_5);

            case "6":
                return mContext.getResources().getDrawable(R.drawable.spectra_6);

            case "7":
                return mContext.getResources().getDrawable(R.drawable.spectra_7);

            case "8":
                return mContext.getResources().getDrawable(R.drawable.spectra_8);

            case "9":
                return mContext.getResources().getDrawable(R.drawable.spectra_9);

            case "10":
                return mContext.getResources().getDrawable(R.drawable.spectra_10);


            case "11":
                return mContext.getResources().getDrawable(R.drawable.spectra_11);

            case "12":
                return mContext.getResources().getDrawable(R.drawable.spectra_12);

            case "13":
                return mContext.getResources().getDrawable(R.drawable.spectra_13);

            case "14":
                return mContext.getResources().getDrawable(R.drawable.spectra_14);

            case "15":
                return mContext.getResources().getDrawable(R.drawable.spectra_15);

            case "16":
                return mContext.getResources().getDrawable(R.drawable.spectra_16);

            case "17":
                return mContext.getResources().getDrawable(R.drawable.spectra_17);

            case "18":
                return mContext.getResources().getDrawable(R.drawable.spectra_18);

            case "19":
                return mContext.getResources().getDrawable(R.drawable.spectra_19);

            case "20":
                return mContext.getResources().getDrawable(R.drawable.spectra_20);


            case "21":
                return mContext.getResources().getDrawable(R.drawable.spectra_21);

            case "22":
                return mContext.getResources().getDrawable(R.drawable.spectra_22);

            case "23":
                return mContext.getResources().getDrawable(R.drawable.spectra_23);

            case "24":
                return mContext.getResources().getDrawable(R.drawable.spectra_24);

            case "25":
                return mContext.getResources().getDrawable(R.drawable.spectra_25);

            case "26":
                return mContext.getResources().getDrawable(R.drawable.spectra_26);

            case "27":
                return mContext.getResources().getDrawable(R.drawable.spectra_27);

            case "28":
                return mContext.getResources().getDrawable(R.drawable.spectra_28);

            case "29":
                return mContext.getResources().getDrawable(R.drawable.spectra_29);

            case "30":
                return mContext.getResources().getDrawable(R.drawable.spectra_30);


            case "31":
                return mContext.getResources().getDrawable(R.drawable.spectra_31);


            case "32":
                return mContext.getResources().getDrawable(R.drawable.spectra_32);


            default:
                return mContext.getResources().getDrawable(R.drawable.spectra_1);
        }

    }

}
