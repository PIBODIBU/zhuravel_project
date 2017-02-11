package main.hibernate.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.model.PassportFile;

import java.lang.reflect.Type;

public class PassportFileSerializer implements JsonSerializer<PassportFile> {
    @Override
    public JsonElement serialize(PassportFile passportFile, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.addProperty("id", passportFile.getId());
        object.addProperty("fileName", passportFile.getFileName());

        return object;
    }
}
