package com.sca.in_telligent.ui.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.sca.in_telligent.R;
import com.sca.in_telligent.ScaApplication;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.di.component.DaggerActivityComponent;
import com.sca.in_telligent.di.module.ActivityModule;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.NetworkUtils;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;

import javax.inject.Inject;


public abstract class BaseActivity extends AppCompatActivity implements
    BaseFragment.Callback, MvpView {

  private Dialog mProgressDialog;

  private ActivityComponent mActivityComponent;

  @Inject
  AudioManager audioManager;


  @Inject
  DataManager dataManager;

  @Inject
  Responder responder;

  @Inject
  FusedLocationProviderClient fusedLocationProviderClient;

  @Inject
  AudioHelper audioHelper;

  public VideoDownloader getVideoDownloader() {
    return videoDownloader;
  }

  @Inject
  VideoDownloader videoDownloader;

  public LocationUtil getLocationUtil() {
    return locationUtil;
  }

  @Inject
  LocationUtil locationUtil;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    mActivityComponent = DaggerActivityComponent.builder()
        .activityModule(new ActivityModule(this))
        .applicationComponent(((ScaApplication) getApplication()).getComponent())
        .build();
    if (!isNetworkConnected()) {
      showNetworkDialog();
    }
  }

  public ActivityComponent getActivityComponent() {
    return mActivityComponent;
  }

  @TargetApi(Build.VERSION_CODES.M)
  public boolean hasPermission(String permission) {
    return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  protected void onDestroy() {

    super.onDestroy();
  }

  protected abstract void setUp();

//  public void setUnBinder(Unbinder unBinder) {
//    mUnBinder = unBinder;
//  }

  @Override
  public void onFragmentAttached() {

  }

  @Override
  public void onFragmentDetached(String tag) {

  }

  @Override
  public void showLoading() {
    hideLoading();
    mProgressDialog = CommonUtils.showLoadingDialog(this);
  }

  @Override
  public void hideLoading() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.cancel();
    }
  }

  private void showSnackBar(String message) {
    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
        message, Snackbar.LENGTH_SHORT);
    View sbView = snackbar.getView();
    TextView textView = (TextView) sbView
        .findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    snackbar.show();
  }

  @Override
  public void onError(String message) {
    if (message != null) {
      showSnackBar(message);
    } else {
      showSnackBar(getString(R.string.some_error));
    }
  }

  @Override
  public void onError(@StringRes int resId) {
    onError(getString(resId));
  }

  @Override
  public void showMessage(String message) {
    if (message != null) {
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void showPopup(String message) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle("UYARI");
    alertDialog.setMessage(message);

    alertDialog
        .setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

          }
        });

    alertDialog.show();
  }

  @Override
  public void showMessage(@StringRes int resId) {
    showMessage(getString(resId));
  }

  @Override
  public void showMessageSnack(String message) {
    showSnackBar(message);
  }

  @Override
  public boolean isNetworkConnected() {
    return NetworkUtils.isNetworkConnected(getApplicationContext());
  }

  @Override
  public void startActivityWithDeeplink(Intent intent) {
    final Uri deepLinkUri = getIntent().getParcelableExtra("deep_link_uri");
    if (deepLinkUri != null) {
      intent.putExtra("deep_link_uri", deepLinkUri);
    }
    startActivity(intent);
  }

  public void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager)
          getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  public AudioManager getAudioManager() {
    int volume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
    Log.d("GcmIntentService", "volume was: " + volume);
    if (volume == 0) {
      volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    return audioManager;
  }

  public FusedLocationProviderClient getFusedLocationProviderClient() {
    return fusedLocationProviderClient;
  }

  public DataManager getDataManager() {
    return dataManager;
  }

  public AudioHelper getAudioHelper() {
    return audioHelper;
  }

  public Responder getResponder() {
    return responder;
  }

  public void showNetworkDialog() {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle("Alert");
    alertDialog.setMessage(R.string.please_check_your_network_connection_try_again);

    alertDialog
        .setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {

          }
        });

    alertDialog.show();
  }


  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }
}
