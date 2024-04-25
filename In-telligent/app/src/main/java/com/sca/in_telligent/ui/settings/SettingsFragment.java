package com.sca.in_telligent.ui.settings;

import static com.sca.in_telligent.util.AlertUtil.showConfirmationAlert;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import androidx.fragment.app.FragmentTabHost;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.settings.account.AccountSettingsFragment;
import com.sca.in_telligent.ui.settings.help.HelpFragment;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class SettingsFragment extends BaseFragment implements SettingsMvpView {


    public interface SettingsCallback {
        void onLogout();
    }

    public static final String TAG = "SettingsFragment";

    @Inject
    SettingsMvpPresenter<SettingsMvpView> mPresenter;

    @BindView(android.R.id.tabhost)
    FragmentTabHost tabHost;

    @BindView(R.id.settings_logout_text)
    TextView settingsLogout;

    private Subscriber subscriber;
    private SettingsCallback settingsCallback;

    public static SettingsFragment newInstance(Subscriber subscriber) {
        Bundle args = new Bundle();
        args.putSerializable("subscriber", subscriber);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        settingsCallback = (SettingsCallback) context;
        subscriber = (Subscriber) getArguments().getSerializable("subscriber");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ActivityComponent component = getActivityComponent();
        tabHost = view.findViewById(android.R.id.tabhost);
        settingsLogout = view.findViewById(R.id.settings_logout_text);
        settingsLogout.setOnClickListener(this::logoutClick);
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.notifications))
                        .setIndicator(getResources().getString(R.string.notifications), null),
                NotificationSettingsFragment.class, getNotificationSettingBundle());
        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.account))
                        .setIndicator(getResources().getString(R.string.account), null),
                AccountSettingsFragment.class, getAccountBundle());
        tabHost.addTab(
                tabHost.newTabSpec(getString(R.string.support))
                        .setIndicator(getResources().getString(R.string.support), null),
                HelpFragment.class, null);

        TabWidget widget = tabHost.getTabWidget();
        for (int i = 0; i < widget.getChildCount(); i++) {
            View tabView = widget.getChildAt(i);
            tabView.setPadding(0, 0, 0, 0);
            TextView tv = tabView.findViewById(android.R.id.title);
            tv.setSingleLine();
            tv.setTextSize(14);
        }
    }

    private Bundle getAccountBundle() {
        Bundle args = new Bundle();
        if (subscriber != null) {
            args.putString("name", subscriber.getName());
            args.putString("mail", subscriber.getEmail());
            args.putString("languageValue", subscriber.getLanguage());
            args.putString("languageName", subscriber.getLanguageName());
        }
        return args;
    }

    private Bundle getNotificationSettingBundle() {
        Bundle args = new Bundle();
        if (subscriber != null) {
            args.putSerializable("buildings", subscriber.getBuildings());
            args.putBoolean("weather", subscriber.isWeatherAlertEnabled());
            args.putBoolean("lightning", subscriber.isLightningAlertEnabled());
        }
        return args;
    }

    @OnClick({R.id.settings_back_button, R.id.settings_back_text})
    void backClick(View v) {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @OnClick(R.id.settings_logout_text)
    void logoutClick(View v) {
        showConfirmationAlert(getContext(),
                R.string.are_you_sure_you_want_to_logout,
                (dialog, which) -> mPresenter.logout());
    }

    @Override
    public void gotoLogin() {

        if (settingsCallback != null) {
            settingsCallback.onLogout();
        }

        startActivityWithDeeplink(LoginActivity.getStartIntent(getActivity()));
        getActivity().finishAffinity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetach();
    }
}
