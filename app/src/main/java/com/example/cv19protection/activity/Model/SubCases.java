package com.example.cv19protection.activity.Model;

public class SubCases {

    public static final String name="name";
    public static final String today_confirmed="today_confirmed";
    public static final String today_deaths="today_deaths";
    public static final String today_recovered="today_recovered";
    public static final String yesterday_open_cases="yesterday_open_cases";
    public static final String yesterday_recovered="yesterday_recovered";
    public static final String today_new_confirmed="today_new_confirmed";


    private String s_name;
    private String s_source;
    private String s_tc;
    private String s_td;
    private String s_tr;
    private String s_yoc;
    private String s_yr;

    public String getTnc() {
        return tnc;
    }

    private String tnc;


    public SubCases(String s_name, String s_tc, String s_td, String s_tr, String s_yoc, String s_yr,String tnc) {
        this.s_name = s_name;
        this.s_tc = s_tc;
        this.s_td = s_td;
        this.s_tr = s_tr;
        this.s_yoc = s_yoc;
        this.s_yr = s_yr;
        this.tnc=tnc;
    }


    public String getS_name() {
        return s_name;
    }

    public String getS_source() {
        return s_source;
    }

    public String getS_tc() {
        return s_tc;
    }

    public String getS_td() {
        return s_td;
    }

    public String getS_tr() {
        return s_tr;
    }

    public String getS_yoc() {
        return s_yoc;
    }

    public String getS_yr() {
        return s_yr;
    }
}
