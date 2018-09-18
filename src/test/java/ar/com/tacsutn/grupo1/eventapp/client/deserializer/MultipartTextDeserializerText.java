package ar.com.tacsutn.grupo1.eventapp.client.deserializer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

class MultipartTextTemplate {

    @JsonDeserialize(using = MultipartTextDeserializer.class)
    private String field1;

    @JsonDeserialize(using = MultipartTextDeserializer.class)
    private String field2;

    String getField1() {
        return field1;
    }

    String getField2() {
        return field2;
    }
}

public class MultipartTextDeserializerText {

    @Test
    public void parseJsonWithMultitext() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonText =
                "{" +
                "  \"field1\": {" +
                "      \"text\": \"field1 content\"," +
                "      \"html\": \"<b>text<\\b> content\"" +
                "    }," +
                "  \"field2\": {" +
                "      \"text\": \"field2 content\"," +
                "      \"html\": \"<b>text<\\b> content\"" +
                "    }" +
                "}";

        MultipartTextTemplate template = mapper.readValue(jsonText, MultipartTextTemplate.class);

        Assert.assertEquals("field1 content", template.getField1());
        Assert.assertEquals("field2 content", template.getField2());
    }

    @Test(expected = JsonMappingException.class)
    public void parseInvalidJsonWithMultitext() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonText =
                "{" +
                "  \"field1\": {" +
                "      \"text\": \"field1 content\"," +
                "      \"valid\": \"<b>text<\\b> content\"" +
                "    }," +
                "  \"field2\": {" +
                "      \"invalid\": \"field2 content\"," +
                "      \"html\": \"<b>text<\\b> content\"" +
                "    }" +
                "}";

        mapper.readValue(jsonText, MultipartTextTemplate.class);
    }
}
