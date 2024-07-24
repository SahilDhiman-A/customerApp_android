package com.spectra.consumer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Activities.MyPlanActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.TopUpResponse;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTopUpAdapter extends RecyclerView.Adapter<MyTopUpAdapter.TopupViewHolder> {

    private Context context;
    private List<TopUpResponse> topUpResponseList;
    private boolean isConsumed=true;

    public MyTopUpAdapter(Context context, List<TopUpResponse> topUpResponseList, boolean isConsumed){
        this.context=context;
        this.isConsumed=isConsumed;
        this.topUpResponseList=topUpResponseList;
    }

    public void updateList(List<TopUpResponse> topUpResponseList){
        this.topUpResponseList=topUpResponseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new MyTopUpAdapter.TopupViewHolder(LayoutInflater.from(context).inflate(R.layout.my_topup, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TopupViewHolder holder, int position) {
        final TopUpResponse topUpResponse=topUpResponseList.get(holder.getAdapterPosition());

        if(topUpResponse.isDeactivateFlag()){
            holder.deactivate.setVisibility(View.VISIBLE);
        }else{
            holder.deactivate.setVisibility(View.GONE);
        }

        if(topUpResponse.getType().equalsIgnoreCase("RC")){
            holder.txt_top_type.setText("Current Auto Top-Up");
        }else{
            holder.txt_top_type.setText("Current Flexi Top-Upp");
        }

        holder.txt_name.setText(topUpResponse.getTopup_name());
        holder.txt_price.setText("₹ " +topUpResponse.getPrice()+" + 18% Taxes");
        holder.deactivate.setOnClickListener(view -> {
            if(context instanceof MyPlanActivity) {
                ((MyPlanActivity) context).confirm_change_dialog(topUpResponse.getTopup_id(),topUpResponse.getType()); ;
            }
        });
        if(isConsumed){
            holder.tvType.setVisibility(View.VISIBLE);
            holder.txt_type.setVisibility(View.VISIBLE);
            holder.txt_type.setText(topUpResponse.getType());
            holder.deactivate.setVisibility(View.GONE);
        }else {
            holder.tvType.setVisibility(View.GONE);
            holder.txt_type.setVisibility(View.GONE);
        }
        holder.txt_data.setText(topUpResponse.getData_volume());
    }
    @Override
    public int getItemCount() {
        return topUpResponseList.size();
    }

    class TopupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_top_type)
        TextView txt_top_type;

        @BindView(R.id.txt_name)
        AppCompatTextView txt_name;
        @BindView(R.id.txt_price)
        AppCompatTextView txt_price;
        @BindView(R.id.deactivate)
        AppCompatTextView deactivate;
        @BindView(R.id.txt_data)
        AppCompatTextView txt_data;
        @BindView(R.id.tvIdVolume)
        AppCompatTextView tvIdVolume;
        @BindView(R.id.txt_type)
        AppCompatTextView txt_type;
        @BindView(R.id.tvType)
        AppCompatTextView tvType;
        TopupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
