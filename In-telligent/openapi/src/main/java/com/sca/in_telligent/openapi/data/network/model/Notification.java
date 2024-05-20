package com.sca.in_telligent.openapi.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Notification implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("buildingId")
    private int buildingId;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("language")
    private String language;

    @SerializedName("suggestedNotificationId")
    private String suggestedNotificationId;

    @SerializedName("type")
    private String type;

    @SerializedName("NotificationsSubscriber")
    private NotificationSubscriber notificationSubscriber;

    @SerializedName("NotificationAttachments")
    private ArrayList<NotificationAttachment> notificationAttachments;

    @SerializedName("isSaved")
    private boolean isSaved;

    @SerializedName("Building")
    private NotificationBuilding notificationBuilding;

    private boolean opened = false;

    public int getId() {
        return id;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSuggestedNotificationId(String suggestedNotificationId) {
        this.suggestedNotificationId = suggestedNotificationId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNotificationSubscriber(
            NotificationSubscriber notificationSubscriber) {
        this.notificationSubscriber = notificationSubscriber;
    }

    public void setNotificationAttachments(
            ArrayList<NotificationAttachment> notificationAttachments) {
        this.notificationAttachments = notificationAttachments;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public void setNotificationBuilding(
            NotificationBuilding notificationBuilding) {
        this.notificationBuilding = notificationBuilding;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getSuggestedNotificationId() {
        return suggestedNotificationId;
    }

    public NotificationSubscriber getNotificationSubscriber() {
        return notificationSubscriber;
    }

    public ArrayList<NotificationAttachment> getNotificationAttachments() {
        return notificationAttachments;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public String getType() {
        return type;
    }

    public NotificationBuilding getNotificationBuilding() {
        return notificationBuilding;
    }

    public boolean isSaved() {
        return isSaved;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.buildingId);
        dest.writeString(this.startDate);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.language);
        dest.writeString(this.suggestedNotificationId);
        dest.writeString(this.type);
        dest.writeSerializable(this.notificationSubscriber);
        dest.writeList(this.notificationAttachments);
        dest.writeByte(this.isSaved ? (byte) 1 : (byte) 0);
        dest.writeValue(this.notificationBuilding);
        dest.writeByte(this.opened ? (byte) 1 : (byte) 0);
    }

    public Notification() {
    }

    protected Notification(Parcel in) {
        this.id = in.readInt();
        this.buildingId = in.readInt();
        this.startDate = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.language = in.readString();
        this.suggestedNotificationId = in.readString();
        this.type = in.readString();
        this.notificationSubscriber = in.readParcelable(NotificationSubscriber.class.getClassLoader());
        this.notificationAttachments = new ArrayList<>();
        in.readList(this.notificationAttachments, NotificationAttachment.class.getClassLoader());
        this.isSaved = in.readByte() != 0;
        this.notificationBuilding = in.readParcelable(NotificationBuilding.class.getClassLoader());
        this.opened = in.readByte() != 0;
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
