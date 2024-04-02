package com.sca.in_telligent.ui.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
    public void onFragmentDetached(String str) {
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

        setUnBinder(ButterKnife.bind(this));


        if (isNetworkConnected()) {
            return;
        }
        showNetworkDialog();
    }

    public ActivityComponent getActivityComponent() {
        return this.mActivityComponent;
    }

    public boolean hasPermission(String str) {
        return checkSelfPermission(str) == PackageManager.PERMISSION_GRANTED;
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

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void hideLoading() {
        Dialog dialog = this.mProgressDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.mProgressDialog.cancel();
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void onError(String str) {
        if (str != null) {
            showSnackBar(str);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(int i) {
        onError(getString(i));
    }

    @Override
    public void showMessage(String str) {
        if (str != null) {
            Toast.makeText((Context) this, (CharSequence) str, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText((Context) this, (CharSequence) getString(R.string.some_error), Toast.LENGTH_LONG).show();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("ResourceType")
    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showPopup(String str) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Alert");
//        builder.setMessage(str);
//        builder.setNeutralButton(getString(17039370), new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.base.BaseActivity.1
//            @Override // android.content.DialogInterface.OnClickListener
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
//        builder.show();
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showMessage(int i) {
        showMessage(getString(i));
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showMessageSnack(String str) {
        showSnackBar(str);
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void startActivityWithDeeplink(Intent intent) {
        Uri uri = (Uri) getIntent().getParcelableExtra("deep_link_uri");
        if (uri != null) {
            intent.putExtra("deep_link_uri", uri);
        }
        startActivity(intent);
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void hideKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public AudioManager getAudioManager() {
        int streamVolume = this.audioManager.getStreamVolume(4);
        Log.d("GcmIntentService", "volume was: " + streamVolume);
        if (streamVolume == 0) {
            streamVolume = this.audioManager.getStreamMaxVolume(3);
        }
        this.audioManager.setStreamVolume(3, streamVolume, 0);
        return this.audioManager;
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

    /* JADX INFO: Access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    // com.sca.in_telligent.ui.main.MainMvpView
    public abstract void phonePermissionResult(boolean permission);
}
