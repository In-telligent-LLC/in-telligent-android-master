package com.sca.in_telligent.di.module;


import dagger.internal.Preconditions;

//public final class ActivityModule_ProvideRxPermissionsFactory implements Factory<RxPermissions> {
//    private final ActivityModule module;
//
//    public ActivityModule_ProvideRxPermissionsFactory(ActivityModule activityModule) {
//        this.module = activityModule;
//    }
//
//    @Override // javax.inject.Provider
//    public RxPermissions get() {
//        return provideRxPermissions(this.module);
//    }
//
//    public static ActivityModule_ProvideRxPermissionsFactory create(ActivityModule activityModule) {
//        return new ActivityModule_ProvideRxPermissionsFactory(activityModule);
//    }
//
//    public static RxPermissions provideRxPermissions(ActivityModule activityModule) {
//        return (RxPermissions) Preconditions.checkNotNullFromProvides(activityModule.provideRxPermissions());
//    }
//}
