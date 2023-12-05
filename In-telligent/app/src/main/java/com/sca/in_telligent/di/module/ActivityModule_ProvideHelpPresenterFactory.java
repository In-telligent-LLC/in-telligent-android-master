package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.settings.help.HelpMvpPresenter;
import com.sca.in_telligent.ui.settings.help.HelpMvpView;
import com.sca.in_telligent.ui.settings.help.HelpPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideHelpPresenterFactory implements Factory<HelpMvpPresenter<HelpMvpView>> {
    private final ActivityModule module;
    private final Provider<HelpPresenter<HelpMvpView>> presenterProvider;

    public ActivityModule_ProvideHelpPresenterFactory(ActivityModule activityModule, Provider<HelpPresenter<HelpMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public HelpMvpPresenter<HelpMvpView> get() {
        return provideHelpPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideHelpPresenterFactory create(ActivityModule activityModule, Provider<HelpPresenter<HelpMvpView>> provider) {
        return new ActivityModule_ProvideHelpPresenterFactory(activityModule, provider);
    }

    public static HelpMvpPresenter<HelpMvpView> provideHelpPresenter(ActivityModule activityModule, HelpPresenter<HelpMvpView> helpPresenter) {
        return (HelpMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideHelpPresenter(helpPresenter));
    }
}
