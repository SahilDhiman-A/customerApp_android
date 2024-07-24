package com.spectra.consumer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.spectra.consumer.Activities.NotPDFActivity;
import com.spectra.consumer.R;
import com.spectra.consumer.alertDailogs.GetHelpDailog;
import com.spectra.consumer.service.model.Response.FaqInfo;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;


public class FAQExpandableListAdapter extends BaseExpandableListAdapter {

    final String TAG = "ExpandableListAdapter";
    private final Context _context;
    private final List<FaqInfo> _listDataHeader;
    private HashMap<FaqInfo, FaqInfo> _listDataChild;
    private int position = -1;
    public FAQItemClickListener mFAQItemClickListener;
    private WebView webView;

    public void notifyDataSetChanged(HashMap<FaqInfo, FaqInfo> listChildData) {
        this._listDataChild = listChildData;
        notifyDataSetChanged();
    }

    public void updateExpendableList(int position, int childPosition, int groupPosition) {
        this._listDataChild.get(_listDataHeader.get(groupPosition)).setUnLike(true);
        this._listDataChild.get(_listDataHeader.get(groupPosition)).setLike(true);
        notifyDataSetChanged();
    }


    public interface FAQItemClickListener {
        void onLikeClick(String faqId);

        void onUnLikeClick(String faqId, String reason);

        void onVideoClick(String url);

        void onGroupExpend(String faqId, int position);

        void onChildLoad(String faqId, int i, int groupPosition, int position);
    }

    public FAQExpandableListAdapter(Context context, List<FaqInfo> listDataHeader, HashMap<FaqInfo, FaqInfo> listChildData, int position) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.position = position;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public void setFAQItemClickListener(FAQItemClickListener mFAQItemClickListener) {
        this.mFAQItemClickListener = mFAQItemClickListener;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final FaqInfo faqinfo = (FaqInfo) getChild(groupPosition, childPosition);
//        mFAQItemClickListener.onChildLoad(faqinfo.get_id(), position, groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        View separator = convertView.findViewById(R.id.separator);

        separator.setVisibility(View.VISIBLE);
        if (_listDataHeader.size() - 1 == groupPosition) {
            separator.setVisibility(View.GONE);
        }

        TextView txtListChild = convertView.findViewById(R.id.expandedListItem);
        TextView link = convertView.findViewById(R.id.link);
        ImageView like = convertView.findViewById(R.id.like);
        ImageView unlike = convertView.findViewById(R.id.unlike);
        ImageView faq_image = convertView.findViewById(R.id.faq_image);

        ConstraintLayout rl_video = convertView.findViewById(R.id.rl_video);
        RelativeLayout Rl_userFeedback = convertView.findViewById(R.id.userFeedback);

        ImageView view_full = convertView.findViewById(R.id.view_full);

        String url = faqinfo.getVideo_url();
//        if(webView!=null){
//            webView.onPause();
//        }
        webView = convertView.findViewById(R.id.webView);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        if (TextUtils.isEmpty(url) && faqinfo.isIeExpended()) {
            rl_video.setVisibility(View.GONE);
        } else {
            rl_video.setVisibility(View.VISIBLE);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
//            webView.getSettings().setSupportZoom(true);
//            webView.getSettings().setBuiltInZoomControls(true);
//            webView.getSettings().setAllowFileAccess(true);
            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                }
            });

            if (faqinfo.isRequreVideoPlay()) {
                webView.loadUrl(url);
            } else {
                webView.loadUrl("");
            }
//            webView.onPause();
        }

        view_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, NotPDFActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TYPE", "video");
                _context.startActivity(intent);
            }
        });

        txtListChild.setText(faqinfo.getAnswer());

        if (!TextUtils.isEmpty(faqinfo.getImage_url())) {
            faq_image.setVisibility(View.VISIBLE);
            Glide.with(_context).load(faqinfo.getImage_url()).into(faq_image);
        } else {
            faq_image.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(faqinfo.getLink())) {
            link.setVisibility(View.VISIBLE);
            link.setText(faqinfo.getLink());
        } else {
            link.setVisibility(View.GONE);
        }

        if (TextUtils.isEmpty(faqinfo.getVideo_url())) {
            rl_video.setVisibility(View.GONE);
        }

        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFAQItemClickListener.onVideoClick(faqinfo.getVideo_url());
            }
        });


        if (faqinfo.isLike() || faqinfo.isUnLike()) {
            Rl_userFeedback.setVisibility(View.GONE);
        } else {
            Rl_userFeedback.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "getChildView: faqinfo.isLike() " + faqinfo.isLike() + "  faqinfo.isUnLike()" + faqinfo.isUnLike());

        like.setImageDrawable(_context.getResources().getDrawable(R.drawable.unselected_like));

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faqinfo.setLike(!faqinfo.isLike());
                Log.d(TAG, "Like: " + faqinfo.isLike());
                like.setImageDrawable(_context.getResources().getDrawable(R.drawable.selected_like));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Rl_userFeedback.setVisibility(View.GONE);
                    }
                }, 2000);
//                like.setVisibility(View.INVISIBLE);
                if (mFAQItemClickListener != null)
                    mFAQItemClickListener.onLikeClick(faqinfo.get_id());
            }
        });

        unlike.setImageDrawable(_context.getResources().getDrawable(R.drawable.unselected_unliked));

        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faqinfo.setUnLike(!faqinfo.isUnLike());
                Log.d(TAG, "UnLike: " + faqinfo.isUnLike());
//                faqinfo.setIeExpended(false);
                GetHelpDailog mGetHelpDailog = new GetHelpDailog(_context);
                mGetHelpDailog.setSubmitListener(new GetHelpDailog.FeedBackSubmitListener() {
                    @Override
                    public void onFeedbackSubmit(@NotNull String feedback) {
//                        unlike.setVisibility(View.INVISIBLE);
                        unlike.setImageDrawable(view.getContext().getResources().getDrawable(R.drawable.selected_unlike));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Rl_userFeedback.setVisibility(View.GONE);
                            }
                        }, 2000);
                        if (mFAQItemClickListener != null)
                            mFAQItemClickListener.onUnLikeClick(faqinfo.get_id(), feedback);
                    }
                });
                mGetHelpDailog.show();
            }
        });

        link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(faqinfo.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                _context.startActivity(intent);
            }
        });
        return convertView;
    }


    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

        FaqInfo faqInfo = (FaqInfo) getGroup(groupPosition);
        String faq_Id = faqInfo.get_id();

        faqInfo.setRequreVideoPlay(true);
        mFAQItemClickListener.onGroupExpend(faq_Id, position);
//      FaqInfo faqInfo = (FaqInfo) getChild(groupPosition, 0);
        Log.d(TAG, "onGroupExpanded: " + faqInfo.isIeExpended());
        if (!faqInfo.isIeExpended()) {
            faqInfo.setIeExpended(true);
            mFAQItemClickListener.onChildLoad(faq_Id, position, groupPosition, 0);
        }
    }


    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
        FaqInfo faqInfo = (FaqInfo) getGroup(groupPosition);
        faqInfo.setRequreVideoPlay(false);
        webView.onPause();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        Log.d(TAG, "getGroupCount: " + this._listDataHeader.size());
        return _listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final FaqInfo headerTitle = (FaqInfo) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        AppCompatImageView img_select = convertView.findViewById(R.id.img_select);
        int rotation = 0;
        if (isExpanded) {
            rotation = 180;
        }
        img_select.setRotation(rotation);
        TextView lblListHeader = convertView.findViewById(R.id.listTitle);
        lblListHeader.setText(headerTitle.getQuestion());
        lblListHeader.setTextColor(_context.getColor(isExpanded ? R.color.bitter_sweet : R.color.black));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}