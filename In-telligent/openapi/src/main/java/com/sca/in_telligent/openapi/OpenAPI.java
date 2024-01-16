package com.sca.in_telligent.openapi;

import android.content.Context;
import android.os.Vibrator;

import com.sca.in_telligent.openapi.data.ApiHelperProvider;
import com.sca.in_telligent.openapi.data.network.ApiHelper;
import com.sca.in_telligent.openapi.data.network.OpenApiNetworkHelper;
import com.sca.in_telligent.openapi.util.AudioHelper;
import com.sca.in_telligent.openapi.util.FlashHelper;
import com.sca.in_telligent.openapi.util.OpenApiAudioHelper;
import com.sca.in_telligent.openapi.util.OpenApiFlashHelper;

import java.util.Objects;

public class OpenAPI {
    private static final String TAG = "OpenAPI";
    private static OpenAPI instance;
    private final AudioHelper audioHelper;
    private final AudioManager audioManager;
    private final Configuration configuration;
    private final Context context;
    private final FlashHelper flashHelper;

    private OpenAPI(Context context, AudioManager audioManager, FlashHelper flashHelper, AudioHelper audioHelper, Configuration configuration) {
        this.context = context;
        this.audioManager = audioManager;
        this.flashHelper = flashHelper;
        this.audioHelper = audioHelper;
        this.configuration = configuration;
    }

    public static void init(Context context, Configuration configuration) {
        Objects.requireNonNull(context, "A context is needed to initialize OpenAPI");
        Objects.requireNonNull(configuration, "A configuration instance is needed to initialize OpenApi");
        if(!configuration.isMocked()) {
            FlashHelper newInstance = OpenApiFlashHelper.newInstance(context);
            instance = new OpenAPI(context, new AudioManager(context), newInstance, OpenApiAudioHelper.newInstance(context, (android.media.AudioManager) context.getSystemService(Context.AUDIO_SERVICE), (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE), newInstance), configuration);
            ApiHelperProvider.setMocked(configuration.isMocked());
            ApiHelperProvider.initialize(context);
        }
    }

    public static OpenAPI getInstance() {
        OpenAPI openAPI = instance;
        if (openAPI == null || openAPI.context == null) {
            throwNotInitializedException();
            return null;
        }
        return openAPI;
    }

    private static OpenAPI throwNotInitializedException() {
        throw new IllegalStateException("You must initialize OpenAPI by calling OpenApi.init before using any methods");
    }

    public AudioHelper getAudioHelper() {
        checkInitialized();
        return this.audioHelper;
    }

    private AudioManager getAudioManager() {
        checkInitialized();
        return this.audioManager;
    }

    private void checkInitialized() {
        OpenAPI openAPI = instance;
        if (openAPI == null || openAPI.context == null) {
            throwNotInitializedException();
        }
    }

    public static ApiHelper getApiHelper() {
        return new OpenApiNetworkHelper(ApiHelperProvider.getAuthApiHelper(), ApiHelperProvider.getPublicApiHelper(), ApiHelperProvider.getUploadApiHelper());
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public static final class Configuration {
        private final int appVersion;
        private final boolean debug;
        private static boolean isMock = true;

        private Configuration(Builder builder) {
            this.debug = builder.debug;
            this.appVersion = builder.appVersion;
        }

        public static boolean isMocked() {
            return isMock;
        }

        public static void setMocked(boolean mocked) {
            isMock = mocked;
        }

        public boolean isDebug() {
            return this.debug;
        }

        public int getAppVersion() {
            return this.appVersion;
        }

        public static class Builder {
            private boolean debug = true;
            private int appVersion = 1;

            public Builder setDebug(boolean z) {
                this.debug = z;
                return this;
            }

            public Builder setAppVersion(int i) {
                this.appVersion = i;
                return this;
            }

            public Configuration build() {
                return new Configuration(this);
            }
        }
    }
}
