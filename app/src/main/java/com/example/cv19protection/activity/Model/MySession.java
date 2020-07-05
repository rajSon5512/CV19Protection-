package com.example.cv19protection.activity.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Date;

public class MySession {

    SharedPreferences sharedPreferences;

    public MySession(Context context){

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setid(String uid)
    {
        sharedPreferences.edit().putString("id",uid).apply();
    }

    public String getid()
    {
        return sharedPreferences.getString("id","");
    }

    public Boolean getVisit() {

        return sharedPreferences.getBoolean("visit",false);
    }

    public void setVisit(Boolean visit) {
        sharedPreferences.edit().putBoolean("visit",visit).apply();
    }


    public void setMobileNumber(String m_number){
        sharedPreferences.edit().putString("mobile_number",m_number).apply();
    }

    public String get_mobile_number(){
        return sharedPreferences.getString("mobile_number",null);
    }

    public void set_Infected(Boolean b){
        sharedPreferences.edit().putBoolean("infected",b).apply();
    }

    public Boolean get_Infected(){
        return sharedPreferences.getBoolean("infected",false);
    }

    public void set_notify(Boolean b){
        sharedPreferences.edit().putBoolean("notify",b).apply();
    }

    public Boolean get_notify(){
        return sharedPreferences.getBoolean("notify",false);
    }

    public void setvisitDate(String date){
        sharedPreferences.edit().putString("date_visit",date).apply();
    }

    public String getvisitDate(){
        return sharedPreferences.getString("date_visit","");
    }


}
