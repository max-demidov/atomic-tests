package name.mdemidov.atomic.service.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class NullableDoubleDeserializer implements JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
        throws JsonParseException {
        return "null".equals(jsonElement.getAsString()) ? 0.0 : jsonElement.getAsDouble();
    }
}
