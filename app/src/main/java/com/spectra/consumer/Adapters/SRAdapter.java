package com.spectra.consumer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Fragments.SRFragment;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.SrReponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SRAdapter extends RecyclerView.Adapter<SRAdapter.SRViewholder> {

    private List<SrReponse> srDataList;
    private Context context;
    private SRFragment fragment;
    View view;
    public SRAdapter(Context context,List<SrReponse> srDataList,SRFragment fragment){
        this.context=context;
        this.srDataList=srDataList;
        this.fragment=fragment;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SRViewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SRAdapter.SRViewholder(LayoutInflater.from(context).inflate(R.layout.sr_item,null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( SRViewholder holder, int position) {
        SrReponse srData=srDataList.get(position);
        holder.txt_sr_number.setText(srData.getSrNumber());
        if(srData.getStatus().equalsIgnoreCase("Active")){
          holder.txt_status.setImageResource(R.drawable.ic_in_progress);
            holder.tvSeeMore.setOnClickListener(v -> fragment.getCheckSRDetails(srData.getSrNumber()));
            holder.tvSeeMore.setVisibility(View.VISIBLE);
        }
        else{
            holder.txt_status.setImageResource(R.drawable.ic_resolved);
            holder.tvSeeMore.setVisibility(View.GONE);
        }
        holder.txt_problem_type.setText(srData.getProblemType());

        holder.txt_prob_sub_type.setText(srData.getSubType());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy' 'hh:mm:ss a");
        Date parse=null;
        try {
            parse=sdf.parse(srData.getETR());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat = "dd-MM-yyyy";
        String form="hh:mm a";
        SimpleDateFormat my = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat seconds = new SimpleDateFormat(form, Locale.US);
        if (parse != null) {
            holder.txt_expected_resolution_time.setText(my.format(parse.getTime())+" at "+seconds.format(parse.getTime()));
        }
        if(position==srDataList.size()-1){
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 200);
            holder.llmain.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return srDataList.size();
    }

    class SRViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_sr_number)
        TextView txt_sr_number;
        @BindView(R.id.llmain)
        LinearLayout llmain;

        @BindView(R.id.txt_status)
        AppCompatImageView txt_status;
        @BindView(R.id.txt_problem_type)
        TextView txt_problem_type;
        @BindView(R.id.txt_prob_sub_type)
        TextView txt_prob_sub_type;
        @BindView(R.id.txt_expected_resolution_time)
        TextView txt_expected_resolution_time;
        @BindView(R.id.txt_expected_resolution)
        TextView txt_expected_resolution;
        @BindView(R.id.tvSeeMore)
        AppCompatTextView tvSeeMore;
        SRViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
