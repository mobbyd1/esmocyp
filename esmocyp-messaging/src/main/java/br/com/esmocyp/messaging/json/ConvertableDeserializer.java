package br.com.esmocyp.messaging.json;

import com.google.gson.*;
import br.com.esmocyp.messaging.model.IMessage;

import java.lang.reflect.Type;

/**
 * Created by ruhandosreis on 16/08/17.
 */
public class ConvertableDeserializer<T extends IMessage> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get( "className" );

        final String className = prim.getAsString();

        final Class<T> clazz = getClassInstance(className);
        return jsonDeserializationContext.deserialize(jsonObject, clazz);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getClassInstance(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException(cnfe.getMessage());
        }
    }
}
