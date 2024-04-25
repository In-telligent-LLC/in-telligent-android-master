package com.sca.in_telligent.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.openapi.Constants;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.service.HeadsUpNotificationService;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.ui.notificationdetail.VoiceCallNotificationActivity;
import com.sca.in_telligent.util.CommonUtils;

public class HeadsUpNotificationActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            return;
        }

        String action = intent.getStringExtra(Constants.CALL_RESPONSE_ACTION_KEY);
        PushNotification pushNotification = (PushNotification) intent.getSerializableExtra(Constants.CALL_NOTIFICATION);

        if (action != null) {
            performClickAction(context, action, pushNotification);
        }

        closeSystemDialogs(context);
    }

    private void performClickAction(Context context, String action, PushNotification pushNotification) {
        switch (action) {
            case Constants.CALL_RECEIVE_ACTION:
                handleReceiveCallAction(context, pushNotification);
                break;
            case Constants.CALL_CANCEL_ACTION:
                handleCancelCallAction(context, pushNotification);
                break;
            case Constants.CALL_AUTO_CANCEL_ACTION:
                handleAutoCancelAction(context, pushNotification);
                break;
        }
    }

    private void handleReceiveCallAction(Context context, PushNotification pushNotification) {
        if (pushNotification != null) {
            Intent intent = new Intent(context, VoiceCallNotificationActivity.class);
            intent.putExtra("notification", pushNotification);
            intent.putExtra("accept", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
            context.stopService(new Intent(context, HeadsUpNotificationService.class));
        }
    }

    private void handleCancelCallAction(Context context, PushNotification pushNotification) {
        context.stopService(new Intent(context, HeadsUpNotificationService.class));
        context.startActivity(getCancelIntent(context, pushNotification, "background"));
    }

    private void handleAutoCancelAction(Context context, PushNotification pushNotification) {
        context.stopService(new Intent(context, HeadsUpNotificationService.class));
        String currentState = ((ScaApplication) context.getApplicationContext()).getCurrentState();
        if (!TextUtils.isEmpty(currentState) && currentState.equals("start")) {
            Intent cancelIntent = getCancelIntent(context, pushNotification, "foreground");
            cancelIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(cancelIntent);
        } else {
            Intent cancelIntent = getCancelIntent(context, pushNotification, "background");
            int flags;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                flags = PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE;
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                flags = PendingIntent.FLAG_UPDATE_CURRENT;

            }
            else {
                flags = PendingIntent.FLAG_CANCEL_CURRENT;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    Integer.parseInt(pushNotification.getNotificationId()),
                    cancelIntent,
                    flags);
            CommonUtils.createNotification(context, pushNotification,
                    false, pushNotification.getTitle(), pushNotification.getBody(), true);
        }
    }


    private Intent getCancelIntent(Context context, PushNotification pushNotification, String from) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        bundle.putSerializable("pushNotification", pushNotification);
        bundle.putString("from", from);
        bundle.putBoolean("show_popup", true);
        bundle.putBoolean("details_screen", true);
        bundle.putInt("buildingId", Integer.parseInt(pushNotification.getBuildingId()));
        bundle.putInt("notificationId", Integer.parseInt(pushNotification.getNotificationId()));
        intent.putExtra("bundle", bundle);
        return intent;
    }

    private void closeSystemDialogs(Context context) {
        context.sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }
}
