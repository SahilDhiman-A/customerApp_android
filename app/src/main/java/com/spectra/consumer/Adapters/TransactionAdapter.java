package com.spectra.consumer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.Response.TransactionListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private Context context;
    private List<TransactionListResponse> transactionDataList;

    public TransactionAdapter(Context context, List<TransactionListResponse> transactionDataList){
        this.context=context;
        this.transactionDataList=transactionDataList;

    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new TransactionAdapter.TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.transaction_item, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder( TransactionViewHolder holder, int position) {
        TransactionListResponse transactionData=transactionDataList.get(position);
    holder.txt_transaction_number.setText(transactionData.getTransactionNo());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date transaction_date_parse=null;
        try {
            transaction_date_parse=sdf.parse(transactionData.getTransactionDate());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat my_format = new SimpleDateFormat(myFormat, Locale.US);
        if (transaction_date_parse != null) {
            holder.txt_transaction_date.setText(my_format.format(transaction_date_parse.getTime()));
        }
        holder.txt_type.setText(transactionData.getType());
    holder.txt_transaction_amount.setText(""+ Constant.Round(Float.parseFloat(transactionData.getAmount()),2));

        holder.txt_payment_mode.setText(transactionData.getPaymentMode());
        holder.txt_description.setText(transactionData.getDescription());




    }

    @Override
    public int getItemCount() {
        return transactionDataList.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_transaction_number)
        TextView txt_transaction_number;
        @BindView(R.id.txt_transaction_date)
        TextView txt_transaction_date;
        @BindView(R.id.txt_type)
        TextView txt_type;
        @BindView(R.id.txt_transaction_amount)
        TextView txt_transaction_amount;
        @BindView(R.id.txt_payment_mode)
        TextView txt_payment_mode;
        @BindView(R.id.txt_description)
        TextView txt_description;
        TransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
