package com.sca.in_telligent.openapi.util;

public interface AudioHelper {

    void startLifeSafetyRingtone();

    void startCriticalRingtone();

    void startVoipRingtone();

    void startPingRingtone();

    void startWeatherRingtone();

    void startUrgentRingtone();

    void startEmergencyRingtone();

    void startLightningRingtone();

    void stopRingtone();
}
