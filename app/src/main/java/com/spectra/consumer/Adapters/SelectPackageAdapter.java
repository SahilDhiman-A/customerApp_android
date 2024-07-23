package com.spectra.consumer.Adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.Response.OfferListResponse;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPackageAdapter extends RecyclerView.Adapter<SelectPackageAdapter.SelectPackageViewHolder> {
    private Context context;
    private PackageSelected packageSelected;
    private List<OfferListResponse> offerListItemList;

    public interface PackageSelected{
        void onpackageSelected(View view, int position);
    }
    public SelectPackageAdapter(Context context, List<OfferListResponse> offerListItemList,PackageSelected packageSelected){
        this.context=context;
        this.offerListItemList=offerListItemList;
        this.packageSelected=packageSelected;
    }
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SelectPackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SelectPackageAdapter.SelectPackageViewHolder(LayoutInflater.from(context).inflate(R.layout.change_plan_item,null));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SelectPackageViewHolder holder, int position) {
        OfferListResponse offerListItem=offerListItemList.get(position);
        holder.txt_plan_name.setText(offerListItem.getDescription());
        if(offerListItem.getCharges()!=null && !offerListItem.getCharges().equalsIgnoreCase("")){
            holder.txt_charges.setText(""+ Constant.Round(Float.parseFloat(offerListItem.getCharges()),2));
        }

        holder.txt_data.setText(offerListItem.getData());
        holder.txt_speed.setText(offerListItem.getSpeed());
        holder.txt_frequency.setText(offerListItem.getFrequency());
        holder.layout_select_package.setTag(position);
        holder.layout_select_package.setOnClickListener(view -> {
            int pos= (int) view.getTag();
            packageSelected.onpackageSelected(view,pos);
        });

    }

    @Override
    public int getItemCount() {
        return offerListItemList.size();
    }

    class SelectPackageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_plan_name)
        TextView txt_plan_name;
        @BindView(R.id.txt_charges)
        TextView txt_charges;
        @BindView(R.id.layout_select_package)
        AppCompatTextView layout_select_package;
        @BindView(R.id.txt_data)
        TextView txt_data;
        @BindView(R.id.txt_speed)
        TextView txt_speed;
        @BindView(R.id.txt_frequency)
        TextView txt_frequency;
        SelectPackageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
