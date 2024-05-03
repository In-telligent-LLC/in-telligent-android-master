package com.sca.in_telligent.ui.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;
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

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.Callback, MvpView {
    @Inject
    AudioHelper audioHelper;
    @Inject
    AudioManager audioManager;
    @Inject
    DataManager dataManager;
    @Inject
    FusedLocationProviderClient fusedLocationProviderClient;
    @Inject
    LocationUtil locationUtil;
    private ActivityComponent mActivityComponent;
    private Dialog mProgressDialog;
    private Unbinder mUnBinder;
    @Inject
    Responder responder;
    @Inject
    VideoDownloader videoDownloader;

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
    }

    protected abstract void setUp();



    public VideoDownloader getVideoDownloader() {
        return this.videoDownloader;
    }

    public LocationUtil getLocationUtil() {
        return this.locationUtil;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
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
        return this.mActivityComponent;
    }



    public boolean hasPermission(String permission) {
        return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onDestroy() {
        Unbinder unbinder = this.mUnBinder;
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public void setUnBinder(Unbinder unbinder) {
        this.mUnBinder = unbinder;
    }

    public void showLoading() {
        hideLoading();
        this.mProgressDialog = CommonUtils.showLoadingDialog(this);
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
        TextView textView = sbView
                .findViewById(R.id.snackbar_text);
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
                .setNeutralButton(getString(android.R.string.ok), (dialogInterface, i) -> {

                });

        alertDialog.show();
    }

    @Override
    public void showMessage(int resId) {
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

    @Override
    public void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public AudioManager getAudioManager() {
        int streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        Log.d("GcmIntentService", "volume was: " + streamVolume);
        if (streamVolume == 0) {
            streamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, streamVolume, 0);
        return audioManager;
    }

    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return this.fusedLocationProviderClient;
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public AudioHelper getAudioHelper() {
        return this.audioHelper;
    }

    public Responder getResponder() {
        return this.responder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void showNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage(R.string.please_check_your_network_connection_try_again);
        builder.setNeutralButton(getString(R.string.ok), (dialogInterface, i) -> {


        });
        builder.show();
    }

    protected void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public abstract void phonePermissionResult(boolean permission);
}
