package com.MGH.alarmwithsound.Activities;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.MGH.alarmwithsound.R;
import com.MGH.alarmwithsound.UseCases.GlobalPrefrencies;
import com.MGH.alarmwithsound.common.BaseObservableMvcView;

public class MainMvcImp extends BaseObservableMvcView<MainMvc.Listener> implements MainMvc {
    private Button shareBtn ,rateBtn , startBtn, stopBtn;
    private Button [] timeBtns;
    private ImageView btnOnOff;
    private View.OnClickListener clickListener;
    private int alarmTimeInMinutes;
    private boolean alarmsON;
    GlobalPrefrencies globalPrefrencies ;

    public MainMvcImp(LayoutInflater inflater , ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.activity_main_ , parent , false));

        globalPrefrencies = new GlobalPrefrencies(getContext());
        alarmsON = globalPrefrencies.getIsDataSet();
        alarmTimeInMinutes = globalPrefrencies.getTime();


        initViews();

        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alarmsON){
                    //stop alarm
                    for(Listener listener : getmListeners()){
                        listener.onStopAlarmClicked();
                    }
                    btnOnOff.setImageResource(R.drawable.btn_off);
                    alarmsON = false;
                    globalPrefrencies.storeIsDataSet(false);
                }else{
                    //start alarm
                    for(Listener listener : getmListeners()){
                        listener.onStartAlarmClicked(alarmTimeInMinutes);
                    }
                    globalPrefrencies.setTime(alarmTimeInMinutes);
                    btnOnOff.setImageResource(R.drawable.btn_on);
                    alarmsON = true;
                    globalPrefrencies.storeIsDataSet(true);
                }
            }
        });

    }

    private void selectBtn(Button btn) {
        for (int i = 0; i < 4 ; i++) {
            timeBtns[i].setTextColor(Color.WHITE);
            timeBtns[i].setBackground((getRootView().getResources().getDrawable(R.drawable.btn_shapes_normal)));
        }
        btn.setTextColor(Color.BLACK);
        btn.setBackground(getRootView().getResources().getDrawable(R.drawable.selected_btn_shape));
    }


    private void initViews() {
        timeBtns = new Button[4];
        btnOnOff = findViewById(R.id.activateAlarmsBtn);
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if(id == R.id._1){
                    selectBtn(timeBtns[0]);
                    alarmTimeInMinutes = 30;
                }else if(id == R.id._2){
                    selectBtn(timeBtns[1]);
                    alarmTimeInMinutes = 45;
                }else if(id == R.id._3){
                    selectBtn(timeBtns[2]);
                    alarmTimeInMinutes = 60;
                }else if(id == R.id._4) {
                    selectBtn(timeBtns[3]);
                    alarmTimeInMinutes = 120;
                }
            }
        };

        timeBtns[0]  = findViewById(R.id._1);
        timeBtns[1]  = findViewById(R.id._2);
        timeBtns[2]  = findViewById(R.id._3);
        timeBtns[3]  = findViewById(R.id._4);

        for (int i = 0; i < 4 ; i++) {
            timeBtns[i].setOnClickListener(clickListener);
        }

        if(alarmsON){
            btnOnOff.setImageResource(R.drawable.btn_on);
        }else{
            btnOnOff.setImageResource(R.drawable.btn_off);
        }
        int selectedTime = globalPrefrencies.getTime();
        if(selectedTime == 30){
            selectBtn(timeBtns[0]);
        }else if(selectedTime == 45){
            selectBtn(timeBtns[1]);
        }else if(selectedTime == 60){
            selectBtn(timeBtns[2]);
        }else if(selectedTime == 120){
            selectBtn(timeBtns[3]);
        }
    }

}
