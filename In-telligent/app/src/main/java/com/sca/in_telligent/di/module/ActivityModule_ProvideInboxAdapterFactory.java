package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.inbox.InboxAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideInboxAdapterFactory implements Factory<InboxAdapter> {
    private final Provider<AppCompatActivity> activityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideInboxAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.activityProvider = provider;
    }

    @Override // javax.inject.Provider
    public InboxAdapter get() {
        return provideInboxAdapter(this.module, this.activityProvider.get());
    }

    public static ActivityModule_ProvideInboxAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideInboxAdapterFactory(activityModule, provider);
    }

    public static InboxAdapter provideInboxAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (InboxAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideInboxAdapter(appCompatActivity));
    }
}
