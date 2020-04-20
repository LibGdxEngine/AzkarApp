package com.HNY.gadremmebres;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;


public class ServiceStartAlarm  extends Service {
MyBroadCastReceiver myBroadCastReceiver;

    private static final String TAG = "ServiceStartAlarm";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    int interval;
    com.HNY.gadremmebres.GlobalPrefrencies globalPrefrencies;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        globalPrefrencies=new com.HNY.gadremmebres.GlobalPrefrencies(this);

        interval=(globalPrefrencies.getMinsInterval()*60000);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, MyBroadCastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),System.currentTimeMillis()+interval, pendingIntent);
        Log.e(TAG,"onStartCommand");

      //  playSound(this, getAlarmUri());
        return super.onStartCommand(intent, flags, startId);
    }

    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
MediaPlayer mMediaPlayer;
    int i;
    final int[] resID = {R.raw.x, R.raw.xx};
    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                i++;
                if(i==resID.length){
                    i=0;
                }
                globalPrefrencies.storeUserId(i);
                Log.e("MMM",i+"");
            }
        } catch (IOException e) {
            Log.e("ZVVVVZV", e.getMessage());

        }
    }



    //    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//
//        if(intent.getExtras()!=null) {
//
//        }
//        Log.e("ServiStart Alarm is Run",System.currentTimeMillis()+interval+ " ");
//    }
    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }
}