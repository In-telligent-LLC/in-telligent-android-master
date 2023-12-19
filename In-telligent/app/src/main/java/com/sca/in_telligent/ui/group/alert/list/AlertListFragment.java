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
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class AlertListFragment extends BaseFragment implements AlertListMvpView,
        AlertListAdapter.Callback {

    public static final String TAG = "AlertListFragment";

    @Inject
    AlertListMvpPresenter<AlertListMvpView> mPresenter;

    @Inject
    AlertListAdapter alertListAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.alert_list_recyclerview)
    RecyclerView alertListRecyclerView;

    @BindView(R.id.swipe_refresh_layout_alerts)
    SwipeRefreshLayout swipeRefreshLayoutAlerts;

    AlertListSelector alertListSelector;

    ArrayList<Notification> notifications = new ArrayList<>();

    private int buildingId;

    public interface AlertListSelector {

        void onAlertDetailSelected(Notification notification, int buildingId);
    }

    public static AlertListFragment newInstance(int buildingId) {
        Bundle args = new Bundle();
        args.putInt("buildingId", buildingId);
        AlertListFragment fragment = new AlertListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        alertListSelector = (AlertListSelector) context;
        buildingId = getArguments().getInt("buildingId");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_list, container, false);
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
                .setPositiveButton(getString(android.R.string.ok), (dialogInterface, i) -> {
                    mPresenter.deleteAlert(notification.getId() + "", position);
                }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {

        });
        alertDialog.show();
    }

    @Override
    public void onAlertSelected(int position) {
        alertListSelector.onAlertDetailSelected(notifications.get(position), buildingId);
    }

    @Override
    public void alertDeleted(int position) {
        if (notifications.size() > position) {
            notifications.remove(position);
            alertListAdapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
