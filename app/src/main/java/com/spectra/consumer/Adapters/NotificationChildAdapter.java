package com.spectra.consumer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.LoginActivity;
import com.spectra.consumer.Activities.MyAccountActivity;
import com.spectra.consumer.Activities.NotPDFActivity;
import com.spectra.consumer.Activities.NotificationArchivedActivity;
import com.spectra.consumer.Activities.NotificationLActivity;
import com.spectra.consumer.Activities.NotificationSearchActivity;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.Response.noticfication.NotificationInfoData;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import activeandroid.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.spectra.consumer.Activities.HomeActivity.canID;
import static com.spectra.consumer.Activities.NotificationLActivity.notificationSelected;
import static com.spectra.consumer.Utils.Constant.getEvent;

public class NotificationChildAdapter extends RecyclerView.Adapter<NotificationChildAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<NotificationInfoData> notificationInfoData;
    private boolean isEdit, isArchive, isSearch;
    AlertDialog dial;
    int select = R.drawable.ic_check;
    int udeSelect = R.drawable.ic_unchecked;

    public NotificationChildAdapter(Context context, ArrayList<NotificationInfoData> notificationInfoData, boolean isEdit, boolean isArchive, boolean isSearch) {
        this.context = context;
        this.notificationInfoData = notificationInfoData;
        notificationSelected.clear();
        this.isEdit = isEdit;
        this.isArchive = isArchive;
        this.isSearch = isSearch;
    }

    public void updateList(ArrayList<NotificationInfoData> notificationInfoData, boolean isEdit) {
        this.isEdit = isEdit;
        this.notificationInfoData = notificationInfoData;
        notificationSelected.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item, parent, false);
        return new NotificationChildAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NotificationInfoData infoData = notificationInfoData.get(holder.getAdapterPosition());
        holder.textTitle.setText(infoData.getContent().getTitle());
        holder.textDec.setText(infoData.getContent().getBody());
        holder.ivDelete.setOnClickListener(view -> {
            if (isProgress()) {
                notificationSelected = new ArrayList<>();
                notificationSelected.add(infoData);
                if (!isArchive) {
                    ((NotificationLActivity) context).deleteNotification(notificationSelected, holder.getAdapterPosition());
                } else {
                    ((NotificationArchivedActivity) context).deleteNotification(notificationSelected, holder.getAdapterPosition());
                }
            }

        });
        holder.viewBackground.setVisibility(View.VISIBLE);
        if (isArchive) {
            holder.ivArchived.setVisibility(View.GONE);
        } else {
            if (isSearch) {
                holder.viewBackground.setVisibility(View.GONE);
            }
            holder.ivArchived.setVisibility(View.VISIBLE);
        }
        if (isEdit) {
            holder.cb_compare.setVisibility(View.VISIBLE);
            holder.ivHolder.setVisibility(View.GONE);
            if (infoData.getIs_read()) {
                holder.dragTtem.setBackgroundResource(R.drawable.not_white);
            } else {
                holder.dragTtem.setBackgroundResource(R.drawable.not_selected);
            }
            if (infoData.getIsSelect()) {
                holder.cb_compare.setImageResource(select);
            } else {
                holder.cb_compare.setImageResource(udeSelect);
            }

            holder.viewForeground.setOnClickListener(v -> {
                if (isProgress()) {
                    if (infoData.getIsSelect()) {
                        holder.cb_compare.setImageResource(udeSelect);
                        infoData.setIsSelect(false);
                        notificationInfoData.get(holder.getAdapterPosition()).setIsSelect(false);
                    } else {
                        infoData.setIsSelect(true);
                        notificationInfoData.get(holder.getAdapterPosition()).setIsSelect(true);
                        holder.cb_compare.setImageResource(select);
                    }
                }
            });

            int color = R.color.black;
            holder.textTitle.setTextColor(ContextCompat.getColor(context, color));
            holder.textDec.setTextColor(ContextCompat.getColor(context, color));
        } else {
            infoData.setIsSelect(false);
            notificationInfoData.get(holder.getAdapterPosition()).setIsSelect(false);
            if (!infoData.getIs_read()) {
                int color = R.color.white;
                holder.textTitle.setTextColor(ContextCompat.getColor(context, color));
                holder.textDec.setTextColor(ContextCompat.getColor(context, color));
                holder.dragTtem.setBackgroundResource(R.drawable.not_red);
            } else {

                holder.dragTtem.setBackgroundResource(R.drawable.not_gray);
                int color = R.color.black;
                holder.textTitle.setTextColor(ContextCompat.getColor(context, color));
                holder.textDec.setTextColor(ContextCompat.getColor(context, color));
            }
            holder.ivHolder.setVisibility(View.VISIBLE);
            holder.cb_compare.setVisibility(View.GONE);
            String type1 = infoData.getType().toLowerCase();
            if (type1.equals("offer") || type1.equals("spectra payment remainder")) {
                holder.ivHolder.setImageResource(R.drawable.ic_discount);
            } else {
                if (type1.equals("payment") || type1.equals("spectra disconnection notice")) {
                    holder.ivHolder.setImageResource(R.drawable.ic_credit_card);
                } else {
                    holder.ivHolder.setImageResource(R.drawable.ic_megaphone);
                }
            }


            holder.viewForeground.setOnClickListener(view -> {
                if (isProgress()) {
                    notificationSelected.clear();
                    notificationSelected.add(infoData);
                    int type = 1;
                    if (infoData.getCan_id().equals(canID)) {
                        type = getEvent(infoData.getType());
                        if (type == 1) {
                            showConfirmationDialog(infoData);
                        }
                    } else {
                        onUpdateNeeded(infoData.getCan_id());
                    }
                    final boolean isRead = infoData.getIs_read();
                    if (!isArchive) {
                        if (isSearch) {
                            ((NotificationSearchActivity) context).readNotification(notificationSelected, isRead, holder.getAdapterPosition(), type);
                        } else {
                            ((NotificationLActivity) context).readNotification(notificationSelected, isRead, holder.getAdapterPosition(), type);
                        }
                    } else {
                        ((NotificationArchivedActivity) context).readNotification(notificationSelected, isRead, holder.getAdapterPosition(), type);
                    }
                    infoData.setIs_read(true);
                    holder.dragTtem.setBackgroundResource(R.drawable.not_gray);
                    int color = R.color.black;
                    holder.textTitle.setTextColor(ContextCompat.getColor(context, color));
                    holder.textDec.setTextColor(ContextCompat.getColor(context, color));
                    notificationInfoData.get(position).setIs_read(true);

                }
            });

        }


        if (holder.getAdapterPosition() == 0) {
            holder.txtEdit.setVisibility(View.VISIBLE);
            holder.txtDate.setVisibility(View.VISIBLE);
            if (!isEdit) {
                holder.txtEdit.setText("Edit");
            } else {
                holder.txtEdit.setText("Cancel");
            }
            holder.txtEdit.setOnClickListener(v -> {
                if (isProgress()) {
                    isEdit = !isEdit;
                    ((NotificationLActivity) context).editClick(isEdit);
                }
            });
        } else {
            holder.txtEdit.setVisibility(View.GONE);
        }


        if (!isArchive && !isSearch) {
            if (holder.getAdapterPosition() == 0) {
                holder.txtEdit.setVisibility(View.VISIBLE);
                holder.txtDate.setVisibility(View.VISIBLE);
                if (!isEdit) {
                    holder.txtEdit.setText("Edit");
                } else {
                    holder.txtEdit.setText("Cancel");
                }
                holder.txtEdit.setOnClickListener(v -> {
                    if (isProgress()) {
                        isEdit = !isEdit;
                        ((NotificationLActivity) context).editClick(isEdit);
                    }
                });
            } else {
                holder.txtEdit.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(infoData.getDate())) {
                holder.txtDate.setText(infoData.getDate());
                holder.txtDate.setVisibility(View.VISIBLE);
            } else {
                holder.txtDate.setVisibility(View.GONE);
            }
        } else {
            holder.txtEdit.setVisibility(View.GONE);
            holder.txtDate.setVisibility(View.GONE);
        }
        holder.ivArchived.setOnClickListener(view -> {
            if (isProgress()) {
                notificationSelected.clear();
                notificationSelected.add(infoData);
                ((NotificationLActivity) context).archivedNotification(notificationSelected, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return notificationInfoData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewForeground;
        public LinearLayout viewBackground;

        @BindView(R.id.ivDelete)
        AppCompatImageView ivDelete;

        @BindView(R.id.ivArchived)
        AppCompatImageView ivArchived;

        @BindView(R.id.textDec)
        AppCompatTextView textDec;
        @BindView(R.id.textTitle)
        AppCompatTextView textTitle;
        @BindView(R.id.cb_compare)
        AppCompatImageView cb_compare;

        @BindView(R.id.ivHolder)
        AppCompatImageView ivHolder;

        @BindView(R.id.drag_item)
        CardView dragTtem;
        @BindView(R.id.txtDate)
        AppCompatTextView txtDate;

        @BindView(R.id.txtEdit)
        AppCompatTextView txtEdit;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            viewBackground = itemView.findViewById(R.id.right_view);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }


    public void showConfirmationDialog(NotificationInfoData notificationInfoData) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.not_topup, null);
        AppCompatTextView tvTitle = v.findViewById(R.id.tvTitle);
        AppCompatImageView img_cross = v.findViewById(R.id.img_cross);

        AppCompatImageView ivImage = v.findViewById(R.id.ivImage);
        AppCompatTextView textDisc = v.findViewById(R.id.textDisc);
        AppCompatTextView viewPDF = v.findViewById(R.id.viewPDF);
        tvTitle.setText(notificationInfoData.getContent().getTitle());
        textDisc.setText(notificationInfoData.getNotData().getOrderInfo().getDetailedDescription());
        dialog.setView(v);
        dialog.setCancelable(true);
        dial = dialog.create();
        dial.show();
        if (TextUtils.isEmpty(notificationInfoData.getImage_url())) {
            viewPDF.setVisibility(View.VISIBLE);
            ivImage.setVisibility(View.GONE);
            viewPDF.setOnClickListener(v12 -> {
                Intent intent = new Intent(context, NotPDFActivity.class);

                if (!TextUtils.isEmpty(notificationInfoData.getPdf_url())) {
                    intent.putExtra("URL", notificationInfoData.getPdf_url());
                    intent.putExtra("TYPE", "PDF");
                } else {
                    intent.putExtra("URL", notificationInfoData.getNotData().getVideoUrl());
                    intent.putExtra("TYPE", "VIDEO");
                }
                dial.cancel();
                context.startActivity(intent);
            });
            if (TextUtils.isEmpty(notificationInfoData.getPdf_url()) && TextUtils.isEmpty(notificationInfoData.getNotData().getVideoUrl())) {
                viewPDF.setVisibility(View.GONE);
                ivImage.setVisibility(View.GONE);
            }
        } else {
            viewPDF.setVisibility(View.GONE);
            ivImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(notificationInfoData.getImage_url()).into(ivImage);
        }
        img_cross.setOnClickListener(v1 -> dial.cancel());
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void onUpdateNeeded(String can_id) {
        String message = MessageFormat.format("This notification is associated with another CAN ID: {0}. You need to switch CAN ID to proceed.", can_id);
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            View v = LayoutInflater.from(context).inflate(R.layout.switch_dialog, null);
            AppCompatTextView back_to_sr = v.findViewById(R.id.title);
            back_to_sr.setText(message);
            TextView txtCancel = v.findViewById(R.id.cancel);
            TextView txtSwitch = v.findViewById(R.id.switchb);
            txtCancel.setOnClickListener(v12 -> dial.dismiss());
            txtSwitch.setOnClickListener(v1 -> {
                dial.dismiss();
                callAccountScreen();
            });
            dialog.setView(v);
            dialog.setCancelable(true);
            dial = dialog.create();
            dial.show();
            back_to_sr.setOnClickListener(view -> {

            });
            dial.show();
            Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void callAccountScreen() {
        Intent intent = new Intent(context, MyAccountActivity.class);
        context.startActivity(intent);

    }


    public ArrayList<NotificationInfoData> getGetArray() {
        notificationSelected = new ArrayList<>();
        for (NotificationInfoData data : notificationInfoData) {
            if (data.getIsSelect()) {
                notificationSelected.add(data);
            }
        }
        return notificationSelected;
    }

    public boolean isProgress() {
        boolean isCall = true;
        try {
            if (isArchive) {
                try {
                    isCall = ((NotificationArchivedActivity) context).isProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (isSearch) {
                try {
                    isCall = ((NotificationSearchActivity) context).isProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    isCall = ((NotificationLActivity) context).isProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCall;

    }


}
