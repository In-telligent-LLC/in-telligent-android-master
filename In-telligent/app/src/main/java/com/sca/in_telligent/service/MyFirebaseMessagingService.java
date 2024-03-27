package com.sca.in_telligent.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
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
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.data.network.model.PushTokenRequest;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.ui.popup.IncomingCallActivity;
import com.sca.in_telligent.ui.popup.LifeSafetyPopupActivity;
import com.sca.in_telligent.ui.popup.PersonalSafetyPopupActivity;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.TimeoutLocationListener;
import com.sca.in_telligent.util.WeatherUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

  String channelId = "my_custom_channel_id";


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

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    Log.d(TAG, "Chego aqui: " + remoteMessage.getNotification());

    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "Message data payload: " + remoteMessage.getData());

      Map<String, String> data = remoteMessage.getData();
      Log.d(TAG, "Message data payload: " + data);

      Gson gson = new Gson();
      JsonElement jsonElement = gson.toJsonTree(data);
      PushNotification pushNotification = gson.fromJson(jsonElement, PushNotification.class);
      state = ((ScaApplication) getApplication()).getCurrentState();

      Log.d("STATE EQUALS TO ----->", " " + state);

      if (state.equals("start")) {
        sendForeground(pushNotification);
      }
      sendNotification(pushNotification);

      if (true) {

      } else {
        handleNow();
      }

    }
  }
  // [END receive_message]

  // [START on_new_token]

  /**
   * Called if InstanceID token is updated. This may occur if the security of the previous token had
   * been compromised. Note that this is called when the InstanceID token is initially generated so
   * this is where you would retrieve the token.
   */
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

  /**
   * Create and show a simple notification containing the received FCM message.
   */

  private void sendNotification(PushNotification pushNotification) {
    Log.d(TAG, "sendNotification: " + pushNotification);
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra("from", "background");
    intent.putExtra("pushNotification", pushNotification);

    int id;
    PendingIntent.getActivity(this,
            Integer.parseInt(pushNotification.getNotificationId()), intent,
            PendingIntent.FLAG_IMMUTABLE);
    PendingIntent pendingIntent;

    String type = pushNotification.getType();
    String action = pushNotification.getAction();

    if (type != null && type.equals("offer")) {
      intent.putExtra("action", "goToOffer");

      int notificationId = Integer.valueOf(pushNotification.getOfferId());
      intent.putExtra("offerId", notificationId);

      id = (int) (System.currentTimeMillis() / 1000);

      pendingIntent = PendingIntent
          .getActivity(this, id, intent, PendingIntent.FLAG_IMMUTABLE);

    } else if (type != null && type.equals("alert")) {
      intent.putExtra("action", "goToAlert");

      int buildingId = Integer.valueOf(pushNotification.getBuildingId());
      intent.putExtra("buildingId", buildingId);
      int notificationId = Integer.valueOf(pushNotification.getNotificationId());
      intent.putExtra("notificationId", notificationId);

      //Old deliveredAlertlogic
      AlertOpenedRequest alertOpenedRequest = new AlertOpenedRequest();
      alertOpenedRequest.setNotificationId(notificationId);
      responder.alertDelivered(alertOpenedRequest);

      pendingIntent = PendingIntent
          .getActivity(this, notificationId, intent,  PendingIntent.FLAG_IMMUTABLE);

    } else if (type != null && type.equals("suggested_alert")) {

      int suggestionId = Integer.valueOf(pushNotification.getId());

      pendingIntent = PendingIntent
          .getActivity(this, suggestionId, intent,  PendingIntent.FLAG_IMMUTABLE);
    } else if (type != null && type.equals("social_media")) {

      intent.putExtra("social_type", pushNotification.getSocialType());
      intent.putExtra("action", "goToSocialMedia");
      id = 0x134204;

      pendingIntent = PendingIntent
          .getActivity(this, id, intent,  PendingIntent.FLAG_IMMUTABLE);
    } else if (action != null && action.equals("FeedAlert")) {

      geofenceClient.populateIntelligentFences(false);

      if (pushNotification.getFeedAlertId() != null) {
        weatherUtil.handleWeatherAlert(pushNotification.getFeedAlertId());
      }

      return;
    } else if (action != null && action.equals("IncomingCall")) {
      Intent incomingCallIntent = IncomingCallActivity.getStartIntent(getApplicationContext());
      incomingCallIntent.putExtra("uuid", pushNotification.getUuid());
      incomingCallIntent.putExtra("conferenceId", pushNotification.getConferenceId());
      incomingCallIntent.putExtra("remoteUserName", pushNotification.getRemoteUserName());
      audioHelper.startVoipRingtone();
      startActivity(intent);

      return;
    } else if (action != null && action.equals("LocationPing")) {

      final String msgId = pushNotification.getUuid();

      responder.received(msgId);

      locationUtil.getCurrentLocation(new TimeoutLocationListener(20_000) {
        @Override
        public void onTimeout() {
          Location location = locationUtil.getLastKnownLocation();

          if (location != null) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("lat", location.getLatitude());
            map.put("lng", location.getLongitude());
            map.put("radius", location.getAccuracy());
            map.put("success", true);

            responder.respond(msgId, map);
          } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("success", false);

            responder.respond(msgId, map);
          }
        }

        @Override
        public void onLocationAvailable(Location location) {
          HashMap<String, Object> map = new HashMap<>();

          map.put("lat", location.getLatitude());
          map.put("lng", location.getLongitude());
          map.put("radius", location.getAccuracy());
          map.put("success", true);

          responder.respond(msgId, map);
        }
      });
      return;
    } else {
      return;
    }

    @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String expires = dataManager.getLifeSafetyOverrideExpire();
    boolean lifeSafetyOverride = false;

    try {
      if (expires != null && df.parse(expires).after(new Date())) {
        lifeSafetyOverride = true;
      }
    } catch (ParseException ignored) {
    }

    if (!lifeSafetyOverride && "true".equals(pushNotification.getLife_safety())) {

      audioHelper.startLifeSafetyRingtone();

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if (!lifeSafetyOverride && "true".equals(pushNotification.getWeather_alert())) {
      audioHelper.startWeatherRingtone();

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if (!lifeSafetyOverride && "true".equals(pushNotification.getCriticalSafety())) {

      audioHelper.startCriticalRingtone();

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if (!lifeSafetyOverride && "true".equals(pushNotification.getPing_alert())) {

      audioHelper.startPingRingtone();

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if ("true".equals(pushNotification.getPersonal_safety())) {

      Intent popup = new Intent(this, PersonalSafetyPopupActivity.class);

      int notificationId = Integer.valueOf(pushNotification.getNotificationId());
      int buildingId = Integer.valueOf(pushNotification.getBuildingId());
      popup.putExtra("buildingId", buildingId);
      popup.putExtra("notificationId", notificationId);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());

      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if (!lifeSafetyOverride && "true".equals(pushNotification.getLightning_alert())) {

      int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

      if (hour < 22 && hour > 7) {
        audioHelper.startLightningRingtone();
      }

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if (!lifeSafetyOverride && "true".equals(pushNotification.getPc_urgent_alert())) {

      audioHelper.startUrgentRingtone();

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);

    } else if (!lifeSafetyOverride && "true".equals(pushNotification.getPc_emergency_alert())) {

      audioHelper.startEmergencyRingtone();

      Intent popup = new Intent(this, LifeSafetyPopupActivity.class);
      popup.putExtra("title", pushNotification.getTitle());
      popup.putExtra("body", pushNotification.getBody());
      popup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(popup);
    } else {
      if (!state.isEmpty() && state.equals("start")) {
        sendForeground(pushNotification);
      }
    }

    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(pushNotification.getTitle())
            .setContentText(pushNotification.getBody())
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Since android Oreo notification channel is needed.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel("default",
          "Channel human readable title",
          NotificationManager.IMPORTANCE_DEFAULT);
      notificationManager.createNotificationChannel(channel);
    }

    if (pushNotification.getNotificationId() != null) {
      notificationManager
          .notify(Integer.parseInt(pushNotification.getNotificationId()) /* ID of notification */,
              notificationBuilder.build());
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
      startActivity(refreshIntent);
    } else if ("offer".equals(pushNotification.getType())) {

    } else if ("social_media".equals(pushNotification.getType())) {
      refreshIntent.putExtra("social_type", pushNotification.getSocialType());
      startActivity(refreshIntent);
    }

  }
}