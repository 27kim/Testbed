package com.d27.service.Adjoe;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.util.Log;

import static com.d27.service.MainActivity.TAG;


public class AlarmUtils {
    private final static int FIVE_SECOND = 5 * 1000;

    private AlarmUtils alarmUtils;

    public AlarmUtils getInstance(){
        if(alarmUtils == null){
            alarmUtils = new AlarmUtils();
        }
        return alarmUtils;
    }

    public void startFiveSecondAlram(Context context) {
        Log.d(TAG, "startFiveSecondAlram");

        Intent alarmIntent = new Intent(context, SingleNotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, alarmIntent, 0);

        startAlarm(context, pendingIntent, FIVE_SECOND);
    }

    private void startAlarm(Context context, PendingIntent pendingIntent, int delay) {
        Log.d(TAG, "startAlarm");
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //this fires every minute even requested for 5 seconds
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5000, pendingIntent);

        /**
         * these below don't work
         * */
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
//        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
//        }else{
//            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
//        }
    }
}
