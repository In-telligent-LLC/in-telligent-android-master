package com.sca.in_telligent.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sca.in_telligent.R;
import com.sca.in_telligent.openapi.BuildConfig;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.network.model.AdResponse;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.BuildingIdItem;
import com.sca.in_telligent.openapi.data.network.model.NavListItem;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.PushNotification;
import com.sca.in_telligent.openapi.data.network.model.SubscribeToCommunityRequest;
import com.sca.in_telligent.openapi.data.network.model.Subscriber;
import com.sca.in_telligent.ui.auth.logout.LogoutActivity;
import com.sca.in_telligent.ui.base.BaseActivity;
import com.sca.in_telligent.ui.contact.call.ContactCallFragment;
import com.sca.in_telligent.ui.contact.list.ContactListFragment;
import com.sca.in_telligent.ui.contact.message.ContactMessageFragment;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailFragment;
import com.sca.in_telligent.ui.group.alert.list.AlertListFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailContainerFragment;
import com.sca.in_telligent.ui.group.detail.GroupDetailSelector;
import com.sca.in_telligent.ui.group.generate.GenerateGroupFragment;
import com.sca.in_telligent.ui.group.list.GroupListFragment;
import com.sca.in_telligent.ui.inbox.InboxFragment;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment;
import com.sca.in_telligent.ui.preview.MessageViewDialog;
import com.sca.in_telligent.ui.settings.SettingsFragment;
import com.sca.in_telligent.ui.settings.account.AccountSettingsFragment;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment;
import com.sca.in_telligent.util.AppUpdateHandler;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.LocationUtils;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.mapper.UriToDataMapper;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;


public class MainActivity extends BaseActivity implements MainMvpView, NavigationDrawerAdapter.Callback, BottomNavigationView.OnNavigationItemSelectedListener, InboxFragment.InboxSelector, NotificationDetailFragment.NotificationDetailCallback, ContactListFragment.ContactListCallback, GroupListFragment.GroupListSelector, GroupDetailSelector, AlertListFragment.AlertListSelector, NotificationSettingsFragment.NotificationSettingsSelector, AccountSettingsFragment.AccountSettingsSelector, MessageViewDialog.PushNotificationDetailCallback, SettingsFragment.SettingsCallback, GenerateGroupFragment.GenerateGroupSelector {
    private static final String TAG = "MainActivity";
    ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.ad_footer_image)
    ImageView adImageView;
    @Inject
    NavigationDrawerAdapter adapter;
    AppBarLayout appBarLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.sos_button)
    ImageView buttonSos;
    private String countryName;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Inject
    WorkManager workManager;
    @BindView(R.id.content_frame)
    FrameLayout frameLayout;
    @Inject
    GeofenceClient mGeofenceClient;
    Location mLastKnownLocation;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;
    RecyclerView navigationViewListView;

    private NumberPicker silenceTimePicker;
    Subscriber subscriber;
    Toolbar toolbar;
    @BindView(R.id.total_silence_number)
    TextView totalSilenceNumber;
    @BindView(R.id.total_silence_off)
    TextView totalSilenceOff;
    @BindView(R.id.total_silence_on)
    TextView totalSilenceOn;
    private CountDownTimer totalSilenceTimer;

//    @BindView()
    TextView version_name;

    ArrayList<Building> groups = new ArrayList<>();
    ArrayList<Building> buildings = new ArrayList<>();
    ArrayList<Building> personalCommunities = new ArrayList<>();
    ArrayList<BuildingIdItem> userBuildingIds = new ArrayList<>();
    ArrayList<Building> contactableBuildings = new ArrayList<>();
    ArrayList<Building> suggestedBuildings = new ArrayList<>();
    private boolean savedClicked = false;
    private boolean groupCreated = false;
    private Unbinder mUnBinder;
    AlertDialog silenceTimeDialog;


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            checkIntent(intent);
        }
        checkDeepLinksParams();
    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        OpenAPI.Configuration.setMocked(BuildConfig.MOCK);

        checkAppUpdates();
        setContentView(R.layout.activity_main);
        checkIntent(getIntent());
        getActivityComponent().inject(this);
        getAudioHelper().stopRingtone();

        ButterKnife.bind(this);

        this.mPresenter.onAttach(this);
        CommonUtils.checkDNDPermission(this);

        navigationViewListView = findViewById(R.id.navigation_view_listview);
        totalSilenceNumber = findViewById(R.id.total_silence_number);
        totalSilenceOn = findViewById(R.id.total_silence_on);
        totalSilenceOff = findViewById(R.id.total_silence_off);
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        adImageView = findViewById(R.id.ad_footer_image);
        buttonSos = findViewById(R.id.sos_button);
        frameLayout = findViewById(R.id.content_frame);
        appBarLayout = findViewById(R.id.app_bar_layout);
        toolbar = findViewById(R.id.toolbar);

        if(OpenAPI.Configuration.isMocked()){
            Button btnTestNotification = findViewById(R.id.btnTestNotification);

            btnTestNotification.setOnClickListener(view ->
                    getAudioHelper().startEmergencyRingtone());

        }


        setUp();
        checkDeepLinksParams();
        this.mGeofenceClient.populateIntelligentFences(false);
    }

    private void checkIntent(Intent intent) {
        if (intent.getExtras() != null && intent.getExtras().getSerializable("pushNotification") != null) {
            handlePush(intent.getExtras());
        } else if (intent.getExtras() == null || intent.getBundleExtra("bundle") == null || intent.getBundleExtra("bundle").getSerializable("pushNotification") == null) {
        } else {
            handlePush(intent.getBundleExtra("bundle"));
        }
    }

    private void checkAppUpdates() {
        new AppUpdateHandler().start(this);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.clear();
    }

    @Override
    public void setUp() {

        showLocationInformation();
        configureNavigationDrawer();
        configureToolbar();
        initSilence();
        configureTotalSilence();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.drawer_open, R.string.drawer_closed);
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);

        this.mPresenter.getSubscriber();
    }

    private void showLocationInformation() {
        if (LocationUtils.hasLocationPermission(this) || LocationUtils.neverAskAgainSelected(this)) {
            return;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.ACCESS_FINE_LOCATION}, 1);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (LocationUtils.isPermissionsGranted(this)) {
            locationPermissionResult(true, false);
        }
    }

    @Override
    public void phonePermissionResult(Permission permission) {

    }

    @Override
    public void phonePermissionResult(boolean permission) {

    }

    private void checkDeepLinksParams() {
        Uri data = getIntent().getData();
        if (data == null) {
            Log.d(TAG, "There's no data associated with this intent");
        } else if (SubscribeToCommunityRequest.ACTION_SUBSCRIBE.equals(data.getPathSegments().get(0))) {
            fetchCommunityInfo(UriToDataMapper.uriToSubscribeToCommunityRequest(data));
        }
    }

    private void fetchCommunityInfo(UriToDataMapper.SubscribeToCommunityData subscribeToCommunityData) {
        this.mPresenter.getCommunityInfo(subscribeToCommunityData.getCommunityId(), subscribeToCommunityData.getInviteId());
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
    public void onDestroy() {
        mPresenter.onDetach();
        if (totalSilenceTimer != null) {
            totalSilenceTimer.cancel();
        }
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onFragmentAttached() {
        DrawerLayout drawerLayout = this.drawerLayout;
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
    }

    @Override
    public void onFragmentDetached(String str) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (findFragmentByTag != null) {
            supportFragmentManager.beginTransaction().disallowAddToBackStack().remove(findFragmentByTag).commit();
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
        this.adapter.setCallback(this);
        this.adapter.addItems(addItemsToList());
        this.mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.navigationViewListView.setLayoutManager(this.mLayoutManager);
        this.navigationViewListView.setItemAnimator(new DefaultItemAnimator());
        this.navigationViewListView.setAdapter(this.adapter);
    }


    private void configureTotalSilence() {
        silenceTimePicker = new NumberPicker(this);
        silenceTimeDialog = setUpAlertDialog(silenceTimePicker, getString(R.string.hours_of_silence),
                (dialog, which) -> {
                    Date now = new Date();
                    getDataManager().setLifeSafetyOverrideExpire(CommonUtils.getSilenceDateString(
                            new Date(now.getTime() + 1000L * 60 * 60 * silenceTimePicker.getValue())));
                    initSilence();
                });

        silenceTimePicker.setMinValue(1);
        silenceTimePicker.setMaxValue(12);
        silenceTimePicker.scrollTo(0, 0);
        silenceTimePicker.setWrapSelectorWheel(false);
    }


    public void initSilence() {
        Date date = new Date();
        Date silenceDate = CommonUtils.getSilenceDate(getDataManager().getLifeSafetyOverrideExpire());
        CountDownTimer countDownTimer = this.totalSilenceTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (silenceDate == null || date.after(silenceDate)) {
            this.totalSilenceOff.setSelected(true);
            this.totalSilenceOn.setSelected(false);
            this.totalSilenceNumber.setText("00:00:00");
            return;
        }
        this.totalSilenceOff.setSelected(false);
        this.totalSilenceOn.setSelected(true);
        long time = silenceDate.getTime() - date.getTime();
        this.totalSilenceNumber.setText(DateUtils.formatElapsedTime(time / 1000));
        this.totalSilenceTimer = new CountDownTimer(time, 1000L) {
            @Override
            public void onTick(long j) {
                MainActivity.this.totalSilenceNumber.setText(DateUtils.formatElapsedTime(j / 1000));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                MainActivity.this.initSilence();
            }
        }.start();
    }


    @OnClick({R.id.total_silence_on})
    public void silenceOnClick(View view) {
        AlertDialog alertDialog = this.silenceTimeDialog;
        if (alertDialog != null) {
            alertDialog.show();
        }
    }


    @OnClick({R.id.total_silence_off})
    public void silenceOffClick(View view) {
        getDataManager().setLifeSafetyOverrideExpire(null);
        initSilence();
    }

    List<NavListItem> addItemsToList() {
        ArrayList arrayList = new ArrayList();
        NavListItem navListItemCreate = new NavListItem(getResources().getString(R.string.create_a_group), R.drawable.icon_create_group);
        NavListItem navListItem = new NavListItem(getResources().getString(R.string.settings), R.drawable.icon_settings);
        NavListItem navListItem2 = new NavListItem(getResources().getString(R.string.saved_messages), R.drawable.icon_saved_messages);
        arrayList.add(navListItem);
        arrayList.add(navListItem2);
        arrayList.add(navListItemCreate);
        return arrayList;
    }

    @Override
    public void onItemClicked(int i) {
        if (!isNetworkConnected()) {
            showNetworkDialog();
        } else if (i == 0) {
            getSupportFragmentManager().beginTransaction().addToBackStack(SettingsFragment.TAG).add((int) R.id.content_frame, SettingsFragment.newInstance(this.subscriber), SettingsFragment.TAG).commit();
            this.drawerLayout.closeDrawers();
        } else if (i == 1) {
            this.savedClicked = true;
            this.bottomNavigationView.setSelectedItemId(R.id.action_inbox);
        } else if (i == 2) {
            goToCreateGroupFragment();
            this.drawerLayout.closeDrawers();
        }
        else {
            startActivity(LogoutActivity.getStartIntent(this));
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
        getSupportFragmentManager().beginTransaction().addToBackStack(ContactListFragment.TAG).replace((int) R.id.content_frame, ContactListFragment.newInstance(this.contactableBuildings), ContactListFragment.TAG).commit();
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

    @Override // com.sca.in_telligent.ui.contact.list.ContactListFragment.ContactListCallback
    public void callClick(Building building) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ContactCallFragment.TAG).add((int) R.id.content_frame, ContactCallFragment.newInstance(Integer.toString(building.getId())), ContactCallFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.contact.list.ContactListFragment.ContactListCallback
    public void messageClick(Building building) {
        int id = building.getId();
        getSupportFragmentManager().beginTransaction().addToBackStack(ContactMessageFragment.TAG).add((int) R.id.content_frame, ContactMessageFragment.newInstance(isManaged(id), isPersonalCommunity(building), this.subscriber.getUser().isCanSendLSA(), id + ""), ContactMessageFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListFragment.GroupListSelector
    public void itemAboutClicked(int i, boolean z) {
        addSuggestedHeaderIfNeeded();
        getSupportFragmentManager().beginTransaction().addToBackStack(GroupDetailContainerFragment.TAG).replace((int) R.id.content_frame, GroupDetailContainerFragment.newInstance(this.subscriber, this.groups, i), GroupDetailContainerFragment.TAG).commit();
    }

    private void addSuggestedHeaderIfNeeded() {
        if (!groups.stream().anyMatch(building -> building.getType() == Building.Type.SUGGESTED_HEADER)) {
            Building suggestedHeader = new Building();
            suggestedHeader.setType(Building.Type.SUGGESTED_HEADER);
            groups.add(0, suggestedHeader);
        }
    }

    @Override
    public void groupsUpdated(ArrayList<Building> arrayList) {
        this.groups = arrayList;
    }

    @Override
    public void onCreateGroupClicked() {
        goToCreateGroupFragment();

    }


    @Override // com.sca.in_telligent.ui.group.list.GroupListFragment.GroupListSelector
    public void onPullToRefreshGroups() {
        getLocation();
        this.mPresenter.getSubscriber(false);
    }

    @Override // com.sca.in_telligent.ui.group.detail.GroupDetailSelector
    public void alertViewSelected(int i) {
        getSupportFragmentManager().beginTransaction().addToBackStack(AlertListFragment.TAG).add((int) R.id.content_frame, AlertListFragment.newInstance(i), AlertListFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.group.detail.GroupDetailSelector
    public void unSubscribed(int i) {
        this.mPresenter.getSubscriber();
    }

    @Override // com.sca.in_telligent.ui.group.detail.GroupDetailSelector
    public void subscribed(int i) {
        this.mPresenter.subscribeToCommunity(i);
    }

    @Override // com.sca.in_telligent.ui.group.alert.list.AlertListFragment.AlertListSelector
    public void onAlertDetailSelected(final Notification notification, int i) {
        getBuilding(i).subscribe(new SingleObserver<Building>() { // from class: com.sca.in_telligent.ui.main.MainActivity.3
            @Override // io.reactivex.SingleObserver
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onSuccess(Building building) {
                MainActivity.this.getSupportFragmentManager().beginTransaction().addToBackStack(AlertDetailFragment.TAG).add((int) R.id.content_frame, AlertDetailFragment.newInstance(notification), AlertDetailFragment.TAG).commit();
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                Log.e(MainActivity.TAG, th.getMessage(), th);
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
    public void loadSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
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


    private boolean isPersonalCommunity(Building building) {
        boolean isPersonal =
                building.getSubscriberId() != null && (building.getSubscriberId().intValue() == subscriber.getId());
        return isPersonal;
    }

    ArrayList<Building> setBuildingType(ArrayList<Building> arrayList, Building.Type type) {
        Iterator<Building> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().setType(type);
        }
        return arrayList;
    }

    @Override // com.sca.in_telligent.ui.group.detail.GroupDetailSelector
    public void groupLeftClicked(int i) {
        GroupDetailContainerFragment groupDetailContainerFragment = (GroupDetailContainerFragment) getSupportFragmentManager().findFragmentByTag(GroupDetailContainerFragment.TAG);
        if (groupDetailContainerFragment != null) {
            groupDetailContainerFragment.goLeft();
        }
    }

    @Override // com.sca.in_telligent.ui.group.detail.GroupDetailSelector
    public void groupRightClick(int i) {
        GroupDetailContainerFragment groupDetailContainerFragment = (GroupDetailContainerFragment) getSupportFragmentManager().findFragmentByTag(GroupDetailContainerFragment.TAG);
        if (groupDetailContainerFragment != null) {
            groupDetailContainerFragment.goRight();
        }
    }

    @Override
    public void messageFeedClick(int i) {
        getSupportFragmentManager().beginTransaction().addToBackStack(AlertListFragment.TAG).add((int) R.id.content_frame, AlertListFragment.newInstance(i), AlertListFragment.TAG).commit();
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



    private void markManagedBuildings(ArrayList<Building> arrayList) {
        Iterator<Building> it = arrayList.iterator();
        while (it.hasNext()) {
            Building next = it.next();
            if (isManaged(next.getId())) {
                next.setIsManagedByUser(true);
            }
        }
    }

    public boolean isContactable(Building building) {
        return isManaged(building.getId()) || building.isTextEnabled() || (building.isVoipEnabled() && !building.getBuildingAddress().isVirtual());
    }

    @OnClick({R.id.sos_button})
    public void sosClick(View view) {
        this.mPresenter.requestPhonePermission();
    }

    @Override
    public void locationPermissionResult(boolean z, boolean z2) {
        if (z) {
            if (getLocationUtil().isProviderEnabled()) {
                getLocation();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", (dialogInterface, i) ->
                    MainActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS")));
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            create.show();
        }
    }


//    @Override
//    public void phonePermissionResult(Permission permission) {
//        if (permission.) {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:" + CommonUtils.getCountrySet().get(this.countryName)));
//            startActivity(intent);
////        } else if (permission.shouldShowRequestPermissionRationale) {
////        } else {
////            showPopup(getResources().getString(R.string.permission_call_message));
////        }
//        }
//
//
//    }

//    @Override // com.sca.in_telligent.ui.main.MainMvpView
//    public void phonePermissionResult(boolean permission) {
//        if (permission) {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:" + CommonUtils.getCountrySet().get(this.countryName)));
//            startActivity(intent);
//        } else if (permission.shouldShowRequestPermissionRationale) {
//        } else {
//            showPopup(getResources().getString(R.string.permission_call_message));
//        }
//        }
//    }

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
        public void showSubscriptionSuccessfulMessage () {
            this.mPresenter.getSubscriber();
            Toast.makeText((Context) this, (int) R.string.you_are_now_subscribed_to_the_community, Toast.LENGTH_SHORT).show();
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


    private AlertDialog setUpAlertDialog(NumberPicker numberPicker, String str, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(numberPicker);
        builder.setTitle(str);
        builder.setCancelable(true);
        builder.setPositiveButton((int) R.string.set, onClickListener).setNegativeButton((int) R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void updateSubscriberLanguage(String str, String str2) {
        this.subscriber.setLanguageName(str);
        this.subscriber.setLanguage(str2);
    }

    protected void onStart() {
        super.onStart();
        this.mPresenter.onAppOpened();
    }

    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onShowNotificationDetails(Notification notification) {
        getSupportFragmentManager().beginTransaction().addToBackStack(AlertListFragment.TAG).add((int) R.id.content_frame, NotificationDetailFragment.newInstance(notification), AlertListFragment.TAG).commit();
    }

    @Override
    public void onLogout() {
        cancelJobs();
    }

    public void onLocationNext() {
        this.mPresenter.requestLocationPermissions(false);
    }

    private void cancelJobs() {

        List<WorkRequest> scheduledJobs = (List<WorkRequest>) workManager.getWorkInfosByTag("job");

        for (WorkRequest workRequest : scheduledJobs) {
            UUID uuid = workRequest.getId();
            workManager.cancelWorkById(uuid);
        }
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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void groupCreated() {
        groupCreated = true;
    }

//    @NonNull
//    @Override
//    public CreationExtras getDefaultViewModelCreationExtras() {
//        return super.getDefaultViewModelCreationExtras();
//    }
}
