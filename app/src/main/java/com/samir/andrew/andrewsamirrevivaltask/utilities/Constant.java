package com.samir.andrew.andrewsamirrevivaltask.utilities;

import android.content.Context;
import android.content.pm.PackageManager;


/**
 * Created by lenovo on 4/27/2016.
 */
public class Constant {

    private static Context context;
    private static Constant instance = null;
    public final static String subUrl = "";

    // local version
    // public static String baseUrl = "";

    //online version
    public static String baseUrl = "https://maps.googleapis.com/";


    public static String googleAPIKey = "AIzaSyC2-JH7QqoaikPj8KWV1x4vMzoDrqPFFT0";

    public String apiValue = "";
    public String apiKey = "";
    public String Authorization = "";


    public static Constant getInstance(Context context) {

        Constant.context = context;

        if (instance == null) {
            instance = new Constant();
        }
        return instance;
    }

  /*  public HashMap<String, String> buildHeader(Context cnt) {

        *//*
        Header key : device [*]

Header Key : version [*]
         *//*

        HashMap<String, String> meMap = new HashMap<String, String>();
        meMap.put(apiKey, apiValue);
        String auth_token = SharedPrefUtil.getInstance(cnt).read("auth_token", null);
        if (auth_token != null) {
            meMap.put(Authorization, auth_token);
        }

        String fireToken = SharedPrefUtil.getInstance(cnt).read("firebasetoken", null);
        if (fireToken != null) {
            meMap.put("device", fireToken);
        }

        meMap.put("version", getVestionCode(cnt));
        return meMap;

    }
*/

    public static String getVestionCode(Context c) {
        /*
        p40sdmkkmgjb1ggyadqz
        e4bbe5b7a4c1eb55652965aee885dd59bd2ee7f4
         */
        String v = "";
        try {

            v += c.getPackageManager()
                    .getPackageInfo(c.getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // Log.e("log",v);
        return v;

    }

}
