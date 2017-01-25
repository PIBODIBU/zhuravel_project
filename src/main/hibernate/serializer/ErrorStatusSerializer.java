package main.hibernate.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.model.ErrorStatus;

import java.lang.reflect.Type;

public class ErrorStatusSerializer implements JsonSerializer<ErrorStatus> {
    @Override
    public JsonElement serialize(ErrorStatus src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("error", src.getError());
        object.addProperty("error_msg", src.getErrorMessage());
        return object;
    }
}
