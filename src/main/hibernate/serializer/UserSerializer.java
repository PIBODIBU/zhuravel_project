package main.hibernate.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.model.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        UserDataSerializer userDataSerializer = new UserDataSerializer();

        object.addProperty("id", user.getUserId());
        object.addProperty("name", user.getName());
        object.addProperty("surname", user.getSurname());
        object.addProperty("middleName", user.getMiddleName());
        object.addProperty("email", user.getEmail());
        object.addProperty("username", user.getUsername());
        object.addProperty("password", user.getPassword());

        if (user.getUserData() != null)
            object.add("userData", userDataSerializer.serialize(user.getUserData(), typeOfSrc, context));

        return object;
    }
}