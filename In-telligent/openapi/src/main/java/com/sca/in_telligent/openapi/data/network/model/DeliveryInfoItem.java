package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliveryInfoItem implements Serializable {

  @SerializedName("notificationId")
  private Integer notificationId;

  @SerializedName("subscriberId")
  private Integer subscriberId;

  @SerializedName("delivered")
  private String delivered;

  @SerializedName("opened")
  private String opened;

  @SerializedName("deleted")
  private String deleted;

  @SerializedName("response")
  private Integer response;

  public DeliverySubscriber getDeliverySubscriber() {
    return deliverySubscriber;
  }

  @SerializedName("Subscriber")
  private DeliverySubscriber deliverySubscriber;


  public Integer getNotificationId() {
    return notificationId;
  }

  public Integer getSubscriberId() {
    return subscriberId;
  }

  public String getDelivered() {
    return delivered;
  }

  public String getOpened() {
    return opened;
  }

  public String getDeleted() {
    return deleted;
  }

  public Integer getResponse() {
    return response;
  }

  public class DeliverySubscriber implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    public String getName() {
      return name;
    }

    public String getEmail() {
      return email;
    }
  }
}
