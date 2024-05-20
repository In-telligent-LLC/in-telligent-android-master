package com.sca.in_telligent.ui.contact.list;

import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.ui.base.BasePresenter;
import com.sca.in_telligent.util.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class ContactListPresenter<V extends ContactListMvpView> extends BasePresenter<V> implements
    ContactListMvpPresenter<V> {

  @Inject
  public ContactListPresenter(DataManager dataManager,
      SchedulerProvider schedulerProvider,
      CompositeDisposable compositeDisposable) {
    super(dataManager, schedulerProvider, compositeDisposable);
  }
}
