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

    public void setAction(String action) {
        this.action = action;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public void setConferenceId(String conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setCriticalSafety(String criticalSafety) {
        this.criticalSafety = criticalSafety;
    }

    public void setFeedAlertId(String feedAlertId) {
        this.feedAlertId = feedAlertId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIncrement_badge(String increment_badge) {
        this.increment_badge = increment_badge;
    }

    public void setLife_safety(String life_safety) {
        this.life_safety = life_safety;
    }

    public void setLightning_alert(String lightning_alert) {
        this.lightning_alert = lightning_alert;
    }

    public void setMessage_suffix(String message_suffix) {
        this.message_suffix = message_suffix;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setNotification_lang(String notification_lang) {
        this.notification_lang = notification_lang;
    }

    public void setNotification_title(String notification_title) {
        this.notification_title = notification_title;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setPc_emergency_alert(String pc_emergency_alert) {
        this.pc_emergency_alert = pc_emergency_alert;
    }

    public void setPc_urgent_alert(String pc_urgent_alert) {
        this.pc_urgent_alert = pc_urgent_alert;
    }

    public void setPersonal_safety(String personal_safety) {
        this.personal_safety = personal_safety;
    }

    public void setPing_alert(String ping_alert) {
        this.ping_alert = ping_alert;
    }

    public void setRemoteUserName(String remoteUserName) {
        this.remoteUserName = remoteUserName;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setWeather_alert(String weather_alert) {
        this.weather_alert = weather_alert;
    }

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
