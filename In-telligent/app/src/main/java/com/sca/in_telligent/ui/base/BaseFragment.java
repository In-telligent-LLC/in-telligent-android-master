package com.sca.in_telligent.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;
import javax.inject.Inject;

import butterknife.Unbinder;
import io.reactivex.rxjava3.annotations.Nullable;


public abstract class BaseFragment extends Fragment implements MvpView {

  private BaseActivity mActivity;
  private Unbinder mUnBinder;
  private Dialog mProgressDialog;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setUp(view);
    if (!isNetworkConnected()) {
      showNetworkDialog();
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof BaseActivity) {
      BaseActivity activity = (BaseActivity) context;
      this.mActivity = activity;
      activity.onFragmentAttached();
    }
  }

  @Override
  public void showLoading() {
    hideLoading();
    mProgressDialog = CommonUtils.showLoadingDialog(this.getContext());
  }

  @Override
  public void hideLoading() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.cancel();
    }
  }

  @Override
  public void onError(String message) {
    if (mActivity != null) {
      mActivity.onError(message);
    }
  }

  @Override
  public void onError(@StringRes int resId) {
    if (mActivity != null) {
      mActivity.onError(resId);
    }
  }

  @Override
  public void showMessage(String message) {
    if (mActivity != null) {
      mActivity.showMessage(message);
    }
  }

  @Override
  public void showMessage(@StringRes int resId) {
    if (mActivity != null) {
      mActivity.showMessage(resId);
    }
  }

  private void showSnackBar(String message) {
    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
        message, Snackbar.LENGTH_SHORT);
    View sbView = snackbar.getView();
    TextView textView = null;
    textView.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
    snackbar.show();
  }

  @Override
  public void showMessageSnack(String message) {
    showSnackBar(message);
  }

  @Override
  public void showPopup(String message) {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
    alertDialog.setTitle("Alert");
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
  public boolean isNetworkConnected() {
    if (mActivity != null) {
      return mActivity.isNetworkConnected();
    }
    return false;
  }

  @Override
  public void onDetach() {
    mActivity = null;
    super.onDetach();
  }

  @Override
  public void hideKeyboard() {
    if (mActivity != null) {
      mActivity.hideKeyboard();
    }
  }

  public ActivityComponent getActivityComponent() {
    if (mActivity != null) {
      return mActivity.getActivityComponent();
    }
    return null;
  }


  @Override
  public void startActivityWithDeeplink(Intent intent) {
    final Uri deepLinkUri = getActivity().getIntent().getParcelableExtra("deep_link_uri");
    if (deepLinkUri != null) {
      intent.putExtra("deep_link_uri", deepLinkUri);
    }
    startActivity(intent);
  }

  public AudioManager getAudioManager() {
    if (mActivity != null) {
      return mActivity.getAudioManager();
    }
    return null;
  }

  public FusedLocationProviderClient getFusedLocationProviderClient() {
    if (mActivity != null) {
      return mActivity.getFusedLocationProviderClient();
    }
    return null;
  }

  public Responder getResponder() {
    return mActivity.responder;
  }

  public VideoDownloader getVideoDownloader() {
    return mActivity.getVideoDownloader();
  }

  public BaseActivity getBaseActivity() {
    return mActivity;
  }

  public void setUnBinder(Unbinder unBinder) {
    mUnBinder = unBinder;
  }

  protected abstract void setUp(View view);

  @Override
  public void onDestroy() {
//    if (mUnBinder != null) {
//      mUnBinder.unbind();
//    }
    super.onDestroy();
  }

  public void showNetworkDialog() {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

  public interface Callback {

    void onFragmentAttached();

    void onFragmentDetached(String tag);
  }
}
