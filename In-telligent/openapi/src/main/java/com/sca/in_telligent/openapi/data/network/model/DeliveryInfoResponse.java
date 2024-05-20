package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DeliveryInfoResponse implements Serializable {

  @SerializedName("sent")
  private int sent;

  @SerializedName("delivered")
  private int delivered;

  @SerializedName("opened")
  private int opened;

  @SerializedName("subscribers")
  private ArrayList<DeliveryInfoItem> deliveryInfoItems;

  public int getSent() {
    return sent;
  }

  public int getDelivered() {
    return delivered;
  }

  public int getOpened() {
    return opened;
  }

  public ArrayList<DeliveryInfoItem> getDeliveryInfoItems() {
    return deliveryInfoItems;
  }

}
