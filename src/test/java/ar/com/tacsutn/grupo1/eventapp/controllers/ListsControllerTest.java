package ar.com.tacsutn.grupo1.eventapp.controllers;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListsController.class)
public class ListsControllerTest extends ControllerTest {
    @Test
    public void canPostNewEventList() throws Exception {
        this.getMockMvc().perform(
                post("/api/v1/lists").
                contentType(APPLICATION_JSON_UTF8).
                content("Test")).
                andExpect(status().isCreated());
    }
}
