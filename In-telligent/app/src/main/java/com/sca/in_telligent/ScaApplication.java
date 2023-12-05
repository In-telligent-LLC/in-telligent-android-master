package com.sca.in_telligent;

import android.app.Application;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.media.AudioManager;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sca.in_telligent.data.DataManager;
import com.sca.in_telligent.di.component.ApplicationComponent;
import com.sca.in_telligent.di.component.DaggerActivityComponent;
import com.sca.in_telligent.di.component.DaggerApplicationComponent;
import com.sca.in_telligent.di.module.ApplicationModule;
import com.sca.in_telligent.openapi.OpenAPI;
import com.sca.in_telligent.openapi.data.network.model.PushTokenRequest;
import com.sca.in_telligent.openapi.data.network.model.PushTokenSuccessResponse;
import com.sca.in_telligent.util.LifecycleInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class ScaApplication extends Application {
    private static final String LOG = "com.sca.in_telligent.ScaApplication";
    @Inject
    LifecycleInterface appLifecycleObserver;
    @Inject
    AudioManager audioManager;
    private ApplicationComponent mApplicationComponent;
    @Inject
    DataManager mDataManager;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        initOpenApi();
        ApplicationComponent build = DaggerActivityComponent.builder().applicationModule(new ApplicationModule(this)).build();
        this.mApplicationComponent = build;
        build.inject(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener() { // from class: com.sca.in_telligent.ScaApplication$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                ScaApplication.this.m111lambda$onCreate$0$comscain_telligentScaApplication(task);
            }
        });
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this.appLifecycleObserver);
    }

    /* renamed from: lambda$onCreate$0$com-sca-in_telligent-ScaApplication  reason: not valid java name */
    public /* synthetic */ void m111lambda$onCreate$0$comscain_telligentScaApplication(Task task) {
        if (task.isSuccessful()) {
            String token = ((InstanceIdResult) task.getResult()).getToken();
            if (this.mDataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()) {
                sendRegistrationToServer(token);
            }
            if (this.mDataManager.getPushToken() == null) {
                this.mDataManager.setPushToken(token);
            }
        }
    }

    private void initOpenApi() {
        OpenAPI.init(this, new OpenAPI.Configuration.Builder().setAppVersion(BuildConfig.VERSION_CODE).setDebug(false).build());
    }

    public String getCurrentState() {
        return this.appLifecycleObserver.getState();
    }

    public ApplicationComponent getComponent() {
        return this.mApplicationComponent;
    }

    public void setComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }

    private void sendRegistrationToServer(String str) {
        PushTokenRequest pushTokenRequest = new PushTokenRequest();
        pushTokenRequest.setEnvironment("fcm");
        pushTokenRequest.setPushToken(str);
        this.mDataManager.registerPushToken(pushTokenRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.sca.in_telligent.ScaApplication$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                PushTokenSuccessResponse pushTokenSuccessResponse = (PushTokenSuccessResponse) obj;
                Log.i(ScaApplication.LOG, "sendRegistrationToServer: successResponse");
            }
        }, new Consumer() { // from class: com.sca.in_telligent.ScaApplication$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.e(ScaApplication.LOG, "sendRegistrationToServer error ", (Throwable) obj);
            }
        });
    }
}
