package com.sca.in_telligent.ui.group.generate;

import android.Manifest;
import android.net.Uri;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.CreateEditGroupRequest;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.io.File;
import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GenerateGroupPresenter<V extends GenerateGroupMvpView> extends
    BasePresenter<V> implements GenerateGroupMvpPresenter<V> {

  @Inject
  public GenerateGroupPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void requestPermissions() {
//    getRxPermissions()
//        .request(Manifest.permission.CAMERA)
//        .subscribe(granted -> {
//          getMvpView().permissionResult(granted);
//        });
  }

  @Override
  public void upload(String buildingId, Uri uri, String name, String description) {
    getMvpView().showLoading();

    RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
    RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), description);

    MultipartBody.Part body = null;

    if (uri != null) {
      File file = new File(uri.getPath());
      RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
      body = MultipartBody.Part
          .createFormData("attachment", file.getName(), reqFile);
    }

    getCompositeDisposable().add(
        getDataManager().createOrEditGroup(buildingId, body, nameBody,descBody)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<SuccessResponse>() {
              @Override
              public void accept(SuccessResponse successResponse) throws Exception {
                getMvpView().hideLoading();
                if (successResponse.isSuccess()) {
                  getMvpView().groupCreateResult(true);
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
  public void createNoThumbnail(String buildingId, CreateEditGroupRequest createEditGroupRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().createOrEditGroupNoThumbnail(buildingId, createEditGroupRequest)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<SuccessResponse>() {
              @Override
              public void accept(SuccessResponse successResponse) throws Exception {
                getMvpView().hideLoading();
                if (successResponse.isSuccess()) {
                  getMvpView().groupCreateResult(true);
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
}
