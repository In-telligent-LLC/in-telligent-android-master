package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class SuccessResponse implements Serializable {
    @SerializedName("errors")
    private ErrorObject errors;
    @SerializedName("success")
    @JsonAdapter(BooleanTypeAdapter.class)
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public ErrorObject getErrors() {
        return this.errors;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class ErrorObject {
        @SerializedName("name")
        private ArrayList<String> name;
        @SerializedName("other")
        private ArrayList<String> other;

        public ErrorObject() {
        }

        public ArrayList<String> getName() {
            return this.name;
        }

        public ArrayList<String> getOther() {
            return this.other;
        }
    }
}
