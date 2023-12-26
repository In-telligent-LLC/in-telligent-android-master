package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.SerializedName;
import com.twilio.voice.Voice;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class DeliveryInfoItem implements Serializable {
//    @SerializedName()
//    private String deleted;
    @SerializedName("delivered")
    private String delivered;
    @SerializedName("Subscriber")
    private DeliverySubscriber deliverySubscriber;
    @SerializedName("notificationId")
    private Integer notificationId;
    @SerializedName("opened")
    private String opened;
    @SerializedName("response")
    private Integer response;
    @SerializedName("subscriberId")
    private Integer subscriberId;

    public DeliverySubscriber getDeliverySubscriber() {
        return this.deliverySubscriber;
    }

    public Integer getNotificationId() {
        return this.notificationId;
    }

    public Integer getSubscriberId() {
        return this.subscriberId;
    }

    public String getDelivered() {
        return this.delivered;
    }

    public String getOpened() {
        return this.opened;
    }

//    public String getDeleted() {
//        return this.deleted;
//    }

    public Integer getResponse() {
        return this.response;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class DeliverySubscriber implements Serializable {
        @SerializedName("email")
        private String email;
        @SerializedName("name")
        private String name;

        public DeliverySubscriber() {
        }

        public String getName() {
            return this.name;
        }

        public String getEmail() {
            return this.email;
        }
    }
}
