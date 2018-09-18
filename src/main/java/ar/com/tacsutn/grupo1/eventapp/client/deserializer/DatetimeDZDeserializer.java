package ar.com.tacsutn.grupo1.eventapp.client.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeDZDeserializer extends StdDeserializer<LocalDateTime> {

    public DatetimeDZDeserializer() {
        this(null);
    }

    public DatetimeDZDeserializer(Class<String> t) {
        super(t);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser,
                                     DeserializationContext deserializationContext) throws IOException {

        JsonNode tree = jsonParser.readValueAsTree();
        String localDateString = tree.get("utc").asText();
        return LocalDateTime.parse(localDateString, DateTimeFormatter.ISO_DATE_TIME);
    }
}
