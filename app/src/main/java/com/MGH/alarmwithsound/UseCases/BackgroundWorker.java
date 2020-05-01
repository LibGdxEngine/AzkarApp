package com.MGH.alarmwithsound.UseCases;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.MGH.alarmwithsound.R;

public class BackgroundWorker extends Worker {

    public BackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        displayNotification("My Worker", "Hey I finished my work");
        runAlarmWWithSound(getApplicationContext());
        return Worker.Result.success();
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "simplifiedcoding")
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
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
