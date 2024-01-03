package com.sca.in_telligent.ui.group.alert.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseFragment;

import java.util.ArrayList;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class AlertListFragment extends BaseFragment implements AlertListMvpView, AlertListAdapter.Callback {
    public static final String TAG = "AlertListFragment";
    @Inject
    AlertListAdapter alertListAdapter;
    @BindView(R.id.alert_list_recyclerview)
    RecyclerView alertListRecyclerView;
    AlertListSelector alertListSelector;
    private int buildingId;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    AlertListMvpPresenter<AlertListMvpView> mPresenter;
    ArrayList<Notification> notifications = new ArrayList<>();
    @BindView(R.id.swipe_refresh_layout_alerts)
    SwipeRefreshLayout swipeRefreshLayoutAlerts;

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public interface AlertListSelector {
        void onAlertDetailSelected(Notification notification, int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$onDeleteAlert$2(DialogInterface dialogInterface, int i) {
    }

    public static AlertListFragment newInstance(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("buildingId", i);
        AlertListFragment alertListFragment = new AlertListFragment();
        alertListFragment.setArguments(bundle);
        return alertListFragment;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.alertListSelector = (AlertListSelector) context;
        this.buildingId = getArguments().getInt("buildingId");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_alert_list, viewGroup, false);
        ActivityComponent activityComponent = getActivityComponent();
        if (activityComponent != null) {
            activityComponent.inject(this);
            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);
        }
        return inflate;
    }

    @Override // com.sca.in_telligent.ui.base.BaseFragment
    protected void setUp(View view) {
        try {
            final String num = Integer.toString(this.buildingId);
            this.mPresenter.getNotifications(num, true);
            this.swipeRefreshLayoutAlerts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.sca.in_telligent.ui.group.alert.list.AlertListFragment$$ExternalSyntheticLambda2
                public void onRefresh() {
                    AlertListFragment.this.m192x4d29168(num);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: lambda$setUp$0$com-sca-in_telligent-ui-group-alert-list-AlertListFragment  reason: not valid java name */
    public /* synthetic */ void m192x4d29168(String str) {
        this.mPresenter.getNotifications(str, false);
    }

    @Override // com.sca.in_telligent.ui.group.alert.list.AlertListMvpView
    public void loadNotifications(ArrayList<Notification> arrayList) {
        this.swipeRefreshLayoutAlerts.setRefreshing(false);
        this.notifications = arrayList;
        this.alertListAdapter.addItems(arrayList, 0);
        this.alertListAdapter.setCallback(this);
        this.mLayoutManager.setOrientation(1);
        this.alertListRecyclerView.setLayoutManager(this.mLayoutManager);
        this.alertListRecyclerView.setAdapter(this.alertListAdapter);
    }

    @Override // com.sca.in_telligent.ui.group.alert.list.AlertListAdapter.Callback
    public void onDeleteAlert(final Notification notification, final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete));
        builder.setMessage(getString(R.string.are_you_sure_you_want_to_delete));
        builder.setPositiveButton(getString(17039370), new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.alert.list.AlertListFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                AlertListFragment.this.m191xb538db72(notification, i, dialogInterface, i2);
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.group.alert.list.AlertListFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                AlertListFragment.lambda$onDeleteAlert$2(dialogInterface, i2);
            }
        });
        builder.show();
    }

    /* renamed from: lambda$onDeleteAlert$1$com-sca-in_telligent-ui-group-alert-list-AlertListFragment  reason: not valid java name */
    public /* synthetic */ void m191xb538db72(Notification notification, int i, DialogInterface dialogInterface, int i2) {
        this.mPresenter.deleteAlert(notification.getId() + "", i);
    }

    @Override // com.sca.in_telligent.ui.group.alert.list.AlertListAdapter.Callback
    public void onAlertSelected(int i) {
        this.alertListSelector.onAlertDetailSelected(this.notifications.get(i), this.buildingId);
    }

    @Override // com.sca.in_telligent.ui.group.alert.list.AlertListMvpView
    public void alertDeleted(int i) {
        if (this.notifications.size() > i) {
            this.notifications.remove(i);
            this.alertListAdapter.notifyItemRemoved(i);
        }
    }

    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }
}
