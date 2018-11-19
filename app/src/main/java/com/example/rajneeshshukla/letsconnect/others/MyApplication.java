package com.example.rajneeshshukla.letsconnect.others;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Class is used to maintain Application's context through out the application
 * Created by Rajneesh Shukla on 19/11/18
 */

public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
    public static Context getContext(){
        return sContext;
    }
}
