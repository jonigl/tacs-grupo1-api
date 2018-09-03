package ar.com.tacsutn.grupo1.eventapp.controllers;

import ar.com.tacsutn.grupo1.eventapp.Controller;
import ar.com.tacsutn.grupo1.eventapp.web.InterestedController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InterestedControllerTest extends ControllerTest {
    @Autowired private InterestedController controller;

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Test
    public void v1CorrectURIShouldReturnOkStatus() throws Exception {
        this.getMvc().perform(get("/api/v1/events/event")).andExpect(status().isOk());
    }

    @Test
    public void v1ShouldReturnDefaultValueWhenNoParamIsSet() throws Exception {
        this.getMvc().perform(get("/api/v1/events/event"))
                .andExpect(jsonPath("$.content")
                        .value("10 usuarios estan interesados en el evento 0"));
    }

    @Test
    public void v1ShouldReturnSetValueWhenParamIsSet() throws Exception {
        this.getMvc().perform(get("/api/v1/events/event").param("id", "1"))
                .andExpect(jsonPath("$.content")
                        .value("10 usuarios estan interesados en el evento 1"));
    }

    @Test
    public void v2ACorrectURIShouldReturnOkStatus() throws Exception {
        this.getMvc().perform(get("/api/v1/events/0/users")).andExpect(status().isOk());
    }

    @Test
    public void v2AShouldReturnSetValueWhenParamIsSet() throws Exception {
        this.getMvc().perform(get("/api/v1/events/0/users"))
                .andExpect(jsonPath("$.content")
                        .value("Los usuarios 0, 1, y 2 estan interesados en el evento 0"));
    }
}