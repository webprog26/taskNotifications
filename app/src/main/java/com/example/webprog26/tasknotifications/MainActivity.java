package com.example.webprog26.tasknotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.webprog26.tasknotifications.service.NotificationService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity_TAG";

    public static final long TIME_NOTIFICATIONS_REPEAT = 1000 * 60;
    public static final String NOTIFICATION_MESSAGE = "com.example.webprog26.tasknotifications.notification_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra(NOTIFICATION_MESSAGE, getResources().getString(R.string.have_some_tea));
        PendingIntent notificationsPendingIntent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        switch (view.getId())
        {
            case R.id.btnStart:
                Log.i(TAG, "setting alarm...");
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), TIME_NOTIFICATIONS_REPEAT, notificationsPendingIntent);
                break;
            case R.id.btnStop:
                alarmManager.cancel(notificationsPendingIntent);
                break;
        }
    }
}
