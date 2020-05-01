package com.MGH.alarmwithsound.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MGH.alarmwithsound.UseCases.BackgroundWorker;
import com.MGH.alarmwithsound.UseCases.GlobalPrefrencies;
import com.MGH.alarmwithsound.UseCases.MyBroadCastReceiver;
import com.MGH.alarmwithsound.R;
import com.MGH.alarmwithsound.UseCases.ServiceStartAlarm;
import com.MGH.alarmwithsound.UseCases.StopServiceClass;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MainMvc.Listener {

    MyBroadCastReceiver myBroadCastReceiver;
    GlobalPrefrencies globalPrefrencies;
    Boolean isDataSet=false;
    AlarmManager alarmManager;
    Intent intentStop,intentStart;
    PendingIntent pendingIntent,pintentStop;
    MainMvcImp mvcImp;
    PeriodicWorkRequest periodicWorkRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvcImp = new MainMvcImp(getLayoutInflater() , null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        globalPrefrencies = new GlobalPrefrencies(this);


        myBroadCastReceiver = new MyBroadCastReceiver();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intentStop = new Intent(this, StopServiceClass.class);
        pintentStop = PendingIntent.getService(this, 0, intentStop, 0);
        intentStart = new Intent(this, ServiceStartAlarm.class);
        pendingIntent = PendingIntent.getService(this, 0, intentStart, PendingIntent.FLAG_ONE_SHOT);

        setContentView(mvcImp.getRootView());




    }
    // Setup a recurring alarm every half hour
    public void scheduleAlarm(int time) {
        long interval = 30;
        switch (time){
            case 30:{
                interval = AlarmManager.INTERVAL_HALF_HOUR;
                break;
            }
            case 45:{
                interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                break;
            }
            case 60:{
                interval = AlarmManager.INTERVAL_HOUR;
                break;
            }
            case 120:{
                interval = AlarmManager.INTERVAL_HALF_DAY;
                break;
            }
        }

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyBroadCastReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, interval, pIntent);
    }
    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyBroadCastReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyBroadCastReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }


    private void startBackgroundWorker(int timeInMinute) {
        periodicWorkRequest = new  PeriodicWorkRequest
                .Builder(BackgroundWorker.class , 15 , TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(periodicWorkRequest);
    }


    private void runAlarmStartTime(int time) {
        startBackgroundWorker(time);
        if(time != -1) {
            globalPrefrencies.storeMinsInterval(time);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long timeInMillis = Long.parseLong((globalPrefrencies.getMinsInterval() * 60000) + "");

            Intent intent = new Intent(this, MyBroadCastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeInMillis, timeInMillis, pendingIntent);
            Toast.makeText(this, "سيتم التنبيه بعد  " + globalPrefrencies.getMinsInterval() + " دقيقه", Toast.LENGTH_LONG).show();
            this.startService(intent);
        }else {
            Toast.makeText(this, "ادخل عدد دقائق التكرار", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStartAlarmClicked(int alarmTime) {
        cancelAlarm();
        scheduleAlarm(alarmTime);
    }


    @Override
    public void onStopAlarmClicked() {
        Toast.makeText(this, "تم اعادة ضبط اعدادات التنبيه", Toast.LENGTH_SHORT).show();
        cancelAlarm();
    }

    @Override
    public void onShareBtnClicked() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"حمل التطبيق وشاركه مع اصدقائك لتعم الفائده");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.MGH.alarmwithsound");
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    @Override
    public void onRateBtnClicked() {
        String url = "https://play.google.com/store/apps/details?id=com.MGH.alarmwithsound";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("XXXACTION");
        registerReceiver(myBroadCastReceiver,intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mvcImp.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mvcImp.unregisterListener(this);
    }
}

