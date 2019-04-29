package name.mdemidov.atomic.example.service.some.object;

import com.google.gson.annotations.JsonAdapter;
import lombok.Getter;
import name.mdemidov.atomic.service.serializer.NullableDoubleDeserializer;

@Getter
public class SomeData {

    @JsonAdapter(NullableDoubleDeserializer.class)
    private double price;
}
