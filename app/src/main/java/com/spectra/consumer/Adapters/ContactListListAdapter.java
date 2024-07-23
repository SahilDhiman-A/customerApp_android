package com.spectra.consumer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.spectra.consumer.Activities.ContactListActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.service.model.Response.Contact;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListListAdapter extends RecyclerView.Adapter<ContactListListAdapter.ContactViewHolder> {
    private Context context;
    private List<Contact> contacts;


    public ContactListListAdapter(Context context, List<Contact> contacts){
        this.context=context;
        this.contacts=contacts;


    }
    public void updateList(List<Contact> contacts){
        this.contacts=contacts;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new ContactListListAdapter.ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_item, null));

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact=contacts.get(position);
        holder.tvFirstName.setText(contact.getFirstName());
        holder.tvLastName.setText(contact.getLastName());
        holder.tvEmail.setText(contact.getEmail());
        holder.tvDesignation.setText(contact.getJobTitle());
        holder.tvMobileNo.setText(contact.getMobilePhone());
        holder.cancel.setOnClickListener(view -> {
            ((ContactListActivity)context).showConfirmationDialog(contact);

        });

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFirstName)
        AppCompatTextView tvFirstName;

        @BindView(R.id.tvLastName)
        AppCompatTextView tvLastName;

        @BindView(R.id.tvEmail)
        AppCompatTextView tvEmail;

        @BindView(R.id.tvDesignation)
                AppCompatTextView tvDesignation;

        @BindView(R.id.tvMobileNo)
                AppCompatTextView tvMobileNo;
        @BindView(R.id.cancel)
        AppCompatTextView cancel;



        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
