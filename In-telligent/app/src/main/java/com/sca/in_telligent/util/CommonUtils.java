package com.sca.in_telligent.util;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.facebook.internal.ServerProtocol;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.util.AudioHelper;

import org.apache.http.client.config.CookieSpecs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "CommonUtils";
    private static final int[] defaultImages = {R.drawable.arm_at_baseball_game, R.drawable.blonde_girl_in_class, R.drawable.blonde_with_phone, R.drawable.business_suit_guy, R.drawable.closeup_phone, R.drawable.couple_looking_at_tablet, R.drawable.elevator_women, R.drawable.girl_in_pink_sweater, R.drawable.guy_glasses_and_tablet, R.drawable.guy_with_glasses, R.drawable.kid_sitting_with_backpack, R.drawable.lobby_guy, R.drawable.man_at_convention, R.drawable.man_leaning_on_wall, R.drawable.man_on_bike, R.drawable.man_with_glasses, R.drawable.sitting_with_books, R.drawable.two_girls_at_football_game, R.drawable.two_girls_looking_at_phone, R.drawable.woman_in_library, R.drawable.women_in_white_dress, R.drawable.women_plus_coffee_cup, R.drawable.women_looking_down, R.drawable.women_outside_on_wall, R.drawable.women_striped_dress};

    private CommonUtils() {
    }

    public static Dialog showLoadingDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView((int) R.layout.progress_dialog);
        AlertDialog create = builder.create();
        if (create.getWindow() != null) {
            create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        create.setCancelable(false);
        create.setCanceledOnTouchOutside(false);
        create.show();
        return create;
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    public static Date getSilenceDate(String dateString) {
        if (dateString != null) {
            DateFormat inDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return inDf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getSilenceDateString(Date date) {
        DateFormat inDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return inDf.format(date);
    }

    public static String getDateString(String oldDate) {
        DateFormat inDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        inDf.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat outDf = new SimpleDateFormat("MM/dd/yy h:mm a");
        outDf.setTimeZone(TimeZone.getDefault());
        String date = null;
        try {
            Date parse = inDf.parse(oldDate);
            date = outDf.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String str) throws IOException {
        InputStream open = context.getAssets().open(str);
        byte[] bArr = new byte[open.available()];
        open.read(bArr);
        open.close();
        return new String(bArr, StandardCharsets.UTF_8);
    }

    public static int getDefaultImage(int i) {
        int[] iArr = defaultImages;
        return iArr[i % iArr.length];
    }

    public static Map<String, String> getCountrySet() {
        int i = 0;
        String[] strArr = {"Afghanistan", "119", "Albania", "19", "Algeria", "17", "American Samoa", "911", "Andorra", "110", "Angola", "110", "Antigua & Barbuda", "911", "Argentina", "101", "Armenia", "103", "Aruba", "911", "Ascension Island", "6666", "Australia", "112", "Austria", "112", "Azerbaijan (Baku)", "02", "Bahamas", "911", "Bahrain", "999", "Bali", "118", "Bangladesh (Dhaka)", "866 551_3", "Barbados", "112", "Belgium", "112", "Belarus", "02", "Belize", "911", "Benin", "117", "Bermuda", "911", "Bhutan", "113", "Bolivia", "911", "Bonaire", "911", "Bosnia_Herzegovina", "122", "Botswana", "911", "Brazil", "911", "Bosnia", "92", "British Virgin Islands", "999", "Brunei", "993", "Bulgaria", "166", "Burkina Faso", "17", "Burma/Myanmar", "999", "Burundi", "117", "Cambodia", "117", "Cameroon", "117", "Canada", "911", "Canary Islands", "112", "Cape Verde", "132", "Cayman Islands", "911", "Central African Republic", "117", "Chad", "17", "Chile", "133", "China", "110", "Colombia", "119", "Comoros Islands", "17", "Congo", "117", "Cook Islands", "999", "Costa Rica", "911", "C™te d'Ivoire", "180", "Croatia", "112", "Cuba", "26811", "Curacao", "444444", "Cyprus", "112", "DR Congo", "112", "Denmark", "112", "Djibouti", "17", "Dominica", "999", "Dominican Republic", "911", "East Timor", "112", "Easter Island", "100_244", "Ecuador", "101", "Egypt", "122", "El Salvador", "911", "England", "112", "Equatorial Guinea", "113", "Eritrea", "1127799", "Estonia", "110", "Ethiopia", "91", "Falkland Islands", "999", "Fiji", "911", "Finland", "112", "France", "112", "French Guiana", "112", "French Polynesia", "17", "Gabon", "1730", "Gambia", "17", "Georgia", "022", "Germany", "110", "Ghana", "999", "Gibraltar", "999", "Greece", "112", "Grenada", "911", "Guadeloupe", "17", "Guam", "911", "Guatemala", "110", "Guinea Bissau", "112", "Gunea", "117", "Guyana", "999", "Haiti", "114", "Honduras", "119", "Hong Kong", "999", "Hungary", "112", "Iceland", "112", "India", "100", "Indonesia", "110", "Iran", "110", "Iraq", "112", "Ireland", "112", "Isle of Man", "999", "Israel", "100", "Italy", "112", "Jamaica", "119", "Japan", "110", "Jordan", "192", "Kazakhstan", "03", "Kenya", "999", "Kiribati", "192", "Kosovo", "192", "North Korea", "110", "South Korea", "112", "Kuwait", "112", "Kyrgyzstan", "103", "Laos", "1191", "Latvia", "112", "Lebanon", "112", "Lesotho", "123", "Liberia", "911", "Libya", "193", "Liechtenstein", "112", "Lithuania", "112", "Luxembourg", "112", "Macau", "999", "Macedonia", "92", "Madagascar", "117", "Malawi", "997", "Malaysia", "999", "Maldives", "119", "Mali ", "18", "Marianas Island", "911", "Marshall Islands", "625 8666", "Malta", "112", "Martinique", "17", "Mauritania", "117", "Mauritius", "999", "MŽxico", "060", "Micronesia", "911", "Moldova", "902", "Monaco", "112", "Mongolia", "102", "Montenegro", "122", "Montserrat", "999", "Morocco", "19", "Mozambique", "119", "Namibia", "1011", "Nauru", "110", "Nepal", "100", "Netherlands", "112", "Netherlands Antilles", "112", "New Zealand", "111", "Nicaragua", "118", "Niger", "112", "New Zealand", "111", "Nigeria", "112", "Northern Ireland", "112", "Norway", "112", "Oman", "999", "Pakistan", "15", "Palau", "911", "Palestine", "100", "Panama", "104", "Papua New Guinea", "000", "Paraguay", "00", "Peru", "911", "Philippines", "117", "Poland", "112", "Portugal", "112", "Puerto Rico", "911", "Qatar", "999", "RŽunion", "17", "Romania", "112", "Russia", "112", "Rwanda", "999", "Samoa", "999", "San Marino", "112", "Sao Tome and Principe", "112", "Saudi Arabia", "999", "Scotland", "112", "Senegal", "17", "Serbia", "94", "Seychelles", "999", "Sierra Leone", "999", "Singapore", "999", "Slovak Republic", "158", "Slovenia", "112", "Solomon Islands", "911", "Somalia", "888", "South Africa", "10111", "Cape Town", "107", "Spain", "112", "Sri Lanka", "2421052", "St. Helena", "911", "St. Kitts & Nevis", "911", "St. Lucia", "911", "St. Marten", "911", "Sudan", "999", "Suriname", "115", "Swaziland", "999", "Sweden", "112", "Switzerland", "117", "Syria", "112", "French Polynesia", "112", "Taiwan", "110", "Tajikistan", "112", "Tanzania", "112", "Thailand", "191", "Tibet", "110", "Togo", "101", "Tonga", "911", "Trinidad & Tobago", "999", "Tunisia", "197", "Turkey", "100", "Turkmenistan", "03", "Turks and Caicos Islands", "911", "Tuvalu", "911", "Uganda", "112", "Ukraine", "02", "United Arab Emirates", "999", "United Kingdom", "112", "United States", "911", "Uruguay", "911", "US Virgin Islands", "911", "Uzbekistan", "03", "Vanuatu", "112", "Vatican City", "112", "Venezuela", "171", "Vietnam", "03", "Western Sahara", "150", "Western Samoa", "999", "Yemen", "194", "Zambia", "999", "Zimbabwe", "999"};
        HashMap hashMap = new HashMap();
        do {
            hashMap.put(strArr[i], strArr[i + 1]);
            i += 2;
        } while (i < 470);
        return hashMap;
    }

    public static boolean checkLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 || (ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0 && ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_BACKGROUND_LOCATION") == 0);
    }

    public static void openPdfFile(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("http://docs.google.com/viewer?url=" + str), "text/html");
        context.startActivity(intent);
    }

    public static void openUrl(Context context, String str) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
    }

    public static void toggleDoNotDIsturb(Context context, final AudioHelper audioHelper) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager.getCurrentInterruptionFilter() != NotificationManager.INTERRUPTION_FILTER_ALL) {
            final int currentInterruptionFilter = notificationManager.getCurrentInterruptionFilter();
            if (notificationManager.isNotificationPolicyAccessGranted()) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                new Timer().schedule(new TimerTask() { // from class: com.sca.in_telligent.util.CommonUtils.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        notificationManager.setInterruptionFilter(currentInterruptionFilter);
                        if (currentInterruptionFilter != 1) {
                            audioHelper.stopRingtone();
                        }
                    }
                }, 60000L);
            }
        }
    }

    public static void buildAlertMessage(String str, String str2, final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // from class: com.sca.in_telligent.util.CommonUtils$$ExternalSyntheticLambda1
// android.content.DialogInterface.OnClickListener
        // from class: com.sca.in_telligent.util.CommonUtils$$ExternalSyntheticLambda0
// android.content.DialogInterface.OnClickListener
        builder.setTitle(str).setMessage(str2).setCancelable(false).setPositiveButton(context.getResources().getString(R.string.ok), (dialogInterface, i) -> context.startActivity(new Intent("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS"))).setNegativeButton(context.getResources().getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public static boolean checkDNDPermission(Context context) {
        if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).isNotificationPolicyAccessGranted()) {
            return true;
        }
        buildAlertMessage(context.getString(R.string.permission_to_manage_dnd), context.getString(R.string.permission_to_manage_dnd_description), context);
        return false;
    }

    public static void openAudioType(AudioHelper audioHelper, PushNotification pushNotification, Context context) {
        if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getLife_safety())) {
            audioHelper.startLifeSafetyRingtone();
        } else if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getWeather_alert())) {
            audioHelper.startWeatherRingtone();
        } else if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getCriticalSafety())) {
            audioHelper.startCriticalRingtone();
        } else if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getPing_alert())) {
            audioHelper.startPingRingtone();
        } else if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getLightning_alert())) {
            int i = Calendar.getInstance().get(11);
            if (i >= 22 || i <= 7) {
                return;
            }
            audioHelper.startLightningRingtone();
        } else if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getPc_urgent_alert())) {
            audioHelper.startUrgentRingtone();
        } else if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(pushNotification.getPc_emergency_alert())) {
            audioHelper.startEmergencyRingtone();
        }
    }

    public static void createNotification(Context context, PushNotification pushNotification, PendingIntent pendingIntent, boolean z, String str, String str2, boolean z2) {
        Uri defaultUri = RingtoneManager.getDefaultUri(2);
        NotificationCompat.Builder contentIntent = new NotificationCompat.Builder(context, CookieSpecs.STANDARD).setSmallIcon((int) R.drawable.ic_launcher).setContentTitle(str).setContentText(str2).setAutoCancel(true).setContentIntent(pendingIntent);
        if (z) {
            contentIntent.setSound(defaultUri);
        }
        if (z2) {
            contentIntent.setOngoing(true);
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(CookieSpecs.STANDARD, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT));
        }
        if (pushNotification.getNotificationId() != null) {
            notificationManager.notify(Integer.parseInt(pushNotification.getNotificationId()), contentIntent.build());
        } else {
            notificationManager.notify((int) System.currentTimeMillis(), contentIntent.build());
        }
    }

    public static void createNotification(Context context, PushNotification pushNotification, PendingIntent pendingIntent, boolean z, String str, String str2) {
        createNotification(context, pushNotification, pendingIntent, z, str, str2, false);
    }

    public static void clearNotification(Context context, int i) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(i);
    }

    public static boolean isAppOnForeground(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
