package com.sca.in_telligent.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.google.android.gms.tasks.Task;
import com.sca.in_telligent.BuildConfig;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.BuildingIdItem;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.openapi.data.network.model.NavListItem;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.openapi.receiver.BeaconScannerBroadcastReceiver;
import com.sca.in_telligent.service.GeofencesUpdateService;
import com.sca.in_telligent.ui.auth.logout.LogoutActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.contact.call.ContactCallFragment;
import com.sca.in_telligent.ui.contact.list.ContactListFragment;
import com.sca.in_telligent.ui.contact.list.ContactListFragment.ContactListCallback;
import com.sca.in_telligent.ui.contact.message.ContactMessageFragment;
import com.sca.in_telligent.ui.group.alert.delivery.DeliveryInfoFragment;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailFragment;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailFragment.AlertDetailSelector;
import com.sca.in_telligent.ui.group.alert.list.AlertListFragment;
import com.sca.in_telligent.ui.group.alert.list.AlertListFragment.AlertListSelector;
import com.sca.in_telligent.ui.group.detail.GroupDetailContainerFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailSelector;
import com.sca.in_telligent.ui.group.detail.created.CreatedGroupDetailFragment;
import com.sca.in_telligent.ui.group.generate.GenerateGroupFragment;
import com.sca.in_telligent.ui.group.generate.GenerateGroupFragment.GenerateGroupSelector;
import com.sca.in_telligent.ui.group.list.GroupListFragment;
import com.sca.in_telligent.ui.group.list.GroupListFragment.GroupListSelector;
import com.sca.in_telligent.ui.group.member.MemberListFragment;
import com.sca.in_telligent.ui.group.member.MemberListFragment.MemberListSelector;
import com.sca.in_telligent.ui.group.member.edit.EditMemberFragment;
import com.sca.in_telligent.ui.group.member.edit.EditMemberFragment.EditCommunityMemberCallback;
import com.sca.in_telligent.ui.group.member.invite.InviteMemberFragment;
import com.sca.in_telligent.ui.group.member.invite.InviteMemberFragment.InviteMemberSelector;
import com.sca.in_telligent.ui.inbox.InboxFragment;
import com.sca.in_telligent.ui.inbox.InboxFragment.InboxSelector;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment.NotificationDetailCallback;
import com.sca.in_telligent.ui.preview.MessageViewDialog;
import com.sca.in_telligent.ui.settings.SettingsFragment;
import com.sca.in_telligent.ui.settings.account.AccountSettingsFragment.AccountSettingsSelector;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment.NotificationSettingsSelector;
import com.sca.in_telligent.util.AppUpdateHandler;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.mapper.UriToDataMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class MainActivity extends BaseActivity implements MainMvpView,
        NavigationDrawerAdapter.Callback, BottomNavigationView.OnNavigationItemSelectedListener,
        InboxSelector, NotificationDetailCallback, ContactListCallback, GroupListSelector,
        GroupDetailSelector, MemberListSelector, EditCommunityMemberCallback,
        InviteMemberSelector, AlertListSelector, AlertDetailSelector, NotificationSettingsSelector,
        AccountSettingsSelector, GenerateGroupSelector, MessageViewDialog.PushNotificationDetailCallback, SettingsFragment.SettingsCallback {

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @Inject
    NavigationDrawerAdapter adapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    GeofenceClient mGeofenceClient;

    @Inject
    FirebaseJobDispatcher firebaseJobDispatcher;

    @BindView(R.id.navigation_view_listview)
    RecyclerView navigationViewListView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.sos_button)
    ImageView buttonSos;

    @BindView(R.id.content_frame)
    FrameLayout frameLayout;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.total_silence_number)
    TextView totalSilenceNumber;

    @BindView(R.id.total_silence_off)
    TextView totalSilenceOff;

    @BindView(R.id.total_silence_on)
    TextView totalSilenceOn;

    @BindView(R.id.ad_footer_image)
    ImageView adImageView;

    ArrayList<Building> groups = new ArrayList<>();
    ArrayList<Building> buildings = new ArrayList<>();
    ArrayList<Building> personalCommunities = new ArrayList<>();
    ArrayList<BuildingIdItem> userBuildingIds = new ArrayList<>();
    ArrayList<Building> contactableBuildings = new ArrayList<>();
    ArrayList<Building> suggestedBuildings = new ArrayList<>();

    Subscriber subscriber;
    Location mLastKnownLocation;
    private String countryName;
    private boolean savedClicked = false;
    private boolean groupCreated = false;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    private static final String TAG = "MainActivity";
    private BeaconScannerBroadcastReceiver beaconScannerBroadcastReceiver;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null
                && intent.getExtras().getSerializable("pushNotification") != null) {
            handlePush(intent.getExtras());
        }

        checkDeepLinksParams();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initCrashlytics();

        checkAppUpdates();

        setContentView(R.layout.activity_main);

        if (getIntent().getExtras().getSerializable("pushNotification") != null) {
            handlePush(getIntent().getExtras());
        }

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(MainActivity.this);
//        TODO:Uncomment this to start fetching ads
//        mPresenter.startFetchingAds();

        setUp();

        checkDeepLinksParams();

        mGeofenceClient.populateIntelligentFences(false);
    }

    private void checkAppUpdates() {
        new AppUpdateHandler().start(this);
    }

    private void initCrashlytics() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlyticsKit);
    }

    private void scheduleGeofencesRefreshJob() {
        Job job = GeofencesUpdateService.create(firebaseJobDispatcher);
        firebaseJobDispatcher.mustSchedule(job);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Clearing saved state to avoid crash when trying to open documents
        outState.clear();
    }

    @Override
    protected void setUp() {
        mPresenter.requestLocationPermissions(false);
        configureNavigationDrawer();
        configureToolbar();
        initSilence();
        configureTotalSilence();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mPresenter.getSubscriber();
    }


    private void checkDeepLinksParams() {
        Uri data = getIntent().getData();

        if (data == null) {
            Log.d(TAG, "There's no data associated with this intent");
            return;
        }

        if ("subscribe".equals(data.getPathSegments().get(0))) {
            UriToDataMapper.SubscribeToCommunityData subscribeToCommunityRequest = UriToDataMapper
                    .uriToSubscribeToCommunityRequest(data);
            fetchCommunityInfo(subscribeToCommunityRequest);
        }
    }

    private void fetchCommunityInfo(UriToDataMapper.SubscribeToCommunityData data) {
        mPresenter.getCommunityInfo(data.getCommunityId(), data.getInviteId());
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void showSubscribeToCommunityDialog(String name, int communityId, int inviteId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.you_were_invited_to_join_a_comunnity, name));
        builder.setMessage(R.string.are_you_sure_you_want_to_join);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> mPresenter.subscribeToCommunity(communityId, inviteId));
        builder.setNegativeButton(android.R.string.cancel, null);
        Dialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        if (totalSilenceTimer != null) {
            totalSilenceTimer.cancel();
        }
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public void onFragmentAttached() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commit();
        }
    }

    private void configureToolbar() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) appBarLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_icon);
    }

    private void configureNavigationDrawer() {
        adapter.setCallback(this);
        adapter.addItems(addItemsToList());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        navigationViewListView.setLayoutManager(mLayoutManager);
        navigationViewListView.setItemAnimator(new DefaultItemAnimator());
        navigationViewListView.setAdapter(adapter);
//    onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
    }

    private NumberPicker silenceTimePicker;
    android.support.v7.app.AlertDialog silenceTimeDialog;
    private CountDownTimer totalSilenceTimer;

    private void configureTotalSilence() {
        silenceTimePicker = new NumberPicker(this);
        silenceTimeDialog = setUpAlertDialog(silenceTimePicker, getString(R.string.hours_of_silence),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date now = new Date();
                        getDataManager().setLifeSafetyOverrideExpire(CommonUtils.getSilenceDateString(
                                new Date(now.getTime() + 1000 * 60 * 60 * silenceTimePicker.getValue())));
                        initSilence();
                    }
                });

        silenceTimePicker.setMinValue(1);
        silenceTimePicker.setMaxValue(12);
        silenceTimePicker.scrollTo(0, 0);
        silenceTimePicker.setWrapSelectorWheel(false);
    }

    private void initSilence() {
        Date now = new Date();
        Date expires = CommonUtils.getSilenceDate(getDataManager().getLifeSafetyOverrideExpire());
        if (totalSilenceTimer != null) {
            totalSilenceTimer.cancel();
        }

        if (expires == null || now.after(expires)) {
            totalSilenceOff.setSelected(true);
            totalSilenceOn.setSelected(false);
            totalSilenceNumber.setText("00:00:00");
        } else {
            totalSilenceOff.setSelected(false);
            totalSilenceOn.setSelected(true);
            long millisToExpire = expires.getTime() - now.getTime();

            totalSilenceNumber.setText(DateUtils.formatElapsedTime(millisToExpire / 1000));

            totalSilenceTimer = new CountDownTimer(millisToExpire, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    totalSilenceNumber.setText(DateUtils.formatElapsedTime(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    initSilence();
                }
            }.start();
        }
    }

    @OnClick(R.id.total_silence_on)
    void silenceOnClick(View v) {
        if (silenceTimeDialog != null) {
            silenceTimeDialog.show();
        }
    }

    @OnClick(R.id.total_silence_off)
    void silenceOffClick(View v) {
        getDataManager().setLifeSafetyOverrideExpire(null);
        initSilence();
    }

    List<NavListItem> addItemsToList() {
        ArrayList<NavListItem> navListObjects = new ArrayList<>();
        NavListItem navListObject1 = new NavListItem(getResources().getString(R.string.settings),
                R.drawable.icon_settings);
        NavListItem navListObject2 = new NavListItem(getResources().getString(R.string.saved_messages),
                R.drawable.icon_saved_messages);
        navListObjects.add(navListObject1);
        navListObjects.add(navListObject2);
        return navListObjects;
    }

    @Override
    public void onItemClicked(int position) {
        if (!isNetworkConnected()) {
            showNetworkDialog();
        } else {
            switch (position) {
                case 0:
                    getSupportFragmentManager()
                            .beginTransaction().addToBackStack(SettingsFragment.TAG)
                            .add(R.id.content_frame, SettingsFragment.newInstance(subscriber),
                                    SettingsFragment.TAG)
                            .commit();
                    drawerLayout.closeDrawers();
                    break;
                case 1:
                    savedClicked = true;
                    bottomNavigationView.setSelectedItemId(R.id.action_inbox);
                    break;
                default:
                    startActivity(LogoutActivity.getStartIntent(this));
            }
        }
    }


    private void goToCreateGroupFragment() {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(GenerateGroupFragment.TAG)
                .replace(R.id.content_frame, GenerateGroupFragment.newInstance(null),
                        GenerateGroupFragment.TAG)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (!isNetworkConnected()) {
            showNetworkDialog();
        } else {
            switch (menuItem.getItemId()) {
                case R.id.action_groups:
                    GroupListFragment groupListFragment = (GroupListFragment) getSupportFragmentManager()
                            .findFragmentByTag(GroupListFragment.TAG);
                    if (groupListFragment != null && !groupCreated) {
                        getSupportFragmentManager()
                                .beginTransaction().addToBackStack(GroupListFragment.TAG)
                                .replace(R.id.content_frame, groupListFragment,
                                        GroupListFragment.TAG)
                                .commit();
                    } else {
                        groupCreated = false;
                        if (groupListFragment != null) {
                            getSupportFragmentManager()
                                    .beginTransaction().addToBackStack(GroupListFragment.TAG)
                                    .replace(R.id.content_frame, groupListFragment,
                                            GroupListFragment.TAG)
                                    .commit();
                        }
                        mPresenter.getSubscriber();
                    }
                    break;

                case R.id.action_contact:
                    openContactTab();
                    break;

                case R.id.action_inbox:
                    getSupportFragmentManager()
                            .beginTransaction().addToBackStack(InboxFragment.TAG)
                            .replace(R.id.content_frame, InboxFragment.newInstance(savedClicked),
                                    InboxFragment.TAG)
                            .commit();
                    savedClicked = false;
                    break;
            }
        }

        return true;
    }

    @Override
    public void itemClicked(int position, Notification notification, int lastIndex) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(NotificationDetailFragment.TAG)
                .add(R.id.content_frame,
                        NotificationDetailFragment.newInstance(notification, position, lastIndex),
                        NotificationDetailFragment.TAG)
                .commit();
    }

    private void openContactTab() {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(ContactListFragment.TAG)
                .replace(R.id.content_frame,
                        ContactListFragment.newInstance(contactableBuildings),
                        ContactListFragment.TAG)
                .commit();
    }

    @Override
    public void nextClick(int position, int lastIndex, boolean saved) {
        InboxFragment inboxFragment = (InboxFragment) getSupportFragmentManager()
                .findFragmentByTag(InboxFragment.TAG);
        Notification notification = null;

        if (saved) {
            if (inboxFragment.getSavedNotifications() != null
                    && inboxFragment.getSavedNotifications().size() > 0) {
                notification = inboxFragment.getSavedNotifications().get(position);
            }
        } else {
            if (inboxFragment.getNotifications() != null && inboxFragment.getNotifications().size() > 0) {
                notification = inboxFragment.getNotifications().get(position);
            }
        }
        if (notification != null) {
            getSupportFragmentManager()
                    .beginTransaction().addToBackStack(NotificationDetailFragment.TAG)
                    .add(R.id.content_frame,
                            NotificationDetailFragment.newInstance(notification, position, lastIndex),
                            NotificationDetailFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void previousClick(int position, int lastIndex, boolean saved) {
        InboxFragment inboxFragment = (InboxFragment) getSupportFragmentManager()
                .findFragmentByTag(InboxFragment.TAG);
        Notification notification = null;

        if (saved) {
            if (inboxFragment.getSavedNotifications() != null
                    && inboxFragment.getSavedNotifications().size() > 0) {
                notification = inboxFragment.getSavedNotifications().get(position);
            }
        } else {
            if (inboxFragment.getNotifications() != null && inboxFragment.getNotifications().size() > 0) {
                notification = inboxFragment.getNotifications().get(position);
            }
        }

        if (notification != null) {
            getSupportFragmentManager()
                    .beginTransaction().addToBackStack(NotificationDetailFragment.TAG)
                    .add(R.id.content_frame,
                            NotificationDetailFragment.newInstance(notification, position, lastIndex),
                            NotificationDetailFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void callClick(Building building) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(ContactCallFragment.TAG)
                .add(R.id.content_frame,
                        ContactCallFragment.newInstance(Integer.toString(building.getId())),
                        ContactCallFragment.TAG)
                .commit();
    }

    @Override
    public void messageClick(Building building) {
        int buildingId = building.getId();
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(ContactMessageFragment.TAG)
                .add(R.id.content_frame,
                        ContactMessageFragment
                                .newInstance(isManaged(buildingId), isPersonalCommunity(building),
                                        subscriber.getUser().isCanSendLSA(),
                                        buildingId + ""),
                        ContactMessageFragment.TAG)
                .commit();
    }

    @Override
    public void itemAboutClicked(int position, boolean createdByMe) {
        addSuggestedHeaderIfNeeded();
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(CreatedGroupDetailFragment.TAG)
                .replace(R.id.content_frame,
                        GroupDetailContainerFragment.newInstance(subscriber, groups, position),
                        GroupDetailContainerFragment.TAG)
                .commit();

    }

    @Override
    public void onCreateGroupClicked() {
        goToCreateGroupFragment();
    }

    private void addSuggestedHeaderIfNeeded() {
        if (!groups.stream().anyMatch(building -> building.getType() == Building.Type.SUGGESTED_HEADER)) {
            Building suggestedHeader = new Building();
            suggestedHeader.setType(Building.Type.SUGGESTED_HEADER);
            groups.add(0, suggestedHeader);
        }
    }

    @Override
    public void groupsUpdated(ArrayList<Building> updatedGroups) {
        groups = updatedGroups;
    }

    @Override
    public void onPullToRefreshGroups() {
        mPresenter.getSubscriber(false);
    }

    @Override
    public void viewMemberSelected(int buildingId, int memberCount, String groupName) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(MemberListFragment.TAG)
                .add(R.id.content_frame,
                        MemberListFragment.newInstance(buildingId, memberCount, groupName),
                        MemberListFragment.TAG)
                .commit();
    }

    @Override
    public void inviteOtherSelected(int buildingId) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(InviteMemberFragment.TAG)
                .add(R.id.content_frame,
                        InviteMemberFragment.newInstance(Integer.toString(buildingId)),
                        InviteMemberFragment.TAG)
                .commit();
    }

    @Override
    public void alertViewSelected(int buildingId) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(AlertListFragment.TAG)
                .add(R.id.content_frame,
                        AlertListFragment.newInstance(buildingId),
                        AlertListFragment.TAG)
                .commit();
    }

    @Override
    public void editGroupSelected(int position) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(GenerateGroupFragment.TAG)
                .replace(R.id.content_frame, GenerateGroupFragment.newInstance(groups.get(position)),
                        GenerateGroupFragment.TAG)
                .commit();
    }

    @Override
    public void unSubscribed(int buildingId) {
        mPresenter.getSubscriber();
    }

    @Override
    public void subscribed(int buildingId) {
        mPresenter.subscribeToCommunity(buildingId);
    }

    @Override
    public void editSelected(Invitee invitee) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(EditMemberFragment.TAG)
                .add(R.id.content_frame,
                        EditMemberFragment.newInstance(invitee),
                        EditMemberFragment.TAG)
                .commit();
    }

    @Override
    public void onCommunityMembersUpdated() {
        MemberListFragment fragment = (MemberListFragment) getSupportFragmentManager().findFragmentByTag(MemberListFragment.TAG);
        if (fragment != null && fragment.isAdded()) {
            fragment.loadMembers(true);
        }
    }

    @Override
    public void onAlertDetailSelected(Notification notification, int buildingId) {

        getBuilding(buildingId).subscribe(new SingleObserver<Building>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(Building building) {
                boolean isPersonalCommunity = isPersonalCommunity(building);

                getSupportFragmentManager()
                        .beginTransaction().addToBackStack(AlertDetailFragment.TAG)
                        .add(R.id.content_frame,
                                AlertDetailFragment.newInstance(notification, isPersonalCommunity),
                                AlertDetailFragment.TAG)
                        .commit();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });
    }

    private Single<Building> getBuilding(int buildingId) {

        return Observable.fromIterable(buildings)
                .concatWith(Observable.fromIterable(personalCommunities))
                .filter(building -> building.getId() == buildingId).
                        firstOrError();
    }

    @Override
    public void onDeliveryInfoClicked(String buildingId, String notificationId, String messageName) {
        boolean userBuilding = userBuildingIds.stream()
                .anyMatch(buildingIdItem -> buildingIdItem.getId() == Integer.parseInt(buildingId));
        if (userBuilding) {
            getSupportFragmentManager()
                    .beginTransaction().addToBackStack(DeliveryInfoFragment.TAG)
                    .add(R.id.content_frame,
                            DeliveryInfoFragment.newInstance(buildingId, notificationId, messageName),
                            DeliveryInfoFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void loadGroups(ArrayList<Building> buildingsFetched,
                           ArrayList<Building> personalCommunitiesFetched, ArrayList<BuildingIdItem> ids) {
        List<Building> all = new ArrayList<>();
        buildings = buildingsFetched;
        personalCommunities = personalCommunitiesFetched;
        userBuildingIds = ids;

        if (buildings != null && buildings.size() > 0) {
            all.addAll(setBuildingType(buildings, Building.Type.NORMAL));
        }
        if (personalCommunities != null && personalCommunities.size() > 0) {
            all.addAll(setBuildingType(personalCommunities, Building.Type.NORMAL));
        }


        List<Building> noDuplicatesGroupsList = all.stream().distinct().collect(Collectors.toList());
        noDuplicatesGroupsList.sort(Comparator.comparing(Building::getName));

        groups = (ArrayList<Building>) noDuplicatesGroupsList;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,
                        GroupListFragment.newInstance(groups,
                                new ArrayList<>(), subscriber.getId()),
                        GroupListFragment.TAG)
                .commit();

        mPresenter.getSuggestedGroups();

        prepareContactBuildings(buildingsFetched, personalCommunitiesFetched);
    }

    @Override
    public void loadSubscriber(Subscriber subscriberFetched) {
        subscriber = subscriberFetched;
    }

    @Override
    public void loadSuggestedGroups(ArrayList<Building> suggestedGroups) {

        // TODO: 14.12.2018 isSubscribedAlready logic should be moved to backend
        boolean isSubscribedAlready = suggestedGroups.stream()
                .anyMatch(suggestedBuilding -> groups.stream()
                        .anyMatch(building -> building.equals(suggestedBuilding)));

        if (isSubscribedAlready) {
            mPresenter.getSuggestedGroups();

        } else {

            setBuildingType(suggestedGroups, Building.Type.SUGGESTED_ITEM);
            suggestedBuildings = suggestedGroups;

            addSuggestedHeaderIfNeeded();

            hideLoading();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame,
                            GroupListFragment.newInstance(groups,
                                    suggestedGroups, subscriber.getId()),
                            GroupListFragment.TAG)
                    .commit();


        }
    }

    ArrayList<Building> setBuildingType(ArrayList<Building> buildings, Building.Type type) {
        for (Building building : buildings) {
            building.setType(type);
        }
        return buildings;
    }

    @Override
    public void groupLeftClicked(int position) {
        GroupDetailContainerFragment groupDetailContainerFragment = (GroupDetailContainerFragment) getSupportFragmentManager()
                .findFragmentByTag(GroupDetailContainerFragment.TAG);

        if (groupDetailContainerFragment != null) {
            groupDetailContainerFragment.goLeft();
        }
    }

    @Override
    public void groupRightClick(int position) {
        GroupDetailContainerFragment groupDetailContainerFragment = (GroupDetailContainerFragment) getSupportFragmentManager()
                .findFragmentByTag(GroupDetailContainerFragment.TAG);

        if (groupDetailContainerFragment != null) {
            groupDetailContainerFragment.goRight();
        }
    }

    @Override
    public void messageFeedClick(int buildingId) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(AlertListFragment.TAG)
                .add(R.id.content_frame,
                        AlertListFragment.newInstance(buildingId),
                        AlertListFragment.TAG)
                .commit();
    }

    private void prepareContactBuildings(ArrayList<Building> contactBuildings,
                                         ArrayList<Building> contactPersonalCommunities) {

        ArrayList<Building> combinedBuildings = new ArrayList<>();
        contactableBuildings = new ArrayList<>();
        combinedBuildings.addAll(contactBuildings);
        combinedBuildings.addAll(contactPersonalCommunities);

        Observable.fromIterable(combinedBuildings)
                .filter(building -> isContactable(building)).toList().subscribe(
                filteredCombinedBuildings -> {
                    contactableBuildings = (ArrayList<Building>) filteredCombinedBuildings.stream()
                            .distinct()
                            .collect(Collectors.toList());

                    markManagedBuildings(contactableBuildings);
                    contactableBuildings.sort(Comparator.comparing(Building::getName));
                });

    }

    private void markManagedBuildings(ArrayList<Building> contactableBuildings) {
        for (Building contactableBuilding : contactableBuildings) {
            if (isManaged(contactableBuilding.getId())) {
                contactableBuilding.setIsManagedByUser(true);
            }
        }
    }

    private boolean isContactable(Building building) {
        return isManaged(building.getId()) || isPersonalCommunity(
                building) || building.isTextEnabled() || building.isVoipEnabled() && !building
                .getBuildingAddress().isVirtual();
    }

    @OnClick(R.id.sos_button)
    void sosClick(View v) {
        mPresenter.requestPhonePermission();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void locationPermissionResult(boolean granted, boolean phone) {
        if (granted) {
            scheduleGeofencesRefreshJob();
            startScanningBeacons();
            if (getLocationUtil().isProviderEnabled()) {
                getLocation();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Location Services Not Active");
                builder.setMessage("Please enable Location Services and GPS");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });
                Dialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        }
    }

    @RequiresPermission(ACCESS_COARSE_LOCATION)
    private void startScanningBeacons() {
        OpenAPI.getInstance().startScanningBeacons(this);
    }

    @Override
    public void phonePermissionResult(boolean granted) {
        if (granted) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent
                    .setData(Uri.parse("tel:" + CommonUtils.getCountrySet().get(countryName)));
            startActivity(intent);
        }
    }

    private void getLocation() {

        try {
            Task<Location> locationResult = getFusedLocationProviderClient().getLastLocation();
            locationResult.addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    mLastKnownLocation = (Location) task.getResult();

                    if (mLastKnownLocation != null) {

                        mPresenter.refreshGeofences(mLastKnownLocation);

                        Locale locale = new Locale("en", "US");
                        Geocoder geocoder = new Geocoder(getApplicationContext(), locale);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude(), 1);
                            if (addresses != null && addresses.size() > 0) {
                                countryName = addresses.get(0).getCountryName();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        getLocationUtil().getLastKnownLocation();
                    }
                } else {
                    Log.d(TAG, "Current location is null. Using defaults.");
                    Log.e(TAG, "Exception: %s", task.getException());
                }
            });

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private boolean isPersonalCommunity(Building building) {
        boolean isPersonal =
                building.getSubscriberId() != null && (building.getSubscriberId().intValue() == subscriber.getId());
        return isPersonal;
    }

    private boolean isManaged(int buildingId) {
        boolean isManaged = userBuildingIds.stream()
                .anyMatch(buildingIdItem -> buildingIdItem.getId() == buildingId);
        return isManaged;
    }

    @Override
    public void alertSubscriptionUpdated(String buildingId, String subscription) {
//    Observable.fromIterable(subscriber.getBuildings())
//        .doOnNext(
//            building -> {
//              if (building.getId() == Integer.parseInt(buildingId)) {
//                building.getBuildingsSubscriber().setAlertsSubscription(subscription);
//              }
//            }).toList().subscribe(updatedBuildings -> subscriber.setBuildings(buildings));
        System.out.println();
    }

    @Override
    public void showSubscriptionSuccessfulMessage() {
        mPresenter.getSubscriber();
        Toast.makeText(this, R.string.you_are_now_subscribed_to_the_community, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void weatherWarningUpdated(boolean weatherEnabled, boolean lightningEnabled) {
        subscriber.setWeatherAlertEnabled(weatherEnabled);
        subscriber.setLightningAlertEnabled(lightningEnabled);
    }

    private void handlePush(Bundle extras) {
        String from = extras.getString("from", "");
        PushNotification pushNotification = (PushNotification) extras
                .getSerializable("pushNotification");

        if (pushNotification != null && "alert".equals(pushNotification.getType())) {
            if (extras.getBoolean("show_popup", false)) {
                MessageViewDialog messageViewDialog = MessageViewDialog.newInstance(pushNotification);
                if (!from.isEmpty() && from.equals("background")) {
                    messageViewDialog.show(getSupportFragmentManager());
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(pushNotification.getTitle())
                            .setMessage(pushNotification.getBody())
                            .setNegativeButton(R.string.ok, (dialog, which) -> {
                                dialog.dismiss();
                            })
                            .setPositiveButton(R.string.view, (dialog, which) -> {
                                dialog.dismiss();
                                messageViewDialog.show(getSupportFragmentManager());
                            })
                            .show();
                }
            }
        }
    }

    private android.support.v7.app.AlertDialog setUpAlertDialog(final NumberPicker picker,
                                                                final String title, final DialogInterface.OnClickListener onClickListener) {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(
                this);
        builder.setView(picker);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.set, onClickListener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void updateSubscriberLanguage(String languageName, String languageValue) {
        subscriber.setLanguageName(languageName);
        subscriber.setLanguage(languageValue);
    }

    @Override
    public void groupCreated() {
        groupCreated = true;
        bottomNavigationView.setSelectedItemId(R.id.action_groups);
        mPresenter.getSubscriber();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAppOpened();
        beaconScannerBroadcastReceiver = new BeaconScannerBroadcastReceiver();
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(beaconScannerBroadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(beaconScannerBroadcastReceiver);
    }

    @Override
    public void onShowNotificationDetails(Notification notification) {
        getSupportFragmentManager()
                .beginTransaction().addToBackStack(AlertListFragment.TAG)
                .add(R.id.content_frame,
                        NotificationDetailFragment.newInstance(notification),
                        AlertListFragment.TAG)
                .commit();
    }

    @Override
    public void onLogout() {
        cancelJobs();
        OpenAPI.getInstance().stopScanningBeacons(this);
    }

    private void cancelJobs() {
        firebaseJobDispatcher.cancelAll();
    }

    @Override
    public void loadImage(AdResponse.BannerAd bannerAd) {
        Glide.with(this)
                .load(bannerAd.getImage())
                .into(adImageView);

        adImageView.setOnClickListener(v -> {

            mPresenter.onClickAd(bannerAd.getAdId());

            if (!TextUtils.isEmpty(bannerAd.getUrl())) {
                CommonUtils.openUrl(this, bannerAd.getUrl());
            }
        });
    }
}
