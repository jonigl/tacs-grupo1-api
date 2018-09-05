package ar.com.tacsutn.grupo1.eventapp.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ControllerTest {
    @Autowired private MockMvc mockMvc;

    public MockMvc getMvc() { return this.mockMvc; }

    public void test200Response(String route) throws Exception {
        this.getMvc().perform(get("/api/v1/" + route)).andExpect(status().isOk());
    }

    public void testRoute(String route, String expectedValue) throws Exception {
        this.getMvc().perform(get("/api/v1/" + route))
                .andExpect(jsonPath("$.content")
                        .value(expectedValue));
    }

    @Test
    public void incorrectURIShouldReturnErrorStatus() throws Exception {
        this.getMvc().perform(get("/fail")).andExpect(status().is4xxClientError());
    }
}