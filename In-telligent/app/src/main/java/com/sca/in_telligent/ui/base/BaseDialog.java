package com.sca.in_telligent.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.Unbinder;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public abstract class BaseDialog extends DialogFragment implements DialogMvpView {
    private BaseActivity mActivity;
    private Unbinder mUnBinder;

    protected abstract void setUp(View view);

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showMessageSnack(String str) {
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showPopup(String str) {
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
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.showLoading();
        }
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void hideLoading() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            baseActivity.hideLoading();
        }
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

    public BaseActivity getBaseActivity() {
        return this.mActivity;
    }

    public ActivityComponent getActivityComponent() {
        BaseActivity baseActivity = this.mActivity;
        if (baseActivity != null) {
            return baseActivity.getActivityComponent();
        }
        return null;
    }

    public void setUnBinder(Unbinder unbinder) {
        this.mUnBinder = unbinder;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(relativeLayout);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(-1, -1);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setUp(view);
    }

    public void show(FragmentManager fragmentManager, String str) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (findFragmentByTag != null) {
            beginTransaction.remove(findFragmentByTag);
        }
        beginTransaction.addToBackStack((String) null);
        show(beginTransaction, str);
    }

    @Override // com.sca.in_telligent.ui.base.DialogMvpView
    public void dismissDialog(String str) {
        dismiss();
        getBaseActivity().onFragmentDetached(str);
    }

    public void onDestroy() {
        Unbinder unbinder = this.mUnBinder;
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void startActivityWithDeeplink(Intent intent) {
        Uri uri = (Uri) getActivity().getIntent().getParcelableExtra("deep_link_uri");
        if (uri != null) {
            intent.putExtra("deep_link_uri", uri);
        }
        startActivity(intent);
    }

    @Override // com.sca.in_telligent.ui.base.MvpView
    public void showNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity);
        builder.setTitle("Alert");
        builder.setMessage(R.string.please_check_your_network_connection_try_again);
        builder.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.base.BaseDialog.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}
