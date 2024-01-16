package com.sca.in_telligent.util.twilio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.data.prefs.PreferencesHelper;
import com.sca.in_telligent.di.ApplicationContext;
import com.sca.in_telligent.openapi.data.network.model.VoipCallRequest;
import com.sca.in_telligent.openapi.data.network.model.VoipCallResponse;
import com.sca.in_telligent.openapi.data.network.model.VoipTokenResponse;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import com.twilio.voice.Call;
import com.twilio.voice.CallException;
import com.twilio.voice.ConnectOptions;
import com.twilio.voice.Voice;

import java.util.HashMap;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

@Singleton
public class AppTwilioUtil implements TwilioUtil {
  private static final String TAG = "AppTwilioUtil";
  private String conferenceId = "";
  private boolean isOutgoing;
  private TwilioUtilListener listener;
  private final CompositeDisposable mCompositeDisposable;
  private final Context mContext;
  private final DataManager mDataManager;
  private final PreferencesHelper mPreferencesHelper;
  private final Responder mResponder;
  private final SchedulerProvider mSchedulerProvider;
  private String remoteUserName;
  private String subscriberId;
  private Call twilioCall;
  private String uuid;

  public interface TwilioUtilListener {
    void callDidEnd();

    void callDidFail();

    void callDidProgress(Call call);

    void callDidStart();
  }

  @Inject
  public AppTwilioUtil(@ApplicationContext Context context, DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, Responder responder, PreferencesHelper preferencesHelper) {
    this.mContext = context;
    this.mDataManager = dataManager;
    this.mSchedulerProvider = schedulerProvider;
    this.mCompositeDisposable = compositeDisposable;
    this.mResponder = responder;
    this.mPreferencesHelper = preferencesHelper;
  }

  private String randomConferenceId() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
    }
    return sb.toString();
  }

  @Override
  public void makeCall(final String str) {
    if (this.conferenceId.isEmpty()) {
      this.conferenceId = randomConferenceId();
    }
    this.subscriberId = this.mPreferencesHelper.getSubscriberId();
    this.mCompositeDisposable.add(this.mDataManager.getVoipToken().subscribeOn(this.mSchedulerProvider.io()).observeOn(this.mSchedulerProvider.ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.util.twilio.AppTwilioUtil$$ExternalSyntheticLambda3
      @Override
      public void accept(Object obj) throws Exception {
        AppTwilioUtil.this.AppTwilioUtil(str, (VoipTokenResponse) obj);
      }

    }));
  }

  @SuppressLint("NotConstructor")
  public void AppTwilioUtil(String str, VoipTokenResponse voipTokenResponse) throws Exception {
    if ((voipTokenResponse.getToken() != null) && (!voipTokenResponse.getToken().isEmpty())) {
      HashMap hashMap = new HashMap();
      hashMap.put("buildingId", str);
      hashMap.put("isCM", "");
      hashMap.put("conferenceId", this.conferenceId);
      this.twilioCall = Voice.connect(this.mContext, new ConnectOptions.Builder(voipTokenResponse.getToken()).params(hashMap).build(), getListener());
      VoipCallRequest voipCallRequest = new VoipCallRequest();
      voipCallRequest.setBuildingId(str);
      voipCallRequest.setConferenceId(this.conferenceId);
      voipCallRequest.setSenderId(Integer.parseInt(this.subscriberId));
      this.mCompositeDisposable.add(this.mDataManager.makeVoipCall(voipCallRequest).subscribeOn(this.mSchedulerProvider.io()).observeOn(this.mSchedulerProvider.ui()).subscribe(new Consumer() { // from class: com.sca.in_telligent.util.twilio.AppTwilioUtil$$ExternalSyntheticLambda0
        @Override // io.reactivex.functions.Consumer
        public void accept(Object obj) throws Exception {
          AppTwilioUtil.this.AppTwilioUtil((VoipCallResponse) obj);
        }
      }, new Consumer() { // from class: com.sca.in_telligent.util.twilio.AppTwilioUtil$$ExternalSyntheticLambda1
        @Override // io.reactivex.functions.Consumer
        public void accept(Object obj) throws Exception {
          AppTwilioUtil.this.AppTwilioUtil((Throwable) obj);
        }
      }));
    }
  }

  public void AppTwilioUtil(VoipCallResponse voipCallResponse) throws Exception {
    if (voipCallResponse.isAccepted()) {
      callWasAnswered();
    } else {
      failCall();
    }
  }


  public void AppTwilioUtil(Throwable th) throws Exception {
    Log.e(TAG, "There was an error trying to perform the call", th);
    failCall();
  }

  @Override
  public void answerCall() {
    HashMap hashMap = new HashMap();
    hashMap.put("isCM", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
    hashMap.put("conferenceId", this.conferenceId);
    this.twilioCall = Voice.connect(this.mContext, new ConnectOptions.Builder("").params(hashMap).build(), getListener());
    HashMap<String, Object> hashMap2 = new HashMap<>();
    hashMap2.put("accepted", true);
    this.mResponder.respond(this.uuid, hashMap2);
  }

  @Override // com.sca.in_telligent.util.twilio.TwilioUtil
  public void rejectCall() {
    if (this.twilioCall == null) {
      return;
    }
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("accepted", false);
    this.mResponder.respond(this.uuid, hashMap);
    this.twilioCall = null;
  }

  @Override
  public void failCall() {
    TwilioUtilListener twilioUtilListener = this.listener;
    if (twilioUtilListener != null) {
      twilioUtilListener.callDidFail();
    }
  }

  @Override
  public void callWasAnswered() {
    TwilioUtilListener twilioUtilListener = this.listener;
    if (twilioUtilListener != null) {
      twilioUtilListener.callDidStart();
    }
  }

  @Override
  public void setListener(TwilioUtilListener twilioUtilListener) {
    this.listener = twilioUtilListener;
  }

  @Override
  public void endCall() {
    Call call = this.twilioCall;
    if (call != null) {
      call.disconnect();
    }
    this.twilioCall = null;
  }

  @Override
  public void fillFields(String str, String str2, String str3, String str4) {
    this.subscriberId = str;
    this.uuid = str2;
    this.remoteUserName = str3;
    this.conferenceId = str4;
  }

  public Call.Listener getListener() {
    return new Call.Listener() { // from class: com.sca.in_telligent.util.twilio.AppTwilioUtil.1
      @Override
      public void onConnectFailure(Call call, CallException callException) {
      }

      @Override
      public void onReconnected(Call call) {
      }

      @Override
      public void onReconnecting(Call call, CallException callException) {
      }

      @Override
      public void onRinging(Call call) {
      }

      @Override
      public void onConnected(Call call) {
        if (AppTwilioUtil.this.listener != null) {
          AppTwilioUtil.this.listener.callDidProgress(call);
        }
      }

      @Override
      public void onDisconnected(Call call, CallException callException) {
        if (AppTwilioUtil.this.listener != null) {
          AppTwilioUtil.this.listener.callDidEnd();
        }
      }
    };
  }
}
