package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class CallDetailResponse implements Serializable {
    @SerializedName("completed")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean completed;

    public boolean isCompleted() {
        return this.completed;
    }
}
