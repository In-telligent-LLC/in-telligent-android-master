package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.group.list.GroupListSpinnerAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideGroupSpinnerAdapterFactory implements Factory<GroupListSpinnerAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideGroupSpinnerAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public GroupListSpinnerAdapter get() {
        return provideGroupSpinnerAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideGroupSpinnerAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideGroupSpinnerAdapterFactory(activityModule, provider);
    }

    public static GroupListSpinnerAdapter provideGroupSpinnerAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (GroupListSpinnerAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideGroupSpinnerAdapter(appCompatActivity));
    }
}
