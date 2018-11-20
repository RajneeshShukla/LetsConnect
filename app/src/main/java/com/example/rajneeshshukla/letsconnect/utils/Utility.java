package com.example.rajneeshshukla.letsconnect.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.rajneeshshukla.letsconnect.others.MyApplication;

import org.w3c.dom.Text;

/**
 * Class contains all the methods that are been used multiple times all over application
 * Created by Rajneesh Shukla on 19/10/18.
 */
public class Utility {

    /**
     * method to toast if internet connection is connected
     *
     * @return true if internet is conencted
     */
    public static boolean isInternetConnected(){
        if(isNetworkAvailable()){
            return  true;
        }else{
            Utility.showLongText(MyApplication.getContext().getApplicationContext(), "Internet is not connected!");
            return false;
        }
    }


    /**
     * method to check internet availability
     *
     * @return bool value true if connected to internet otherwise false
     */
    private static boolean isNetworkAvailable() {
        Context con = MyApplication.getContext();
        NetworkInfo info = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
            info = connectivityManager.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    /**
     * Will display short Toast message
     * @param context  context of the class
     * @param message  message want to show as toast
     */
    public static void showShortText(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Will display long Toast message
     * @param context  context of the class
     * @param message  message want to show as toast
     */
    public static void showLongText(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
