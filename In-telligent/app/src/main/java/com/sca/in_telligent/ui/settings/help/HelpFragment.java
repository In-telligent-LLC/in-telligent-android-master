package com.sca.in_telligent.ui.settings.help;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sca.in_telligent.BuildConfig1;
import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.SupportRequest;
import com.sca.in_telligent.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpFragment extends BaseFragment implements HelpMvpView {

    private static final int MAX_TAPS_TO_SEE_VERSION = 4;

    @Inject
    HelpMvpPresenter<HelpMvpView> mPresenter;

    @BindView(R.id.settings_support_number_field)
    TextView phoneLink;

    @BindView(R.id.settings_support_send_button)
    TextView sendButton;

    @BindView(R.id.settings_support_message_edittext)
    EditText supportMessageText;

    @BindView(R.id.settings_support_hours_field)
    TextView hoursText;


    int amountOfTapsToShowVersion = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_support, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        phoneLink.setText("1-312-526-3286");
        Linkify.addLinks(phoneLink, Patterns.PHONE, "tel:", Linkify.sPhoneNumberMatchFilter,
                Linkify.sPhoneNumberTransformFilter);

        hoursText.setLongClickable(true);
        hoursText.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), String.format("Version %s (%s)",
                    BuildConfig1.VERSION_NAME,
                    BuildConfig1.VERSION_CODE),
                    Toast.LENGTH_LONG).show();

            return false;
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetach();
    }

    @Override
    public void phonePermissionResult(boolean granted) {
        if (granted) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent
                    .setData(Uri.parse("tel:" + "13125263286"));
            startActivity(intent);
        }
    }

    @Override
    public void messageSent(boolean success) {
        showMessage(R.string.your_request_has_been_sent_to_support_team);
    }

    @OnClick(R.id.settings_support_send_button)
    void sendSupportMail(View v) {
        if (supportMessageText.getText().toString().length() > 3) {
            SupportRequest supportRequest = new SupportRequest();
            supportRequest.setMessage(supportMessageText.getText().toString());
            mPresenter.sendSupportMail(supportRequest);
        }
    }
}
