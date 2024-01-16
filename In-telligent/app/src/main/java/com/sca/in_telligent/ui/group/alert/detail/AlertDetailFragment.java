package com.sca.in_telligent.ui.group.alert.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.util.CommonUtils;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AlertDetailFragment extends BaseFragment implements AlertDetailMvpView {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ARG_NOTIFICATION = "arg_notification";
    public static final String TAG = "AlertDetailFragment";
    @BindView(R.id.alert_detail_title_text)
    TextView alertTitle;
    @BindView(R.id.alert_detail_attachment_text)
    TextView attachmentText;
    @BindView(R.id.alert_detail_description_text)
    TextView descriptionText;
    @BindView(R.id.alert_detail_info_text)
    TextView infoText;
    @Inject
    AlertDetailMvpPresenter<AlertDetailMvpView> mPresenter;
    private Notification notification;

    public static AlertDetailFragment newInstance(Notification notification) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTIFICATION, notification);
        AlertDetailFragment alertDetailFragment = new AlertDetailFragment();
        alertDetailFragment.setArguments(bundle);
        return alertDetailFragment;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.notification = (Notification) getArguments().getParcelable(ARG_NOTIFICATION);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_alert_detail, viewGroup, false);
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);
        }
        return inflate;
    }

    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    protected void setUp(View view) {
        Notification notification = this.notification;
        if (notification != null) {
            this.alertTitle.setText(notification.getTitle());
            this.infoText.setText(CommonUtils.getDateString(this.notification.getStartDate()) + ", " + getString(InboxNotificationTypeMapper.map(this.notification).getName()));
            this.descriptionText.setText(this.notification.getDescription());
            this.attachmentText.setText(getString(R.string.attachment_1_total, Integer.valueOf(this.notification.getNotificationAttachments().size())));
        }
    }
}
