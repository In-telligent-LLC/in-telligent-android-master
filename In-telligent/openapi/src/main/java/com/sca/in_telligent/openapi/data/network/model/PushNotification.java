package com.sca.in_telligent.openapi.data.network.model;

import com.facebook.internal.NativeProtocol;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PushNotification implements Serializable {
    @SerializedName(NativeProtocol.WEB_DIALOG_ACTION)
    private String action;
    @SerializedName("alertType")
    private String alertType;
    @SerializedName("body")
    private String body;
    @SerializedName("building_id")
    private String buildingId;
    @SerializedName("building_name")
    private String building_name;
    @SerializedName("conferenceId")
    private String conferenceId;
    @SerializedName("critical_safety")
    private String criticalSafety;
    @SerializedName("feedAlertId")
    private String feedAlertId;
    @SerializedName("id")
    private String id;
    @SerializedName("increment_badge")
    private String increment_badge;
    @SerializedName("life_safety")
    private String life_safety;
    @SerializedName("lightning_alert")
    private String lightning_alert;
    @SerializedName("message_suffix")
    private String message_suffix;
    @SerializedName("notification_id")
    private String notificationId;
    @SerializedName("notification_lang")
    private String notification_lang;
    @SerializedName("notification_title")
    private String notification_title;
    @SerializedName("offer_id")
    private String offerId;
    @SerializedName("pc_emergency_alert")
    private String pc_emergency_alert;
    @SerializedName("pc_urgent_alert")
    private String pc_urgent_alert;
    @SerializedName("personal_safety")
    private String personal_safety;
    @SerializedName("ping_alert")
    private String ping_alert;
    @SerializedName("remoteUserName")
    private String remoteUserName;
    @SerializedName("social_type")
    private String socialType;
    @SerializedName("type")
    private String type;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("weather_alert")
    private String weather_alert;

    public String getRemoteUserName() {
        return this.remoteUserName;
    }

    public String getConferenceId() {
        return this.conferenceId;
    }

    public String getFeedAlertId() {
        return this.feedAlertId;
    }

    public String getCriticalSafety() {
        return this.criticalSafety;
    }

    public String getId() {
        return this.id;
    }

    public String getOfferId() {
        return this.offerId;
    }

    public String getBody() {
        return this.body;
    }

    public String getTitle() {
        return getBuilding_name() + ":" + getNotification_title();
    }

    public String getNotificationId() {
        return this.notificationId;
    }

    public String getBuildingId() {
        return this.buildingId;
    }

    public String getAlertType() {
        return this.alertType;
    }

    public String getType() {
        return this.type;
    }

    public String getPc_emergency_alert() {
        return this.pc_emergency_alert;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getPc_urgent_alert() {
        return this.pc_urgent_alert;
    }

    public String getPersonal_safety() {
        return this.personal_safety;
    }

    public String getIncrement_badge() {
        return this.increment_badge;
    }

    public String getPing_alert() {
        return this.ping_alert;
    }

    public String getWeather_alert() {
        return this.weather_alert;
    }

    public String getLightning_alert() {
        return this.lightning_alert;
    }

    public String getLife_safety() {
        return this.life_safety;
    }

    public String getSocialType() {
        return this.socialType;
    }

    public String getAction() {
        return this.action;
    }

    public String getMessage_suffix() {
        return this.message_suffix;
    }

    public String getNotification_lang() {
        return this.notification_lang;
    }

    public String getNotification_title() {
        return this.notification_title;
    }

    public String getBuilding_name() {
        return this.building_name;
    }
}
