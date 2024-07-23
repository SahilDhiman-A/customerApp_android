package com.spectra.consumer.Adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Activities.LinkedCanLdListActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.CanResponse;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCanLinkedAdapter extends RecyclerView.Adapter<SelectCanLinkedAdapter.SelectCanViewHolder> {
    private Context context;
    private String linkedCan="";
    private List<CanResponse> userDataList;
    public SelectCanLinkedAdapter(Context context, List<CanResponse> userDataList,String linkedCan) {
        this.context = context;
        this.userDataList = userDataList;
        this.linkedCan=linkedCan;

    }
    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SelectCanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SelectCanLinkedAdapter.SelectCanViewHolder(LayoutInflater.from(context).inflate(R.layout.select_can_link, null));
    }
    public void removeID(int pos){
        userDataList.remove(pos);
        notifyDataSetChanged();
    }
    public void setLinkedCan(String linkedCan){
        this.linkedCan=linkedCan;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(SelectCanViewHolder holder, int position) {
        CanResponse userData = userDataList.get(position);
        if(linkedCan.equals(userData.getLink_canid())){
            if(!userData.isLinked()){
                holder.ivMore.setVisibility(View.GONE);
            }
            holder.ivSelected.setVisibility(View.VISIBLE);
            holder.ivMore.setOnClickListener(view -> {
                if (context instanceof LinkedCanLdListActivity) {


                    ((LinkedCanLdListActivity) context).showPopupMenu(holder.ivMore,holder.getAdapterPosition(),userData.isLinked(), true);
                }
            });
        }else {
            holder.ivSelected.setVisibility(View.INVISIBLE);
            holder.ivMore.setOnClickListener(view -> {
                if (context instanceof LinkedCanLdListActivity) {
                    ((LinkedCanLdListActivity) context).showPopupMenu(holder.ivMore,holder.getAdapterPosition(),userData.isLinked() ,false);
                }
            });
        }





        holder.txtAccount_number.setText("Account "+(position+1));
        holder.tvCanId.setText("CAN ID - "+userData.getLink_canid());
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    class SelectCanViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_can_id)
        TextView tvCanId;
        @BindView(R.id.ivSelected)
        AppCompatImageView ivSelected;
        @BindView(R.id.txtAccount_number)
        TextView txtAccount_number;
        @BindView(R.id.ivMore)
        AppCompatImageView ivMore;

        SelectCanViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
