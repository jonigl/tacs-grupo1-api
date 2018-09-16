package ar.com.tacsutn.grupo1.eventapp.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventbriteClientTest {

    private MockRestServiceServer mockServer;

    @Autowired
    private EventbriteClient eventbriteClient;

    @Before
    public void setUpMock() {
        RestTemplate restTemplate = eventbriteClient.getRestTemplate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void getEventFromValidResponse() throws IOException {
        String jsonPath = getPath("sample-response.json");
        byte[] json = Files.readAllBytes(new File(jsonPath).toPath());

        mockServer.expect(anything())
                  .andExpect(method(HttpMethod.GET))
                  .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        String eventId = "123456789ab";
        EventTemplate event = eventbriteClient.getEvent(eventId).get();

        Assert.assertEquals(eventId, event.getId());
        Assert.assertEquals("sample event", event.getName());
        Assert.assertEquals(EventTemplate.Status.LIVE, event.getStatus());
    }

    @Test
    public void getEventFromWrongResponse() throws IOException {
        mockServer.expect(anything())
                  .andExpect(method(HttpMethod.GET))
                  .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        Optional<EventTemplate> event = eventbriteClient.getEvent("12345678");

        Assert.assertFalse(event.isPresent());
    }

    private String getPath(String filename) {
        String[] arr = this.getClass().getName().split("\\.");
        arr[arr.length - 1] = filename;

        Path testPath = Paths.get("src", "test", "java");
        Path path = Paths.get(testPath.toString(), arr);

        return path.toString();
    }
}
