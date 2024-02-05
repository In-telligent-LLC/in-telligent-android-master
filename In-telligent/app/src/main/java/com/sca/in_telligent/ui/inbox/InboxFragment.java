package com.sca.in_telligent.ui.inbox;

import static com.sca.in_telligent.ui.inbox.InboxSpinnerItemType.NONE;
import static com.sca.in_telligent.ui.inbox.InboxSpinnerItemType.SAVED;
import static com.sca.in_telligent.ui.inbox.InboxSpinnerItemType.UNREAD;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sca.in_telligent.R;
import com.sca.in_telligent.di.component.ActivityComponent;
import com.sca.in_telligent.openapi.data.network.model.AlertDeleteRequest;
import com.sca.in_telligent.openapi.data.network.model.AlertOpenedRequest;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.ui.base.BaseFragment;
import com.sca.in_telligent.util.AppResponder.ResponderListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;

public class InboxFragment extends BaseFragment implements InboxMvpView, InboxAdapter.Callback,
        ResponderListener {

    public static final String TAG = "InboxFragment";

    @Inject
    InboxAdapter inboxAdapter;

    @Inject
    InboxMvpPresenter<InboxMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.inbox_recyclerview)
    RecyclerView inboxNotificationRecyclerview;

    @Inject
    InboxSpinnerAdapter inboxSpinnerAdapter;

    @BindView(R.id.inbox_spinner)
    Spinner inboxSpinner;

    @BindView(R.id.swipe_refresh_layout_inbox)
    SwipeRefreshLayout swipeRefreshLayoutInbox;

    InboxSelector inboxSelector;
    private ArrayList<Notification> notifications;
    private ArrayList<Notification> savedNotifications;
    private boolean openSaved = false;
    private boolean openUnread = false;
    private InboxSpinnerItemType currentSpinnerType = InboxSpinnerItemType.NONE;

    public interface InboxSelector {

        void itemClicked(int position, Notification notification, int lastIndex);
    }

    public static InboxFragment newInstance(boolean openSaved) {
        Bundle args = new Bundle();
        args.putBoolean("openSaved", openSaved);
        InboxFragment fragment = new InboxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inboxSelector = (InboxSelector) context;
        openSaved = getArguments().getBoolean("openSaved");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        ActivityComponent component = getActivityComponent();
        inboxNotificationRecyclerview = view.findViewById(R.id.inbox_recyclerview);
        inboxSpinner = view.findViewById(R.id.inbox_spinner);
        swipeRefreshLayoutInbox = view.findViewById(R.id.swipe_refresh_layout_inbox);


        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;
    }

    @Override
    protected void setUp(View view) {
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add(getString(R.string.none));
        spinnerItems.add(getString(R.string.unread));
        spinnerItems.add(getString(R.string.saved));
        inboxSpinnerAdapter.addItems(spinnerItems);
        inboxSpinner.setAdapter(inboxSpinnerAdapter);
        if (openSaved) {
            inboxSpinner.setSelection(2);
        } else {
            inboxSpinner.setSelection(0);
        }

        inboxAdapter.setCallback(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        inboxNotificationRecyclerview.setLayoutManager(mLayoutManager);
        inboxNotificationRecyclerview.setAdapter(inboxAdapter);

        swipeRefreshLayoutInbox.setOnRefreshListener(() -> {
            mPresenter.getInbox("0", false);
            inboxSpinner.setSelection(0);
        });
    }

    @OnItemSelected(R.id.inbox_spinner)
    public void spinnerItemSelected(Spinner spinner, int position) {
        InboxSpinnerItemType spinnerItemType = NONE;
        switch (spinnerItemType.getSpinnerMode(position)) {
            case NONE:
                currentSpinnerType = NONE;
                mPresenter.getInbox("0");
                break;
            case UNREAD:
                currentSpinnerType = UNREAD;
                createUnreadList();
                break;
            case SAVED:
                currentSpinnerType = SAVED;
                mPresenter.getSavedMessages();
                break;
        }
    }

    public void reloadSavedMessages(){
        currentSpinnerType = SAVED;
        mPresenter.getSavedMessages();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onNotificationSelected(int position, Notification notification) {
        if (notification == null) {
            Log.e(TAG, "onAlertDelete: notification cannot be null");
            showMessage(R.string.there_was_error_sending_request);
            return;
        }

        if (notification.getNotificationSubscriber().getOpened() == null && !notification.isOpened()) {
            notifications.get(position).setOpened(true);
            inboxAdapter.notifyItemChanged(position);
            openedAlert(notification.getId());
        }
        inboxSelector.itemClicked(position, notification, notifications.size());
    }

    @Override
    public void onSavedMessageSelected(int position, Notification notification) {
        inboxSelector.itemClicked(position, notification, savedNotifications != null ? (savedNotifications.size()) : 0);
    }

    @SuppressLint("CheckResult")
    private void createUnreadList() {
        if (notifications != null && notifications.size() > 0) {
            ArrayList<Notification> unreadList = new ArrayList<>();
            Observable.fromIterable(notifications)
                    .filter(notification -> notification.getNotificationSubscriber().getOpened() == null
                            && !notification.isOpened()).toList()
                    .subscribe(unreadNotifications -> {
                        unreadList.addAll(unreadNotifications);
                        loadInbox(unreadList, 0);
                    });

        } else {
            openUnread = true;
            mPresenter.getInbox("0");
        }
    }

    @Override
    public void loadInbox(List<Notification> notificationList, int page) {
        swipeRefreshLayoutInbox.setRefreshing(false);
        notifications = (ArrayList<Notification>) notificationList;
        if (openUnread) {
            openUnread = false;
            createUnreadList();
        } else {
            inboxAdapter.addItems(notifications, page);
        }


    }

    @Override
    public void loadSavedMessages(List<Notification> notificationList) {
        savedNotifications = (ArrayList<Notification>) notificationList;
        Observable.fromIterable(savedNotifications)
                .doOnNext(notification -> {
                    notification.setOpened(true);
                    notification.setSaved(true);
                })
                .toList()
                .subscribe(savedMessages -> inboxAdapter.addItems(savedNotifications, 0));
        if (openSaved) {
            inboxAdapter.setCallback(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            inboxNotificationRecyclerview.setLayoutManager(mLayoutManager);
            inboxNotificationRecyclerview.setAdapter(inboxAdapter);
        }
    }


    @Override
    public void onAlertDelete(@NonNull Notification notification, int position) {

        if (notification == null) {
            Log.e(TAG, "onAlertDelete: notification cannot be null");
            showMessage(R.string.there_was_error_sending_request);
            return;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(getString(R.string.delete));
        alertDialog.setMessage(
                getString(R.string.are_you_sure_you_want_to_delete));
        alertDialog
                .setPositiveButton(getString(android.R.string.ok), (dialogInterface, i) -> {
                    AlertDeleteRequest alertDeleteRequest = new AlertDeleteRequest(notification.getId() + "");
                    mPresenter.deleteAlert(alertDeleteRequest, position);
                }).setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {

        });
        alertDialog.show();
    }


    @Override
    public void alertDeleted(int position) {
        if (notifications.size() > position) {
            inboxAdapter.removeItem(position);
        }
    }

    private void openedAlert(int notificationId) {
        AlertOpenedRequest alertOpenedRequest = new AlertOpenedRequest();
        alertOpenedRequest.setNotificationId(notificationId);

        getResponder().setResponderListener(this);
        getResponder().alertOpened(alertOpenedRequest);
    }

    @Override
    public void responderFinished(boolean finished) {
        boolean result = finished;
    }

    public ArrayList<Notification> getSavedNotifications() {
        return savedNotifications;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
}
