package ar.com.tacsutn.grupo1.eventapp.client.deserializer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

class DatetimeDZTemplate {

    @JsonDeserialize(using = DatetimeDZDeserializer.class)
    private LocalDateTime start;

    @JsonDeserialize(using = DatetimeDZDeserializer.class)
    private LocalDateTime end;

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}

public class DatetimeDZDeserializerTest {

    @Test
    public void parseJsonWithDatetimeDZ() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonText =
                "{" +
                "    \"start\": {" +
                "        \"timezone\": \"Europe/Berlin\"," +
                "        \"local\": \"2018-10-26T09:00:00\"," +
                "        \"utc\": \"2018-10-26T07:00:00Z\"" +
                "    }," +
                "    \"end\": {" +
                "        \"timezone\": \"Europe/Berlin\"," +
                "        \"local\": \"2018-10-26T16:00:00\"," +
                "        \"utc\": \"2018-10-26T14:00:00Z\"" +
                "    }" +
                "}";

        LocalDateTime start = LocalDateTime.of(2018, 10, 26, 7, 0);
        LocalDateTime end = LocalDateTime.of(2018, 10, 26, 14, 0);

        DatetimeDZTemplate template = mapper.readValue(jsonText, DatetimeDZTemplate.class);
        Assert.assertEquals(start, template.getStart());
        Assert.assertEquals(end, template.getEnd());
    }

    @Test(expected = JsonMappingException.class)
    public void parseInvalidJsonWithDatetimeDZ() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonText =
                "{" +
                "    \"start\": {" +
                "        \"timezone\": \"Europe/Berlin\"," +
                "        \"valid\": \"2018-10-26T09:00:00\"," +
                "        \"utc\": \"2018-10-26T07:00:00Z\"" +
                "    }," +
                "    \"end\": {" +
                "        \"timezone\": \"Europe/Berlin\"," +
                "        \"local\": \"2018-10-26T16:00:00\"," +
                "        \"invalid\": \"2018-10-26T14:00:00Z\"" +
                "    }" +
                "}";

        mapper.readValue(jsonText, DatetimeDZTemplate.class);
    }

    @Test(expected = JsonMappingException.class)
    public void parseJsonWithInvalidDatetimeDZ() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonText =
                "{" +
                "    \"start\": {" +
                "        \"timezone\": \"Europe/Berlin\"," +
                "        \"local\": \"2018-10-32T09:00:00\"," +
                "        \"utc\": \"2018-10-32T07:00:00Z\"" +
                "    }," +
                "    \"end\": {" +
                "        \"timezone\": \"Europe/Berlin\"," +
                "        \"local\": \"2018-10-32T16:00:00\"," +
                "        \"utc\": \"2018-10-32T14:00:00Z\"" +
                "    }" +
                "}";

        mapper.readValue(jsonText, DatetimeDZTemplate.class);
    }
}
