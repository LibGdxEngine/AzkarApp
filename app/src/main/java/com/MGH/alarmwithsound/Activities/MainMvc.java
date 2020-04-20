package com.MGH.alarmwithsound.Activities;

public interface MainMvc {
    public interface Listener{
        void onStartAlarmClicked(int alarmTime);
        void onStopAlarmClicked();
        void onShareBtnClicked();
        void onRateBtnClicked();
    }
}
