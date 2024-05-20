package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.group.list.GroupListAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideGroupListAdapterFactory implements Factory<GroupListAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideGroupListAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public GroupListAdapter get() {
        return provideGroupListAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideGroupListAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideGroupListAdapterFactory(activityModule, provider);
    }

    public static GroupListAdapter provideGroupListAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (GroupListAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideGroupListAdapter(appCompatActivity));
    }
}
