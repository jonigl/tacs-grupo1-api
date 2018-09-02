package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.controller.Controller;
import ar.com.tacsutn.grupo1.eventapp.web.InterestedControllerV1;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InterestedControllerV1Test extends ControllerTest {
    @Autowired private InterestedControllerV1 controller;

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Test
    public void correctURIShouldReturnOkStatus() throws Exception {
        this.getMvc().perform(get("/api/v1/events")).andExpect(status().isOk());
    }

    @Test
    public void incorrectURIShouldReturnErrorStatus() throws Exception {
        this.getMvc().perform(get("/api/v1/fail")).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnDefaultValueWhenNoParamIsSet() throws Exception {
        this.getMvc().perform(get("/api/v1/events"))
                .andExpect(jsonPath("$.content")
                        .value("10 users interested in 'Event0' event."));
    }

    @Test
    public void shouldReturnSetValueWhenParamIsSet() throws Exception {
        this.getMvc().perform(get("/api/v1/events").param("event", "Event1"))
                .andExpect(jsonPath("$.content")
                        .value("10 users interested in 'Event1' event."));
    }
}
