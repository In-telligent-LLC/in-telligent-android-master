package com.sca.in_telligent.di.module;

import androidx.appcompat.app.AppCompatActivity;
import com.sca.in_telligent.ui.contact.list.ContactListGroupAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideContactListGroupAdapterFactory implements Factory<ContactListGroupAdapter> {
    private final Provider<AppCompatActivity> appCompatActivityProvider;
    private final ActivityModule module;

    public ActivityModule_ProvideContactListGroupAdapterFactory(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        this.module = activityModule;
        this.appCompatActivityProvider = provider;
    }

    @Override // javax.inject.Provider
    public ContactListGroupAdapter get() {
        return provideContactListGroupAdapter(this.module, this.appCompatActivityProvider.get());
    }

    public static ActivityModule_ProvideContactListGroupAdapterFactory create(ActivityModule activityModule, Provider<AppCompatActivity> provider) {
        return new ActivityModule_ProvideContactListGroupAdapterFactory(activityModule, provider);
    }

    public static ContactListGroupAdapter provideContactListGroupAdapter(ActivityModule activityModule, AppCompatActivity appCompatActivity) {
        return (ContactListGroupAdapter) Preconditions.checkNotNullFromProvides(activityModule.provideContactListGroupAdapter(appCompatActivity));
    }
}
