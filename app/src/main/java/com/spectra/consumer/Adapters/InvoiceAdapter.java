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

import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.Response.InvoiceListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    private List<InvoiceListResponse> invoiceDataList;
    private Context context;
    private InvoiceItemClicked invoiceItemClicked;

    public InvoiceAdapter(Context context, List<InvoiceListResponse> invoiceDataList, InvoiceItemClicked invoiceItemClicked) {
        this.context = context;
        this.invoiceDataList = invoiceDataList;
        this.invoiceItemClicked = invoiceItemClicked;
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new InvoiceViewHolder(LayoutInflater.from(context).inflate(R.layout.invoice_item, null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(InvoiceAdapter.InvoiceViewHolder holder, int position) {
        InvoiceListResponse invoiceData = invoiceDataList.get(position);
        holder.invoice_number.setText(invoiceData.getDisplayInvNo());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date invoice_date_parse = null;
        Date start_date_parse = null;
        Date end_date_parse = null;
        Date due_date_parse = null;
        try {
            invoice_date_parse = sdf.parse(invoiceData.getInvoicedt());
            start_date_parse = sdf.parse(invoiceData.getStartdt());
            end_date_parse = sdf.parse(invoiceData.getEnddt());
            due_date_parse = sdf.parse(invoiceData.getDuedt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String myFormat = "dd-MM-yyyy";
        String invoiceformat = "dd/MM/yyyy";
        SimpleDateFormat my_format = new SimpleDateFormat(myFormat, Locale.US);
        if (invoice_date_parse != null) {
            holder.invoice_date.setText(my_format.format(invoice_date_parse.getTime()));
        }
        if (due_date_parse != null) {
            holder.invoice_due_date.setText(my_format.format(due_date_parse.getTime()));
        }
        SimpleDateFormat invoice_format = new SimpleDateFormat(invoiceformat, Locale.US);
        if (start_date_parse != null) {
            if (end_date_parse != null) {
                holder.invoice_period.setText(invoice_format.format(start_date_parse.getTime()) + " - " + invoice_format.format(end_date_parse.getTime()));
            }
        }
        holder.view_invoice.setTag(position);
        holder.email_invoice.setTag(position);
        holder.invoice_amount.setText("" + Constant.Round(Float.parseFloat(invoiceData.getAmount()), 2));
        holder.view_invoice.setOnClickListener(view -> {
            int pos = (int) view.getTag();
            invoiceItemClicked.Clicked(view, pos);
        });
        holder.email_invoice.setOnClickListener(view -> {
            int pos = (int) view.getTag();
            invoiceItemClicked.Clicked(view, pos);
        });
    }

    @Override
    public int getItemCount() {
        return invoiceDataList.size();
    }

    public interface InvoiceItemClicked {
        void Clicked(View view, int position);
    }

    class InvoiceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.invoice_number)
        TextView invoice_number;
        @BindView(R.id.invoice_date)
        TextView invoice_date;
        @BindView(R.id.invoice_period)
        TextView invoice_period;
        @BindView(R.id.invoice_amount)
        TextView invoice_amount;
        @BindView(R.id.invoice_due_date)
        TextView invoice_due_date;
        @BindView(R.id.view_invoice)
        AppCompatTextView view_invoice;
        @BindView(R.id.email_invoice)
        AppCompatTextView email_invoice;

        InvoiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
