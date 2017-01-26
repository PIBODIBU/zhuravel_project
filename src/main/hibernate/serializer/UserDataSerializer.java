package main.hibernate.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.model.UserData;

import java.lang.reflect.Type;

public class UserDataSerializer implements JsonSerializer<UserData> {
    @Override
    public JsonElement serialize(UserData userData, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        UserSerializer userSerializer = new UserSerializer();

        object.addProperty("id", userData.getId());
//        object.add("user", userSerializer.serialize(userData.getUser(), type, context)); // StackOverflow
        object.addProperty("passportSeries", userData.getPassportSeries());
        object.addProperty("passportNumber", userData.getPassportNumber());
        object.addProperty("passportValidity", userData.getPassportValidity());
        object.addProperty("passportRegistration", userData.getPassportRegistration());
        object.addProperty("phone", userData.getPhone());
        object.addProperty("passportUrl", userData.getPassportUrl());

        return object;
    }
}
