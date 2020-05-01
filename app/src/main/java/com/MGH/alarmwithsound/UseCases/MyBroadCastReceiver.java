package com.MGH.alarmwithsound.UseCases;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.MGH.alarmwithsound.R;

public class MyBroadCastReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.MGH.alarmwithsound.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }

    private void runAlarmWWithSound(Context context){
        final int[] resID = {R.raw.x, R.raw.xx, R.raw.xxx, R.raw.xxxx, R.raw.xxxxx, R.raw.xxxxxx, R.raw.a, R.raw.aa, R.raw.aaa, R.raw.aaaa, R.raw.aaaaa, R.raw.aaaaaa, R.raw.aaaaaaa,R.raw.z,R.raw.zz,R.raw.zzz,R.raw.zzzz,R.raw.zzzzz,R.raw.zzzzzz};

        GlobalPrefrencies globalPrefrencies=new GlobalPrefrencies(context);

        int anInt = Integer.parseInt(globalPrefrencies.getUserId());
        if(anInt==resID.length){
            globalPrefrencies.storeUserId(0);
            anInt=0;
        }
        MediaPlayer mp;

        mp=MediaPlayer.create(context, resID[anInt]);
        mp.start();
        globalPrefrencies.storeUserId(anInt+1);
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();

    }
}
