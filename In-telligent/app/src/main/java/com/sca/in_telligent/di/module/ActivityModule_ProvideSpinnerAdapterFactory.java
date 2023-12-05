package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.inbox.InboxSpinnerAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideSpinnerAdapterFactory implements Factory<InboxSpinnerAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideSpinnerAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public InboxSpinnerAdapter get() {
        return provideSpinnerAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideSpinnerAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideSpinnerAdapterFactory(activityModule, provider);
    }

    public static InboxSpinnerAdapter provideSpinnerAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (InboxSpinnerAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideSpinnerAdapter(appCompatActivity));
    }
}
