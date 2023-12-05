package com.sca.in_telligent.openapi.data;

/**
 * Created by Marcos Ambrosi on 2/6/19.
 *
 * Decouples Android components from the rest of the APIComponents;
 * instead of accesing directly to SharedPreferences,
 * classes that need access to stored information, can do it through this layer.
 */
public interface CredentialsProvider {
    String getAuthToken();
}
