package main.hibernate.serializer;

import com.google.gson.*;
import main.model.PassportFile;
import main.model.UserData;

import java.lang.reflect.Type;

public class UserDataSerializer implements JsonSerializer<UserData> {
    @Override
    public JsonElement serialize(UserData userData, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        PassportFileSerializer passportFileSerializer = new PassportFileSerializer();

        object.addProperty("id", userData.getUserDataId());
        object.addProperty("passportSeries", userData.getPassportSeries());
        object.addProperty("passportNumber", userData.getPassportNumber());
        object.addProperty("passportValidity", userData.getPassportValidity());
        object.addProperty("passportRegistration", userData.getPassportRegistration());
        object.addProperty("phone", userData.getPhone());
        object.addProperty("companyName", userData.getCompanyName());
        object.addProperty("bonusCardNumber", userData.getBonusCardNumber());

        JsonArray passportFilesArray = new JsonArray();
        for (PassportFile passportFile : userData.getPassportFiles())
            passportFilesArray.add(passportFileSerializer.serialize(passportFile, type, context));
        object.add("passportPhotos", passportFilesArray);

        return object;
    }
}
