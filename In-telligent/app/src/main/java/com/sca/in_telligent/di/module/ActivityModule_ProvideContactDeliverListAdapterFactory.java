package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.contact.message.deliver.ContactDeliverListAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactDeliverListAdapterFactory implements Factory<ContactDeliverListAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideContactDeliverListAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactDeliverListAdapter get() {
        return provideContactDeliverListAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideContactDeliverListAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideContactDeliverListAdapterFactory(activityModule, provider);
    }

    public static ContactDeliverListAdapter provideContactDeliverListAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (ContactDeliverListAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideContactDeliverListAdapter(appCompatActivity));
    }
}
