package com.sca.in_telligent.openapi.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NotificationBuilding implements Parcelable {

  @SerializedName("name")
  private String name;

  @SerializedName("imageUrl")
  private String imageUrl;

  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeString(this.imageUrl);
  }

  public NotificationBuilding() {
  }

  protected NotificationBuilding(Parcel in) {
    this.name = in.readString();
    this.imageUrl = in.readString();
  }

  public static final Creator<NotificationBuilding> CREATOR = new Creator<NotificationBuilding>() {
    @Override
    public NotificationBuilding createFromParcel(Parcel source) {
      return new NotificationBuilding(source);
    }

    @Override
    public NotificationBuilding[] newArray(int size) {
      return new NotificationBuilding[size];
    }
  };
}
