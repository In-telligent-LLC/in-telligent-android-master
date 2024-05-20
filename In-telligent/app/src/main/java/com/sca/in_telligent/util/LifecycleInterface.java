package com.sca.in_telligent.util;

import android.arch.lifecycle.LifecycleObserver;

public interface LifecycleInterface extends LifecycleObserver {

  void onCreated();

  void onStarted();

  void onStopped();

  void onDestroyed();

  String getState();
}
