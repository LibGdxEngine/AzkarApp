package com.MGH.alarmwithsound.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.MGH.alarmwithsound.UseCases.GlobalPrefrencies;
import com.MGH.alarmwithsound.UseCases.MyBroadCastReceiver;
import com.MGH.alarmwithsound.R;
import com.MGH.alarmwithsound.UseCases.ServiceStartAlarm;
import com.MGH.alarmwithsound.UseCases.StopServiceClass;

public class MainActivity extends AppCompatActivity implements MainMvc.Listener {

    MyBroadCastReceiver myBroadCastReceiver;
    GlobalPrefrencies globalPrefrencies;
    Boolean isDataSet=false;
    AlarmManager alarmManager;
    Intent intentStop,intentStart;
    PendingIntent pendingIntent,pintentStop;
    MainMvcImp mvcImp;

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


    private void runAlarmStartTime(int time) {
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
        runAlarmStartTime(alarmTime);
    }

    @Override
    public void onStopAlarmClicked() {
        Toast.makeText(this, "تم اعادة ضبط اعدادات التنبيه", Toast.LENGTH_SHORT).show();
        globalPrefrencies.storeMinsInterval(60);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        stopService(new Intent(this,ServiceStartAlarm.class));
        try {

            unregisterReceiver(myBroadCastReceiver);
        }catch (Exception  e){
            Log.e("XXX",e.getMessage());
        }
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

