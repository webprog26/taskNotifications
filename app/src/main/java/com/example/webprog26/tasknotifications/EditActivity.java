package com.example.webprog26.tasknotifications;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webprog26.tasknotifications.service.NotificationService;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtNotificationMessage;
    private NotificationService mNotificationService;
    boolean mBound = false;

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, NotificationService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mEtNotificationMessage = (EditText) findViewById(R.id.etNotificationMessage);
        Button btnSendNotificationMessage = (Button) findViewById(R.id.btnSendNotification);
        btnSendNotificationMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnSendNotification:
                if(!isMessageNotNull(mEtNotificationMessage))
                {
                    Toast.makeText(this, getResources().getString(R.string.message_empty), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mBound)
                {
                    mNotificationService.setMessage(mEtNotificationMessage.getText().toString());
                    Toast.makeText(this, getResources().getString(R.string.message_sent_successfully), Toast.LENGTH_SHORT).show();
                    mEtNotificationMessage.setText("");
                }
                break;
        }
    }

    /**
     * Checks that message, sending to NotificationService is not empty
     * @param editText
     * @return boolean notEmpty
     */
    private boolean isMessageNotNull(EditText editText)
    {
        return editText.getText().toString().length() > 0;
    }

    //Service connection required to bind NotificationService
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NotificationService.LocalBinder binder = (NotificationService.LocalBinder) iBinder;
            mNotificationService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound)
        {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
