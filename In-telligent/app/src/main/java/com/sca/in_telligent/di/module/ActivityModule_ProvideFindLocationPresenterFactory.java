package com.sca.in_telligent.di.module;

import com.sca.in_telligent.ui.findlocation.FindLocationMvpPresenter;
import com.sca.in_telligent.ui.findlocation.FindLocationMvpView;
import com.sca.in_telligent.ui.findlocation.FindLocationPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final class ActivityModule_ProvideFindLocationPresenterFactory implements Factory<FindLocationMvpPresenter<FindLocationMvpView>> {
    private final ActivityModule module;
    private final Provider<FindLocationPresenter<FindLocationMvpView>> presenterProvider;

    public ActivityModule_ProvideFindLocationPresenterFactory(ActivityModule activityModule, Provider<FindLocationPresenter<FindLocationMvpView>> provider) {
        this.module = activityModule;
        this.presenterProvider = provider;
    }

    @Override // javax.inject.Provider
    public FindLocationMvpPresenter<FindLocationMvpView> get() {
        return provideFindLocationPresenter(this.module, this.presenterProvider.get());
    }

    public static ActivityModule_ProvideFindLocationPresenterFactory create(ActivityModule activityModule, Provider<FindLocationPresenter<FindLocationMvpView>> provider) {
        return new ActivityModule_ProvideFindLocationPresenterFactory(activityModule, provider);
    }

    public static FindLocationMvpPresenter<FindLocationMvpView> provideFindLocationPresenter(ActivityModule activityModule, FindLocationPresenter<FindLocationMvpView> findLocationPresenter) {
        return (FindLocationMvpPresenter) Preconditions.checkNotNullFromProvides(activityModule.provideFindLocationPresenter(findLocationPresenter));
    }
}
