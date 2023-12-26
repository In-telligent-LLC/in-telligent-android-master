package com.sca.in_telligent.openapi.data.network.error;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class ErrorResponseTest {
    @SerializedName("Errors")
    public ArrayList<ErrorObjectTest> arrayList = new ArrayList<>();

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class ErrorObjectTest {
        public String context;
        public String description;
        public String field;
        public String location;
        public String type;

        public ErrorObjectTest() {
        }
    }
}
