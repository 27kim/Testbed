package com.d27.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            String msg = intent.getStringExtra("message");
            intent.setAction(MainActivity.FILTER_ACTION_KEY);

        SystemClock.sleep(5000);

        String resultMsg = "The result string after 6 seconds of processing.." + msg;
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage", resultMsg));
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(getApplicationContext(), "Intent Service started", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Intent Service destroyed", Toast.LENGTH_SHORT).show();
    }
}
