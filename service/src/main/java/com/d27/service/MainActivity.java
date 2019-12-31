package com.d27.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.d27.service.Adjoe.AlarmUtils;

public class MainActivity extends AppCompatActivity {
    public static final String FILTER_ACTION_KEY = "999";
    public static final String TAG = "MainActivity";
    private Button startServiceButton;
    private Button stopServiceButton;

    //BoundService class Object
    BoundService mBoundService;
    boolean mServiceBound = false;

    //for IntentService
    TextView textView;
    Button button;
    EditText editText;
    MyReceiver myReceiver;

    Button buttonForAlarm;
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

        //for IntentService
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.inputText);

        //Connecting submit on keyboard with the button.
        editText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    button.performClick();
                    return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, MyIntentService.class);
                intent.putExtra("message", message);
                //Starting the IntentService
                startService(intent);
            }
        });

        buttonForAlarm = findViewById(R.id.buttonForAlarm);
        buttonForAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "buttonForAlarm  pressed");
                new AlarmUtils().getInstance().startFiveSecondAlram(getApplicationContext());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(FILTER_ACTION_KEY);
        myReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(myReceiver, new IntentFilter(FILTER_ACTION_KEY));
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

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("broadcastMessage");
            textView.setText(textView.getText() + "\n" + message);

        }
    }
}
