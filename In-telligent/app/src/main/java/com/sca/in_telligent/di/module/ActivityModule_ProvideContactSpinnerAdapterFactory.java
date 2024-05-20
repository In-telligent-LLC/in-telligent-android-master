package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.contact.message.ContactMessageSpinnerAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactSpinnerAdapterFactory implements Factory<ContactMessageSpinnerAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideContactSpinnerAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactMessageSpinnerAdapter get() {
        return provideContactSpinnerAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideContactSpinnerAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideContactSpinnerAdapterFactory(activityModule, provider);
    }

    public static ContactMessageSpinnerAdapter provideContactSpinnerAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (ContactMessageSpinnerAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideContactSpinnerAdapter(appCompatActivity));
    }
}
