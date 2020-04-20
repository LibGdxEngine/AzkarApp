package com.MGH.alarmwithsound.UseCases;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class StopServiceClass extends Service {
    private static final String TAG = "StopServiceClass";
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.e(TAG,"onCreate");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    GlobalPrefrencies globalPrefrencies;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        globalPrefrencies=new GlobalPrefrencies(this);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, StopBroadCastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(globalPrefrencies.getStopAlarm()),pendingIntent);
        Log.e(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

}
