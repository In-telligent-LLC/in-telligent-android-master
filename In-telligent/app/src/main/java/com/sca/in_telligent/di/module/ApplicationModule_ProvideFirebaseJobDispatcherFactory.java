package com.sca.in_telligent.di.module;

import android.content.Context;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideFirebaseJobDispatcherFactory implements Factory<FirebaseJobDispatcher> {
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideFirebaseJobDispatcherFactory(ApplicationModule applicationModule, Provider<Context> provider) {
        this.module = applicationModule;
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public FirebaseJobDispatcher get() {
        return provideFirebaseJobDispatcher(this.module, this.contextProvider.get());
    }

    public static ApplicationModule_ProvideFirebaseJobDispatcherFactory create(ApplicationModule applicationModule, Provider<Context> provider) {
        return new ApplicationModule_ProvideFirebaseJobDispatcherFactory(applicationModule, provider);
    }

    public static FirebaseJobDispatcher provideFirebaseJobDispatcher(ApplicationModule applicationModule, Context context) {
        return (FirebaseJobDispatcher) Preconditions.checkNotNullFromProvides(applicationModule.provideFirebaseJobDispatcher(context));
    }
}
