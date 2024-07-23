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

import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCanAdapter extends RecyclerView.Adapter<SelectCanAdapter.SelectCanViewHolder> {

    private Context context;
    private List<LoginMobileResponse> userDataList;
     private CanIdSelected canIdSelected;


    public interface CanIdSelected{
        void Clicked(View view, int position);
    }

    public SelectCanAdapter(Context context, List<LoginMobileResponse> userDataList, CanIdSelected canIdSelected){
        this.context=context;
        this.userDataList=userDataList;
        this.canIdSelected=canIdSelected;
    }



    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SelectCanViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SelectCanAdapter.SelectCanViewHolder(LayoutInflater.from(context).inflate(R.layout.select_can_item,null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SelectCanViewHolder holder, int position) {
        LoginMobileResponse userData=userDataList.get(position);
int position_account=position+1;
holder.txtaccount_number.setText("Account " +position_account);
holder.txt_can_id.setText("CAN ID - "+userData.getCANId());
holder.layout_account.setTag(position);
        holder.layout_account.setOnClickListener(view -> {
            int pos= (int) view.getTag();
            canIdSelected.Clicked(view,pos);
        });
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    class SelectCanViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtAccount_number)
        TextView txtaccount_number;
        @BindView(R.id.txt_can_id)
        TextView txt_can_id;
        @BindView(R.id.layout_account)
        LinearLayout layout_account;

        SelectCanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
