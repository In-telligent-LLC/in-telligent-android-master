package com.sca.in_telligent.ui.base;

public interface MvpPresenter<V extends MvpView> {

  void onAttach(V mvpView);

  void onDetach();
}