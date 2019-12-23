package com.d27.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button startServiceButton;
    private Button stopServiceButton;

    //BoundService class Object
    BoundService mBoundService;
    boolean mServiceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for foreground service
        startServiceButton = findViewById(R.id.start_button);
        stopServiceButton = findViewById(R.id.stop_button);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(v.getContext(), ForegroundService.class));
            }
        });

        stopServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(v.getContext(), ForegroundService.class));
            }
        });

        //for bound service
        final TextView timestampText = (TextView) findViewById(R.id.timestamp_text);
        Button printTimestampButton = (Button) findViewById(R.id.print_timestamp);
        Button stopServiceButon = (Button) findViewById(R.id.stop_service);
        Button bindButton = findViewById(R.id.bind);

        printTimestampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    timestampText.setText(mBoundService.getTimestamp());
                } else {
                    Toast.makeText(getApplicationContext(), "mServiceBound not bound ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoundService.class);
                //Service starts automatically when binded
                //startService(intent);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        stopServiceButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceBound) {
                    unbindService(mServiceConnection);
                    mServiceBound = false;
                }

                //Unbinding stops the service automatically
                /*
                Intent intent = new Intent(MainActivity.this,
                        BoundService.class);
                stopService(intent);
                */
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mServiceBound) {
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mServiceBound = true;
            BoundService.MyBinder myBinder = (BoundService.MyBinder) iBinder;
            mBoundService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mServiceBound = false;
        }
    };
}
