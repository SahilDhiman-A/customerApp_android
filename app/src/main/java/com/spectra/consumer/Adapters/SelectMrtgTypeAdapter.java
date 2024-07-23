package com.spectra.consumer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Models.MrtgType;
import com.spectra.consumer.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectMrtgTypeAdapter extends RecyclerView.Adapter<SelectMrtgTypeAdapter.SelectViewHolder> {
    private Context context;
    private List<MrtgType> mrtgTypeList;
    private MrtgClicked mrtgClicked;


    public interface MrtgClicked{
        void mrtgSelected(View view, int position);
    }

    public SelectMrtgTypeAdapter(Context context,List<MrtgType> mrtgTypeList,MrtgClicked mrtgClicked){
        this.context=context;
        this.mrtgTypeList=mrtgTypeList;
        this.mrtgClicked=mrtgClicked;
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SelectMrtgTypeAdapter.SelectViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SelectMrtgTypeAdapter.SelectViewHolder(LayoutInflater.from(context).inflate(R.layout.select_can_item,null));
    }

    @Override
    public void onBindViewHolder( SelectMrtgTypeAdapter.SelectViewHolder holder, int position) {
           MrtgType mrtgType=mrtgTypeList.get(position);
           holder.txtaccount_number.setText(mrtgType.type_description);
           holder.txt_can_id.setVisibility(View.GONE);
           holder.layout_account.setTag(position);
           holder.layout_account.setOnClickListener(view -> {
               int pos= (int) view.getTag();
               mrtgClicked.mrtgSelected(view,pos);
           });


    }

    @Override
    public int getItemCount() {
        return mrtgTypeList.size();
    }

    class SelectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtAccount_number)
        TextView txtaccount_number;
        @BindView(R.id.txt_can_id)
        TextView txt_can_id;
        @BindView(R.id.layout_account)
        LinearLayout layout_account;
        SelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
