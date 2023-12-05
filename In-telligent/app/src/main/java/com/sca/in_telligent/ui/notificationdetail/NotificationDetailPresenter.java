package com.sca.in_telligent.ui.notificationdetail;

import com.sca.in_telligent.R;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.EditSaveMessageRequest;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguageResponse;
import com.sca.in_telligent.openapi.data.network.model.NotificationResponse;
import com.sca.in_telligent.openapi.data.network.model.SuccessResponse;
import com.sca.in_telligent.openapi.data.network.model.TranslationResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class NotificationDetailPresenter<V extends NotificationDetailMvpView> extends
    BasePresenter<V> implements NotificationDetailMvpPresenter<V> {

  @Inject
  public NotificationDetailPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void editSavedMessage(EditSaveMessageRequest editSaveMessageRequest) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().editSavedMessage(editSaveMessageRequest)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(new Consumer<SuccessResponse>() {
          @Override
          public void accept(SuccessResponse successResponse) throws Exception {
            getMvpView().hideLoading();
            getMvpView().saveButtonChange(editSaveMessageRequest.getAction());
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            getMvpView().hideLoading();
            getMvpView().showMessage(R.string.message_not_saved);
          }
        })
    );
  }

  @Override
  public void listLanguages() {
    getCompositeDisposable().add(
        getDataManager().getLanguages().subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<NotificationLanguageResponse>() {
              @Override
              public void accept(NotificationLanguageResponse notificationLanguageResponse)
                  throws Exception {
                getMvpView().loadLanguages(notificationLanguageResponse.getLanguages());
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {

              }
            }));
  }

  @Override
  public void getTranslatedAlert(String id, String lang) {
    getMvpView().showLoading();
    getCompositeDisposable().add(
        getDataManager().getAlertTranslation(id, lang).subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<TranslationResponse>() {
              @Override
              public void accept(TranslationResponse translationResponse)
                  throws Exception {
                getMvpView().hideLoading();
                getMvpView().translatedAlert(translationResponse);
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                getMvpView().hideLoading();
              }
            }));
  }
}
