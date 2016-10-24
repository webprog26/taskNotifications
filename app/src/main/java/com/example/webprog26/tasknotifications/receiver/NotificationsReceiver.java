package com.example.webprog26.tasknotifications.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.webprog26.tasknotifications.MainActivity;
import com.example.webprog26.tasknotifications.R;
import com.example.webprog26.tasknotifications.service.NotificationService;

import static android.content.Context.ALARM_SERVICE;
import static com.example.webprog26.tasknotifications.MainActivity.NOTIFICATION_MESSAGE;
import static com.example.webprog26.tasknotifications.MainActivity.TIME_NOTIFICATIONS_REPEAT;

public class NotificationsReceiver extends BroadcastReceiver {

    public NotificationsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intentata) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.putExtra(NOTIFICATION_MESSAGE, context.getResources().getString(R.string.have_some_tea));
        PendingIntent notificationsPendingIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), TIME_NOTIFICATIONS_REPEAT, notificationsPendingIntent);
    }
}
