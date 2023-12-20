package com.sca.in_telligent.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.sca.in_telligent.openapi.data.network.model.Building;
import com.sca.in_telligent.openapi.data.network.model.BuildingMember;
import com.sca.in_telligent.openapi.data.network.model.DeliveryInfoItem;
import com.sca.in_telligent.openapi.data.network.model.Invitee;
import com.sca.in_telligent.openapi.data.network.model.NavListItem;
import com.sca.in_telligent.openapi.data.network.model.Notification;
import com.sca.in_telligent.openapi.data.network.model.NotificationAttachment;
import com.sca.in_telligent.di.ActivityContext;
import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordMvpPresenter;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordMvpView;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordPresenter;
import com.sca.in_telligent.ui.auth.login.LoginMvpPresenter;
import com.sca.in_telligent.ui.auth.login.LoginMvpView;
import com.sca.in_telligent.ui.auth.login.LoginPresenter;
import com.sca.in_telligent.ui.auth.logout.LogoutMvpPresenter;
import com.sca.in_telligent.ui.auth.logout.LogoutMvpView;
import com.sca.in_telligent.ui.auth.logout.LogoutPresenter;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsMvpPresenter;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsMvpView;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsPresenter;
import com.sca.in_telligent.ui.auth.register.SignupPasswordMvpPresenter;
import com.sca.in_telligent.ui.auth.register.SignupPasswordMvpView;
import com.sca.in_telligent.ui.auth.register.SignupPasswordPresenter;
import com.sca.in_telligent.ui.auth.reset.ResetPasswordMvpPresenter;
import com.sca.in_telligent.ui.auth.reset.ResetPasswordMvpView;
import com.sca.in_telligent.ui.auth.reset.ResetPasswordPresenter;
import com.sca.in_telligent.ui.contact.call.ContactCallMvpPresenter;
import com.sca.in_telligent.ui.contact.call.ContactCallMvpView;
import com.sca.in_telligent.ui.contact.call.ContactCallPresenter;
import com.sca.in_telligent.ui.contact.list.ContactListGroupAdapter;
import com.sca.in_telligent.ui.contact.list.ContactListMvpPresenter;
import com.sca.in_telligent.ui.contact.list.ContactListMvpView;
import com.sca.in_telligent.ui.contact.list.ContactListPresenter;
import com.sca.in_telligent.ui.contact.message.ContactMessageMvpPresenter;
import com.sca.in_telligent.ui.contact.message.ContactMessageMvpView;
import com.sca.in_telligent.ui.contact.message.ContactMessagePresenter;
import com.sca.in_telligent.ui.contact.message.ContactMessageSpinnerAdapter;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverListAdapter;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverMvpPresenter;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverMvpView;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverPresenter;
import com.sca.in_telligent.ui.findlocation.FindLocationMvpPresenter;
import com.sca.in_telligent.ui.findlocation.FindLocationMvpView;
import com.sca.in_telligent.ui.findlocation.FindLocationPresenter;

import com.sca.in_telligent.ui.group.alert.detail.AlertDetailMvpPresenter;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailMvpView;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailPresenter;
import com.sca.in_telligent.ui.group.alert.list.AlertListAdapter;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpPresenter;
import com.sca.in_telligent.ui.group.alert.list.AlertListMvpView;
import com.sca.in_telligent.ui.group.alert.list.AlertListPresenter;

import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpPresenter;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailMvpView;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailPresenter;

import com.sca.in_telligent.ui.group.list.GroupListAdapter;
import com.sca.in_telligent.ui.group.list.GroupListMvpPresenter;
import com.sca.in_telligent.ui.group.list.GroupListMvpView;
import com.sca.in_telligent.ui.group.list.GroupListPresenter;
import com.sca.in_telligent.ui.group.list.GroupListSpinnerAdapter;

import com.sca.in_telligent.ui.inbox.InboxAdapter;
import com.sca.in_telligent.ui.inbox.InboxMvpPresenter;
import com.sca.in_telligent.ui.inbox.InboxMvpView;
import com.sca.in_telligent.ui.inbox.InboxPresenter;
import com.sca.in_telligent.ui.inbox.InboxSpinnerAdapter;
import com.sca.in_telligent.ui.intro.IntroMvpPresenter;
import com.sca.in_telligent.ui.intro.IntroMvpView;
import com.sca.in_telligent.ui.intro.IntroPresenter;
import com.sca.in_telligent.ui.main.MainMvpPresenter;
import com.sca.in_telligent.ui.main.MainMvpView;
import com.sca.in_telligent.ui.main.MainPresenter;
import com.sca.in_telligent.ui.main.NavigationDrawerAdapter;
import com.sca.in_telligent.ui.notificationdetail.NotificationAttachmentAdapter;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailMvpPresenter;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailMvpView;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailPresenter;
import com.sca.in_telligent.ui.popup.IncomingCallMvpPresenter;
import com.sca.in_telligent.ui.popup.IncomingCallMvpView;
import com.sca.in_telligent.ui.popup.IncomingCallPresenter;
import com.sca.in_telligent.ui.preview.MessageViewMvpPresenter;
import com.sca.in_telligent.ui.preview.MessageViewMvpView;
import com.sca.in_telligent.ui.preview.MessageViewPresenter;
import com.sca.in_telligent.ui.settings.SettingsMvpPresenter;
import com.sca.in_telligent.ui.settings.SettingsMvpView;
import com.sca.in_telligent.ui.settings.SettingsPresenter;
import com.sca.in_telligent.ui.settings.account.AccountSettingsMvpPresenter;
import com.sca.in_telligent.ui.settings.account.AccountSettingsMvpView;
import com.sca.in_telligent.ui.settings.account.AccountSettingsPresenter;
import com.sca.in_telligent.ui.settings.help.HelpMvpPresenter;
import com.sca.in_telligent.ui.settings.help.HelpMvpView;
import com.sca.in_telligent.ui.settings.help.HelpPresenter;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingAdapter;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsMvpPresenter;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsMvpView;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsPresenter;
import com.sca.in_telligent.ui.splash.SplashMvpPresenter;
import com.sca.in_telligent.ui.splash.SplashMvpView;
import com.sca.in_telligent.ui.splash.SplashPresenter;
import com.sca.in_telligent.util.rx.AppSchedulerProvider;
import com.sca.in_telligent.util.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

//    @Provides
//    RxPermissions provideRxPermissions() {
//        return new RxPermissions(mActivity);
//    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    FusedLocationProviderClient provideLocationProviderClient(AppCompatActivity activity) {
        return LocationServices.getFusedLocationProviderClient(activity);
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    IntroMvpPresenter<IntroMvpView> provideIntroPresenter(
            IntroPresenter<IntroMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignupPasswordMvpPresenter<SignupPasswordMvpView> provideSignupPasswordPresenter(
            SignupPasswordPresenter<SignupPasswordMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SignupDemographicsMvpPresenter<SignupDemographicsMvpView> provideSignupDemographicsPresenter(
            SignupDemographicsPresenter<SignupDemographicsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LogoutMvpPresenter<LogoutMvpView> provideLogoutPresenter(
            LogoutPresenter<LogoutMvpView> presenter) {
        return presenter;
    }

    @Provides
    ForgotPasswordMvpPresenter<ForgotPasswordMvpView> provideForgotPasswordPresenter(
            ForgotPasswordPresenter<ForgotPasswordMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ResetPasswordMvpPresenter<ResetPasswordMvpView> provideResetPasswordPresenter(
            ResetPasswordPresenter<ResetPasswordMvpView> presenter) {
        return presenter;
    }

    @Provides
    NavigationDrawerAdapter provideNavigationAdapter(AppCompatActivity activity) {
        return new NavigationDrawerAdapter(new ArrayList<NavListItem>(),
                activity.getApplicationContext());
    }

    @Provides
    InboxMvpPresenter<InboxMvpView> provideInboxPresenter(InboxPresenter<InboxMvpView> presenter) {
        return presenter;
    }

    @Provides
    InboxAdapter provideInboxAdapter(AppCompatActivity activity) {
        return new InboxAdapter(mActivity, new ArrayList<Notification>());
    }

    @Provides
    InboxSpinnerAdapter provideSpinnerAdapter(AppCompatActivity appCompatActivity) {
        return new InboxSpinnerAdapter(appCompatActivity, new ArrayList<String>());
    }

    @Provides
    NotificationDetailMvpPresenter<NotificationDetailMvpView> provideNotificationDetailPresenter(
            NotificationDetailPresenter<NotificationDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    NotificationAttachmentAdapter provideNotificationAttachmentAdapter(
            AppCompatActivity appCompatActivity) {
        return new NotificationAttachmentAdapter(appCompatActivity.getApplicationContext(), new ArrayList<NotificationAttachment>());
    }

    @Provides
    ContactMessageMvpPresenter<ContactMessageMvpView> provideContactMessagePresenter(
            ContactMessagePresenter<ContactMessageMvpView> presenter) {
        return presenter;
    }

    @Provides
    ContactListMvpPresenter<ContactListMvpView> provideContactListPresenter(
            ContactListPresenter<ContactListMvpView> presenter) {
        return presenter;
    }

    @Provides
    ContactListGroupAdapter provideContactListGroupAdapter(AppCompatActivity appCompatActivity) {
        return new ContactListGroupAdapter(new ArrayList<Building>());
    }

    @Provides
    ContactCallMvpPresenter<ContactCallMvpView> provideContactCallPresenter(
            ContactCallPresenter<ContactCallMvpView> presenter) {
        return presenter;
    }

    @Provides
    GroupListMvpPresenter<GroupListMvpView> provideGroupListPresenter(
            GroupListPresenter<GroupListMvpView> presenter) {
        return presenter;
    }

    @Provides
    GroupListAdapter provideGroupListAdapter(AppCompatActivity appCompatActivity) {
        return new GroupListAdapter(new ArrayList<Building>(), new ArrayList<Building>(),
                mActivity);
    }

    @Provides
    GroupListSpinnerAdapter provideGroupSpinnerAdapter(AppCompatActivity appCompatActivity) {
        return new GroupListSpinnerAdapter(appCompatActivity, new ArrayList<String>());
    }

    @Provides
    ContactMessageSpinnerAdapter provideContactSpinnerAdapter(AppCompatActivity appCompatActivity) {
        return new ContactMessageSpinnerAdapter(appCompatActivity, new ArrayList<String>());
    }


    @Provides
    GroupDetailMvpPresenter<GroupDetailMvpView> provideGroupDetailPresenter(
            GroupDetailPresenter<GroupDetailMvpView> presenter) {
        return presenter;
    }



    @Provides
    AlertListMvpPresenter<AlertListMvpView> provideAlertListPresenter(
            AlertListPresenter<AlertListMvpView> presenter) {
        return presenter;
    }

    @Provides
    AlertDetailMvpPresenter<AlertDetailMvpView> provideAlertDetailPresenter(
            AlertDetailPresenter<AlertDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    AlertListAdapter provideAlertListAdapter(AppCompatActivity activity) {
        return new AlertListAdapter(mActivity, new ArrayList<Notification>());
    }

    @Provides
    SettingsMvpPresenter<SettingsMvpView> provideSettingsPresenter(
            SettingsPresenter<SettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    NotificationSettingsMvpPresenter<NotificationSettingsMvpView> provideNotificationSettingsPresenter(
            NotificationSettingsPresenter<NotificationSettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    AccountSettingsMvpPresenter<AccountSettingsMvpView> provideAccountSettingsPresenter(
            AccountSettingsPresenter<AccountSettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    HelpMvpPresenter<HelpMvpView> provideHelpPresenter(HelpPresenter<HelpMvpView> presenter) {
        return presenter;
    }

    @Provides
    NotificationSettingAdapter provideNotificationSettingsAdapter(
            AppCompatActivity appCompatActivity) {
        return new NotificationSettingAdapter(new ArrayList<Building>());
    }

    @Provides
    ContactDeliverMvpPresenter<ContactDeliverMvpView> provideContactDeliverPresenter(
            ContactDeliverPresenter<ContactDeliverMvpView> presenter) {
        return presenter;
    }

    @Provides
    ContactDeliverListAdapter provideContactDeliverListAdapter(AppCompatActivity appCompatActivity) {
        return new ContactDeliverListAdapter(new ArrayList<Invitee>(), new ArrayList<BuildingMember>());
    }

    @Provides
    MessageViewMvpPresenter<MessageViewMvpView> provideMessageViewPresenter(
            MessageViewPresenter<MessageViewMvpView> presenter) {
        return presenter;
    }

    @Provides
    FindLocationMvpPresenter<FindLocationMvpView> provideFindLocationPresenter(
            FindLocationPresenter<FindLocationMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    IncomingCallMvpPresenter<IncomingCallMvpView> provideIncomingCallPresenter(
            IncomingCallPresenter<IncomingCallMvpView> presenter) {
        return presenter;
    }
}