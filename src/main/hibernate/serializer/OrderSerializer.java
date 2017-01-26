package main.hibernate.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import main.model.Order;

import java.lang.reflect.Type;

public class OrderSerializer implements JsonSerializer<Order> {
    @Override
    public JsonElement serialize(Order order, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        UserSerializer userSerializer = new UserSerializer();

        object.addProperty("id", order.getId());
        object.add("agent", userSerializer.serialize(order.getAgent(), typeOfSrc, context));
        object.add("buyer", userSerializer.serialize(order.getBuyer(), typeOfSrc, context));
        object.addProperty("date", order.getDate().toString());
        object.addProperty("buying_item_name", order.getBuyingItemName());
        object.addProperty("buying_comment", order.getBuyingComment());
        object.addProperty("sold_comment", order.getSoldComment());
        object.addProperty("is_done", order.getDone());
        object.addProperty("is_archived", order.getArchived());
        object.addProperty("is_canceled", order.getCanceled());

        return object;
    }
}