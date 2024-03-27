package com.sca.in_telligent.util.twilio;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.twilio.voice.Call;
import com.twilio.voice.CallException;
import com.twilio.voice.ConnectOptions;
import com.twilio.voice.Voice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Singleton
public class AppTwilioUtil implements TwilioUtil {

  private static String TAG = AppTwilioUtil.class.getSimpleName();

  private boolean isOutgoing;

  private String conferenceId = "";

  private String remoteUserName;
  private String uuid;
  private Context mContext;
  private String subscriberId;

  private Call activeCall;

  private final DataManager mDataManager;
  private final SchedulerProvider mSchedulerProvider;
  private final CompositeDisposable mCompositeDisposable;
  private final PreferencesHelper mPreferencesHelper;
  private final Responder mResponder;


  private TwilioUtilListener listener;

  @Inject
  public AppTwilioUtil(@ApplicationContext Context context, DataManager dataManager,
                       SchedulerProvider schedulerProvider,
                       CompositeDisposable compositeDisposable, Responder responder,
                       PreferencesHelper preferencesHelper) {
    mContext = context;
    mDataManager = dataManager;
    mSchedulerProvider = schedulerProvider;
    mCompositeDisposable = compositeDisposable;
    mResponder = responder;
    mPreferencesHelper = preferencesHelper;
  }

  private String randomConferenceId() {
    String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int lettersLength = letters.length();

    Random generator = new Random();
    StringBuilder randomStringBuilder = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      randomStringBuilder.append(letters.charAt(generator.nextInt(lettersLength)));
    }
    return randomStringBuilder.toString();
  }

  @Override
  public void makeCall(String buildingId) {

    if (conferenceId.isEmpty()) {
      conferenceId = randomConferenceId();
    }

    subscriberId = mPreferencesHelper.getSubscriberId();

    mCompositeDisposable.add(mDataManager.getVoipToken().subscribeOn(mSchedulerProvider.io())
            .observeOn(mSchedulerProvider.ui()).subscribe(
                    voipTokenResponse -> {
                      if (voipTokenResponse.getToken() != null & !voipTokenResponse.getToken()
                              .isEmpty()) {

                        Log.d(TAG, "Voip token: " + voipTokenResponse.getToken());

                        Map<String, String> params = new HashMap<>();
                        params.put("buildingId", buildingId);
                        params.put("isCM", "true");
                        params.put("conferenceId", conferenceId);
                        params.put("to", "+5511987491403");

                        ConnectOptions connectOptions = new ConnectOptions.Builder(voipTokenResponse.getToken())
                                .params(params)
                                .build();

                        activeCall = Voice.connect(mContext, connectOptions, getListener());

                        Log.d(TAG, "Twilio call: " + activeCall.getState());
                        if (activeCall.getState() == Call.State.CONNECTING) {
                          Log.d(TAG, "connecting linha 110 " + activeCall.getState());
                            listener.callDidProgress(activeCall);

                        } else if (activeCall.getState() == Call.State.CONNECTED) {
                          Log.d(TAG, "connected linha 113 " + activeCall.getState());
                            callWasAnswered();
                        } else {
                          Log.d(TAG, "else linha 116 " + activeCall.getState());
                            failCall();
                        }

//                        VoipCallRequest voipCallRequest = new VoipCallRequest();
//                        voipCallRequest.setBuildingId(buildingId);
//                        voipCallRequest.setConferenceId(conferenceId);
//                        voipCallRequest.setSenderId(Integer.parseInt(subscriberId));

//                        mCompositeDisposable.add(mDataManager.makeVoipCall(voipCallRequest)
//                                .subscribeOn(mSchedulerProvider.io())
//                                .observeOn(mSchedulerProvider.ui()).
//                                subscribe(voipCallResponse -> {
//                                  Log.d(TAG, "linha 125: " + activeCall.getState());
//                                  if (voipCallResponse.isAccepted()) {
//                                    callWasAnswered();
//                                  } else {
//                                    Log.d(TAG, "CAIU no else: " + activeCall.getState());
//
//                                    failCall();
//                                  }
//                                }, throwable -> {
//                                  Log.d(TAG, "CAIU aqui no throw: " + activeCall.getState());
//
//                                  failCall();
//                                }));

                      }
                    }, throwable -> {
                      Log.e(TAG, "There was an error trying to perform the call", throwable);
                      failCall();
                    }));
  }

  @Override
  public void answerCall() {
    String token = "";
    Map<String, String> params = new HashMap<>();
    params.put("isCM", "true");
    params.put("conferenceId", conferenceId);

    ConnectOptions connectOptions = new ConnectOptions.Builder(token)
            .params(params)
            .build();

    activeCall = Voice
            .connect(mContext, connectOptions, getListener());



    HashMap<String, Object> map = new HashMap<>();

    map.put("accepted", true);

    mResponder.respond(uuid, map);
  }

  @Override
  public void rejectCall() {

    if (activeCall == null) {
      return;
    }

    HashMap<String, Object> map = new HashMap<>();
    map.put("accepted", false);

    mResponder.respond(uuid, map);

    activeCall = null;
  }

  @Override
  public void failCall() {
    if (listener != null) {
      listener.callDidFail();
    }
  }

  @Override
  public void callWasAnswered() {
    if (listener != null) {
      listener.callDidStart();
    }
  }

  @Override
  public void setListener(TwilioUtilListener listener) {
    this.listener = listener;
  }

  @Override
  public void endCall() {
    if (activeCall != null) {
      activeCall.disconnect();
    }
    activeCall = null;
  }

  @Override
  public void fillFields(String subscriberId, String uuid,
                         String remoteUsername, String conferenceId) {
    this.subscriberId = subscriberId;
    this.uuid = uuid;
    this.remoteUserName = remoteUsername;
    this.conferenceId = conferenceId;
  }


  public Call.Listener getListener() {
    return new Call.Listener() {

      @Override
      public void onConnectFailure(Call call, CallException e) {

      }

      @Override
      public void onRinging(@NonNull Call call) {

      }

      @Override
      public void onConnected(Call call) {
        if (listener != null) {
          listener.callDidProgress(call);
        }
      }

      @Override
      public void onReconnecting(@NonNull Call call, @NonNull CallException callException) {

      }

      @Override
      public void onReconnected(@NonNull Call call) {

      }

      @Override
      public void onDisconnected(Call call, CallException e) {
        if (listener != null) {
          listener.callDidEnd();
        }
      }

      @Override
      public void onCallQualityWarningsChanged(@NonNull Call call, @NonNull Set<Call.CallQualityWarning> currentWarnings, @NonNull Set<Call.CallQualityWarning> previousWarnings) {
        Call.Listener.super.onCallQualityWarningsChanged(call, currentWarnings, previousWarnings);
      }
    };
  }

  public interface TwilioUtilListener {

    void callDidFail();

    void callDidProgress(Call call);

    void callDidStart();

    void callDidEnd();

  }
}
