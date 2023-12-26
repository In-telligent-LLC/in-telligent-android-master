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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.snackbar.Snackbar;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.Responder;
import com.sca.in_telligent.util.VideoDownloader;

public abstract class BaseFragment extends Fragment implements MvpView {
    private BaseActivity mActivity;
    private Dialog mProgressDialog;
    private Unbinder mUnBinder;

    public interface Callback {
        void onFragmentAttached();

        void onFragmentDetached(String str);
    }

    protected abstract void setUp(View view);

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);


        setUp(view);
        if (isNetworkConnected()) {
            return;
        }
        showNetworkDialog();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) context;
            this.mActivity = baseActivity;
            baseActivity.onFragmentAttached();
        }
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showLoading() {
        hideLoading();
        this.mProgressDialog = CommonUtils.showLoadingDialog(getContext());
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void hideLoading() {
        Dialog dialog = this.mProgressDialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.mProgressDialog.cancel();
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void onError(String str) {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.onError(str);
        }
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void onError(int i) {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.onError(i);
        }
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showMessage(String str) {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.showMessage(str);
        }
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showMessage(int i) {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.showMessage(i);
        }
    }


    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        snackbar.show();
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showMessageSnack(String str) {
        showSnackBar(str);
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

    @Override // com.sca.in_telligent.ui.base.MvpView
    public boolean isNetworkConnected() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            return baseActivity.isNetworkConnected();
        }
        return false;
    }

    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void hideKeyboard() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.hideKeyboard();
        }
    }

    public ActivityComponent getActivityComponent() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            return baseActivity.getActivityComponent();
        }
        return null;
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void startActivityWithDeeplink(Intent intent) {
        Uri uri = (Uri) getActivity().getIntent().getParcelableExtra("deep_link_uri");
        if (uri != null) {
            intent.putExtra("deep_link_uri", uri);
        }
        startActivity(intent);
    }

    public AudioManager getAudioManager() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            return baseActivity.getAudioManager();
        }
        return null;
    }

    public FusedLocationProviderClient getFusedLocationProviderClient() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            return baseActivity.getFusedLocationProviderClient();
        }
        return null;
    }

    public Responder getResponder() {
        return this.mActivity.responder;
    }

    public VideoDownloader getVideoDownloader() {
        return this.mActivity.getVideoDownloader();
    }

    public BaseActivity getBaseActivity() {
        return this.mActivity;
    }

    public void setUnBinder(Unbinder unbinder) {
        this.mUnBinder = unbinder;
    }

    public void onDestroy() {
        Unbinder unbinder = this.mUnBinder;
        if (unbinder != null) {
            unbinder.unbind();
        }
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
}
