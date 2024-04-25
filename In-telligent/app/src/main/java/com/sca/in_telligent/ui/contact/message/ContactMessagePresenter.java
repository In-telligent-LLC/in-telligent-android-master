package com.sca.in_telligent.ui.contact.message;

import android.Manifest.permission;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.CreateNotificationRequest;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.SuggestNotificationRequest;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;

public class ContactMessagePresenter<V extends ContactMessageMvpView> extends
    BasePresenter<V> implements ContactMessageMvpPresenter<V> {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;

  @Inject
  public ContactMessagePresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }


@Override
public void getStoragePermission() {
    // Check if the permissions are already granted
    if (ContextCompat.checkSelfPermission(getMvpView().getContext(), permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(getMvpView().getContext(), permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

        // Request the permissions
        ActivityCompat.requestPermissions(
            (Activity) getMvpView().getContext(),
            new String[]{permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE},
            MY_PERMISSIONS_REQUEST_CODE
        );
    } else {
        // Permissions are already granted
        getMvpView().storagePermissionResult(true);
    }
}

  @Override
  public void createNotification(String buildingId, String title, String body, String type,
      ArrayList<String> attachmentPaths, String sendToEmail,
      ArrayList<String> subscriberIds) {

    getMvpView().showLoading();

    List<Part> parts = new ArrayList<>();

    for (int i = 0; i < attachmentPaths.size(); i++) {
      parts.add(prepareFilePart("attachment[" + i + "]", attachmentPaths.get(i)));
    }

    RequestBody buildingIdBody = RequestBody.create(MediaType.parse("text/plain"), buildingId);
    RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
    RequestBody bodyBody = RequestBody.create(MediaType.parse("text/plain"), body);
    RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), type);
    RequestBody sendToEmailBody = RequestBody.create(MediaType.parse("text/plain"), sendToEmail);
    RequestBody subscriberIdsBody = RequestBody
        .create(MediaType.parse("text/plain"), subscriberIds.toString());

    getCompositeDisposable().add(
        getDataManager().createNotification(parts, buildingIdBody, titleBody, bodyBody, typeBody,
             sendToEmailBody, subscriberIdsBody)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<SuccessResponse>() {
              @Override
              public void accept(SuccessResponse successResponse) throws Exception {
                getMvpView().hideLoading();
                if (successResponse.isSuccess()) {
                  getMvpView().messageSendResult(successResponse.isSuccess());
                } else {
                  if (successResponse.getErrors() != null) {
                    getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                  }
                }
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                getMvpView().hideLoading();
              }
            }));
  }

  @Override
  public void createNotificationNoAttachment(CreateNotificationRequest createNotificationRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().createNotificationNoThumbnail(createNotificationRequest)
            .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            getMvpView().hideLoading();
            if (successResponse.isSuccess()) {
              getMvpView().messageSendResult(successResponse.isSuccess());
            } else {
              if (successResponse.getErrors() != null) {
                getMvpView().showMessage(successResponse.getErrors().getName().get(0));
              }
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
          }
        }));
  }

  @Override
  public void suggestNotification(
          SuggestNotificationRequest suggestNotificationRequest
  ) {
    getMvpView().showLoading();

    getCompositeDisposable().add(
        getDataManager().suggestNotification(suggestNotificationRequest)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
                successResponse -> {
                  getMvpView().hideLoading();
                  if (successResponse.isSuccess()) {
                    getMvpView().messageSendResult(successResponse.isSuccess());
                  } else {
                    if (successResponse.getErrors() != null) {
                      getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                    }
                  }

                }, throwable -> getMvpView().hideLoading()));

  }

  @Override
  public void suggestNotificationNoAttachment(
      SuggestNotificationRequest suggestNotificationRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().suggestNotificationNoThumbnail(suggestNotificationRequest)
            .subscribeOn(getSchedulerProvider().io()).
            observeOn(getSchedulerProvider().ui()).subscribe(successResponse -> {
              getMvpView().hideLoading();
              if (successResponse.isSuccess()) {
                getMvpView().messageSendResult(successResponse.isSuccess());
              } else {
                if (successResponse.getErrors() != null) {
                  getMvpView().showMessage(successResponse.getErrors().getName().get(0));
                }
              }
            }, throwable -> getMvpView().hideLoading()));
  }

  private MultipartBody.Part prepareFilePart(String partName, String filePath) {

    File file = new File(filePath);

    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

    return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
  }
}
