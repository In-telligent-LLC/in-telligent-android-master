package com.sca.in_telligent.ui.group.alert.detail;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.ui.inbox.InboxNotificationType;
import com.sca.in_telligent.util.CommonUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class AlertDetailFragment extends BaseFragment implements AlertDetailMvpView {

    public static final String TAG = "AlertDetailFragment";
    public static final String ARG_NOTIFICATION = "arg_notification";
    public static final String ARG_SHOW_DELIVERY_BOX = "arg_show_delivery_box";

    @Inject
    AlertDetailMvpPresenter<AlertDetailMvpView> mPresenter;

    AlertDetailSelector alertDetailSelector;

    @BindView(R.id.alert_detail_delivery_button)
    TextView deliveryInformationButton;

    @BindView(R.id.alert_detail_title_text)
    TextView alertTitle;

    @BindView(R.id.alert_detail_info_text)
    TextView infoText;

    @BindView(R.id.alert_detail_attachment_text)
    TextView attachmentText;

    @BindView(R.id.alert_detail_description_text)
    TextView descriptionText;

    private Notification notification;
    private static boolean showDeliveryBox;

    public static AlertDetailFragment newInstance(Notification notification) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTIFICATION, notification);
        args.putBoolean(ARG_SHOW_DELIVERY_BOX, showDeliveryBox);
        AlertDetailFragment fragment = new AlertDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface AlertDetailSelector {

        void onDeliveryInfoClicked(String buildingId, String notificationId, String messageName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        alertDetailSelector = (AlertDetailSelector) context;
        notification = (Notification) getArguments().getSerializable(ARG_NOTIFICATION);
        showDeliveryBox = getArguments().getBoolean(ARG_SHOW_DELIVERY_BOX);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_detail, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @OnClick(R.id.alert_detail_delivery_button)
    void deliveryInfoClick() {
        String notificationId = Integer.toString(notification.getId());
        String buildingId = Integer.toString(notification.getBuildingId());
        alertDetailSelector.onDeliveryInfoClicked(buildingId, notificationId, notification.getTitle());
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        if (notification != null) {
            alertTitle.setText(notification.getTitle());

            InboxNotificationType inboxNotificationType = InboxNotificationTypeMapper.map(notification);
            infoText.setText(CommonUtils.getDateString(notification.getStartDate()) + ", " + getString(inboxNotificationType.getName()));
            descriptionText.setText(notification.getDescription());
            String attachmentString = getString(R.string.attachment_1_total, notification.getNotificationAttachments().size());
            attachmentText
                    .setText(attachmentString);
            deliveryInformationButton.setVisibility(showDeliveryBox ? View.VISIBLE : View.GONE);
        }
    }
}
