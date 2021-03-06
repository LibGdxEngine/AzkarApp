package com.MGH.alarmwithsound.UseCases;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;




public class GlobalPrefrencies {


//    private static final String CURRANCY = "carrancy";
//    private static final String COUNTRY_ID = "country_id";
//    private static final String TXTTAB = "txtTab" ;
    private static final String USER_NAME = "USER_NAME";
//    private static final String ALLOW_NOTIFICATION = "allow_notification";
    final static String USER_ID="id";
//    final static String SETTINGS_LOGIN = "login";
    final static String APP_lANGUAGE = "language";
    final static String PREFS_NAME = "settings";
//    static final String USER_API_TOKEN ="api_token" ;
//    static final String USER_PASSWORD="password";
//    static final String USER_IMAGE = "user_image";
//    final static String USER_PHONE="phone";
//    final static String USER_EMAIL="email";
    private  Context context;
    private SharedPreferences prefs;
    private SharedPreferences.Editor PrefsEditor;


    public GlobalPrefrencies(Context context) {
        this.context = context;
        prefs = null;
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        PrefsEditor = prefs.edit();
    }



    public int getTime(){
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        return prefs.getInt("time", 30);
    }

    public void setTime(int time){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("time", time);
        editor.commit();
    }


    public String getLanguage(){

        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Log.d(APP_lANGUAGE, Locale.getDefault().getDisplayLanguage());
        value = prefs.getString("language", "ar");
        return value;
    }


    public  String getName() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Log.d(APP_lANGUAGE, Locale.getDefault().getDisplayLanguage());
        value = prefs.getString(USER_NAME, "");
        return value;
    }

    public void storeUserId(int id){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_ID, String.valueOf(id));
        Log.e("DSDSDS",id+"");
        editor.commit();

    }
    public  int getMinsInterval() {
        int value = 0;
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);

        value = prefs.getInt("mins", 102);
        return value;
    }



    public  String getStopAlarm() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        long timeZone = Calendar.getInstance().getTimeInMillis();
        value = prefs.getString("stopclock",timeZone+"" );
        return value;
    }


    public  String getUserId() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);

        value = prefs.getString(USER_ID, "0");
        return value;
    }
    public void storeMinsInterval(int mins){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("mins", mins);

        editor.commit();

    }

    public void storeIsDataSet(Boolean isDataSet) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isDataSet", isDataSet);
        editor.commit();

    }


    public Boolean getIsDataSet() {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        return settings.getBoolean("isDataSet" , false);
    }
}
/*

    public void storeCountryById(int idCountry) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(COUNTRY_ID, idCountry);
        editor.commit();

    }
public void storeUserImage(String user_image){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_IMAGE, user_image);
        editor.commit();

    }

    public  String getUserImage() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Log.d(APP_lANGUAGE, Locale.getDefault().getDisplayLanguage());
        value = prefs.getString(USER_IMAGE, "");
        return value;
    }
    public void storeCurrancy(String currancy) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CURRANCY, currancy);
        editor.commit();
    }
  public String getTypeCurrancy() {
        String  value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Log.d(APP_lANGUAGE, Locale.getDefault().getDisplayLanguage());
        value = prefs.getString(CURRANCY, "ر.س");
        return value;
    }
    public Boolean getLoginStatus() {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Boolean value = prefs.getBoolean(SETTINGS_LOGIN, false);
        return value;
    }
     public Boolean getIsSettingBody() {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Boolean value = prefs.getBoolean("isDataSet", false);
        return value;
    }





    public Boolean getAllowNotification() {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Boolean value = prefs.getBoolean(ALLOW_NOTIFICATION, true);
        return value;
    }

    public void storeAllowNotification(Boolean status) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(ALLOW_NOTIFICATION, status);
        editor.commit();

    }

    public void storeLoginStatus(Boolean status) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SETTINGS_LOGIN, status);
        editor.commit();

    }

    public String getApi_token() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Log.d(APP_lANGUAGE, Locale.getDefault().getDisplayLanguage());
        value = prefs.getString(USER_API_TOKEN, "");
        return value;
    }

    public void storeTypeShowRecycle(String txtTab) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TXTTAB, txtTab);
        editor.commit();
    }

    public int getCountryById() {
        int value = 0;
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        value = prefs.getInt(COUNTRY_ID,2);
        return value;
    }
    public void storePassword(String password){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_PASSWORD,password);
        editor.commit();
    }

    public  String getUserPhone() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Log.d(APP_lANGUAGE, Locale.getDefault().getDisplayLanguage());
        value = prefs.getString(USER_PHONE, "0");
        return value;
    }


    public void storeWieght(String wieght){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("wieght", wieght );
        editor.commit();

    }
    public String getWieght(){

        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        value = prefs.getString("wieght", "70");
        return value;
    }


    public void storeValWaterML(String waterml){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("waterml", waterml );
        editor.commit();

    }
    public String getValWaterML(){

        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        value = prefs.getString("waterml", "2000");
        return value;
    }




    public void storeValWaterMLDrink(String watermlDrink){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("watermlDrink", watermlDrink );
        editor.commit();

    }
    public String getValWaterMLDrink(){

        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        value = prefs.getString("watermlDrink", "5000");
        return value;
    }
    public void storePhone(String phone){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_PHONE, phone);
        editor.commit();

    }

    public void storeEmail(String email){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_EMAIL, email);
        editor.commit();

    }



    public void storeStopAlarm(String startAt){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("stopclock", startAt);
        editor.commit();

    }

 public  final String USERDATA = "MyVariables";

    public  void saveMap(String key, Map<String,String> inputMap){
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        if (prefs!= null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key).commit();
            editor.putString(key, jsonString);
            editor.commit();
        }
    }

    public Map<String,String> loadMap(String key){
        Map<String,String> outputMap = new HashMap<String,String>();
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        try{
            if (prefs!= null){
                String jsonString = prefs.getString(key, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String k = keysItr.next();
                    String v = (String) jsonObject.get(k);
                    outputMap.put(k,v);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }





    public void storeStartAlarm(String startAt){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("startclock", startAt);
        editor.commit();

    }


    public  String getStartAlarm() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        long timeZone = Calendar.getInstance().getTimeInMillis();
        value = prefs.getString("startclock",timeZone+"" );
        return value;
    }

    public void storeName(String id){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_NAME, String.valueOf(id));
        editor.commit();

    }


    public  String getAddress() {
        String value = "";
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);

        value = prefs.getString("address", "1");
        return value;
    }


    public void storeAddress(String Address){

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("address", Address);
        editor.commit();

    }


    public void storeApi_token(String api_token) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USER_API_TOKEN, api_token);
        editor.commit();
    }

    public Boolean getIsFirstOpeen() {
        final SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, 0);
        Boolean value = prefs.getBoolean("firstStart", true);
        return value;
    }
    public void setIsFirstOpeen(Boolean status) {

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,
                0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstStart", status);
        editor.commit();

    }
    */
