package com.HNY.gadremmebres;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    final int[] resID = {R.raw.x, R.raw.xx};
    EditText editTextNumnOfMins;
    MyBroadCastReceiver myBroadCastReceiver;
    com.HNY.gadremmebres.GlobalPrefrencies globalPrefrencies;
    Button bt;
    Button bt1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.button);
        bt1 = (Button) findViewById(R.id.button2);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        globalPrefrencies=new com.HNY.gadremmebres.GlobalPrefrencies(this);

        editTextNumnOfMins=findViewById(R.id.et_numMin);
        myBroadCastReceiver=new MyBroadCastReceiver();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intentStop = new Intent(this, StopServiceClass.class);
        pintentStop = PendingIntent.getService(this, 0, intentStop, 0);
        intentStart = new Intent(this, ServiceStartAlarm.class);
        pendingIntent = PendingIntent.getService(this, 0, intentStart, PendingIntent.FLAG_ONE_SHOT);



    }

    AlarmManager alarmManager;
    Intent intentStop,intentStart;
    PendingIntent pendingIntent,pintentStop;
    public void startAlarm(View view) {
        runAlarmStartTime();
    }

    private void runAlarmStartTime() {
        if(!TextUtils.isEmpty(editTextNumnOfMins.getText().toString().trim())) {
            globalPrefrencies.storeMinsInterval(Integer.parseInt(editTextNumnOfMins.getText().toString().trim()));

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


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"شارك التطبيق مع اصدقائك لتربح الثواب عند الله");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.HNY.gadremmebres");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=Ide-3gIEPD8&list=RD4keMNmczf4U&index=8";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("XXXACTION");
        registerReceiver(myBroadCastReceiver,intentFilter);
    }
    Boolean isDataSet=false;
    public void stopAlarm(View view) {
        Toast.makeText(this, "تم اعادة ضبط اعدادات التنبيه", Toast.LENGTH_SHORT).show();

        isDataSet = false;
        editTextNumnOfMins.setText("");
        globalPrefrencies.storeIsDataSet(isDataSet);
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
}


