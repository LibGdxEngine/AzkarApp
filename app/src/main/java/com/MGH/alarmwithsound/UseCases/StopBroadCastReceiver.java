package com.MGH.alarmwithsound.UseCases;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.util.ArrayList;


public class StopBroadCastReceiver extends BroadcastReceiver {
MyBroadCastReceiver receiver;
Intent service;

    private static final String TAG = "StopBroadCastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        service=new Intent(context,ServiceStartAlarm.class);
        receiver=new MyBroadCastReceiver();
        context.stopService(service);
        Log.e(TAG,"is Run");


    }
   ArrayList<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos;
    public void amKillProcess(String process,Context context)
    {
        runningAppProcessInfos=new ArrayList<>();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        runningAppProcessInfos.addAll(am.getRunningAppProcesses());
//        for (ActivityManager.RunningTaskInfo service : am.getRunningTasks(Integer.MAX_VALUE)) {
//
//                Log.e("service ",service.description+"");
//        }
//        for (int i = 0; i < runningAppProcessInfos.size(); i++) {
//            Log.e("process "+i,runningAppProcessInfos.get(i).processName);
//        }
        am.killBackgroundProcesses(process);
    }
}
