package com.sca.in_telligent.di.component;

import com.sca.in_telligent.di.PerActivity;
import com.sca.in_telligent.di.module.ActivityModule;
import com.sca.in_telligent.ui.auth.forgot.ForgotPasswordDialog;
import com.sca.in_telligent.ui.auth.login.LoginActivity;
import com.sca.in_telligent.ui.auth.logout.LogoutActivity;
import com.sca.in_telligent.ui.auth.register.SignupDemographicsActivity;
import com.sca.in_telligent.ui.auth.register.SignupPasswordActivity;
import com.sca.in_telligent.ui.auth.reset.ResetPasswordActivity;
import com.sca.in_telligent.ui.contact.call.ContactCallFragment;
import com.sca.in_telligent.ui.contact.list.ContactListFragment;
import com.sca.in_telligent.ui.contact.message.ContactMessageFragment;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverDialog;
import com.sca.in_telligent.ui.findlocation.FindLocationDialog;
import com.sca.in_telligent.ui.group.alert.detail.AlertDetailFragment;
import com.sca.in_telligent.ui.group.alert.list.AlertListFragment;
import com.sca.in_telligent.ui.group.detail.other.GroupDetailFragment;
import com.sca.in_telligent.ui.group.generate.GenerateGroupFragment;
import com.sca.in_telligent.ui.group.list.GroupListFragment;
import com.sca.in_telligent.ui.inbox.AttachmentPreviewDialog;
import com.sca.in_telligent.ui.inbox.InboxFragment;
import com.sca.in_telligent.ui.intro.IntroActivity;
import com.sca.in_telligent.ui.main.MainActivity;
import com.sca.in_telligent.ui.notificationdetail.NotificationDetailFragment;
import com.sca.in_telligent.ui.popup.IncomingCallActivity;
import com.sca.in_telligent.ui.popup.LifeSafetyPopupActivity;
import com.sca.in_telligent.ui.popup.PersonalSafetyPopupActivity;
import com.sca.in_telligent.ui.preview.MessageViewDialog;
import com.sca.in_telligent.ui.settings.SettingsFragment;
import com.sca.in_telligent.ui.settings.account.AccountSettingsFragment;
import com.sca.in_telligent.ui.settings.help.HelpFragment;
import com.sca.in_telligent.ui.settings.notification.NotificationSettingsFragment;
import com.sca.in_telligent.ui.splash.SplashActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  void inject(MainActivity activity);

  void inject(SplashActivity activity);

  void inject(LoginActivity activity);

  void inject(LogoutActivity activity);

  void inject(IntroActivity activity);

  void inject(SignupDemographicsActivity activity);

  void inject(SignupPasswordActivity activity);

  void inject(ForgotPasswordDialog dialog);

  void inject(ResetPasswordActivity activity);

  void inject(InboxFragment fragment);

  void inject(NotificationDetailFragment fragment);

  void inject(AttachmentPreviewDialog dialog);

  void inject(ContactListFragment fragment);

  void inject(ContactMessageFragment fragment);

  void inject(ContactCallFragment fragment);

  void inject(GroupListFragment fragment);

  void inject(GroupDetailFragment fragment);

  void inject(GenerateGroupFragment fragment);

  void inject(AlertListFragment fragment);

  void inject(AlertDetailFragment fragment);

  void inject(SettingsFragment fragment);

  void inject(AccountSettingsFragment fragment);

  void inject(NotificationSettingsFragment fragment);

  void inject(HelpFragment fragment);

  void inject(ContactDeliverDialog dialog);

  void inject(MessageViewDialog dialog);

  void inject(FindLocationDialog dialog);

  void inject(LifeSafetyPopupActivity activity);

  void inject(PersonalSafetyPopupActivity activity);

  void inject(IncomingCallActivity activity);
}
