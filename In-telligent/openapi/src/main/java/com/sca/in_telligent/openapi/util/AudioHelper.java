package com.sca.in_telligent.openapi.util;

public interface AudioHelper {
    void startCriticalRingtone();

    void startEmergencyRingtone();

    void startLifeSafetyRingtone();

    void startLightningRingtone();

    void startPingRingtone();

    void startUrgentRingtone();

    void startVoipRingtone();

    void startWeatherRingtone();

    void stopRingtone();
}
