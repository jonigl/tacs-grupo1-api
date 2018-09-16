package ar.com.tacsutn.grupo1.eventapp.client.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class MultipartTextDeserializer extends StdDeserializer<String> {

    public MultipartTextDeserializer() {
        this(null);
    }

    public MultipartTextDeserializer(Class<String> t) {
        super(t);
    }

    @Override
    public String deserialize(JsonParser jsonParser,
                              DeserializationContext deserializationContext) throws IOException {

        JsonNode tree = jsonParser.readValueAsTree();
        return tree.get("text").asText();
    }
}
