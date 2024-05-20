package com.sca.in_telligent.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by zacharyzeno on 2/1/17.
 */

public class Utils {

    public static String ellipsize(String input, int maxLength) {
        String ellip = "...";
        if (input == null || input.length() <= maxLength
                || input.length() < ellip.length()) {
            return input;
        }
        return input.substring(0, maxLength - ellip.length()).concat(ellip);
    }
    public static String ellipsizeMore(String text, int maxLength) {
        if(text.length() <= maxLength) {
            return text;
        }

        return Html.escapeHtml(Utils.ellipsize(text, maxLength - 3)) + " <font color=#41b5d6>more</font>";
//        return Html.fromHtml(Html.escapeHtml(Utils.ellipsize(text, maxLength - 3)) + " <font color=#41b5d6>more</font>");
    }

    public static synchronized void runOnUiThread(Runnable runnable) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(runnable);
    }

    private static HandlerThread handlerThread;
    public static synchronized Looper getBackgroundLooper() {
        if(handlerThread == null) {
            handlerThread = new HandlerThread("HANDLER_THREAD");
            handlerThread.start();
        }

        return handlerThread.getLooper();
    }

    public static synchronized void runOnBackgroundThread(Runnable runnable) {
        Handler backgroundHandler = new Handler(getBackgroundLooper());
        backgroundHandler.post(runnable);
    }

    public static synchronized Handler getBackgroundHandler() {
        return new Handler(getBackgroundLooper());
    }

    public static synchronized void logIsOnMainThread(String message) {
        Log.d("Utils", "Is on main thread?: " + message + ": " + (Looper.getMainLooper() == Looper.myLooper()));
    }

    public static final SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String convertTimeFormat(final SimpleDateFormat sdfInput, final SimpleDateFormat sdfOutput, final String string) {
        String returnString = "";
        try {
            returnString = sdfOutput.format(sdfInput.parse(string));
        }catch (ParseException e) {
            e.printStackTrace();
            Log.d("In-telligent" , "exception e = " + e);
        }
        return returnString;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
