package com.sca.in_telligent.ui.group.alert.delivery;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.openapi.data.network.model.DeliveryInfoResponse;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class DeliveryInfoPresenter<V extends DeliveryInfoMvpView> extends
    BasePresenter<V> implements DeliveryInfoMvpPresenter<V> {

  @Inject
  public DeliveryInfoPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }

  @Override
  public void getDeliveryInfo(String buildingId, String notificationId) {
    getMvpView().showLoading();
    getCompositeDisposable().add(getDataManager().getDeliveryInformation(buildingId, notificationId)
        .subscribeOn(getSchedulerProvider().io()).observeOn(getSchedulerProvider().ui()).subscribe(
            new Consumer<DeliveryInfoResponse>() {
              @Override
              public void accept(DeliveryInfoResponse deliveryInfoResponse) throws Exception {
                getMvpView().hideLoading();
                getMvpView().deliveryInfoFetched(deliveryInfoResponse);
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                getMvpView().hideLoading();
              }
            }));
  }
}
