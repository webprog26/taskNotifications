package com.example.webprog26.tasknotifications.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.webprog26.tasknotifications.MainActivity;
import com.example.webprog26.tasknotifications.R;

import java.util.HashMap;

/**
 * Created by webprog26 on 24.10.2016.
 */

public class NotificationUtils {

    private static final String TAG = "NotificationUtils_TAG";

    private static NotificationUtils instance;

    private static Context mContext;
    private NotificationManager mNotificationManager;
    private int lastId = 0;
    private HashMap<Integer, Notification> mNotificationMap;

    private NotificationUtils(Context context)
    {
        this.mContext = context;
        mNotificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationMap = new HashMap<>();
    }

    public static NotificationUtils getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new NotificationUtils(context);
        }

        return instance;
    }

    public int createNotification(String message)
    {
        Intent notificationIntent = new Intent(mContext, MainActivity.class);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);
            notificationBuilder.setSmallIcon(R.drawable.coffee_icon)
                    .setAutoCancel(true)
                    .setTicker(message)
                    .setContentText(message)
                    .setContentIntent(PendingIntent.getActivity(mContext, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT))
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(mContext.getResources().getString(R.string.app_name))
                    .setDefaults(Notification.DEFAULT_ALL);

        Notification notification = notificationBuilder.build();
        mNotificationManager.notify(lastId, notification);
        mNotificationMap.put(lastId, notification);

        return lastId++;
    }
}
