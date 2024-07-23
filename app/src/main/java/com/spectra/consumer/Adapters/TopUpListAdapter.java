package com.spectra.consumer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.TopUpResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopUpListAdapter extends RecyclerView.Adapter<TopUpListAdapter.TopupViewHolder> {
    private Context context;
    private List<TopUpResponse> topUpResponseList;
    private  TopUpclicked topUpclicked;

    public interface TopUpclicked{
        void topUpClicked(View view, int position);
    }
    public TopUpListAdapter(Context context,List<TopUpResponse> topUpResponseList,TopUpclicked topUpclicked){
        this.context=context;
        this.topUpResponseList=topUpResponseList;
        this.topUpclicked=topUpclicked;

    }
    @NonNull
    @Override
    public TopupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new TopUpListAdapter.TopupViewHolder(LayoutInflater.from(context).inflate(R.layout.topup_item, null));

    }

    @Override
    public void onBindViewHolder(@NonNull TopupViewHolder holder, int position) {
        TopUpResponse topUpResponse=topUpResponseList.get(position);
        holder.txt_plan_name.setText(topUpResponse.getName());
        holder.txt_data.setText(topUpResponse.getData());
        holder.txt_price.setText(topUpResponse.getPrice());
        holder.txt_total.setText(topUpResponse.getTotal());
        holder.txt_tax.setText(topUpResponse.getTax());
        holder.pay.setTag(position);
        holder.pay.setOnClickListener(view -> {
            int pos= (int) view.getTag();
            topUpclicked.topUpClicked(view,pos);
        });

    }

    @Override
    public int getItemCount() {
        return topUpResponseList.size();
    }

    class TopupViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pay)
        AppCompatTextView pay;
        @BindView(R.id.txt_plan_name)
        AppCompatTextView txt_plan_name;
        @BindView(R.id.txt_price)
                AppCompatTextView txt_price;
        @BindView(R.id.txt_tax)
                AppCompatTextView txt_tax;
        @BindView(R.id.txt_total)
                AppCompatTextView txt_total;
        @BindView(R.id.txt_data)
                AppCompatTextView txt_data;
        TopupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
