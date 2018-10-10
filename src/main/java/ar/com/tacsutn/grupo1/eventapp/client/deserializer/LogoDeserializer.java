package ar.com.tacsutn.grupo1.eventapp.client.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class LogoDeserializer extends StdDeserializer<String> {

    public LogoDeserializer() {
        this(null);
    }

    public LogoDeserializer(Class<String> t) {
        super(t);
    }

    @Override
    public String deserialize(JsonParser jsonParser,
                              DeserializationContext deserializationContext) throws IOException {

        JsonNode tree = jsonParser.readValueAsTree();
        return tree.get("original").get("url").asText();
    }
}
