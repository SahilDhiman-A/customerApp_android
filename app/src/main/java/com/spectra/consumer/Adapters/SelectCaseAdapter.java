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
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.CaseTypeResponse;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectCaseAdapter extends RecyclerView.Adapter<SelectCaseAdapter.SelectCaseViewholder> {
    private Context context;
    private List<CaseTypeResponse> caseTypeDataList;
    private CaseSelected caseSelected;


    public interface CaseSelected{
        void selected(View view, int position);
    }

    public SelectCaseAdapter(Context context, List<CaseTypeResponse> caseTypeDataList,CaseSelected caseSelected){
        this.context=context;
        this.caseTypeDataList=caseTypeDataList;
        this.caseSelected=caseSelected;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public SelectCaseViewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new SelectCaseAdapter.SelectCaseViewholder(LayoutInflater.from(context).inflate(R.layout.select_can_item,null));
    }

    @Override
    public void onBindViewHolder( SelectCaseViewholder holder, int position) {
        CaseTypeResponse caseTypeData=caseTypeDataList.get(position);
holder.txtaccount_number.setText("");
holder.txt_can_id.setTextColor(context.getResources().getColor(R.color.white));
holder.txt_can_id.setText(caseTypeData.getCaseDesc());
holder.layout_account.setTag(position);
holder.layout_account.setOnClickListener(view -> {
    int pos= (int) view.getTag();
    caseSelected.selected(view,pos);
});
    }

    @Override
    public int getItemCount() {
        return caseTypeDataList.size();
    }

    class SelectCaseViewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtAccount_number)
        TextView txtaccount_number;
        @BindView(R.id.txt_can_id)
        TextView txt_can_id;
        @BindView(R.id.layout_account)
        LinearLayout layout_account;
        SelectCaseViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
