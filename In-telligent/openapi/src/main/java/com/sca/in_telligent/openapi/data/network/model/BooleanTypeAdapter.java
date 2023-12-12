package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class BooleanTypeAdapter implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.gson.JsonDeserializer
    public Boolean deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        boolean booleanValue;
        if (((JsonPrimitive) jsonElement).isNumber()) {
            int asInt = jsonElement.getAsInt();
            if (asInt == 0) {
                booleanValue = false;
            } else {
                booleanValue = ((asInt == 1 || asInt > 1) ? true : null).booleanValue();
            }
            return Boolean.valueOf(booleanValue);
        }
        return Boolean.valueOf(jsonElement.getAsBoolean());
    }

    @Override // com.google.gson.JsonSerializer
    public JsonElement serialize(Boolean bool, Type type, JsonSerializationContext jsonSerializationContext) {
        if (bool.booleanValue()) {
            return new JsonPrimitive((Number) 1);
        }
        return new JsonPrimitive((Number) 0);
    }
}
