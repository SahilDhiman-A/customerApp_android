package com.spectra.consumer.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.spectra.consumer.Activities.HomeActivity;
import com.spectra.consumer.Activities.LoginActivity;
import com.spectra.consumer.Activities.NotificationLActivity;
import com.spectra.consumer.Activities.SplashActivity;
import com.spectra.consumer.Activities.TrackActivity;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.Utils.SiUtils;

import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;

import static android.app.Notification.BADGE_ICON_SMALL;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.getEvent;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onNewToken(String token) {
        Log.e(TAG, token);
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            String title = notification.getTitle();
            String body = notification.getBody();
            String image = "";
            try {
                if (notification.getImageUrl() != null) {
                    image = notification.getImageUrl().toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (remoteMessage.getData().size() > 0) {
                try {
                    Map<String, String> data = remoteMessage.getData();
                    JSONObject json = new JSONObject(Objects.requireNonNull(data.get("order_info")));
                    try {
                        if (TextUtils.isEmpty(image)) {
                            image = data.get("image_url");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String type = json.getString("type");
                    String id = data.get("_id");
                    String can_id = data.get("can_id");
                    handleDataMessage(id, title, body, type, image,can_id);
                } catch (Exception e) {
                    Log.e(TAG, "Exception: " + e.getMessage());
                }
            }
        }

    }

    private void handleDataMessage(String id, String title, String message, String type, String image,String can_id) {
        try {
            if (!TextUtils.isEmpty(title)) {
                try {
                    getBitmapAsyncAndDoWork(id, image, type, title, message,can_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        }
    }

    private Intent readAction(String event,String canId,String id) {
        Intent intent;

        intent = new Intent(getApplicationContext(), HomeActivity.class);
        int type = getEvent(event);
        if (type != 1) {
            if (type == 2) {
                intent.putExtra("cameFromScreen", "SR_TAB");
            }
            if (type == 3) {
                intent.putExtra("cameFromScreen", "INVOICE_TAB");
            }
            intent.putExtra("CAN_ID", canId);
            intent.putExtra("NOT_ID", id);
            return intent;
        } else {
            intent = new Intent(getApplicationContext(), NotificationLActivity.class);

        }
        intent.putExtra("CAN_ID", canId);
        intent.putExtra("NOT_ID", id);
        return intent;
    }


    private void sendNotification(String id, Bitmap bitmap, String type, String title, String message ,String can_id) {
        int notificationId = 0;
        try {
            int min = 50;
            int max = 100;
            notificationId = (int) (Math.random() * (max - min + 1) + min);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent;
        //
        try {
            intent = readAction(type,can_id,id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine("Spectra");
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, "98765")
                            .setSmallIcon(R.drawable.notification_push)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);
            if (bitmap != null) {
                notificationBuilder.setStyle(
                        new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("98765",
                        "Spectra",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setShowBadge(true);
                notificationManager.createNotificationChannel(channel);
            }


            notificationManager.notify(notificationId, notificationBuilder.build());


            try {
                Intent myIntent = new Intent("FBRIMAGE");
                this.sendBroadcast(myIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {

        }
    }

    private void getBitmapAsyncAndDoWork(String id, String imageUrl, String type, String title, String message,String can_id) {

        if (TextUtils.isEmpty(imageUrl) || !imageUrl.contains("http")) {
            sendNotification(id, null, type, title, message,can_id);
        } else {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(imageUrl)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            sendNotification(id, resource, type, title, message,can_id);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            sendNotification(id, null, type, title, message,can_id);
                        }
                    });
        }

    }
}