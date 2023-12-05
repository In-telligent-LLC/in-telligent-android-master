package com.sca.in_telligent.openapi;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sca.in_telligent.openapi.data.ApiHelperProvider;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.OpenApiNetworkHelper;
import com.sca.in_telligent.openapi.receiver.BeaconScannerBroadcastReceiver;
import com.sca.in_telligent.openapi.service.BeaconScannerService;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.openapi.util.BluetoothUtil;
import com.sca.in_telligent.openapi.util.FlashHelper;
import com.sca.in_telligent.openapi.util.OpenApiAudioHelper;
import com.sca.in_telligent.openapi.util.OpenApiFlashHelper;

import static java.util.Objects.requireNonNull;

/**
 * Created by zacharyzeno on 3/15/18.
 */

public class OpenAPI {

    private static final String TAG = OpenAPI.class.getSimpleName();

    private static OpenAPI instance;
    private Context context;

    private final FlashHelper flashHelper;
    private final AudioHelper audioHelper;
    private final AudioManager audioManager;
    private final Configuration configuration;
    private BeaconScannerBroadcastReceiver beaconScannerBroadcastReceiver;

    private OpenAPI(Context context, AudioManager audioManager, FlashHelper flashHelper, AudioHelper audioHelper, Configuration configuration) {
        this.context = context;
        this.audioManager = audioManager;
        this.flashHelper = flashHelper;
        this.audioHelper = audioHelper;
        this.configuration = configuration;
    }

    public static void init(@NonNull Context context, @NonNull Configuration configuration) {
        requireNonNull(context, "A context is needed to initialize OpanAPI");
        requireNonNull(configuration, "A configuration instance is needed to initialize OpenApi");

        android.media.AudioManager serviceAudioManager = (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        FlashHelper flashHelper = OpenApiFlashHelper.newInstance(context);

        OpenApiAudioHelper audioHelper = OpenApiAudioHelper.newInstance(context, serviceAudioManager, vibrator, flashHelper);

        AudioManager audioManager = new AudioManager(context);

        OpenAPI.instance = new OpenAPI(context, audioManager, flashHelper, audioHelper, configuration);
        ApiHelperProvider.initialize(context);
    }

    public static OpenAPI getInstance() {
        if (instance == null || instance.context == null) {
            throwNotInitializedException();
            return null;
        }
        return instance;
    }

    private static OpenAPI throwNotInitializedException() {
        throw new IllegalStateException("You must initialize OpenAPI by calling OpenApi.init before using any methods");
    }

    public void startScanningBeacons(@NonNull Context context) {
        checkInitialized();
        requireNonNull(context, "A context is needed to initialize OpenAPI");
        if (BluetoothUtil.isBluetoothEnabled(context)) {
            Log.d(TAG, "Bluetooth is enabled: starting beacon scanning service from OpenApi class");
            Intent intent = BeaconScannerService.getIntent(context, BeaconScannerService.ACTION_START_FOREGROUND);
            context.startService(intent);
        }
    }

    public void stopScanningBeacons(@Nullable Context context) {
        checkInitialized();
        requireNonNull(context, "A context is needed to initialize OpenAPI");
        Intent intent = BeaconScannerService.getIntent(context, BeaconScannerService.ACTION_STOP_FOREGROUND);
        context.stopService(intent);
    }

    public AudioHelper getAudioHelper() {
        checkInitialized();
        return this.audioHelper;
    }

    private AudioManager getAudioManager() {
        checkInitialized();
        return audioManager;
    }

    private void checkInitialized() {
        if (instance == null || instance.context == null) {
            throwNotInitializedException();
            return;
        }
    }

    public static ApiHelper getApiHelper() {
        return new OpenApiNetworkHelper(ApiHelperProvider.getAuthApiHelper(),
                ApiHelperProvider.getPublicApiHelper(),
                ApiHelperProvider.getUploadApiHelper());
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static final class Configuration {
        private boolean debug;
        private int appVersion;

        protected Configuration(Builder builder) {
            this.debug = builder.debug;
            this.appVersion = builder.appVersion;
        }

        public boolean isDebug() {
            return debug;
        }

        public int getAppVersion() {
            return appVersion;
        }

        public static class Builder {
            private boolean debug = true;
            private int appVersion = 1;

            public Builder setDebug(boolean debug) {
                this.debug = debug;
                return this;
            }

            public Builder setAppVersion(int appVersion) {
                this.appVersion = appVersion;
                return this;
            }

            public Configuration build() {
                return new Configuration(this);
            }
        }

    }

}
