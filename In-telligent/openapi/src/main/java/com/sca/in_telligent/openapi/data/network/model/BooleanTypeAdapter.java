package com.sca.in_telligent.openapi.data.network.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class BooleanTypeAdapter implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {

  public Boolean deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {

    if (((JsonPrimitive) json).isNumber()) {
      int code = json.getAsInt();
      return code == 0 ? false :
          code == 1 || code > 1 ? true :
              null;
    } else {
      return json.getAsBoolean();
    }

  }

  @Override
  public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
    if (src) {
      return new JsonPrimitive(1);
    } else {
      return new JsonPrimitive(0);
    }
  }
}