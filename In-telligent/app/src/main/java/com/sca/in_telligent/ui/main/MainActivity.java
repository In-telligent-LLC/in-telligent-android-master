package com.sca.in_telligent.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sca.in_telligent.BuildConfig;
import com.sca.in_telligent.R;
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
import com.sca.in_telligent.ui.group.list.GroupListFragment;
import com.sca.in_telligent.ui.inbox.InboxFragment;
import com.sca.in_telligent.ui.locationprompt.LocationPromptActivity;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment;
import com.sca.in_telligent.ui.preview.MessageViewDialog;
import com.sca.in_telligent.ui.settings.SettingsFragment;
import com.sca.in_telligent.ui.settings.account.AccountSettingsFragment;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment;
import com.sca.in_telligent.util.AppUpdateHandler;
import com.sca.in_telligent.util.CommonUtils;
import com.sca.in_telligent.util.LocationUtil;
import com.sca.in_telligent.util.geofence.GeofenceClient;
import com.sca.in_telligent.util.mapper.UriToDataMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MainActivity extends BaseActivity implements MainMvpView, NavigationDrawerAdapter.Callback, BottomNavigationView.OnNavigationItemSelectedListener, InboxFragment.InboxSelector, NotificationDetailFragment.NotificationDetailCallback, ContactListFragment.ContactListCallback, GroupListFragment.GroupListSelector, GroupDetailSelector, AlertListFragment.AlertListSelector, NotificationSettingsFragment.NotificationSettingsSelector, AccountSettingsFragment.AccountSettingsSelector, MessageViewDialog.PushNotificationDetailCallback, SettingsFragment.SettingsCallback {
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
    FirebaseJobDispatcher firebaseJobDispatcher;
    @BindView(R.id.content_frame)
    FrameLayout frameLayout;
    @Inject
    GeofenceClient mGeofenceClient;
    Location mLastKnownLocation;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;
    @BindView(R.id.navigation_view_listview)
    RecyclerView navigationViewListView;
    AlertDialog silenceTimeDialog;
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
    @BindView(R.id.version)
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


    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            checkIntent(intent);
        }
        checkDeepLinksParams();
    }


    @Override // com.sca.in_telligent.ui.base.BaseActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        checkAppUpdates();
        setContentView(R.layout.activity_main);
        checkIntent(getIntent());
        getActivityComponent().inject(this);
        getAudioHelper().stopRingtone();
        setUnBinder(ButterKnife.bind(this));
        this.mPresenter.onAttach(this);
        CommonUtils.checkDNDPermission(this);
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

    /* JADX WARN: Multi-variable type inference failed */
    private void checkAppUpdates() {
        new AppUpdateHandler().start(this);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sca.in_telligent.ui.base.BaseActivity
    protected void setUp() {
        this.version_name.setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME);
        showLocationInformation();
        configureNavigationDrawer();
        configureToolbar();
        initSilence();
        configureTotalSilence();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, (int) R.string.drawer_open, (int) R.string.drawer_closed);
        this.actionBarDrawerToggle = actionBarDrawerToggle;
        this.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        this.mPresenter.getSubscriber();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showLocationInformation() {
        if (LocationUtil.hasLocationPermission(this) || LocationUtils.neverAskAgainSelected(this)) {
            return;
        }
        startActivity(LocationPromptActivity.Companion.getStartIntent(this));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sca.in_telligent.ui.base.BaseActivity
    public void onResume() {
        super.onResume();
        if (LocationUtils.isPermissionsGranted(this)) {
            locationPermissionResult(true, false);
        }
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void showSubscribeToCommunityDialog(String str, final int i, final int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.you_were_invited_to_join_a_comunnity, new Object[]{str}));
        builder.setMessage(R.string.are_you_sure_you_want_to_join);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                MainActivity.this.m266x381bc0d4(i, i2, dialogInterface, i3);
            }
        });
        builder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    /* renamed from: lambda$showSubscribeToCommunityDialog$0$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ void m266x381bc0d4(int i, int i2, DialogInterface dialogInterface, int i3) {
        this.mPresenter.subscribeToCommunity(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.sca.in_telligent.ui.base.BaseActivity
    public void onDestroy() {
        this.mPresenter.onDetach();
        CountDownTimer countDownTimer = this.totalSilenceTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override // com.sca.in_telligent.ui.base.BaseActivity, com.sca.in_telligent.ui.base.BaseFragment.Callback
    public void onFragmentAttached() {
        DrawerLayout drawerLayout = this.drawerLayout;
        if (drawerLayout != null) {
            drawerLayout.closeDrawers();
        }
    }

    @Override // com.sca.in_telligent.ui.base.BaseActivity, com.sca.in_telligent.ui.base.BaseFragment.Callback
    public void onFragmentDetached(String str) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (findFragmentByTag != null) {
            supportFragmentManager.beginTransaction().disallowAddToBackStack().remove(findFragmentByTag).commit();
        }
    }

    private void configureToolbar() {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        this.appBarLayout = appBarLayout;
        Toolbar toolbar = (Toolbar) appBarLayout.findViewById(2131231479);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((int) R.drawable.ic_menu_icon);
    }

    private void configureNavigationDrawer() {
        this.adapter.setCallback(this);
        this.adapter.addItems(addItemsToList());
        this.mLayoutManager.setOrientation(1);
        this.navigationViewListView.setLayoutManager(this.mLayoutManager);
        this.navigationViewListView.setItemAnimator(new DefaultItemAnimator());
        this.navigationViewListView.setAdapter(this.adapter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void configureTotalSilence() {
        NumberPicker numberPicker = new NumberPicker(this);
        this.silenceTimePicker = numberPicker;
        this.silenceTimeDialog = setUpAlertDialog(numberPicker, getString(R.string.hours_of_silence), new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.getDataManager().setLifeSafetyOverrideExpire(CommonUtils.getSilenceDateString(new Date(new Date().getTime() + (MainActivity.this.silenceTimePicker.getValue() * 3600000))));
                MainActivity.this.initSilence();
            }
        });
        this.silenceTimePicker.setMinValue(1);
        this.silenceTimePicker.setMaxValue(12);
        this.silenceTimePicker.scrollTo(0, 0);
        this.silenceTimePicker.setWrapSelectorWheel(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.sca.in_telligent.ui.main.MainActivity$2] */
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
        this.totalSilenceTimer = new CountDownTimer(time, 1000L) { // from class: com.sca.in_telligent.ui.main.MainActivity.2
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                MainActivity.this.totalSilenceNumber.setText(DateUtils.formatElapsedTime(j / 1000));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                MainActivity.this.initSilence();
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.total_silence_on})
    public void silenceOnClick(View view) {
        androidx.appcompat.app.AlertDialog alertDialog = this.silenceTimeDialog;
        if (alertDialog != null) {
            alertDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.total_silence_off})
    public void silenceOffClick(View view) {
        getDataManager().setLifeSafetyOverrideExpire(null);
        initSilence();
    }

    List<NavListItem> addItemsToList() {
        ArrayList arrayList = new ArrayList();
        NavListItem navListItem = new NavListItem(getResources().getString(R.string.settings), R.drawable.icon_settings);
        NavListItem navListItem2 = new NavListItem(getResources().getString(R.string.saved_messages), R.drawable.icon_saved_messages);
        arrayList.add(navListItem);
        arrayList.add(navListItem2);
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sca.in_telligent.ui.main.NavigationDrawerAdapter.Callback
    public void onItemClicked(int i) {
        if (!isNetworkConnected()) {
            showNetworkDialog();
        } else if (i == 0) {
            getSupportFragmentManager().beginTransaction().addToBackStack(SettingsFragment.TAG).add((int) R.id.content_frame, SettingsFragment.newInstance(this.subscriber), SettingsFragment.TAG).commit();
            this.drawerLayout.closeDrawers();
        } else if (i == 1) {
            this.savedClicked = true;
            this.bottomNavigationView.setSelectedItemId(R.id.action_inbox);
        } else {
            startActivity(LogoutActivity.getStartIntent(this));
        }
    }

    @Override // com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (!isNetworkConnected()) {
            showNetworkDialog();
            return true;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 2131230785) {
            openContactTab();
            return true;
        } else if (itemId != 2131230789) {
            if (itemId != 2131230791) {
                return true;
            }
            getSupportFragmentManager().beginTransaction().addToBackStack(InboxFragment.TAG).replace((int) R.id.content_frame, InboxFragment.newInstance(this.savedClicked), InboxFragment.TAG).commit();
            this.savedClicked = false;
            return true;
        } else {
            GroupListFragment groupListFragment = (GroupListFragment) getSupportFragmentManager().findFragmentByTag(GroupListFragment.TAG);
            if (groupListFragment != null && !this.groupCreated) {
                getSupportFragmentManager().beginTransaction().addToBackStack(GroupListFragment.TAG).replace((int) R.id.content_frame, groupListFragment, GroupListFragment.TAG).commit();
                return true;
            }
            this.groupCreated = false;
            if (groupListFragment != null) {
                getSupportFragmentManager().beginTransaction().addToBackStack(GroupListFragment.TAG).replace((int) R.id.content_frame, groupListFragment, GroupListFragment.TAG).commit();
            }
            this.mPresenter.getSubscriber();
            return true;
        }
    }

    @Override // com.sca.in_telligent.ui.inbox.InboxFragment.InboxSelector
    public void itemClicked(int i, Notification notification, int i2) {
        getSupportFragmentManager().beginTransaction().addToBackStack(NotificationDetailFragment.TAG).add((int) R.id.content_frame, NotificationDetailFragment.newInstance(notification, i, i2), NotificationDetailFragment.TAG).commit();
    }

    private void openContactTab() {
        getSupportFragmentManager().beginTransaction().addToBackStack(ContactListFragment.TAG).replace((int) R.id.content_frame, ContactListFragment.newInstance(this.contactableBuildings), ContactListFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment.NotificationDetailCallback
    public void nextClick(int i, int i2, boolean z) {
        Notification notification;
        InboxFragment inboxFragment = (InboxFragment) getSupportFragmentManager().findFragmentByTag(InboxFragment.TAG);
        if (z) {
            if (inboxFragment.getSavedNotifications() != null && inboxFragment.getSavedNotifications().size() > 0) {
                notification = inboxFragment.getSavedNotifications().get(i);
            }
            notification = null;
        } else {
            if (inboxFragment.getNotifications() != null && inboxFragment.getNotifications().size() > 0) {
                notification = inboxFragment.getNotifications().get(i);
            }
            notification = null;
        }
        if (notification != null) {
            getSupportFragmentManager().beginTransaction().addToBackStack(NotificationDetailFragment.TAG).add((int) R.id.content_frame, NotificationDetailFragment.newInstance(notification, i, i2), NotificationDetailFragment.TAG).commit();
        }
    }

    @Override // com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment.NotificationDetailCallback
    public void previousClick(int i, int i2, boolean z) {
        Notification notification;
        InboxFragment inboxFragment = (InboxFragment) getSupportFragmentManager().findFragmentByTag(InboxFragment.TAG);
        if (z) {
            if (inboxFragment.getSavedNotifications() != null && inboxFragment.getSavedNotifications().size() > 0) {
                notification = inboxFragment.getSavedNotifications().get(i);
            }
            notification = null;
        } else {
            if (inboxFragment.getNotifications() != null && inboxFragment.getNotifications().size() > 0) {
                notification = inboxFragment.getNotifications().get(i);
            }
            notification = null;
        }
        if (notification != null) {
            getSupportFragmentManager().beginTransaction().addToBackStack(NotificationDetailFragment.TAG).add((int) R.id.content_frame, NotificationDetailFragment.newInstance(notification, i, i2), NotificationDetailFragment.TAG).commit();
        }
    }

    @Override // com.sca.in_telligent.ui.contact.list.ContactListFragment.ContactListCallback
    public void callClick(Building building) {
        getSupportFragmentManager().beginTransaction().addToBackStack(ContactCallFragment.TAG).add((int) R.id.content_frame, ContactCallFragment.newInstance(Integer.toString(building.getId())), ContactCallFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.contact.list.ContactListFragment.ContactListCallback
    public void messageClick(Building building) {
        int id = building.getId();
        getSupportFragmentManager().beginTransaction().addToBackStack(ContactMessageFragment.TAG).add((int) R.id.content_frame, ContactMessageFragment.newInstance(isManaged(id), this.subscriber.getUser().isCanSendLSA(), id + ""), ContactMessageFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListFragment.GroupListSelector
    public void itemAboutClicked(int i, boolean z) {
        addSuggestedHeaderIfNeeded();
        getSupportFragmentManager().beginTransaction().addToBackStack(GroupDetailContainerFragment.TAG).replace((int) R.id.content_frame, GroupDetailContainerFragment.newInstance(this.subscriber, this.groups, i), GroupDetailContainerFragment.TAG).commit();
    }

    private void addSuggestedHeaderIfNeeded() {
        if (this.groups.stream().anyMatch(new Predicate() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MainActivity.lambda$addSuggestedHeaderIfNeeded$1((Building) obj);
            }
        })) {
            return;
        }
        Building building = new Building();
        building.setType(Building.Type.SUGGESTED_HEADER);
        this.groups.add(0, building);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$addSuggestedHeaderIfNeeded$1(Building building) {
        return building.getType() == Building.Type.SUGGESTED_HEADER;
    }

    @Override // com.sca.in_telligent.ui.group.list.GroupListFragment.GroupListSelector
    public void groupsUpdated(ArrayList<Building> arrayList) {
        this.groups = arrayList;
    }

    @Override
    public void onCreateGroupClicked() {

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

    @Override
    public void editGroupSelected(int position) {

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

            @Override // io.reactivex.SingleObserver
            public void onSuccess(Building building) {
                MainActivity.this.getSupportFragmentManager().beginTransaction().addToBackStack(AlertDetailFragment.TAG).add((int) R.id.content_frame, AlertDetailFragment.newInstance(notification), AlertDetailFragment.TAG).commit();
            }

            @Override // io.reactivex.SingleObserver
            public void onError(Throwable th) {
                Log.e(MainActivity.TAG, th.getMessage(), th);
            }
        });
    }

    private Single<Building> getBuilding(final int i) {
        return Observable.fromIterable(this.buildings).concatWith(Observable.fromIterable(this.personalCommunities)).filter(new Predicate() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return MainActivity.lambda$getBuilding$2(i, (Building) obj);
            }
        }).firstOrError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$getBuilding$2(int i, Building building) throws Exception {
        return building.getId() == i;
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void loadGroups(ArrayList<Building> arrayList, ArrayList<Building> arrayList2, ArrayList<BuildingIdItem> arrayList3) {
        ArrayList arrayList4 = new ArrayList();
        this.buildings = arrayList;
        this.personalCommunities = arrayList2;
        this.userBuildingIds = arrayList3;
        if (arrayList != null && arrayList.size() > 0) {
            arrayList4.addAll(setBuildingType(this.buildings, Building.Type.NORMAL));
        }
        ArrayList<Building> arrayList5 = this.personalCommunities;
        if (arrayList5 != null && arrayList5.size() > 0) {
            arrayList4.addAll(setBuildingType(this.personalCommunities, Building.Type.NORMAL));
        }
        List list = (List) arrayList4.stream().distinct().collect(Collectors.toList());
        list.sort(Comparator.comparing(MainActivity$$ExternalSyntheticLambda11.INSTANCE));
        this.groups = (ArrayList) list;
        getSupportFragmentManager().beginTransaction().replace((int) R.id.content_frame, GroupListFragment.newInstance(this.groups, new ArrayList(), this.subscriber.getId()), GroupListFragment.TAG).commit();
        this.mPresenter.getSuggestedGroups();
        prepareContactBuildings(arrayList, arrayList2);
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void loadSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void loadSuggestedGroups(ArrayList<Building> arrayList) {
        if (arrayList.stream().anyMatch(new Predicate() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MainActivity.this.m263xbe3ce7c6((Building) obj);
            }
        })) {
            this.mPresenter.getSuggestedGroups();
            return;
        }
        setBuildingType(arrayList, Building.Type.SUGGESTED_ITEM);
        this.suggestedBuildings = arrayList;
        addSuggestedHeaderIfNeeded();
        hideLoading();
        getSupportFragmentManager().beginTransaction().replace((int) R.id.content_frame, GroupListFragment.newInstance(this.groups, arrayList, this.subscriber.getId()), GroupListFragment.TAG).commit();
    }

    /* renamed from: lambda$loadSuggestedGroups$4$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ boolean m263xbe3ce7c6(final Building building) {
        return this.groups.stream().anyMatch(new Predicate() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean equals;
                equals = ((Building) obj).equals(Building.this);
                return equals;
            }
        });
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

    @Override // com.sca.in_telligent.ui.group.detail.GroupDetailSelector
    public void messageFeedClick(int i) {
        getSupportFragmentManager().beginTransaction().addToBackStack(AlertListFragment.TAG).add((int) R.id.content_frame, AlertListFragment.newInstance(i), AlertListFragment.TAG).commit();
    }

    @Override
    public void viewMemberSelected(int buldingId, int memberCount, String groupName) {

    }

    @Override
    public void inviteOtherSelected(int buildingId) {

    }

    private void prepareContactBuildings(ArrayList<Building> arrayList, ArrayList<Building> arrayList2) {
        ArrayList arrayList3 = new ArrayList();
        this.contactableBuildings = new ArrayList<>();
        arrayList3.addAll(arrayList);
        arrayList3.addAll(arrayList2);
        Observable.fromIterable(arrayList3).filter(new io.reactivex.functions.Predicate() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda10
            @Override // io.reactivex.functions.Predicate
            public final boolean test(Object obj) {
                return MainActivity.this.m264x564d339c((Building) obj);
            }
        }).toList().subscribe(new Consumer() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda8
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MainActivity.this.m265x3b8ea25d((List) obj);
            }
        });
    }

    /* renamed from: lambda$prepareContactBuildings$6$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ void m265x3b8ea25d(List list) throws Exception {
        ArrayList<Building> arrayList = (ArrayList) list.stream().distinct().collect(Collectors.toList());
        this.contactableBuildings = arrayList;
        markManagedBuildings(arrayList);
        this.contactableBuildings.sort(Comparator.comparing(MainActivity$$ExternalSyntheticLambda11.INSTANCE));
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

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: isContactable */
    public boolean m264x564d339c(Building building) {
        return isManaged(building.getId()) || building.isTextEnabled() || (building.isVoipEnabled() && !building.getBuildingAddress().isVirtual());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @OnClick({R.id.sos_button})
    public void sosClick(View view) {
        this.mPresenter.requestPhonePermission();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void locationPermissionResult(boolean z, boolean z2) {
        if (z) {
            if (getLocationUtil().isProviderEnabled()) {
                getLocation();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity.4
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                }
            });
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            create.show();
        }
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void phonePermissionResult(Permission permission) {
        if (permission.granted) {
            Intent intent = new Intent("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:" + CommonUtils.getCountrySet().get(this.countryName)));
            startActivity(intent);
        } else if (permission.shouldShowRequestPermissionRationale) {
        } else {
            showPopup(getResources().getString(R.string.permission_call_message));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void getLocation() {
        try {
            getFusedLocationProviderClient().getLastLocation().addOnCompleteListener((Activity) this, new OnCompleteListener() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda7
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    MainActivity.this.m259lambda$getLocation$7$comscain_telligentuimainMainActivity(task);
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /* renamed from: lambda$getLocation$7$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ void m259lambda$getLocation$7$comscain_telligentuimainMainActivity(Task task) {
        if (task.isSuccessful()) {
            Location location = (Location) task.getResult();
            this.mLastKnownLocation = location;
            if (location != null) {
                this.mPresenter.refreshGeofences(location);
                try {
                    List<Address> fromLocation = new Geocoder(getApplicationContext(), new Locale("en", "US")).getFromLocation(this.mLastKnownLocation.getLatitude(), this.mLastKnownLocation.getLongitude(), 1);
                    if (fromLocation == null || fromLocation.size() <= 0) {
                        return;
                    }
                    this.countryName = fromLocation.get(0).getCountryName();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            getLocationUtil().getLastKnownLocation();
            return;
        }
        Log.d(TAG, "Current location is null. Using defaults.");
        Log.e(TAG, "Exception: %s", task.getException());
    }

    private boolean isManaged(final int i) {
        return this.userBuildingIds.stream().anyMatch(new Predicate() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MainActivity.lambda$isManaged$8(i, (BuildingIdItem) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$isManaged$8(int i, BuildingIdItem buildingIdItem) {
        return buildingIdItem.getId() == i;
    }

    @Override // com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment.NotificationSettingsSelector
    public void alertSubscriptionUpdated(String str, String str2) {
        System.out.println();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void showSubscriptionSuccessfulMessage() {
        this.mPresenter.getSubscriber();
        Toast.makeText((Context) this, (int) R.string.you_are_now_subscribed_to_the_community, 0).show();
    }

    @Override // com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment.NotificationSettingsSelector
    public void weatherWarningUpdated(boolean z, boolean z2) {
        this.subscriber.setWeatherAlertEnabled(z);
        this.subscriber.setLightningAlertEnabled(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void handlePush(Bundle bundle) {
        String string = bundle.getString("from", "");
        Log.i("information", "handlePush from");
        final PushNotification pushNotification = (PushNotification) bundle.getSerializable("pushNotification");
        if (pushNotification != null && "alert".equals(pushNotification.getType()) && bundle.getBoolean("show_popup", false)) {
            final MessageViewDialog newInstance = MessageViewDialog.newInstance(pushNotification);
            if (!string.isEmpty() && string.equals("background")) {
                if (getAudioHelper() != null) {
                    getAudioHelper().stopRingtone();
                }
                CommonUtils.clearNotification(this, Integer.parseInt(pushNotification.getNotificationId()));
                newInstance.show(getSupportFragmentManager());
                return;
            }
            new AlertDialog.Builder(this).setTitle(pushNotification.getTitle()).setMessage(pushNotification.getBody()).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.m261lambda$handlePush$9$comscain_telligentuimainMainActivity(pushNotification, dialogInterface, i);
                }
            }).setPositiveButton(R.string.view, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.m260lambda$handlePush$10$comscain_telligentuimainMainActivity(pushNotification, newInstance, dialogInterface, i);
                }
            }).show();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$handlePush$9$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ void m261lambda$handlePush$9$comscain_telligentuimainMainActivity(PushNotification pushNotification, DialogInterface dialogInterface, int i) {
        getAudioHelper().stopRingtone();
        CommonUtils.clearNotification(this, Integer.parseInt(pushNotification.getNotificationId()));
        dialogInterface.dismiss();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$handlePush$10$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ void m260lambda$handlePush$10$comscain_telligentuimainMainActivity(PushNotification pushNotification, MessageViewDialog messageViewDialog, DialogInterface dialogInterface, int i) {
        getAudioHelper().stopRingtone();
        dialogInterface.dismiss();
        CommonUtils.clearNotification(this, Integer.parseInt(pushNotification.getNotificationId()));
        messageViewDialog.show(getSupportFragmentManager());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private androidx.appcompat.app.AlertDialog setUpAlertDialog(NumberPicker numberPicker, String str, DialogInterface.OnClickListener onClickListener) {
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

    @Override // com.sca.in_telligent.ui.settings.account.AccountSettingsFragment.AccountSettingsSelector
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

    @Override // com.sca.in_telligent.ui.preview.MessageViewDialog.PushNotificationDetailCallback
    public void onShowNotificationDetails(Notification notification) {
        getSupportFragmentManager().beginTransaction().addToBackStack(AlertListFragment.TAG).add((int) R.id.content_frame, NotificationDetailFragment.newInstance(notification), AlertListFragment.TAG).commit();
    }

    @Override // com.sca.in_telligent.ui.settings.SettingsFragment.SettingsCallback
    public void onLogout() {
        cancelJobs();
    }

    public void onLocationNext() {
        this.mPresenter.requestLocationPermissions(false);
    }

    private void cancelJobs() {
        this.firebaseJobDispatcher.cancelAll();
    }

    @Override // com.sca.in_telligent.ui.main.MainMvpView
    public void loadImage(final AdResponse.BannerAd bannerAd) {
        Glide.with(this).load(bannerAd.getImage()).into(this.adImageView);
        this.adImageView.setOnClickListener(new View.OnClickListener() { // from class: com.sca.in_telligent.ui.main.MainActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.this.m262lambda$loadImage$11$comscain_telligentuimainMainActivity(bannerAd, view);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: lambda$loadImage$11$com-sca-in_telligent-ui-main-MainActivity  reason: not valid java name */
    public /* synthetic */ void m262lambda$loadImage$11$comscain_telligentuimainMainActivity(AdResponse.BannerAd bannerAd, View view) {
        this.mPresenter.onClickAd(bannerAd.getAdId());
        if (TextUtils.isEmpty(bannerAd.getUrl())) {
            return;
        }
        CommonUtils.openUrl(this, bannerAd.getUrl());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}
