package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class Subscriber implements Serializable {
    @SerializedName("betaEnabled")
    private boolean betaEnabled;
    @SerializedName("Buildings")
    private ArrayList<Building> buildings;
    @SerializedName("email")
    private String email;
    @SerializedName("id")
    private int id;
    @SerializedName("Interests")
    private ArrayList<Interest> interests;
    @SerializedName("language")
    private String language;
    @SerializedName("languageName")
    private String languageName;
    @SerializedName("lightningAlertConfirmed")
    private boolean lightningAlertConfirmed;
    @SerializedName("lightningAlertEnabled")
    private boolean lightningAlertEnabled;
    @SerializedName("name")
    private String name;
    @SerializedName("PersonalCommunity")
    private ArrayList<Building> personalCommunities;
    @SerializedName("SubscriberAutoSubscribeOptOut")
    private ArrayList<SubscriberOptOut> subscriberOptOuts;
    @SerializedName("User")
    private User user;
    @SerializedName("weatherAlertEnabled")
    private boolean weatherAlertEnabled;

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setLanguageName(String str) {
        this.languageName = str;
    }

    public void setWeatherAlertEnabled(boolean z) {
        this.weatherAlertEnabled = z;
    }

    public void setLightningAlertEnabled(boolean z) {
        this.lightningAlertEnabled = z;
    }

    public void setBuildings(ArrayList<Building> arrayList) {
        this.buildings = arrayList;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getLanguageName() {
        return this.languageName;
    }

    public boolean isWeatherAlertEnabled() {
        return this.weatherAlertEnabled;
    }

    public boolean isLightningAlertEnabled() {
        return this.lightningAlertEnabled;
    }

    public boolean isLightningAlertConfirmed() {
        return this.lightningAlertConfirmed;
    }

    public boolean isBetaEnabled() {
        return this.betaEnabled;
    }

    public User getUser() {
        return this.user;
    }

    public ArrayList<Building> getPersonalCommunities() {
        return this.personalCommunities;
    }

    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    public ArrayList<SubscriberOptOut> getSubscriberOptOuts() {
        return this.subscriberOptOuts;
    }

    public ArrayList<Interest> getInterests() {
        return this.interests;
    }
}
