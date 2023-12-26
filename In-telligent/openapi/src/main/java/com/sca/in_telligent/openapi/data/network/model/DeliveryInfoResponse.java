package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class DeliveryInfoResponse implements Serializable {
    @SerializedName("delivered")
    private int delivered;
    @SerializedName("subscribers")
    private ArrayList<DeliveryInfoItem> deliveryInfoItems;
    @SerializedName("opened")
    private int opened;
    @SerializedName("sent")
    private int sent;

    public int getSent() {
        return this.sent;
    }

    public int getDelivered() {
        return this.delivered;
    }

    public int getOpened() {
        return this.opened;
    }

    public ArrayList<DeliveryInfoItem> getDeliveryInfoItems() {
        return this.deliveryInfoItems;
    }
}
