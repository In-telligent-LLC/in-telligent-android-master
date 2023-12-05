package com.sca.in_telligent.di.module;

import com.sca.in_telligent.util.AppVideoDownloader;
import com.sca.in_telligent.util.VideoDownloader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ApplicationModule_ProvideVideoDownloaderFactory implements Factory<VideoDownloader> {
    private final Provider<AppVideoDownloader> appVideoDownloaderProvider;
    private final ApplicationModule module;

    public ApplicationModule_ProvideVideoDownloaderFactory(ApplicationModule applicationModule, Provider<AppVideoDownloader> provider) {
        this.module = applicationModule;
        this.appVideoDownloaderProvider = provider;
    }

    @Override // javax.inject.Provider
    public VideoDownloader get() {
        return provideVideoDownloader(this.module, this.appVideoDownloaderProvider.get());
    }

    public static ApplicationModule_ProvideVideoDownloaderFactory create(ApplicationModule applicationModule, Provider<AppVideoDownloader> provider) {
        return new ApplicationModule_ProvideVideoDownloaderFactory(applicationModule, provider);
    }

    public static VideoDownloader provideVideoDownloader(ApplicationModule applicationModule, AppVideoDownloader appVideoDownloader) {
        return (VideoDownloader) Preconditions.checkNotNullFromProvides(applicationModule.provideVideoDownloader(appVideoDownloader));
    }
}
