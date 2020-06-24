package com.example.cv19protection.activity.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

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
        return sharedPreferences.getString("id","-1");
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


}
