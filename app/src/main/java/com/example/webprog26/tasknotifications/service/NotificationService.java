package com.example.webprog26.tasknotifications.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.webprog26.tasknotifications.EditActivity;
import com.example.webprog26.tasknotifications.MainActivity;
import com.example.webprog26.tasknotifications.R;

import java.util.ArrayList;

/**
 * Created by webprog26 on 24.10.2016.
 */

public class NotificationService extends IntentService {

    private static final String TAG = "NotificationService_TAG";

    //private static final String GROUP_KEY_COFFEE = "group_key_coffee";
    private static final int NOTIFICATION_ID = 101;

    private final IBinder mBinder = new LocalBinder();

    private String message;

    public NotificationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //If message not defined, it  will be taken from Iintent extras
        if(message == null)
        {
            message = intent.getStringExtra(MainActivity.NOTIFICATION_MESSAGE);
        }

        sendNotification(message);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    /**
     * Sends notifications
     * @param message
     */
    private void sendNotification(String message)
    {
        Log.i("Start", "notification");


        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(getResources().getString(R.string.app_name));
        mBuilder.setContentText(message);
        mBuilder.setTicker(message);
        mBuilder.setSmallIcon(R.drawable.coffee_icon);
        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setAutoCancel(true);



        Intent resultIntent = new Intent(this, EditActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(EditActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    /**
     * Sets message received from EditActivity while binding to be notification-message
     * @param message
     */
    public void setMessage(String message)
    {
        this.message = message;
        Log.i(TAG, "message " + message);
    }

    //Redefined class Binder
    public class LocalBinder extends Binder {

        /**
         * Method to receive Service.this
         * @return NotificationService.this
         */
        public NotificationService getService(){
            return NotificationService.this;
        }
    }
}
