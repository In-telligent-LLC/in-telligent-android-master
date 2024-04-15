package com.sca.in_telligent.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sca.in_telligent.R;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.DataManager.LoggedInMode;
import com.sca.in_telligent.di.component.DaggerServiceComponent;
import com.sca.in_telligent.di.component.ServiceComponent;
import com.sca.in_telligent.di.module.ServiceModule;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.data.network.model.PushTokenRequest;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.ui.popup.IncomingCallActivity;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.TimeoutLocationListener;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String TAG = "MyFirebaseMsgService";

  @Inject
  DataManager dataManager;

  @Inject
  AudioHelper audioHelper;

  @Inject
  WeatherUtil weatherUtil;

  @Inject
  Responder responder;

  @Inject
  LocationUtil locationUtil;

  @Inject
  GeofenceClient geofenceClient;

  private String state = "";

  @Override
  public void onCreate() {
    super.onCreate();
    ServiceComponent component = DaggerServiceComponent.builder()
        .serviceModule(new ServiceModule(this))
        .applicationComponent(((ScaApplication) getApplication()).getComponent())
        .build();
    component.inject(this);
  }

  @SuppressLint("CheckResult")
  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    Log.d(TAG, "Message notification: " + remoteMessage.getNotification().getTitle() + " " + remoteMessage.getNotification().getBody());

    if (remoteMessage.getData().size() > 0) {
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());


      PushNotification pushNotification = new PushNotification();
        pushNotification.setNotification_title(remoteMessage.getNotification().getTitle());
        pushNotification.setBody(remoteMessage.getNotification().getBody());
        pushNotification.setAlertType(remoteMessage.getData().get("alertType"));


      state = ((ScaApplication) getApplication()).getCurrentState();

      if(state.equals("start")){
        sendForeground(pushNotification);
      }
      else {
        sendNotification(getApplicationContext(), pushNotification);
      }

      handleNow();

    }
  }

  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);

    dataManager.setPushToken(token);
    if (dataManager.getCurrentUserLoggedInMode() == LoggedInMode.LOGGED_IN_MODE_LOGGED_IN
        .getType()) {
      sendRegistrationToServer(token);
    }
  }
  // [END on_new_token]

  /**
   * Handle time allotted to BroadcastReceivers.
   */
  private void handleNow() {
    Log.d(TAG, "Short lived task is done.");
  }

  /**
   * Persist token to third-party servers.
   *
   * Modify this method to associate the user's FCM InstanceID token with any server-side account
   * maintained by your application.
   *
   * @param token The new token.
   */
  @SuppressLint("CheckResult")
  private void sendRegistrationToServer(String token) {
    PushTokenRequest pushTokenRequest = new PushTokenRequest();
    pushTokenRequest.setEnvironment("fcm");
    pushTokenRequest.setPushToken(token);
    dataManager.registerPushToken(pushTokenRequest).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(successResponse -> {
          if (successResponse.isSuccess()) {
          }
        }, throwable -> System.out.println());
  }


  public void sendNotification(Context context, PushNotification pushNotification) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra("from", "background");
    intent.putExtra("pushNotification", pushNotification);

    int flags;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      flags = PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE;
    } else {
      flags = PendingIntent.FLAG_CANCEL_CURRENT;
    }

    PendingIntent pendingIntent = PendingIntent.getActivity(context,
            0,
            intent, flags);

    String type = pushNotification.getType();
    String action = pushNotification.getAction();

    Log.d(TAG, "Type: " + type);
    Log.d(TAG, "Action: " + action);


    if (type != null) {
      switch (type) {
        case "offer":
          intent.putExtra("action", "goToOffer");
          intent.putExtra("offerId", Integer.parseInt(pushNotification.getOfferId()));
          break;
        case "alert":
          intent.putExtra("action", "goToAlert");
          intent.putExtra("buildingId", Integer.parseInt(pushNotification.getBuildingId()));
          intent.putExtra("notificationId", Integer.parseInt(pushNotification.getNotificationId()));
          AlertOpenedRequest alertOpenedRequest = new AlertOpenedRequest();
          alertOpenedRequest.setNotificationId(Integer.parseInt(pushNotification.getNotificationId()));
          responder.alertDelivered(alertOpenedRequest);
          break;
        case "suggested_alert":
          break; // Nothing to do here
        case "social_media":
          intent.putExtra("social_type", pushNotification.getSocialType());
          intent.putExtra("action", "goToSocialMedia");
          break;
        default:
          return;
      }
    } else if (action != null) {
      switch (action) {
        case "FeedAlert":
          geofenceClient.populateIntelligentFences(false);
          if (pushNotification.getFeedAlertId() != null) {
            weatherUtil.handleWeatherAlert(pushNotification.getFeedAlertId());
          }
          return;
        case "IncomingCall":
          Intent incomingCallIntent = IncomingCallActivity.getStartIntent(getApplicationContext());
          incomingCallIntent.putExtra("uuid", pushNotification.getUuid());
          incomingCallIntent.putExtra("conferenceId", pushNotification.getConferenceId());
          incomingCallIntent.putExtra("remoteUserName", pushNotification.getRemoteUserName());
          audioHelper.startVoipRingtone();
          startActivity(incomingCallIntent);
          return;
        case "LocationPing":
          handleLocationPing(pushNotification.getUuid());
          return;
        default:
          return;
      }
    }

    // If we reach here, it means we need to show a notification
    sendNotificationWithIntent(pushNotification, pendingIntent);
  }

  private void handleLocationPing(String msgId) {
    responder.received(msgId);
    locationUtil.getCurrentLocation(new TimeoutLocationListener(20_000) {
      @Override
      public void onTimeout() {
        Location location = locationUtil.getLastKnownLocation();
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", location != null);
        if (location != null) {
          map.put("lat", location.getLatitude());
          map.put("lng", location.getLongitude());
          map.put("radius", location.getAccuracy());
        }
        responder.respond(msgId, map);
      }

      @Override
      public void onLocationAvailable(Location location) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("lat", location.getLatitude());
        map.put("lng", location.getLongitude());
        map.put("radius", location.getAccuracy());
        responder.respond(msgId, map);
      }
    });
  }

  private void sendNotificationWithIntent(PushNotification pushNotification, PendingIntent pendingIntent) {
    NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(pushNotification.getTitle())
                    .setContentText(pushNotification.getBody())
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel("default",
              "Channel human readable title",
              NotificationManager.IMPORTANCE_DEFAULT);
      notificationManager.createNotificationChannel(channel);
    }

    if (pushNotification.getNotificationId() != null) {
      notificationManager.notify(Integer.parseInt(pushNotification.getNotificationId()), notificationBuilder.build());
    }
  }


  private void sendForeground(PushNotification pushNotification) {

    Intent refreshIntent = new Intent(this, MainActivity.class);
    refreshIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    refreshIntent.putExtra("pushNotification", pushNotification);
    refreshIntent.putExtra("from", "foreground");


    refreshIntent.putExtra("show_popup", true);

    if ("alert".equals(pushNotification.getType())) {

      int buildingId = Integer.valueOf(pushNotification.getBuildingId());
      refreshIntent.putExtra("buildingId", buildingId);
      int notificationId = Integer.valueOf(pushNotification.getNotificationId());
      refreshIntent.putExtra("notificationId", notificationId);
      String title = pushNotification.getTitle();
      String body = pushNotification.getBody();

      refreshIntent.putExtra("title", title);
      refreshIntent.putExtra("body", body);
      startActivity(refreshIntent);

    } else if ("offer".equals(pushNotification.getType())) {

    } else if ("social_media".equals(pushNotification.getType())) {
      refreshIntent.putExtra("social_type", pushNotification.getSocialType());
      startActivity(refreshIntent);
    }

  }
}