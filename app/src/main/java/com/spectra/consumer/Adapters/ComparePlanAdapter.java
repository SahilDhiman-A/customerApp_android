package com.spectra.consumer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Activities.PlanComparisionActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.ComparePlanItem;
import com.spectra.consumer.service.model.Response.ComparePlanResponse;
import com.spectra.consumer.service.model.Response.KnowMoreItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComparePlanAdapter extends RecyclerView.Adapter<ComparePlanAdapter.CompareViewHolder> {
    private ComparePlanResponse comparePlanResponse;
    private ArrayList<ComparePlanItem> comparePlanItems;
    private Context mContext;

    public ComparePlanAdapter(Context context, ComparePlanResponse response) {
        this.mContext = context;
        this.comparePlanResponse = response;
        this.comparePlanItems = (ArrayList<ComparePlanItem>) comparePlanResponse.getResponse();

        if (comparePlanItems != null && comparePlanItems.size() > 0) {
            ComparePlanItem comparePlanItem = new ComparePlanItem();
            comparePlanItem.setPlanid("");
            comparePlanItem.setSpeed("Speed");
            comparePlanItem.setData("Data");
            comparePlanItem.setCharges("Charges");
            comparePlanItem.setFrequency("Bill Frequency");
            comparePlanItem.setSpecialBenefit("Special Benefits");
            comparePlanItems.add(0, comparePlanItem);
        }
    }

    @NonNull
    @Override
    public CompareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_item_compare_item, parent, false);
        return new CompareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompareViewHolder holder, int position) {
        ComparePlanItem comparePlanItem = comparePlanItems.get(position);
        holder.com_tv_header.setText(comparePlanItem.getPlanid());
        holder.com_tv_speed.setText(comparePlanItem.getSpeed());
        holder.com_tv_data.setText(comparePlanItem.getData());
        holder.com_tv_charges.setText(comparePlanItem.getCharges());
        holder.com_tv_frequency.setText(comparePlanItem.getFrequency());
        holder.com_tv_benefits.setText(comparePlanItem.getSpecialBenefit());

        if (position % 2 == 0) {
            holder.lay_com.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        } else {
            holder.lay_com.setBackgroundColor(ContextCompat.getColor(mContext, R.color.com_current_plan));
        }

        if (position == 1) {
            holder.tv_know_more.setVisibility(View.VISIBLE);
            holder.com_tv_header.setText("Current Plan");
            holder.tvSelectPlan.setVisibility(View.GONE);
        } else if (position == 0) {
            holder.tv_know_more.setVisibility(View.GONE);
            holder.com_tv_header.setText("");
            holder.tvSelectPlan.setVisibility(View.GONE);
        } else {
            holder.tv_know_more.setVisibility(View.VISIBLE);
            holder.tvSelectPlan.setVisibility(View.VISIBLE);
            holder.com_tv_header.setText(comparePlanItem.getDescription());
        }
        holder.tv_know_more.setOnClickListener(v -> {
            if (comparePlanItems != null && comparePlanItems.size() > position) {
                ComparePlanItem planItem = comparePlanItems.get(position);
                if (mContext instanceof PlanComparisionActivity) {
                    ((PlanComparisionActivity) mContext).callKnowMore(planItem);
                }
            }
        });
        holder.tvSelectPlan.setOnClickListener(v -> {
            if (comparePlanItems != null && comparePlanItems.size() > position) {
                ComparePlanItem planItem = comparePlanItems.get(position);
                if (mContext instanceof PlanComparisionActivity) {
                    ((PlanComparisionActivity) mContext).SelectPlan(planItem);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (comparePlanResponse != null)
            return comparePlanItems.size();
        else
            return 0;
    }

    class CompareViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.com_tv_header)
        TextView com_tv_header;

        @BindView(R.id.com_tv_speed)
        TextView com_tv_speed;

        @BindView(R.id.com_tv_data)
        TextView com_tv_data;

        @BindView(R.id.com_tv_charges)
        TextView com_tv_charges;

        @BindView(R.id.com_tv_frequency)
        TextView com_tv_frequency;
        @BindView(R.id.tvSelectPlan)
        TextView tvSelectPlan;
        @BindView(R.id.com_tv_benefits)
        TextView com_tv_benefits;

        @BindView(R.id.lay_com)
        LinearLayout lay_com;

        @BindView(R.id.tv_know_more)
        TextView tv_know_more;

        public CompareViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
