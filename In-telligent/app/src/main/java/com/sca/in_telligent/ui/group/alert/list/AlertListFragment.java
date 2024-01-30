package com.sca.in_telligent.ui.group.alert.list;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public interface AlertListSelector {
        void onAlertDetailSelected(Notification notification, int i);
    }


    public static AlertListFragment newInstance(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("buildingId", i);
        AlertListFragment alertListFragment = new AlertListFragment();
        alertListFragment.setArguments(bundle);
        return alertListFragment;
    }

    @Override
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
            swipeRefreshLayoutAlerts = inflate.findViewById(R.id.swipe_refresh_layout_alerts);
            alertListRecyclerView = inflate.findViewById(R.id.alert_list_recyclerview);

            setUnBinder(ButterKnife.bind(this, inflate));
            this.mPresenter.onAttach(this);
        }
        return inflate;
    }

    @Override
    protected void setUp(View view) {
        String id;
        try {
            id = Integer.toString(buildingId);
            mPresenter.getNotifications(id, true);

            swipeRefreshLayoutAlerts.setOnRefreshListener(() ->
                    mPresenter.getNotifications(id, false));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void loadNotifications(ArrayList<Notification> notificationsItems) {
        swipeRefreshLayoutAlerts.setRefreshing(false);
        notifications = notificationsItems;
        alertListAdapter.addItems(notifications, 0);
        alertListAdapter.setCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        alertListRecyclerView.setLayoutManager(mLayoutManager);
        alertListRecyclerView.setAdapter(alertListAdapter);
    }


    @Override
    public void onDeleteAlert(Notification notification, int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getString(R.string.delete));
        alertDialog.setMessage(
                getString(R.string.are_you_sure_you_want_to_delete));
        alertDialog
                .setPositiveButton(getString(android.R.string.ok), (dialogInterface, i) -> mPresenter.deleteAlert(notification.getId() + "", position)).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {

                });
        alertDialog.show();
    }

    @Override
    public void onAlertSelected(int position) {
        this.alertListSelector.onAlertDetailSelected(this.notifications.get(position), this.buildingId);
    }


    @Override
    public void alertDeleted(int position) {
        if (notifications.size() > position) {
            notifications.remove(position);
            alertListAdapter.notifyItemRemoved(position);
        }
    }

    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }
}
