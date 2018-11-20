package com.example.rajneeshshukla.letsconnect.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.rajneeshshukla.letsconnect.R;
import com.example.rajneeshshukla.letsconnect.others.MyApplication;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Class contains all the methods that are been used multiple times all over application
 * Created by Rajneesh Shukla on 19/10/18.
 */
public class Utility {

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

    /**
     * Hides the soft keyboard from acticity
     */
    public static void hideSoftKeyboard(Activity act) {
        if (act != null && act.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) act.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Method to show loader on screen
     *
     * @param activity : present activity
     */
    public static void showLoader(Activity activity) {
        long start = new Date().getTime();
        if (activity != null) {
            ViewGroup view = (ViewGroup) activity.getWindow().getDecorView().getRootView();
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View loader = activity.findViewById(R.id.loading_indicator);
            if (loader == null) {
                assert inflater != null;
                inflater.inflate(R.layout.custom_progress_loader, view, true);
                loader = activity.findViewById(R.id.loading_indicator);
            }
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            loader.setVisibility(View.VISIBLE);
        }
        long end = new Date().getTime();
    }

    /**
     * Method to hide loader once functionality is done
     *
     * @param activity : current activity
     */
    public static void hideLoader(Activity activity) {
        try {
            if (activity != null) {
                View loader = activity.findViewById(R.id.loading_indicator);
                if (loader != null) {
                    loader.setVisibility(View.GONE);
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            } else
                Utility.logData("==cannot hide progress bar===");
        }catch (Exception e){
            Utility.logExceptionData(e);
        }
    }

    /**
     * method to toast getMessage if internet is not connected
     *
     * @return true if internet is connected
     */
    public static boolean isInternetConnected() {
        if (isNetworkAvailable()) {
            return true;
        } else {
            Utility.showLongText(MyApplication.getContext().getApplicationContext(), "Check your internet connection...");
            return false;
        }
    }

    /**
     * Method are used to log data
     */
    public static void logData(Object obj) {
        Log.d(AppStringConstants.MESSAGE_EXCEPTION_TAG, AppStringConstants.MESSAGE_EXCEPTION_TAG + obj.toString());
    }

    /**
     * Method to log exceptions
     */
    public static void logExceptionData(Exception e) {
        Log.e(AppStringConstants.EXCEPTION_LOG_TAG, AppStringConstants.EXCEPTION_TAG + Log.getStackTraceString(e));
    }
}
