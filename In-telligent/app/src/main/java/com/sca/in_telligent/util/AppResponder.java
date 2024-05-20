package com.sca.in_telligent.util;

import android.util.Log;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;

import org.json.JSONObject;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class AppResponder implements Responder {

  public interface ResponderListener {

    void responderFinished(boolean finished);
  }

  @Inject
  DataManager dataManager;

  public void setResponderListener(
      ResponderListener responderListener) {
    this.responderListener = responderListener;
  }

  private ResponderListener responderListener;

  @Inject
  public AppResponder() {
  }

  @Override
  public void received(String msgId) {
    dataManager.receivedMessage(msgId).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<JSONObject>() {
          @Override
          public void accept(JSONObject jsonObject) throws Exception {
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        });
  }

  @Override
  public void respond(String msgId, HashMap<String, Object> params) {
    dataManager.respondToMessage(msgId, params).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<JSONObject>() {
          @Override
          public void accept(JSONObject jsonObject) throws Exception {

          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        });
  }

  @Override
  public void alertOpened(AlertOpenedRequest alertOpenedRequest) {
    dataManager.alertOpened(alertOpenedRequest).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            Log.d("Notification", "marked notification as read ");
            responderListener.responderFinished(successResponse.isSuccess());

          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            System.out.println();
          }
        });
  }

  @Override
  public void alertDelivered(AlertOpenedRequest alertDeliveredRequest) {
    dataManager.alertDelivered(alertDeliveredRequest).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            responderListener.responderFinished(successResponse.isSuccess());
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {

          }
        });
  }

  @Override
  public void respondToPersonalSafety(HashMap<String, Object> params) {
    dataManager.respondPersonalSafety(params).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<JSONObject>() {
          @Override
          public void accept(JSONObject jsonObject) throws Exception {
            Log.d("Notification", "Responded to personal safety");
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            Log.d("Notification", "Not Responded to personal safety");
          }
        });
  }
}
