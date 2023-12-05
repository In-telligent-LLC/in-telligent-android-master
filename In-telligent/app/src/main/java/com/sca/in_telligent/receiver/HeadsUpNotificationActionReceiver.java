package com.sca.in_telligent.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.service.HeadsUpNotificationService;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity;
import com.sca.in_telligent.util.CommonUtils;

public class HeadsUpNotificationActionReceiver extends BroadcastReceiver {
    private void performClickAction(Context context, String str, PushNotification pushNotification) {
        if (str.equals(Constants.CALL_RECEIVE_ACTION) && pushNotification != null) {
            Intent intent = new Intent(context, VoiceCallNotificationActivity.class);
            intent.putExtra("notification", pushNotification);
            intent.putExtra("accept", true);
            intent.addFlags(335544320);
            intent.setFlags(intent.getFlags() | 8388608);
            context.startActivity(intent);
            context.stopService(new Intent(context, HeadsUpNotificationService.class));
        } else if (str.equals(Constants.CALL_CANCEL_ACTION)) {
            context.stopService(new Intent(context, HeadsUpNotificationService.class));
            context.startActivity(getCancelIntent(context, pushNotification, "background"));
        } else if (str.equals(Constants.CALL_AUTO_CANCEL_ACTION)) {
            context.stopService(new Intent(context, HeadsUpNotificationService.class));
            String currentState = ((ScaApplication) context.getApplicationContext()).getCurrentState();
            if (!currentState.isEmpty() && currentState.equals("start")) {
                context.startActivity(getCancelIntent(context, pushNotification, "fourground"));
            } else {
                CommonUtils.createNotification(context, pushNotification, PendingIntent.getActivity(context, Integer.parseInt(pushNotification.getNotificationId()), getCancelIntent(context, pushNotification, "background"), 134217728), false, pushNotification.getTitle(), pushNotification.getBody(), true);
            }
        }
    }

    private Intent getCancelIntent(Context context, PushNotification pushNotification, String str) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(872415232);
        Bundle bundle = new Bundle();
        bundle.putSerializable("pushNotification", pushNotification);
        bundle.putString("from", str);
        bundle.putBoolean("show_popup", true);
        bundle.putBoolean("details_screen", true);
        bundle.putInt("buildingId", Integer.valueOf(pushNotification.getBuildingId()).intValue());
        bundle.putInt("notificationId", Integer.valueOf(pushNotification.getNotificationId()).intValue());
        intent.putExtra("bundle", bundle);
        return intent;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return;
        }
        String stringExtra = intent.getStringExtra(Constants.CALL_RESPONSE_ACTION_KEY);
        PushNotification pushNotification = (PushNotification) intent.getSerializableExtra(Constants.CALL_NOTIFICATION);
        if (stringExtra != null) {
            performClickAction(context, stringExtra, pushNotification);
        }
        context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }
}
