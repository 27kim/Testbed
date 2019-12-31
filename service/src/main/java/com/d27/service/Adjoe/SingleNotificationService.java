package com.d27.service.Adjoe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import androidx.core.app.NotificationCompat;

import com.d27.service.MainActivity;
import com.d27.service.R;

import static com.d27.service.App.CHANNEL_ID;
import static com.d27.service.MainActivity.TAG;

public class SingleNotificationService extends Service {

    public static void start(Context context) {
        Intent starter = new Intent(context, SingleNotificationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(starter);
        }else{
            context.startService(starter);
        }
    }
    private static final String LOG_TAG = SingleNotificationService.class.getSimpleName();
    private Chronometer mChronometer;
    NotificationManager notificationManager;
    PendingIntent pendingIntent;
    Intent notificationIntent;
    Thread thread;
    static final int FOREGROUND_SERVICE_ID = 0xB1;

    public SingleNotificationService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "NotificationService - onCreate");

        super.onCreate();
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        notificationIntent = new Intent(this, MainActivity.class);

        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

//        startForeground(FOREGROUND_SERVICE_ID, getNotification());
    }

    private Notification getNotification() {
        Log.d(TAG, "getNotification time stamp : " + getTimestamp());

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Testing")
                .setContentText(getTimestamp())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        startForeground(FOREGROUND_SERVICE_ID, getNotification());

//        thread = new Thread() {
//            @Override
//            public void run() {
//                while (!isInterrupted()) {
//                    try {
//                        Thread.sleep(5000);
//                        startForeground(FOREGROUND_SERVICE_ID, getNotification());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        break;
//                    }
//                }
//            }
//        };
//
//        thread.start();

        return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getTimestamp() {
        long elapsedMillis = SystemClock.elapsedRealtime()
                - mChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);
        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");

        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }
}
