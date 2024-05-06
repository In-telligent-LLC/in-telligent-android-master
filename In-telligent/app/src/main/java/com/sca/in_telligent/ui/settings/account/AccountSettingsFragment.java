package com.sca.in_telligent.ui.settings.account;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.NotificationLanguage;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.openapi.data.network.model.SubscriberRequest;
import com.sca.in_telligent.openapi.data.network.model.UpdateSubscriberRequest;
import com.sca.in_telligent.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

public class AccountSettingsFragment extends BaseFragment implements AccountSettingsMvpView {

    @Inject
    AccountSettingsMvpPresenter<AccountSettingsMvpView> mPresenter;

    public static final String TAG = "AccountSettingsFragment";

    @BindView(R.id.settings_account_name_edittext)
    TextView nameEdittext;

    @BindView(R.id.settings_account_mail_edittext)
    TextView mailEdittext;

    @BindView(R.id.settings_account_language_selected)
    TextView alertLanguageSelect;

    private String languageValue = "";
    private String languageName = "";
    private String name = "";
    private String mail = "";

    private ArrayList<NotificationLanguage> languages = new ArrayList<>();

    public interface AccountSettingsSelector {

        void updateSubscriberLanguage(String languageName, String languageValue);
    }

    private AccountSettingsSelector settingsSelector;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        name = getArguments().getString("name");
        mail = getArguments().getString("mail");
        languageValue = getArguments().getString("languageValue");
        languageName = getArguments().getString("languageName");
        settingsSelector = (AccountSettingsSelector) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            nameEdittext = view.findViewById(R.id.settings_account_name_edittext);
            mailEdittext = view.findViewById(R.id.settings_account_mail_edittext);
            alertLanguageSelect = view.findViewById(R.id.settings_account_language_selected);

            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

            alertLanguageSelect.setOnClickListener(this::alertLanguageClick);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        mPresenter.listLanguages();

        nameEdittext.setText(name);
        mailEdittext.setText(mail);
        alertLanguageSelect.setText(languageName);
    }

    @OnClick(R.id.settings_account_language_selected)
    void alertLanguageClick(View v) {
        String[] languageOptions = new String[languages.size()];
        int i = 0;
        for (NotificationLanguage l : languages) {
            languageOptions[i++] = l.getName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle(R.string.set_language_to)
                .setItems(languageOptions, (dialogInterface, pos) -> {
                    languageName = languages.get(pos).getName();
                    languageValue = languages.get(pos).getLanguage();
                    alertLanguageSelect.setText(languageName);
                    UpdateSubscriberRequest updateSubscriberRequest = new UpdateSubscriberRequest();
                    SubscriberRequest subscriberRequest = new SubscriberRequest();
                    subscriberRequest.setLanguage(languageValue);
                    updateSubscriberRequest.setSubscriberRequest(subscriberRequest);
                    mPresenter.syncMetadata(updateSubscriberRequest);

                })
                .show();
    }

    @Override
    public void loadLanguages(ArrayList<NotificationLanguage> notificationLanguages) {
        languages = notificationLanguages;
        Observable.fromIterable(languages)
                .filter(notificationLanguage -> notificationLanguage.getLanguage().equals(languageValue))
                .defaultIfEmpty(
                        NotificationLanguage.getFromDeviceLanguage(Locale.getDefault())
                )
                .toList().
                subscribe(notificationLanguagesSelected -> alertLanguageSelect
                        .setText(notificationLanguagesSelected.get(0).getName()));
    }

    @Override
    public void subscriberLanguageResult(Subscriber subscriber) {
        settingsSelector
                .updateSubscriberLanguage(subscriber.getLanguageName(), subscriber.getLanguage());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetach();
    }
}
