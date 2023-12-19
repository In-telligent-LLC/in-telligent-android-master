package com.sca.in_telligent.di.module;

import android.content.Context;

import androidx.work.WorkManager;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ApplicationModule_ProvideWorkManagerFactory implements Factory<WorkManager> {
    private final Provider<Context> contextProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideWorkManagerFactory(ApplicationModule applicationModule, Provider<Context> provider) {
        this.module = applicationModule;
        this.contextProvider = provider;
    }

    @Override // javax.inject.Provider
    public WorkManager get() {
        return provideWorkManager(this.module, this.contextProvider.get());
    }

    public static ApplicationModule_ProvideWorkManagerFactory create(ApplicationModule applicationModule, Provider<Context> provider) {
        return new ApplicationModule_ProvideWorkManagerFactory(applicationModule, provider);
    }

    public static WorkManager provideWorkManager(ApplicationModule applicationModule, Context context) {
        return (WorkManager) Preconditions.checkNotNullFromProvides(applicationModule.provideWorkManager(context));
    }
}
